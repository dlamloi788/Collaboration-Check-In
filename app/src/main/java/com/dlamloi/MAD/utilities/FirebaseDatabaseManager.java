package com.dlamloi.MAD.utilities;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Don on 14/05/2018.
 */

public class FirebaseDatabaseManager {

    private FirebaseAuth mFirebaseAuth;
    private static FirebaseDatabaseManager sInstance;

    private FirebaseDatabaseManager() { }

    public static FirebaseDatabaseManager getInstance() {
        if (sInstance == null) {
            sInstance = new FirebaseDatabaseManager();
        }
        return sInstance;
    }




}
