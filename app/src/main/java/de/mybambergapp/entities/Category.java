package de.mybambergapp.entities;

/**
 * Created by christian on 25.07.16.
 */
public class Category {


    private Long id;


    private String categoryname;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
