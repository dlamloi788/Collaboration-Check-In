package com.dlamloi.MAD.register;


import android.net.Uri;

import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;
import com.dlamloi.MAD.utilities.FirebaseRepositoryManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by Don on 14/05/2018.
 */

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.OnRegisterListener {


    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;
    private final RegisterContract.View mView;

    public RegisterPresenter(RegisterContract.View view) {
        mView = view;
        mFirebaseAuthenticationManager =new FirebaseAuthenticationManager(this);
    }


    @Override
    public void register(String firstName, String surname, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            mView.showPasswordError();
            return;
        }
        mView.showProgressbar();
        mFirebaseAuthenticationManager.register(email, password, firstName, surname);
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

    @Override
    public void onSuccess() {
        mView.navigateToLogin();
    }

    @Override
    public void onFailure() {
        mView.showRegistrationError();
        mView.hideProgressbar();
    }

    @Override
    public void onRegistrationAttempt() {
        mView.hideProgressbar();
    }
}
