package com.dlamloi.MAD.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.dlamloi.MAD.meetingcreation.CreateMeetingPresenter;
import com.dlamloi.MAD.taskcreation.CreateTaskPresenter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Don on 8/05/2018.
 */

public class Meeting {

    private String mId;
    private String mCreatorEmail;
    private String mMeetingTitle;
    private String mMeetingDate; //In the form of DD-MONTH-YYYY
    private String mMeetingTime; //In the form of HH:MM am/pm
    private String mMeetingLocation;
    private String mAgenda; //Agenda info (if needed)

    public Meeting() {

    }

    /**
     * Use this constructor to a create an instance before passing to the RepositoryManager
     *
     * @param creatorEmail    the email of the meeting publisher
     * @param meetingTitle    the title of the meeting
     * @param meetingDate     the meeting date
     * @param meetingTime     the meeting time
     * @param meetingLocation the location of the meeting
     * @param agenda          the agenda of the meeting
     */
    public Meeting(String creatorEmail, String meetingTitle, String meetingDate, String meetingTime, String meetingLocation, String agenda) {
        mCreatorEmail = creatorEmail;
        mMeetingTitle = meetingTitle;
        mMeetingDate = meetingDate;
        mMeetingTime = meetingTime;
        mMeetingLocation = meetingLocation;
        mAgenda = agenda;
    }

    public Meeting(String id, String creatorEmail, String meetingTitle, String meetingDate, String meetingTime, String meetingLocation, String agenda) {
        this.mId = id;
        this.mCreatorEmail = creatorEmail;
        this.mMeetingTitle = meetingTitle;
        this.mMeetingDate = meetingDate;
        this.mMeetingTime = meetingTime;
        this.mMeetingLocation = meetingLocation;
        this.mAgenda = agenda;
    }

    public String getId() {

        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getCreatorEmail() {
        return mCreatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.mCreatorEmail = creatorEmail;
    }

    public String getMeetingLocation() {
        return mMeetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        mMeetingLocation = meetingLocation;
    }

    public String getMeetingTitle() {
        return mMeetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.mMeetingTitle = meetingTitle;
    }

    public String getMeetingDate() {
        return mMeetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.mMeetingDate = meetingDate;
    }

    public String getMeetingTime() {
        return mMeetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.mMeetingTime = meetingTime;
    }

    public String getAgenda() {
        return mAgenda;
    }

    public void setAgenda(String agenda) {
        this.mAgenda = agenda;
    }

    public static class MeetingComparator implements Comparator<Meeting> {
        @Override
        public int compare(Meeting o1, Meeting o2) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(CreateMeetingPresenter.DATE_PATTERN, Locale.ENGLISH);
            Date firstMeeting = null;
            Date secondMeeting = null;
            try {
                firstMeeting = dateFormat.parse(o1.getMeetingDate());
                secondMeeting = dateFormat.parse(o2.getMeetingDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return firstMeeting.compareTo(secondMeeting);

        };
    }

}


