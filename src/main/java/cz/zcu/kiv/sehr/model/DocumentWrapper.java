package cz.zcu.kiv.sehr.model;

import org.bson.Document;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created by ondraprazak on 20.12.16.
 */
@XmlRootElement
public class DocumentWrapper {

    Date created;

    String userId;

    String archetypeId;

    String name;

    List<String> sharedWith;

    Document data;

    public DocumentWrapper(Date created, String userId, String archetypeId, String name, List<String> sharedWith) {
        this.created = created;
        this.userId = userId;
        this.archetypeId = archetypeId;
        this.name = name;
        this.sharedWith = sharedWith;
    }

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

    public List<String> getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(List<String> sharedWith) {
        this.sharedWith = sharedWith;
    }
}
