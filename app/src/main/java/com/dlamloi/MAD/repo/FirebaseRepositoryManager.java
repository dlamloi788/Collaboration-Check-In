package com.dlamloi.MAD.repo;

import com.dlamloi.MAD.model.ChatMessage;
import com.dlamloi.MAD.model.CloudFile;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.model.Meeting;
import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.model.Update;
import com.dlamloi.MAD.utilities.FirebaseCallbackManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Don on 25/05/2018.
 */

public class FirebaseRepositoryManager {

    private DatabaseReference mDatabaseReference;
    private String mGroupId;


    /**
     * This constructor should be used when creating groups
     */
    public FirebaseRepositoryManager() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(FirebaseCallbackManager.GROUPS);
    }

    /**
     * This constructor is used when adding to child nodes of the group
     *
     * @param groupId The group id of the group the user is currently viewing
     */
    public FirebaseRepositoryManager(String groupId) {
        this();
        mGroupId = groupId;

    }

    /**
     * Creates a unique key for an item
     *
     * @return a unique key
     */
    private String generateKey() {
        return mDatabaseReference.push().getKey();
    }

    public void addGroup(Group group) {
        String id = generateKey();
        group.setId(id);
        mDatabaseReference.child(id).setValue(group);
    }

    public void addUpdate(Update update) {
        String id = generateKey();
        update.setId(id);
        mDatabaseReference.child(mGroupId).child(FirebaseCallbackManager.UPDATES).child(id).setValue(update);
    }

    public void addMeeting(Meeting meeting) {
        String id = generateKey();
        meeting.setId(id);
        mDatabaseReference.child(mGroupId).child(FirebaseCallbackManager.MEETINGS).child(id).setValue(meeting);
    }

    public void addTask(Task task) {
        String id = generateKey();
        task.setId(id);
        mDatabaseReference.child(mGroupId).child(FirebaseCallbackManager.TASKS).child(id).setValue(task);
    }

    public void addFile(CloudFile cloudFile) {
        String id = generateKey();
        mDatabaseReference.child(mGroupId).child(FirebaseCallbackManager.FILES).child(id).setValue(cloudFile);

    }

    public void sendMessage(ChatMessage message) {
        String id = generateKey();
        mDatabaseReference.child(mGroupId).child(FirebaseCallbackManager.MESSAGES).child(id).setValue(message);
    }



    public ArrayList<String> getGroupMemberEmails(String groupId) {
        ArrayList<String> userEmails = new ArrayList<>();
        mDatabaseReference.child(groupId).child("memberEmails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot emailSnapshot : dataSnapshot.getChildren()) {
                    String email = emailSnapshot.getValue(String.class);
                    userEmails.add(email);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return userEmails;
    }



}
