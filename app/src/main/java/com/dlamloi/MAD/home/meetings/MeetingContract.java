package com.dlamloi.MAD.home.meetings;

import com.dlamloi.MAD.base.BasePresenter;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Meeting;

/**
 * Created by Don on 16/05/2018.
 */

/**
 * This interface is the contract between the view and the presenter
 */
public interface MeetingContract {

    /**
     * The view associated with the contract
     */
    interface View extends BaseView<Meeting> {

    }

    /**
     * The presenter associated with the contract
     */
    interface Presenter extends BasePresenter {

    }

    /**
     * Listens for meetings being added to the database
     */
    interface MeetingListener {

        /**
         * Notifies the presenter that a meeting has been added to the database
         *
         * @param meeting the meeting that was recently added
         */
        void onMeetingAdd(Meeting meeting);

    }

    /**
     * Listens for meeting recyclerview row taps
     */
    interface MeetingItemClickListener {

        /**
         * Notifies the presenter that a meeting row was tapped
         *
         * @param meetingId the id of the meeting that was tapped
         */
        void meetingClick(String meetingId);
    }
}
