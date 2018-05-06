package com.dlamloi.MAD.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements TextWatcher{

    public static final int REGISTER_REQUEST_CODE = 1;
    public static final String USER_EMAIL_KEY = "user email";

    private EditText mEmailEt;
    private EditText mPasswordEt;
    private Button loginBtn;
    private TextView loginFailedTv;
    private ProgressBar loginPb;

    private FirebaseAuth mAuth;

    private boolean isEmailEmpty;
    private boolean isPasswordEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        isEmailEmpty = true;
        isPasswordEmpty = true;
        loginBtn = findViewById(R.id.login_button);
        loginFailedTv = findViewById(R.id.login_failed_textview);
        loginPb = findViewById(R.id.login_progressbar);

        ImageView usernameIv = findViewById(R.id.username_imageview);


        mEmailEt = findViewById(R.id.email_edittext);
        mEmailEt.addTextChangedListener(this);

        mEmailEt.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                usernameIv.setColorFilter(Color.WHITE);
                mEmailEt.setHintTextColor(Color.WHITE);
                mEmailEt.setTextColor(Color.WHITE);
            } else {
                usernameIv.clearColorFilter();
                mEmailEt.setHintTextColor(Color.parseColor("#939393"));
                mEmailEt.setTextColor(Color.parseColor("#939393"));
            }
        });

        ImageView passwordIv = findViewById(R.id.password_imageview);
        mPasswordEt = findViewById(R.id.password_edittext);
        mPasswordEt.addTextChangedListener(this);

        mPasswordEt.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                passwordIv.setColorFilter(Color.WHITE);
                mPasswordEt.setHintTextColor(Color.WHITE);
                mPasswordEt.setTextColor(Color.WHITE);
            } else {
                passwordIv.clearColorFilter();
                mPasswordEt.setHintTextColor(Color.parseColor("#939393"));
                mPasswordEt.setTextColor(Color.parseColor("#939393"));
            }
        });

        Button signUpBtn = findViewById(R.id.signup_button);
        signUpBtn.setOnClickListener(view -> {
            startActivityForResult(new Intent(this, RegisterActivity.class), REGISTER_REQUEST_CODE);
        });

        loginBtn.setOnClickListener(view -> {
            loginPb.setVisibility(View.VISIBLE);
            String email = mEmailEt.getText().toString();
            String password = mPasswordEt.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        loginPb.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            login(user);
                        } else {
                            loginFailedTv.setText(getString(R.string.invalid_username_password));
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
                loginFailedTv.setText(getString(R.string.email_not_verified));
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
        loginBtn.setEnabled(!anyAnyEmptyFields);
        if (!anyAnyEmptyFields) {
            loginBtn.setBackground(getDrawable(R.drawable.round_button_enabled));
        } else {
            loginBtn.setBackground(getDrawable(R.drawable.round_button_disable));
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
