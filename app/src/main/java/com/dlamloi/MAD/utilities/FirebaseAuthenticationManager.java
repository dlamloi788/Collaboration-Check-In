package com.dlamloi.MAD.utilities;

import android.net.Uri;

import com.dlamloi.MAD.base.BaseAuthentication;
import com.dlamloi.MAD.login.LoginContract;
import com.dlamloi.MAD.register.RegisterContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by Don on 25/05/2018.
 */

public class FirebaseAuthenticationManager {

    public static final String DEFAULT_PROFILE_PICTURE_URL = "https://firebasestorage.googleapis.com/v0/b/mad-application-69143.appspot.com/o/profile%20pictures%2Fdefault-profile.jpg?alt=media&token=3be71da7-0e32-4320-b916-b8fafdbcf54e";

    private FirebaseAuth mFirebaseAuth;
    private BaseAuthentication mAuthentication;

    //Empty constructor
    public FirebaseAuthenticationManager() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuthenticationManager(BaseAuthentication authenticationListener) {
        this();
        mAuthentication = authenticationListener;
    }

    /**
     * Attempts to login the user
     *
     * @param email    the users email
     * @param password the users password
     */
    public void login(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    ((LoginContract.OnLoginListener) (mAuthentication)).onLoginAttempt();
                    if (task.isSuccessful()) {
                        checkEmailVerified(mFirebaseAuth.getCurrentUser());
                    } else {
                        mAuthentication.onFailure();
                    }
                });
    }

    /**
     * Checks if the user is logged in
     */
    public void checkIfUserLoggedIn() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            checkEmailVerified(user);
        }
    }

    /**
     * Checks if the user has their email verified
     *
     * @param user the user attempting to login
     */
    private void checkEmailVerified(FirebaseUser user) {
        if (user.isEmailVerified()) {
            mAuthentication.onSuccess();
        } else {
            ((LoginContract.OnLoginListener) (mAuthentication)).onEmailNotVerified();
        }
    }

    public void register(String email, String password, String firstName, String surname) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    ((RegisterContract.OnRegisterListener) (mAuthentication)).onRegistrationAttempt();
                    if (task.isSuccessful()) {
                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                        user.sendEmailVerification();
                        UserProfileChangeRequest requestBuilder = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(Uri.parse(DEFAULT_PROFILE_PICTURE_URL))
                                .setDisplayName(firstName + " " + surname)
                                .build();
                        user.updateProfile(requestBuilder);
                        mAuthentication.onSuccess();
                    } else {
                        mAuthentication.onFailure();
                    }
                });
    }

    public FirebaseUser getCurrentUser() {
        return mFirebaseAuth.getCurrentUser();
    }


    public void signOut() {
        mFirebaseAuth.signOut();

    }
}
