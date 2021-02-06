/* Admin class that extends the User class. 
 * Nothing much is used from this java file.
 * It only has methods that would have been useful if the Admin were able to change his/her name. */
public class Admin extends User implements AdminInterface, java.io.Serializable {
	
	// As of now, the default constructor for Admin sets the username and password to what Req09 requires.
	public Admin() {
		super("Admin", "Admin01", "FIRST NAME", "LAST NAME");
	}
	
	public Admin(String u, String p, String f, String l) {
		super(u,p,f,l);
	}
	
	// Due to Req09, the Admin's login information will always be the same.
	public String getUsername() {
		return "Admin";
	}
	
	public String getPassword() {
		return "Admin01";
	}
}
