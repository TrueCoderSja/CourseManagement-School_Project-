package in.stjosephsacademy.abhiyush.recordmanagement;

@SuppressWarnings("serial")
public class InvalidCredentialsException extends Exception {
	public InvalidCredentialsException() {
		super("Invalid Username Or Password");
	}
}
