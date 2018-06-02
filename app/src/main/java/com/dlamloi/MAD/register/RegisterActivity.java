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

/**
 * This class is responsible for displaying the UI elements
 */
public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    public static final String DEFAULT_PROFILE_PICTURE_URL = "https://firebasestorage.googleapis.com/v0/b/mad-application-69143.appspot.com/o/profile%20pictures%2Fdefault-profile.jpg?alt=media&token=3be71da7-0e32-4320-b916-b8fafdbcf54e";

    @BindView(R.id.first_name_edittext)
    EditText mFirstNameEt;
    @BindView(R.id.last_name_edittext)
    EditText mLastNameEt;
    @BindView(R.id.email_edittext)
    EditText mEmailEt;
    @BindView(R.id.password_edittext)
    EditText mPasswordEt;
    @BindView(R.id.confirm_password_edittext)
    EditText mConfirmPasswordEt;
    @BindView(R.id.register_button)
    Button mRegisterButton;
    @BindView(R.id.confirm_password_error_textview)
    TextView mConfirmPasswordErrorTv;
    @BindView(R.id.register_progressbar)
    ProgressBar mRegisterPb;

    private RegisterContract.Presenter mRegisterPresenter;

    /**
     * {@inheritDoc}
     */
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

    /**
     * Handles the register button click
     */
    @OnClick(R.id.register_button)
    public void registerButtonClick() {
        String firstName = mFirstNameEt.getText().toString();
        String surname = mLastNameEt.getText().toString();
        String email = mEmailEt.getText().toString();
        String password = mPasswordEt.getText().toString();
        String confirmPassword = mConfirmPasswordEt.getText().toString();
        mRegisterPresenter.register(firstName, surname, email, password, confirmPassword);
    }

    /**
     * Calls the presenter to determine whether the register button should be enabled
     * when an edittext changes
     */
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

    /**
     * Calls the presenter to determine whether the first name edittext should be highlighted
     *
     * @param v the view that the edittext is associated with
     * @param hasFocus whether the first name edittext has focus
     */
    @OnFocusChange(R.id.first_name_edittext)
    public void firstNameFocus(View v, boolean hasFocus) {
        mRegisterPresenter.firstNameHasFocus(hasFocus);
    }

    /**
     * Calls the presenter to determine whether the last name edittext should be highlighted
     *
     * @param v the view that the edittext is associated with
     * @param hasFocus whether the last name edittext has focus
     */
    @OnFocusChange(R.id.last_name_edittext)
    public void lastNameFocus(View v, boolean hasFocus) {
        mRegisterPresenter.lastNameHasFocus(hasFocus);
    }

    /**
     * Calls the presenter to determine whether the email edittext should be highlighted
     *
     * @param v the view that the edittext is associated with
     * @param hasFocus whether the email edittext has focus
     */
    @OnFocusChange(R.id.email_edittext)
    public void emailFocus(View v, boolean hasFocus) {
        mRegisterPresenter.emailHasFocus(hasFocus);
    }

    /**
     * Calls the presenter to determine whether the password edittext should be highlighted
     *
     * @param v the view that the edittext is associated with
     * @param hasFocus whether the password edittext has focus
     */
    @OnFocusChange(R.id.password_edittext)
    public void passwordFocus(View v, boolean hasFocus) {
        mRegisterPresenter.passwordHasFocus(hasFocus);
    }

    /**
     * Calls the presenter to determine whether the confirm password edittext should be highlighted
     *
     * @param v the view that the edittext is associated with
     * @param hasFocus whether the confirm password edittext has focus
     */
    @OnFocusChange(R.id.confirm_password_edittext)
    public void confirmPasswordFocus(View v, boolean hasFocus) {
        mRegisterPresenter.confirmPasswordHasFocus(hasFocus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPasswordError() {
        mConfirmPasswordErrorTv.setVisibility(View.VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showRegistrationError() {
        Utility.showToast(this, getString(R.string.registration_failed));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void highlightFirstName() {
        highlightEditText(mFirstNameEt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unhighlightFirstName() {
        unlightEditText(mFirstNameEt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void highlightLastName() {
        highlightEditText(mLastNameEt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unhighlightLastName() {
        unlightEditText(mLastNameEt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void highlightEmail() {
        highlightEditText(mEmailEt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unhighlightEmail() {
        unlightEditText(mEmailEt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void highlightPassword() {
        highlightEditText(mPasswordEt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unhighlightPassword() {
        unlightEditText(mPasswordEt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void highlightConfirmPassword() {
        highlightEditText(mConfirmPasswordEt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unhighlightConfirmPassword() {
        unlightEditText(mConfirmPasswordEt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enableRegisterButton() {
        mRegisterButton.setEnabled(true);
        mRegisterButton.setBackground(getDrawable(R.drawable.round_button_enabled));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableRegisterButton() {
        mRegisterButton.setEnabled(false);
        mRegisterButton.setBackground(getDrawable(R.drawable.round_button_disable));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgressbar() {
        mRegisterPb.setVisibility(View.VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideProgressbar() {
        mRegisterPb.setVisibility(View.INVISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void navigateToLogin() {
        finish();
    }

    /**
     * Highlights the provided edittext in white
     *
     * @param editText the edittext to be highlighted in white
     */
    private void highlightEditText(EditText editText) {
        editText.setHintTextColor(Color.WHITE);
        editText.setTextColor(Color.WHITE);
    }

    /**
     * Removes the highlight from the provided edittext
     *
     * @param editText the edittext to have the highlight removed
     */
    private void unlightEditText(EditText editText) {
        editText.setHintTextColor(getResources().getColor(R.color.LoginRegisterEditText_TextColorHint));
        editText.setTextColor(getResources().getColor(R.color.LoginRegisterEditText_TextColorHint));

    }

}
