package de.mybambergapp.dto;

/**
 * Created by christian on 23.07.16.
 */

import java.util.Date;
import java.util.List;

import de.mybambergapp.entities.Category;


/**
 * todo: = PrefDTO nur das es von Server kommt
 */
public class UserDTO {


    private Long id;

    private String androidID;

    private Date startdate;

    private Date enddate;

    private List<Category> categoryList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAndroidID() {
        return androidID;
    }

    public void setAndroidID(String androidID) {
        this.androidID = androidID;
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

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }




}
