package in.stjosephsacademy.abhiyush.recordmanagement;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import in.stjosephsacademy.abhiyush.Constants;
import in.stjosephsacademy.abhiyush.Main;
import in.stjosephsacademy.abhiyush.customdatatypes.EntryAlreadyExistsException;
import in.stjosephsacademy.abhiyush.customdatatypes.NoSuchElementException;
import in.stjosephsacademy.abhiyush.customdatatypes.StringDictionary;
import in.stjosephsacademy.abhiyush.customdatatypes.StringList;

public class RecordManager {
	private static StringList courses;
	private static StringDictionary adminCredentials, studentCredentials, studentCourses;
	private static File adminCredentialsFile, studentCredentialsFile, coursesFile, studentCoursesFile;
	
	public static void init() {
		//Initialize admin credentials
		adminCredentials=new StringDictionary();
		adminCredentialsFile=new File(Constants.ADMIN_CREDENTIAL_FILE);
		fillDict(adminCredentialsFile, adminCredentials, new String[][] {
			{ Constants.DEFAULT_USERNAME, Constants.DEFAULT_PASSWORD }
		});
		
		//Initialize student Credentials
		studentCredentials=new StringDictionary();
		studentCredentialsFile=new File(Constants.STUDENT_CREDENTIAL_FILE);
		fillDict(studentCredentialsFile, studentCredentials, null);
		
		//Initialze courses
		courses=new StringList();
		coursesFile=new File(Constants.COURSES_FILE);
		fillList(coursesFile, courses, Constants.DEFAULT_COURSES);
		
		//Initialize courses to students
		studentCourses=new StringDictionary();
		studentCoursesFile=new File(Constants.STUDENT_COURSES_FILE);
		fillDict(studentCoursesFile, studentCourses, null);
	}
	
