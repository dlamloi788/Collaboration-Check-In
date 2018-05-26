package com.dlamloi.MAD.home.tasks;

import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.utilities.FirebaseCallbackManager;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

public class TaskPresenter implements TaskContract.Presenter, TaskContract.TaskListener {

    private final TaskContract.View mView;
    private FirebaseCallbackManager mFirebaseCallbackManager;
    private ArrayList<Task> mTasks = new ArrayList<>();

    public TaskPresenter(TaskContract.View view, String groupId) {
        mView = view;
        mFirebaseCallbackManager = new FirebaseCallbackManager(groupId);
        mFirebaseCallbackManager.attachTasksListener(this);

    }

    @Override
    public void onTaskAdd(Task tasks) {
        mTasks.add(tasks);
        mView.populateRecyclerView(mTasks);
    }
}
