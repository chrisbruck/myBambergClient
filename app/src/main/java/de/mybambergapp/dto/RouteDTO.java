package de.mybambergapp.dto;

import java.util.List;

import de.mybambergapp.entities.Event;

/**
 * Created by christian on 13.06.16.
 */
public class RouteDTO {

    private Long id;

    private List<EventDTO> events;

    public RouteDTO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }
}
