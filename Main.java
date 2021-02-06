import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// THIS IS THE JAVA FILE YOU MUST RUN TO RUN THE PROGRAM
public class Main {
	
	/* The main method in this class creates a CRSData object (an object that stores all of the information)
	 * and calls on a function that will print the first menu of the program. 
	 * This first printMainMenu function will then lead to all of the other print functions that this program uses.
	 * 
	 * Though this file is very long in terms of code, it is only filled with functions that print menus for the user.
	 * 
	 * Every print function passes along Scanner s and CRSData data */
	public static void main(String[] args) {
		CRSData data = new CRSData();
		
		Scanner s = new Scanner(System.in);
	
		printMainMenu(s, data);
	}
	
	/* Function that prints the first menu of the program and asks the user whether they are a user or student.
	 * Leads to the login displays for Admin and Student. */
	public static void printMainMenu(Scanner s, CRSData data) {
		System.out.println("\nAre you an admin or student?\n1.Admin\n2.Student");
		try {
			int choice = Integer.parseInt(s.nextLine());
			switch (choice) {
			case 1: 
				printAdminLogin(s, data);
				break;
			case 2:
				printStudentLogin(s, data);
				break;
			default: 
				System.out.println("Invalid argument. Please enter a number corresponding to the option you would like to select.\n");
				printMainMenu(s, data);
				break;
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Please enter a number.");
			printMainMenu(s, data);
		}

	}
	
	/* Function that prints the Admin login display. 
	 * If login is successful, it calls on the function that prints the Admin's main menu. */
	public static void printAdminLogin(Scanner s, CRSData data) {
		System.out.print("\nUsername: ");
		String username = s.nextLine();
		System.out.print("Password: ");
		String password = s.nextLine();
		if (data.admin.checkLogin(username, password) == 1) {
			System.out.println("Login was successful!");
			printAdminMenu(s, data);
		}
		else {
			System.out.println("Username or password is incorrect");
			printMainMenu(s, data);
		}
	}
	
	/* ---------------------------------------------------------------------------------------------
	 * ---------------------------------------------------------------------------------------------
	 * ------------------------------------ADMIN PRINT FUNCTIONS------------------------------------ 
	 * ---------------------------------------------------------------------------------------------
	 * ---------------------------------------------------------------------------------------------*/
	
	/* Function that prints the Admin's main menu.
	 * They must choose to do either Course Management actions or Report actions. */
	public static void printAdminMenu(Scanner s, CRSData data) {
		System.out.println("\nWhat would you like to do?\n1.Course Management\n2.Reports\n3.Log Out");
		try {
			int choice = Integer.parseInt(s.nextLine());
			switch(choice) {
			case 1:
				printAdminCourseManagement(s, data);
				break;
			case 2:
				printAdminReports(s, data);
				break;
			case 3:
				printMainMenu(s, data);
				break;
			default:
				System.out.println("Invalid argument. Please enter a number corresponding to the option you would like to select.");
				printAdminMenu(s, data);
				break;
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Please enter a number.");
			printAdminMenu(s, data);
		}
	}
	
	/* Function that prints all of the Admin's Course Management actions */
	public static void printAdminCourseManagement(Scanner s, CRSData data) {
		System.out.println("\nWhat do you want to do?");
		System.out.println("1.Create a new course\n2.Delete a course\n3.Edit a course\n4.Display information for a given course\n5.Register a student\n6.Go Back");      
		try {
			int choice = Integer.parseInt(s.nextLine());
			switch (choice) {
			case 1:
				printAdminCreateCourse(s, data);
				break;
			case 2:
				printAdminDeleteCourse(s, data);
				break;
			case 3:
				printAdminEditCourse(s, data);
				break;
			case 4:
				printAdminDisplay(s, data);
				break;
			case 5:
				printAdminRegisterStudent(s, data);
				break;
			case 6:
				printAdminMenu(s, data);
				break;
			default:
				System.out.println("Invalid argument. Please enter a number corresponding to the option you would like to select.");
				printAdminCourseManagement(s, data);
				break;
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Please enter a number.");
			printAdminCourseManagement(s, data);
		}
	}
	
	/* Function that allows Admin to create a course. 
	 * When a new course is created, data adds it to its ArrayList and updates. */
	public static void printAdminCreateCourse(Scanner s, CRSData data) {
		System.out.print("\nYou are creating a course.\nEnter course name: ");
		String courseName = s.nextLine();
		int checkName = data.searchCourseListName(courseName);
		System.out.print("Enter course id: ");
		String courseId = s.nextLine();
		if (checkName != -1 && !courseId.equals(data.coursesList.get(checkName).id)) {
			System.out.println("A course with the name you entered already exists but the course ID you entered does not match what is already set for that course.");
			printAdminCourseManagement(s, data);
		}
		else {
			System.out.print("Enter the maximum number of students that can register for this class: ");
			int maxStudents = Integer.parseInt(s.nextLine());
			System.out.print("Enter course instructor name: ");
			String courseInstructor = s.nextLine();
			System.out.print("Enter course section: ");
			int courseSection = Integer.parseInt(s.nextLine());
			if (data.searchCourseSectionExist(courseName, courseSection)) {
				System.out.println("Sorry, but this course already has a section with this number.");
				printAdminCourseManagement(s,data);
			}
			else {
				System.out.print("Enter course location: ");
				String courseLocation = s.nextLine();
				Course course = new Course(courseName, courseId, maxStudents, 0, new ArrayList<Student>(), courseInstructor, courseSection, courseLocation);
				data.coursesList.add(course);
				data.updateData();
				System.out.println("Course was successfully created!");
				printAdminCourseManagement(s, data);
			}
		}
	}
	
	/* Function that allows Admin to delete a course. */
	public static void printAdminDeleteCourse(Scanner s, CRSData data) {
		System.out.println("\nWhich course would you like to delete?\nBe sure to write the name of the course exactly how it was registered into the program.");
		String course = s.nextLine();
		System.out.println("Which section of this course would you like to delete?");
		int section = Integer.parseInt(s.nextLine());
		int index = data.searchCourseListNameSection(course, section);
		if (index == -1) {
			System.out.println("Sorry. We couldn't find that course.");
			printAdminCourseManagement(s, data);
		}
		else {
			for (int i = 0; i < data.coursesList.get(index).students.size(); i++) {
				int courseInStudent = data.coursesList.get(index).students.get(i).searchCourse(course);
				data.coursesList.get(index).students.get(i).courses.remove(courseInStudent);
			}
			data.coursesList.remove(index);
			data.updateData();
			System.out.println("Successfully deleted course!");
			printAdminCourseManagement(s, data);
		}
	}
	
	/* Function that allows Admin to search a course they want to edit, 
	 * select which part of the course they want to change, 
	 * and actually change it if they are allowed. */
	public static void printAdminEditCourse(Scanner s, CRSData data) {
		System.out.print("\nEnter name of course you wish to edit: ");
		String course = s.nextLine();
		System.out.print("Enter ID of section you wish to edit: ");
		int section = Integer.parseInt(s.nextLine());
		int index = data.searchCourseListNameSection(course, section);
		if (index == -1) {
			System.out.println("Sorry. We couldn't find that course.");
			printAdminCourseManagement(s,data);
		}
		else {
			System.out.println("\nYou have selected " + data.coursesList.get(index).name + ". Which would you like to edit about it?");
			System.out.println("1. Maximum number of students allowed on course\n2. Course instructor\n3. Course section number\n4. Course location\n5. Exit");
			try {
				int choice = Integer.parseInt(s.nextLine());
				switch (choice) {
				case 1:
					System.out.print("Enter the maximum number of students you want to allow for this course: ");
					int maxStudents = Integer.parseInt(s.nextLine());
					data.coursesList.get(index).setMaxStudents(maxStudents);
					System.out.println("You have successfully changed the number of students allowed in this course!");
					printAdminCourseManagement(s, data);
					break;
				case 2:
					System.out.print("Enter the name of the course instructor: ");
					String instructor = s.nextLine();
					data.coursesList.get(index).setInstructor(instructor);
					System.out.println("You have successfully changed the instructor for this course!");
					printAdminCourseManagement(s, data);
					break;
				case 3:
					System.out.print("Enter the new section number for this course: ");
					int section2 = Integer.parseInt(s.nextLine());
					if (!data.searchCourseSectionExist(course, section)) {
						data.coursesList.get(index).setSection(section2);
						System.out.println("You have successfully changed the section number for this course!");
					}
					else {
						System.out.println("Sorry, but that course already has a class with that section number.");
					}
					printAdminCourseManagement(s, data);
					break;
				case 4:
					System.out.print("Enter the new location for this course: ");
					String location = s.nextLine();
					data.coursesList.get(index).setLocation(location);
					System.out.println("You have successfully changed the location for this course!");
					printAdminCourseManagement(s, data);
					break;
				case 5:
					printAdminCourseManagement(s, data);
					break;
				default:
					System.out.println("Invalid argument. Please enter a number corresponding to the option you would like to select.");
					printAdminEditCourse(s, data);
					break;
				}
			}
			catch (NumberFormatException e) {
				System.out.println("Please enter a number.");
				printAdminEditCourse(s, data);
			}
		}
	}
	
	/* Function that allows Admin to display information of one course*/
	public static void printAdminDisplay(Scanner s, CRSData data) {
		System.out.print("\nEnter course ID of course that you want to display: ");
		String course = s.nextLine();
		System.out.print("Enter course section: ");
		int section = Integer.parseInt(s.nextLine());
		int index = data.searchCourseListIdSection(course, section);
		if (index == -1) {
			System.out.println("Sorry, but we couldn't find a course with that ID or section.");
			printAdminCourseManagement(s,data);
		}
		else {
			Course selectedCourse = data.coursesList.get(index);
			System.out.println("\nName of course: " + selectedCourse.name);
			System.out.println("Course ID: " + selectedCourse.id);
			System.out.println("Maximum Number of Students: " + selectedCourse.maxStudents);
			System.out.println("Students in Course: " + selectedCourse.getStudents());
			System.out.println("Instructor: " + selectedCourse.instructor);
			System.out.println("Course Section: " + selectedCourse.section);
			System.out.println("Location: " + selectedCourse.location);
			System.out.println("\nEnter \"1\" to go back to the previous menu.");
			String choice = s.nextLine();
			if (choice.equals("1")){
				printAdminCourseManagement(s,data);
			}
			else {
				printAdminDisplay(s, data);
			}
		}
	}
	
	/* Function that allows Admin to register a new student. */
	public static void printAdminRegisterStudent(Scanner s, CRSData data) {
		System.out.println("\nYou chose to register a student: ");
		System.out.print("Enter first name of student: ");
		String firstName = s.nextLine();
		System.out.print("Enter last name of student: ");
		String lastName = s.nextLine();
		if (data.searchStudent(firstName, lastName) != -1) {
			System.out.println("Sorry, but a student with that name already exists.");
			printAdminCourseManagement(s, data);
		}
		else {
			String userName = "student" + data.studentsList.size();
			Student student = new Student(userName, "student", firstName, lastName);
			data.studentsList.add(student);
			data.updateData();
			System.out.println("\nStudent has been successfully registered.");
			System.out.println("Student name is: " + firstName + " " + lastName);
			System.out.println("Student username is: " + userName);
			System.out.println("Student password is: student");
			printAdminCourseManagement(s, data);
		}
	}
	
	/* Function that displays the Admin's Reports actions. */
	public static void printAdminReports(Scanner s, CRSData data) {
		System.out.println("\nWhat do you want to do?");
		System.out.println("1.View All Courses\n2.View All FULL Courses\n3.Write Full Courses to A File");
		System.out.println("4.View Names Registered For a Course\n5.View Courses a Student is Registered In\n6.Sort by Number of Students\n7.Go Back"); 
		try {
			int choice = Integer.parseInt(s.nextLine());
			switch (choice) {
			case 1:
				printAdminViewAll(s, data);
				break;
			case 2:
				printAdminFullCourses(s, data);
				break;
			case 3:
				printAdminWriteFile(s, data);
				break;
			case 4:
				printAdminSpecificCourse(s, data);
				break;
			case 5:
				printAdminSpecificStudent(s, data);
				break;
			case 6:
				printAdminSort(s, data);
				break;
			case 7:
				printAdminMenu(s, data);
				break;
			default:
				System.out.println("Invalid argument. Please enter a number corresponding to the option you would like to select.");
				printAdminReports(s, data);
				break;
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Please enter a number.");
			printAdminReports(s, data);
		}
	}
	
	/* Function that allows Admin to view all courses in the system. */
	public static void printAdminViewAll(Scanner s, CRSData data) {
		System.out.println("\nHere are all of the courses: ");
		String result = "";
		ArrayList<Course> courses = data.coursesList;
		for (int i = 0; i < courses.size(); i++) {
			result += "\n" + courses.get(i).name + "\n";
			result += "Course ID: " + courses.get(i).id + "\n";
			result += "Course Section: " + courses.get(i).section + "\n";
			result += "Instructor: " + courses.get(i).instructor + "\n";
			result += "Location: " + courses.get(i).location + "\n";
			result += "Number of students registered: " + courses.get(i).nStudents + "/" + courses.get(i).maxStudents + "\n";
			result += "Students:\n";
			if (courses.get(i).students.size() != 0) {
				for (int j = 0; j < courses.get(i).students.size(); j++) {
					String first = courses.get(i).students.get(j).getFirstName();
					String last = courses.get(i).students.get(j).getLastName();
					String user = courses.get(i).students.get(j).getUsername();
					result += "Name: " + first + " " + last + " (Username: " + user + ")\n";
				}
			}
			else {
				result += "No students are in this course.\n";
			}
		}
		System.out.println(result);
		System.out.println("Enter \"1\" to exit to main menu.");
		String choice = s.nextLine();
		if (choice.equals("1")){
			printAdminReports(s,data);
		}
		else {
			printAdminReports(s, data);
		}
	}
	
	/* Function that allows Admin to view all of the courses that are full. */
	public static void printAdminFullCourses(Scanner s, CRSData data) {
		System.out.println("\nHere are the courses that are full: ");
		String result = "";
		ArrayList<Course> courses = data.coursesList;
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).maxStudents == courses.get(i).nStudents) {
				result += "\n" + courses.get(i).name + "\n";
				result += "Course ID: " + courses.get(i).id + "\n";
				result += "Course Section: " + courses.get(i).section + "\n";
				result += "Instructor: " + courses.get(i).instructor + "\n";
				result += "Location: " + courses.get(i).location + "\n";
				result += "Maximum number of students allowed in course: " + courses.get(i).maxStudents + "\n";
			}
		}
		if (result.equals("")) {
			System.out.println("\nNone\n");
		}
		else {
			System.out.println(result);
		}
		System.out.println("Enter \"1\" to exit to main menu.");
		String choice = s.nextLine();
		if (choice.equals("1")){
			printAdminReports(s,data);
		}
		else {
			printAdminFullCourses(s, data);
		}
	}
	
	/* Function that allows Admin to write full courses to a file. */
	public static void printAdminWriteFile(Scanner s, CRSData data) {
		String result = "";
		ArrayList<Course> courses = data.coursesList;
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).maxStudents == courses.get(i).nStudents) {
				if (i == courses.size()-1) {
					result += courses.get(i).name;
				}
				else {
					result += courses.get(i).name + ",";
				}
			}
		}
		if (result.equals("")) {
			result += "None";
		}
		try {
			File file = new File("fullCourses.txt");
			file.createNewFile(); 
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(result);
			fileWriter.flush();
			fileWriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nWe wrote the course names in in fullcourses.text!");
		printAdminReports(s, data);
	}
	
	/* Function that allows Admin to view the students in a specific course. 
	 * I could have used the getStudents() function from the courses class, 
	 * but I thought it just looked more aesthetically pleasing to have each student be on a different line.*/
	public static void printAdminSpecificCourse(Scanner s, CRSData data) {
		System.out.print("Please enter course name: ");
		String name = s.nextLine();
		System.out.print("Please enter course section: ");
		int section = Integer.parseInt(s.nextLine());
		if (data.searchCourseListNameSection(name, section) == -1) {
			System.out.println("\nSorry, but that course does not exist.");
			printAdminReports(s, data);
		}
		else {
			System.out.println("\nStudents registered in " + name + " are:");
			if (data.coursesList.get(data.searchCourseListNameSection(name, section)).students.size() == 0) {
				System.out.println("None");
			}
			for (int i = 0; i < data.coursesList.get(data.searchCourseListNameSection(name, section)).students.size(); i++){
				System.out.println(data.coursesList.get(data.searchCourseListNameSection(name, section)).students.get(i).getFirstName() + " " + 
						data.coursesList.get(data.searchCourseListNameSection(name, section)).students.get(i).getLastName());
			}
			System.out.println("\nEnter \"1\" to exit to main menu.");
			String choice = s.nextLine();
			if (choice.equals("1")) {
				printAdminReports(s,data);
			}
			else {
				printAdminSpecificCourse(s, data);
			}
		}
	}
	
	/* Function that allows Admin to view all of the courses a specific student is enrolled in. */
	public static void printAdminSpecificStudent(Scanner s, CRSData data) {
		System.out.print("Please enter student's first name: ");
		String first = s.nextLine();
		System.out.print("Please enter student's last name: ");
		String last = s.nextLine();
		int index = data.searchStudent(first, last);
		if (index == -1) {
			System.out.println("This student does not exist.");
		}
		else {
			if (data.studentsList.get(index).courses.size() == 0) {
				System.out.println("\nThis student is not enrolled in any courses.");
				printAdminReports(s, data);
			}
			else {
				System.out.println("\nHere are " + first + " " + last + "\'s courses: ");
				for (int i = 0; i < data.studentsList.get(index).courses.size(); i++) {
					System.out.println(data.studentsList.get(index).courses.get(i).name);
				}
				System.out.println("\nEnter \"1\" to exit to main menu.");
				String choice = s.nextLine();
				if (choice.equals("1")) {
					printAdminReports(s,data);
				}
				else {
					printAdminSpecificCourse(s, data);
				}
			}
		}
	}
	
	/* Function that allows Admin to sort the ArrayList of courses in data and print the courses. */
	public static void printAdminSort(Scanner s, CRSData data) {
		data.sortCourses();
		System.out.println("\nHere are are the newly sorted courses: ");
		String result = "";
		ArrayList<Course> courses = data.coursesList;
		for (int i = 0; i < courses.size(); i++) {
			result += "\n" + courses.get(i).name + "\n";
			result += "Course ID: " + courses.get(i).id + "\n";
			result += "Course Section: " + courses.get(i).section + "\n";
			result += "Instructor: " + courses.get(i).instructor + "\n";
			result += "Location: " + courses.get(i).location + "\n";
			result += "Number of students registered: " + courses.get(i).nStudents + "/" + courses.get(i).maxStudents + "\n";
			result += "Students:\n";
			if (courses.get(i).students.size() != 0) {
				for (int j = 0; j < courses.get(i).students.size(); j++) {
					String first = courses.get(i).students.get(j).getFirstName();
					String last = courses.get(i).students.get(j).getLastName();
					String user = courses.get(i).students.get(j).getUsername();
					result += "Name: " + first + " " + last + " (Username: " + user + ")\n";
				}
			}
			else {
				result += "No students are in this course.\n";
			}
		}
		System.out.println(result);
		System.out.println("Enter \"1\" to exit to main menu.");
		String choice = s.nextLine();
		if (choice.equals("1")){
			printAdminReports(s,data);
		}
		else {
			printAdminReports(s, data);
		}
	}

	/* -----------------------------------------------------------------------------------------------
	 * -----------------------------------------------------------------------------------------------
	 * ------------------------------------STUDENT PRINT FUNCTIONS------------------------------------ 
	 * -----------------------------------------------------------------------------------------------
	 * -----------------------------------------------------------------------------------------------*/

	/* Function that prints the student login display. */
	public static void printStudentLogin(Scanner s, CRSData data) {
		System.out.print("\nUsername: ");
		String username = s.nextLine();
		System.out.print("Password: ");
		String password = s.nextLine();
		int index = data.searchStudent(username);
		if (index == -1) {
			System.out.println("Sorry, but your username does not exist.");
			printMainMenu(s, data);
		}
		else if (index >= 0 && data.studentsList.get(index).checkLogin(username, password) == 1) {
			System.out.println("Login was successful!");
			printStudentMenu(s, data, data.studentsList.get(index));
		}
		else if (index >= 0 && data.studentsList.get(index).checkLogin(username, password) == 2) {
			printChangeStudentInfo(s, data, data.studentsList.get(index));
		}
		else {
			System.out.println("Password is incorrect");
			printMainMenu(s, data);
		}
	}
	
	/* Function that prints the display for when a student first logs in. 
	 * The students is prompted to change their username and password. */
	public static void printChangeStudentInfo(Scanner s, CRSData data, Student student) {
		System.out.println("\nHi " + student.getFirstName() + " " + student.getLastName() + "!");
		System.out.println("It looks like you haven't changed your username and password yet.\nPlease do that now!");
		System.out.print("Enter new username: ");
		String username = s.nextLine();
		System.out.print("Enter new password: ");
		String password = s.nextLine();
		if (data.checkForUniqueUsername(username)) {
			student.changeUsername(username);
			student.changePassword(password);
			student.changed = true;
			data.updateData();
			System.out.println("You have successfully changed your login information!");
			printStudentMenu(s, data, student);
		}
		else {
			System.out.println("Sorry, but that username is already taken.");
			printMainMenu(s, data);
		}
	}
	
	/* Function that prints the student menu. */
	public static void printStudentMenu(Scanner s, CRSData data, Student student) {
		System.out.println("\nWelcome, " + student.getFirstName() + " " + student.getLastName() + "!");
		System.out.println("What would you like to do?\n1.View All Courses\n2.View All Courses That Are Not Full\n3.Register in a Course\n4.Withdraw From a Course\n5.View All Courses You Are In\n6.Log Out");
		try {
			int choice = Integer.parseInt(s.nextLine());
			switch(choice) {
			case 1:
				printStudentViewAllCourses(s, data, student);
				break;
			case 2:
				printStudentViewCoursesNotFull(s, data, student);
				break;
			case 3:
				printStudentRegister(s, data, student);
				break;
			case 4:
				printStudentWithdraw(s, data, student);
				break;
			case 5:
				printStudentViewCourses(s, data, student);
				break;
			case 6:
				printMainMenu(s, data);
			default:
				System.out.println("Invalid argument. Please enter a number corresponding to the option you would like to select.");
				printStudentMenu(s, data, student);
				break;
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Please enter a number.");
			printStudentMenu(s, data, student);
		}
	}
	
	/* Function that allows a student to view all of the courses in the system. */
	public static void printStudentViewAllCourses(Scanner s, CRSData data, Student student) {
		System.out.println("\nHere are all of the available courses: ");
		String result = "";
		ArrayList<Course> courses = data.coursesList;
		for (int i = 0; i < courses.size(); i++) {
			result += courses.get(i).name + "\n";
			result += "Course ID: " + courses.get(i).id + "\n";
			result += "Course Section: " + courses.get(i).section + "\n";
			result += "Instructor: " + courses.get(i).instructor + "\n";
			result += "Location: " + courses.get(i).location + "\n";
			result += "Number of students registered: " + courses.get(i).nStudents + "/" + courses.get(i).maxStudents + "\n\n";
		}
		System.out.println(result);
		System.out.println("Enter \"1\" to exit to main menu.");
		String choice = s.nextLine();
		if (choice.equals("1")){
			printStudentMenu(s,data, student);
		}
		else {
			printStudentViewAllCourses(s, data, student);
		}
	}
	
	/* Function that allows a student to view all of the courses that are not full. */
	public static void printStudentViewCoursesNotFull(Scanner s, CRSData data, Student student) {
		System.out.println("\nHere are the courses that are not full: ");
		String result = "";
		ArrayList<Course> courses = data.coursesList;
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).maxStudents != courses.get(i).nStudents) {
				result += courses.get(i).name + "\n";
				result += "Course ID: " + courses.get(i).id + "\n";
				result += "Course Section: " + courses.get(i).section + "\n";
				result += "Instructor: " + courses.get(i).instructor + "\n";
				result += "Location: " + courses.get(i).location + "\n";
				result += "Number of students registered: " + courses.get(i).nStudents + "\n";
				result += "Maximum number of students allowed in course: " + courses.get(i).maxStudents + "\n\n";
			}
		}
		if (result.equals("")) {
			System.out.println("Sorry! Looks like all of the courses are full!");
		}
		else {
			System.out.println(result);
		}
		System.out.println("Enter \"1\" to exit to main menu.");
		String choice = s.nextLine();
		if (choice.equals("1")){
			printStudentMenu(s,data, student);
		}
		else {
			printStudentViewAllCourses(s, data, student);
		}
	}
	
	/* Function that allows a student to register for a course. */
	public static void printStudentRegister(Scanner s, CRSData data, Student student) {
		System.out.println("\nYou are now registering for a course!");
		System.out.print("Enter course name: ");
		String courseName = s.nextLine();
		System.out.print("Enter course section: ");
		int courseSection = Integer.parseInt(s.nextLine());
		int index = data.searchCourseListNameSection(courseName, courseSection);
		if (index == -1) {
			System.out.println("Sorry, but this course does not exist.");
			printStudentMenu(s, data, student);
		}
		else if (student.searchCourse(courseName) != -1) {
			System.out.println("Sorry, but you are enrolled in this course already.");
			printStudentMenu(s, data, student);
		}
		else if (data.coursesList.get(index).maxStudents == data.coursesList.get(index).nStudents) {
			System.out.println("Sorry. This course is full.");
			printStudentMenu(s, data, student);
		}
		else {
			data.coursesList.get(index).students.add(student);
			data.coursesList.get(index).nStudents++;
			student.courses.add(data.coursesList.get(index));
			data.updateData();
			System.out.println("You have successfully registered for " + data.coursesList.get(index).name + "!");
			printStudentMenu(s, data, student);
		}
	}
	
	/* Function that allows a student to withdraw from a course. */
	public static void printStudentWithdraw(Scanner s, CRSData data, Student student) {
		System.out.println("\nYou are now withdrawing from a course!");
		System.out.print("Enter course name: ");
		String courseName = s.nextLine();
		int index = student.searchCourse(courseName);
		int section = student.courses.get(index).section;
		int index2 = data.searchCourseListNameSection(courseName, section);
		int index3 = data.coursesList.get(index2).searchStudent(student.getUsername());
		if (index == -1) {
			System.out.println("Sorry, but you are not currently enrolled in that class.");
			printStudentMenu(s, data, student);
		}
		else {
			data.coursesList.get(index2).students.remove(index3);
			data.coursesList.get(index2).nStudents--;
			student.courses.remove(index);
			data.updateData();
			System.out.println("You have withdrawn from the class.");
			printStudentMenu(s, data, student);
			
		}
	}
	
	/* Function that allows a student to view all of the courses they are in. */
	public static void printStudentViewCourses(Scanner s, CRSData data, Student student) {
		System.out.println("\nHere are all of the classes you are enrolled in: ");
		String result = "";
		for (int i = 0; i < student.courses.size(); i++) {
			if (student.courses.size() -1 != i) {
				result += "\n" + student.courses.get(i).name + "\n";
				result += "Course ID: " + student.courses.get(i).id + "\n";
				result += "Course Section: " + student.courses.get(i).section + "\n";
				result += "Instructor: " + student.courses.get(i).instructor + "\n";
				result += "Location: " + student.courses.get(i).location + "\n";
				result += "Number of students registered: " + student.courses.get(i).nStudents + "/" + student.courses.get(i).maxStudents + "\n";
			}
			else {
				result += "\n" + student.courses.get(i).name + "\n";
				result += "Course ID: " + student.courses.get(i).id + "\n";
				result += "Course Section: " + student.courses.get(i).section + "\n";
				result += "Instructor: " + student.courses.get(i).instructor + "\n";
				result += "Location: " + student.courses.get(i).location + "\n";
				result += "Number of students registered: " + student.courses.get(i).nStudents + "/" + student.courses.get(i).maxStudents;
			}
		}

		System.out.println(result);
		printStudentMenu(s, data, student);
	}
}
