package com.dlamloi.MAD.register;


import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;

/**
 * Created by Don on 14/05/2018.
 */

/**
 * Handles the presentation logic from the register view and listens for registration attempts
 */
public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.OnRegisterListener {

    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;
    private final RegisterContract.View mView;

    /**
     * Creates a new instance of the register presenter
     *
     * @param view the view that the presenter will be moderating
     */
    public RegisterPresenter(RegisterContract.View view) {
        mView = view;
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(String firstName, String surname, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            mView.showPasswordError();
            return;
        }
        mView.showProgressbar();
        mFirebaseAuthenticationManager.register(email, password, firstName, surname);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void firstNameHasFocus(boolean hasFocus) {
        if (hasFocus) {
            mView.highlightFirstName();
        } else {
            mView.unhighlightFirstName();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lastNameHasFocus(boolean hasFocus) {
        if (hasFocus) {
            mView.highlightLastName();
        } else {
            mView.unhighlightLastName();
        }
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
    public void confirmPasswordHasFocus(boolean hasFocus) {
        if (hasFocus) {
            mView.highlightConfirmPassword();
        } else {
            mView.unhighlightConfirmPassword();
        }
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSuccess() {
        mView.navigateToLogin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onFailure() {
        mView.showRegistrationError();
        mView.hideProgressbar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRegistrationAttempt() {
        mView.hideProgressbar();
    }
}
