import java.util.ArrayList;

/* Student class that extends the User class. */
public class Student extends User implements StudentInterface, java.io.Serializable {
	
	/* Boolean to describe whether student has changed his or her username and password yet.
	 * This is to help with when a student logs in for the first time and is asked to change their login information. */
	public boolean changed;
	
	// ArrayList of all of the names of the courses this student is in.
	public ArrayList<Course> courses;
	
	// Constructor for student also takes into account the variables unique to students.
	public Student(String u, String p, String f, String l) {
		super(u,p,f,l);
		changed = false;
		courses = new ArrayList<Course>();
	}
	
	/* Overrides the checkLogin function from User.java 
	 * so that it takes into account whether the student has changed his/her login information yet. */
	public int checkLogin(String u, String p) {
		if (u.equals(getUsername()) && p.equals(getPassword()) && changed) {
			return 1;
		}
		else if (u.equals(getUsername()) && p.equals(getPassword()) && !changed){
			return 2;
		}
		else {
			return 3;
		}
	}
	
	// Function that looks through a student's list of courses to find a course with a specific name.
	public int searchCourse(String name) {
		int index = -1;
		for (int i = 0; i < courses.size(); i++) {
			if (name.equals(courses.get(i).name)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
}
