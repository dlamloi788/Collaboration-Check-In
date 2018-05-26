package com.dlamloi.MAD.viewgroups;

import android.util.Log;

import com.dlamloi.MAD.model.Group;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

/**
 * Created by Don on 14/05/2018.
 */

public class ViewGroupPresenter implements ViewGroupContract.Presenter, ViewGroupContract.ViewGroupListener {

    public static final String LOG_GROUP = "The group is";


    private final ViewGroupContract.View mView;
    private ViewGroupContract.Interactor mViewGroupInteractor;


    public ViewGroupPresenter(ViewGroupContract.View view) {
        mView = view;
        mViewGroupInteractor = new ViewGroupInteractor(this);
    }


    @Override
    public void dataAdded() {
        mView.setLoadingProgressBarVisibility(false);
    }

    @Override
    public void logout() {
        mViewGroupInteractor.signout();
        mView.logout();
    }

    @Override
    public void loadProfileData() {
        String[] userInformation = mViewGroupInteractor.retrieveUserInformation();
        Log.d("DOESUSEREXIST?", userInformation[0]);
        mView.setProfileImage(userInformation[0]);
        mView.setDisplayName(userInformation[1]);
        mView.setEmail(userInformation[2]);
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


    @Override
    public void onGroupAdd(ArrayList<Group> groups) {
        mView.populateRecyclerView(groups);
    }
}
