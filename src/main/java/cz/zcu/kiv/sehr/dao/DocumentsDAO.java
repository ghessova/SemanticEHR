package cz.zcu.kiv.sehr.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import cz.zcu.kiv.sehr.model.DocumentWrapper;
import cz.zcu.kiv.sehr.model.Role;
import cz.zcu.kiv.sehr.model.UserWrapper;
import cz.zcu.kiv.sehr.utils.Config;
import cz.zcu.kiv.sehr.utils.PagingParams;
import cz.zcu.kiv.sehr.utils.Utils;
import org.bson.Document;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
     * @param user
     * @param pagingParams
     * @return
     */
    public List<Document> findDocuments(String archetypeId, UserWrapper user, PagingParams pagingParams) {
        BasicDBObject query = new BasicDBObject();
        if (!Utils.isEmpty(archetypeId)) query.put("archetypeId", archetypeId);
        if (user != null && user.getRole() != Role.ADMIN) query.put("username", user.getUsername());
        return Config.getDBC().findDocuments(query, Config.DOCUMENTS,  pagingParams.skip, pagingParams.limit);
    }

    public List<Document> findDocumentsByFieldValue(String field, Object value, PagingParams pagingParams)
    {
        BasicDBObject query = new BasicDBObject(field, value);
        return Config.getDBC().findDocuments(query, Config.DOCUMENTS, pagingParams.skip, pagingParams.limit);
    }


    public long deleteDocumentById(String id) {

        return Config.getDBC().removeDocumentById(id, Config.DOCUMENTS);

    }

    public int insertDocument(UserWrapper user, String documentName, String archetypeId, Document document) {
        Document record = new Document();
        record.append("username", user.getUsername());
        record.append("name", documentName);
        record.append("archetypeId", archetypeId);
        record.append("datetime", new Date().toString()); //todo date format
        record.append("sharedWith", new ArrayList<String>());
        record.append("data", document);
        return Config.getDBC().addDocument(record, Config.DOCUMENTS) ? 1 : 0;

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

    public List<DocumentWrapper> listDocuments(String archetypeId, UserWrapper user, PagingParams pagingParams)
    {
        List<Document> documents = this.findDocuments(archetypeId, user, pagingParams);
        return documents.stream().map(document -> new DocumentWrapper(document.getDate("datetime"),
                document.getString("username"), document.getString("archetypeId"), document.getString("name"),
                (ArrayList<String>)document.get("sharedWith")))
                .collect(Collectors.toList());
    }

    public List<Document> searchDocuments(String keyword, PagingParams pagingParams)
    {
        BasicDBObject query = new BasicDBObject("$text", new BasicDBObject("$search", keyword));
        return Config.getDBC().findDocuments(query, Config.DOCUMENTS,  pagingParams.skip, pagingParams.limit);
    }
}
