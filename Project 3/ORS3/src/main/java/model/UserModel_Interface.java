package model;

import java.util.List;

import dto.UserDTO;
import exception.ApplicationException;
import exception.DuplicateRecordException;
import exception.RecordNotFoundException;

/**
 * Data Access Object of Users
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */

public interface UserModel_Interface {

    /**
     * Add a user
     *
     * @param dto
     * @throws ApplicationException
     * @throws DuplicateRecordException
     *             : throws when user already exists
     */
    public long add(UserDTO dto) throws ApplicationException,
            DuplicateRecordException;

    /**
     * Update a User
     *
     * @param dto
     * @throws ApplicationException
     * @throws DuplicateRecordException
     *             : if updated user record is already exist
     */
    public void update(UserDTO dto) throws ApplicationException,
            DuplicateRecordException;

    /**
     * Delete a user
     *
     * @param dto
     * @throws ApplicationException
     */
    public void delete(UserDTO dto) throws ApplicationException;

    /**
     * Find user by login
     *
     * @param login
     *            : get parameter
     * @return dto
     * @throws ApplicationException
     * @throws DuplicateRecordException
     */
    public UserDTO findByLogin(String login) throws ApplicationException;

    /**
     * Find user by PK
     *
     * @param pk
     *            : get parameter
     * @return dto
     * @throws ApplicationException
     */
    public UserDTO findByPK(long pk) throws ApplicationException;

    /**
     * Search Users
     *
     * @return list : List of Users
     * @param dto
     *            : Search Parameters
     * @throws ApplicationException
     */
    public List search(UserDTO dto) throws ApplicationException;

    /**
     * Search Users with pagination
     *
     * @return list : List of Users
     * @param dto
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws ApplicationException
     */
    public List search(UserDTO dto, int pageNo, int pageSize)
            throws ApplicationException;

    /**
     * Get List of Users
     *
     * @return list : List of Users
     * @throws DatabaseException
     */
    public List list() throws ApplicationException;

    /**
     * Get List of Users with pagination
     *
     * @return list : List of Users
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws ApplicationException
     */
    public List list(int pageNo, int pageSize) throws ApplicationException;

    /**
     * Change Password By pk
     *
     * @param pk
     *            ,oldPassword,newPassword : get parameter
     * @return dto
     * @throws ApplicationException
     * @throws RecordNotFoundException
     */
    public boolean changePassword(Long id, String oldPassword,
            String newPassword) throws RecordNotFoundException,
            ApplicationException;

    /**
     * User Authentication
     *
     * @return dto : Contains User's information
     * @param login
     *            : User Login
     * @param password
     *            : User Password
     * @throws ApplicationException
     * @throws RecordNotFoundException 
     */
    public UserDTO authenticate(String login, String password)
            throws ApplicationException, RecordNotFoundException;


    /**
     * Register a User
     *
     * @param dto
     * @return
     * @throws ApplicationException
     * @throws DuplicateRecordException
     */
    public long registerUser(UserDTO dto) throws ApplicationException,
            DuplicateRecordException;

    /**
     * reset password
     */
    public boolean resetPassword(UserDTO dto) throws ApplicationException;

    /**
     * forget password
     */
    public boolean forgetPassword(String login) throws ApplicationException,
            RecordNotFoundException;

}