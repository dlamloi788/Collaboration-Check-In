package com.dlamloi.MAD.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlamloi.MAD.home.meetings.MeetingFragment;
import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.update.UpdateContract;
import com.dlamloi.MAD.home.update.UpdateFragment;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.viewgroups.ViewPagerAdapter;
import com.dlamloi.MAD.login.LoginActivity;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class GroupHomeActivity extends AppCompatActivity implements UpdateContract.View {


    public static final String GROUP_KEY = "mGroup";
    private GroupHomePresenter mUpdatePresenter;
    private ViewPagerAdapter mViewPagerAdapter;
    private CircleImageView mProfileImageIv;
    private TextView mFirstNameTv;
    private TextView mEmailTv;


    @BindView(R.id.container_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        mUpdatePresenter = new GroupHomePresenter(this);
        setUpMaterialDrawer(toolbar);
        mUpdatePresenter.setup(getIntent());
        initTableLayout();
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


    //Ask Ryan if this is ok too!!!f
    private void setUpMaterialDrawer(Toolbar toolbar) {
        View view = getLayoutInflater().inflate(R.layout.nav_header_view_group, null, false);
        mProfileImageIv = view.findViewById(R.id.profilePictureIv);
        mFirstNameTv = view.findViewById(R.id.first_name_textview);
        mEmailTv = view.findViewById(R.id.profile_email_textview);
        mUpdatePresenter.loadProfileData();

        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(view)
                .withOnDrawerItemClickListener(((view1, position, drawerItem) -> mUpdatePresenter.onDrawerItemClicked(position, drawerItem)))
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
    public void setUpViewPager(Group group) {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        UpdateFragment updateFragment = UpdateFragment.newInstance(group);
        MeetingFragment meetingFragment = MeetingFragment.newInstance(group);
        UpdateFragment updateFragment2 = UpdateFragment.newInstance(group);
        UpdateFragment updateFragment3 = UpdateFragment.newInstance(group);
        UpdateFragment updateFragment4 = UpdateFragment.newInstance(group);
        mViewPagerAdapter.addFragment(updateFragment, "");
        mViewPagerAdapter.addFragment(meetingFragment, "");
        mViewPagerAdapter.addFragment(updateFragment2, "");
        mViewPagerAdapter.addFragment(updateFragment3, "");
        mViewPagerAdapter.addFragment(updateFragment4, "");
        mViewPager.setAdapter(mViewPagerAdapter);
    }

}
