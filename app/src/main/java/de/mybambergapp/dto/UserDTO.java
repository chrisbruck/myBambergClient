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

    private String androidId;

    private Date startdate;

    private Date enddate;

    private List<Category> categoryList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
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
