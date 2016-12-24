package cz.zcu.kiv.sehr.dao;

import com.mongodb.BasicDBObject;
import cz.zcu.kiv.sehr.utils.Config;
import cz.zcu.kiv.sehr.utils.PagingParams;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by ghessova on 20.12.16.
 */
public class ArchetypesDAO {

    Logger logger = LogManager.getLogger(ArchetypesDAO.class);

    private static ArchetypesDAO instance = new ArchetypesDAO();

    private ArchetypesDAO() {

    }

    public static ArchetypesDAO getInstance() {
        return instance;
    }


    public long deleteArchetypeById(String id) {

        return Config.getDBC().removeDocumentById(id, Config.DEFINITIONS);

    }

    public long deleteRequestById(String id) {

        return Config.getDBC().removeDocumentById(id, Config.REQUESTS);

    }



    public int insertArchetype(Document document) {
        return Config.getDBC().addDocument(document, Config.DEFINITIONS) ? 1 : 0;
    }



    public int insertRequest(String userId, String archetypeId) {
        Document record = new Document();
        record.append("userId", userId);
        record.append("archetypeId", archetypeId);
        record.append("datetime", new Date().toString()); //todo date format
        return Config.getDBC().addDocument(record, Config.REQUESTS) ? 1 : 0;
    }

    public Document findArchetypeById(String archetypeId) {
        return Config.getDBC().findDocumentById(archetypeId, Config.DEFINITIONS);
    }


    public List<Document> getArchetypes(PagingParams pagingParams) {
        return Config.getDBC().findDocuments(new BasicDBObject(), Config.DEFINITIONS,  pagingParams.skip, pagingParams.limit);
    }

    public List<Document> getRequests(PagingParams pagingParams, String userId) {
        BasicDBObject query = new BasicDBObject("userId",userId);
        return Config.getDBC().findDocuments(query, Config.REQUESTS,  pagingParams.skip, pagingParams.limit);
    }

    public List<Document> getRequests(PagingParams pagingParams) {
        return Config.getDBC().findDocuments(new BasicDBObject(), Config.REQUESTS,  pagingParams.skip, pagingParams.limit);
    }

    public List<Document> searchForArchetypes(String keyword, PagingParams pagingParams) {
        BasicDBObject query = new BasicDBObject("$text", new BasicDBObject("$search", keyword));
        return Config.getDBC().findDocuments(query, Config.DEFINITIONS,  pagingParams.skip, pagingParams.limit);
    }

}
