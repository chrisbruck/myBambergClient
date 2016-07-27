package de.mybambergapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

import de.mybambergapp.entities.Category;

/**
 * Created by christian on 27.07.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    private Long id;


    private String eventname;
    private String description;

    private Category category;

    private Date startdate;

    private Date enddate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
}
