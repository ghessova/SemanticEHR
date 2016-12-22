package cz.zcu.kiv.sehr.model;

/**
 * Object representing single user within aplication
 *
 */
public class UserWrapper {

    String userId;

    String username;

    String password;

    String emailAddress;

    String role;

    public void setUserId(String userId) {
        this.userId = userId;
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

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
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

    public String getRole() {
        return role;
    }
}
