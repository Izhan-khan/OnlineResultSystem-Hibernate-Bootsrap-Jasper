package test;
 
import model.MarksheetModel_Hibernate_Implement;
import model.MarksheetModel_Interface;
import model.MarksheetModel_JDBC_Implement;
import model.ModelFactory;
import utility.DataValidator;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dto.MarksheetDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;
 
/**
 * Marksheet Model Test classes
 *  
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 *  
 */
public class MarksheetModel_Test {
 
    /**
     * Model object to test
     */
 
    // public static MarksheetModelInt model = new MarksheetModelHibImpl();
 
    public static MarksheetModel_Interface model = ModelFactory.getInstance().getMarksheetModel();
 
    /**
     * Main method to call test methods.
     *  
     * @param args
     */
    public static void main(String[] args) {
       // testAdd();
        // testDelete();
        // testUpdate();
        // testFindByRollNo();
        // testFindByPK();
//    	testList();
//         testSearch();
//    	 testMeritList();
 
    }
 
    /**
     * Tests add a Marksheet
     */
    public static void testAdd() {
 
        try {
        	long j=2;
        	for(long i=1;i<15;i++) {
            MarksheetDTO dto = new MarksheetDTO();
            //dto.setId(10L);
            dto.setRollNo(""+i);
            dto.setName("Student "+i);
            dto.setPhysics((int) Math.round(Math.random()*100));
            dto.setChemistry((int) Math.round(Math.random()*100));
            dto.setMaths((int) Math.round(Math.random()*100));
            dto.setStudentId(j);
            dto.setCreatedBy("Admin");
    		dto.setModifiedBy("Admin");
    		dto.setCreatedDatetime(Timestamp.from(Instant.now()));
    		dto.setModifiedDatetime(Timestamp.from(Instant.now()));
    		
            long pk = model.add(dto);
            System.out.println("Test add succ");
            MarksheetDTO addedDto = model.findByPK(pk);
            if (addedDto == null) {
                System.out.println("Test add fail");
            }
            j++;
        	}
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateRecordException e) {
            e.printStackTrace();
        }
 
    }
 
    /**
     * Tests delete a Marksheet
     */
    public static void testDelete() {
 
        try {
            MarksheetDTO dto = new MarksheetDTO();
            long pk = 8L;
            dto.setId(pk);
            model.delete(dto);
            MarksheetDTO deletedDto = model.findByPK(pk);
            if (deletedDto != null) {
                System.out.println("Test Delete fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Tests update a Marksheet
     */
    public static void testUpdate() {
 
        try {
            MarksheetDTO dto = model.findByPK(3L);
            dto.setName("new");
            dto.setChemistry(88);
            dto.setMaths(88);
            model.update(dto);
            System.out.println("Test Update ");
            MarksheetDTO updatedDTO = model.findByPK(3L);
            if (!"rk choudhary".equals(updatedDTO.getName())) {
                System.out.println("Test Update fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateRecordException e) {
            e.printStackTrace();
        }
 
    }
 
    /**
     * Tests find a marksheet by Roll No.
     */
 
    public static void testFindByRollNo() {
 
        try {
            MarksheetDTO dto = model.findByRollNo("3");
            if (dto == null) {
                System.out.println("Test Find By RollNo fail");
            }
            System.out.println(dto.getId());
            System.out.println(dto.getRollNo());
            System.out.println(dto.getName());
            System.out.println(dto.getPhysics());
            System.out.println(dto.getChemistry());
            System.out.println(dto.getMaths());
            System.out.println(dto.getCreatedBy());
            System.out.println(dto.getCreatedDatetime());
            System.out.println(dto.getModifiedBy());
            System.out.println(dto.getModifiedDatetime());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
 
    }
 
    /**
     * Tests find a marksheet by PK.
     */
    public static void testFindByPK() {
        try {
            MarksheetDTO dto = new MarksheetDTO();
            long pk = 3L;
            dto = model.findByPK(pk);
            if (dto == null) {
                System.out.println("Test Find By PK fail");
            }
            System.out.println(dto.getId());
            System.out.println(dto.getRollNo());
            System.out.println(dto.getName());
            System.out.println(dto.getPhysics());
            System.out.println(dto.getChemistry());
            System.out.println(dto.getMaths());
            System.out.println(dto.getCreatedBy());
            System.out.println(dto.getCreatedDatetime());
            System.out.println(dto.getModifiedBy());
            System.out.println(dto.getModifiedDatetime());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
 
    }
 
    /**
     * Tests list of Marksheets
     */
    public static void testList() {
        try {
            MarksheetDTO dto = new MarksheetDTO();
            List list = new ArrayList();
            list = model.list();
            if (list.size() < 0) {
                System.out.println("Test List fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                dto = (MarksheetDTO) it.next();
            	
                
                System.out.println(dto.getId());
                System.out.println(dto.getRollNo());
                System.out.println(dto.getName());
                System.out.println(dto.getPhysics());
                System.out.println(dto.getChemistry());
                System.out.println(dto.getMaths());
                System.out.println(dto.getCreatedBy());
                System.out.println(dto.getCreatedDatetime());
                System.out.println(dto.getModifiedBy());
                System.out.println(dto.getModifiedDatetime());
                System.out.println();
                System.out.println();
                
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
 
    }
 
    /**
     * Tests search a Marksheets
     */
 
    public static void testSearch() {
        try {
            MarksheetDTO dto = new MarksheetDTO();
            List list = new ArrayList();
            dto.setName("i");
            list = model.search(dto, 1, 10);
            if (list.size() < 0) {
                System.out.println("Test Search fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                dto = (MarksheetDTO) it.next();
            
                System.out.println(dto.getId());
                System.out.println(dto.getRollNo());
                System.out.println(dto.getName());
                System.out.println(dto.getPhysics());
                System.out.println(dto.getChemistry());
                System.out.println(dto.getMaths());
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
     * Tests get the meritlist of Marksheets
     */
    public static void testMeritList() {
        try {
            MarksheetDTO dto = new MarksheetDTO();
            List list = new ArrayList();
            list = model.getMeritList(1, 5);
            if (list.size() < 0) {
                System.out.println("Test List fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
              
            	dto = (MarksheetDTO) it.next();
          
         	   	System.out.print(dto.getId()+" ");
                System.out.print(dto.getRollNo()+" ");
                System.out.print(dto.getName()+" ");
                System.out.print(dto.getPhysics()+" ");
                System.out.print(dto.getChemistry()+" ");
                System.out.print(dto.getMaths()+" ");
                System.out.print(dto.getCreatedBy()+" ");
                System.out.print(dto.getCreatedDatetime()+" ");
                System.out.print(dto.getModifiedBy()+" ");
                System.out.println(dto.getModifiedDatetime()+" ");
         
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
 
    }
}
