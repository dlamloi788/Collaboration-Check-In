package com.dlamloi.MAD.home;

import android.app.Activity;
import android.content.Intent;

import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


/**
 * Created by Don on 15/05/2018.
 */

/**
 * The contract between the group view and contract
 */
public interface GroupHomeContract {

    /**
     * The associated view in the contract
     */
    interface View {

        /**
         * Sets the action bar title
         *
         * @param title the title of action bar
         */
        void setGroupTitle(String title);

        /**
         * Sets the profile picture in the account header
         *
         * @param url the url of the users profile image
         */
        void setProfileImage(String url);

        /**
         * Sets the profile display name in the account header
         * @param displayName the users display name
         */
        void setDisplayName(String displayName);

        /**
         * Sets the profile email in the account header
         *
         * @param email the email of the user
         */
        void setEmail(String email);

        /**
         * Signs the user out
         */
        void logout();

        /**
         * Sets up the viewpager
         *
         * @param groupId the id the user is currently in
         */
        void setUpViewPager(String groupId);

        /**
         * Masks the viewpager with a dark tint
         */
        void showShadow();

        /**
         * Hides the dark tint over the viewpager
         */
        void hideShadow();

        /**
         * Collapses the action menu
         */
        void collapseActionMenu();

        /**
         * Displays dialog to allow user to save file with a desired name
         */
        void showSetFileNameDialog();

        /**
         * Reveals the upload progressbar
         */
        void showUploadProgressbar();

        /**
         * Hides the upload progressbar
         */
        void hideUploadProgressbar();

        /**
         * Reveals the upload progressbar
         */
        void showDownloadProgressbar();

        /**
         * Hides the download progressbar
         */
        void hideDownloadProgressbar();

        /**
         * Updates the update progressbar by a number
         * @param currentProgress the progress of the progressbar
         */
        void updateUploadProgressBar(int currentProgress);

        /**
         * Updates the download progressbar by a number
         * @param currentProgress the progress of the progressbar
         */
        void updateDownloadProgressBar(int currentProgress);

        /**
         * Displays a toast stating that the download is complete
         */
        void showUploadCompleteToast();

        /**
         * Navigates to the activity that allow users to post an update
         */
        void navigateToPostUpdate();

        /**
         * Navigates to the activity that allow users to schedule a meeting
         */
        void navigateToScheduleMeeting();

        /**
         * Navigates to the activity that allow users to assign a task
         */
        void navigateToAssignTask();

        /**
         * Navigates to the activity that allow users to upload a file
         */
        void navigateToUploadFile();

        /**
         * Navigates to the activity that allow users to upload a photo via the camera application
         * @param timeStamp the current time
         */
        void navigateToCameraUpload(String timeStamp);

        /**
         * Shows the floating action button
         */
        void showFab();

        /**
         * Hides the floating action button
         */
        void hideFab();

        /**
         * Returns to the group page
         */
        void goBackToGroups();



        /**
         * Hides the upload file dialog
         */
        void hideEnterFileNameDialog();

        /**
         *  Shakes the file edittext of the upload dialog if empty
         */
        void shakeUploadFileName();

        /**
         * Shows textview stating that the upload requires a file name
         */
        void showFileNameError();

        /**
         * Hides the textview with the upload file name error
         */
        void hideFileNameError();

        /**
         * Clears the file name in the upload dialog edittext
         */
        void clearFileName();
    }

    /**
     * The associated presenter in the contract
     */
    interface Presenter {

        /**
         * Retrieves the data from the intent and instantiates objects
         * @param intent the intent with the data
         */
        void setup(Intent intent);

        /**
         * Calls the appropriate method upon selecting an item
         *
         * @param position the position of the item
         * @param drawerItem the draweritem
         * @return true if the item was selected successfully; false otherwise
         */
        boolean onDrawerItemClicked(int position, IDrawerItem drawerItem);

        /**
         * Signs the user out
         */
        void logout();

        /**
         * Loads the profile data into the account header
         */
        void loadProfileData();

        /**
         * Shows the shadow over the viewpager upon opening the floating action menu
         */
        void onActionMenuClick();

        /**
         * Hides the shadow over the viewpager when an item in the floating action menu is selected
         */
        void onActionMenuItemSelected();

        /**
         * Retrieves file URI and displays dialog to save file with a specified name
         *
         * @param resultCode the result of the activity
         * @param data the intent with the data
         */
        void uploadFile(int resultCode, Intent data);

        /**
         *  Shows the progressbar for uploading and calls the repository manager to save the file with a desired name
         *
         * @param fileName the desired file name
         */
        void uploadFile(String fileName);

        /**
         * Navigates to the post update activity
         */
        void postUpdate();

        /**
         * Navigates to the schedule meeting activity
         */
        void scheduleMeeting();

        /**
         * Navigates to the assign task activity
         */
        void assignTask();

        /**
         * Navigates to the file manager
         */
        void uploadFile();

        /**
         * Checks for permissions with the associated activity
         *
         * @param activity the activity which invoked this method
         */
        void generateImageURI(Activity activity);

        /**
         * Creates a URI out of the file path
         *
         * @param resultCode
         * @param path
         */
        void generateImageURI(int resultCode, String path);

        /**
         * Determines if the viewpager is on the chatfragment and if so the floating action button is hidden
         * @param position the position that the user is currently at in the tablayout
         */
        void shouldFabBeHidden(int position);

    }

    /**
     * The listener for new firebasestorage events
     */
    interface FirebaseStorageEventListener {

        /**
         * Notifies the presenter that the upload/download progress has changed
         * @param currentProgress the progress of the upload/download
         */
        void notifyProgressChange(int currentProgress);

        /**
         * Notifies that the upload is complete and stores the uploaded data into the database
         *
         * @param fileName the name of the file
         * @param url the url of the file
         */
        void onUploadComplete(String fileName, String url);

        /**
         * Notifies the presenter that the download is complete
         */
        void onDownloadComplete();
    }

}
