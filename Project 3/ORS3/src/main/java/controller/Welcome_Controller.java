package controller;
 
import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.log4j.Logger;

import utility.ServletUtility;
 
/**
 * Welcome functionality Controller. Performs operation for Show Welcome page
 *  
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */

@WebServlet("/Welcome_Controller")
public class Welcome_Controller extends Base_Controller {
 
    private static Logger log = Logger.getLogger(Welcome_Controller.class);
 
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.info("WelcomeCtl Method doGet Started");
 
        ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
 
        log.info("WelcomeCtl Method doGet Ended");
    }
 
    @Override
    protected String getView() {
        return ORSView.WELCOME_VIEW;
    }
    
}