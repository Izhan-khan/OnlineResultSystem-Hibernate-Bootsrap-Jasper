package controller;

import model.CollegeModel_Interface;
import model.CourseModel_Interface;
import model.ModelFactory;
import model.SubjectModel_Interface;
import model.TimeTableModel_Interface;
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
import dto.StudentDTO;
import dto.TimeTableDTO;
import exception.ApplicationException;
import exception.RecordNotFoundException;

/**
 * TimeTable List functionality Controller. Performs operation for list, search
 * and delete operations of TimeTable
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
@WebServlet("/controller/TimeTableList_Controller")
public class TimeTableList_Controller extends Base_Controller {

	private static Logger log = Logger.getLogger(TimeTableList_Controller.class);

	
	

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

		TimeTableDTO dto = new TimeTableDTO();
		
		dto.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		dto.setExamTime(DataUtility.getString(request.getParameter("examTime")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		dto.setSemester(DataUtility.getLong(request.getParameter("semester")));
		
		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("TimeTableListCtl doGet Start");

		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		TimeTableDTO dto = (TimeTableDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		TimeTableModel_Interface model = ModelFactory.getInstance().getTimeTableModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
				
			}else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.TIMETABLE_CTL, request,
                        response);
                return;
            }else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo=1;
				String[] ids = request.getParameterValues("ids");
				try {
					System.out.println("ids "+ids);
					if(ids != null &&ids.length>0) {
						
						TimeTableDTO DTOdelete = new TimeTableDTO();
						
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
				} catch (RecordNotFoundException e) {
					ServletUtility.setErrorMessage("Record not found", request);
					return;
				}

			} else if (OP_CANCEL.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
				return;

			} 
			System.out.println("sub"+dto.getSubjectId());
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setList(list, request);
			
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setDto(dto, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(ORSView.TIMETABLE_LIST_VIEW, request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.info("TimeTableListCtl doGet End");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	@Override
	protected String getView() {
		return ORSView.TIMETABLE_LIST_VIEW;
	}
}