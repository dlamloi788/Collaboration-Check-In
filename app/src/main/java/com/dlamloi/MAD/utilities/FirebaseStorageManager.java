package com.dlamloi.MAD.utilities;

import android.net.Uri;

import com.dlamloi.MAD.home.GroupHomeContract;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Don on 27/05/2018.
 */

public class FirebaseStorageManager {

    private GroupHomeContract.FirebaseStorageEventListener mStorageEventListener;

    public FirebaseStorageManager(GroupHomeContract.FirebaseStorageEventListener storageListener) {
        mStorageEventListener = storageListener;
    }

    public void uploadFile(String groupId, Uri file, String fileName) {
        StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference()
                .child("files/" + groupId + "/" + fileName);
        firebaseStorage.putFile(file).addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            int currentProgress = (int) progress;
            mStorageEventListener.notifyProgressChange(currentProgress);

        }).addOnCompleteListener(task -> {
            mStorageEventListener.onUploadComplete();
        });
    }

}
