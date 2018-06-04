package com.dlamloi.MAD.login;

import com.dlamloi.MAD.firebasemanager.FirebaseAuthenticationManager;

/**
 * Created by Don on 14/05/2018.
 */

/**
 * Handles the presentation logic for the login activity and listens for login attempts
 */
public class LoginPresenter implements LoginContract.Presenter, LoginContract.OnLoginListener {

    private final LoginContract.View mView;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;

    /**
     * Creates an instance of the login presenter
     *
     * @param view the view that the presenter is moderating
     */
    public LoginPresenter(LoginContract.View view) {
        this.mView = view;
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(String email, String password) {
        mView.showLoginProgress();
        mView.disableLoginButton();
        mFirebaseAuthenticationManager.login(email, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart() {
        mFirebaseAuthenticationManager.checkIfUserLoggedIn();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void emailHasFocus(boolean hasFocus) {
        if (hasFocus) {
            mView.highlightEmail();
        } else {
            mView.unhighlightEmail();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void passwordHasFocus(boolean hasFocus) {
        if (hasFocus) {
            mView.highlightPassword();
        } else {
            mView.unhighlightPassword();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shouldLoginBeEnabled(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            mView.enableLoginButton();
        } else {
            mView.disableLoginButton();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSuccess() {
        mView.loginSuccess();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onFailure() {
        mView.showLoginFailedTextView();
        mView.enableLoginButton();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEmailNotVerified() {
        mView.showEmailNotVerified();
        mView.enableLoginButton();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLoginAttempt() {
        mView.hideLoginProgress();
    }
}
