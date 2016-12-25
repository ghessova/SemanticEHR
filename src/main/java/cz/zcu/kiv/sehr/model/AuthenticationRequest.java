package cz.zcu.kiv.sehr.model;

/**
 * Crate containing login credentials
 *
 */
public class AuthenticationRequest {

    /** Username */
    private String username;

    /** Password */
    private String password;

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }
}
