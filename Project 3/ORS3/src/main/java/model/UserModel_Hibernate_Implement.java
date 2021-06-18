package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import dto.UserDTO;
import exception.ApplicationException;
import exception.DatabaseException;
import exception.DuplicateRecordException;
import exception.RecordNotFoundException;
import utility.EmailBuilder;
import utility.EmailMessage;
import utility.EmailUtility;
import utility.HibernateDataSource;

/**
 * Hibernate Implementation of UserModel
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */


public class UserModel_Hibernate_Implement implements UserModel_Interface {

	
	private static Logger log = Logger.getLogger(UserModel_Hibernate_Implement.class);
	
	/**
	 * Add a User
	 *
	 * @param dto
	 * @throws ApplicationException,DuplicateRecordException
	 *
	 */
	public long add(UserDTO dto) throws ApplicationException,DuplicateRecordException {
	
	    log.info("Model add Started");
	    long pk = 0;
	
	    UserDTO dtoExist = findByLogin(dto.getLogin());
	
	    if (dtoExist != null) {
	        throw new DuplicateRecordException("LoginId is already exist");
	    }
	
	    Session session = HibernateDataSource.getSession();
	    Transaction transaction = null;
	    try {
	        transaction = session.beginTransaction();
	        session.save(dto);
	        pk = dto.getId();
	        transaction.commit();
	    } catch (HibernateException e) {
	        log.error("Database Exception..", e);
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        throw new ApplicationException("Exception in User Add "
	                + e.getMessage());
	    } finally {
	        session.close();
	    }
	    log.info("Model add End");
	    return dto.getId();
	}
	
