package cz.zcu.kiv.sehr.model;

import org.bson.Document;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Object representing single user within aplication
 *
 */
@XmlRootElement
public class UserWrapper {

    String username;

    String password;

    String emailAddress;

    String role;

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

    public Document toDocument() {
        Document record = new Document();
        record.append("username", username);
        record.append("password", password);
        record.append("emailAddress", emailAddress);
        record.append("role", role);

        return record;
    }
}
