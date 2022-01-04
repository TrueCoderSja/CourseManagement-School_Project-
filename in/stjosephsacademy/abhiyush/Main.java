package in.stjosephsacademy.abhiyush;
import java.util.Scanner;

import in.stjosephsacademy.abhiyush.customdatatypes.EntryAlreadyExistsException;
import in.stjosephsacademy.abhiyush.customdatatypes.NoSuchElementException;
import in.stjosephsacademy.abhiyush.recordmanagement.Admin;
import in.stjosephsacademy.abhiyush.recordmanagement.CourseAlreadyEnrolledException;
import in.stjosephsacademy.abhiyush.recordmanagement.CourseNotEnrolledException;
import in.stjosephsacademy.abhiyush.recordmanagement.RecordManager;
import in.stjosephsacademy.abhiyush.recordmanagement.InvalidCredentialsException;
import in.stjosephsacademy.abhiyush.recordmanagement.NoCourseWithIndexException;
import in.stjosephsacademy.abhiyush.recordmanagement.Student;
import in.stjosephsacademy.abhiyush.recordmanagement.UnauthorisedActionException;

public class Main {
	private static Scanner in;
	
	public static void main(String args[]) {
		RecordManager.init();
		in=new Scanner(System.in);
		String inp;
		
		while(true) {
			System.out.println("Choose an option:\n1)Admin Login \n2)Student Login\n3)Exit");
			inp=in.nextLine().trim();
			if(inp.trim().equals("1")) {
				String user, pass;
				System.out.print("Enter Username: ");
				user=in.nextLine().trim();
				System.out.print("Enter Password: ");
				pass=in.nextLine().trim();
				try {
					Admin admin=new Admin(user, pass);
					useAsAdmin(admin);
				} catch (InvalidCredentialsException e) {
					System.out.println("Invalid Username Or Password. Try again\n");
				}
			} else if (inp.trim().equals("2")) {
				String user, pass;
				System.out.print("Enter Username: ");
				user=in.nextLine().trim();
				System.out.print("Enter Password: ");
				pass=in.nextLine().trim();
				try {
					Student student=new Student(user, pass);
					useAsStudent(student);
				} catch (InvalidCredentialsException e) {
					System.out.println("Invalid Username Or Password. Try again\n");
				}
			} else if(inp.trim().equals("3")) {
				System.out.println("\tEXITING");
				System.exit(0);
			} else {
				System.out.println("Invalid input, try again.\n");
			}
		}
	}
	
	public static void useAsAdmin(Admin admin) {
		System.out.println(); //Add a newline
		String[] options= {
				"View Students",
				"Add Student",
				"Remove Student",
				"View Courses",
				"Add Course",
				"Change Password",
				"Log Out"
		};
		
		while(true) {
			System.out.println(); //Add a newline
			System.out.println("What would you like to do next?");
			for(int i=0;i<options.length;i++) {
				System.out.println((i+1)+") "+options[i]);
			}
			String inp=in.nextLine().trim();
			System.out.println(); //Add a newline
			switch(inp.trim()) {
			case "1":
				try {
					String[][] studentsData=admin.getStudentsList();
					int maxNameLength=Integer.valueOf(studentsData[studentsData.length-1][0]);
					int coursesMaxLength=Integer.valueOf(studentsData[studentsData.length-1][1]);
					String emptyNameSuffix=" ", entrySeperator="", border="";
					for(int n=0;n<maxNameLength;n++)
						emptyNameSuffix+=" ";
					int entryLength=emptyNameSuffix.length()+coursesMaxLength+3;
					for(int n=0;n<entryLength;n++) {
						entrySeperator+="-";
						border+="=";
					}
					entrySeperator=" -"+entrySeperator+"- ";
					border=" ="+border+"= ";
					System.out.println("STUDENTS  DATA\n=============\n\n"+border);
					for(int i=0;i<studentsData.length-1;i++) {
						String name=studentsData[i][0];
						String[] courses=studentsData[i][1].split("<NEW_COURSE>");
						String nameSuffix="";
						for(int n=0;n<maxNameLength-name.length();n++)
							nameSuffix+=" ";
						if(i!=0)
							System.out.println(entrySeperator);
						System.out.print("| "+" "+name+nameSuffix+" | ");
						for(int n=0;n<courses.length;n++) {
							String courseSuffix="";
							for(int n2=0;n2<coursesMaxLength-courses[n].length();n2++)
								courseSuffix+=" ";
							if(n==0)
								System.out.println(courses[n]+courseSuffix+" |");
							else
								System.out.println("| "+emptyNameSuffix+" | "+courses[n]+courseSuffix+" |");
						}
					}
					System.out.println(border);
					String totalStudents="Total Students: "+(studentsData.length-1);
					System.out.print("| "+totalStudents);
					for(int n=0;n<entryLength-totalStudents.length();n++)
						System.out.print(" ");
							System.out.println(" |\n"+border+"\n");
				} catch (UnauthorisedActionException e1) {
					System.out.println("Access Denied");
				}
				break;
			case "2":
				System.out.println("ADD NEW STUDENT\n===============\n");
				System.out.println("Enter username and password of new Student");
				String newUser=in.nextLine().trim();
				String newPassword=in.nextLine().trim();
				try {
					if(admin.registerSudent(newUser, newPassword)==true) {
						System.out.println("Student registered successfully.");
					}
				} catch (EntryAlreadyExistsException e) {
					System.out.println("The student with username \""+newUser+"\" already exists");
				} catch (UnauthorisedActionException e) {
					System.out.println("Access Denied");
				}
				break;
			case "3":
				System.out.println("REMOVE STUDENT\n=============\n");
				System.out.print("Enter student username: ");
				String studentUsername=in.nextLine().trim();
				try {
					if(admin.removeStudent(studentUsername)==true) {
						System.out.println("Student removed successfully");
					}
				} catch (UnauthorisedActionException e1) {
					System.out.println("ACCESS DENIED");
				} catch (NoSuchElementException e1) {
					System.out.println("No student with this username");
				}
			case "4":
				System.out.println("COURSES\n=======\n");
				String[] courses=admin.getCoursesList();
				for(int i=0;i<courses.length;i++) {
					System.out.println('\u25cf'+" "+courses[i]);
				}
				System.out.println();
				break;
			case "5":
				System.out.print("ADD NEW COURSE\n==============\n");
				System.out.println("Enter name of new Course: ");
				String courseName=in.nextLine().trim();
				try {
					if(admin.addCourse(courseName)==true) {
						System.out.println("Course added successfully");
					}
				} catch (UnauthorisedActionException e) {
					System.out.println("Access Denied");
				}
				break;
			case "6":
				System.out.println("CHANGE PASSWORD\n===============\n");
				System.out.print("Enter new password: ");
				String pwd=in.nextLine().trim();
				System.out.print("Confirm new password: ");
				String pwdCnfrm=in.nextLine().trim();
				if(pwd.equals(pwdCnfrm)) {
					try {
						if(admin.changePassword(pwd)==true)
							System.out.println("Password changed successfully.");
					} catch (UnauthorisedActionException e) {
						System.out.println("Access Denied");
					}
				} else {
					System.out.println("Password do not match. Try again");
				}
			case "7":
				System.out.println("\tLOGGED OUT\n");
				admin=null;
				return;
			default:
				System.out.println("\tWrong input. Try again");
			}
		}
	}
	
