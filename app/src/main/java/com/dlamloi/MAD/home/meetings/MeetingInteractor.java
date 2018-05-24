package com.dlamloi.MAD.home.meetings;

import com.dlamloi.MAD.model.Meeting;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Don on 22/05/2018.
 */

public class MeetingInteractor implements MeetingContract.Interactor {


    private MeetingContract.OnMeetingListener mOnMeetingListener;
    private DatabaseReference mDatabaseReference;
    private ArrayList<Meeting> mMeetings = new ArrayList<>();


    public MeetingInteractor(MeetingContract.OnMeetingListener onMeetingListener, String groupId) {
        mOnMeetingListener = onMeetingListener;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("groups").child(groupId).child("meetings");
    }


    @Override
    public void onAttach() {
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Meeting meeting = dataSnapshot.getValue(Meeting.class);
                mMeetings.add(meeting);
                mOnMeetingListener.onMeetingAdd(mMeetings);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
