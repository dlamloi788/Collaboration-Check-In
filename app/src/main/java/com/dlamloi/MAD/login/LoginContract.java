package com.dlamloi.MAD.login;


import com.dlamloi.MAD.base.BaseAuthentication;

/**
 * Created by Don on 14/05/2018.
 */

public interface LoginContract {

    interface View {

        void showLoginProgress();

        void hideLoginProgress();

        void showLoginFailedTextView();

        void showEmailNotVerified();

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

    interface OnLoginListener extends BaseAuthentication{

        void onEmailNotVerified();

        void onLoginAttempt();
    }

    interface Interactor {

        void firebaseLogin(String email, String password);

        void checkIfUserLoggedIn();
    }

}



