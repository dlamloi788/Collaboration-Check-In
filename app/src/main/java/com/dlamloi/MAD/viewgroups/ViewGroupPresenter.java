package com.dlamloi.MAD.viewgroups;

import android.support.v7.widget.RecyclerView;

import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

/**
 * Created by Don on 14/05/2018.
 */

public class ViewGroupPresenter implements ViewGroupContract.Presenter, ViewGroupContract.ViewGroupListener {


    private final ViewGroupContract.View mView;
    private ViewGroupContract.Interactor mViewGroupInteractor;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;


    public ViewGroupPresenter(ViewGroupContract.View view) {
        mView = view;
        mViewGroupInteractor = new ViewGroupInteractor(this);
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager();
    }


    @Override
    public void dataAdded() {
        mView.hideLoadingProgressBar();
        mView.showFab();
    }

    @Override
    public void logout() {
        mViewGroupInteractor.signout();
        mView.logout();
    }

    @Override
    public void loadProfileData() {
        mView.setProfileImage(mFirebaseAuthenticationManager.getPhotoUrl());
        mView.setDisplayName(mFirebaseAuthenticationManager.getCurrentUserDisplayName());
        mView.setEmail(mFirebaseAuthenticationManager.getCurrentUserEmail());
    }

    @Override
    public boolean onDrawerItemClicked(int position, IDrawerItem drawerItem) {
        switch (drawerItem.getIdentifier()) {
            case 1:
                logout();
                break;

        }
        return true;
    }


    @Override
    public void onGroupAdd(ArrayList<Group> groups) {
        mView.populateRecyclerView(groups);
    }


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
}
