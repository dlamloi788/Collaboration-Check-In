package com.dlamloi.MAD.groupcreation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.utilities.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateGroupActivity extends AppCompatActivity implements CreateGroupContract.View {


    public static final String GROUP_NAME_TAG = "group name";
    public static final String EMAILS_TAG = "emails";

    private CreateGroupPresenter mCreateGroupPresenter;


    @BindView(R.id.group_name_edittext) EditText mGroupNameEt;
    @BindView(R.id.member_one_edittext)EditText mMemberOneEt;
    @BindView(R.id.member_two_edittext)EditText mMemberTwoEt;
    @BindView(R.id.member_three_edittext) EditText mMemberThreeEt;
    @BindView(R.id.member_four_edittext) EditText mMemberFourEt;
    @BindView(R.id.member_five_edittext) EditText mMemberFiveEt;
    @BindView(R.id.publish_group_button) FloatingActionButton publishGroupBtn;
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


    }

    @OnClick(R.id.publish_group_button)
    public void publishButtonClick() {
        ArrayList<String> userEmails = new ArrayList<>();
        for (EditText editText : memberEts) {
            userEmails.add(editText.getText().toString());
        }
        mCreateGroupPresenter.createGroup(
                mGroupNameEt.getText().toString(),
                userEmails);
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
    public void showNoEmailEnteredToast() {
        Utility.showToast(this, getString(R.string.enter_email_address));
    }
}
