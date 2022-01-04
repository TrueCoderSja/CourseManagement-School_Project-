package in.stjosephsacademy.abhiyush.recordmanagement;

import in.stjosephsacademy.abhiyush.customdatatypes.EntryAlreadyExistsException;
import in.stjosephsacademy.abhiyush.customdatatypes.NoSuchElementException;

public class Admin {
	
	private String username;
	private String password;
	
	public Admin(String username, String password) throws InvalidCredentialsException {
		if(RecordManager.verifyAdmin(username, password)==true) {
			this.username=username;
			this.password=password;
		}
		else
			throw new InvalidCredentialsException();
	}
	
	public String[][] getStudentsList() throws UnauthorisedActionException {
		return RecordManager.getStudentList(username, password);
	}
	
	public boolean registerSudent(String newUsername, String newPassword) throws EntryAlreadyExistsException, UnauthorisedActionException {
		return RecordManager.addStudent(username, password, newUsername, newPassword);
	}
	
	public boolean removeStudent(String studentUsername) throws UnauthorisedActionException, NoSuchElementException {
		return RecordManager.removeStudent(username, password, studentUsername);
	}
	
	public String[] getCoursesList() {
		return RecordManager.getCoursesList();
	}
	
	public boolean addCourse(String courseName) throws UnauthorisedActionException {
		return RecordManager.addCourse(username, password, courseName);
	}
	
	public boolean changePassword(String newPassword) throws UnauthorisedActionException {
		return RecordManager.changeAdminPassword(username, password, newPassword);
	}
}
