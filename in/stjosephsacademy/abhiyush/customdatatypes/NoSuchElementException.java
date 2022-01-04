package in.stjosephsacademy.abhiyush.customdatatypes;

@SuppressWarnings("serial")
public class NoSuchElementException extends Exception {
	public NoSuchElementException() {
		super("No element with this id exists.");
	}
}
