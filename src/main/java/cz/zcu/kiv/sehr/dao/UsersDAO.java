package cz.zcu.kiv.sehr.dao;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.Document;

import com.mongodb.BasicDBObject;

import cz.zcu.kiv.sehr.utils.Config;
import cz.zcu.kiv.sehr.utils.PagingParams;
import cz.zcu.kiv.sehr.model.UserWrapper;

/**
 * Access manager for Users
 *
 */
public class UsersDAO {

    Logger logger = LogManager.getLogger(UsersDAO.class);

    private static UsersDAO instance = new UsersDAO();

    private UsersDAO() {
        // Singleton
    }

    public static UsersDAO getInstance() {
        return instance;
    }

    public List<Document> getUsers(PagingParams pagingParams) {
        return Config.getDBC().findDocuments(new BasicDBObject(), Config.USERS,  pagingParams.skip, pagingParams.limit);
    }

    public int insertUser(UserWrapper user) {
        return Config.getDBC().addDocument(user.toDocument(), Config.USERS) ? 1 : 0;
    }

    public Document findUserById(String userId) {
        return Config.getDBC().findDocumentById(userId, Config.USERS);
    }

    public Document findUserByUsername(String username)
    {
        return Config.getDBC().findDocumentByFieldValue("username", username, Config.USERS);
    }

    public long deleteUserById(String userId) {
        return Config.getDBC().removeDocumentById(userId, Config.USERS);
    }

    public long deleteUserByName(String username)
    {
        return Config.getDBC().removeDocumentByFieldValue("username", username, Config.USERS);
    }
}
