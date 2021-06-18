package dto;

/**
 * Subject DTO classes
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 *
 */

public class SubjectDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Subject Name
	 */
	private String name;
	/**
	 * Description Name
	 */
	private String description;
	/**
	 * Subject Course Id
	 */
	private long courseId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getCourseId() {
		return courseId;
	}
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	public String getKey() {
		return id+"";
	}
	public String getValue() {
		return name;
	}
	
}
