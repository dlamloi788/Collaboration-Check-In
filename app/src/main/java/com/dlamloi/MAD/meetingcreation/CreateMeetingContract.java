package com.dlamloi.MAD.meetingcreation;

import android.content.Intent;
import android.support.v7.app.AlertDialog;

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

        void meetingPublished();

        void showLeaveConfirmationDialog(AlertDialog leaveConfirmationDialog);

        void hideLeaveDialog(AlertDialog leaveConfirmationDialog);

        void leave();

        void showDateDialog(int year, int month, int day);

        void showTimeDialog(int hour, int minute);

        void setMeetingDate(String date);

        void setTimeText(String time);

        void enableCreateButton();

        void disableCreateButton();
    }

    interface Presenter {


        void selectLocation();

        void result(int requestCode, int resultCode, Intent data);

        void meetingTimeClick();

        void createMeeting(String meetingTitle, String meetingDate, String meetingTime, String meetingLocation, String meetingAgenda);

        void onBackPressed(AlertDialog leaveConfirmationDialog, String s, String toString, String string, String s1, String toString1);
        
        void leaveDialogNoClick(AlertDialog leaveConfirmationDialog);

        void meetingDateEtClicked();

        void datePicked(int year, int month, int dayOfMonth);

        void timePicked(int hourOfDay, int minute);

        void shouldCreateBeEnabled(String s, String s1, String s2, String s3);
    }

}
