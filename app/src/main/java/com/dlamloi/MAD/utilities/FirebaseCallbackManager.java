package com.dlamloi.MAD.utilities;

/**
 * Created by Don on 25/05/2018.
 */

import com.dlamloi.MAD.home.meetings.MeetingContract;
import com.dlamloi.MAD.home.tasks.TaskContract;
import com.dlamloi.MAD.home.update.UpdateContract;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.model.Meeting;
import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.model.Update;
import com.dlamloi.MAD.viewgroups.ViewGroupContract;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class helps encapsulate Firebase Code and helps deal with callbacks
 */
public class FirebaseCallbackManager {

    public static final String GROUPS = "groups";
    public static final String UPDATES = "updates";
    public static final String MEETINGS = "meetings";
    public static final String TASKS = "tasks";
    public static final String FILES = "files";

    private DatabaseReference mDatabaseReference;
    private String mGroupId;


    public FirebaseCallbackManager() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(GROUPS);
    }

    public FirebaseCallbackManager(String groupId) {
        this();
        mGroupId = groupId;
     }



    public void attachGroupListener(ViewGroupContract.Interactor interactor) {
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Group group = dataSnapshot.getValue(Group.class);
                interactor.onGroupReceive(group);
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


    public void attachUpdatesListener(UpdateContract.UpdateListener updateListener) {
        mDatabaseReference.child(mGroupId).child(UPDATES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Update update = dataSnapshot.getValue(Update.class);
                updateListener.onUpdateAdd(update);
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

    public void attachMeetingsListener(MeetingContract.MeetingListener meetingListener) {
        mDatabaseReference.child(mGroupId).child(MEETINGS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Meeting meeting = dataSnapshot.getValue(Meeting.class);
                meetingListener.onMeetingAdd(meeting);
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

    public void attachTasksListener(TaskContract.TaskListener taskListener) {
        mDatabaseReference.child(mGroupId).child(TASKS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                taskListener.onTaskAdd(task);
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
