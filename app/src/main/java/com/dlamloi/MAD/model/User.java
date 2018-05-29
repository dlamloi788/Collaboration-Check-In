package com.dlamloi.MAD.model;

/**
 * Created by Don on 11/04/2018.
 */

public class User {

    private String mEmail;
    private String mDisplayName;

    public User() {
        //Empty constructor required for firebase
    }

    public User(String email, String displayName) {
        this.mEmail = email;
        this.mDisplayName = displayName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        this.mDisplayName = displayName;
    }
}
