package com.dlamloi.MAD.register;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.login.LoginActivity;
import com.dlamloi.MAD.utilities.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View{

    public static final String DEFAULT_PROFILE_PICTURE_URL = "https://firebasestorage.googleapis.com/v0/b/mad-application-69143.appspot.com/o/profile%20pictures%2Fdefault-profile.jpg?alt=media&token=3be71da7-0e32-4320-b916-b8fafdbcf54e";

    @BindView(R.id.first_name_edittext) EditText mFirstNameEt;
    @BindView(R.id.last_name_edittext) EditText mLastNameEt;
    @BindView(R.id.email_edittext)EditText mEmailEt;
    @BindView(R.id.password_edittext) EditText mPasswordEt;
    @BindView(R.id.confirm_password_edittext) EditText mConfirmPasswordEt;
    @BindView(R.id.register_button) Button mRegisterButton;
    @BindView(R.id.confirm_password_error_textview)TextView mConfirmPasswordErrorTv;
    @BindView(R.id.register_progressbar) ProgressBar mRegisterPb;

    private RegisterPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mPresenter = new RegisterPresenter(this);

    }

    @OnClick(R.id.register_button)
    public void registerButtonClick() {
        String firstName = mFirstNameEt.getText().toString();
        String surname = mLastNameEt.getText().toString();
        String email = mEmailEt.getText().toString();
        String password = mPasswordEt.getText().toString();
        String confirmPassword = mConfirmPasswordEt.getText().toString();
        mPresenter.register(firstName, surname, email, password, confirmPassword);
    }



    @Override
    public void showMissingDetails() {
        mConfirmPasswordErrorTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRegistrationError() {
        Utility.showToast(this, getString(R.string.registration_failed));
    }

    /**
     private void login() {
     if (hasNoEmptyFields()) {
     String email = mEmailEt.getText().toString();
     String password = mPasswordEt.getText().toString();
     String confirmPassword = mConfirmPasswordEt.getText().toString();
     if (password.equals(confirmPassword)) {
     createAccount(email, password);
     }
     } else {
     mConfirmPasswordErrorTv.setVisibility(View.VISIBLE);
     }
     } */


    /**
     private boolean hasNoEmptyFields() {
     return !(TextUtils.isEmpty(mEmailEt.getText())
     && TextUtils.isEmpty(mPasswordEt.getText())
     && TextUtils.isEmpty(mConfirmPasswordEt.getText()));
     } */

    /**
     private void shouldRegisterButtonBeEnabled() {
     boolean areAnyFieldsEmpty = mFirstNameEt.getText().length() == 0
     || mLastNameEt.getText().length() == 0
     || mEmailEt.getText().length() == 0
     || mPasswordEt.getText().length() == 0
     || mConfirmPasswordEt.getText().length() == 0;

     mRegisterButton.setEnabled(!areAnyFieldsEmpty);
     if (!areAnyFieldsEmpty) {
     mRegisterButton.setBackground(getDrawable(R.drawable.round_button_enabled));
     } else {
     mRegisterButton.setBackground(getDrawable(R.drawable.round_button_disable));
     }
     } */
}
