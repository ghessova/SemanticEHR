package cz.zcu.kiv.sehr.model;

import org.bson.Document;

/**
 * Response used by AuthenticationResource on login
 *
 */
public class AuthenticationResponse {

    String accessToken;

    UserWrapper user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserWrapper getUser() {
        return user;
    }

    public void setUser(UserWrapper user) {
        this.user = user;
    }
}
