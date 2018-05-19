package com.dlamloi.MAD.taskcreation;

import com.dlamloi.MAD.model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private DatabaseReference mDatabaseReference;


    public CreateTaskPresenter(CreateTaskContract.View view, String groupId) {
        mView = view;

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("groups").child(groupId).child("tasks");
    }

    @Override
    public void loadSpinnerData(String groupId) {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("groups").child(groupId).child("memberEmails");
        ArrayList<String> memberEmails = new ArrayList<>();
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot emailSnapshot : dataSnapshot.getChildren()) {
                    String email = emailSnapshot.getValue(String.class);
                    memberEmails.add(email);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mView.showSpinnerData(memberEmails);
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
        String id = mDatabaseReference.push().getKey();
        Task task = new Task(id, taskTitle, taskDescription, STATUS_PENDING, assignedMember, dueDate);
        mDatabaseReference.child(id).setValue(task);

    }
}
