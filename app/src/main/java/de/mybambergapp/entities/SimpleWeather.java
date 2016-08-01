package de.mybambergapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by christian on 01.08.16.
 */

public class SimpleWeather {

    private Long id;


    private String name;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
