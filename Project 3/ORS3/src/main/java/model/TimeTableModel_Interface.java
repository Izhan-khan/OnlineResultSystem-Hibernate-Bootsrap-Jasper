package model;

import java.util.List;

import dto.TimeTableDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;
import exception.RecordNotFoundException;

/**
 * Data Access Object of Time Table
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */

public interface TimeTableModel_Interface {
	/**
	 * Add a Time Table
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : throws when Time Table already exists
	 */
	public long add(TimeTableDTO dto) throws DuplicateRecordException,ApplicationException;
	
	/**
	 * Delete a Time Table
	 *
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(TimeTableDTO dto) throws RecordNotFoundException,ApplicationException;
	
	/**
	 * Find Time Table by Name
	 *
	 * @param name : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public TimeTableDTO findBySubjectId(Long subjectId)throws ApplicationException;
	/**
	 * Find Time Table by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public TimeTableDTO findByPK(long pk)throws ApplicationException;
	/**
	 * Update a Time Table
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : if updated user record is already exist
	 */
	public void update(TimeTableDTO dto)throws RecordNotFoundException,ApplicationException,DuplicateRecordException;
	
	/**
	 * Search Time Table with pagination
	 *
	 * @return list : List of Time Table
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List search(TimeTableDTO dto,int pageNo,int pageSize)throws ApplicationException;

	/**
	 * Search Time Table 
	 *
	 * @return list : List of Time Table
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List search(TimeTableDTO dto)throws ApplicationException;

	/**
	 * get List of Time Table with pagination
	 *
	 * @return list : List of Time Table
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List list(int pageNo,int pageSize)throws ApplicationException;

	/**
	 * get List of Time Table 
	 *
	 * @return list : List of Time Table
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List list()throws ApplicationException;
	
}