	public static void useAsStudent(Student student) {
		System.out.println(); //Add a newline
		String[] options= {
				"View Enrolled Courses",
				"Enroll a Course",
				"Denroll a course",
				"Change Password",
				"Log Out"
		};
		
		while(true) {
			System.out.println(); //Add a newline
			System.out.println("What would you like to do next?");
			for(int i=0;i<options.length;i++) {
				System.out.println((i+1)+") "+options[i]);
			}
			String inp=in.nextLine().trim();
			System.out.println(); //Add a newline
			switch(inp) {
			case "1":
				String[] enrolledCourses;
				try {
					enrolledCourses = student.getCoursesList();
					if(enrolledCourses==null) {
						System.out.println("No courses enrolled");
					} else {
						System.out.println("Courses: ");
						for(int i=0;i<enrolledCourses.length;i++)
							System.out.println("\t"+(i+1)+") "+enrolledCourses[i]);
					}
					System.out.println(); //Add a newline
				} catch (UnauthorisedActionException e1) {
					System.out.println("Access Denied");
				}
				break;
			case "2":
				System.out.println("ENROLL COURSE\n=============\n");
				String[] courses=RecordManager.getCoursesList();
				System.out.println("Select a course:");
				for(int i=0;i<courses.length;i++)
					System.out.println((i+1)+") "+courses[i]);
				System.out.print("Your option: ");
				try {
					String tmp=in.nextLine().trim();
					int courseIndex=Integer.parseInt(tmp)-1;
					if(student.takeCourse(courseIndex)==true) {
						System.out.println("Course enrolled successfully!");
					}
				} catch(NumberFormatException e) {
					System.out.println("Invalid input, try again.");
				} catch (UnauthorisedActionException e) {
					System.out.println("Access Denied");
				} catch (CourseAlreadyEnrolledException e) {
					System.out.println("The course is already enrolled");
				} catch (NoCourseWithIndexException e) {
					System.out.println("Invalid Input. Try Again.");
				}
				break;
			case "3":
				System.out.println("DEENROLL COURSE\n===============\n");
				String[] courses_tmp=RecordManager.getCoursesList();
				System.out.println("Select a course:");
				for(int i=0;i<courses_tmp.length;i++)
					System.out.println((i+1)+") "+courses_tmp[i]);
				System.out.print("Your option: ");
				try {
					String tmp=in.nextLine().trim();
					int courseIndex=Integer.parseInt(tmp)-1;
					if(student.deEnrollCourse(courseIndex)==true) {
						System.out.println("Course Denrolled successfully!");
					}
				} catch(NumberFormatException e) {
					System.out.println("Invalid input, try again.");
				} catch (UnauthorisedActionException e) {
					System.out.println("Access Denied");
				} catch (CourseNotEnrolledException e) {
					System.out.println("This course is not enrolled");
				} catch (NoCourseWithIndexException e) {
					System.out.println("Invalid Input. Try Again.");
				}
				break;
			case "4":
				System.out.println("CHANGE PASSWORD\n===============\n");
				System.out.print("Enter new password: ");
				String pwd=in.nextLine().trim();
				System.out.print("Confirm new password: ");
				String pwdCnfrm=in.nextLine().trim();
				if(pwd.equals(pwdCnfrm)) {
					try {
						if(student.changePassword(pwd)==true)
							System.out.println("Password changed successfully.");
					} catch (UnauthorisedActionException e) {
						System.out.println("Access Denied");
					}
				} else {
					System.out.println("Password do not match. Try again");
				}
				break;
			case "5":
				System.out.println("\tLOGGED OUT\n");
				student=null;
				return;
			default:
					System.out.println("\tWrong input. Try again");
			}
		}
	}
	
	public static void quit() {
		System.out.println("An error occurred.\nExiting...");
		System.exit(0);
	}
}
