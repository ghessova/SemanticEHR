package cz.zcu.kiv.sehr.database;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.util.JSON;
import cz.zcu.kiv.sehr.utils.Config;
import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;

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


    @Override
    public void addDocument(Document document, String collection)
    {
        MongoCollection<Document> mongoCollection = getDatabase().getCollection(collection);
        mongoCollection.insertOne(document);
    }

    @Override
    public long removeDocumentById(String id, String collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        return removeDocument(query, collection);

    }

    @Override
    public long removeDocument(Bson query, String collection) {
        MongoCollection<Document> mongoCollection = getDatabase().getCollection(collection);
        DeleteResult result = mongoCollection.deleteOne(query);
        return result.getDeletedCount();
    }

    @Override
    public Document findDocumentById(String id, String collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        List<Document> results = findDocuments(query, collection);
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public List<Document> findDocuments(Bson query, String collection) {
        MongoCollection<Document> mongoCollection = getDatabase().getCollection(collection);
        FindIterable<Document> resultSet = mongoCollection.find();
        return Lists.newArrayList(resultSet);
    }
}
