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
import com.dlamloi.MAD.model.Group;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateGroupActivity extends AppCompatActivity {


    public static final String GROUP_NAME_TAG = "group name";
    public static final String EMAILS_TAG = "emails";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


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
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("groups");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        memberEts = new EditText[]{memberOneEt, memberTwoEt, memberThreeEt, memberFourEt, memberFiveEt};

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> createGroup());
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

    /**
     * Creates a group using the data in the text field and inserts it
     * into the firebase database
     */
    private void createGroup() {
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
            String id = mDatabaseReference.push().getKey();
            Group group = new Group(id, groupNameEt.getText().toString(),
                    mUser.getEmail(), userEmails);

            mDatabaseReference.child(id).setValue(group);
        }
    }


}
