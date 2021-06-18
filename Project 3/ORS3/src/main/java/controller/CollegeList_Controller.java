package controller;

import model.CollegeModel_Interface;
import model.ModelFactory;
import utility.DataUtility;
import utility.PropertyReader;
import utility.ServletUtility;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dto.BaseDTO;
import dto.CollegeDTO;
import exception.ApplicationException;

/**
 * College List functionality Controller. Performs operation for list, search
 * and delete operations of College
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */

@WebServlet("/controller/CollegeList_Controller")
public class CollegeList_Controller extends Base_Controller {

	private static Logger log = Logger.getLogger(CollegeList_Controller.class);

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		CollegeDTO dto = new CollegeDTO();

		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setCity(DataUtility.getString(request.getParameter("city")));

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("CollegeListCtl.doGet Start");

		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		CollegeDTO dto = (CollegeDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		CollegeModel_Interface model = ModelFactory.getInstance().getCollegeModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			}else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.COLLEGE_CTL, request,
                        response);
                return;
            }else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo=1;
				String[] ids = request.getParameterValues("ids");
				try {
					System.out.println("ids "+ids);
					if(ids != null &&ids.length>0) {
						
						CollegeDTO DTOdelete = new CollegeDTO();
						
						for(String id :ids) {
							System.out.println("id "+id);
							DTOdelete.setId(DataUtility.getLong(id)); 
							model.delete(DTOdelete);
							ServletUtility.setSuccessMessage("Data Successfully deleted", request);
						}
						op=null;
						dto=null;
						
					}else {
						ServletUtility.setErrorMessage("Select at least one record", request);
						dto = null;
					}
					
				} catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				}

			} else if (OP_CANCEL.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
				return;

			} 
			
			
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setDto(dto, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(ORSView.COLLEGE_LIST_VIEW, request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.info("CollegeListCtl doGet End");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doGet(req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.COLLEGE_LIST_VIEW;
	}
}