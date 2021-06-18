package model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import dto.SubjectDTO;
import exception.ApplicationException;
import exception.DatabaseException;
import exception.DuplicateRecordException;
import utility.HibernateDataSource;

/**
 * Hibernate Implementation of SubjectModel
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
public class SubjectModel_Hibernate_Implement implements SubjectModel_Interface {

	private static Logger log = Logger.getLogger(SubjectModel_Hibernate_Implement.class);

	/**
	 * Add a Subject
	 *
	 * @param dto
	 * @throws ApplicationException,DuplicateRecordException
	 *
	 */
	public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {

		log.info("Model add Started");
		long pk = 0;
		SubjectDTO duplicateSubjectName = findByName(dto.getName());

		if (duplicateSubjectName != null) {
			throw new DuplicateRecordException("Subject Name already exists");
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
			throw new ApplicationException("Exception in Subject Add " + e.getMessage());
		} finally {
			session.close();
		}

		log.info("Model add End");
		return pk;
	}

	/**
	 * Delete a Subject
	 *
	 * @param dto
	 * @throws ApplicationException
	 */

	public void delete(SubjectDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in subject Delete" + e.getMessage());
		} finally {
			session.close();
		}
		log.info("Model delete End");
	}

	/**
	 * Find User by Subject Name
	 *
	 * @param collage : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */

	public SubjectDTO findByName(String name) throws ApplicationException {
		log.info("Model findByName Started");
		Session session = null;
		SubjectDTO dto = null;
		try {
			System.out.println("session " + session);
			session = HibernateDataSource.getSession();
			System.out.println("session " + session);
			Criteria criteria = session.createCriteria(SubjectDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (SubjectDTO) list.get(0);
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
	 * Find Collage by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */

	public SubjectDTO findByPK(long pk) throws ApplicationException {
		log.info("Model findByPK Started");
		Session session = null;
		SubjectDTO dto = null;
		try {
			session = HibernateDataSource.getSession();
			dto = (SubjectDTO) session.get(SubjectDTO.class, pk);
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Subject by pk");
		} finally {
			session.close();
		}

		log.info("Model findByPK End");
		return dto;
	}

	/**
	 * Update a Collage
	 *
	 * @param dto
	 * @throws ApplicationException,DuplicateRecordException
	 */

	public void update(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		log.info("Model update Started");
		Session session = null;
		Transaction transaction = null;

		SubjectDTO dtoExist = findByName(dto.getName());

		// Check if updated Subject already exist
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Subject is already exist");
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
				throw new ApplicationException("Exception in Subject Update" + e.getMessage());
			}
		} finally {
			session.close();
		}
		log.info("Model update End");
	}

	/**
	 * Searches Subjects with pagination
	 *
	 * @return list : List of Subjects
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws ApplicationException
	 */

	public List search(SubjectDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.info("Model search Started");
		Session session = null;
		List list = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria criteria = session.createCriteria(SubjectDTO.class);

			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				criteria.add(Restrictions.like("name", dto.getName() + "%"));
			}
			if (dto.getCourseId() > 0) {
				criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
			}

			// if page size is greater than zero the apply pagination
			if (pageSize > 0) {
				criteria.setFirstResult(((pageNo - 1) * pageSize));
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception in subject search");
		} finally {
			session.close();
		}

		log.info("Model search End");
		return list;
	}

	/**
	 * Search Subject
	 *
	 * @param dto : Search Parameters
	 * @throws ApplicationException
	 */

	public List search(SubjectDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * Gets List of Subject
	 *
	 * @return list : List of Subject
	 * @throws ApplicationException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * get List of Subject with pagination
	 *
	 * @return list : List of Subject
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
			Criteria criteria = session.createCriteria(SubjectDTO.class);

			// if page size is greater than zero then apply pagination
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in  Subject list");
		} finally {
			session.close();
		}

		log.info("Model list End");
		return list;
	}
}
