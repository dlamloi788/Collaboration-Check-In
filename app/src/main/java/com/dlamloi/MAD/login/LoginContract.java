package com.dlamloi.MAD.login;

import android.view.View;

import com.google.firebase.auth.FirebaseUser;


/**
 * Created by Don on 14/05/2018.
 */

public interface LoginContract {

    interface View {

        void setLoginProgressVisibility(boolean visibility);

        void setLoginFailedTextViewVisiblity(boolean visibility);

        void setEmailNotVerified();

        void setVisibility(boolean visibility, android.view.View view);

        void loginSuccess();
    }


    interface Presenter {

        void login(String email, String password);


        void onStart();

    }

}



