package model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import dto.FacultyDTO;
import exception.ApplicationException;
import exception.DatabaseException;
import exception.DuplicateRecordException;
import utility.HibernateDataSource;

/**
 * Hibernate Implementation of FacultyModel
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
public class FacultyModel_Hibernate_Implement implements FacultyModel_Interface {

	private static Logger log = Logger.getLogger(FacultyModel_Hibernate_Implement.class);

	/**
	 * Add a Faculty
	 *
	 * @param dto
	 * @throws ApplicationException,DuplicateRecordException
	 *
	 */
	public long add(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {

		log.info("Model add Started");
		long pk = 0;
		FacultyDTO dtoExist = findByEmail(dto.getEmail());

		if (dtoExist != null) {
			throw new DuplicateRecordException("Faculty  already exists");
		}

		Session session = HibernateDataSource.getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in Faculty Add " + e.getMessage());
		} finally {
			session.close();
		}

		log.info("Model add End");
		return pk;
	}

	/**
	 * Delete a Faculty
	 *
	 * @param dto
	 * @throws ApplicationException
	 */

	public void delete(FacultyDTO dto) throws ApplicationException {
		log.info("Model delete Started");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateDataSource.getSession();
			transaction = session.beginTransaction();
			session.delete(dto);
			transaction.commit();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in faculty Delete" + e.getMessage());
		} finally {
			session.close();
		}
		log.info("Model delete End");
	}

	/**
	 * Find User by Faculty Name
	 *
	 * @param Faculty : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */

	public FacultyDTO findByEmail(String email) throws ApplicationException {
		log.info("Model findByName Started");
		Session session = null;
		FacultyDTO dto = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			criteria.add(Restrictions.eq("email", email));
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (FacultyDTO) list.get(0);
			}

		} catch (HibernateException e) {
			log.error("Database Exception.." + e.getMessage());
			throw new ApplicationException("Exception in getting User by Login " + e.getMessage());

		} finally {
			if (session != null)
				session.close();
		}

		log.info("Model findByName End");
		return dto;
	}

	/**
	 * Find Faculty by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */

	public FacultyDTO findByPK(long pk) throws ApplicationException {
		log.info("Model findByPK Started");
		Session session = null;
		FacultyDTO dto = null;
		try {
			session = HibernateDataSource.getSession();
			dto = (FacultyDTO) session.get(FacultyDTO.class, pk);
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Faculty by pk");
		} finally {
			session.close();
		}

		log.info("Model findByPK End");
		return dto;
	}

	/**
	 * Update a Faculty
	 *
	 * @param dto
	 * @throws ApplicationException,DuplicateRecordException
	 */

	public void update(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		log.info("Model update Started");
		Session session = null;
		Transaction transaction = null;

		FacultyDTO dtoExist = findByEmail(dto.getEmail());

		// Check if updated Faculty already exist
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Faculty is already exist");
		}

		try {

			session = HibernateDataSource.getSession();
			transaction = session.beginTransaction();
			session.update(dto);
			transaction.commit();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			if (transaction != null) {
				transaction.rollback();
				throw new ApplicationException("Exception in Faculty Update" + e.getMessage());
			}
		} finally {
			session.close();
		}
		log.info("Model update End");
	}

	/**
	 * Searches Faculty with pagination
	 *
	 * @return list : List of Faculty
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws ApplicationException
	 */

	public List search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.info("Model search Started");
		Session session = null;
		List list = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);

			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				criteria.add(Restrictions.like("name", dto.getName() + "%"));
			}
			if (dto.getCollegeId() > 0) {
				criteria.add(Restrictions.eq("collegeId", dto.getCollegeId()));
			}
			if (dto.getCourseId() > 0) {
				criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
			}
			if (dto.getSubjectId() > 0) {
				criteria.add(Restrictions.eq("subjectId", dto.getSubjectId()));
			}
			
			// if page size is greater than zero the apply pagination
			if (pageSize > 0) {
				criteria.setFirstResult(((pageNo - 1) * pageSize));
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception in faculty search");
		} finally {
			session.close();
		}

		log.info("Model search End");
		return list;
	}

	/**
	 * Search Faculty
	 *
	 * @param dto : Search Parameters
	 * @throws ApplicationException
	 */

	public List search(FacultyDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * Gets List of Faculty
	 *
	 * @return list : List of Faculty
	 * @throws ApplicationException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * get List of Faculty with pagination
	 *
	 * @return list : List of Faculty
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.info("Model list Started");
		Session session = null;
		List list = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);

			// if page size is greater than zero then apply pagination
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in  Faculty list");
		} finally {
			session.close();
		}

		log.info("Model list End");
		return list;
	}
}
