package com.dlamloi.MAD.repo;

import android.util.Log;

import com.dlamloi.MAD.home.tasks.TaskFragment;
import com.dlamloi.MAD.model.ChatMessage;
import com.dlamloi.MAD.model.CloudFile;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.model.Meeting;
import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.model.Update;
import com.dlamloi.MAD.model.User;
import com.dlamloi.MAD.taskcreation.CreateTaskContract;
import com.dlamloi.MAD.taskcreation.CreateTaskPresenter;
import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;
import com.dlamloi.MAD.utilities.FirebaseCallbackManager;
import com.dlamloi.MAD.viewmeeting.ViewMeetingContract;
import com.dlamloi.MAD.viewtask.ViewTaskContract;
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

    public static final String SPINNER_CALL = "Spinner call";
    public static final String ADMIN_EMAIL = "Admin email";
    public static final String CURRENT_EMAIL = "Current email";
    public static final String USER_EMAIL = "User email";

    private DatabaseReference mDatabaseReference;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;
    private String mGroupId;
    private String adminEmail;


    /**
     * This constructor should be used when creating groups
     */
    public FirebaseRepositoryManager() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(FirebaseCallbackManager.GROUPS);
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager();
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

    /**
     * Adds a group into the firebase database
     *
     * @param group the group to be added
     */
    public void addGroup(Group group) {
        String id = generateKey();
        group.setId(id);
        mDatabaseReference.child(id).setValue(group);
    }

    /**
     * Adds an update into the firebase database
     *
     * @param update the update to be added
     */
    public void addUpdate(Update update) {
        String id = generateKey();
        update.setId(id);
        mDatabaseReference.child(mGroupId).child(FirebaseCallbackManager.UPDATES).child(id).setValue(update);
    }

    /**
     * Adds a meeting into the firebase database
     *
     * @param meeting the meeting to be added
     */
    public void addMeeting(Meeting meeting) {
        String id = generateKey();
        meeting.setId(id);
        mDatabaseReference.child(mGroupId).child(FirebaseCallbackManager.MEETINGS).child(id).setValue(meeting);
    }

    /**
     * Adds a task into the firebase database
     *
     * @param task the task to be added
     */
    public void addTask(Task task) {
        String id = generateKey();
        task.setId(id);
        mDatabaseReference.child(mGroupId).child(FirebaseCallbackManager.TASKS).child(id).setValue(task);
    }

    /**
     * Adds a selected file into the firebase database
     *
     * @param cloudFile the file to be added
     */
    public void addFile(CloudFile cloudFile) {
        String id = generateKey();
        mDatabaseReference.child(mGroupId).child(FirebaseCallbackManager.FILES).child(id).setValue(cloudFile);

    }

    /**
     * Adds a user into the firebase database
     *
     * @param user the user to be added
     */
    public void addUser(User user) {
        mDatabaseReference.getRoot().child(FirebaseCallbackManager.USERS).push().setValue(user);
    }

    /**
     * Adds a message to the firebase database
     *
     * @param message the message to be stored
     */
    public void sendMessage(ChatMessage message) {
        String id = generateKey();
        mDatabaseReference.child(mGroupId).child(FirebaseCallbackManager.MESSAGES).child(id).setValue(message);
    }


    /**
     * Begins the process of retreiving the display names of the group members
     *
     * @param presenter the create task presenter
     */
    public void getUsers(CreateTaskPresenter presenter) {
        mDatabaseReference.child(mGroupId).child("adminEmail").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adminEmail = dataSnapshot.getValue(String.class);
                Log.d(ADMIN_EMAIL, adminEmail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayList<User> users = new ArrayList<>();
        mDatabaseReference.getRoot().child(FirebaseCallbackManager.USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    users.add(user);
                }
                taskOnStart(users, presenter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * Transforms user emails to display names and passes it to the
     * presenter
     *
     * @param users     the users of the group
     * @param presenter the creater task presenter
     */
    private void taskOnStart(ArrayList<User> users, CreateTaskPresenter presenter) {
        mDatabaseReference.child(mGroupId).child("memberEmails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<String> displayNames = new ArrayList<>();
                final ArrayList<String> emails = new ArrayList<>();
                for (DataSnapshot emailSnapshot : dataSnapshot.getChildren()) {
                    String email = emailSnapshot.getValue(String.class);
                    emails.add(email);
                }

                boolean hasAdminBeenFound = false;
                for (String email : emails) {
                    for (User user : users) {
                        Log.d("LoopEmail", email);
                        Log.d("UserEmail", user.getEmail());
                        if (user.getEmail().equalsIgnoreCase(email)) {
                            displayNames.add(user.getDisplayName() + " - " + email.toLowerCase());
                            break;
                        }
                        if (!hasAdminBeenFound && user.getEmail().equalsIgnoreCase(adminEmail)) {
                            displayNames.add(user.getDisplayName() + " - " + adminEmail.toLowerCase());
                            hasAdminBeenFound = true;
                        }
                    }
                }


                presenter.addSpinnerData(displayNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Provides data of the task tapped
     *
     * @param taskId    the task id of the task tapped
     * @param presenter the task presenter
     */
    public void setUpTaskData(String taskId, ViewTaskContract.Presenter presenter) {
        mDatabaseReference.child(mGroupId).child("tasks").child(taskId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.getValue(Task.class);
                presenter.onDataReceive(task);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateTask(String taskId, String status) {
        mDatabaseReference.child(mGroupId).child(FirebaseCallbackManager.TASKS).child(taskId).child(FirebaseCallbackManager.STATUS).setValue(status);
    }

    public void setUpMeetingData(String meetingId, ViewMeetingContract.Presenter presenter) {
        mDatabaseReference.child(mGroupId).child("meetings").child(meetingId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Meeting meeting = dataSnapshot.getValue(Meeting.class);
                presenter.onDataReceive(meeting);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}

