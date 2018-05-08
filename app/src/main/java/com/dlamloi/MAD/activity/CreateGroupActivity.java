package com.dlamloi.MAD.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.dlamloi.MAD.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateGroupActivity extends AppCompatActivity {


    public static final String GROUP_NAME_TAG = "group name";
    public static final String EMAILS_TAG = "emails";


    @BindView(R.id.group_name_edittext) EditText groupNameEt;
    @BindView(R.id.member_one_edittext)EditText memberOneEt;
    @BindView(R.id.member_two_edittext)EditText memberTwoEt;
    @BindView(R.id.member_three_edittext) EditText memberThreeEt;
    @BindView(R.id.member_four_edittext) EditText memberFourEt;
    @BindView(R.id.member_five_edittext) EditText memberFiveEt;
    private EditText[] memberEts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
;
        memberEts = new EditText[]{memberOneEt, memberTwoEt, memberThreeEt, memberFourEt, memberFiveEt};


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(groupNameEt.getText()) || areAllMembersEmpty()) {
                    Toast.makeText(CreateGroupActivity.this, "No email specified", Toast.LENGTH_LONG).show();
                }
                else {
                    ArrayList<String> userEmails = new ArrayList<>();
                    for (EditText editText : memberEts) {
                        if (!TextUtils.isEmpty(editText.getText().toString())) {
                            userEmails.add(editText.getText().toString());
                        }
                    }
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(GROUP_NAME_TAG, groupNameEt.getText().toString());
                    resultIntent.putStringArrayListExtra(EMAILS_TAG, userEmails);
                    setResult(HomeActivity.CREATE_GROUP_REQUEST_CODE, resultIntent);
                    finish();
                }
            }
        });
    }

    private boolean areAllMembersEmpty() {
        for (EditText editText : memberEts) {
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                return false;
            }
        }
        return true;
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



}
