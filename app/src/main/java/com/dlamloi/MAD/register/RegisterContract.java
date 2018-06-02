package com.dlamloi.MAD.register;

import com.dlamloi.MAD.base.BaseAuthentication;

/**
 * Created by Don on 14/05/2018.
 */

/**
 * The contract between the register presenter and view
 */
public interface RegisterContract {

    /**
     * The associated view in the contract
     */
    interface View {

        /**
         * Shows the textview that the passwords do not match
         */
        void showPasswordError();

        /**
         * Shows a toast stating that registration has failed
         */
        void showRegistrationError();

        /**
         * Highlights the firstname edittext in white
         */
        void highlightFirstName();

        /**
         * Removes highlight from the firstname edittext
         */
        void unhighlightFirstName();

        /**
         * Highlights the lastname edittext in white
         */
        void highlightLastName();

        /**
         * Removes highlight from the lastname edittext
         */
        void unhighlightLastName();

        /**
         * Highlights the email edittext in white
         */
        void highlightEmail();

        /**
         * Removes highlight from the email edittext
         */
        void unhighlightEmail();

        /**
         * Highlights the password edittext in white
         */
        void highlightPassword();

        /**
         * Removes highlight from the password edittext
         */
        void unhighlightPassword();

        /**
         * Highlights the confirm password edittext in white
         */
        void highlightConfirmPassword();

        /**
         * Removes highlight from the confirm password edittext
         */
        void unhighlightConfirmPassword();

        /**
         * Enables the register button
         */
        void enableRegisterButton();

        /**
         * Disables the register button
         */
        void disableRegisterButton();

        /**
         * Shows the progressbar
         */
        void showProgressbar();

        /**
         * Hides the progressbar
         */
        void hideProgressbar();

        /**
         * Finishes activity and goes back to the login activity
         */
        void navigateToLogin();
    }

    /**
     * The associated presenter in the contract
     */
    interface Presenter {

        /**
         * Attempts to register the user using the provided details
         *
         * @param firstName       the first name of the user
         * @param surname         the surname of the user
         * @param email           the email of the user
         * @param password        the password of the user
         * @param confirmPassword the password of the user again for confirmation purposes
         */
        void register(String firstName, String surname, String email, String password, String confirmPassword);

        /**
         * Highlights the firstname edittext in white if has focus
         *
         * @param hasFocus whether the firstname edittext has focus
         */
        void firstNameHasFocus(boolean hasFocus);

        /**
         * Highlights the lastname edittext in white if has focus
         *
         * @param hasFocus whether the lastname edittext has focus
         */
        void lastNameHasFocus(boolean hasFocus);

        /**
         * Highlights the email edittext in white if has focus
         *
         * @param hasFocus whether the email edittext has focus
         */
        void emailHasFocus(boolean hasFocus);

        /**
         * Highlights the password edittext in white if has focus
         *
         * @param hasFocus whether the password edittext has focus
         */
        void passwordHasFocus(boolean hasFocus);

        /**
         * Highlights the confirmPassword edittext in white if has focus
         *
         * @param hasFocus whether the confirmPassword edittext has focus
         */
        void confirmPasswordHasFocus(boolean hasFocus);

        /**
         * Enables the register button if no details are empty otherwise it is disabled
         *
         * @param details the provided user details
         */
        void shouldRegisterBeEnabled(String... details);
    }

    /**
     * This interface listens for registration attempts
     */
    interface OnRegisterListener extends BaseAuthentication {

        /**
         * Notifies the user that registration was attempted
         */
        void onRegistrationAttempt();
    }


}
