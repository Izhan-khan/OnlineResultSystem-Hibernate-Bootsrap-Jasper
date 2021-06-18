package model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import dto.TimeTableDTO;
import exception.ApplicationException;
import exception.DatabaseException;
import exception.DuplicateRecordException;
import utility.HibernateDataSource;

/**
 * Hibernate Implementation of TimeTableModel
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
public class TimeTableModel_Hibernate_Implement implements TimeTableModel_Interface {

	private static Logger log = Logger.getLogger(TimeTableModel_Hibernate_Implement.class);

	/**
	 * Add a TimeTable
	 *
	 * @param dto
	 * @throws ApplicationException,DuplicateRecordException
	 *
	 */
	public long add(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException {

		log.info("Model add Started");
		long pk = 0;
		TimeTableDTO duplicateTimeTableName = findBySubjectId(dto.getSubjectId());

		if (duplicateTimeTableName != null && duplicateTimeTableName.getCourseId() == dto.getCourseId()) {
			throw new DuplicateRecordException("Subject already exists");
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
			throw new ApplicationException("Exception in TimeTable Add " + e.getMessage());
		} finally {
			session.close();
		}

		log.info("Model add End");
		return pk;
	}

	/**
	 * Delete a TimeTable
	 *
	 * @param dto
	 * @throws ApplicationException
	 */

	public void delete(TimeTableDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in timetable Delete" + e.getMessage());
		} finally {
			session.close();
		}
		log.info("Model delete End");
	}

	/**
	 * Find User by TimeTable Name
	 *
	 * @param collage : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */

	public TimeTableDTO findBySubjectId(Long subjectId) throws ApplicationException {
		log.info("Model findBySubjectId Started");
		Session session = null;
		TimeTableDTO dto = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			criteria.add(Restrictions.eq("subjectId", subjectId));
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (TimeTableDTO) list.get(0);
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

	public TimeTableDTO findByPK(long pk) throws ApplicationException {
		log.info("Model findByPK Started");
		Session session = null;
		TimeTableDTO dto = null;
		try {
			session = HibernateDataSource.getSession();
			dto = (TimeTableDTO) session.get(TimeTableDTO.class, pk);
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting TimeTable by pk");
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
	 * @throws ApplicationException
	 */

	public void update(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException {
		log.info("Model update Started");
		Session session = null;
		Transaction transaction = null;

		TimeTableDTO dtoExist = findBySubjectId(dto.getSubjectId());

		// Check if updated TimeTable already exist
		if (dtoExist != null && dtoExist.getId() != dto.getId() && dtoExist.getCourseId() == dto.getCourseId() ) {
			throw new DuplicateRecordException("TimeTable is already exist");
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
				throw new ApplicationException("Exception in TimeTable Update" + e.getMessage());
			}
		} finally {
			session.close();
		}
		log.info("Model update End");
	}

	/**
	 * Searches TimeTables with pagination
	 *
	 * @return list : List of TimeTables
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws ApplicationException
	 */

	public List search(TimeTableDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.info("Model search Started");
		Session session = null;
		List list = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);

			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getSubjectId()> 0) {
				criteria.add(Restrictions.eq("subjectId", dto.getSubjectId()));
			}
			if (dto.getCourseId()> 0) {
				criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
			}
			if (dto.getExamDate() != null ) {
				criteria.add(Restrictions.eq("examDate", dto.getExamDate()));
			}
			
			
			// if page size is greater than zero the apply pagination
			if (pageSize > 0) {
				criteria.setFirstResult(((pageNo - 1) * pageSize));
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception in timetable search");
		} finally {
			session.close();
		}

		log.info("Model search End");
		return list;
	}

	/**
	 * Search TimeTable
	 *
	 * @param dto : Search Parameters
	 * @throws ApplicationException
	 */

	public List search(TimeTableDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * Gets List of TimeTable
	 *
	 * @return list : List of TimeTable
	 * @throws ApplicationException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * get List of TimeTable with pagination
	 *
	 * @return list : List of TimeTable
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
			Criteria criteria = session.createCriteria(TimeTableDTO.class);

			// if page size is greater than zero then apply pagination
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in  TimeTable list");
		} finally {
			session.close();
		}

		log.info("Model list End");
		return list;
	}

}
