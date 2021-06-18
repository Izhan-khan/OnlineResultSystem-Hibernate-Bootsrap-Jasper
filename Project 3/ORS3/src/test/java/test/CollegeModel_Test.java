package test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import dto.CollegeDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;
import model.CollegeModel_Interface;
import model.CollegeModel_JDBC_Implement;
import model.ModelFactory;

/**
 * College Model Test classes
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 * 
 */
public class CollegeModel_Test {

	/**
	 * Model object to test
	 */

//	public static CollegeModel_Interface model = ModelFactory.getInstance().getCollegeModel();

	public static CollegeModel_Interface model = ModelFactory.getInstance().getCollegeModel();
	
	/**
	 * Main method to call test methods.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
//        testAdd();
//		 testDelete()
//		 testUpdate();
//		 testFindByName();
//		 testFindByPK();
		 testSearch();
		// testList();

		
	}

	/**
	 * Tests add a College
	 */
	public static void testAdd() {

		try {
			for(int i=6;i<=15;i++) {
			CollegeDTO dto = new CollegeDTO();

			dto.setName("College "+i);
			dto.setAddress("Address "+i);
			dto.setState("State "+i);
			dto.setCity("City "+i);
			dto.setPhoneNo(Long.toString(Math.round(Math.random()*Math.pow(10, 10))));
			dto.setCreatedBy("Admin");
			dto.setModifiedBy("Admin");
			dto.setCreatedDatetime(Timestamp.from(Instant.now()));
			dto.setModifiedDatetime(Timestamp.from(Instant.now()));
			long pk = model.add(dto);
			System.out.println("Test add succ");
			CollegeDTO addedDto = model.findByPK(pk);
			if (addedDto == null) {
				System.out.println("Test add fail");
			}
			
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests delete a College
	 */
	public static void testDelete() {

		try {
			CollegeDTO dto = new CollegeDTO();
			dto.setId(4L);
			model.delete(dto);
			System.out.println("Test Delete succ");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests update a College
	 */
	public static void testUpdate() {

		try {
			CollegeDTO dto = model.findByPK(4L);
			dto.setName("College 4");
			dto.setAddress("Telibandha");
			dto.setState("C.G");
			dto.setCity("Rapiur");
			dto.setPhoneNo("9945245455");
			dto.setCreatedBy("Admin");
			dto.setModifiedBy("Admin");
			dto.setCreatedDatetime(Timestamp.from(Instant.now()));
			dto.setModifiedDatetime(Timestamp.from(Instant.now()));
			model.update(dto);
			CollegeDTO updatedDTO = model.findByPK(1L);
			System.out.println("Test Update succ");
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests find a College by Name.
	 */

	public static void testFindByName() {

		try {
			CollegeDTO dto = model.findByName("College 1");

			if(dto==null) System.out.println("dto :"+dto);
				
				else{
				
					
				System.out.println(dto.getId());
				System.out.println(dto.getName());
				System.out.println(dto.getAddress());
				System.out.println(dto.getState());
				System.out.println(dto.getCity());
				System.out.println(dto.getPhoneNo());
				System.out.println(dto.getCreatedBy());
				System.out.println(dto.getCreatedDatetime());
				System.out.println(dto.getModifiedBy());
				System.out.println(dto.getModifiedDatetime());
				}
			} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests find a College by PK.
	 */
	public static void testFindByPK() {
		try {		
			
			CollegeDTO dto = model.findByPK(2);

		if(dto==null) System.out.println("dto :"+dto);
			
			else{
			
				
			System.out.println(dto.getId());
			System.out.println(dto.getName());
			System.out.println(dto.getAddress());
			System.out.println(dto.getState());
			System.out.println(dto.getCity());
			System.out.println(dto.getPhoneNo());
			System.out.println(dto.getCreatedBy());
			System.out.println(dto.getCreatedDatetime());
			System.out.println(dto.getModifiedBy());
			System.out.println(dto.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests search a College
	 */

	public static void testSearch() {
		try {
			CollegeDTO dto = new CollegeDTO();
			List list = new ArrayList();
			dto.setCity("ra");
			list = model.search(dto, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				dto = (CollegeDTO) it.next();
				System.out.println(dto.getId());
				System.out.println(dto.getName());
				System.out.println(dto.getAddress());
				System.out.println(dto.getState());
				System.out.println(dto.getCity());
				System.out.println(dto.getPhoneNo());
				System.out.println(dto.getCreatedBy());
				System.out.println(dto.getCreatedDatetime());
				System.out.println(dto.getModifiedBy());
				System.out.println(dto.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests get List.
	 */
	public static void testList() {

		try {
			CollegeDTO dto = new CollegeDTO();
			List list = new ArrayList();
			list = model.list();
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				dto = (CollegeDTO) it.next();
				System.out.println(dto.getId());
				System.out.println(dto.getName());
				System.out.println(dto.getAddress());
				System.out.println(dto.getState());
				System.out.println(dto.getCity());
				System.out.println(dto.getPhoneNo());
				System.out.println(dto.getCreatedBy());
				System.out.println(dto.getCreatedDatetime());
				System.out.println(dto.getModifiedBy());
				System.out.println(dto.getModifiedDatetime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
