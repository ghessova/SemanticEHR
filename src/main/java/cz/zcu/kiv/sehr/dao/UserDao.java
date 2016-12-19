package cz.zcu.kiv.sehr.dao;

public class UserDao {
	
	public enum ROLE {
		ADMIN, USER;
	}

	private String username;
	private String password;
	private String emailAddress;
	private ROLE role;
	
	public UserDao(String username, String password, String emailAddress, ROLE role) {
		super();
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
		this.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setRole(ROLE role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public ROLE getRole() {
		return role;
	}
	
}