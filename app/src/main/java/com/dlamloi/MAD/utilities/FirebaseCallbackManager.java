package com.dlamloi.MAD.utilities;

/**
 * Created by Don on 25/05/2018.
 */

import com.dlamloi.MAD.home.chat.MessageContract;
import com.dlamloi.MAD.home.files.FileContract;
import com.dlamloi.MAD.home.meetings.MeetingContract;
import com.dlamloi.MAD.home.tasks.TaskContract;
import com.dlamloi.MAD.home.update.UpdateContract;
import com.dlamloi.MAD.model.ChatMessage;
import com.dlamloi.MAD.model.CloudFile;
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
 * This class helps encapsulate Firebase Code and helps deal with callbacks from Firebase
 */
public class FirebaseCallbackManager {

    public static final String GROUPS = "groups";
    public static final String USERS = "users";
    public static final String UPDATES = "updates";
    public static final String MEETINGS = "meetings";
    public static final String TASKS = "tasks";
    public static final String FILES = "files";
    public static final String MESSAGES = "messages";
    public static final String STATUS = "status";

    private DatabaseReference mDatabaseReference;
    private String mGroupId;


    /**
     * Creates an instance of the firebase callback manager
     */
    public FirebaseCallbackManager() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(GROUPS);
    }

    /**
     * Creates an instance of the firebase callback manager pointing to a specific reference
     *
     * @param groupId the id of the group for the callback manager to point at
     */
    public FirebaseCallbackManager(String groupId) {
        this();
        mGroupId = groupId;
     }


    /**
     * Attaches a listener to the group reference in the database
     *
     * @param viewGroupListener the group listener observing any groups that are added
     */
    public void attachGroupListener(ViewGroupContract.ViewGroupListener viewGroupListener) {
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Group group = dataSnapshot.getValue(Group.class);
                if (isUserAMember(group)) {
                    viewGroupListener.onGroupAdd(group);
                }
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

    /**
     * Determines whether the current user is a member of the group
     *
     * @param group the group who's email list is matched against the current users email
     * @return true if the admin email matches or is part of the member list; otherwise false
     */
    private boolean isUserAMember(Group group) {
        String currentUserEmail  = new FirebaseAuthenticationManager().getCurrentUserEmail();
        for (String email : group.getMemberEmails()) {
            if (currentUserEmail.equalsIgnoreCase(email)) {
                return true;
            }
        }
        return currentUserEmail.equalsIgnoreCase(group.getAdminEmail());
    }

    /**
     * Attaches a listener to the update reference at a specified group
     *
     * @param updateListener the update listener observing any updates that are added
     */
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

    /**
     * Attaches a listener to the meeting reference at a specified group
     *
     * @param meetingListener the meeting listener observing any meetings that are added
     */
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

    /**
     * Attaches a listener to the task reference at a specified group
     *
     * @param taskListener the task listener observing any new tasks that are added
     */
    public void attachTasksListener(TaskContract.TaskListener taskListener) {
        mDatabaseReference.child(mGroupId).child(TASKS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                taskListener.onTaskAdd(task);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                taskListener.onTaskComplete(task.getId());
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

    /**
     * Attaches a listener to the task reference at a specific grup
     *
     * @param fileListener the file listener observing any new files uploaded
     */
    public void attachFilesListener(FileContract.newFileListener fileListener) {
        mDatabaseReference.child(mGroupId).child(FILES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CloudFile cloudFile = dataSnapshot.getValue(CloudFile.class);
                fileListener.onFileUpload(cloudFile);
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

    /**
     * Attaches a listener to the message reference at a specific group
     *
     * @param messageListener the message listener observing any new messages
     */
    public void attachMessageListener(MessageContract.newMessageListener messageListener) {
        mDatabaseReference.child(mGroupId).child(MESSAGES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                messageListener.onNewMessage(chatMessage);
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
