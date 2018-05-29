package com.dlamloi.MAD.groupcreation;

import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by Don on 17/05/2018.
 */

public class CreateGroupPresenter implements CreateGroupContract.Presenter {

    private final CreateGroupContract.View mView;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;


    public CreateGroupPresenter(CreateGroupContract.View view) {
        mView = view;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager();
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager();
    }


    @Override
    public void createGroup(String groupName, ArrayList<String> userEmails) {
        if (doesListContainAllEmptyEmails(userEmails)) {
            mView.showNoEmailEnteredToast();
        } else {
            String userEmail = mFirebaseAuthenticationManager.getCurrentUserEmail();
            Group group = new Group(groupName, userEmail, userEmails);
            mFirebaseRepositoryManager.addGroup(group);
        }
        mView.groupCreated();
    }

    private boolean doesListContainAllEmptyEmails(ArrayList<String> userEmails) {
        for (String userEmail : userEmails) {
            if (!userEmail.isEmpty()) {
                return false;
            }

        }
        return true;
    }

}
