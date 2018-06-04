package com.dlamloi.MAD.login;


import com.dlamloi.MAD.base.BaseAuthentication;

/**
 * Created by Don on 14/05/2018.
 */

/**
 * The contract between the login view and presenter
 */
public interface LoginContract {

    /**
     * The view associated with the contract
     */
    interface View {

        /**
         * Shows the login progressbar
         */
        void showLoginProgress();

        /**
         * Hides the login progressbar
         */
        void hideLoginProgress();

        /**
         * Shows the TextView stating that login has failed
         */
        void showLoginFailedTextView();

        /**
         * Shows the TextView stating that the email is not verified
         */
        void showEmailNotVerified();

        /**
         * Starts login upon successful authentication
         */
        void loginSuccess();

        /**
         * Enables the login button
         */
        void enableLoginButton();

        /**
         * Disables the login button
         */
        void disableLoginButton();

        /**
         * Sets the email edittext text color and hint color to a whiter shade
         */
        void highlightEmail();

        /**
         * Reverts email edittext textcolor and hint  back to default color
         */
        void unhighlightEmail();

        /**
         * Sets the password edittext text color and hint color to a whiter shade
         */
        void unhighlightPassword();

        /**
         * Sets the password edittext text color and hint color to a whiter shade
         */
        void highlightPassword();
    }

    /**
     * The presenter associated with the contract
     */
    interface Presenter {

        /**
         * Attempts to login the user
         *
         * @param email    the email of the user attempting to login
         * @param password the password of the user attempting to login
         */
        void login(String email, String password);

        /**
         * Enables button if email and password aren't empty; disables otherwise
         *
         * @param email    the email of the user attempting to login
         * @param password the password of the user attempting to login
         */
        void shouldLoginBeEnabled(String email, String password);

        /**
         * Checks if the user is logged in
         */
        void onStart();

        /**
         * Determines if the email edittext is in focus
         *
         * @param hasFocus whether the email edittext has focus
         */
        void emailHasFocus(boolean hasFocus);

        /**
         * Determines if the password edittext is in focus
         *
         * @param hasFocus whether the password edittext has focus
         */
        void passwordHasFocus(boolean hasFocus);
    }

    /**
     * This interface listeners to login events
     */
    interface OnLoginListener extends BaseAuthentication {

        /**
         * Notifies the presenter that the email is not notified
         */
        void onEmailNotVerified();

        /**
         * Notifies the presenter that the user has attempted to login
         */
        void onLoginAttempt();
    }

}



