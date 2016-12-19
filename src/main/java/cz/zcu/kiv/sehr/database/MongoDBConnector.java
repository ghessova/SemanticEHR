package cz.zcu.kiv.sehr.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cz.zcu.kiv.sehr.utils.Config;
import org.bson.Document;

/**
 * Singleton class managing DB connection
 *
 */
public class MongoDBConnector implements DBConnector {

    /** Singleton instance */
    private static final DBConnector INSTANCE = new MongoDBConnector();

    /** IP address for MongoDB client */
    private final String IP_ADDRESS = Config.getDBAddress();

    /** Database name */
    private final String DB_NAME = Config.getDBName();

    /** Connection to database server */
    private MongoClient connection = null;

    /**
     * Singleton instance obtainer
     */
    public static DBConnector getInstance() {
        return INSTANCE;
    }

    /**
     * Private constructor
     *
     */
    private MongoDBConnector() { /* */ }

    /**
     * Get connection to database server
     *
     * @return Connection to DB server
     */
    private MongoClient getConnection() {
        if (connection == null)
            connection = new MongoClient(IP_ADDRESS);

        return connection;
    }

    /**
    * Get connection to database
    *
    * @return Connection to DB
    */
    private MongoDatabase getDatabase() {
        return getConnection().getDatabase(DB_NAME);
    }

    /**
     * Terminate conection to database
     *
     */
    public void terminateConnection() {
        if(connection != null)
            connection.close();
    }

    /**
     * Add single document to collection
     *
     * @param document - Document to be added
     */
    @Override
    public void addDocument(Document document)
    {
        MongoCollection<Document> definitions = getDatabase().getCollection("definitions");
        definitions.insertOne(document);
    }

}
