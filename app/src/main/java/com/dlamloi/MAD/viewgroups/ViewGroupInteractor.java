package com.dlamloi.MAD.viewgroups;

import android.util.Log;

import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;
import com.dlamloi.MAD.utilities.FirebaseCallbackManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Don on 22/05/2018.
 */

public class ViewGroupInteractor implements ViewGroupContract.Interactor{

    private ViewGroupContract.ViewGroupListener mViewGroupListener;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;
    private FirebaseCallbackManager mFirebaseCallbackManager;
    private ArrayList<Group> mGroups = new ArrayList<>();

    public ViewGroupInteractor(ViewGroupContract.ViewGroupListener viewGroupListener)  {
        mViewGroupListener = viewGroupListener;
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager();
        mFirebaseCallbackManager = new FirebaseCallbackManager();
        mFirebaseCallbackManager.attachGroupListener(this);
    }

    @Override
    public void onGroupReceive(Group group) {
        if (isUserAMember(group)) {
            mGroups.add(group);
        }
        mViewGroupListener.onGroupAdd(mGroups);
    }


    private boolean isUserAMember(Group group) {
        String currentUserEmail = mFirebaseAuthenticationManager.getCurrentUserEmail();
        if (group.getAdminEmail().equalsIgnoreCase(currentUserEmail)) {
            return true;
        }

        for (String email : group.getMemberEmails()) {
            if (currentUserEmail.equalsIgnoreCase(email.trim())) {
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
        mFirebaseAuthenticationManager.signOut();
    }
}
