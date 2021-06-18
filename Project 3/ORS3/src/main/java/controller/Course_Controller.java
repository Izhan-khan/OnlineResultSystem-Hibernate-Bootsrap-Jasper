package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dto.BaseDTO;
import dto.CourseDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;
import model.CourseModel_Interface;
import model.ModelFactory;
import utility.DataUtility;
import utility.DataValidator;
import utility.PropertyReader;
import utility.ServletUtility;

/**
 * Course functionality Controller. Performs operation for add, update, delete
 * and get Course
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
@WebServlet("/controller/Course_Controller")
public class Course_Controller extends Base_Controller {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(Course_Controller.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.info("CourseCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Name"), request);
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Description"), request);
			pass = false;
		}
		if (request.getParameter("duration").equalsIgnoreCase("0")) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.select", "Duration"), request);
			pass = false;
		}
		
		log.info("CourseCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.info("CourseCtl Method populateDTO Started");

		CourseDTO dto = new CourseDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setName(DataUtility.getString(request.getParameter("name")));

		dto.setDescription(DataUtility.getString(request.getParameter("description")));

		dto.setDuration(DataUtility.getLong(request.getParameter("duration")));

		populateGenericDTO(dto, request);
		
		log.info("CourseCtl Method populateDTO Ended");

		return dto;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("CourseCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		CourseModel_Interface model = ModelFactory.getInstance().getCourseModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			CourseDTO dto = (CourseDTO) populateDTO(request);

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
				ServletUtility.setErrorMessage("Course already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			CourseDTO dto = (CourseDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;

		} else { // View page

			if (id > 0 || op != null) {
				CourseDTO dto;
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

		ServletUtility.forward(ORSView.COURSE_VIEW, request, response);

		log.info("CourseCtl Method doGet Ended");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	doGet(req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.COURSE_VIEW;
	}

}