	/**
	 * Delete a User
	 *
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(UserDTO dto) throws ApplicationException {
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
	        throw new ApplicationException("Exception in User Delete"
	                + e.getMessage());
	    } finally {
	        session.close();
	    }
	    log.info("Model delete End");
	}
	
	/**
	 * Find User by Login
	 *
	 * @param login
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public UserDTO findByLogin(String login) throws ApplicationException {
	    log.info("Model findByLoginId Started");
	    Session session = null;
	    UserDTO dto = null;
	    try {
	        session = HibernateDataSource.getSession();
	        Criteria criteria = session.createCriteria(UserDTO.class);
	        criteria.add(Restrictions.eq("login", login));
	        List list = criteria.list();

	        if (list.size() == 1) {
	            dto = (UserDTO) list.get(0);
	        }
	
	    } catch (HibernateException e) {
	        log.error("Database Exception..", e);
	        throw new ApplicationException(
	                "Exception in getting User by Login " + e.getMessage());
	
	    } finally {
	    	if(session !=null)session.close();
	    }
	
	    log.info("Model findByLoginId End");
	    return dto;
	}
	
	/**
	 * Find User by PK
	 *
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public UserDTO findByPK(long pk) throws ApplicationException {
	    log.info("Model findByPK Started");
	    Session session = null;
	    UserDTO dto = null;
	    try {
	        session = HibernateDataSource.getSession();
	        dto = (UserDTO) session.get(UserDTO.class, pk);
	    } catch (HibernateException e) {
	        log.error("Database Exception..", e);
	        throw new ApplicationException(
	                "Exception : Exception in getting User by pk");
	    } finally {
	        session.close();
	    }
	
	    log.info("Model findByPK End");
	    return dto;
	}
	
	/**
	 * Update a User
	 *
	 * @param dto
	 * @throws ApplicationException
	 */
	public void update(UserDTO dto) throws ApplicationException, DuplicateRecordException {
	    log.info("Model update Started");
	    Session session = null;
	    Transaction transaction = null;
	
	    UserDTO dtoExist = findByLogin(dto.getLogin());
	
	    // Check if updated LoginId already exist
	    if (dtoExist != null && dtoExist.getId() != dto.getId()) {
	        throw new DuplicateRecordException("LoginId is already exist");
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
	            throw new ApplicationException("Exception in User Update"
	                    + e.getMessage());
	        }
	    } finally {
	        session.close();
	    }
	    log.info("Model update End");
	}
	
	/**
	 * Searches User
	 *
	 * @param dto
	 *            : Search Parameters
	 * @throws ApplicationException
	 */
	public List search(UserDTO dto) throws ApplicationException {
	    return search(dto, 0, 0);
	}
	
	/**
	 * Searches Users with pagination
	 *
	 * @return list : List of Users
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 *
	 * @throws ApplicationException
	 */
	public List search(UserDTO dto, int pageNo, int pageSize)
	        throws ApplicationException {
	
	    
	    log.info("Model search Started");
	    Session session = null;
	    List list = null;
	    try {
	        session = HibernateDataSource.getSession();
	        Criteria criteria = session.createCriteria(UserDTO.class);
	
	        if(dto !=null) {
	        if (dto.getId() > 0) {
	            criteria.add(Restrictions.eq("id", dto.getId()));
	        }
	        if (dto.getName() != null && dto.getName().length() > 0) {
	            criteria.add(Restrictions.like("name", dto.getName()
	                    + "%"));
	        }
	        if (dto.getLogin() != null && dto.getLogin().length() > 0) {
	            criteria.add(Restrictions.like("login", dto.getLogin() + "%"));
	        }
	        if (dto.getPassword() != null && dto.getPassword().length() > 0) {
	            criteria.add(Restrictions.like("password", dto.getPassword()
	                    + "%"));
	        }
	        if (dto.getGender() != null && dto.getGender().length() > 0) {
	            criteria.add(Restrictions.like("gender", dto.getGender() + "%"));
	        }
	        if (dto.getDob() != null && dto.getDob().getDate() > 0) {
	            criteria.add(Restrictions.eq("dob", dto.getDob()));
	        }
	        if (dto.getRoleId() > 0) {
	            criteria.add(Restrictions.eq("roleId", dto.getRoleId()));
	        }
	        }
	        // if page size is greater than zero the apply pagination
	        if (pageSize > 0) {
	            criteria.setFirstResult(((pageNo - 1) * pageSize));
	            criteria.setMaxResults(pageSize);
	        }
	
	        criteria.addOrder(Order.asc("id"));
	        
	        list = criteria.list();
	    } catch (HibernateException e) {
	        log.error("Database Exception..", e);
	        throw new ApplicationException("Exception in user search");
	    } finally {
	        session.close();
	    }
	
	    log.info("Model search End");
	    return list;
	}
	
	/**
	 * Gets List of user
	 *
	 * @return list : List of Users
	 * @throws ApplicationException
	 */
	public List list() throws ApplicationException {
	    return list(0, 0);
	}
	
	/**
	 * get List of User with pagination
	 *
	 * @return list : List of Users
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
	        Criteria criteria = session.createCriteria(UserDTO.class);
	
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
	                "Exception : Exception in  Users list");
	    } finally {
	        session.close();
	    }
	
	    log.info("Model list End");
	    return list;
	    
	}
	
	/**
	 * Changes password of the user
	 *
	 * @return true or flase
	 * 
	 * @throws ApplicationException,RecordNotFoundException
	 */
	public boolean changePassword(Long id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException {

		log.info("Model changePassword() started");
		
		System.out.println(findByPK(id).getPassword().equalsIgnoreCase(oldPassword));

		System.out.println(newPassword);
		
		if(findByPK(id) != null  && !findByPK(id).getPassword().equalsIgnoreCase(oldPassword)) {
			throw new RecordNotFoundException(" Old Password is incorrect");
		}
		
		
		boolean pass = false;
		Session session = null;
		Transaction transaction = null;
		UserDTO userDTO = null;
		
		try {
		
			if(findByPK(id) != null  && findByPK(id).getPassword().equalsIgnoreCase(oldPassword)) {
			
			session = HibernateDataSource.getSession();
			transaction = session.beginTransaction();
			
			userDTO = findByPK(id);
			userDTO.setPassword(newPassword);
			session.update(userDTO);
			transaction.commit();
			
			pass = true;
			}
			
		} catch (Exception e) {
			log.error(e);
			if(transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in changePassword()");
		}finally {
			session.close();
		}
		
		HashMap<String, String> map = new HashMap<String, String>(); 
		map.put("login", userDTO.getLogin());
        map.put("password", userDTO.getPassword());
        map.put("name", userDTO.getName());
		
		String message =  EmailBuilder.getChangePasswordMessage(map);
		
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setTo(userDTO.getLogin());
		emailMessage.setSubject("SUNARYS ORS Password has been changed Successfully.");
		emailMessage.setMessage(message);
		emailMessage.setMessageType(EmailMessage.HTML_MSG);
		
		EmailUtility.sendMail(emailMessage);
		
		log.info("Model changePassword() End");
        
		return pass;
	}
	
	/**
	 * Authenticates  the user 
	 *
	 * @return DTO : DTO of Users
	 * 
	 * @throws ApplicationException
	 * @throws RecordNotFoundException 
	 */
	public UserDTO authenticate(String login, String password) throws ApplicationException, RecordNotFoundException {

		log.info("Model authenticate() started");
		
		Session session = null;
		Criteria criteria = null;
		UserDTO userDTO = null;
		
		try {
			session = HibernateDataSource.getSession();
			criteria = session.createCriteria(UserDTO.class);
			criteria.add(Restrictions.eq("login",login));
			List list = criteria.list();
			System.out.println("pass "+password);
			Iterator iterator = list.iterator();
			System.out.println("List Size "+list.size());
			if(list.size() == 1 ) {
					while(iterator.hasNext()) {
						System.out.println("List get " +list.get(0));
								userDTO = (UserDTO)list.get(0);
								iterator.next();
								System.out.println("UserDTO "+userDTO.getLogin()+userDTO.getPassword());
										
					}
				}
			if(userDTO !=null && !userDTO.getPassword().equalsIgnoreCase(password)) {
				userDTO = null;
				throw new RecordNotFoundException("Invalid LoginId and Password");
			}
			
			
		} catch (HibernateException e) {
				log.error(e);
				throw new HibernateException("hibernateException");
		}finally {
			if(session !=null) session.close();
		}
	
		log.info("Model authenticate() ended");
		
		return userDTO;
	}
	
	/**
	 * Registers user to database
	 *
	 * @return long  : primary key of Users
	 * @throws ApplicationException,DuplicateRecordException
	 */
	public long registerUser(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		
		log.info("Model registerUser() started");
			
		if(findByLogin(dto.getLogin()) != null) {
			throw new DuplicateRecordException("User already exist");
		}
		
		 long pk = add(dto);
		
		 HashMap<String,String> map =  new HashMap<String, String>();
		 map.put("login", dto.getLogin());
		 map.put("password", dto.getPassword());
		 map.put("name", dto.getName());
		 
		 String message =  EmailBuilder.getUserRegistrationMessage(map);
		 
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setTo(dto.getLogin());
		 emailMessage.setFrom("Sunrays@gmail.com");	
		 emailMessage.setSubject("User Registration Message");
		 emailMessage.setMessageType(EmailMessage.HTML_MSG);
		 emailMessage.setMessage(message);
		 
		 EmailUtility.sendMail(emailMessage);
		
		 log.info("Model registerUser() ended");
		 
		return pk;
	}
	
	public boolean resetPassword(UserDTO dto) throws ApplicationException {
		return false;
	}

	/**
	 * Sends password to email id
	 *
	 * @return boolean : true or flase
	 * 
	 * @throws ApplicationException,RecordNotFoundException
	 */
	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {

		log.info("Model forgetPassword() started");
		
		boolean pass = false;
		
		if(findByLogin(login) == null ) {
			throw  new RecordNotFoundException("Email Id doesnt exist in database");
		}
		UserDTO userDTO = findByLogin(login);
		pass = true;
		System.out.println(userDTO.getLogin());
		System.out.println(userDTO.getPassword());
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("login", userDTO.getLogin());
		hashMap.put("password", userDTO.getPassword());
		hashMap.put("name", userDTO.getName());
		
		String message =  EmailBuilder.getForgetPasswordMessage(hashMap);
		
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setTo(userDTO.getLogin());
		emailMessage.setFrom("sunrays@gmail.com");
		emailMessage.setSubject("Forgot password Message");
		emailMessage.setMessageType(EmailMessage.HTML_MSG);
		emailMessage.setMessage(message);
		
		EmailUtility.sendMail(emailMessage);
		
		return pass;
	}
	
}
