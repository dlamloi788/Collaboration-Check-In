package com.dlamloi.MAD.viewmeeting;

import com.dlamloi.MAD.model.Meeting;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;

/**
 * Created by Don on 31/05/2018.
 */

/**
 * Handles the meeting view presentation logic
 */
public class ViewMeetingPresenter implements ViewMeetingContract.Presenter {

    private final ViewMeetingContract.View mView;
    private String mGroupId;
    private String mMeetingId;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;

    /**
     * Creates an instance of the view meeting presenter
     * @param view the view the presenter is moderating
     * @param groupId the id of the group the user is currently in
     * @param meetingId the meeting the user would like to view
     */
    public ViewMeetingPresenter(ViewMeetingContract.View view, String groupId, String meetingId) {
        mView = view;
        mGroupId = groupId;
        mMeetingId = meetingId;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager(mGroupId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initData() {
        mFirebaseRepositoryManager.setUpMeetingData(mMeetingId,this);
    }

    /**
     * Binds the data to the activity upon receiving the data
     *
     * @param meeting the meeting that was retrieved
     */
    public void onDataReceive(Meeting meeting) {
        mView.bindData(
                meeting.getMeetingTitle(),
                meeting.getMeetingTime(),
                meeting.getMeetingDate(),
                meeting.getMeetingLocation(),
                meeting.getAgenda()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leave() {
        mView.leave();
    }
}
