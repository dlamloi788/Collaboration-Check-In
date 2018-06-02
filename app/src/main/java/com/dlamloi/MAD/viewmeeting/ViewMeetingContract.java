package com.dlamloi.MAD.viewmeeting;

import com.dlamloi.MAD.model.Meeting;

/**
 * Created by Don on 31/05/2018.
 */

/**
 * The contract between meeting view and the presenter
 */
public interface ViewMeetingContract {

    /**
     * The associated view in the contract
     */
    interface View {

        /**
         * Binds the data from the received data
         *
         * @param meetingTitle the meeting title
         * @param meetingTime the meeting time
         * @param meetingDate the meeting date
         * @param meetingLocation the meeting location
         * @param agenda the meeting agenda
         */
        void bindData(String meetingTitle, String meetingTime, String meetingDate, String meetingLocation, String agenda);

        /**
         * Finishes the activity
         */
        void leave();
    }

    /**
     * The associated view in the presenter
     */
    interface Presenter {

        /**
         * Retrieves the meeting data from the repository
         */
        void initData();

        /**
         * Binds the data to the activity upon receiving the data
         *
         * @param meeting the meeting that was retrieved
         */
        void onDataReceive(Meeting meeting);

        /**
         * Leaves the activity
         */
        void leave();
    }


}
