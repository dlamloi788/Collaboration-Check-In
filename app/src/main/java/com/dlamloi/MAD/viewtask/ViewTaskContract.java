package com.dlamloi.MAD.viewtask;

import com.dlamloi.MAD.model.Task;

/**
 * Created by Don on 30/05/2018.
 */

/**
 * The contract between the view task view and the presenter
 */
public interface ViewTaskContract {

    /**
     * The associated view in the contract
     */
    interface View {

        /**
         * Binds the specified task data to the view
         *
         * @param title the title of the task
         * @param detail the detail of the task
         * @param dueDate the due date of the task
         */
        void bindTaskData(String title, String detail, String dueDate);

        /**
         * Finishes the activity
         */
        void leave();

        /**
         * Displays a toast stating that there is an error with completing the task
         */
        void showTaskCompleteError();

        /**
         * Displays a toast stating that the toast is complete
         */
        void showTaskCompleteToast();
    }

    /**
     * The associated presenter in the contract
     */
    interface Presenter {

        /**
         * Retrieves the specified task data from the repository
         */
        void initTaskData();

        /**
         * Calls the view to bind the received task data
         *
         * @param task the received task
         */
        void onDataReceive(Task task);

        /**
         * Finishes the activity and
         */
        void leave();

        /**
         * Calls repository to mark the task as complete or displays an error
         * if user is not assigned
         */
        void taskCompleted();

    }

    /**
     * Listens for row taps on the task recyclerview
     */
    interface TaskDataListener {

    }

}
