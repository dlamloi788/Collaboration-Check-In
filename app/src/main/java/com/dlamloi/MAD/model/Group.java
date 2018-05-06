package com.dlamloi.MAD.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Don on 11/04/2018.
 */

public class Group implements Parcelable {


    private String id;
    private String name;
    private String adminEmail;
    private ArrayList<String> memberEmails;
    private ArrayList<Update> updates = new ArrayList<>();


    public Group() {

    }

    public Group(String id, String name, String adminEmail, ArrayList<String> memberEmails) {
        this.id = id;
        this.name = name;
        this.adminEmail = adminEmail;
        this.memberEmails = memberEmails;
    }

    public Group(Parcel in) {
        id = in.readString();
        name = in.readString();
        adminEmail = in.readString();
        memberEmails = in.createStringArrayList();
        updates = in.createTypedArrayList(Update.CREATOR);
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public List<String> getMemberEmails() { return memberEmails; }

    public void setMemberEmails(ArrayList<String> memberEmails) {
        this.memberEmails = memberEmails;
    }

    public ArrayList<Update> getUpdates() {
        return updates;
    }

    public void setUpdates(ArrayList<Update> updates) {
        this.updates = updates;
    }

    @Override
    public String toString() {
        return name + " - " + id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(adminEmail);
        dest.writeStringList(memberEmails);
        dest.writeTypedList(updates);
    }
}



