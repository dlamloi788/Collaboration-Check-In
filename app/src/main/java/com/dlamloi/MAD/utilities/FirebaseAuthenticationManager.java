package com.dlamloi.MAD.utilities;

import android.net.Uri;
import android.util.Log;

import com.dlamloi.MAD.base.BaseAuthentication;
import com.dlamloi.MAD.login.LoginContract;
import com.dlamloi.MAD.model.User;
import com.dlamloi.MAD.register.RegisterContract;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by Don on 25/05/2018.
 */

/**
 * This class manages details involving Firebase Authentication
 */
public class FirebaseAuthenticationManager {

    public static final String DEFAULT_PROFILE_PICTURE_URL = "https://firebasestorage.googleapis.com/v0/b/mad-application-69143.appspot.com/o/profile%20pictures%2Fdefault-profile.jpg?alt=media&token=3be71da7-0e32-4320-b916-b8fafdbcf54e";
    public static final String DISPLAY_NAME = "Display name";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;
    private BaseAuthentication mAuthentication;

    /**
     * Creates an instance of the Firebase Authentication Manager
     *
     * Use this constructor when only wanting to retrieve user data
     */
    public FirebaseAuthenticationManager() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Creates an instance of the Firebase Authentication Manager with a listener to notify
     * of events occuring
     *
     * Use this when involving login or registration
     *
     * @param authenticationListener the authentication event listener
     */
    public FirebaseAuthenticationManager(BaseAuthentication authenticationListener) {
        this();
        mAuthentication = authenticationListener;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager();
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

    /**
     * Registers the user to Firebase Authentication and notifies the listener about the outcome
     *
     * @param email the users email
     * @param password the users password
     * @param firstName the users first name
     * @param surname the users surname
     */
    public void register(String email, String password, String firstName, String surname) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    ((RegisterContract.OnRegisterListener) (mAuthentication)).onRegistrationAttempt();
                    if (task.isSuccessful()) {
                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                        user.sendEmailVerification();
                        String displayName = firstName + " " + surname;
                        UserProfileChangeRequest requestBuilder = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(Uri.parse(DEFAULT_PROFILE_PICTURE_URL))
                                .setDisplayName(displayName)
                                .build();
                        user.updateProfile(requestBuilder);
                        mFirebaseRepositoryManager.addUser(new User(user.getEmail(), displayName));
                        Log.d(DISPLAY_NAME, displayName);
                        mAuthentication.onSuccess();
                    } else {
                        mAuthentication.onFailure();
                    }
                });
    }

    /**
     * Returns the current user
     *
     * @return the current user
     */
    private FirebaseUser getCurrentUser() {
        return mFirebaseAuth.getCurrentUser();
    }

    /**
     * Gets the email of the current user
     *
     * @return the email of the current user
     */
    public String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }

    /**
     * Gets the display name of the current user
     *
     * @return the display name of the current user
     */
    public String getCurrentUserDisplayName() {
        return getCurrentUser().getDisplayName();
    }

    /**
     * Gets the url of the current users profile picture
     *
     * @return
     */
    public String getPhotoUrl() {
        return getCurrentUser().getPhotoUrl().toString();
    }

    /**
     * Signs the current user out
     */
    public void signOut() {
        mFirebaseAuth.signOut();

    }


}
