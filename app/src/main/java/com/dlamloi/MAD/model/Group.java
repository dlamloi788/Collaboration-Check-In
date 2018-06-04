package com.dlamloi.MAD.model;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Don on 11/04/2018.
 */

/**
 * This class encapsulates the group data into an object
 */
public class Group {


    private String id;
    private String name;
    private String adminEmail;
    private ArrayList<String> memberEmails;
    private HashMap<String, Update> updates = new HashMap<>();
    private HashMap<String, Meeting> meetingPlans = new HashMap<>();
    private ArrayList<String> fileUris = new ArrayList<>();
    private HashMap<String, Task> tasks = new HashMap<>();
    private HashMap<String, ChatMessage> chatMessages = new HashMap<>();


    public Group() {

    }

    /**
     * Use this constructor to create a new task
     *
     * @param name         the group name
     * @param adminEmail   the group administrators email
     * @param memberEmails the members of the group
     */
    public Group(String name, String adminEmail, ArrayList<String> memberEmails) {
        this.name = name;
        this.adminEmail = adminEmail;
        this.memberEmails = memberEmails;
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
        in.readMap(updates, Update.class.getClassLoader());
        in.readMap(meetingPlans, Meeting.class.getClassLoader());
        fileUris = in.createStringArrayList();
        in.readMap(tasks, Task.class.getClassLoader());
        in.readMap(chatMessages, ChatMessage.class.getClassLoader());

    }

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

    public ArrayList<String> getMemberEmails() {
        return memberEmails;
    }

    public void setMemberEmails(ArrayList<String> memberEmails) {
        this.memberEmails = memberEmails;
    }

    public HashMap<String, Update> getUpdates() {
        return updates;
    }

    public void setUpdates(HashMap<String, Update> updates) {
        this.updates = updates;
    }

    public HashMap<String, Meeting> getMeetingPlans() {
        return meetingPlans;
    }

    public void setMeetingPlans(HashMap<String, Meeting> meetingPlans) {
        this.meetingPlans = meetingPlans;
    }

    public ArrayList<String> getFileUris() {
        return fileUris;
    }

    public void setFileUris(ArrayList<String> fileUris) {
        this.fileUris = fileUris;
    }

    public HashMap<String, Task> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<String, Task> tasks) {
        this.tasks = tasks;
    }

    public HashMap<String, ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(HashMap<String, ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

}



