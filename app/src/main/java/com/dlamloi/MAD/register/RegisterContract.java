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


        void showPasswordError();

        void showRegistrationError();

        void highlightFirstName();

        void unhighlightFirstName();

        void highlightLastName();

        void unhighlightLastName();

        void highlightEmail();

        void unhighlightEmail();

        void highlightPassword();

        void unhighlightPassword();

        void highlightConfirmPassword();

        void unhighlightConfirmPassword();

        void enableRegisterButton();

        void disableRegisterButton();

        void showProgressbar();

        void hideProgressbar();

        void navigateToLogin();
    }

    /**
     * The associated presenter in the contract
     */
    interface Presenter {


        void register(String firstName, String surname, String email, String password, String confirmPassword);

        void firstNameHasFocus(boolean hasFocus);

        void lastNameHasFocus(boolean hasFocus);

        void emailHasFocus(boolean hasFocus);

        void passwordHasFocus(boolean hasFocus);

        void confirmPasswordHasFocus(boolean hasFocus);

        void shouldRegisterBeEnabled(String... details);
    }

    interface OnRegisterListener extends BaseAuthentication{



        void onRegistrationAttempt();
    }


}
