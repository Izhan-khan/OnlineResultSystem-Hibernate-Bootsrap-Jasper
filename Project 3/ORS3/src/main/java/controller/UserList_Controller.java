package controller;

import model.CourseModel_Interface;
import model.ModelFactory;
import model.RoleModel_Interface;
import model.UserModel_Interface;
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
import dto.UserDTO;
import exception.ApplicationException;

/**
 * User List functionality Controller. Performs operation for list, search and
 * delete operations of User
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
@WebServlet("/controller/UserList_Controller")
public class UserList_Controller extends Base_Controller {

	private static Logger log = Logger.getLogger(UserList_Controller.class);

	
	

	@Override
		protected void preload(HttpServletRequest request) {

		 RoleModel_Interface model = ModelFactory.getInstance().getRoleModel();
	        try {
	            List l = model.list();
	            request.setAttribute("roleList", l);
	        } catch (ApplicationException e) {
	            log.error(e);
	        
	        }
	}
	
	
	
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		UserDTO dto = new UserDTO();

		dto.setName(DataUtility.getString(request.getParameter("name")));

		dto.setLogin(DataUtility.getString(request.getParameter("login")));

		dto.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("UserListCtl doGet Start");

		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		UserDTO dto = (UserDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("op "+op);
		
		UserModel_Interface model = ModelFactory.getInstance().getUserModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					System.out.println(pageNo);
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				System.out.println("in delete");
				// get the selected checkbox ids array for delete list
				String[] ids = request.getParameterValues("ids");
				if (ids != null && ids.length > 0) {
					UserDTO deletedDto = new UserDTO();
					for (String id : ids) {
						deletedDto.setId(DataUtility.getLong(id));
						model.delete(deletedDto);
						 ServletUtility.setSuccessMessage(
	                                "Data Deleted Successfully", request);
						 op =null;
						 dto=null;
						 
	                    }
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
					dto = null;
				}
			}
			if(op==null) dto=null;
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setDto(dto, request);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(ORSView.USER_LIST_VIEW, request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.info("UserListCtl doGet End");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.USER_LIST_VIEW;
	}

}
