package com.dlamloi.MAD.home.meetings;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dlamloi.MAD.model.Meeting;
import com.dlamloi.MAD.firebasemanager.FirebaseCallbackManager;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

/**
 * The class handles the UI logic for view and listens for meetings being added to the database
 */
public class MeetingPresenter implements MeetingContract.Presenter, MeetingContract.MeetingListener {

    public static final String MEETING_PRESENTER = "MeetingPresenter";

    private final MeetingContract.View mView;
    private FirebaseCallbackManager mFirebaseCallbackManager;
    private ArrayList<Meeting> mMeetings = new ArrayList<>();

    /**
     * Creates an instance of the meeting presenter
     * @param view the view that the presenter is moderating
     * @param groupId the id of the group that the user is currently in
     */
    public MeetingPresenter(MeetingContract.View view, String groupId) {
        mView = view;
        mFirebaseCallbackManager = new FirebaseCallbackManager(groupId);
        mFirebaseCallbackManager.attachMeetingsListener(this);
        Log.d(MEETING_PRESENTER, "Im being called...");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMeetingAdd(Meeting meeting) {
        mMeetings.add(meeting);
        mView.populateRecyclerView(mMeetings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scrollStateChanged(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
                mView.showFab();
                break;

            default:
                mView.hideFab();
                break;
        }
    }
}
