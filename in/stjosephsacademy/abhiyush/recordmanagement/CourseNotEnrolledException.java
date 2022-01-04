package in.stjosephsacademy.abhiyush.recordmanagement;
@SuppressWarnings("serial")
public class CourseNotEnrolledException extends Exception {
	public CourseNotEnrolledException() {
		super("The course is already enrolled");
	}
}
