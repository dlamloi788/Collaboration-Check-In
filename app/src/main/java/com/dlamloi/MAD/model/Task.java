package com.dlamloi.MAD.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Don on 8/05/2018.
 */

public class Task implements Parcelable{

    private String mId;
    private String mTitle;
    private String mDetail;
    private String mStatus; //The status of the task. Incomplete/Complete/Overdue
    private String mAssignedMember; //The email of the assigned member
    private String mDueDate;


    protected Task(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mDetail = in.readString();
        mStatus = in.readString();
        mAssignedMember = in.readString();
        mDueDate = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mDetail);
        dest.writeString(mStatus);
        dest.writeString(mAssignedMember);
        dest.writeString(mDueDate);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getAssignedMember() {
        return mAssignedMember;
    }

    public void setAssignedMember(String assignedMember) {
        mAssignedMember = assignedMember;
    }

    public String getDueDate() {
        return mDueDate;
    }

    public void setDueDate(String dueDate) {
        mDueDate = dueDate;
    }

    public static Creator<Task> getCREATOR() {
        return CREATOR;
    }
}

