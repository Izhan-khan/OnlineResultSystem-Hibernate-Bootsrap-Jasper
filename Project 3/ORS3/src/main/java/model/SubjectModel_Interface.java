package model;

import java.util.List;

import dto.SubjectDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;

/**
 * Data Access Object of Student
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */

public interface SubjectModel_Interface {
    
	/**
     * Add a user
     *
     * @param dto
     * @throws ApplicationException
     * @throws DuplicateRecordException
     *             : throws when user already exists
     */
    public long add(SubjectDTO dto) throws ApplicationException,
            DuplicateRecordException;

    /**
     * Update a Subject
     *
     * @param dto
     * @throws ApplicationException
     * @throws DuplicateRecordException
     *             : if updated user record is already exist
     */
    public void update(SubjectDTO dto) throws ApplicationException,
            DuplicateRecordException;

    /**
     * Delete a user
     *
     * @param dto
     * @throws ApplicationException
     */
    public void delete(SubjectDTO dto) throws ApplicationException;

    /**
     * Find user by login
     *sDTO findByName(String login) throws ApplicationException;

    /**
     * Find user by PK
     *
     * @param pk
     *            : get parameter
     * @return dto
     * @throws ApplicationException
     */
    public SubjectDTO findByPK(long pk) throws ApplicationException;

    /**
     * Search Subjects
     *
     * @return list : List of Subjects
     * @param dto
     *            : Search Parameters
     * @throws ApplicationException
     */
    public List search(SubjectDTO dto) throws ApplicationException;

    /**
     * Search Subjects with pagination
     *
     * @return list : List of Subjects
     * @param dto
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws ApplicationException
     */
    public List search(SubjectDTO dto, int pageNo, int pageSize)
            throws ApplicationException;

    /**
     * Get List of Subjects
     *
     * @return list : List of Subjects
     * @throws DatabaseException
     */
    public List list() throws ApplicationException;

    /**
     * Get List of Subjects with pagination
     *
     * @return list : List of Subjects
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws ApplicationException
     */
    public List list(int pageNo, int pageSize) throws ApplicationException;

}
