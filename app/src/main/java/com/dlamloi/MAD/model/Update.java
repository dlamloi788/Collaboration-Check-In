package com.dlamloi.MAD.model;

/**
 * Created by Don on 20/04/2018.
 */

/**
 * This class wraps the update data into a class
 */
public class Update {

    private String id;
    private String title;
    private String date;
    private String details;
    private String publisher;

    public Update() {

    }

    /**
     * Use this constructor to create an update to pass into the RepositoryManager
     *
     * @param title     the title of the update
     * @param date      the publish date of the update
     * @param details   the update details
     * @param publisher the publisher of the update
     */
    public Update(String title, String date, String details, String publisher) {
        this.title = title;
        this.date = date;
        this.details = details;
        this.publisher = publisher;
    }


    public Update(String id, String title, String date, String details, String publisher) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.details = details;
        this.publisher = publisher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

}
