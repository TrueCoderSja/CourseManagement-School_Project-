package in.stjosephsacademy.abhiyush.recordmanagement;

@SuppressWarnings("serial")
public class CourseAlreadyEnrolledException extends Exception {
	public CourseAlreadyEnrolledException() {
		super("The course is already enrolled.");
	}
}
