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

import java.util.ArrayList;

/**
 * Created by Don on 22/05/2018.
 */

public class ViewGroupInteractor implements ViewGroupContract.Interactor{

    private ViewGroupContract.OnViewGroupListener mOnViewGroupListener;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private ArrayList<Group> mGroups = new ArrayList<>();

    public ViewGroupInteractor(ViewGroupContract.OnViewGroupListener onViewGroupListener)  {
        mOnViewGroupListener = onViewGroupListener;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("groups");
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Group group = dataSnapshot.getValue(Group.class);
                //Log.d(LOG_GROUP, group.getId());
                if (isUserAMember(group)) {
                    mGroups.add(group);
                    Log.d("GROUPSIZE", mGroups.size() + "");
                }
                Log.d("ONCHILDADDED", "I'm called once..?");
                //Fix this tomorrow
                mOnViewGroupListener.onGroupAdd(mGroups);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Group group = dataSnapshot.getValue(Group.class);
                if (!mGroups.isEmpty()) {
                    int index = indexOfGroup(group);
                    mGroups.set(index, group);
                    //Fix this tomorrow
                    //mView.notifyItemChanged(index);
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
    public String[] retrieveUserInformation() {
        String userInformation[] = new String[3];
        userInformation[0] = mFirebaseUser.getPhotoUrl().toString();
        userInformation[1] = mFirebaseUser.getDisplayName();
        userInformation[2] = mFirebaseUser.getEmail();
        return userInformation;
    }

    private boolean isUserAMember(Group group) {
        String currentUserEmail = mFirebaseUser.getEmail();
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


    @Override
    public void signout() {
        mFirebaseAuth.signOut();
    }
}
