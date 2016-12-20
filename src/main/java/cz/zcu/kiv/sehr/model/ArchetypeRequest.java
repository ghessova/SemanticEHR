package cz.zcu.kiv.sehr.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by ondraprazak on 20.12.16.
 */
@XmlRootElement
public class ArchetypeRequest {

    String userId;

    Date requestDate;

    String archetypeId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getArchetypeId() {
        return archetypeId;
    }

    public void setArchetypeId(String archetypeId) {
        this.archetypeId = archetypeId;
    }
}
