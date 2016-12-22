package cz.zcu.kiv.sehr.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import cz.zcu.kiv.sehr.utils.Config;
import cz.zcu.kiv.sehr.utils.PagingParams;
import cz.zcu.kiv.sehr.utils.Utils;
import org.bson.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by ghessova on 22.12.16.
 */
public class DocumentsDAO {

    private static DocumentsDAO instance = new DocumentsDAO();

    private DocumentsDAO() {

    }

    public static DocumentsDAO getInstance() {
        return instance;
    }


    public Document findDocumentById(String id) {

        return Config.getDBC().findDocumentById(id, Config.DOCUMENTS);

    }

    /**
     *
     * @param archetypeId
     * @param userId
     * @param pagingParams
     * @return
     */
    public List<Document> findDocuments(String archetypeId, String userId, PagingParams pagingParams) {
        BasicDBObject query = new BasicDBObject();
        if (!Utils.isEmpty(archetypeId)) query.put("archetypeId", archetypeId);
        if (!Utils.isEmpty(userId)) query.put("userId", userId);
        return Config.getDBC().findDocuments(query, Config.DOCUMENTS,  pagingParams.skip, pagingParams.limit);
    }


    public long deleteDocumentById(String id) {

        return Config.getDBC().removeDocumentById(id, Config.DOCUMENTS);

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

    /**
     *
     * @param documentId document id
     * @param changed serialized JSON document contaning fields to update
     * @return
     */
    public long editDocument(String documentId, String changed) {
        BasicDBObject changedFieldsDoc; //deserialize
        try {
            changedFieldsDoc = (BasicDBObject) JSON.parse(changed);
        } catch (Exception e) {
           return 0;
        }
        return Config.getDBC().updateDocument(documentId, Config.DOCUMENTS, changedFieldsDoc);

    }
}
