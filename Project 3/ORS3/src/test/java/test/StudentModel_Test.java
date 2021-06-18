package test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;

import dto.StudentDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;
import model.StudentModel_Hibernate_Implement;
import utility.DataUtility;
/**
 * Student Model Test classes
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 * 
 */
public class StudentModel_Test {

	static StudentDTO dto = new StudentDTO();
	
	static StudentModel_Hibernate_Implement model = new StudentModel_Hibernate_Implement();
	
	public static void testAdd() {
		
		try {
			for(long i=16;i<26;i++) {
		dto.setName("Student "+i);
		dto.setCollegeId(Math.round(Math.random()*10));
		dto.setDob(DataUtility.getDate("04/08/1997"));
		dto.setEmail("Email "+i);
		dto.setMobileNo(Long.toString(Math.round(Math.random()*Math.pow(10, 10))));
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(Timestamp.from(Instant.now()));
		dto.setModifiedDatetime(Timestamp.from(Instant.now()));
		
		model.add(dto);
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	
	public static void testDelete() {
		
		dto.setId(3L);
		
		try {
			model.delete(dto);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}


	public static void testFindByEmailId() {
        try {
            StudentDTO dto = new StudentDTO();
            dto = model.findByEmailId("izhankhan002@gmail.com");
            if (dto == null) {
                System.out.println("Test Find By EmailId fail");
            }
            System.out.println(dto.getId());
            System.out.println(dto.getName());
            System.out.println(dto.getDob());
            System.out.println(dto.getMobileNo());
            System.out.println(dto.getEmail());
            System.out.println(dto.getCollegeId());
            System.out.println(dto.getCreatedBy());
            System.out.println(dto.getCreatedDatetime());
            System.out.println(dto.getModifiedBy());
            System.out.println(dto.getModifiedDatetime());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

	public static void testFindByPK() {
        try {
            StudentDTO dto = new StudentDTO();
            dto = model.findByPK(2);
            if (dto == null) {
                System.out.println("Test Find By EmailId fail");
            }
            System.out.println(dto.getId());
            System.out.println(dto.getName());
            System.out.println(dto.getDob());
            System.out.println(dto.getMobileNo());
            System.out.println(dto.getEmail());
            System.out.println(dto.getCollegeId());
            System.out.println(dto.getCreatedBy());
            System.out.println(dto.getCreatedDatetime());
            System.out.println(dto.getModifiedBy());
            System.out.println(dto.getModifiedDatetime());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
	
	
	public static void testUpdate() {
		
		try {
			dto.setName("Izhan");
			dto.setCollegeId(1L);
			dto.setDob(DataUtility.getDate("04/08/1997"));
			dto.setEmail("izhankhan002@gmail.com");
			dto.setMobileNo("9981250820");
			dto.setCreatedBy("Admin");
			dto.setModifiedBy("Admin");
			dto.setCreatedDatetime(Timestamp.from(Instant.now()));
			dto.setModifiedDatetime(Timestamp.from(Instant.now()));

		dto.setId(2L);
		
		model.update(dto);
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	
	 public static void testSearch() {
		 
	        try {
	            StudentDTO dto = new StudentDTO();
	            dto.setName("Izhan");
	            List list = model.search(dto, 0, 0);
	            if (list.size() < 0) {
	                System.out.println("Test Serach fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                dto = (StudentDTO) it.next();
	                System.out.println(dto.getId());
	                System.out.println(dto.getName());
	                System.out.println(dto.getDob());
	                System.out.println(dto.getMobileNo());
	                System.out.println(dto.getEmail());
	                System.out.println(dto.getCollegeId());
	                System.out.println(dto.getCreatedBy());
	                System.out.println(dto.getCreatedDatetime());
	                System.out.println(dto.getModifiedBy());
	                System.out.println(dto.getModifiedDatetime());
	            }
	 
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }
	 
	    }
	
	
	
	public static void testList() {
		 
        try {
            StudentDTO dto = new StudentDTO();
            List list = model.list(1, 10);
            if (list.size() < 0) {
                System.out.println("Test list fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                dto = (StudentDTO) it.next();
                
                System.out.println(dto.getId());
                System.out.println(dto.getName());
                System.out.println(dto.getDob());
                System.out.println(dto.getMobileNo());
                System.out.println(dto.getEmail());
                System.out.println(dto.getCollegeId());
                System.out.println(dto.getCreatedBy());
                System.out.println(dto.getCreatedDatetime());
                System.out.println(dto.getModifiedBy());
                System.out.println(dto.getModifiedDatetime());
            }
 
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
	
	
	public static void main(String[] args) {
	 
		testAdd();
		
//		System.out.println();
		
	}
	
}
