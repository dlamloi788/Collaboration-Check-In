package com.dlamloi.MAD.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.dlamloi.MAD.adapter.GroupAdapter;
import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.Group;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    public static final int CREATE_GROUP_REQUEST_CODE = 1;
    public static final String ON_CHILD_ADDED = "OnChildAdded";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    @BindView(R.id.groups_loading_progressbar) ProgressBar mLoadingGroupsPb;
    @BindView(R.id.groups_recyclerview) RecyclerView mGroupsRv;
    private GroupAdapter mGroupAdapter;
    private List<Group> mGroupList;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Log.d("mUser profile picture", mUser.getPhotoUrl() + "");
        mGroupList = new ArrayList<>();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mGroupAdapter = new GroupAdapter(this, mGroupList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mGroupsRv.setLayoutManager(layoutManager);
        mGroupsRv.setAdapter(mGroupAdapter);
        setupFirebaseDatabase();

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
        ArrayList<String> groupMemberEmails = data.getStringArrayListExtra(CreateGroupActivity.EMAILS_TAG);
        String groupId = mDatabaseReference.push().getKey();
        Group group = new Group(groupId, groupName, mUser.getEmail(), groupMemberEmails);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference groupsReference = database.getReference();
        groupsReference.child("groups").child(groupId).setValue(group);

    }

    private void setupFirebaseDatabase() {
        mDatabaseReference = mFirebaseDatabase.getReference("groups");

        mDatabaseReference.addChildEventListener(new ChildEventListener() {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d(ON_CHILD_ADDED, "On child added called...");
            mLoadingGroupsPb.setVisibility(View.INVISIBLE);
            Group group = dataSnapshot.getValue(Group.class);
            if (isUserAMember(group)) {
                mGroupList.add(group);
            }
            mGroupAdapter.notifyItemInserted(mGroupList.size());

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Group group = dataSnapshot.getValue(Group.class);
            if (!mGroupList.isEmpty()) {
                int index = indexOfGroup(group);
                mGroupList.set(index, group);
                mGroupAdapter.notifyItemChanged(index);
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            int index = indexOfGroupWithKey(key);
            mGroupList.remove(index);
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

    private boolean isUserAMember(Group group) {
        String currentUserEmail = mUser.getEmail();
        if (group.getAdminEmail().equals(currentUserEmail)) {
            return true;
        }

        for (String email : group.getMemberEmails()) {
            if (currentUserEmail.equals(email)) {
                return true;
            }
        }

        return false;

    }


    /** private void findAllGroups(DataSnapshot dataSnapshot) {
        Group group = dataSnapshot.getValue(Group.class);
        if (group.getMemberEmails().contains(mUser.getEmail())
                || mUser.getEmail().equals(group.getAdminEmail())) {
            mGroupList.add(dataSnapshot.getValue(Group.class));
        }

    } */

    private int indexOfGroupWithKey(String key) {
        int index = 0;
        for (Group group : mGroupList) {
            if (group.getId().equals(key)) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    private int indexOfGroup(Group newGroup) {
        int index = 0;
        for (Group group : mGroupList) {
            if (group.getId().equals(newGroup.getId())) {
                return index;
            }
            ++index;
        }
        return -1;
    }



}

