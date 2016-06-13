package de.mybambergapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by christian on 10.06.16.
 */
public class Tag {
    @JsonIgnore
    private Long ID;
    @JsonIgnore
    private String tagName;


    public Tag(){

    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
