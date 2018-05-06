package com.dlamloi.MAD.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Don on 20/04/2018.
 */

public class Update implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Update createFromParcel(Parcel source) {
            return new Update(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Update[size];
        }
    };

    private String title;
    private String date;
    private String details;
    private String publisher;

    public Update() {

    }

    public Update(String title, String date, String details, String publisher) {
        this.title = title;
        this.date = date;
        this.details = details;
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    public Update (Parcel source) {
        this.title = source.readString();
        this.date = source.readString();
        this.details = source.readString();
        this.publisher = source.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.details);
        dest.writeString(this.publisher);

    }
}
