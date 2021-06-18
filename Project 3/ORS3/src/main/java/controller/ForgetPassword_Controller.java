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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.log4j.Logger;

import dto.BaseDTO;
import dto.UserDTO;
import exception.ApplicationException;
import exception.RecordNotFoundException;
 
/**
 * Forget Password functionality Controller. Performs operation for Forget
 * Password
 *  
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
 
@WebServlet("/ForgetPassword_Controller")
public class ForgetPassword_Controller extends Base_Controller {
 
    private static Logger log = Logger.getLogger(ForgetPassword_Controller.class);
 
    @Override
    protected boolean validate(HttpServletRequest request) {
 
        log.info("ForgetPasswordCtl Method validate Started");
 
        boolean pass = true;
        
        String login = request.getParameter("login");
        
        if (DataValidator.isNull(login)) {
        	ServletUtility.setErrorMessage(PropertyReader.getValue("error.require", "Email Id"), request);
			pass = false;
        }else if (!DataValidator.isEmail(login)) {
        	ServletUtility.setErrorMessage(PropertyReader.getValue("error.invalid", "Email Id"), request);
			pass = false;
        }
        log.info("ForgetPasswordCtl Method validate Ended");
 
        return pass;
    }
 
    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
 
        log.info("ForgetPasswordCtl Method populateDTO Started");
 
        UserDTO dto = new UserDTO();
 
        dto.setLogin(DataUtility.getString(request.getParameter("login")));
 
        log.info("ForgetPasswordCtl Method populateDTO Ended");
 
        return dto;
    }
 
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.info("ForgetPasswordCtl Method doGet Started");
 
        String op = DataUtility.getString(request.getParameter("operation"));
 
        UserDTO dto = (UserDTO) populateDTO(request);
 
        // get model
        UserModel_Interface model = ModelFactory.getInstance().getUserModel();
 
        if (OP_GO.equalsIgnoreCase(op)) {
 
            try {
                model.forgetPassword(dto.getLogin());
                ServletUtility.setSuccessMessage(
                		"Password has been sent to your email id",
                        request);
            } catch (RecordNotFoundException e) {
                ServletUtility.setErrorMessage(e.getMessage(), request);
                log.error(e);
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
                ServletUtility.forward(ORSView.FORGET_PASSWORD_VIEW, request,
                        response);
        } else {
            ServletUtility.forward(ORSView.FORGET_PASSWORD_VIEW, request,
                    response);
        }
 
        log.info("ForgetPasswordCtl Method doGet Ended");
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }
 
    @Override
    protected String getView() {
        return ORSView.FORGET_PASSWORD_VIEW;
    }
 
}