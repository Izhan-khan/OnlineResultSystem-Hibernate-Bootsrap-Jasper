package test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;

import dto.RoleDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;
import model.RoleModel_Hibernate_Implement;
/**
 * Role Model Test classes
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 * 
 */
public class RoleModel_Test {

	static RoleDTO dto = new RoleDTO(); 
	
	static RoleModel_Hibernate_Implement model = new RoleModel_Hibernate_Implement();
	
	public static void testAdd() {
		
		dto.setName("Manger");
		dto.setDescription("Manages employees");
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(Timestamp.from(Instant.now()));
		dto.setModifiedDatetime(Timestamp.from(Instant.now()));
		
		try {
			model.add(dto);
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
	
	public static void testUpdate() {
		
		dto.setName("Manager");
		dto.setDescription("Manages department");
		dto.setId(2L);
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(Timestamp.from(Instant.now()));
		dto.setModifiedDatetime(Timestamp.from(Instant.now()));
		
		
		try {
			model.update(dto);
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}
	
	public static RoleDTO testFindByName() {
		
		try {
			
			dto = model.findByName("Manger");
			
			System.out.println(dto.getName());
			System.out.println(dto.getDescription());
			System.out.println(dto.getId());
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	
	public static RoleDTO testFindByPK() {
		
		try {
			
			dto = model.findByPK(2L);
			
			System.out.println(dto.getName());
			System.out.println(dto.getDescription());
			System.out.println(dto.getId());
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	public static List testList() {
		List list = null;
		try {
			list = model.list();

			Iterator it = list.iterator();
			
			while(it.hasNext()) {
				
			RoleDTO roleDTO = (RoleDTO) it.next();
				
			System.out.println(roleDTO.getId());
			System.out.println(roleDTO.getName());
			System.out.println(roleDTO.getDescription());
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	} 
	
	public static List testSearch() {
		List list = null;
		try {
			RoleDTO roleDTO = new RoleDTO();
		
			list = model.search(null, 1, 10);
			
			Iterator it = list.iterator();
			
			while(it.hasNext()) {
				
			roleDTO = (RoleDTO) it.next();
				
			System.out.println(roleDTO.getId());
			System.out.println(roleDTO.getName());
			System.out.println(roleDTO.getDescription());
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	} 
		
	public static void main(String[] args) {
		
		testSearch();
		
	}
	
}
