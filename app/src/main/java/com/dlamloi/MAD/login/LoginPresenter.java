package com.dlamloi.MAD.login;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Don on 14/05/2018.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;
    private FirebaseAuth mFirebaseAuth;


    public LoginPresenter(LoginContract.View view) {
        this.mView = view;
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void login(String email, String password) {
        mView.setLoginProgressVisibility(true);
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mView, task -> {
                    mView.setLoginProgressVisibility(false);
                    if (task.isSuccessful()) {
                        checkLogin(mFirebaseAuth.getCurrentUser());

                    } else {
                        mView.setLoginFailedTextViewVisiblity(true);
                    }
                });
    }

    public void checkLogin(FirebaseUser user) {
        if (user.isEmailVerified()) {
            mView.loginSuccess();
        } else {
            mView.setEmailNotVerified();
        }
    }

    @Override
    public void onStart() {
        mView.setLoginFailedTextViewVisiblity(false);
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            checkLogin(user);
        }
    }
}
