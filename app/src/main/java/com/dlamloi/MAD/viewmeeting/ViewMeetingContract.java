package com.dlamloi.MAD.viewmeeting;

import com.dlamloi.MAD.model.Meeting;

/**
 * Created by Don on 31/05/2018.
 */

public interface ViewMeetingContract {

    interface View {

        void bindData(String meetingTime, String meetingTime1, String meetingDate, String meetingLocation, String agenda);

        void leave();
    }

    interface Presenter {

        void initData();

        void onDataReceive(Meeting meeting);

        void leave();
    }


}
