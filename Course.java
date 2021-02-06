import java.util.ArrayList;

public class Course implements java.io.Serializable{

	// All of these variables are things that were in Req01.
	public String name;
	public String id;
	public int maxStudents;
	public int nStudents;
	public ArrayList<Student> students;
	public String instructor;
	public int section;
	public String location;
	
	public Course(String n, String i, int m, int ns, ArrayList<Student> s, 
			String in, int sect, String l) {
		name = n;
		id = i;
		maxStudents = m;
		nStudents = ns;
		students = s;
		instructor = in;
		section = sect;
		location = l;
	}
	
	public void setMaxStudents(int i) {
		maxStudents = i;
	}
	
	public void setInstructor(String s) {
		instructor = s;
	}
	
	public void setSection(int s) {
		section = s;
	}
	
	public void setLocation(String s) {
		location = s;
	}
	
	/* Returns the student ArrayList in a string where all of the names are separated by commas. 
	 * This is only really used when needing to print all of the students of a certain course in one line. */
	public String getStudents() {
		String result = "";
		if (students != null) {
			for (int i = 0; i < students.size(); i++) {
				if (i == students.size() - 1) {
					result += students.get(i).getFirstName() + " " + students.get(i).getLastName();
				}
				else {
					result += students.get(i).getFirstName() + " " + students.get(i).getLastName() + ", ";
				}
			}
			return result;
		}
		else {
			return "None";
		}
	}
	
	// Looks through ArrayList of students to see if it has a student of a specific username.
	public int searchStudent(String name) {
		int index = -1;
		for (int i = 0; i < students.size(); i++) {
			if (name.equals(students.get(i).getUsername())) {
				index = i;
				break;
			}
		}
		return index;
	}
}
