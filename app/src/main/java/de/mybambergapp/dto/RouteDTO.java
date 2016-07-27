package de.mybambergapp.dto;

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
