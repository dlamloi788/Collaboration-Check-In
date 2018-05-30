package com.dlamloi.MAD.viewtask;

import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;

/**
 * Created by Don on 30/05/2018.
 */

public class ViewTaskPresenter implements ViewTaskContract.Presenter, ViewTaskContract.TaskDataListener {

    private final ViewTaskContract.View mView;
    private String mTaskId;

    private FirebaseRepositoryManager mFirebaseRepositoryManager;


    public ViewTaskPresenter(ViewTaskContract.View view, String groupId, String taskId) {
        mView = view;
        mTaskId = taskId;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager(groupId);
    }


    @Override
    public void initTaskData() {
        mFirebaseRepositoryManager.setUpTaskData(mTaskId, this);
    }

    @Override
    public void onDataReceive(Task task) {
        mView.bindTaskData(
                task.getTitle(),
                task.getDetail(),
                task.getDueDate()
        );
    }

    @Override
    public void leave() {
        mView.leave();
    }

    @Override
    public void taskCompleted() {
        mFirebaseRepositoryManager.completedTask(mTaskId);

    }

}
