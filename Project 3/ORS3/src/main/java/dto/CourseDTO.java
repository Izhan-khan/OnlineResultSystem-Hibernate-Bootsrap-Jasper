package dto;

/**
 * Course DTO classes
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 *
 */

public class CourseDTO extends BaseDTO  {
	
	
	private static final long serialVersionUID = 1L;
	
	/**
     * Name of Course
     */
	private String name;
	/**
     * Course Description
     */
	private String description;
	/**
     * Course Duration
     */
	private long duration;
	
	
	public String getName() {
		return name;
	}

	public void setName(String courseName) {
		this.name = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getKey() {
		return id+"";
	}

	public String getValue() {
		return name;
	}

}
