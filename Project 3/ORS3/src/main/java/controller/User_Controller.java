
package controller;

import model.ModelFactory;
import model.RoleModel_Interface;
import model.UserModel_Interface;
import utility.DataUtility;
import utility.DataValidator;
import utility.PropertyReader;
import utility.ServletUtility;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dto.BaseDTO;
import dto.UserDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;

/**
 * User functionality Controller. Performs operation for add, update and get
 * User
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
@WebServlet("/controller/User_Controller")
public class User_Controller extends Base_Controller {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(User_Controller.class);

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
	protected boolean validate(HttpServletRequest request) {

		log.info("UserCtl Method validate Started");

		boolean pass = true;

		String login = request.getParameter("login");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "First Name"), request);
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Last Name"), request);
			pass = false;
		}

		if (DataValidator.isNull(login)) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Login Id"), request);
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.email", "Login "), request);
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("password"))) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Password"), request);
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Confirm Password"), request);
			pass = false;
		}
		

		if (request.getParameter("gender").equals("0")){
			ServletUtility.setErrorMessage("Please select gender", request);
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Mobile"), request);
			pass = false;
		} else if (request.getParameter("mobileNo").length() != 10) {
			ServletUtility.setErrorMessage("Mobile Number should be of 10 digits",request);
			pass = false;
		} else if (!DataValidator.isLong(request.getParameter("mobileNo"))) {
			ServletUtility.setErrorMessage("Mobile Number should not contain letters",request);
			pass = false;
		}

		
		if (DataValidator.isNull(dob)) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.require", "Date Of Birth"), request);
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.date", "Date Of Birth"), request);
			pass = false;
		}
		
		
		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			ServletUtility.setErrorMessage("Password and confirm password should be same.", request);
			pass = false;
		}
		log.info("UserCtl Method validate Ended");

		if ("0".equals(request.getParameter("roleId"))) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.select", "Role Id"), request);
			pass = false;
		}
		log.info("UserCtl Method validate Ended");

		
		
		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.info("UserCtl Method populateDTO Started");

		UserDTO dto = new UserDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		dto.setName(DataUtility.getString(request.getParameter("firstName")) + " "
				+ DataUtility.getString(request.getParameter("lastName")));

		dto.setLogin(DataUtility.getString(request.getParameter("login")));

		dto.setPassword(DataUtility.getString(request.getParameter("password")));

		dto.setGender(DataUtility.getString(request.getParameter("gender")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		populateGenericDTO(dto, request);
		
		log.info("UserCtl Method populateDTO Ended");

		return dto;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("UserCtl Method doGet Started");
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModel_Interface model = ModelFactory.getInstance().getUserModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			UserDTO dto = (UserDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					System.out.println(id);
				} else {
					long pk = model.add(dto);
					dto.setId(pk);
				}
				ServletUtility.setDto(dto, request);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			UserDTO dto = (UserDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;

		} else { // View page

			if (id > 0 || op != null) {
				System.out.println("in id > 0  condition");
				UserDTO dto;
				try {
					dto = model.findByPK(id);
					ServletUtility.setDto(dto, request);
				} catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				}
			}
		}

		ServletUtility.forward(ORSView.USER_VIEW, request, response);

		log.info("UserCtl Method doGet Ended");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.USER_VIEW;
	}

}
