package com.dlamloi.MAD.model;

/**
 * Created by Don on 24/05/2018.
 */

/**
 * This class wraps a file that will be stored into the database
 */
public class CloudFile {

    private String name;
    private String uri;

    public CloudFile() {
        //Empty constructor required for firebase
    }

    public CloudFile(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
