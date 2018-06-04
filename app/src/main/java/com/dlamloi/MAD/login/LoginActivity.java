package com.dlamloi.MAD.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.register.RegisterActivity;
import com.dlamloi.MAD.utilities.Utility;
import com.dlamloi.MAD.viewgroups.ViewGroupsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

/**
 * This class is responsible for the user logging into their account
 */
public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    public static final int REGISTER_REQUEST_CODE = 1;
    public static final String USER_EMAIL_KEY = "user email";
    public static final String LOGIN_SUCCESS = "loginsuccess";

    private LoginContract.Presenter mLoginPresenter;

    @BindView(R.id.email_edittext)
    EditText mEmailEt;
    @BindView(R.id.password_edittext)
    EditText mPasswordEt;
    @BindView(R.id.login_button)
    Button mLoginBtn;
    @BindView(R.id.login_failed_textview)
    TextView mLoginFailedTv;
    @BindView(R.id.login_progressbar)
    ProgressBar mLoginPb;
    @BindView(R.id.username_imageview)
    ImageView mEmailIv;
    @BindView(R.id.password_imageview)
    ImageView mPasswordIv;
    @BindView(R.id.signup_button)
    Button mSignUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenter(this);
        mLoginPresenter.shouldLoginBeEnabled(mEmailEt.getText().toString(),
                mPasswordEt.getText().toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onStart() {
        super.onStart();
        mLoginPresenter.onStart();
    }

    /**
     * Handles focus changes from the email edittext
     *
     * @param v the view that the edittext is on
     * @param hasFocus whether the email edittext has focus or not
     */
    @OnFocusChange(R.id.email_edittext)
    public void emailEtChange(View v, boolean hasFocus) {
        mLoginPresenter.emailHasFocus(hasFocus);
    }

    /**
     * Handles focus changes from the password edittext
     *
     * @param v the view that the edittext is on
     * @param hasFocus whether the password edittext has focus or not
     */
    @OnFocusChange(R.id.password_edittext)
    public void passwordEtChange(View v, boolean hasFocus) {
        mLoginPresenter.passwordHasFocus(hasFocus);
    }

    /**
     * Handles text changing in the edittexts0
     */
    @OnTextChanged(value = {R.id.email_edittext, R.id.password_edittext},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void shouldLoginBeEnabled() {
        mLoginPresenter.shouldLoginBeEnabled(mEmailEt.getText().toString(),
                mPasswordEt.getText().toString());
    }

    /**
     * Handles the sign up button click
     */
    @OnClick(R.id.signup_button)
    public void signUpClick() {
        Utility.startIntent(this, RegisterActivity.class);
    }

    /**
     * Handles the login button click
     */
    @OnClick(R.id.login_button)
    public void loginClick() {
        String email = mEmailEt.getText().toString().trim();
        String password = mPasswordEt.getText().toString().trim();
        mLoginPresenter.login(email, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showLoginProgress() {
        mLoginPb.setVisibility(View.VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideLoginProgress() {
        mLoginPb.setVisibility(View.INVISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showLoginFailedTextView() {
        mLoginFailedTv.setVisibility(View.VISIBLE);
        mLoginFailedTv.setText(getString(R.string.invalid_username_password));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showEmailNotVerified() {
        mLoginFailedTv.setVisibility(View.VISIBLE);
        mLoginFailedTv.setText(getString(R.string.email_not_verified));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loginSuccess() {
        Log.d(LOGIN_SUCCESS, "loginSuccess() called...?");
        Utility.startIntent(this, ViewGroupsActivity.class);
        finish();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enableLoginButton() {
        mLoginBtn.setEnabled(true);
        mLoginBtn.setBackground(getDrawable(R.drawable.round_button_enabled));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableLoginButton() {
        mLoginBtn.setEnabled(false);
        mLoginBtn.setBackground(getDrawable(R.drawable.round_button_disable));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void highlightEmail() {
        highlightEditText(mEmailEt, mEmailIv);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unhighlightEmail() {
        unhighlightEditText(mEmailEt, mEmailIv);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void highlightPassword() {
        highlightEditText(mPasswordEt, mPasswordIv);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unhighlightPassword() {
        unhighlightEditText(mPasswordEt, mPasswordIv);
    }

    /**
     * Highlights specified the edittext and imageview in the  colour white
     *
     * @param editText  the edittext to change colour
     * @param imageView the imageview to change colour
     */
    private void highlightEditText(EditText editText, ImageView imageView) {
        imageView.setColorFilter(Color.WHITE);
        editText.setHintTextColor(Color.WHITE);
        editText.setTextColor(Color.WHITE);

    }

    /**
     * Removes the highlight from the specified edittext and imageview
     *
     * @param editText  the edittext to remove the highlight
     * @param imageView the imageview to remove the highlight
     */
    private void unhighlightEditText(EditText editText, ImageView imageView) {
        imageView.clearColorFilter();
        editText.setHintTextColor(getResources().getColor(R.color.LoginRegisterEditText_TextColorHint));
        editText.setTextColor(getResources().getColor(R.color.LoginRegisterEditText_TextColorHint));
    }
}




