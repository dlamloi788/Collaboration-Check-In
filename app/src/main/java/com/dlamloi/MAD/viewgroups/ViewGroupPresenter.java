package com.dlamloi.MAD.viewgroups;

import android.util.Log;

import com.dlamloi.MAD.model.Group;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

/**
 * Created by Don on 14/05/2018.
 */

public class ViewGroupPresenter implements ViewGroupContract.Presenter {

    public static final String LOG_GROUP = "The group is";


    private ArrayList<Group> mGroups = new ArrayList<>();
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;
    private final ViewGroupContract.View mView;
    private FirebaseAuth mFirebaseAuth;


    public ViewGroupPresenter(ViewGroupContract.View view) {
        mView = view;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("groups");
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Group group = dataSnapshot.getValue(Group.class);
                Log.d(LOG_GROUP, group.getId());
                if (isUserAMember(group)) {
                    mGroups.add(group);
                    Log.d("GROUPSIZE", mGroups.size() + "");
                }
                mView.notifyItemInserted(mGroups.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Group group = dataSnapshot.getValue(Group.class);
                if (!mGroups.isEmpty()) {
                    int index = indexOfGroup(group);
                    mGroups.set(index, group);
                    mView.notifyItemChanged(index);
                }
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
    public void onBindGroupViewAtPosition(GroupAdapter.ViewHolder holder, int position) {
        Group group = mGroups.get(position);
        holder.setGroupName(group.getName());

    }

    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public void dataAdded() {
        mView.setLoadingProgressBarVisibility(false);
    }

    @Override
    public void logout() {
        mFirebaseAuth.signOut();
        mView.logout();
    }

    @Override
    public void loadProfileData() {
        mView.setProfileImage(mUser.getPhotoUrl().toString());
        mView.setDisplayName(mUser.getDisplayName());
        mView.setEmail(mUser.getEmail());
    }

    @Override
    public boolean onDrawerItemClicked(int position, IDrawerItem drawerItem) {
        switch (drawerItem.getIdentifier()) {
            case 1:
                break;

            case 2:
                logout();
                break;

        }
        return true;
    }

    public void rowTapped(int position) {
        Group group = mGroups.get(position);
        mView.viewGroup(group);
    }

    private boolean isUserAMember(Group group) {
        String currentUserEmail = mUser.getEmail();
        Log.d("CURRENTEMAIL", currentUserEmail);
        if (group.getAdminEmail().equalsIgnoreCase(currentUserEmail)) {
            return true;
        }

        for (String email : group.getMemberEmails()) {
            if (currentUserEmail.equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    private int indexOfGroup(Group newGroup) {
        int index = 0;
        for (Group group : mGroups) {
            if (group.getId().equals(newGroup.getId())) {
                return index;
            }
            ++index;
        }
        return -1;
    }
}
