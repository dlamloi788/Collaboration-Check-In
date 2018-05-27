package com.dlamloi.MAD.home.files;

import com.dlamloi.MAD.home.GroupHomeContract;
import com.dlamloi.MAD.model.CloudFile;
import com.dlamloi.MAD.utilities.FirebaseCallbackManager;
import com.dlamloi.MAD.utilities.FirebaseStorageManager;

import java.util.ArrayList;

/**
 * Created by Don on 24/05/2018.
 */

public class FilePresenter implements FileContract.Presenter, GroupHomeContract.FirebaseStorageEventListener, FileContract.FileListener {

    private final FileContract.View mView;
    private ArrayList<CloudFile> mCloudFiles = new ArrayList<>();
    private FirebaseStorageManager mFirebaseStorageManager;
    private FirebaseCallbackManager mFirebaseCallbackManager;


    public FilePresenter(FileContract.View view, String groupId) {
        mView = view;
        mFirebaseCallbackManager = new FirebaseCallbackManager(groupId);
        mFirebaseCallbackManager.attachFilesListener(this);
        mFirebaseStorageManager = new FirebaseStorageManager(this);

    }

    @Override
    public void onFileUpload(CloudFile file) {
        mCloudFiles.add(file);
        mView.populateRecyclerView(mCloudFiles);
    }

    @Override
    public void downloadFile(String name, String uri, String externalFilesDir) {
        mFirebaseStorageManager.downloadFile(name, uri, externalFilesDir);
    }

    @Override
    public void notifyProgressChange(int currentProgress) {

    }

    @Override
    public void onUploadComplete(String fileName, String s) {

    }


}
