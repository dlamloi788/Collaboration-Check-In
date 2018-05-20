package com.dlamloi.MAD.register;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.utilities.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

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

    private RegisterPresenter mRegisterPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mRegisterPresenter = new RegisterPresenter(this);
        mRegisterPresenter.shouldRegisterBeEnabled(
                mFirstNameEt.getText().toString(),
                mLastNameEt.getText().toString(),
                mEmailEt.getText().toString(),
                mPasswordEt.getText().toString(),
                mConfirmPasswordEt.getText().toString());
    }


    @OnClick(R.id.register_button)
    public void registerButtonClick() {
        String firstName = mFirstNameEt.getText().toString();
        String surname = mLastNameEt.getText().toString();
        String email = mEmailEt.getText().toString();
        String password = mPasswordEt.getText().toString();
        String confirmPassword = mConfirmPasswordEt.getText().toString();
        mRegisterPresenter.register(firstName, surname, email, password, confirmPassword);
    }

    @OnTextChanged(value = {R.id.first_name_edittext, R.id.last_name_edittext, R.id.email_edittext
    , R.id.password_edittext, R.id.confirm_password_edittext},
    callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void shouldRegisterBeEnabled() {
        mRegisterPresenter.shouldRegisterBeEnabled(
                mFirstNameEt.getText().toString(),
                mLastNameEt.getText().toString(),
                mEmailEt.getText().toString(),
                mPasswordEt.getText().toString(),
                mConfirmPasswordEt.getText().toString());
    }


    @OnFocusChange(R.id.first_name_edittext)
    public void firstNameFocus(View v, boolean hasFocus) {
        mRegisterPresenter.firstNameHasFocus(hasFocus);
    }

    @OnFocusChange(R.id.last_name_edittext)
    public void lastNameFocus(View v, boolean hasFocus) {
        mRegisterPresenter.lastNameHasFocus(hasFocus);
    }

    @OnFocusChange(R.id.email_edittext)
    public void emailFocus(View v, boolean hasFocus) {
        mRegisterPresenter.emailHasFocus(hasFocus);
    }

    @OnFocusChange(R.id.password_edittext)
    public void passwordFocus(View v, boolean hasFocus) {
        mRegisterPresenter.passwordHasFocus(hasFocus);
    }

    @OnFocusChange(R.id.confirm_password_edittext)
    public void confirmPasswordFocus(View v, boolean hasFocus) {
        mRegisterPresenter.confirmPasswordHasFocus(hasFocus);
    }


    @Override
    public void showPasswordError() {
        mConfirmPasswordErrorTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRegistrationError() {
        Utility.showToast(this, getString(R.string.registration_failed));
    }

    @Override
    public void highlightFirstName() {
        highlightEditText(mFirstNameEt);
    }

    @Override
    public void unhighlightFirstName() {
        unlightEditText(mFirstNameEt);
    }

    @Override
    public void highlightLastName() {
        highlightEditText(mLastNameEt);
    }

    @Override
    public void unhighlightLastName() {
        unlightEditText(mLastNameEt);
    }

    @Override
    public void highlightEmail() {
        highlightEditText(mEmailEt);
    }

    @Override
    public void unhighlightEmail() {
        unlightEditText(mEmailEt);
    }

    @Override
    public void highlightPassword() {
        highlightEditText(mPasswordEt);
    }

    @Override
    public void unhighlightPassword() {
        unlightEditText(mPasswordEt);
    }

    @Override
    public void highlightConfirmPassword() {
        highlightEditText(mConfirmPasswordEt);
    }

    @Override
    public void unhighlightConfirmPassword() {
        unlightEditText(mConfirmPasswordEt);
    }

    @Override
    public void enableRegisterButton() {
        mRegisterButton.setEnabled(true);
        mRegisterButton.setBackground(getDrawable(R.drawable.round_button_enabled));
    }

    @Override
    public void disableRegisterButton() {
        mRegisterButton.setEnabled(false);
        mRegisterButton.setBackground(getDrawable(R.drawable.round_button_disable));

    }

    @Override
    public void showProgressbar() {
        mRegisterPb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        mRegisterPb.setVisibility(View.INVISIBLE);
    }

    @Override
    public void navigateToLogin() {
        finish();
    }

    private void highlightEditText(EditText editText) {
        editText.setHintTextColor(Color.WHITE);
        editText.setTextColor(Color.WHITE);
    }

    private void unlightEditText(EditText editText) {
        editText.setHintTextColor(getResources().getColor(R.color.LoginRegisterEditText_TextColorHint));
        editText.setTextColor(getResources().getColor(R.color.LoginRegisterEditText_TextColorHint));

    }

}
