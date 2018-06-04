package com.dlamloi.MAD.home.files;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.dlamloi.MAD.home.GroupHomeContract;
import com.dlamloi.MAD.model.CloudFile;
import com.dlamloi.MAD.firebasemanager.FirebaseCallbackManager;
import com.dlamloi.MAD.firebasemanager.FirebaseStorageManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

/**
 * Created by Don on 24/05/2018.
 */

/**
 * Handles the presentation logic, listeners for firebase storage events and calls appropriate classes data storage
 */
public class FilePresenter implements FileContract.Presenter, GroupHomeContract.FirebaseStorageEventListener, FileContract.newFileListener {

    private final FileContract.View mView;
    private ArrayList<CloudFile> mCloudFiles = new ArrayList<>();
    private FirebaseStorageManager mFirebaseStorageManager;
    private FirebaseCallbackManager mFirebaseCallbackManager;

    /**
     * Creates a new instance of the filepresenter
     *
     * @param view    the view the presenter is moderating
     * @param groupId the id of the group the user is currently in
     */
    public FilePresenter(FileContract.View view, String groupId) {
        mView = view;
        mFirebaseCallbackManager = new FirebaseCallbackManager(groupId);
        mFirebaseCallbackManager.attachFilesListener(this);
        mFirebaseStorageManager = new FirebaseStorageManager(this);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onFileUpload(CloudFile file) {
        mCloudFiles.add(file);
        mView.populateRecyclerView(mCloudFiles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void downloadFile(Activity activity, String name, String uri, String downloadFolderDir) {
        checkReadWritePermissions(activity, name, uri, downloadFolderDir);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyProgressChange(int currentProgress) {
        mView.updateProgressBar(currentProgress);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUploadComplete(String fileName, String s) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDownloadComplete() {
        mView.hideProgressBar();
        mView.showDownloadCompleteToast();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scrollStateChanged(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
                mView.showFab();
                break;

            default:
                mView.hideFab();
                break;
        }
    }

    /**
     * Displays permission request as required and handles permission approvals and denial
     * as required
     *
     * @param activity the activity that called this method
     * @param name the name of the group
     * @param uri the uri of the file 
     * @param downloadFolderDir the location of the download folder
     */
    private void checkReadWritePermissions(Activity activity, String name, String uri, String downloadFolderDir) {
        Dexter.withActivity(activity)
                .withPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mFirebaseStorageManager.downloadFile(name, uri, downloadFolderDir);
                        mView.showProgressBar();
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


}
