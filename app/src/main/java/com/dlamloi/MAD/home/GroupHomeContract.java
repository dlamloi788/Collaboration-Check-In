package com.dlamloi.MAD.home;

import android.content.Intent;

import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;


/**
 * Created by Don on 15/05/2018.
 */

public interface GroupHomeContract {

    interface View {

        void setGroupTitle(String title);

        void setProfileImage(String url);

        void setDisplayName(String displayName);

        void setEmail(String surname);

        void logout();

        void setUpViewPager(String groupId);

        void showShadow();

        void hideShadow();

        void collapseActionMenu();

        void showSetFileNameDialog();

        void showProgressbar();

        void hideProgressbar();

        void updateProgressBar(int currentProgress);

        void showUploadCompleteToast();

        File getImageDirectory();

        void navigateToPostUpdate();

        void navigateToScheduleMeeting();

        void navigateToAssignTask();

        void navigateToUploadFile();

        void navigateToCameraUpload(String s);
    }

    interface Presenter {

        void setup(Intent intent);

        boolean onDrawerItemClicked(int position, IDrawerItem drawerItem);

        void logout();

        void loadProfileData();

        void onActionMenuClick();

        void onActionMenuItemSelected();

        void uploadFile(int resultCode, Intent data);

        void uploadFile(String fileName);

        void postUpdate();

        void scheduleMeeting();

        void assignTask();

        void uploadFile();

        void cameraUpload();

        void cameraUpload(int resultCode, String path);



    }

    interface FirebaseStorageEventListener {

        void notifyProgressChange(int currentProgress);

        void onUploadComplete(String fileName, String s);
    }

}
