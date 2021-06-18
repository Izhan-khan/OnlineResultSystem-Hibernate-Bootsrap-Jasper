package dto;

import java.util.Date;

/**
 * Student DTO classes
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 *
 */

public class StudentDTO extends BaseDTO implements DropdownList {

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
     * Name of Student
     */
    private String name;
    /**
     * Date of Birth of Student
     */
    private Date dob;
    /**
     * Mobileno of Student
     */
    private String mobileNo;
    /**
     * Email of Student
     */
    private String email;
    /**
     * CollegeId of Student
     */
    private long collegeId;
    /**
     * accessor
     */
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Long getCollegeId() {
        return collegeId;
    }
    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }
    
    
	public String getKey() {
		return id+"";
	}
	public String getValue() {
		return name;
	}

}
