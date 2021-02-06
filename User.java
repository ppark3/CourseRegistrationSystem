// Parent class for Student and Admin
public abstract class User implements java.io.Serializable{
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;

	public User(String u, String p, String f, String l) {
		username = u;
		password = p;
		firstName = f;
		lastName = l;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void changeUsername(String name) {
		username = name;
	}
	
	public void changePassword(String name) {
		password = name;
	}

	public void setFirstName(String name) {
		firstName = name;
	}
	
	public void setLastName(String name) {
		lastName = name;
	}
	
	// Function that checks to see if login is successful for both Admin and Student. 
	public int checkLogin(String u, String p) {
		if (u.equals(username) && p.equals(password)) {
			return 1;
		}
		else {
			return 2;
		}
	}
}
