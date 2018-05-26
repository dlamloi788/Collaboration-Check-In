package com.dlamloi.MAD.model;

/**
 * Created by Don on 24/05/2018.
 */

public class File {

    private String name;
    private String uri;

    public File() {
        //Empty constructor required for firebase
    }

    public File(String name, String uri) {
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
