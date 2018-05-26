package com.dlamloi.MAD.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;
import com.dlamloi.MAD.utilities.FirebaseStorageManager;
import com.dlamloi.MAD.viewgroups.ViewGroupsActivity;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Don on 15/05/2018.
 */

public class GroupHomePresenter implements GroupHomeContract.Presenter, GroupHomeContract.FirebaseStorageEventListener{

    public static final String STORAGE_FILE_PATH = "files/";

    private final GroupHomeContract.View mView;
    private FirebaseUser mUser;
    private String mGroupId;
    private Uri mFile;
    private FirebaseStorageManager mFirebaseStorageManager;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;


    public GroupHomePresenter(GroupHomeContract.View view) {
        mView = view;
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager();
        mUser = mFirebaseAuthenticationManager.getCurrentUser();
        mFirebaseStorageManager = new FirebaseStorageManager(this);

    }


    public void setup(Intent intent) {
        String id = intent.getStringExtra(ViewGroupsActivity.GROUP_ID_KEY);
        String title = intent.getStringExtra(ViewGroupsActivity.GROUP_TITLE_KEY);
        mGroupId = id;
        mView.setGroupTitle(title);
        mView.setUpViewPager(mGroupId);

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

    private String timeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    @Override
    public void logout() {
        mFirebaseAuthenticationManager.signOut();
        mView.logout();
    }

    @Override
    public void loadProfileData() {
        mView.setProfileImage(mUser.getPhotoUrl().toString());
        mView.setDisplayName(mUser.getDisplayName());
        mView.setEmail(mUser.getEmail());
    }

    @Override
    public void onActionMenuClick() {
        mView.showShadow();
    }

    @Override
    public void onActionMenuItemSelected() {
        mView.hideShadow();
        mView.collapseActionMenu();
    }

    @Override
    public void uploadFile(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mFile = data.getData();
            mView.showSetFileNameDialog();
        }
    }

    @Override
    public void uploadFile(String fileName) {
        mView.showProgressbar();
        mFirebaseStorageManager.uploadFile(mGroupId, mFile, fileName);
    }


    @Override
    public void postUpdate() {
        mView.navigateToPostUpdate();
    }

    @Override
    public void scheduleMeeting() {
        mView.navigateToScheduleMeeting();
    }

    @Override
    public void assignTask() {
        mView.navigateToAssignTask();
    }

    @Override
    public void uploadFile() {
        mView.navigateToUploadFile();
    }

    @Override
    public void cameraUpload() {
        mView.navigateToCameraUpload(timeStamp());
    }

    @Override
    public void notifyProgressChange(int currentProgress) {
        mView.updateProgressBar(currentProgress);
    }

    @Override
    public void onUploadComplete() {
        mView.hideProgressbar();
        mView.showUploadCompleteToast();
    }
}


