package cz.zcu.kiv.sehr.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ondraprazak on 27.12.16.
 */
@XmlRootElement
public enum Role {

    ADMIN, USER;

    @JsonValue
    public String serialize()
    {
        return this.toString();
    }

    @JsonCreator
    public Role deserialize(String ser)
    {
        return Role.valueOf(ser);
    }
}
