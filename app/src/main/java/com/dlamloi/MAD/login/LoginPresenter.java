package com.dlamloi.MAD.login;

import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Don on 14/05/2018.
 */

public class LoginPresenter implements LoginContract.Presenter, LoginContract.OnLoginListener {

    private final LoginContract.View mView;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;


    public LoginPresenter(LoginContract.View view) {
        this.mView = view;
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager(this);
    }

    @Override
    public void login(String email, String password) {
        mView.showLoginProgress();
        mFirebaseAuthenticationManager.login(email, password);

    }

    @Override
    public void onStart() {
        mFirebaseAuthenticationManager.checkIfUserLoggedIn();
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
    public void shouldLoginBeEnabled(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            mView.enableLoginButton();
        } else {
            mView.disableLoginButton();
        }
    }

    @Override
    public void onSuccess() {
        mView.loginSuccess();
    }

    @Override
    public void onFailure() {
        mView.showLoginFailedTextView();
    }

    @Override
    public void onEmailNotVerified() {
        mView.showEmailNotVerified();
    }

    @Override
    public void onLoginAttempt() {
        mView.hideLoginProgress();
    }
}
