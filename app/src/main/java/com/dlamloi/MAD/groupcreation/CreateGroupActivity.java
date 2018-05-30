package com.dlamloi.MAD.groupcreation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.utilities.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class CreateGroupActivity extends AppCompatActivity implements CreateGroupContract.View {


    public static final String GROUP_NAME_TAG = "group name";
    public static final String EMAILS_TAG = "emails";

    private CreateGroupPresenter mCreateGroupPresenter;


    @BindView(R.id.group_name_textinputlayout)
    TextInputLayout mGroupNameTextInputLayout;
    @BindView(R.id.group_name_edittext)
    EditText mGroupNameEt;
    @BindView(R.id.member_one_textinputlayout)
    TextInputLayout mMemberOneTextInputLayout;
    @BindView(R.id.member_one_edittext)
    EditText mMemberOneEt;
    @BindView(R.id.member_two_edittext)
    EditText mMemberTwoEt;
    @BindView(R.id.member_three_edittext)
    EditText mMemberThreeEt;
    @BindView(R.id.member_four_edittext)
    EditText mMemberFourEt;
    @BindView(R.id.member_five_edittext)
    EditText mMemberFiveEt;
    @BindView(R.id.create_group_button)
    FloatingActionButton mCreateGroupButton;
    private EditText[] memberEts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mCreateGroupPresenter = new CreateGroupPresenter(this);
        memberEts = new EditText[]{mMemberOneEt, mMemberTwoEt, mMemberThreeEt, mMemberFourEt, mMemberFiveEt};
        hideFab();
    }

    @OnClick(R.id.create_group_button)
    public void createGroupButtonClick() {
        ArrayList<String> userEmails = new ArrayList<>();
        for (EditText editText : memberEts) {
            userEmails.add(editText.getText().toString().trim());
        }
        mCreateGroupPresenter.createGroup(
                mGroupNameEt.getText().toString(),
                userEmails);
    }

    @OnTextChanged(value = {R.id.group_name_edittext, R.id.member_one_edittext, R.id.member_two_edittext,
            R.id.member_three_edittext, R.id.member_four_edittext, R.id.member_five_edittext},
    callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void shouldCreateBeEnabled() {
        ArrayList<String> emails = new ArrayList<>();
        for (EditText editText : memberEts) {
            emails.add(editText.getText().toString().trim());
        }
        mCreateGroupPresenter.shouldCreateBeEnabled(mGroupNameEt.getText().toString(),
                emails);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void groupCreated() {
        finish();
    }

    @Override
    public void showFab() {
        mCreateGroupButton.setVisibility(View.VISIBLE);
        mCreateGroupButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    @Override
    public void hideFab() {
        mCreateGroupButton.setVisibility(View.INVISIBLE);
        mCreateGroupButton.animate().translationY(mCreateGroupButton.getHeight() + 30).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    @Override
    public void showEmailError(String error) {
        Utility.showToast(this, error);
    }
}
