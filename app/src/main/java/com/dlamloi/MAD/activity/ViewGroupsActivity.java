package com.dlamloi.MAD.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlamloi.MAD.R;
import com.dlamloi.MAD.adapter.GroupAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewGroupsActivity extends AppCompatActivity {

    public static final String USER_PHOTO_URI = "User uri";


    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    @BindView(R.id.groups_loading_progressbar) ProgressBar mLoadingGroupsPb;
    @BindView(R.id.groups_recyclerview)
    RecyclerView mGroupsRv;
    private GroupAdapter mGroupAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.view_groups_activity_title));
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        setUpMaterialDrawer(toolbar);

        Log.d("mUser profile picture", mUser.getPhotoUrl() + "");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("groups");
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mLoadingGroupsPb.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mGroupAdapter = new GroupAdapter(this, mDatabaseReference);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mGroupsRv.setLayoutManager(layoutManager);
        mGroupsRv.setAdapter(mGroupAdapter);

        FloatingActionButton createGroupBtn = findViewById(R.id.create_group_button);
        createGroupBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, CreateGroupActivity.class));
        });
    }

    private void setUpMaterialDrawer(Toolbar toolbar) {

        Log.d(USER_PHOTO_URI, mUser.getPhotoUrl().toString());

        View view = getLayoutInflater().inflate(R.layout.nav_header_view_group, null, false);
        CircleImageView profilePictureIV = view.findViewById(R.id.profilePictureIv);
        TextView firstNameTv = view.findViewById(R.id.first_name_textview);
        TextView emailTv = view.findViewById(R.id.profile_email_textview);

        Glide.with(this).load(mUser.getPhotoUrl()).into(profilePictureIV);
        firstNameTv.setText(mUser.getDisplayName());
        emailTv.setText(mUser.getEmail());

        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(view)
                .withOnDrawerItemClickListener(((view1, position, drawerItem) -> {
                    switch (drawerItem.getIdentifier()) {
                        case 1:
                            manageAccount();
                            break;

                        case 2:
                            logout();
                            break;

                    }
                    return true;
                }))
                .build();


        drawer.addItem(new PrimaryDrawerItem().withName(R.string.manage_account).withIcon(R.drawable.settings_icon).withIdentifier(1));
        drawer.addItem(new DividerDrawerItem());
        drawer.addItem(new PrimaryDrawerItem().withName(R.string.log_out).withIcon(R.drawable.logout_icon).withIdentifier(2).withSelectable(false));

    }

    private void leaveGroup() {
    }

    public void manageAccount() {
    }

    public void logout() {
        mAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_groups, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
