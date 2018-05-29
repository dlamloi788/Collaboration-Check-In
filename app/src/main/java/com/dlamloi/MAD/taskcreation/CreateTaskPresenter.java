package com.dlamloi.MAD.taskcreation;

import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Don on 17/05/2018.
 */

public class CreateTaskPresenter implements CreateTaskContract.Presenter {

    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_COMPLETE = "Complete";
    public static final String STATUS_OVERDUE = "Overdue";

    private final CreateTaskContract.View mView;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;


    public CreateTaskPresenter(CreateTaskContract.View view, String groupId) {
        mView = view;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager(groupId);
        mFirebaseRepositoryManager.getUsers(this);

    }

    @Override
    public void loadSpinnerData(String groupId) {

    }

    @Override
    public void taskDateClicked() {
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        mView.showDateDialog(year, month, day);
    }

    @Override
    public void datePicked(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String date = dateFormat.format(calendar.getTime());
        mView.setDueDate(date);
    }

    @Override
    public void assignTask(String assignedMember, String taskTitle, String dueDate, String taskDescription) {
        Task task = new Task(taskTitle, taskDescription, STATUS_PENDING, assignedMember, dueDate);
        mFirebaseRepositoryManager.addTask(task);
        mView.leave();
    }

    @Override
    public void addSpinnerData(ArrayList<String> displayNames) {
        mView.setUpSpinnerData(displayNames);
    }
}
