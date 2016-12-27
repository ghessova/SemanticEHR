package cz.zcu.kiv.sehr.database;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

/**
 * DB connector interface for document-oriented databases.
 *
 * Created by ondraprazak on 19.12.16. *
 */
public interface DBConnector {

    /**
     * Adds single document to collection.
     *
     * @param collection collection name
     * @param document   - Document to be added
     * @return true when successfully added, false otherwise
     */
    boolean addDocument(Document document, String collection);

    /**
     * Attempts to delete document by id from a collections.
     *
     * @param id         inner document id
     * @param collection collection name
     * @return number of actually deleted documents
     */
    long removeDocumentById(String id, String collection);

    long removeDocumentByFieldValue(String field, Object value, String collection);

    /**
     * Attempts to delete document from a collections .
     *
     * @param query      json representing query parameters according to mongo query language
     * @param collection collection name
     * @return number of actually deleted documents
     */
    long removeDocument(Bson query, String collection);

    /**
     * Finds document by id.
     *
     * @param id         inner document id
     * @param collection collection name
     * @return document or null if not found
     */
    Document findDocumentById(String id, String collection);

    Document findDocumentByFieldValue(String field, Object value, String collection);

    /**
     * Finds documents by query.
     *
     * @param query      json representing query parameters according to mongo query language
     * @param collection collection name
     * @return list of documents
     */
    List<Document> findDocuments(Bson query, String collection, int skip, int limit);

    /**
     *
     * @param id
     * @param collection
     * @param updatedFields
     * @return
     */
    long updateDocument(String id, String collection, Bson updatedFields);

}