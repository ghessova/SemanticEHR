package cz.zcu.kiv.sehr.database;

import org.bson.Document;

/**
 * Created by ondraprazak on 19.12.16.
 */
public interface DBConnector {

    void addDocument(Document document);
}
