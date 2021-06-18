package model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import dto.CourseDTO;
import exception.ApplicationException;
import exception.DatabaseException;
import exception.DuplicateRecordException;
import utility.HibernateDataSource;

/**
 * Hibernate Implementation of CourseModel
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
public class CourseModel_Hibernate_Implement implements CourseModel_Interface {

    private static Logger log = Logger.getLogger(CourseModel_Hibernate_Implement.class);

    /**
     * Add a Course
     *
     * @param dto
     * @throws ApplicationException
     *
     */
    public long add(CourseDTO dto) throws ApplicationException,
            DuplicateRecordException {

        log.info("Model add Started");
        long pk = 0;
      CourseDTO duplicateCourseName = findByName(dto.getName());

        if (duplicateCourseName != null) {
            throw new DuplicateRecordException("Course Name already exists");
        }

        Session session = HibernateDataSource.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            pk =(Long) session.save(dto);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            log.error("Database Exception..", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new ApplicationException("Exception in Course Add "
                    + e.getMessage());
        } finally {
            session.close();
        }

        log.info("Model add End");
        return pk;
    }

    /**
     * Delete a Course
     *
     * @param dto
     * @throws ApplicationException
     */
  
    public void delete(CourseDTO dto) throws ApplicationException {
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
            throw new ApplicationException("Exception in college Delete"
                    + e.getMessage());
        } finally {
            if(session != null) session.close();
        }
        log.info("Model delete End");
    }

    /**
     * Find User by Course Name
     *
     * @param collage
     *            : get parameter
     * @return dto
     * @throws ApplicationException
     */
    
    public CourseDTO findByName(String name) throws ApplicationException {
        log.info("Model findByName Started");
        Session session = null;
        CourseDTO dto = null;
        try {
             session = HibernateDataSource.getSession();
            Criteria criteria = session.createCriteria(CourseDTO.class);
            criteria.add(Restrictions.eq("name", name));
            List list = criteria.list();
            if (list.size() > 0) {
                dto = (CourseDTO) list.get(0);
            }

        } catch (HibernateException e) {
            log.error("Database Exception.." + e.getMessage());
            throw new ApplicationException(
                    "Exception in getting Course by Name " + e.getMessage());

        } finally {
        	if(session != null) session.close();
        }

        log.info("Model findByName End");
        return dto;
    }

    /**
     * Find Collage by PK
     *
     * @param pk
     *            : get parameter
     * @return dto
     * @throws ApplicationException
     */
    
    public CourseDTO findByPK(long pk) throws ApplicationException {
        log.info("Model findByPK Started");
        Session session = null;
        CourseDTO dto = null;
        try {
            session = HibernateDataSource.getSession();
            dto = (CourseDTO) session.get(CourseDTO.class, pk);
        } catch (HibernateException e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting Course by pk");
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
    
    public void update(CourseDTO dto) throws ApplicationException,
            DuplicateRecordException {
        log.info("Model update Started");
        Session session = null;
        Transaction transaction = null;

        CourseDTO dtoExist = findByName(dto.getName());

        // Check if updated Course already exist
        if (dtoExist != null && dtoExist.getId() != dto.getId()) {
            throw new DuplicateRecordException("Course is already exist");
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
                throw new ApplicationException("Exception in Course Update"
                        + e.getMessage());
            }
        } finally {
            session.close();
        }
        log.info("Model update End");
    }

    /**
     * Searches Courses with pagination
     *
     * @return list : List of Courses
     * @param dto
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     *
     * @throws ApplicationException
     */
    
    public List search(CourseDTO dto, int pageNo, int pageSize)
            throws ApplicationException {
        log.info("Model search Started");
        Session session = null;
        List list = null;
        try {
            session = HibernateDataSource.getSession();
            Criteria criteria = session.createCriteria(CourseDTO.class);

            if (dto.getId() > 0) {
                criteria.add(Restrictions.eq("id", dto.getId()));
            }
            if (dto.getName() != null && dto.getName().length() > 0) {
                criteria.add(Restrictions.like("name", dto.getName() + "%"));
            }
            if (dto.getDescription() != null && dto.getDescription().length() > 0) {
                criteria.add(Restrictions.like("address", dto.getDescription()
                        + "%"));
            }
            if ( dto.getDuration() > 0) {
                criteria.add(Restrictions.eq("duration", dto.getDuration()));
            }

            // if page size is greater than zero the apply pagination
            if (pageSize > 0) {
                criteria.setFirstResult(((pageNo - 1) * pageSize));
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (HibernateException e) {
            log.error("Database Exception..", e);
            throw new ApplicationException("Exception in college search");
        } finally {
            session.close();
        }

        log.info("Model search End");
        return list;
    }

    /**
     * Search Course
     *
     * @param dto
     *            : Search Parameters
     * @throws ApplicationException
     */
   
    public List search(CourseDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    /**
     * Gets List of Course
     *
     * @return list : List of Course
     * @throws DatabaseException
     */
    
    public List list() throws ApplicationException {
        return list(0, 0);
    }

    /**
     * get List of Course with pagination
     *
     * @return list : List of Course
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws ApplicationException
     */
    public List list(int pageNo, int pageSize) throws ApplicationException {
        log.info("Model list Started");
        Session session = null;
        List list = null;
        try {
            session = HibernateDataSource.getSession();
            Criteria criteria = session.createCriteria(CourseDTO.class);

            // if page size is greater than zero then apply pagination
            if (pageSize > 0) {
                pageNo = ((pageNo - 1) * pageSize) + 1;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (HibernateException e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in  Course list");
        } finally {
            session.close();
        }

        log.info("Model list End");
        return list;
    }
}
