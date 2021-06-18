package controller;

import model.CollegeModel_Interface;
import model.ModelFactory;
import model.StudentModel_Interface;
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
import dto.StudentDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;

/**
 * Student functionality Controller. Performs operation for add, update, delete
 * and get Student
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
@WebServlet("/controller/Student_Controller")
public class Student_Controller extends Base_Controller {

	private static Logger log = Logger.getLogger(Student_Controller.class);

	@Override
	protected void preload(HttpServletRequest request) {
		CollegeModel_Interface model = ModelFactory.getInstance().getCollegeModel();
		try {
			List l = model.list();
			request.setAttribute("collegeList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.info("StudentCtl Method validate Started");

		boolean pass = true;

		String op = DataUtility.getString(request.getParameter("operation"));
		String email = request.getParameter("email");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "First Name"), request);
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.require", "Last Name"), request);
			pass = false;
		}
		
		if (DataValidator.isNull(dob)) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.require", "Date Of Birth"), request);
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.date", "Date Of Birth"), request);
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
		
		if (DataValidator.isNull(email)) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Email Id"), request);
			pass = false;
		} else if (!DataValidator.isEmail(email)) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.invalid", "Email Id"), request);
			pass = false;
		}
		
		if ((request.getParameter("collegeId").equalsIgnoreCase("0"))) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.select", "College "), request);
			pass = false;
		}

		log.info("StudentCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.info("StudentCtl Method populateDTO Started");

		StudentDTO dto = new StudentDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setName(DataUtility.getString(request.getParameter("firstName"))+ " " + DataUtility.getString(request.getParameter("lastName")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		dto.setMobileNo(DataUtility.getString(request.getParameter("mobile")));

		dto.setEmail(DataUtility.getString(request.getParameter("email")));

		dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

		populateGenericDTO(dto, request);
		
		log.info("StudentCtl Method populateDTO Ended");

		return dto;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("StudentCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model

		StudentModel_Interface model = ModelFactory.getInstance().getStudentModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			StudentDTO dto = (StudentDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
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
				ServletUtility.setErrorMessage("Student Email Id already exists", request);
			}

		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			StudentDTO dto = (StudentDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;

		} else { // View page

			if (id > 0 || op != null) {
				StudentDTO dto;
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

		ServletUtility.forward(ORSView.STUDENT_VIEW, request, response);

		log.info("StudentCtl Method doGet Ended");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doGet(req, resp);
	}
	
	
	@Override
	protected String getView() {
		return ORSView.STUDENT_VIEW;
	}

}