package com.dlamloi.MAD.home.meetings;

import android.util.Log;

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

public class MeetingPresenter implements MeetingContract.Presenter, MeetingContract.OnMeetingListener {

    private final MeetingContract.View mView;
    private MeetingInteractor mMeetingInteractor;


    public MeetingPresenter(MeetingContract.View view, String groupId) {
        mView = view;
        Log.d("MeetingPresenter", "Im being called...");
        mMeetingInteractor = new MeetingInteractor(this, groupId);
        mMeetingInteractor.onAttach();
    }


    @Override
    public void onMeetingAdd(ArrayList<Meeting> meetings) {
        mView.populateRecyclerView(meetings);
    }
}
