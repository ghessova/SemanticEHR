package cz.zcu.kiv.sehr.model;

import org.bson.Document;

/**
 * Response used by AuthenticationResource on login
 *
 */
public class AuthenticationResponse {

    String accessToken;

    Document user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Document getUser() {
        return user;
    }

    public void setUser(Document user) {
        this.user = user;
    }
}
