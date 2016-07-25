package de.mybambergapp.dto;

import java.util.List;

import de.mybambergapp.entities.Category;
import de.mybambergapp.entities.Event;

/**
 * Created by christian on 13.06.16.
 */
public class RouteDTO {



    private List<EventDTO> events;



    public RouteDTO(){

    }





    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }


}
