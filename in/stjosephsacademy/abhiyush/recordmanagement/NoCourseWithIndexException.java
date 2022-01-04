package in.stjosephsacademy.abhiyush.recordmanagement;

@SuppressWarnings("serial")
public class NoCourseWithIndexException extends Exception {
	public NoCourseWithIndexException() {
		super("No such Course");
	}
}
