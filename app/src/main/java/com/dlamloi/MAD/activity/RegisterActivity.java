package com.dlamloi.MAD.activity;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements TextWatcher{

    public static final String DEFAULT_PROFILE_PICTURE_URL = "https://firebasestorage.googleapis.com/v0/b/mad-application-69143.appspot.com/o/profile%20pictures%2Fdefault-profile.jpg?alt=media&token=3be71da7-0e32-4320-b916-b8fafdbcf54e";

    private FirebaseAuth mAuth;
    @BindView(R.id.first_name_edittext) EditText mFirstNameEt;
    @BindView(R.id.last_name_edittext) EditText mLastNameEt;
    @BindView(R.id.email_edittext)EditText mEmailEt;
    @BindView(R.id.password_edittext) EditText mPasswordEt;
    @BindView(R.id.confirm_password_edittext) EditText mConfirmPasswordEt;
    @BindView(R.id.register_button) Button mRegisterButton;
    @BindView(R.id.confirm_password_error_textview)TextView mConfirmPasswordErrorTv;
    @BindView(R.id.register_progressbar) ProgressBar mRegisterPb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mFirstNameEt.addTextChangedListener(this);
        mLastNameEt.addTextChangedListener(this);
        mEmailEt.addTextChangedListener(this);
        mPasswordEt.addTextChangedListener(this);
        mConfirmPasswordEt.addTextChangedListener(this);

        mRegisterButton.setOnClickListener(view -> {
            login();
        });


    }

    private void login() {
        if (hasNoEmptyFields()) {
            String email = mEmailEt.getText().toString();
            String password = mPasswordEt.getText().toString();
            String confirmPassword = mConfirmPasswordEt.getText().toString();
            if (password.equals(confirmPassword)) {
                mConfirmPasswordErrorTv.setVisibility(View.INVISIBLE);
                mRegisterPb.setVisibility(View.VISIBLE);
                createAccount(email, password);
            }
        } else {
            mConfirmPasswordErrorTv.setVisibility(View.VISIBLE);
        }
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mRegisterPb.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            sendEmailVerification();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent userIntent = new Intent();
                            userIntent.putExtra(LoginActivity.USER_EMAIL_KEY, user.getEmail());
                            setResult(LoginActivity.REGISTER_REQUEST_CODE, userIntent);
                            Log.d("UID", user.getUid());
                            UserProfileChangeRequest profileBuilder = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(Uri.parse(DEFAULT_PROFILE_PICTURE_URL))
                                    .setDisplayName(mFirstNameEt.getText() + " " + mLastNameEt.getText())
                                    .build();
                            user.updateProfile(profileBuilder);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this,
                                    getString(R.string.registration_failed),
                                            Toast.LENGTH_LONG)  .show();

                        }
                    }
                });
    }



    private boolean hasNoEmptyFields() {
        return !(TextUtils.isEmpty(mEmailEt.getText())
                && TextUtils.isEmpty(mPasswordEt.getText())
                && TextUtils.isEmpty(mConfirmPasswordEt.getText()));
    }

    private void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification();
        }

    }

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


    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        shouldRegisterButtonBeEnabled();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
