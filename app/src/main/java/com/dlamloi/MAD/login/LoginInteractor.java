package com.dlamloi.MAD.login;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Don on 21/05/2018.
 */

public class LoginInteractor implements LoginContract.Interactor {

    private LoginContract.OnLoginListener mOnLoginListener;
    private FirebaseAuth mFirebaseAuth;


    public LoginInteractor(LoginContract.OnLoginListener onLoginListener) {
        mOnLoginListener = onLoginListener;
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void firebaseLogin(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    mOnLoginListener.onLoginAttempt();
                    if (task.isSuccessful()) {
                        checkEmailVerified(mFirebaseAuth.getCurrentUser());
                    } else {
                        mOnLoginListener.onFailure();
                    }
                });
    }

    @Override
    public void checkIfUserLoggedIn() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            checkEmailVerified(user);
        }
    }

    private void checkEmailVerified(FirebaseUser user) {
        if (user.isEmailVerified()) {
            mOnLoginListener.onSuccess();
        } else {
            mOnLoginListener.onEmailNotVerified();
        }
    }

}
