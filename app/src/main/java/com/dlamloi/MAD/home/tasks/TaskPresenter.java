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

public class TaskPresenter implements TaskContract.Presenter {

    private final TaskContract.View mView;
    private DatabaseReference mDatabaseReference;
    private ArrayList<Task> mTasks = new ArrayList<>();


    public TaskPresenter(TaskContract.View view, String groupId) {
        mView = view;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("groups").child(groupId).child("tasks");

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                mTasks.add(task);
                mView.notifyItemInserted(mTasks.size());
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


    @Override
    public void loadAdapterData() {
        mView.setRecyclerViewData(mTasks);
    }
}
