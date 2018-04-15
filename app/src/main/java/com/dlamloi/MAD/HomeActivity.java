package com.dlamloi.MAD;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    public static final int CREATE_GROUP_REQUEST_CODE = 1;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private RecyclerView mGroupsRv;
    private GroupAdapter mGroupAdapter;
    private List<Group> mGroupList;
    private ProgressBar mLoadingPb;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mGroupsRv = findViewById(R.id.groups_recyclerview);
        mGroupList = new ArrayList<>();
        mLoadingPb = findViewById(R.id.loading_progressbar);

        mLoadingPb.setVisibility(View.VISIBLE);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mGroupAdapter = new GroupAdapter(mGroupList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mGroupsRv.setLayoutManager(layoutManager);
        mGroupsRv.setAdapter(mGroupAdapter);
        getDataFireBase();

        FloatingActionButton createGroupBtn = findViewById(R.id.create_group_button);
        createGroupBtn.setOnClickListener(view -> {
            startActivityForResult(new Intent(this, CreateGroupActivity.class), CREATE_GROUP_REQUEST_CODE);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void signOut() {
        mAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CREATE_GROUP_REQUEST_CODE:
                if (data != null) {
                    createGroup(data);
                }
                break;
        }
    }

    private void createGroup(Intent data) {
        String groupName = data.getStringExtra(CreateGroupActivity.GROUP_NAME_TAG);
        List<String> groupMemberEmails = data.getStringArrayListExtra(CreateGroupActivity.EMAILS_TAG);

        Group group = new Group(groupName, mUser.getEmail(), groupMemberEmails);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference groupsReference = database.getReference();
        groupsReference.child("groups").child(groupName).setValue(group);

    }

    private void getDataFireBase() {
        mDatabaseReference = mFirebaseDatabase.getReference("groups");
        mDatabaseReference.addChildEventListener(new ChildEventListener() {


        int count = 0;

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            ++count;
            Group group = dataSnapshot.getValue(Group.class);
            if (group.getMemberEmails().contains(mUser.getEmail())
                    || mUser.getEmail().equals(group.getAdminEmail())) {
                mGroupList.add(dataSnapshot.getValue(Group.class));
            }
            mGroupAdapter.notifyDataSetChanged();
            if (count >= dataSnapshot.getChildrenCount()) {
                mLoadingPb.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Group group = dataSnapshot.getValue(Group.class);
        dataSnapshot.getKey();
        mGroupList.add(group);
        mGroupAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        mGroupAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
        });




    }

}

