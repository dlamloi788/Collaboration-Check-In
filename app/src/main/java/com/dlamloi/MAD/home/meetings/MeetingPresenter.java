package com.dlamloi.MAD.home.meetings;

import com.dlamloi.MAD.model.Meeting;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

public class MeetingPresenter implements MeetingContract.Presenter {

    private final MeetingContract.View mView;
    private DatabaseReference mDatabaseReference;
    private ArrayList<Meeting> mMeetings = new ArrayList<>();




    public MeetingPresenter(MeetingContract.View view, String groupId) {
        mView = view;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("groups").child(groupId).child("meetings");
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Meeting meeting = dataSnapshot.getValue(Meeting.class);
                mMeetings.add(meeting);
                mView.notifyItemInserted(mMeetings.size());
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


    @Override
    public void loadAdapterData() {
        mView.setRecyclerViewData(mMeetings);
    }
}
