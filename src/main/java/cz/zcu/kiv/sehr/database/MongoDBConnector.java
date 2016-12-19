package cz.zcu.kiv.sehr.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by ondraprazak on 19.12.16.
 */
public class MongoDBConnector {

    public static void addDocument(Document document)
    {
        MongoClient mongoClient = new MongoClient("147.228.127.146");
        MongoDatabase db = mongoClient.getDatabase("sehr");
        MongoCollection<Document> definitions = db.getCollection("definitions");
        definitions.insertOne(document);
    }

}
