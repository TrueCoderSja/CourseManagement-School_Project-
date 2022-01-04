package in.stjosephsacademy.abhiyush.recordmanagement;

public class Student {
	
private String username;
private String password;
	
	public Student(String username, String password) throws InvalidCredentialsException {
		if(RecordManager.verifyStudent(username, password)==true) {
			this.username=username;
			this.password=password;
		}
		else
			throw new InvalidCredentialsException();
	}
	
	public String[] getCoursesList() throws UnauthorisedActionException {
		return RecordManager.getStudentCourseList(username, password);
	}
	
	public boolean takeCourse(int courseIndex) throws UnauthorisedActionException, CourseAlreadyEnrolledException, NoCourseWithIndexException {
		return RecordManager.enrollCourse(username, password, courseIndex);
	}
	
	public boolean deEnrollCourse(int courseIndex) throws CourseNotEnrolledException, UnauthorisedActionException, NoCourseWithIndexException {
		return RecordManager.deEnrollCourse(username, password, courseIndex);
	}
	
	public boolean changePassword(String newPassword) throws UnauthorisedActionException {
		return RecordManager.changeStudentPassword(username, password, newPassword);
	}
}
