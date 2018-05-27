package com.dlamloi.MAD.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.dlamloi.MAD.model.CloudFile;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;
import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;
import com.dlamloi.MAD.utilities.FirebaseStorageManager;
import com.dlamloi.MAD.viewgroups.ViewGroupsActivity;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

/**
 * Created by Don on 15/05/2018.
 */

public class GroupHomePresenter implements GroupHomeContract.Presenter, GroupHomeContract.FirebaseStorageEventListener {

    public static final String STORAGE_FILE_PATH = "files/";
    public static final String FILE_NAME = "CloudFile name";


    private final GroupHomeContract.View mView;
    private FirebaseUser mUser;
    private String mGroupId;
    private Uri mFile;
    private FirebaseStorageManager mFirebaseStorageManager;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;


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
        mFirebaseRepositoryManager = new FirebaseRepositoryManager(mGroupId);

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

    private void upload() {
        mView.showSetFileNameDialog();
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
            Log.d(FILE_NAME, data.getData().toString());
            mFile = data.getData();
            upload();
        }
    }

    @Override
    public void uploadFile(String fileName) {
        mView.showProgressbar();
        String fileExtension = mFile.toString().substring(mFile.toString().lastIndexOf('.'));
        mFirebaseStorageManager.uploadFile(mGroupId, mFile, fileName + fileExtension);
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
    public void cameraUpload(int resultCode, String path) {
        if (resultCode == Activity.RESULT_OK) {
            mFile = Uri.fromFile(new File(path));
            upload();
        }

    }

    @Override
    public void notifyProgressChange(int currentProgress) {
        mView.updateProgressBar(currentProgress);
    }

    @Override
    public void onUploadComplete(String fileName, String url) {
        CloudFile file = new CloudFile(fileName, url);
        mFirebaseRepositoryManager.addFile(file);
        mView.hideProgressbar();
        mView.showUploadCompleteToast();

    }
}


