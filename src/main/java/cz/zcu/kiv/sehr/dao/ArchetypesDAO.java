package cz.zcu.kiv.sehr.dao;

import com.mongodb.BasicDBObject;
import cz.zcu.kiv.sehr.utils.Config;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.diagnostics.Loggers;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

/**
 * Created by ghessova on 20.12.16.
 */
public class ArchetypesDAO {

    Logger logger = LogManager.getLogger(ArchetypesDAO.class);



    public long deleteArchetypeById(String id) {

        return Config.getDBC().removeDocumentById(id, Config.DEFINITIONS);

    }

    public long deleteRequestById(String id) {

        return Config.getDBC().removeDocumentById(id, Config.REQUESTS);

    }

    public long deleteDocumentById(String id) {

        return Config.getDBC().removeDocumentById(id, Config.DOCUMENTS);

    }

    public int insertArchetype(Document document) {

        return Config.getDBC().addDocument(document, Config.DEFINITIONS) ? 1 : 0;

    }

    public int insertDocument(String userId, String documentName, String archetypeId, Document document) {
        Document record = new Document();
        record.append("userId", userId);
        record.append("name", documentName);
        record.append("archetypeId", archetypeId);
        record.append("datetime", new Date().toString()); //todo date format
        record.append("data", document);
        return Config.getDBC().addDocument(record, Config.DEFINITIONS) ? 1 : 0;

    }

    public int insertRequest(String userId, String archetypeId) {
        Document record = new Document();
        record.append("userId", userId);
        record.append("archetypeId", archetypeId);
        record.append("datetime", new Date().toString()); //todo date format
        return Config.getDBC().addDocument(record, Config.DEFINITIONS) ? 1 : 0;
    }

    public Document findArchetypeById(String archetypeId) {
        return Config.getDBC().findDocumentById(archetypeId, Config.DEFINITIONS);
    }

    public Document findDocumentById(String documentId) {
        return Config.getDBC().findDocumentById(documentId, Config.DOCUMENTS);
    }

    public List<Document> findArchetypes(String keyword, int skip, int limit) {
        Bson query;
        if (keyword == null || "".equals(keyword)) {
            query = new Document();
        }
        else {
            query= new BasicDBObject("$text", new BasicDBObject("$search", keyword));
        }
        return Config.getDBC().findDocuments(query, Config.DEFINITIONS,  skip, limit);
    }

}
