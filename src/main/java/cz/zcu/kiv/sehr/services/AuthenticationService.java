package cz.zcu.kiv.sehr.services;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.zcu.kiv.sehr.model.Role;
import cz.zcu.kiv.sehr.model.UserWrapper;
import org.bson.Document;

import cz.zcu.kiv.sehr.dao.UsersDAO;
import cz.zcu.kiv.sehr.utils.PagingParams;

import javax.jws.soap.SOAPBinding;

/**
 * Resvice managing users authentication and access token generating
 *
 */
public class AuthenticationService {

    /** Singleton */
    private static final AuthenticationService INSTANCE = new AuthenticationService();

    /** Accesstoken created to users */
    private Map<String, UserWrapper> token_map = null;

    /** Constructor */
    private AuthenticationService() {
        token_map = new HashMap<>();
    }

    /** Instance getter */
    public static AuthenticationService getInstance() { return INSTANCE; }

    /**
     * Performs authentication and access token creation
     *
     */
    public UserWrapper authenticate(String username, String password) throws Exception {
        List<Document> users = UsersDAO.getInstance().getUsers(new PagingParams(0, 0));

        for (Document user : users) {
            if ((username.equals(user.getString("username"))) && (password.equals(user.getString("password")))) {
                UserWrapper loggedUser = new UserWrapper();
                loggedUser.setUsername(user.getString("username"));
                loggedUser.setPassword(user.getString("password"));
                loggedUser.setRole(Role.valueOf(user.getString("role")));
                loggedUser.setEmailAddress(user.getString("emailAddress"));
                // Return loggedUser
                return loggedUser;
            }
        }

        throw new RuntimeException("Invalid username or password");
    }

    /**
     * Create new token for user
     *
     */
    public String issueToken(UserWrapper user) {
        String token = null;

        // Generate UNIQUE secure random token
        do {
            SecureRandom rnd = new SecureRandom();
            token = new BigInteger(128, rnd).toString(32);
        } while (validateToken(token));

        // Insert token with user to map
        token_map.put(token, user);

        return token;
    }

    /**
     * Check if token is valid
     *
     */
    public boolean validateToken(String token) {
        return token_map.containsKey(token);
    }

    /**
     * Invalidate token if presented
     *
     */
    public boolean invalidateToken(String token) {
        return token_map.remove(token, findUser(token));
    }

    /**
     * Find user with requested token
     *
     */
    public UserWrapper findUser(String token) {
       if (!validateToken(token)) return null;

       return token_map.get(token);
    }
}
