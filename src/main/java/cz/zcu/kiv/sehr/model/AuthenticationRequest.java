package cz.zcu.kiv.sehr.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Crate containing login credentials
 *
 */
@XmlRootElement
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
