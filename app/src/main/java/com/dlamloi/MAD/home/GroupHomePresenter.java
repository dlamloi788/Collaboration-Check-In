package com.dlamloi.MAD.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.dlamloi.MAD.model.CloudFile;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;
import com.dlamloi.MAD.firebasemanager.FirebaseAuthenticationManager;
import com.dlamloi.MAD.firebasemanager.FirebaseStorageManager;
import com.dlamloi.MAD.viewgroups.ViewGroupsActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

/**
 * Created by Don on 15/05/2018.
 */

/**
 * This class handles the presentation logic and listens for files uploaded to the firebase storage
 */
public class GroupHomePresenter implements GroupHomeContract.Presenter, GroupHomeContract.FirebaseStorageEventListener {

    public static final String FILE_NAME = "CloudFile name";

    private final GroupHomeContract.View mView;
    private String mGroupId;
    private Uri mFile;
    private FirebaseStorageManager mFirebaseStorageManager;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;

    /**
     * Creates an instance of the group home presenter
     *
     * @param view the view which the presenter is moderating
     */
    public GroupHomePresenter(GroupHomeContract.View view) {
        mView = view;
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager();
        mFirebaseStorageManager = new FirebaseStorageManager(this);
    }

    /**
     * Sets up title and viewpager and retrieves data inside the intent
     *
     * @param intent the intent with the data
     */
    public void setup(Intent intent) {
        String id = intent.getStringExtra(ViewGroupsActivity.GROUP_ID_KEY);
        String title = intent.getStringExtra(ViewGroupsActivity.GROUP_TITLE_KEY);
        mGroupId = id;
        mView.setGroupTitle(title);
        mView.setUpViewPager(mGroupId);
        mFirebaseRepositoryManager = new FirebaseRepositoryManager(mGroupId);
    }

    /**
     * Handles the drawer item click depending on which item was clicked
     *
     * @param position   the position of the item
     * @param drawerItem the draweritem
     * @return
     */
    public boolean onDrawerItemClicked(int position, IDrawerItem drawerItem) {
        switch (drawerItem.getIdentifier()) {
            case 1:
                mView.goBackToGroups();
                break;

            case 2:
                logout();
                break;
        }
        return true;
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
        FirebaseAuthenticationManager authenticationManager = new FirebaseAuthenticationManager();
        mView.setProfileImage(authenticationManager.getPhotoUrl());
        mView.setDisplayName(authenticationManager.getCurrentUserDisplayName());
        mView.setEmail(authenticationManager.getCurrentUserEmail());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActionMenuClick() {
        mView.showShadow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActionMenuItemSelected() {
        mView.hideShadow();
        mView.collapseActionMenu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uploadFile(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Log.d(FILE_NAME, data.getData().toString());
            mFile = data.getData();
            saveFileWithName();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uploadFile(String fileName) {
        if (!fileName.isEmpty()) {
            mView.showUploadProgressbar();
            String fileExtension = mFile.toString().substring(mFile.toString().lastIndexOf('.'));
            mFirebaseStorageManager.uploadFile(mGroupId, mFile, fileName + fileExtension);
            mView.hideFileNameError();
            mView.clearFileName();
            mView.hideEnterFileNameDialog();
        } else {
            mView.showFileNameError();
            mView.shakeUploadFileName();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postUpdate() {
        mView.navigateToPostUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleMeeting() {
        mView.navigateToScheduleMeeting();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignTask() {
        mView.navigateToAssignTask();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uploadFile() {
        mView.navigateToUploadFile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void generateImageURI(Activity activity) {
        checkCameraPermissions(activity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void generateImageURI(int resultCode, String path) {
        if (resultCode == Activity.RESULT_OK) {
            mFile = Uri.fromFile(new File(path));
            saveFileWithName();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyProgressChange(int currentProgress) {
        mView.updateUploadProgressBar(currentProgress);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUploadComplete(String fileName, String url) {
        CloudFile file = new CloudFile(fileName, url);
        mFirebaseRepositoryManager.addFile(file);
        mView.hideUploadProgressbar();
        mView.showUploadCompleteToast();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDownloadComplete() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shouldFabBeHidden(int position) {
        if (position == 4) {
            mView.hideFab();
        } else {
            mView.showFab();
        }
        mView.collapseActionMenu();
    }

    /**
     * Handles permissions regarding the opening of camera
     *
     * @param activity the activity that called this method
     */
    private void checkCameraPermissions(Activity activity) {
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mView.navigateToCameraUpload(timeStamp());
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    /**
     * Creates a unique id by generating a timestamp
     *
     * @return the timestamp of the current date
     */
    private String timeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    /**
     * Shows the dialog to save the file with a desired name
     */
    private void saveFileWithName() {
        mView.showSetFileNameDialog();
    }
}


