package com.dlamloi.MAD.register;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by Don on 21/05/2018.
 */

public class RegisterInteractor implements RegisterContract.Interactor {

    public static final String DEFAULT_PROFILE_PICTURE_URL = "https://firebasestorage.googleapis.com/v0/b/mad-application-69143.appspot.com/o/profile%20pictures%2Fdefault-profile.jpg?alt=media&token=3be71da7-0e32-4320-b916-b8fafdbcf54e";


    private RegisterContract.OnRegisterListener mOnRegisterListener;
    private FirebaseAuth mFirebaseAuth;

    public RegisterInteractor(RegisterContract.OnRegisterListener onRegisterListener) {
         mOnRegisterListener = onRegisterListener;
         mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void firebaseRegister(String email, String password, String firstName, String surname) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       mOnRegisterListener.onRegistrationAttempt();
                       FirebaseUser user = mFirebaseAuth.getCurrentUser();
                       user.sendEmailVerification();
                       UserProfileChangeRequest requestBuilder = new UserProfileChangeRequest.Builder()
                               .setPhotoUri(Uri.parse(DEFAULT_PROFILE_PICTURE_URL))
                               .setDisplayName(firstName + " " + surname)
                               .build();
                       user.updateProfile(requestBuilder);
                       mOnRegisterListener.onSuccess();
                   } else {
                        mOnRegisterListener.onFailure();
                   }
                });
    }


}
