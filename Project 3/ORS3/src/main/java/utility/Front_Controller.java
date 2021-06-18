package utility;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.ORSView;

/**
 * Manages Session checking and prevent users to access Application without login
 * 
 * @author SUNRAYS Technologies
 *@version 1.0
 */
@WebFilter({"/controller/*","/doc/*"})
public class Front_Controller implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		
		HttpSession session = httpRequest.getSession();
		
		
		System.out.println("User"+session.getAttribute("user"));
		
		
		if(session.getAttribute("user") == null) {
			
			httpRequest.setAttribute("sessionExpiredMessage", "Session Expired Re-Login");
			String URI = httpRequest.getRequestURI();
			session.setAttribute("URI", URI);
			System.out.println("uri "+URI);
			ServletUtility.forward(ORSView.LOGIN_VIEW,httpRequest, httpResponse);
			
			
		}else {
			chain.doFilter(httpRequest, httpResponse);
			
		}
		
		
	}

	public void destroy() {
		
	}

}
