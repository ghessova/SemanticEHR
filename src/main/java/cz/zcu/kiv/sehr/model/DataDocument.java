package cz.zcu.kiv.sehr.model;

import org.bson.Document;

import java.util.Date;

/**
 * Created by ondraprazak on 20.12.16.
 */
public class DataDocument {

    Date created;

    String userId;

    String archetypeId;

    String name;

    Document data;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArchetypeId() {
        return archetypeId;
    }

    public void setArchetypeId(String archetypeId) {
        this.archetypeId = archetypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Document getData() {
        return data;
    }

    public void setData(Document data) {
        this.data = data;
    }
}
