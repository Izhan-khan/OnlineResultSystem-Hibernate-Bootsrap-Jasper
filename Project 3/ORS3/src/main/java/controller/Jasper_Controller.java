package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.internal.SessionImpl;

import dto.UserDTO;
import utility.HibernateDataSource;
import utility.JDBCDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Servlet implementation class Jasper
 * 
 *  
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */


@WebServlet("/controller/Jasper_Controller")
public class Jasper_Controller extends Base_Controller {
	
	public Jasper_Controller() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("DoGet Jasper Report");
		try {
			
			// jrxml file contains jasper Design which we get from using TIBCO Jaspersoft 
			
			JasperReport jasperReport = JasperCompileManager.compileReport("C:\\Users\\Khan\\Desktop\\Project  Backup\\Project 3\\ORS3\\src\\main\\resources\\MeritList.jrxml");
			
			//Here we are taking jrxml file and converting jasper Design to Jasper Report Template
			
			HttpSession session = request.getSession(true);
			UserDTO dto = (UserDTO) session.getAttribute("user");
			dto.getName();
			
			//this map file is used to provide the author detail in final pdf (downside)(can be empty also)
			
			Map<String, Object> map = new HashMap();
			map.put("user", dto.getName());
			Connection conn = null;
			
			ResourceBundle rb = ResourceBundle.getBundle("system");
			
			String Database = rb.getString("DATABASE");
			if ("Hibernate".equalsIgnoreCase(Database)) {
				conn = ((SessionImpl) HibernateDataSource.getSession()).connection();
			}

			if ("JDBC".equalsIgnoreCase(Database)) {
				conn = JDBCDataSource.getConnection();
			}

			// here jasper Resport Template is filled with data and is converted into Jasper Print(.jrprint file)
			//which is now ready to be viewed ,print or can be exported into diffrent formats
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
			
			// here we are converting & exporting Jasper Print(.jrprint file) to a pdf file
			
			byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
		
			
			
			
			// response.setContentType() means what type of response you want to send to client
			
			response.setContentType("application/pdf");
			response.getOutputStream().write(pdf);
			response.getOutputStream().flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected String getView() {
		return null;
	}
}