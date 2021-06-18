package model;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Factory of Model classes
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 *
 */
public final class ModelFactory {

	private static ResourceBundle bundle = ResourceBundle.getBundle("system");

	private static final String DATABASE = bundle.getString("DATABASE");
	private static ModelFactory mFactory = null;
	/**
	 * Cache of Model classes
	 */
	private static HashMap<String, Object> modelCache = new HashMap<String, Object>();

	/**
	 * Constructor is private so no other class can create instance of Model Locator
	 */
	private ModelFactory() {

	}

	/**
	 * Get the instance of Model Locator
	 *
	 * @return mFactory
	 */
	public static ModelFactory getInstance() {
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}

	/**
	 * Get instance of Mark-sheet Model
	 *
	 * @return MarksheetModel_Interface
	 */
	public MarksheetModel_Interface getMarksheetModel() {
		MarksheetModel_Interface marksheetModel = (MarksheetModel_Interface) modelCache.get("marksheetModel");
		if (marksheetModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				marksheetModel = new MarksheetModel_Hibernate_Implement();
			}
			if ("JDBC".equals(DATABASE)) {
				marksheetModel = new MarksheetModel_JDBC_Implement();
			}
			modelCache.put("marksheetModel", marksheetModel);
		}

		return marksheetModel;
	}

	/**
	 * Get instance of User Model
	 *
	 * @return UserModel_Interface
	 */
	public UserModel_Interface getUserModel() {
		UserModel_Interface userModel = (UserModel_Interface) modelCache.get("userModel");
		if (userModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				userModel = new UserModel_Hibernate_Implement();
			}
			if ("JDBC".equals(DATABASE)) {
				userModel = new UserModel_JDBC_Implement();
			}
			modelCache.put("userModel", userModel);
		}

		return userModel;
	}

	/**
	 * Get instance of College Model
	 *
	 * @return CollegeModel_Interface
	 */
	public CollegeModel_Interface getCollegeModel() {
		CollegeModel_Interface collegeModel = (CollegeModel_Interface) modelCache.get("collegeModel");
		if (collegeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				collegeModel = new CollegeModel_Hibernate_Implement();
			}
			if ("JDBC".equals(DATABASE)) {
				collegeModel = new CollegeModel_JDBC_Implement();
			}
			modelCache.put("collegeModel", collegeModel);
		}

		return collegeModel;
	}

	/**
	 * Get instance of Student Model
	 *
	 * @return StudentModel_Interface
	 */
	public StudentModel_Interface getStudentModel() {
		StudentModel_Interface studentModel = (StudentModel_Interface) modelCache.get("StudentModel");
		if (studentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				studentModel = new StudentModel_Hibernate_Implement();
			}
			if ("JDBC".equals(DATABASE)) {
				studentModel = new StudentModel_JDBC_Implement();
			}
			modelCache.put("studentModel", studentModel);
		}

		return studentModel;
	}

	/**
	 * Get instance of Role Model
	 *
	 * @return RoleModel_Interface
	 */
	public RoleModel_Interface getRoleModel() {
		RoleModel_Interface roleModel = (RoleModel_Interface) modelCache.get("roleModel");
		if (roleModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				roleModel = new RoleModel_Hibernate_Implement();
			}
			if ("JDBC".equals(DATABASE)) {
				roleModel = new RoleModel_JDBC_Implement();
			}
			modelCache.put("roleModel", roleModel);
		}

		return roleModel;

	}
	
	/**
	 * Get instance of Course Model
	 *
	 * @return CourseModel_Interface
	 */
	public CourseModel_Interface getCourseModel() {

		CourseModel_Interface courseModel = (CourseModel_Interface) modelCache.get("courseModel");

		if (courseModel == null) {

			if (bundle.getString("DATABASE").equals("Hibernate")) {
				courseModel = new CourseModel_Hibernate_Implement();
			}
			if (bundle.getString("DATABASE").equals("JDBC")) {
				courseModel = new CourseModel_JDBC_Implement();
			}
			modelCache.put("courseModel", courseModel);
		}
		return courseModel;
	}
	
	/**
	 * Get instance of Faculty Model
	 *
	 * @return FacultyModel_Interface
	 */
	public FacultyModel_Interface getFacultyModel() {

		FacultyModel_Interface facultyModel = (FacultyModel_Interface) modelCache.get("facultyModel");

		if (facultyModel == null) {

			if (bundle.getString("DATABASE").equals("Hibernate")) {
				facultyModel = new FacultyModel_Hibernate_Implement();
			}
			if (bundle.getString("DATABASE").equals("JDBC")) {
				facultyModel = new FacultyModel_JDBC_Implement();
			}
			modelCache.put("facultyModel", facultyModel);
		}
		return facultyModel;
	}
	/**
	 * Get instance of Subject Model
	 *
	 * @return SubjectModel_Interface
	 */
	public SubjectModel_Interface getSubjectModel() {

		SubjectModel_Interface subjectModel = (SubjectModel_Interface) modelCache.get("subjectModel");

		if (subjectModel == null) {

			if (bundle.getString("DATABASE").equals("Hibernate")) {
				subjectModel = new SubjectModel_Hibernate_Implement();
			}
			if (bundle.getString("DATABASE").equals("JDBC")) {
				subjectModel = new SubjectModel_JDBC_Implement();
			}
			modelCache.put("subjectModel", subjectModel);
		}
		return subjectModel;
	}
	
	/**
	 * Get instance of TimeTable Model
	 *
	 * @return TimeTableModel_Interface
	 */
	public TimeTableModel_Interface getTimeTableModel() {

		TimeTableModel_Interface timetableModel = (TimeTableModel_Interface) modelCache.get("timetableModel");

		if (timetableModel == null) {

			if (bundle.getString("DATABASE").equals("Hibernate")) {
				timetableModel = new TimeTableModel_Hibernate_Implement();
			}
			if (bundle.getString("DATABASE").equals("JDBC")) {
				timetableModel = new TimeTableModel_JDBC_Implement();
			}
			modelCache.put("timetableModel", timetableModel);
		}
		return timetableModel;
	}

}