package model;

import java.util.List;

import dto.CourseDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException; 
/**
 * Data Access Object of Course
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
public interface CourseModel_Interface {
	
	/**
	 * Add a Course
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : throws when Course already exists
	 */
	
	public long add(CourseDTO dto)throws ApplicationException,DuplicateRecordException ;
	
	/**
	 * Delete a Course
	 *
	 * @param dto
	 * @throws ApplicationException
	 */
	
	public void delete(CourseDTO dto)throws ApplicationException;

	/**
	 * Update a Course
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : if updated user record is already exist
	 */
	public void update(CourseDTO dto)throws ApplicationException,DuplicateRecordException;
	/**
	 * Find Course by Name
	 *
	 * @param name : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public CourseDTO findByName(String coursename)throws ApplicationException;

	/**
	 * Find Course by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public CourseDTO findByPK(long pk)throws ApplicationException;
	/**
	 * Search Course with pagination
	 *
	 * @return list : List of Course
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List search(CourseDTO dto,int pageNo,int pageSize)throws ApplicationException;
	/**
	 * Search Course
	 *
	 * @return list : List of Course
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List search(CourseDTO dto)throws ApplicationException;
	/**
	 * get List of Course with pagination
	 *
	 * @return list : List of Course
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List list(int pageNo,int pageSize)throws ApplicationException;
	/**
	 * get List of Course 
	 *
	 * @return list : List of Course
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List list()throws ApplicationException;
	
}
