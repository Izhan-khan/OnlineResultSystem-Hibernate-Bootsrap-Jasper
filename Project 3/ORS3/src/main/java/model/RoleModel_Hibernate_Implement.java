package model;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import dto.RoleDTO;
import dto.SubjectDTO;
import exception.ApplicationException;
import exception.DatabaseException;
import exception.DuplicateRecordException;
import utility.HibernateDataSource;
/**
 * Hibernate Implementation of RoleModel
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
public class RoleModel_Hibernate_Implement implements RoleModel_Interface {

	public static Logger log = Logger.getLogger(RoleModel_Hibernate_Implement.class);
	/**
	 * Add a Role
	 *
	 * @param dto
	 * @throws ApplicationException,DuplicateRecordException
	 *
	 */
	public long add(RoleDTO dto) throws ApplicationException, DuplicateRecordException {
		
		log.info("RoleModel_Hibernate_Implement add() started");
		
		if(findByName(dto.getName()) != null){
			throw new DuplicateRecordException("Role already exists");
		}
		
		Session session = null;
		Transaction transaction = null;
		Long pk = 0L;
		
		try {

			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			pk = (Long)session.save(dto);
			transaction.commit();
			
		} catch (HibernateException e) {
			log.error(e);
			if(transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in adding Role");
		}finally {
			session.close();
		}
		log.info("RoleModel_Hibernate_Implement add() ended");	

		return pk;
	}
	
	/**
	 * Update a Role
	 *
	 * @param dto
	 * @throws ApplicationException,DuplicateRecordException
	 */
	public void update(RoleDTO dto) throws ApplicationException, DuplicateRecordException {
		
		log.info("RoleModel_Hibernate_Implement update() started");
		
		if(findByName(dto.getName()) != null && findByName(dto.getName()).getId() != dto.getId()) {
			throw new DuplicateRecordException("Role already exists");
		}
	
		Session session = null;
		Transaction transaction = null;
		
		try {
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(dto);
			transaction.commit();
			
		}catch (HibernateException e) {
			log.error(e);
			if(transaction !=null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in updating Role");
		}finally {
		session.close();	
		}
		log.info("RoleModel_Hibernate_Implement update() ended");
	}
	
	/**
	 * Delete a Role
	 *
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(RoleDTO dto) throws ApplicationException {

		log.info("RoleModel_Hibernate_Implement delete() started");
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(dto);
			transaction.commit();
			
		} catch (HibernateException e) {
			log.error(e);
			if(transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in deleting Role");
		}finally {
			session.close();
		}
		log.info("RoleModel_Hibernate_Implement delete() ended");
	}
	
	
	/**
	 * Find User by Role Name
	 *
	 * @param Role : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public RoleDTO findByName(String name) throws ApplicationException {

		log.info("RoleModel_Hibernate_Implement findByName() started");
		
		Session session = null;
		RoleDTO roleDTO =  null;
		
		try {
			
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();
			
			Criteria criteria = session.createCriteria(RoleDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			
			if(list.size() == 1) {
				roleDTO = (RoleDTO) list.get(0);
			}else {
				roleDTO = null;
			}
		} catch (HibernateException e) {
			log.error(e);
			throw new ApplicationException("Exception in findByName()");
		}finally {
			session.close();
		}
		
		log.info("RoleModel_Hibernate_Implement findByName() ended");
	
		return roleDTO;
	}
	
	/**
	 * Find Role by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public RoleDTO findByPK(long pk) throws ApplicationException {

		log.info("RoleModel_Hibernate_Implement findByPk() started");
		
		Session session = null;
		RoleDTO roleDTO = null;
		
		try {
		
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();
			
			Criteria criteria = session.createCriteria(RoleDTO.class);
			criteria.add(Restrictions.eq("id", pk));
			List list = criteria.list();
					
			if(list.size() == 1) {
				roleDTO = (RoleDTO) list.get(0);
			}else {
				roleDTO = null;
			}
			
		} catch (HibernateException e) {
			log.error(e);
			throw new ApplicationException("Exception in findByPk");
		}finally {
			session.close();
		}
		
		log.info("RoleModel_Hibernate_Implement findByPk() ended");
				
		return roleDTO;
	}
	
	/**
	 * Searches Roles with pagination
	 *
	 * @return list : List of Roles
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws ApplicationException
	 */

	public List search(RoleDTO dto, int pageNo, int pageSize) throws ApplicationException {

		log.info("RoleModel_Hibernate_Implement search() started");
		
		List list = null;
		Session session  = null;
		
		try {
			session = HibernateDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);

			if(dto != null) {
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
			}
            // if page size is greater than zero the apply pagination
            if (pageSize > 0) {
                criteria.setFirstResult(((pageNo - 1) * pageSize));
                criteria.setMaxResults(pageSize);
            }
			
			
			list = criteria.list();
			
		}catch (HibernateException e) {
			log.error(e);
			throw new ApplicationException("Exception in search()");
		} finally {
			if(session !=null )session.close();
		}

		log.info("RoleModel_Hibernate_Implement search() ended");

		return list;
	} 
	/**
	 * Searches Role 
	 *
	 * @return list : List of Roles
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws ApplicationException
	 */

	public List search(RoleDTO dto) throws ApplicationException {
		return search(dto,0,0);
	}
	/**
	 * get List of Role 
	 *
	 * @return list : List of Role
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List list() throws ApplicationException {
		return list(0,0);
	}
	/**
	 * get List of Role with pagination
	 *
	 * @return list : List of Role
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		
		log.info("RoleModel_Hibernate_Implement list() started");
				
		List list =null;
		Session session = null;
		
		try {
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();
			
			Criteria criteria = session.createCriteria(RoleDTO.class);

			// if page size is greater than zero the apply pagination
            if (pageSize > 0) {
                criteria.setFirstResult(((pageNo - 1) * pageSize));
                criteria.setMaxResults(pageSize);
            }

			
			list = criteria.list();

		} catch (HibernateException e) {
			log.error(e);
			throw new ApplicationException("Exception in list()");
		}finally {
			session.close();
		}
	
		log.info("RoleModel_Hibernate_Implement list() ended");
		
		return list;
	}

}
