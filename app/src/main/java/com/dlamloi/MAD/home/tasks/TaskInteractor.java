package com.dlamloi.MAD.home.tasks;

import com.dlamloi.MAD.model.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Don on 22/05/2018.
 */

public class TaskInteractor implements TaskContract.Interactor {

    private DatabaseReference mDatabaseReference;
    private TaskContract.OnTaskListener mOnTaskListener;
    private ArrayList<Task> mTasks = new ArrayList<>();

    public TaskInteractor(TaskContract.OnTaskListener onTaskListener, String groupId) {
        mOnTaskListener = onTaskListener;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("groups").child(groupId).child("tasks");

    }

    @Override
    public void onAttach() {
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                mTasks.add(task);
                mOnTaskListener.onTaskAdd(mTasks);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}
