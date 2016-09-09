package de.mybambergapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import de.mybambergapp.entities.Category;
import de.mybambergapp.dto.Event;

/**
 * Created by christian on 13.06.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteDTO {


    private List<Event> eventList;
    private String androidId ;

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }


    @Override
    public String toString() {
        return "RouteDTO{" +
                "eventList=" + eventList +
                '}';
    }


}
