package com.dlamloi.MAD.viewgroups;

import android.support.v7.widget.RecyclerView;

import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;
import com.dlamloi.MAD.utilities.FirebaseCallbackManager;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

/**
 * Created by Don on 14/05/2018.
 */

/**
 * Handles the view group view presentation logic and listens for new groups being added
 */
public class ViewGroupPresenter implements ViewGroupContract.Presenter, ViewGroupContract.ViewGroupListener {


    private final ViewGroupContract.View mView;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;
    private FirebaseCallbackManager mFirebaseCallbackManager;
    private ArrayList<Group> mGroups = new ArrayList<>();

    /**
     * Creates a new instance of the view group presenter
     *
     * @param view the view which the presenter will be moderating
     */
    public ViewGroupPresenter(ViewGroupContract.View view) {
        mView = view;
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager();
        mFirebaseCallbackManager = new FirebaseCallbackManager();
        mFirebaseCallbackManager.attachGroupListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dataAdded() {
        mView.hideLoadingProgressBar();
        mView.showFab();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout() {
        mFirebaseAuthenticationManager.signOut();
        mView.logout();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadProfileData() {
        mView.setProfileImage(mFirebaseAuthenticationManager.getPhotoUrl());
        mView.setDisplayName(mFirebaseAuthenticationManager.getCurrentUserDisplayName());
        mView.setEmail(mFirebaseAuthenticationManager.getCurrentUserEmail());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onDrawerItemClicked(int position, IDrawerItem drawerItem) {
        switch (drawerItem.getIdentifier()) {
            case 1:
                logout();
                break;

        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onGroupAdd(Group group) {
        mGroups.add(group);
        mView.populateRecyclerView(mGroups);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scrollStateChanged(int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                mView.showFab();
                break;

            default:
                mView.hideFab();
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createGroupClicked() {
        mView.navigateToCreateGroup();
    }
}
