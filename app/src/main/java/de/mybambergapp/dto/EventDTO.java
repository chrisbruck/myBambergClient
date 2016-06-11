package de.mybambergapp.dto;

import java.util.Date;

import de.mybambergapp.entities.Tag;
import de.mybambergapp.entities.Weather;

/**
 * Created by christian on 10.06.16.
 */
public class EventDTO {
    private Long id;

    private String eventname;

    private Long locationId;

    private Tag tag;

    private Weather weather;

    private Date date;


    public EventDTO() {
    }


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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
