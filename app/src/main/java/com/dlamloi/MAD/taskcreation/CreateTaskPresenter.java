package com.dlamloi.MAD.taskcreation;

import com.dlamloi.MAD.meetingcreation.CreateMeetingPresenter;
import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;
import com.dlamloi.MAD.utilities.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Don on 17/05/2018.
 */

/**
 * This class is responsible for handling the UI logic of the view
 */
public class CreateTaskPresenter implements CreateTaskContract.Presenter {

    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_COMPLETE = "Complete";

    private final CreateTaskContract.View mView;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;

    /**
     * Creates an instance of the create task presenter
     *
     * @param view    the view that the presenter will be moderating
     * @param groupId the id of the group that the user is currently in
     */
    public CreateTaskPresenter(CreateTaskContract.View view, String groupId) {
        mView = view;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager(groupId);
        mFirebaseRepositoryManager.getUsers(this);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void taskDateClicked() {
        mView.showDateDialog();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void datePicked(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(CreateMeetingPresenter.DATE_PATTERN);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String date = dateFormat.format(calendar.getTime());
        mView.setDueDate(date);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignTask(String assignedMember, String taskTitle, String dueDate, String taskDescription) {
        String[] assignedMemberSplit = assignedMember.split("-");
        String assignMemberEmail = assignedMemberSplit[1].trim();
        String assignMemberDisplayName = assignedMemberSplit[0].trim();
        Task task = new Task(taskTitle, taskDescription, STATUS_PENDING, assignMemberEmail, assignMemberDisplayName, dueDate);
        mFirebaseRepositoryManager.addTask(task);
        mView.leave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSpinnerData(ArrayList<String> displayNames) {
        mView.setUpSpinnerData(displayNames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shouldAssignBeEnabled(String selectedItem, String title, String duedate) {
        if (Utility.areAnyTextEmpty(selectedItem, title, duedate)) {
            mView.disableAssignButton();
        } else {
            mView.enableAssignButton();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void homeButtonPressed(String title, String dueDate) {
        if (!title.isEmpty() || !dueDate.isEmpty()) {
            mView.showAlertDialog();
        } else {
            mView.leave();
        }
    }

    @Override
    public void setUpDateDialog() {
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        mView.setUpDatePickerDialog(year, month, day);
    }
}