	private static void fillDict(File srcFile, StringDictionary dict, String[][] defaultData) {
		try {
			DataInputStream din=new DataInputStream(new FileInputStream(srcFile));
			while(true) {
				try {
					dict.add(din.readUTF(), din.readUTF());
				} catch(EOFException e) {
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			din.close();
		} catch (FileNotFoundException e) {
			try {
				srcFile.createNewFile();
				if(defaultData!=null) {
					DataOutputStream dout=new DataOutputStream(new FileOutputStream(srcFile));
					for(int i=0;i<defaultData.length;i++) {
						dout.writeUTF(defaultData[i][0]);
						dout.writeUTF(defaultData[i][1]);
						dict.add(defaultData[i][0], defaultData[i][1]);
					}
					dout.close();
				}
			} catch (IOException ex) {
				e.printStackTrace();
				Main.quit();
			}
		} catch (IOException e) {
			e.printStackTrace();
			Main.quit();
		}
	}
	
	private static void fillList(File srcFile, StringList list, String[] defaultData) {
		try {
			DataInputStream din=new DataInputStream(new FileInputStream(srcFile));
			while(true) {
				try {
					list.add(din.readUTF());
				} catch(EOFException e) {
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			din.close();
		} catch (FileNotFoundException e) {
			try {
				srcFile.createNewFile();
				if(defaultData!=null) {
					DataOutputStream dout=new DataOutputStream(new FileOutputStream(srcFile));
					for(int i=0;i<defaultData.length;i++) {
						dout.writeUTF(defaultData[i]);
						list.add(defaultData[i]);
					}
					dout.close();
				}
			} catch (IOException ex) {
				e.printStackTrace();
				Main.quit();
			}
		} catch (IOException e) {
			e.printStackTrace();
			Main.quit();
		}
	}
	
	private static void writeDict(File destFile, StringDictionary dict) {
		try {
			DataOutputStream dout=new DataOutputStream(new FileOutputStream(destFile));
			String[] keys=dict.getKeys();
			for(int i=0;i<keys.length;i++) {
				dout.writeUTF(keys[i]);
				dout.writeUTF(dict.getValue(keys[i]));
			}
			dout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Main.quit();
		} catch (IOException e) {
			e.printStackTrace();
			Main.quit();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			Main.quit();
		}
	}
	
	private static void writeList(File destFile, StringList list) {
		try {
			DataOutputStream dout=new DataOutputStream(new FileOutputStream(destFile));
			String[] elem=list.toArray();
			for(int i=0;i<elem.length;i++) {
				dout.writeUTF(elem[i]);
			}
			dout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Main.quit();
		} catch (IOException e) {
			e.printStackTrace();
			Main.quit();
		}
	}
	
	
	static boolean verifyAdmin(String username, String password) {
		try {
			if(adminCredentials.getValue(username).equals(password))
				return true;
		} catch (NoSuchElementException e) {
			//Nothing to do
		}
		return false;
	}
	
	static boolean changeAdminPassword(String username, String password, String newPassword) throws UnauthorisedActionException {
		if(verifyAdmin(username, password)) {
			adminCredentials.add(username, newPassword);
			writeDict(adminCredentialsFile, adminCredentials);
			return true;
		} else {
			throw new UnauthorisedActionException();
		}
	}
	
	static boolean changeStudentPassword(String username, String password, String newPassword) throws UnauthorisedActionException {
		if(verifyStudent(username, password)) {
			studentCredentials.add(username, newPassword);
			writeDict(studentCredentialsFile, studentCredentials);
			return true;
		} else {
			throw new UnauthorisedActionException();
		}
	}
	
	static boolean verifyStudent(String username, String password) {
		try {
			if(studentCredentials.getValue(username).equals(password))
				return true;
		} catch (NoSuchElementException e) {
			//Nothing to do
		}
		return false;
	}
	
	static boolean addStudent(String adminUsername, String adminPassword, String newUsername, String newPassword) throws EntryAlreadyExistsException, UnauthorisedActionException {
		if(verifyAdmin(adminUsername, adminPassword)) {
			try {
				studentCredentials.getValue(newUsername);
				throw new EntryAlreadyExistsException(newUsername);
			} catch(NoSuchElementException e) {
				studentCredentials.add(newUsername, newPassword);
				writeDict(studentCredentialsFile, studentCredentials);			
				return true;
			}
		} else {
			throw new UnauthorisedActionException();
		}
	}
	
	static boolean removeStudent(String adminUsername, String adminPassword, String studentUsername) throws UnauthorisedActionException, NoSuchElementException {
		if(verifyAdmin(adminUsername, adminPassword)) {
			studentCredentials.deleteEntry(adminUsername);
			writeDict(studentCredentialsFile, studentCredentials);
			return true;
		} else {
			throw new UnauthorisedActionException();
		}
	}
	
	static String[] getStudentCourseList(String studentUsername, String studentPassword) throws UnauthorisedActionException {
		if(verifyStudent(studentUsername, studentPassword)) {
			try {
				return studentCourses.getValue(studentUsername).split("<NEW_COURSE>");
			} catch (NoSuchElementException e) {
				return null;
			}
		} else {
			throw new UnauthorisedActionException();
		}
	}
	
	static boolean enrollCourse(String studentUsername, String studentPassword, int courseIndex) throws UnauthorisedActionException, CourseAlreadyEnrolledException, NoCourseWithIndexException {
		if(verifyStudent(studentUsername, studentPassword)) {
			if(courseIndex<0 || courseIndex>=courses.length())
				throw new NoCourseWithIndexException();
			try {
				String prevValue=studentCourses.getValue(studentUsername);
				String[] prevCourses=prevValue.split("<NEW_COURSE>");
				//If no error
				for(int i=0;i<prevCourses.length;i++) {
					if(courses.get(courseIndex).equals(prevCourses[i])) {
						throw new CourseAlreadyEnrolledException();
					}
				}
				prevValue+="<NEW_COURSE>"+courses.get(courseIndex);
				studentCourses.add(studentUsername, prevValue);
			} catch(NoSuchElementException e) {
				studentCourses.add(studentUsername, courses.get(courseIndex));
			}
			writeDict(studentCoursesFile, studentCourses);
			return true;
		} else {
			throw new UnauthorisedActionException();
		}
	}

	static boolean deEnrollCourse(String studentUsername, String studentPassword, int courseIndex) throws CourseNotEnrolledException, UnauthorisedActionException, NoCourseWithIndexException {
		if(courseIndex<0 || courseIndex>=courses.length())
			throw new NoCourseWithIndexException();
		if(verifyStudent(studentUsername, studentPassword)) {
			try {
				String prevValue=studentCourses.getValue(studentUsername);
				String[] enrolledCourses=prevValue.split("<NEW_COURSE>");
				int index=-1;
				//If no error
				for(int i=0;i<enrolledCourses.length;i++) {
					if(courses.get(courseIndex).equals(enrolledCourses[i])) {
						index=i;
						break;
					}
				}
				String newValue="";
				if(index!=-1) {
					for(int i=0;i<enrolledCourses.length;i++) {
						if(i==index)
							continue;
						if(i==0 || (i==1 && index==0))
							newValue+=enrolledCourses[i];
						else
							newValue+="<NEW_COURSE>"+enrolledCourses[i];
					}
					studentCourses.add(studentUsername, newValue);
				} else {
					throw new CourseNotEnrolledException();
				}
			} catch(NoSuchElementException e) {
				throw new CourseNotEnrolledException();
			}
			writeDict(studentCoursesFile, studentCourses);
			return true;
		} else {
			throw new UnauthorisedActionException();
		}
	}
	
	static boolean addCourse(String adminUsername, String adminPassword, String courseName) throws UnauthorisedActionException {
		if(verifyAdmin(adminUsername, adminPassword)) {
			courses.add(courseName);
			writeList(coursesFile, courses);			
			return true;
		} else {
			throw new UnauthorisedActionException();
		}
	}
	
//	public static boolean removeCourse(String adminUsername, String adminPassword, int courseIndex) throws UnauthorisedActionException {
//		if(verifyAdmin(adminUsername, adminPassword)) {
//			courses.delete(courses.get(courseIndex));
//			writeList(coursesFile, courses);			
//			return true;
//		} else {
//			throw new UnauthorisedActionException();
//		}
//	}
	
	static String[][] getStudentList(String adminUsername, String adminPassword) throws UnauthorisedActionException {
		if(verifyAdmin(adminUsername, adminPassword)) {
			String[] studentsNames=studentCredentials.getKeys();
			String[] coursesEnrolled=new String[studentsNames.length];
			for(int i=0;i<studentsNames.length;i++) {
				try {
					coursesEnrolled[i]=studentCourses.getValue(studentsNames[i]);
				} catch (NoSuchElementException e) {
					coursesEnrolled[i]="";
				}
			}
			
			String[][] studentsData=new String[studentsNames.length+1][2];
			int namesMaxLength=0, coursesMaxLength=0;
			for(int i=0;i<studentsNames.length;i++) {
				studentsData[i][0]=studentsNames[i];
				namesMaxLength=(studentsNames[i].length()>namesMaxLength)?studentsNames[i].length():namesMaxLength;
				studentsData[i][1]=coursesEnrolled[i];
			}
			for(int i=0;i<courses.length();i++) {
				coursesMaxLength=(courses.get(i).length()>coursesMaxLength)?courses.get(i).length():coursesMaxLength;
			}
			//Store max length of name and courses in last index
			studentsData[studentsData.length-1][0]=namesMaxLength+"";
			studentsData[studentsData.length-1][1]=coursesMaxLength+"";
			return studentsData;
		} else {
			throw new UnauthorisedActionException();
		}
	}
	
	public static String[] getCoursesList() {
		return courses.toArray();
	}
}
