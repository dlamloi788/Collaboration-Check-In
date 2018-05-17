package com.dlamloi.MAD.meetingcreation;

import android.app.Activity;
import android.content.Intent;

import com.dlamloi.MAD.model.Meeting;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        mView.showDateCalendar();
    }

    @Override
    public void createMeeting(String meetingTitle, String meetingDate, String meetingTime, String meetingLocation, String meetingAgenda) {
        String id = mDatabaseReference.push().getKey();
        String meetingCreator = mUser.getDisplayName();
        Meeting meeting = new Meeting(id, mUser.getEmail(), meetingTitle, meetingDate, meetingTime,
                meetingLocation, meetingAgenda);
        mDatabaseReference.child(id).setValue(meeting);
        mView.meetingPublished();

    }


}
