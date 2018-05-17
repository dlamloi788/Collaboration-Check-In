package com.dlamloi.MAD.meetingcreation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.dlamloi.MAD.model.Meeting;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Don on 17/05/2018.
 */

public class CreateMeetingPresenter implements CreateMeetingContract.Presenter {

    private final CreateMeetingContract.View mView;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;



    public CreateMeetingPresenter(CreateMeetingContract.View view, String groupId) {
        mView = view;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("groups").child(groupId).child("meetings");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void selectLocation() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            mView.startShowLocation(builder);
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CreateMeetingActivity.PLACE_PICKER_REQUEST:
                    mView.setPlaceText(data);


            }
        }
    }

    @Override
    public void meetingTimeClick() {
        Calendar date = Calendar.getInstance();
        int hourOfDay = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        mView.showTimeDialog(hourOfDay, minute);
    }

    @Override
    public void timePicked(int hourOfDay, int minute) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
        date.set(Calendar.MINUTE, minute);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String time = timeFormat.format(date.getTime());
        mView.setTimeText(time);

    }

    @Override
    public void createMeeting(String meetingTitle, String meetingDate, String meetingTime, String meetingLocation, String meetingAgenda) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY");
        String dateToday = dateFormat.format(calendar.getTime());

        String id = mDatabaseReference.push().getKey();
        String meetingCreator = mUser.getDisplayName();
        Meeting meeting = new Meeting(id, mUser.getEmail(), dateToday ,meetingTitle, meetingDate, meetingTime,
                meetingLocation, meetingAgenda);
        mDatabaseReference.child(id).setValue(meeting);
        mView.meetingPublished();

    }

    @Override
    public void onBackPressed(AlertDialog leaveConfirmationDialog, String name, String date, String time, String location, String agenda) {
        if (areAllFieldsEmpty(name, date, time, location, agenda)) {
            mView.leave();
        } else {
            mView.showLeaveConfirmationDialog(leaveConfirmationDialog);
        }
    }

    @Override
    public void leaveDialogNoClick(AlertDialog leaveConfirmationDialog) {
        mView.hideLeaveDialog(leaveConfirmationDialog);
    }

    @Override
    public void meetingDateEtClicked() {
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        mView.showDateDialog(year, month, day);
    }

    @Override
    public void datePicked(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String date = dateFormat.format(calendar.getTime());
        mView.setMeetingDate(date);
    }



    private boolean areAllFieldsEmpty(String... texts) {
        for (String text : texts) {
            if (!text.isEmpty()) {
                return false;
            }
        }
        return true;
    }


}
