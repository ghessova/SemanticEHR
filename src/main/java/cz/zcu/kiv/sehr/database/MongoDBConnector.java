package cz.zcu.kiv.sehr.database;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import cz.zcu.kiv.sehr.utils.Config;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.List;

/**
 * Singleton class managing DB connection
 *
 */
public class MongoDBConnector implements DBConnector {

    Logger logger = LogManager.getLogger(MongoDBConnector.class);


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
    private MongoDBConnector() {
        initialize();
    }

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
    public boolean addDocument(Document document, String collection)
    {
        try {
            MongoCollection<Document> mongoCollection = getDatabase().getCollection(collection);
            mongoCollection.insertOne(document);
            return true;
        } catch (Exception e) {
            logger.error("Error while adding document to collection " + collection  + ", document: " + document.toString() + ", exception:\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public long removeDocumentById(String id, String collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        return removeDocument(query, collection);

    }

    @Override
    public long removeDocument(Bson query, String collection) {

        try {
            MongoCollection<Document>  mongoCollection = getDatabase().getCollection(collection);
            DeleteResult result = mongoCollection.deleteOne(query);
            return result.getDeletedCount();
        } catch (Exception e) {
            logger.error("Could not remove document from collection " + collection  + ", parameters json: " + query.toString() + ", exception:\n" + e.getMessage());
            return 0;
        }

    }

    @Override
    public Document findDocumentById(String id, String collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        List<Document> results = findDocuments(query, collection, 0, 0);
        if (results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public List<Document> findDocuments(Bson query, String collection, int skip, int limit) {
        try {
            MongoCollection<Document> mongoCollection = getDatabase().getCollection(collection);
            FindIterable<Document> resultSet = mongoCollection.find(query);
            if (skip > 0) {
                resultSet = resultSet.skip(skip);
            }
            if (limit > 0) {
                resultSet = resultSet.limit(limit);
            }
            return Lists.newArrayList(resultSet);
        } catch (Exception e) {
            logger.error("Error while searching for documents in collection " + collection  + ", parameters json: " + query.toString() + ", exception:\n" + e.getMessage());
            return Collections.emptyList();
        }

    }

    @Override
    public long updateDocument(String id, String collection, Bson updatedFields) {
        MongoCollection<Document> mongoCollection = getDatabase().getCollection(collection);
        BasicDBObject searchQuery = new BasicDBObject().append("_id", new ObjectId(id));

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", updatedFields);

        UpdateResult result = mongoCollection.updateOne(searchQuery, newDocument);
        return result.getModifiedCount();
    }


    private void initialize() {
        //indices on collection definitions
        MongoCollection<Document> mongoCollection = getDatabase().getCollection(Config.DEFINITIONS);

        Document uniqueIndex = new Document("archetypeId", 3);
        mongoCollection.createIndex(uniqueIndex, new IndexOptions().unique(true)); //creates index if not exists, archetypeId must be unique

        Document textIndex = new Document("archetypeId", "text").append("name", "text"); //to allow fulltext search
        mongoCollection.createIndex(textIndex);


    }


}
