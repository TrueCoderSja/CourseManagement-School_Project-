package in.stjosephsacademy.abhiyush.recordmanagement;

@SuppressWarnings("serial")
public class UnauthorisedActionException extends Exception {
	public UnauthorisedActionException() {
		System.out.println("Access Denied");
	}
}
