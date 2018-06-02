package com.dlamloi.MAD.taskcreation;

import java.util.ArrayList;

/**
 * Created by Don on 17/05/2018.
 */

/**
 * The contract between the create task view and presenter
 */
public interface CreateTaskContract {

    /**
     * The associated view in the contract
     */
    interface View {

        /**
         * Provides the spinner with data from the database
         *
         * @param userEmails the list of users from the database
         */
        void setUpSpinnerData(ArrayList<String> userEmails);

        /**
         * Shows the date dialog
         *
         * @param year  the date year when the dialog is opened
         * @param month the date month when the dialog is opened
         * @param day   the date day when the dialog is opened
         */
        void showDateDialog(int year, int month, int day);

        /**
         * Sets the due date edittext with the specified date
         *
         * @param date the selected date
         */
        void setDueDate(String date);

        /**
         * Leaves the activity
         */
        void leave();

        /**
         * Disables the assign button
         */
        void disableAssignButton();

        /**
         * Enables the assign button
         */
        void enableAssignButton();

        /**
         * Shows the alert dialog confirming the users departure
         */
        void showAlertDialog();
    }

    /**
     * The associated view in the presenter
     */
    interface Presenter {

        /**
         * Opens the date dialog
         */
        void taskDateClicked();

        /**
         * Calls the view to set the date edittext when a date has been selected
         *
         * @param year       the selected year
         * @param month      the selected month
         * @param dayOfMonth the selected day
         */
        void datePicked(int year, int month, int dayOfMonth);

        /**
         * Creates a task and calls the repository manager to store the task
         *
         * @param assignedMember  the assigned member for the task
         * @param taskTitle       the title of the task
         * @param dueDate         the date the task is due
         * @param taskDescription the description of the task
         */
        void assignTask(String assignedMember, String taskTitle, String dueDate, String taskDescription);

        /**
         * Adds data to the view spinner from data retrieved from the firebase database
         *
         * @param displayNames the data from the database
         */
        void addSpinnerData(ArrayList<String> displayNames);

        /**
         * Enables the assign button if no texts are empty otherwise it is disabled
         *
         * @param selectedMember the selected member for the task
         * @param title          the title of the task
         * @param duedate        the due date of the task
         */
        void shouldAssignBeEnabled(String selectedMember, String title, String duedate);

        /**
         * Shows a leave confirmation dialog if the one of the provided texts are not empty
         *
         * @param title   the title of the task
         * @param dueDate the due date of the task
         */
        void homeButtonPressed(String title, String dueDate);
    }


}
