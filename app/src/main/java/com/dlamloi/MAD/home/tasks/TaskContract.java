package com.dlamloi.MAD.home.tasks;

import com.dlamloi.MAD.base.BasePresenter;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Task;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

/**
 * A contract between the task view and presenter
 */
public interface TaskContract {

    /**
     * The view associated with the contract
     */
    interface View extends BaseView<Task> {

        /**
         * Retrieves a list of tasks
         *
         * @return the list of tasks
         */
        ArrayList<Task> getTasks();

        /**
         * Notifies that a task has been completed
         *
         * @param index the index of the task that has been completed
         */
        void taskCompleted(int index);
    }

    /**
     * The presenter associated with the contract
     */
    interface Presenter extends BasePresenter {

    }

    /**
     * Listens for new tasks added to the database
     */
    interface TaskListener {

        /**
         * Notifies the presenter that a task had been added to the database
         *
         * @param task the task that was added to the database
         */
        void onTaskAdd(Task task);

        /**
         * Notifies the presnter that a task was marked complete
         *
         * @param taskId the id of the task that was completed
         */
        void onTaskComplete(String taskId);
    }

    /**
     * Listens for the task recyclerview row tap
     */
    interface TaskItemClickListener {

        /**
         * Notifies that a task row was tapped
         *
         * @param taskId the id of the task that was tapped
         */
        void taskClick(String taskId);
    }
}
