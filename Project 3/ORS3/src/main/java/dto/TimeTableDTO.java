package dto;

import java.util.Date;

/**
 * TimeTable DTO classes
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 *
 */

public class TimeTableDTO extends BaseDTO{

	
	private static final long serialVersionUID = 1L;
	
	private String examTime;
	
	private Date examDate;
	
	private Long courseId;
	
	private Long subjectId;
	
	private Long semester;
	
	
	
	public String getExamTime() {
		return examTime;
	}
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public Long getSemester() {
		return semester;
	}
	public void setSemester(Long semester) {
		this.semester = semester;
	}
	
	public String getKey() {
		return id+"";
	}
	public String getValue() {
		return "TimeTableDTO getValue()";
	}

}
