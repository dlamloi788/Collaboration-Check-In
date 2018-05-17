package com.dlamloi.MAD.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlamloi.MAD.home.meetings.MeetingFragment;
import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.update.UpdateFragment;
import com.dlamloi.MAD.meetingcreation.CreateMeetingActivity;
import com.dlamloi.MAD.login.LoginActivity;
import com.dlamloi.MAD.taskcreation.CreateTaskActivity;
import com.dlamloi.MAD.updatecreation.PostUpdateActivity;
import com.dlamloi.MAD.utilities.Utility;
import com.dlamloi.MAD.viewgroups.ViewGroupsActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class GroupHomeActivity extends AppCompatActivity implements GroupHomeContract.View {


    public static final String GROUP_KEY = "group";
    private GroupHomePresenter mGroupHomePresenter;
    private ViewPagerAdapter mViewPagerAdapter;
    private CircleImageView mProfileImageIv;
    private TextView mFirstNameTv;
    private TextView mEmailTv;

    private String mGroupId;

    @BindView(R.id.group_home_rootview)
    CoordinatorLayout mGroupHomeCoordinatorLayout;
    @BindView(R.id.container_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.floating_actions_menu)
    FloatingActionsMenu mFloatingActionsMenu;
    @BindView(R.id.post_update_button)
    FloatingActionButton postUpdateBtn;
    @BindView(R.id.schedule_meeting_button)
    FloatingActionButton scheduleMeetingBtn;
    @BindView(R.id.assign_task_button)
    FloatingActionButton assignTaskBtn;
    @BindView(R.id.upload_file_button)
    FloatingActionButton uploadFileBtn;
    @BindView(R.id.shadow_view)
    View shadowView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        mGroupHomePresenter = new GroupHomePresenter(this);
        setUpMaterialDrawer(toolbar);
        mGroupHomePresenter.setup(getIntent());
        initTableLayout();
        mGroupId = getIntent().getStringExtra(ViewGroupsActivity.GROUP_ID_KEY);
        mFloatingActionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                mGroupHomePresenter.onActionMenuClick();
            }

            @Override
            public void onMenuCollapsed() {
                mGroupHomePresenter.onActionMenuItemSelected();
            }
        });

    }

    @OnClick(R.id.post_update_button)
    public void postUpdateButtonClick() {
        Intent intent = new Intent(this, PostUpdateActivity.class);
        intent.putExtra(GROUP_KEY, mGroupId);
        Utility.startIntent(this, intent);
        mGroupHomePresenter.onActionMenuItemSelected();
    }

    @OnClick(R.id.schedule_meeting_button)
    public void scheduleMeetingButtonClick() {
        Intent intent = new Intent(this, CreateMeetingActivity.class);
        intent.putExtra(GROUP_KEY, mGroupId);
        Utility.startIntent(this, intent);
        mGroupHomePresenter.onActionMenuItemSelected();

    }

    @OnClick(R.id.assign_task_button)
    public void assignTaskButtonClick() {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        intent.putExtra(GROUP_KEY, mGroupId);
        Utility.startIntent(this, intent);
        mGroupHomePresenter.onActionMenuItemSelected();
    }


    @Override
    public void setGroupTitle(String title) {
        setTitle(title);
    }

    private void initTableLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.white));
        int[] drawables = {R.drawable.home_icon, R.drawable.group_icon,
                R.drawable.task_icon, R.drawable.cloud_icon, R.drawable.chat_icon};

        for (int i = 0; i < mTabLayout.getTabCount(); ++i) {
            mTabLayout.getTabAt(i).setIcon(drawables[i]);
        }

    }


    //Ask Ryan if this is ok too!!!!
    private void setUpMaterialDrawer(Toolbar toolbar) {
        View view = getLayoutInflater().inflate(R.layout.nav_header_view_group, null, false);
        mProfileImageIv = view.findViewById(R.id.profilePictureIv);
        mFirstNameTv = view.findViewById(R.id.first_name_textview);
        mEmailTv = view.findViewById(R.id.profile_email_textview);
        mGroupHomePresenter.loadProfileData();

        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(view)
                .withOnDrawerItemClickListener(((view1, position, drawerItem) -> mGroupHomePresenter.onDrawerItemClicked(position, drawerItem)))
                .build();

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.leave_group).withIcon(R.drawable.leave_group_icon).withIdentifier(1));
        drawer.addItem(new PrimaryDrawerItem().withName(R.string.manage_account).withIcon(R.drawable.settings_icon).withIdentifier(2));
        drawer.addItem(new DividerDrawerItem());
        drawer.addItem(new PrimaryDrawerItem().withName(R.string.log_out).withIcon(R.drawable.logout_icon).withIdentifier(3).withSelectable(false));

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

    @Override
    public void logout() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    //Ask Ryan if it's ok to pass data like this???
    @Override
    public void setUpViewPager(String groupId) {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        UpdateFragment updateFragment = UpdateFragment.newInstance(groupId);
        MeetingFragment meetingFragment = MeetingFragment.newInstance(groupId);
        UpdateFragment updateFragment2 = UpdateFragment.newInstance(groupId);
        UpdateFragment updateFragment3 = UpdateFragment.newInstance(groupId);
        UpdateFragment updateFragment4 = UpdateFragment.newInstance(groupId);
        mViewPagerAdapter.addFragment(updateFragment, "");
        mViewPagerAdapter.addFragment(meetingFragment, "");
        mViewPagerAdapter.addFragment(updateFragment2, "");
        mViewPagerAdapter.addFragment(updateFragment3, "");
        mViewPagerAdapter.addFragment(updateFragment4, "");
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void showShadow() {
        shadowView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideShadow() {
        shadowView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void collapseActionMenu() {
        mFloatingActionsMenu.collapse();
    }

}
