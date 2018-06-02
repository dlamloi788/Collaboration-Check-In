package com.dlamloi.MAD.utilities;

import android.net.Uri;
import android.util.Log;

import com.dlamloi.MAD.home.GroupHomeContract;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;

/**
 * Created by Don on 27/05/2018.
 */

/**
 * This class is responsible for uploading and download files to and from the Firebase Storage
 * and also provides progress updates
 */
public class FirebaseStorageManager {

    public static final String FILE_EXTENSION = "CloudFile Extension";
    public static final String DOWNLOAD = "Download Status";
    private GroupHomeContract.FirebaseStorageEventListener mStorageEventListener;

    /**
     * Creates an intance of the Firebase Storage Manager
     *
     * @param storageListener the event listener to be notified of storage events
     */
    public FirebaseStorageManager(GroupHomeContract.FirebaseStorageEventListener storageListener) {
        mStorageEventListener = storageListener;
    }

    /**
     * Uploads a file to the Firebase Storage
     *
     * @param groupId  the id of the group the usr is currently in
     * @param fileUri  the URI of the file
     * @param fileName the name of the file
     */
    public void uploadFile(String groupId, Uri fileUri, String fileName) {
        StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference()
                .child("files/" + groupId + "/" + fileName);
        firebaseStorage.putFile(fileUri).addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            updateProgress((int) progress);
        }).addOnSuccessListener(task -> {
            mStorageEventListener.onUploadComplete(fileName, task.getDownloadUrl().toString());
        });
    }

    /**
     * Downloads a file from the Firebase Storage
     *
     * @param name        the name of the file to download
     * @param uri         the URI of the file to download
     * @param downloadDir the download directory of the device
     */
    public void downloadFile(String name, String uri, String downloadDir) {
        StorageReference urlReference = FirebaseStorage.getInstance().getReferenceFromUrl(uri);
        String fileExtension = urlReference.getName().substring(urlReference.getName().lastIndexOf('.'));
        Log.d(FILE_EXTENSION, fileExtension);
        File file = new File(downloadDir + "/" + name);
        urlReference.getFile(file).addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            updateProgress((int) progress);
        }).addOnSuccessListener(task -> {
            mStorageEventListener.onDownloadComplete();
        });
    }

    /**
     * Notifies the listener that the upload or download progress has changed
     * @param progress the progress of the upload or download
     */
    private void updateProgress(int progress) {
        mStorageEventListener.notifyProgressChange(progress);
    }

}
