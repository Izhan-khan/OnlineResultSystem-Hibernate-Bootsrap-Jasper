package model;

import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import dto.StudentDTO;
import exception.ApplicationException;
import exception.DatabaseException;
import exception.DuplicateRecordException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 * Hibernate Implementation of StudentModel
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
public class StudentModel_Hibernate_Implement implements StudentModel_Interface {

	public static Logger log = Logger.getLogger(StudentModel_Hibernate_Implement.class);
	/**
	 * Add a Student
	 *
	 * @param dto
	 * @throws ApplicationException,DuplicateRecordException
	 *
	 */
	public long add(StudentDTO dto) throws ApplicationException, DuplicateRecordException {

		log.info("StudentModel_Hibernate_Implement add() started");

		if (findByEmailId(dto.getEmail()) != null) {
			throw new DuplicateRecordException("Email id already exists");
		}

		Session session = null;
		Transaction transaction = null;
		Long pk = 0L;

		try {

			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();

			transaction = session.beginTransaction();
			pk = (Long) session.save(dto);
			transaction.commit();

		} catch (HibernateException e) {
			log.error(e);

			if (transaction != null) {
				transaction.rollback();
			}

			throw new ApplicationException("Exception in add()");

		} finally {
			session.close();
		}

		log.info("StudentModel_Hibernate_Implement add() started");

		return pk;
	}
	
	/**
	 * Update a Student
	 *
	 * @param dto
	 * @throws ApplicationException,DuplicateRecordException
	 */

	public void update(StudentDTO dto) throws ApplicationException, DuplicateRecordException {

		log.info("StudentModel_Hibernate_Implement update() started");

		if (findByEmailId(dto.getEmail()) != null && findByEmailId(dto.getEmail()).getId() != dto.getId()) {
			throw new DuplicateRecordException("Email alreafy exists");
		}

		Session session = null;
		Transaction transaction = null;

		try {

			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();

			transaction = session.beginTransaction();
			session.update(dto);
			transaction.commit();

		} catch (HibernateException e) {
			log.error(e);
			if (transaction != null) {
				transaction.rollback();
			}

		} finally {
			session.close();
		}

		log.info("StudentModel_Hibernate_Implement update() ended");

	}
	
	/**
	 * Delete a Student
	 *
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(StudentDTO dto) throws ApplicationException {

		log.info("StudentModel_Hibernate_Implement delete() started");

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

			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in delete()");

		} finally {
			session.close();
		}
		log.info("StudentModel_Hibernate_Implement delete() ended");

	}
	/**
	 * Find User by Student Name
	 *
	 * @param Student : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public StudentDTO findByEmailId(String emailId) throws ApplicationException {

		log.info("StudentModel_Hibernate_Implement findByEmailId() started");

		Session session = null;

		StudentDTO studentDTO = null;

		try {

			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();

			Criteria criteria = session.createCriteria(StudentDTO.class);
			criteria.add(Restrictions.eq("email", emailId));

			List list = criteria.list();

			if (list.size() == 1) {
				studentDTO = (StudentDTO) list.get(0);
			} else {
				list = null;
			}
		} catch (HibernateException e) {
			log.error(e);
			throw new ApplicationException("Exception in delete()");

		} finally {
			session.close();
		}

		log.info("StudentModel_Hibernate_Implement findByEmailId() ended");
		return studentDTO;
	}
	/**
	 * Find Student by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */

	public StudentDTO findByPK(long pk) throws ApplicationException {

		log.info("StudentModel_Hibernate_Implement findByPK() started");

		Session session = null;

		StudentDTO studentDTO = null;

		try {

			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();

			Criteria criteria = session.createCriteria(StudentDTO.class);
			criteria.add(Restrictions.eq("id", pk));

			List list = criteria.list();

			if (list.size() == 1) {
				studentDTO = (StudentDTO) list.get(0);
			} else {
				list = null;
			}

		} catch (HibernateException e) {
			log.error(e);
			throw new ApplicationException("Exception in findByPK()");

		} finally {
		if(session !=null)	session.close();
		}
		log.info("StudentModel_Hibernate_Implement findByPK() ended");

		return studentDTO;
	}
	
	/**
	 * Searches Students with pagination
	 *
	 * @return list : List of Students
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws ApplicationException
	 */

	public List search(StudentDTO dto, int pageNo, int pageSize) throws ApplicationException {

		log.info("StudentModel_Hibernate_Implement search() started");

		List list = null;

		Session session = null;

		try {

			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

			session = sessionFactory.openSession();

			Criteria criteria = session.createCriteria(StudentDTO.class);

			if(dto != null) {
			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				criteria.add(Restrictions.like("name", dto.getName() + "%"));
			}
			if (dto.getDob() != null && dto.getDob().getDate() > 0) {
				criteria.add(Restrictions.eq("dob", dto.getDob()));
			}
			if (dto.getEmail() != null && dto.getEmail().length() > 0) {
				criteria.add(Restrictions.like("email", dto.getEmail() + "%"));
			}
			if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
				criteria.add(Restrictions.like("mobileNo", dto.getMobileNo() + "%"));
			}

			// if page size is greater than zero the apply pagination
			if (pageSize > 0) {
				criteria.setFirstResult(((pageNo - 1) * pageSize));
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();
			}
		} catch (HibernateException e) {

			log.error("Database Exception..", e);
			throw new ApplicationException("Exception in Student search");
		} finally {
			if (session != null) session.close();
		}

		log.info("StudentModel_Hibernate_Implement search() ended");
		return list;
	}

	/**
	 * Searches Students 
	 *
	 * @return list : List of Students
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws ApplicationException
	 */

	public List search(StudentDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * get List of Student 
	 *
	 * @return list : List of Student
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * get List of Student with pagination
	 *
	 * @return list : List of Student
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.info("Model list Started");
		Session session = null;
		List list = null;
		try {

			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

			session = sessionFactory.openSession();

			Criteria criteria = session.createCriteria(StudentDTO.class);

			// if page size is greater than zero then apply pagination
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in  Student list");
		} finally {
			if(session !=null)	session.close();
		}

		log.info("Model list End");
		return list;
	}

}
