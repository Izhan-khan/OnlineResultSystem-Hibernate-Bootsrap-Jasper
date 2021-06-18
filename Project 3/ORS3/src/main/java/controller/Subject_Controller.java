package controller;
 
import model.CollegeModel_Interface;
import model.CourseModel_Interface;
import model.ModelFactory;
import model.SubjectModel_Interface;
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
import dto.SubjectDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;
 
/**
 * Subject functionality Controller. Performs operation for add, update,
 * delete and get Subject
 *  
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
 @WebServlet("/controller/Subject_Controller")
public class Subject_Controller extends Base_Controller {
 
    private static Logger log = Logger.getLogger(Subject_Controller.class);
 
    @Override
	protected void preload(HttpServletRequest request) {

	 CourseModel_Interface model = ModelFactory.getInstance().getCourseModel();
        try {
            List l = model.list();
            request.setAttribute("courseList", l);
        } catch (ApplicationException e) {
            log.error(e);
        
        }
}
 
    @Override
    protected boolean validate(HttpServletRequest request) {
 
        log.info("SubjectCtl Method validate Started");
 
        boolean pass = true;
 
        String op = DataUtility.getString(request.getParameter("operation"));
        String email = request.getParameter("email");
        String dob = request.getParameter("dob");
 
        if (DataValidator.isNull(request.getParameter("name"))) {
        	ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Subject Name"),request);
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("description"))) {
        	ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "description"),request);
            pass = false;
        }
        if (request.getParameter("courseId").equalsIgnoreCase("0")) {
        	ServletUtility.setErrorMessage(PropertyReader.getValue("error.select", "Course Id"),request);
            pass = false;
        }
 
        log.info("SubjectCtl Method validate Ended");
 
        return pass;
    }
 
    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
 
        log.info("SubjectCtl Method populateDTO Started");
 
        SubjectDTO dto = new SubjectDTO();
 
        dto.setId(DataUtility.getLong(request.getParameter("id")));
 
        dto.setName(DataUtility.getString(request.getParameter("name")));
 
        dto.setDescription(DataUtility.getString(request.getParameter("description")));
 
        dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
 
        populateGenericDTO(dto, request);
        
        log.info("SubjectCtl Method populateDTO Ended");
 
        return dto;
    }
 
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
 
        log.info("SubjectCtl Method doGet Started");
 
        String op = DataUtility.getString(request.getParameter("operation"));
 
        // get model
 
        SubjectModel_Interface model = ModelFactory.getInstance().getSubjectModel();
 
        long id = DataUtility.getLong(request.getParameter("id"));
 
        if (OP_SAVE.equalsIgnoreCase(op)) {
 
            SubjectDTO dto = (SubjectDTO) populateDTO(request);
 System.out.println("id"+id);
            try {
                if (id > 0) {
                    model.update(dto);
                } else {
                    long pk = model.add(dto);
                    dto.setId(pk);
                }
 
                ServletUtility.setDto(dto, request);
                ServletUtility.setSuccessMessage("Data is successfully saved",
                        request);
 
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setDto(dto, request);
                ServletUtility.setErrorMessage(
                        "Subject already exists", request);
            }
 
        }
 
        else if (OP_DELETE.equalsIgnoreCase(op)) {
 
            SubjectDTO dto = (SubjectDTO) populateDTO(request);
            try {
                model.delete(dto);
                ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request,
                        response);
                return;
 
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
 
        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
 
            ServletUtility
                    .redirect(ORSView.SUBJECT_LIST_CTL, request, response);
            return;
 
        } else { // View page
 
            if (id > 0 || op != null) {
                SubjectDTO dto;
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
 
        ServletUtility.forward(ORSView.SUBJECT_VIEW, request, response);
 
        log.info("SubjectCtl Method doGet Ended");
    }
 
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    		doGet(req, resp);
    }
    
    
    @Override
    protected String getView() {
        return ORSView.SUBJECT_VIEW;
    }
 
}