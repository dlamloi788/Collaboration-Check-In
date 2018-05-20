package com.dlamloi.MAD.login;


/**
 * Created by Don on 14/05/2018.
 */

public interface LoginContract {

    interface View {

        void showLoginProgress();

        void hideLoginProgress();

        void showLoginFailedTextView();

        void hideLoginFailedTextView();

        void setEmailNotVerified();

        void loginSuccess();

        void enableLoginButton();
        
        void disableLoginButton();

        void highlightEmail();

        void unhighlightEmail();

        void unhighlightPassword();

        void highlightPassword();
    }


    interface Presenter {

        void login(String email, String password);

        void shouldLoginBeEnabled(String email, String password);

        void onStart();

        void emailHasFocus(boolean hasFocus);

        void passwordHasFocus(boolean hasFocus);
    }

}



