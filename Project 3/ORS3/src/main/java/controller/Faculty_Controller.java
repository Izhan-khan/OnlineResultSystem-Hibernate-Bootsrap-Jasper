package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dto.BaseDTO;
import dto.FacultyDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;
import model.CollegeModel_Interface;
import model.CourseModel_Interface;
import model.FacultyModel_Interface;
import model.ModelFactory;
import model.SubjectModel_Interface;
import utility.DataUtility;
import utility.DataValidator;
import utility.PropertyReader;
import utility.ServletUtility;

/**
 * Faculty functionality Controller. Performs operation for add, update, delete
 * and get Faculty
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
@WebServlet("/controller/Faculty_Controller")
public class Faculty_Controller extends Base_Controller {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(Faculty_Controller.class);

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
	protected boolean validate(HttpServletRequest request) {

		log.info("FacultyCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.require", "First Name"), request);
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.require", "Last Name"), request);
			pass = false;
		}


		if (DataValidator.isNull(request.getParameter("qualification"))) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.require", "Qualification"), request);
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("email"))) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.require", "Email"), request);
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
		
		if (DataValidator.isNull(request.getParameter("gender"))) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.select", "Gender"), request);
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dob"))) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.select", "Date of Birth"), request);
			pass = false;
		}
		if (request.getParameter("collegeId").equalsIgnoreCase("0")) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.select", "College Id"), request);
			pass = false;
		}
		if (request.getParameter("courseId").equalsIgnoreCase("0")) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.select", "Course Id"), request);
			pass = false;
		}
		if (request.getParameter("subjectId").equalsIgnoreCase("0")) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.select", "Subject Id"), request);
			pass = false;
		}
		
		log.info("FacultyCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.info("FacultyCtl Method populateDTO Started");

		FacultyDTO dto = new FacultyDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setName(DataUtility.getString(request.getParameter("firstName"))+" "+DataUtility.getString(request.getParameter("lastName")));	

		dto.setQualification(DataUtility.getString(request.getParameter("qualification")));

		dto.setEmail(DataUtility.getString(request.getParameter("email")));

		dto.setGender(DataUtility.getString(request.getParameter("gender")));

		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		
		dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		
		populateGenericDTO(dto, request);
		
		log.info("FacultyCtl Method populateDTO Ended");

		return dto;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("FacultyCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		FacultyModel_Interface model = ModelFactory.getInstance().getFacultyModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			FacultyDTO dto = (FacultyDTO) populateDTO(request);

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
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Faculty Name already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			FacultyDTO dto = (FacultyDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;

		} else { // View page

			if (id > 0 || op != null) {
				FacultyDTO dto;
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

		ServletUtility.forward(ORSView.FACULTY_VIEW, request, response);

		log.info("FacultyCtl Method doGet Ended");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doGet(req, resp);
	}
	
	
	
	@Override
	protected String getView() {
		return ORSView.FACULTY_VIEW;
	}

}