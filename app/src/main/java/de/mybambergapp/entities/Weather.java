package de.mybambergapp.entities;

/**
 * Created by christian on 10.06.16.
 */
public class Weather {

    private Long id;



    private String weathername;

    public Weather(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeathername() {
        return weathername;
    }

    public void setWeathername(String weathername) {
        this.weathername = weathername;
    }
}
