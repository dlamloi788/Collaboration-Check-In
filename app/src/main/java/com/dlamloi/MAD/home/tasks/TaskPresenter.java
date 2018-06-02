package com.dlamloi.MAD.home.tasks;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.utilities.FirebaseCallbackManager;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

/**
 * Handles the presntation logic of the task screen and listens for new tasks being added to the database
 */
public class TaskPresenter implements TaskContract.Presenter, TaskContract.TaskListener {

    private final TaskContract.View mView;
    private FirebaseCallbackManager mFirebaseCallbackManager;
    private ArrayList<Task> mTasks = new ArrayList<>();

    /**
     * Creates a new instance of the task presenter
     *
     * @param view the view that the presenter is moderating
     * @param groupId the id of the group the user is currently in
     */
    public TaskPresenter(TaskContract.View view, String groupId) {
        mView = view;
        mFirebaseCallbackManager = new FirebaseCallbackManager(groupId);
        mFirebaseCallbackManager.attachTasksListener(this);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTaskAdd(Task tasks) {
        mTasks.add(tasks);
        mView.populateRecyclerView(mTasks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTaskComplete(String id) {
        ArrayList<Task> tasks = mView.getTasks();
        int index = 0;
        for (Task task : tasks) {
            if (task.getId().equalsIgnoreCase(id)) {
                break;
            }
            ++index;
        }
        mView.taskCompleted(index);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scrollStateChanged(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
                mView.showFab();
                break;

            default:
                mView.hideFab();
                break;
        }
    }


}
