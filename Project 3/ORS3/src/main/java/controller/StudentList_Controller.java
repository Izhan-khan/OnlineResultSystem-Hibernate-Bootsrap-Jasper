package controller;

import model.ModelFactory;
import model.StudentModel_Interface;
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
import dto.CollegeDTO;
import dto.StudentDTO;
import exception.ApplicationException;

/**
 * Student List functionality Controller. Performs operation for list, search
 * and delete operations of Student
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
@WebServlet("/controller/StudentList_Controller")
public class StudentList_Controller extends Base_Controller {

	private static Logger log = Logger.getLogger(StudentList_Controller.class);

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		StudentDTO dto = new StudentDTO();

		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setEmail(DataUtility.getString(request.getParameter("email")));

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("StudentListCtl doGet Start");

		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		System.out.println("page-number  "+pageNo);

		pageNo = (pageNo == 0) ? 1 : pageNo;

		System.out.println("page-number  "+pageNo);

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		StudentDTO dto = (StudentDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		StudentModel_Interface model = ModelFactory.getInstance().getStudentModel();

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
                ServletUtility.redirect(ORSView.STUDENT_CTL, request,
                        response);
                return;
            }else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo=1;
				String[] ids = request.getParameterValues("ids");
				try {
					System.out.println("ids "+ids);
					if(ids != null &&ids.length>0) {
						
						StudentDTO DTOdelete = new StudentDTO();
						
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

				ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
				return;

			} 
			
								
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setDto(dto, request);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(ORSView.STUDENT_LIST_VIEW, request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.info("StudentListCtl doGet End");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doGet(req, resp);
	}
	
	@Override
	protected String getView() {
		return ORSView.STUDENT_LIST_VIEW;
	}
}