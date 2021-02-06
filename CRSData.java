import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.File;

/* This class is mainly used for creating an object that will store all of the information
 * (such as an ArrayList of students and courses and the Admin)
 * 
 * Other than a couple of exceptions, every function in this class is a function that has to do with
 * searching through the ArrayLists of students and courses. */
public class CRSData implements java.io.Serializable  {
	
	// variables that store all of the information that this program needs
	ArrayList<Student> studentsList = new ArrayList<Student>();
	ArrayList<Course> coursesList = new ArrayList<Course>(); 
	Admin admin = new Admin();
	
	public static void main(String[] args) {
	}
	
	/* constructor for CRSData object 
	 * Every time a CRSData object is made, it takes the information from the csv or ser file. 
	 * This is where deserializaiton happens. */
	public CRSData() {
		File serFile = new File("data.ser");
		if (!serFile.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader("MyUniversityCourses.csv"));
				String line = br.readLine();
				line = br.readLine();
				while (line != null) {
					String[] courseInfo = line.split(",");
					Course course = createCourse(courseInfo);
					coursesList.add(course);
					line = br.readLine();
				}
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else {
			ArrayList<Object> deserialized = new ArrayList<Object>();
			try {
				FileInputStream fis = new FileInputStream("data.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				deserialized = (ArrayList<Object>)ois.readObject();
				ois.close();
				fis.close();
			}
			catch(IOException i ){
				i.printStackTrace();
			}
			catch(ClassNotFoundException c) {
				c.printStackTrace();
			}
			admin = (Admin)deserialized.get(0);
			coursesList = (ArrayList<Course>)deserialized.get(1);
			studentsList = (ArrayList<Student>)deserialized.get(2);
		}
	}
	
	/* This createCourse function is only used when taking information from the CSV file (when every parameter is a String). 
	 * This is a helper function for CRSData's constructor. */
	public Course createCourse(String[] data) {
		String name = data[0];
		String id = data[1];
		int maxStudents = Integer.parseInt(data[2]);
		int nStudents = Integer.parseInt(data[3]);
		ArrayList<Student> students = new ArrayList<Student>();
		String instructor = data[5];
		int section = Integer.parseInt(data[6]);
		String location = data[7];
		Course course = new Course(name, id, maxStudents, nStudents, students, instructor, section, location);
		return course;
	}
	
	/* Serializes the data in this object to data.ser 
	 * This is used in Main.java whenever a data is changed and needs to be updated in the ser file. */
	public void updateData() {
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(admin);
		data.add(coursesList);
		data.add(studentsList);
		
		try {
			FileOutputStream fileOut = new FileOutputStream("data.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(data);
			out.close();
			fileOut.close();
		}
		catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	// Searches through ArrayList of courses and returns the index of the course with the same name as the one the user inputed.
	public int searchCourseListName(String name) {
		int index = -1;
		for (int i = 0; i < coursesList.size(); i++) {;
			if (name.equals(coursesList.get(i).name)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	// Searches through ArrayList of courses and returns the index of the course with the same name and section as the one the user inputed.
	public int searchCourseListNameSection(String name, int section) {
		int index = -1;
		for (int i = 0; i < coursesList.size(); i++) {
			if (name.equals(coursesList.get(i).name) && section == coursesList.get(i).section) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	// Searches through ArrayList of courses and returns the index of the course with the same ID and section as the one the user inputed.
	public int searchCourseListIdSection(String id, int section) {
		int index = -1;
		for (int i = 0; i < coursesList.size(); i++) {
			if (id.equals(coursesList.get(i).id) && section == coursesList.get(i).section) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	// Searches through ArrayList of courses and checks to see if a specific course has a specific section.
	public boolean searchCourseSectionExist(String name, int section) {
		boolean result = false;
		for (int i = 0; i < coursesList.size(); i++) {
			if (section == coursesList.get(i).section && name.equals(coursesList.get(i).name)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	/* Searches through ArrayList of courses and checks which course has the most number of students. 
	 * It then returns the number of students in the course with the most number of students. */
	public int searchCoursesStudentsMax() {
		int result = 0;
		for (int i = 0; i < coursesList.size(); i++) {
			if (coursesList.get(i).nStudents > result) {
				result = i;
			}
		}
		return result;
	}
	
	// Sorts all of the courses in coursesList from least number of students to most. 
	public void sortCourses() {
		int maxNStudent = searchCoursesStudentsMax();
		if (maxNStudent != 0) {
			ArrayList<Course> newList = new ArrayList<Course>();
			for (int j = 0; j < maxNStudent; j++) {
				for (int i = 0; i < coursesList.size(); i++) {
					if (coursesList.get(i).nStudents == j) {
						newList.add(coursesList.get(i));
					}
				}
			}
			coursesList = newList;
		}
		updateData();
	}
	
	// Searches through ArrayList of students and checks if a certain username already exists. 
	public boolean checkForUniqueUsername(String username) {
		boolean result = true;
		for (int i = 0; i < studentsList.size(); i++) {
		if (username.equals(studentsList.get(i).getUsername())) {
			result = false;;
			break;
		}
	}
	return result;
	}
	
	// Searches through ArrayList of students and returns the index of the student with the same username as the one the user inputed.
	public int searchStudent(String username) {
		int index = -1;
		for (int i = 0; i < studentsList.size(); i++) {
			if (username.equals(studentsList.get(i).getUsername())) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	// Searches through ArrayList of students and returns the index of the student with the same name as the one the user inputed.
	public int searchStudent(String first, String last) {
		int result = -1;
		for (int i = 0; i < studentsList.size(); i++) {
			if (first.equals(studentsList.get(i).getFirstName()) && last.equals(studentsList.get(i).getLastName())) {
				result = i;
				break;
			}
		}
		return result;
	}
}
