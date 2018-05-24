package com.dlamloi.MAD.home.tasks;

import com.dlamloi.MAD.model.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

public class TaskPresenter implements TaskContract.Presenter, TaskContract.OnTaskListener {

    private final TaskContract.View mView;
    private TaskInteractor mTaskInteractor;


    public TaskPresenter(TaskContract.View view, String groupId) {
        mView = view;
        mTaskInteractor = new TaskInteractor(this, groupId);
        mTaskInteractor.onAttach();

    }

    @Override
    public void onTaskAdd(ArrayList<Task> tasks) {
        mView.populateRecyclerView(tasks);
    }
}
