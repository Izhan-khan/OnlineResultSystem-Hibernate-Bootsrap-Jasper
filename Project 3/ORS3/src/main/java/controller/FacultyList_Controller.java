package controller;
 
import model.CollegeModel_Interface;
import model.CourseModel_Interface;
import model.FacultyModel_Interface;
import model.ModelFactory;
import model.SubjectModel_Interface;
import utility.DataUtility;
import utility.PropertyReader;
import utility.ServletUtility;

import java.io.IOException;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.log4j.Logger;

import dto.BaseDTO;
import dto.FacultyDTO;
import dto.StudentDTO;
import exception.ApplicationException;
 
/**
 * Faculty List functionality Controller. Performs operation for list, search
 * and delete operations of Faculty
 *  
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
 
@WebServlet("/controller/FacultyList_Controller") 
public class FacultyList_Controller extends Base_Controller {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(FacultyList_Controller.class);
 
	
	@Override
	protected void preload(HttpServletRequest request) {

	CollegeModel_Interface collegeModel = ModelFactory.getInstance().getCollegeModel();
	CourseModel_Interface courseModel = ModelFactory.getInstance().getCourseModel();
	SubjectModel_Interface subjectModel = ModelFactory.getInstance().getSubjectModel();
	
	try {
		List collegeList = collegeModel.list();
		List courseList = courseModel.list();
		List subjectList = subjectModel.list();
		
		
		request.setAttribute("collegeList", collegeList);
		request.setAttribute("courseList", courseList);
		request.setAttribute("subjectList", subjectList);

	
	} catch (ApplicationException e) {
		// TODO: handle exception
		log.error(e);
	}


}



	
	
	
	
    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        FacultyDTO dto = new FacultyDTO();
 
        dto.setName(DataUtility.getString(request.getParameter("name")));
        dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
        dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
        dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
        
        return dto;
    }
 
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.info("FacultyListCtl.doGet Start");
 
        List list = null;
 
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
 
        pageNo = (pageNo == 0) ? 1 : pageNo;
 
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
                .getValue("page.size")) : pageSize;
 
        FacultyDTO dto = (FacultyDTO) populateDTO(request);
 
        String op = DataUtility.getString(request.getParameter("operation"));
 
        FacultyModel_Interface model = ModelFactory.getInstance().getFacultyModel();
 
        try {
 
            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
                    || "Previous".equalsIgnoreCase(op)) {
 
                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }
 
            }else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.FACULTY_CTL, request,
                        response);
                return;
            }else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo=1;
				String[] ids = request.getParameterValues("ids");
				try {
					System.out.println("ids "+ids);
					if(ids != null &&ids.length>0) {
						
						FacultyDTO DTOdelete = new FacultyDTO();
						
						for(String id :ids) {
							System.out.println("id "+id);
							DTOdelete.setId(DataUtility.getLong(id)); 
							model.delete(DTOdelete);
							ServletUtility.setSuccessMessage("Data Successfully deleted", request);
						}
					
					}else {
						ServletUtility.setErrorMessage("Select at least one record", request);
					}
					
				} catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				}

			} else if (OP_CANCEL.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return;

			} 
			
								
			list = model.search(dto, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setDto(dto, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(ORSView.FACULTY_LIST_VIEW, request, response);
 
        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
        log.info("FacultyListCtl doGet End");
    }
 
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    		doGet(req, resp);
    }
    
    @Override
    protected String getView() {
        return ORSView.FACULTY_LIST_VIEW;
    }
}