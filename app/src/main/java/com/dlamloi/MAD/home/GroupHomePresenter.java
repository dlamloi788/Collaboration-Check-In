package com.dlamloi.MAD.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.viewgroups.ViewGroupsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Don on 15/05/2018.
 */

public class GroupHomePresenter implements GroupHomeContract.Presenter {

    public static final String STORAGE_FILE_PATH = "files/";

    private final GroupHomeContract.View mView;
    private FirebaseUser mUser;
    private String mGroupId;
    private Uri mFile;


    public GroupHomePresenter(GroupHomeContract.View view) {
        mView = view;
        mUser = FirebaseAuth.getInstance().getCurrentUser();

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
        StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference()
                .child("files/" + mGroupId + "/" + fileName);
        mView.showProgressbar();
        firebaseStorage.putFile(mFile).addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) /taskSnapshot.getTotalByteCount();
            int currentProgress = (int) progress;
            mView.updateProgressBar(currentProgress);

        }).addOnCompleteListener(task -> {
            mView.hideProgressbar();
            mView.showUploadCompleteToast();
        });
    }
}


