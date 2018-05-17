package com.dlamloi.MAD.groupcreation;

import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.utilities.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Don on 17/05/2018.
 */

public class CreateGroupPresenter implements CreateGroupContract.Presenter {

    private final CreateGroupContract.View mView;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;


    public CreateGroupPresenter(CreateGroupContract.View view) {
        mView = view;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("groups");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public void createGroup(String groupName, ArrayList<String> userEmails) {
        if (doesListContainAllEmptyEmails(userEmails)) {
            mView.showNoEmailEnteredToast();
        } else {
            String id = mDatabaseReference.push().getKey();
            Group group = new Group(id, groupName, mUser.getEmail(), userEmails);
            mDatabaseReference.child(id).setValue(group);
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
