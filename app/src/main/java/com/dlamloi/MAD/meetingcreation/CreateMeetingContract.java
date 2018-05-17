package com.dlamloi.MAD.meetingcreation;

import android.content.Intent;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * Created by Don on 17/05/2018.
 */

public interface CreateMeetingContract {

    interface View {

        void startShowLocation(PlacePicker.IntentBuilder builder) throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException;

        void setPlaceText(Intent data);

        void showDateCalendar();

        void meetingPublished();
    }

    interface Presenter {


        void selectLocation();

        void result(int requestCode, int resultCode, Intent data);

        void meetingTimeClick();

        void createMeeting(String meetingTitle, String meetingDate, String meetingTime, String meetingLocation, String meetingAgenda);
    }

}
