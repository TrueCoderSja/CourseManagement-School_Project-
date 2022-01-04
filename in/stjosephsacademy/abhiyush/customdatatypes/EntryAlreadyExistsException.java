package in.stjosephsacademy.abhiyush.customdatatypes;

@SuppressWarnings("serial")
public class EntryAlreadyExistsException extends Exception {
	public EntryAlreadyExistsException(String username) {
		super("The user \""+username+"\" already exists");
	}
}
