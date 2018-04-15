package com.dlamloi.MAD.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Don on 11/04/2018.
 */

public class Group {

    private String name;
    private String displayImage;
    private String adminEmail;
    private List<String> memberEmails;



    public Group() {

    }

    public Group(String name, String adminEmail, List<String> memberEmails) {
        this.name = name;
        this.adminEmail = adminEmail;
        this.memberEmails = memberEmails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayImage() {
        return displayImage;
    }

    public void setDisplayImage(String displayImage) {
        this.displayImage = displayImage;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public List<String> getMemberEmails() { return memberEmails; }




}



