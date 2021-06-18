package test;
 
import model.FacultyModel_Hibernate_Implement;
import model.FacultyModel_Interface;
import utility.DataUtility;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dto.FacultyDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;
 
/**
 * Faculty Model Test classes
 *  
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 *  
 */
public class FacultyModel_Test {
 
    /**
     * Model object to test
     */
 
    // public static FacultyModelInt model = new FacultyModelHibImpl();
 
    public static FacultyModel_Interface model = new FacultyModel_Hibernate_Implement();
 
    /**
     * Main method to call test methods.
     *  
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args){
        
    	 testAdd();
        // testDelete();
        // testUpdate();
        // testFindByPK();
        // testFindByLogin();
        // testSearch();
        // testList();
       
    	
    }
 
    /**
     * Tests add a Faculty
     *  
     * @throws ParseException
     */
    public static void testAdd() {
 
        try {
            FacultyDTO dto = new FacultyDTO();
            // dto.setId(8L);
            dto.setName("vipin");
            dto.setQualification("kumawat");
            dto.setEmail("ranjitchoudha12ry20@gmail.com");
            dto.setMobileNo("4444");
            dto.setGender("4444");
            dto.setDob(DataUtility.getDate("04/08/1997"));
            dto.setCollegeId(2L);
            dto.setCourseId(2L);
            dto.setSubjectId(2L);
             dto.setCreatedBy("Admin");
            dto.setModifiedBy("Admin");
            dto.setCreatedDatetime(Timestamp.from(Instant.now()));
            dto.setModifiedDatetime(Timestamp.from(Instant.now()));
            long pk = model.add(dto);
            System.out.println("Successfully add");
            System.out.println(dto.getDob());
            FacultyDTO addedDto = model.findByPK(pk);
            if (addedDto == null) {
                System.out.println("Test add fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateRecordException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Tests delete a Faculty
     */
    public static void testDelete() {
 
        try {
            FacultyDTO dto = new FacultyDTO();
            long pk = 21L;
            dto.setId(pk);
            model.delete(dto);
            FacultyDTO deletedDto = model.findByPK(pk);
            if (deletedDto != null) {
                System.out.println("Test Delete fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Tests update a Faculty
     */
    public static void testUpdate() {
 
        try {
            FacultyDTO dto = model.findByEmail("ranjitchoudha12ry20@gmail.com");
            dto.setMobileNo("9536565688");
 
            model.update(dto);
 
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateRecordException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Tests find a Faculty by PK.
     */
    public static void testFindByPK() {
        try {
            FacultyDTO dto = new FacultyDTO();
            long pk = 15L;
            dto = model.findByPK(pk);
            if (dto == null) {
                System.out.println("Test Find By PK fail");
            }
            System.out.println(dto.getId());
            System.out.println(dto.getName());
            System.out.println(dto.getEmail());
            System.out.println(dto.getQualification());
            System.out.println(dto.getDob());
            System.out.println(dto.getCollegeId());
            System.out.println(dto.getGender());
            System.out.println(dto.getSubjectId());
            System.out.println(dto.getCourseId());
            System.out.println(dto.getMobileNo());
            System.out.println(dto.getCreatedBy());
            System.out.println(dto.getModifiedBy());
            System.out.println(dto.getCreatedDatetime());
            System.out.println(dto.getModifiedDatetime());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
 
    }
 
    /**
     * Tests find a Faculty by Login.
     */
    public static void testFindByLogin() {
        FacultyDTO dto = new FacultyDTO();
        try {
            dto = model.findByEmail("ranjitchoudhary20@gmail.com");
            if (dto == null) {
                System.out.println("Test Find By PK fail");
            }
            System.out.println(dto.getId());
            System.out.println(dto.getName());
            System.out.println(dto.getEmail());
            System.out.println(dto.getQualification());
            System.out.println(dto.getDob());
            System.out.println(dto.getCollegeId());
            System.out.println(dto.getGender());
            System.out.println(dto.getSubjectId());
            System.out.println(dto.getCourseId());
            System.out.println(dto.getMobileNo());
            System.out.println(dto.getCreatedBy());
            System.out.println(dto.getModifiedBy());
            System.out.println(dto.getCreatedDatetime());
            System.out.println(dto.getModifiedDatetime());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Tests get List.
     */
    public static void testList() {
 
        try {
            FacultyDTO dto = new FacultyDTO();
            List list = new ArrayList();
            list = model.list(1, 10);
            if (list.size() < 0) {
                System.out.println("Test list fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                dto = (FacultyDTO) it.next();
                System.out.println(dto.getId());
                System.out.println(dto.getName());
                System.out.println(dto.getEmail());
                System.out.println(dto.getQualification());
                System.out.println(dto.getDob());
                System.out.println(dto.getCollegeId());
                System.out.println(dto.getGender());
                System.out.println(dto.getSubjectId());
                System.out.println(dto.getCourseId());
                System.out.println(dto.getMobileNo());
                System.out.println(dto.getCreatedBy());
                System.out.println(dto.getModifiedBy());
                System.out.println(dto.getCreatedDatetime());
                System.out.println(dto.getModifiedDatetime());
            }
 
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
 
 
    /**FacultyDTO dto = new FacultyDTO();
     * Tests get Search
     */
    public static void testSearch() {
 
        try {
            FacultyDTO dto = new FacultyDTO();
            List list = new ArrayList();
            // dto.setFirstName("ranjit");
            // dto.setLastName("ch");
            dto.setEmail("ranjitchoudhary20@gmail.com");
 
            list = model.search(dto, 0, 0);
            if (list.size() < 0) {
                System.out.println("Test Serach fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                dto = (FacultyDTO) it.next();
                System.out.println(dto.getId());
                System.out.println(dto.getName());
                System.out.println(dto.getEmail());
                System.out.println(dto.getQualification());
                System.out.println(dto.getDob());
                System.out.println(dto.getCollegeId());
                System.out.println(dto.getGender());
                System.out.println(dto.getSubjectId());
                System.out.println(dto.getCourseId());
                System.out.println(dto.getMobileNo());
                System.out.println(dto.getCreatedBy());
                System.out.println(dto.getModifiedBy());
                System.out.println(dto.getCreatedDatetime());
                System.out.println(dto.getModifiedDatetime());
            }
 
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
 
    }
 

 
}