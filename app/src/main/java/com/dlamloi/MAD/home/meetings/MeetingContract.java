package com.dlamloi.MAD.home.meetings;

import com.dlamloi.MAD.base.BaseInteractor;
import com.dlamloi.MAD.base.BasePresenter;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Meeting;

/**
 * Created by Don on 16/05/2018.
 */

public interface MeetingContract {

    interface View extends BaseView<Meeting> {

    }

    interface Presenter extends BasePresenter {

    }

    interface MeetingListener {

        void onMeetingAdd(Meeting meeting);

    }

    interface MeetingItemClickListener {

        void meetingClick(String meetingId);
    }
}
