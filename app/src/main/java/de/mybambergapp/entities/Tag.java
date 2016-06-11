package de.mybambergapp.entities;

/**
 * Created by christian on 10.06.16.
 */
public class Tag {

    private Long ID;
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
