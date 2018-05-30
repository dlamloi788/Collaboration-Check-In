package com.dlamloi.MAD.viewmeeting;

import com.dlamloi.MAD.model.Meeting;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;

/**
 * Created by Don on 31/05/2018.
 */

public class ViewMeetingPresenter implements ViewMeetingContract.Presenter {

    private final ViewMeetingContract.View mView;
    private String mGroupId;
    private String mMeetingId;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;

    public ViewMeetingPresenter(ViewMeetingContract.View view, String groupId, String meetingId) {
        mView = view;
        mGroupId = groupId;
        mMeetingId = meetingId;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager(mGroupId);
    }


    @Override
    public void initData() {
        mFirebaseRepositoryManager.setUpMeetingData(mMeetingId,this);
    }

    public void onDataReceive(Meeting meeting) {
        mView.bindData(
                meeting.getMeetingTitle(),
                meeting.getMeetingTime(),
                meeting.getMeetingDate(),
                meeting.getMeetingLocation(),
                meeting.getAgenda()
        );
    }

    @Override
    public void leave() {
        mView.leave();
    }
}
