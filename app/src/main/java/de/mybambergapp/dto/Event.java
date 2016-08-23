package de.mybambergapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

import de.mybambergapp.entities.Category;
import de.mybambergapp.entities.Location;
import de.mybambergapp.entities.SimpleWeather;
import de.mybambergapp.entities.Tag;

/**
 * Created by christian on 27.07.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    private Long id;
    private String eventname;

    private Location location;

    private String description;

    private Category category;


    private List<Tag> taglist;


    private Date enddate;

    private Date startdate;

    private boolean valid;



    private SimpleWeather simpleWeather;


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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Tag> getTaglist() {
        return taglist;
    }

    public void setTaglist(List<Tag> taglist) {
        this.taglist = taglist;
    }

    public SimpleWeather getSimpleWeather() {
        return simpleWeather;
    }

    public void setSimpleWeather(SimpleWeather simpleWeather) {
        this.simpleWeather = simpleWeather;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
