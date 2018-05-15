package com.dlamloi.MAD.home;

import android.content.Intent;

import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.viewgroups.ViewGroupsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by Don on 15/05/2018.
 */

public class UpdatePresenter implements UpdateContract.Presenter {

    private final UpdateContract.View mView;
    private FirebaseUser mUser;

    public UpdatePresenter(UpdateContract.View view) {
        mView = view;
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }


    public void setup(Intent intent) {
        Group group = intent.getParcelableExtra(ViewGroupsActivity.GROUP_KEY);
        String title = group.getName();
        mView.setGroupTitle(title);
        mView.setUpViewPager(group);
    }

    public boolean onDrawerItemClicked(int position, IDrawerItem drawerItem) {
        switch (drawerItem.getIdentifier()) {
            case 1:
                //leavegroup
                break;

            case 2:
                //manageAccount
                break;

            case 3:
                logout();
                break;
        }
        return true;
    }

    @Override
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        mView.logout();
    }

    @Override
    public void loadProfileData() {
        mView.setProfileImage(mUser.getPhotoUrl().toString());
        mView.setDisplayName(mUser.getDisplayName());
        mView.setEmail(mUser.getEmail());
    }




}
