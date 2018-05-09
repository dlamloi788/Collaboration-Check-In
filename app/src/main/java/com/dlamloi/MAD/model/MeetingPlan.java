package com.dlamloi.MAD.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Don on 8/05/2018.
 */

public class MeetingPlan implements Parcelable {

    private String id;
    private String creatorEmail;
    private String meetingTitle;
    private String meetingDate; //In the form of DD-MONTH-YYYY
    private String meetingTime; //In the form of HH:MM AM/PM
    private String agenda; //Agenda info (if needed)
    private int duration; //Duration expressed in minutes (

    public MeetingPlan() {

    }


    public MeetingPlan(String id, String creatorEmail, String meetingTitle, String meetingDate, String meetingTime, String agenda, int duration) {
        this.id = id;
        this.creatorEmail = creatorEmail;
        this.meetingTitle = meetingTitle;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.agenda = agenda;
        this.duration = duration;
    }

    protected MeetingPlan(Parcel in) {
        id = in.readString();
        creatorEmail = in.readString();
        meetingTitle = in.readString();
        meetingDate = in.readString();
        meetingTime = in.readString();
        agenda = in.readString();
        duration = in.readInt();
    }

    public static final Creator<MeetingPlan> CREATOR = new Creator<MeetingPlan>() {
        @Override
        public MeetingPlan createFromParcel(Parcel in) {
            return new MeetingPlan(in);
        }

        @Override
        public MeetingPlan[] newArray(int size) {
            return new MeetingPlan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(creatorEmail);
        dest.writeString(meetingTitle);
        dest.writeString(meetingDate);
        dest.writeString(meetingTime);
        dest.writeString(agenda);
        dest.writeInt(duration);
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static Creator<MeetingPlan> getCREATOR() {
        return CREATOR;
    }
}
