package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dto.BaseDTO;
import dto.CollegeDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;
import model.CollegeModel_Interface;
import model.ModelFactory;
import utility.DataUtility;
import utility.DataValidator;
import utility.PropertyReader;
import utility.ServletUtility;

/**
 * College functionality Controller. Performs operation for add, update, delete
 * and get College
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
@WebServlet("/controller/College_Controller")
public class College_Controller extends Base_Controller {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(College_Controller.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.info("CollegeCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.require", "Name"),request);
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address"))) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.require", "Address"),request);
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("state"))) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.require", "State"),request);
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("city"))) {
			ServletUtility.setErrorMessage( PropertyReader.getValue("error.require", "City"),request);
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("phoneNo"))) {
			ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Mobile"), request);
			pass = false;
		} else if (request.getParameter("phoneNo").length() != 10) {
			ServletUtility.setErrorMessage("Mobile Number should be of 10 digits",request);
			pass = false;
		} else if (!DataValidator.isLong(request.getParameter("phoneNo"))) {
			ServletUtility.setErrorMessage("Mobile Number should not contain letters",request);
			pass = false;
		}
		
		

		log.info("CollegeCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.info("CollegeCtl Method populateDTO Started");

		CollegeDTO dto = new CollegeDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setName(DataUtility.getString(request.getParameter("name")));

		dto.setAddress(DataUtility.getString(request.getParameter("address")));

		dto.setState(DataUtility.getString(request.getParameter("state")));

		dto.setCity(DataUtility.getString(request.getParameter("city")));

		dto.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));

		populateGenericDTO(dto, request);
		
		log.info("CollegeCtl Method populateDTO Ended");

		return dto;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("CollegeCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		CollegeModel_Interface model = ModelFactory.getInstance().getCollegeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			CollegeDTO dto = (CollegeDTO) populateDTO(request);

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
				ServletUtility.setErrorMessage("College Name already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			CollegeDTO dto = (CollegeDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;

		} else { // View page

			if (id > 0 || op != null) {
				CollegeDTO dto;
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

		ServletUtility.forward(ORSView.COLLEGE_VIEW, request, response);

		log.info("CollegeCtl Method doGet Ended");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doGet(req, resp);
	}
	
	
	@Override
	protected String getView() {
		return ORSView.COLLEGE_VIEW;
	}

}