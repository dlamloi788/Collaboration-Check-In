package com.dlamloi.MAD.viewtask;

import com.dlamloi.MAD.home.tasks.TaskFragment;
import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;
import com.dlamloi.MAD.taskcreation.CreateTaskPresenter;
import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;

/**
 * Created by Don on 30/05/2018.
 */

public class ViewTaskPresenter implements ViewTaskContract.Presenter, ViewTaskContract.TaskDataListener {

    private final ViewTaskContract.View mView;
    private String mTaskId;
    private String mAssignedMember;

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
        mAssignedMember = task.getAssignedMember();
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
        String currentMember = new FirebaseAuthenticationManager().getCurrentUserEmail();
        if (mAssignedMember.equalsIgnoreCase(currentMember)) {
            mFirebaseRepositoryManager.updateTask(mTaskId, CreateTaskPresenter.STATUS_COMPLETE);
            mView.showTaskCompleteToast();
        } else {
            mView.showTaskCompleteError();
        }

    }

}
