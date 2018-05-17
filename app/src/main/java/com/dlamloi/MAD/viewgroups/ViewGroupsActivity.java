package com.dlamloi.MAD.viewgroups;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlamloi.MAD.R;
import com.dlamloi.MAD.groupcreation.CreateGroupActivity;
import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.login.LoginActivity;
import com.dlamloi.MAD.model.Group;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewGroupsActivity extends AppCompatActivity implements ViewGroupContract.View {

    public static final String USER_PHOTO_URI = "User uri";
    public static final String GROUP_ID_KEY = "group_id";
    public static final String GROUP_TITLE_KEY = "group_title";
    public static final String GROUP_REFERENCE = "groups";

    private ViewGroupPresenter mViewGroupPresenter;

    @BindView(R.id.groups_loading_progressbar)
    ProgressBar mLoadingGroupsPb;
    @BindView(R.id.groups_recyclerview)
    RecyclerView mGroupsRv;
    CircleImageView mProfileImageIv;
    TextView mFirstNameTv;
    TextView mEmailTv;

    private GroupAdapter mGroupAdapter;
    private DatabaseReference mDatabaseReference;
    private GroupItemClickListener mGroupItemClickListener = (id, title) -> {
        Intent intent = new Intent(this, GroupHomeActivity.class);
        intent.putExtra(GROUP_ID_KEY, id);
        intent.putExtra(GROUP_TITLE_KEY, title);
        startActivity(intent);
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.view_groups_activity_title));
        ButterKnife.bind(this);
        mViewGroupPresenter = new ViewGroupPresenter(this);
        setUpMaterialDrawer(toolbar);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(GROUP_REFERENCE);
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mViewGroupPresenter.dataAdded();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mViewGroupPresenter.loadAdapterData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mGroupsRv.setLayoutManager(layoutManager);
        mGroupsRv.setAdapter(mGroupAdapter);
        setUpMaterialDrawer(toolbar);

    }

    @Override
    public void setLoadingProgressBarVisibility(boolean visibility) {
        if (visibility) {
            mLoadingGroupsPb.setVisibility(View.VISIBLE);
        } else {
            mLoadingGroupsPb.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.create_group_button)
    public void createGroupButtonClick() {
        startActivity(new Intent(this, CreateGroupActivity.class));
    }

    @Override
    public void logout() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void setProfileImage(String url) {
        Glide.with(this).load(url).into(mProfileImageIv);
    }


    @Override
    public void setDisplayName(String displayName) {
        mFirstNameTv.setText(displayName);
    }

    @Override
    public void setEmail(String email) {
        mEmailTv.setText(email);
    }

    //Ask Ryan, can we do this in the view? no logic code
    private void setUpMaterialDrawer(Toolbar toolbar) {
        View view = getLayoutInflater().inflate(R.layout.nav_header_view_group, null, false);
        mProfileImageIv = view.findViewById(R.id.profilePictureIv);
        mFirstNameTv = view.findViewById(R.id.first_name_textview);
        mEmailTv = view.findViewById(R.id.profile_email_textview);
        mViewGroupPresenter.loadProfileData();

        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(view)
                .withOnDrawerItemClickListener(((view1, position, drawerItem) -> mViewGroupPresenter.onDrawerItemClicked(position, drawerItem)))
                .build();

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.manage_account).withIcon(R.drawable.settings_icon).withIdentifier(1));
        drawer.addItem(new DividerDrawerItem());
        drawer.addItem(new PrimaryDrawerItem().withName(R.string.log_out).withIcon(R.drawable.logout_icon).withIdentifier(2).withSelectable(false));

    }

    @Override
    public void notifyItemInserted(int position) {
        mGroupAdapter.notifyItemInserted(position);
    }

    @Override
    public void notifyItemChanged(int position) {
        mGroupAdapter.notifyItemChanged(position);
    }

    @Override
    public void setRecyclerViewData(ArrayList<Group> groups) {
        mGroupAdapter = new GroupAdapter(groups, mGroupItemClickListener);
    }
    /**
     @Override public boolean onCreateOptionsMenu(Menu menu) {
     // Inflate the menu; this adds items to the action bar if it is present.
     getMenuInflater().inflate(R.menu.view_groups, menu);
     return true;
     }

     @Override public boolean onOptionsItemSelected(MenuItem item) {
     // Handle action bar item clicks here. The action bar will
     // automatically handle clicks on the Home/Up button, so long
     // as you specify a parent activity in AndroidManifest.xml.
     int id = item.getItemId();

     //noinspection SimplifiableIfStatement
     if (id == R.id.action_settings) {
     return true;
     }

     return super.onOptionsItemSelected(item);
     } */


}
