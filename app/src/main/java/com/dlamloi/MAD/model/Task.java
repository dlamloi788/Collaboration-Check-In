package com.dlamloi.MAD.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Don on 8/05/2018.
 */

public class Task {

    private String mId;
    private String mTitle;
    private String mDetail;
    private String mStatus; //The status of the task. Pending/Complete/Overdue
    private String mAssignedMember; //The email of the assigned member
    private String mDueDate;


    //Empty constructor is a requirement for using firebase realtime database
    public Task() {

    }

    /**
     * Use this constructor to create a task before adding via the RepositoryManager
     *
     * @param title          the title of the task
     * @param detail         the details behind the task
     * @param status         the completion status of task
     * @param assignedMember the member assigned to the task
     * @param dueDate        the date the task is to be complete by
     */
    public Task(String title, String detail, String status, String assignedMember, String dueDate) {
        mTitle = title;
        mDetail = detail;
        mStatus = status;
        mAssignedMember = assignedMember;
        mDueDate = dueDate;
    }

    public Task(String id, String title, String detail, String status, String assignedMember, String dueDate) {
        mId = id;
        mTitle = title;
        mDetail = detail;
        mStatus = status;
        mAssignedMember = assignedMember;
        mDueDate = dueDate;
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

}

