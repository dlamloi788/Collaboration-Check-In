package com.dlamloi.MAD.viewtask;

import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;
import com.dlamloi.MAD.firebasemanager.FirebaseAuthenticationManager;

/**
 * Created by Don on 30/05/2018.
 */

/**
 * Handles the presentation logic from the view task UI
 */
public class ViewTaskPresenter implements ViewTaskContract.Presenter {

    private final ViewTaskContract.View mView;
    private String mTaskId;
    private String mAssignedMember;

    private FirebaseRepositoryManager mFirebaseRepositoryManager;

    /**
     * Creates an instance of the view task presenter
     *
     * @param view the view which the presenter will be moderating
     * @param groupId the id of the group that the user is currently in
     * @param taskId the id of the task that the user is viewing
     */
    public ViewTaskPresenter(ViewTaskContract.View view, String groupId, String taskId) {
        mView = view;
        mTaskId = taskId;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager(groupId);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void leave() {
        mView.leave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void taskCompleted() {
        String currentMember = new FirebaseAuthenticationManager().getCurrentUserEmail();
        if (mAssignedMember.equalsIgnoreCase(currentMember)) {
            mFirebaseRepositoryManager.updateTask(mTaskId);
            mView.showTaskCompleteToast();
        } else {
            mView.showTaskCompleteError();
        }

    }

}
