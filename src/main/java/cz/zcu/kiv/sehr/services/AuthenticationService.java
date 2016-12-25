package cz.zcu.kiv.sehr.services;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import cz.zcu.kiv.sehr.model.UserWrapper;

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
        token_map = new HashMap<String, UserWrapper>();
    }

    /** Instance getter */
    public static AuthenticationService getInstance() { return INSTANCE; }

    /**
     * Performs authentication and access token creation
     *
     */
    public UserWrapper authenticate(String username, String password) throws Exception {
        // TODO Write authentication logic once there will be some default users

        return new UserWrapper();
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
        return token_map.remove(token) == null;
    }
}
