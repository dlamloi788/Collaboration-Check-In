package com.dlamloi.MAD.groupcreation;

import android.util.Log;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.firebasemanager.FirebaseAuthenticationManager;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Don on 17/05/2018.
 */

public class CreateGroupPresenter implements CreateGroupContract.Presenter {

    public static final String BACK_PRESED = "Back press";


    private final CreateGroupContract.View mView;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;


    /**
     * Creates an instance of the CreateGroupPresenter
     *
     * @param view the view which the presenter will be instructing
     */
    public CreateGroupPresenter(CreateGroupContract.View view) {
        mView = view;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager();
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager();
    }


    /**
     * Creates a group to be inserted into the database
     *
     * @param groupName    the name of the group
     * @param memberEmails the emails of the group members
     */
    @Override
    public void createGroup(String groupName, ArrayList<String> memberEmails) {
        for (Iterator<String> emailIterator = memberEmails.iterator(); emailIterator.hasNext(); ) {
            String email = emailIterator.next();
            if (email.isEmpty()) {
                emailIterator.remove();
            }
        }
        String adminEmail = mFirebaseAuthenticationManager.getCurrentUserEmail();

        if (doesSameEmailAppearTwice(memberEmails, adminEmail)) {
            mView.showEmailError();
        } else {
            Log.d("GROUOPCREATED", "I'm called...?");
            Group group = new Group(groupName, adminEmail, memberEmails);
            mFirebaseRepositoryManager.addGroup(group);
            mView.leave();
        }
    }

    /**
     * Determines whether an email appears twice
     *
     * @param memberEmails the list of member emails
     * @param adminEmail   the email of the group administrator
     * @return true if an email is entered twice or if an email matches the administrator email; false otherwise
     */
    private boolean doesSameEmailAppearTwice(ArrayList<String> memberEmails, String adminEmail) {
        HashSet<String> emailSet = new HashSet<>();
        for (String email : memberEmails) {
            if (email.equalsIgnoreCase(adminEmail)) {
                return true;
            }
            emailSet.add(email.toLowerCase());
        }
        return emailSet.size() < memberEmails.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shouldCreateBeEnabled(String groupName, ArrayList<String> emails) {
        if (!groupName.isEmpty() && !doesListContainAllEmptyEmails(emails)) {
            mView.showFab();
        } else {
            mView.hideFab();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void homeButtonClicked(String groupName, ArrayList<String> emails) {
        if (!groupName.isEmpty() || !doesListContainAllEmptyEmails(emails)) {
            mView.showLeaveDialog();
            Log.d(BACK_PRESED, "I should show...");
        } else {
            Log.d(BACK_PRESED, "I shouldn't show...");
            mView.leave();
        }
    }


    /**
     * Checks if at least one edittext have text inside.
     *
     * @param memberEmails the member emails of the grop
     * @return true if no edittexts have emails them; false otherwise
     */
    private boolean doesListContainAllEmptyEmails(ArrayList<String> memberEmails) {
        for (String userEmail : memberEmails) {
            if (!userEmail.isEmpty()) {
                return false;
            }

        }
        return true;
    }

}
