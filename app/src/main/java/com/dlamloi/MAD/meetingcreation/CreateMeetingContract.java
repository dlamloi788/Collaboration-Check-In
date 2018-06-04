package com.dlamloi.MAD.meetingcreation;

import android.content.Intent;

/**
 * Created by Don on 17/05/2018.
 */

/**
 * The contract between the create meeting view and presenter
 */
public interface CreateMeetingContract {

    /**
     * The associated view in the contract
     */
    interface View {

        /**
         * Sets the place text from the data inside the intent
         *
         * @param data the intent with the selected location
         */
        void setPlaceText(Intent data);

        /**
         * Displays a leave dialog confirming the users departure
         */
        void showLeaveConfirmationDialog();

        /**
         * Leaves the meeting creation activity
         */
        void leave();

        /**
         * Shows the date dialog
         */
        void showDateDialog();

        /**
         * Shows the time dialog
         */
        void showTimeDialog();

        /**
         * Sets the meeting date edittext to the selected date
         *
         * @param date the selected date
         */
        void setMeetingDate(String date);

        /**
         * Sets the meeting time edittext to the selected time
         *
         * @param time the selected time
         */
        void setMeetingTime(String time);

        /**
         * Enables the create menu button
         */
        void enableCreateButton();

        /**
         * Disables the create menu button
         */
        void disableCreateButton();

        /**
         * Shows the bottom sheet dialog
         */
        void showBottomSheetDialog();

        /**
         * Starts the google places picker
         */
        void startSelectLocation();

        /**
         * Sets up the date picker dialog
         *
         * @param year the current year
         * @param month the current month
         * @param day the current day
         */
        void setUpDatePickerDialog(int year, int month, int day);

        /**
         * Sets up the time picker dialog
         *
         * @param hour the current hour
         * @param minute the current minute
         */
        void setUpTimePickerDialog(int hour, int minute);
    }

    /**
     * The associated presenter in the contract
     */
    interface Presenter {

        /**
         * Starts either the place picker or shows a bottom sheet dialog depending if the text in the meeting location
         * edittext is empty
         *
         * @param location the text in the meeting edittext
         */
        void selectLocation(String location);

        /**
         * Called on result returned by an activity
         *
         * @param requestCode the requestcode of the activity
         * @param resultCode  the result status
         * @param data        the intent that contains data
         */
        void result(int requestCode, int resultCode, Intent data);

        /**
         * Calls the view to display the time dialog
         */
        void meetingTimeClick();

        /**
         * Creates a meeting object and calls the repository manager to store the object into the database
         *
         * @param meetingTitle    the title of the meeting
         * @param meetingDate     the date of the meeting
         * @param meetingTime     the time of the meeting
         * @param meetingLocation the location of the meeting
         * @param meetingAgenda   the agenda of the meeting
         */
        void createMeeting(String meetingTitle, String meetingDate, String meetingTime, String meetingLocation, String meetingAgenda);

        /**
         * Calls the view to display the date dialog
         */
        void meetingDateEtClicked();

        /**
         * Retrieves the selected date and calls the view to set the meeting date edittext
         * with the selected date
         *
         * @param year       the selected year
         * @param month      the selected month
         * @param dayOfMonth the selected day
         */
        void datePicked(int year, int month, int dayOfMonth);

        /**
         * Gets the selected time and calls the view to set the meeting time edittext
         * with the selected time
         *
         * @param hourOfDay the selected hour
         * @param minute    the selected minute
         */
        void timePicked(int hourOfDay, int minute);

        /**
         * Enables the create menu button if none of the provided texts are empty; otherwise keeps disabled
         *
         * @param name     the name of the meeting
         * @param date     the date of the meeting
         * @param time     the time of the meeting
         * @param location the location of the meeting
         */
        void shouldCreateBeEnabled(String name, String date, String time, String location);

        /**
         * Shows a leave confirmation dialog none of the texts are empty; otherwise it just finishes the activity
         *
         * @param name
         * @param date
         * @param time
         * @param location
         * @param agenda
         */
        void homeButtonPressed(String name, String date, String time, String location, String agenda);

        /**
         * Sets up the time picker dialog
         */
        void setUpTimePickerDialog();

        /**
         * Sets up the date picker dialog
         */
        void setUpDatePickerDialog();
    }

}
