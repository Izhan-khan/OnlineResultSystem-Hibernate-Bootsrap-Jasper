package controller;

import model.ModelFactory;
import model.UserModel_Interface;
import utility.DataUtility;
import utility.DataValidator;
import utility.PropertyReader;
import utility.ServletUtility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dto.BaseDTO;
import dto.RoleDTO;
import dto.UserDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;

/**
 * User registration functionality Controller. Performs operation for User
 * Registration
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
@WebServlet("/UserRegistration_Controller")
public class UserRegistration_Controller extends Base_Controller {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String OP_SIGN_UP = "SignUp";

	private static Logger log = Logger.getLogger(UserRegistration_Controller.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.info("UserRegistrationCtl Method validate Started");

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
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Email Id"), request);
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.invalid", "Email Id"), request);
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

		if (DataValidator.isNull(dob)) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Date of Birth"), request);
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			System.out.println(dob);
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.invalid", "Date of Birth"), request);
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobile"))) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Mobile"), request);
			pass = false;
		} else if (request.getParameter("mobile").length() != 10) {
			ServletUtility.setErrorMessage("Mobile Number should be of 10 digits",request);
			pass = false;
		} else if (!DataValidator.isLong(request.getParameter("mobile"))) {
			ServletUtility.setErrorMessage("Mobile Number should not contain letters",request);
			pass = false;
		}

		if (request.getParameter("gender").equals("0")){
			ServletUtility.setErrorMessage("Please select gender", request);
			pass = false;
		}
		
		
		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			ServletUtility.setErrorMessage("Confirm  Password  does not match", request);
			pass = false;
		}

		log.info("UserRegistrationCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.info("UserRegistrationCtl Method populateDTO Started");

		UserDTO dto = new UserDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setRoleId(RoleDTO.STUDENT);

		dto.setName(DataUtility.getString(request.getParameter("firstName"))+" "+DataUtility.getString(request.getParameter("lastName")));

		dto.setLogin(DataUtility.getString(request.getParameter("login")));

		dto.setPassword(DataUtility.getString(request.getParameter("password")));
		
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobile")));

		dto.setGender(DataUtility.getString(request.getParameter("gender")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		populateGenericDTO(dto, request);
		
		log.info("UserRegistrationCtl Method populateDTO Ended");

		return dto;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("UserRegistrationCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModel_Interface model = ModelFactory.getInstance().getUserModel();

		if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			UserDTO dto = (UserDTO) populateDTO(request);

			try {
				long pk = model.registerUser(dto);
				dto.setId(pk);
				request.getSession().setAttribute("userDto", dto);
				ServletUtility.setSuccessMessage("You have been successfully Registered", request);
				ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				log.error(e);
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
				ServletUtility.forward(ORSView.USER_REGISTRATION_VIEW, request, response);
			}

		} else {
			ServletUtility.forward(ORSView.USER_REGISTRATION_VIEW, request, response);
		}

		log.info("UserRegistrationCtl Method doGet Ended");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.USER_REGISTRATION_VIEW;
	}

}