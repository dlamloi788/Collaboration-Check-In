package com.dlamloi.MAD.taskcreation;

import java.util.ArrayList;

/**
 * Created by Don on 17/05/2018.
 */

public interface CreateTaskContract {

    interface View {

        void setUpSpinnerData(ArrayList<String> userEmails);

        void showDateDialog(int year, int month, int day);

        void setDueDate(String date);

        void leave();

        void disableAssignButton();

        void enableAssignButton();

        void showAlertDialog();
    }

    interface Presenter {

        void taskDateClicked();

        void datePicked(int year, int month, int dayOfMonth);

        void assignTask(String assignedMember, String taskTitle, String dueDate, String taskDescription);

        void addSpinnerData(ArrayList<String> displayNames);

        void shouldAssignBeEnabled(String selectedItem, String title, String duedate);

        void homeButtonPressed(String toString, String string);
    }


}
