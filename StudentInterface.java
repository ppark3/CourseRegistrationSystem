// Interface for Student
public interface StudentInterface {
	
	public void setFirstName(String name);
	public void setLastName(String name);
	
	public void changeUsername(String name);
	public void changePassword(String name);
	
	public int checkLogin(String u, String p);
	public int searchCourse(String name);
	
}
