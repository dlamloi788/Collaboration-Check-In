package com.dlamloi.MAD.register;


import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by Don on 14/05/2018.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    public static final String DEFAULT_PROFILE_PICTURE_URL = "https://firebasestorage.googleapis.com/v0/b/mad-application-69143.appspot.com/o/profile%20pictures%2Fdefault-profile.jpg?alt=media&token=3be71da7-0e32-4320-b916-b8fafdbcf54e";


    private final RegisterContract.View mView;
    private final FirebaseAuth mFirebaseAuth;

    public RegisterPresenter(RegisterContract.View view) {
        mView = view;
        mFirebaseAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void register(String firstName, String surname, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            mView.showPasswordError();
            return;
        }

        mView.showProgressbar();
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    mView.hideProgressbar();
                    if (task.isSuccessful()) {
                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                        sendEmailVerification(user);
                        UserProfileChangeRequest requestBuilder = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(Uri.parse(DEFAULT_PROFILE_PICTURE_URL))
                                .setDisplayName(firstName + " " + surname)
                                .build();
                        user.updateProfile(requestBuilder);
                        mView.navigateToLogin();
                    } else {
                        mView.showRegistrationError();
                    }
                });
    }

    @Override
    public void firstNameHasFocus(boolean hasFocus) {
        if (hasFocus) {
            mView.highlightFirstName();
        } else {
            mView.unhighlightFirstName();
        }
    }

    @Override
    public void lastNameHasFocus(boolean hasFocus) {
        if (hasFocus) {
            mView.highlightLastName();
        } else {
            mView.unhighlightLastName();
        }
    }

    @Override
    public void emailHasFocus(boolean hasFocus) {
        if (hasFocus) {
            mView.highlightEmail();
        } else {
            mView.unhighlightEmail();
        }
    }

    @Override
    public void passwordHasFocus(boolean hasFocus) {
        if (hasFocus) {
            mView.highlightPassword();
        } else {
            mView.unhighlightPassword();
        }
    }

    @Override
    public void confirmPasswordHasFocus(boolean hasFocus) {
        if (hasFocus) {
            mView.highlightConfirmPassword();
        } else {
            mView.unhighlightConfirmPassword();
        }
    }

    @Override
    public void shouldRegisterBeEnabled(String... details) {
        for (String detail : details) {
            if (detail.isEmpty()) {
                mView.disableRegisterButton();
                return;
            }
        }
        mView.enableRegisterButton();
    }

    private void sendEmailVerification(FirebaseUser user) {
        if (user != null) {
            user.sendEmailVerification();
        }
    }
}
