package com.dlamloi.MAD.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements TextWatcher{

    public static final int REGISTER_REQUEST_CODE = 1;
    public static final String USER_EMAIL_KEY = "user email";

    @BindView(R.id.email_edittext) EditText mEmailEt;
    @BindView(R.id.password_edittext) EditText mPasswordEt;
    @BindView(R.id.login_button) Button mLoginBtn;
    @BindView(R.id.login_failed_textview) TextView mLoginFailedTv;
    @BindView(R.id.login_progressbar) ProgressBar mLoginPb;
    @BindView(R.id.username_imageview) ImageView mUsernameIv;
    @BindView(R.id.password_imageview) ImageView mPasswordIv;


    private FirebaseAuth mAuth;

    private boolean isEmailEmpty;
    private boolean isPasswordEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        isEmailEmpty = true;
        isPasswordEmpty = true;


        mEmailEt.addTextChangedListener(this);

        mEmailEt.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                mUsernameIv.setColorFilter(Color.WHITE);
                mEmailEt.setHintTextColor(Color.WHITE);
                mEmailEt.setTextColor(Color.WHITE);
            } else {
                mUsernameIv.clearColorFilter();
                mEmailEt.setHintTextColor(Color.parseColor("#939393"));
                mEmailEt.setTextColor(Color.parseColor("#939393"));
            }
        });

        mPasswordEt.addTextChangedListener(this);

        mPasswordEt.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                mPasswordIv.setColorFilter(Color.WHITE);
                mPasswordEt.setHintTextColor(Color.WHITE);
                mPasswordEt.setTextColor(Color.WHITE);
            } else {
                mPasswordIv.clearColorFilter();
                mPasswordEt.setHintTextColor(Color.parseColor("#939393"));
                mPasswordEt.setTextColor(Color.parseColor("#939393"));
            }
        });

        Button signUpBtn = findViewById(R.id.signup_button);
        signUpBtn.setOnClickListener(view -> {
            startActivityForResult(new Intent(this, RegisterActivity.class), REGISTER_REQUEST_CODE);
        });

        mLoginBtn.setOnClickListener(view -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getApplicationWindowToken(), 0);
            mLoginPb.setVisibility(View.VISIBLE);
            String email = mEmailEt.getText().toString();
            String password = mPasswordEt.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        mLoginPb.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            login(user);
                        } else {
                            mLoginFailedTv.setText(getString(R.string.invalid_username_password));
                        }
                    });
        });

        shouldLoginBeEnabled();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if the user has signed in and if they have update the UI accordingly
        FirebaseUser user = mAuth.getCurrentUser();
        login(user);
    }

    private void login(FirebaseUser user) {
        if (user != null) {
            if (user.isEmailVerified()) {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else {
                mLoginFailedTv.setText(getString(R.string.email_not_verified));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REGISTER_REQUEST_CODE:
                if (data != null) {
                    mEmailEt.setText(data.getStringExtra(USER_EMAIL_KEY));
                    mEmailEt.setTextColor(Color.parseColor("#939393"));
                }
                break;

        }
    }

    private void shouldLoginBeEnabled() {
        boolean anyAnyEmptyFields = mEmailEt.getText().length() == 0
                || mPasswordEt.getText().length() == 0;
        mLoginBtn.setEnabled(!anyAnyEmptyFields);
        if (!anyAnyEmptyFields) {
            mLoginBtn.setBackground(getDrawable(R.drawable.round_button_enabled));
        } else {
            mLoginBtn.setBackground(getDrawable(R.drawable.round_button_disable));
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        shouldLoginBeEnabled();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
