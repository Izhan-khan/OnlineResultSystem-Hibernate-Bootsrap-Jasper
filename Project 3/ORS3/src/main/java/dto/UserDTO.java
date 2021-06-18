package dto;

import java.util.Date;

/**
 * User DTO classes
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 *
 */

public class UserDTO extends BaseDTO implements DropdownList {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Lock Active constant for User
     */
    public static final String ACTIVE = "Active";
    /**
     * Lock Inactive constant for User
     */
    public static final String INACTIVE = "Inactive";
    /**
     * Name of User
     */
    private String name;
	/**
     * Login of User
     */
    private String login;
    /**
     * Password of User
     */
    private String password;
    /**
     * LocalDate of Birth of User
     */
    private Date dob;
    /**
     * MobielNo of User
     */
    private String mobileNo;
    /**
     * Role of User
     */
    private long roleId;
    /**
     * Gender of User
     */
    private String gender;
    /**
     * accessor
     */
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getKey() {
        return id + "";
    }

    public String getValue() {
        return name;
    }
}
