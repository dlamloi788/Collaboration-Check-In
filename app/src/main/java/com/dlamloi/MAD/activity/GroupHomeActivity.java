package com.dlamloi.MAD.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlamloi.MAD.fragment.MeetingFragment;
import com.dlamloi.MAD.fragment.UpdateFragment;
import com.dlamloi.MAD.R;
import com.dlamloi.MAD.adapter.ViewPagerAdapter;
import com.dlamloi.MAD.login.LoginActivity;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.viewgroups.ViewGroupsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class GroupHomeActivity extends AppCompatActivity {


    public static final String GROUP_KEY = "mGroup";
    public static final String USER_KEY = "user";
    private Group mGroup;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    @BindView(R.id.container_viewpager) ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        mGroup = (Group) getIntent().getParcelableExtra(ViewGroupsActivity.GROUP_KEY);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        setUpMaterialDrawer(toolbar);

        setTitle(mGroup.getName());

        setupViewPager(mViewPager);

        TabLayout mTabLayout = findViewById(R.id.tablayout);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.white));
        mTabLayout.setupWithViewPager(mViewPager);
        int[] drawables = {R.drawable.home_icon, R.drawable.group_icon,
                R.drawable.task_icon, R.drawable.cloud_icon, R.drawable.chat_icon};

        for (int i = 0; i < mTabLayout.getTabCount(); ++i) {
            mTabLayout.getTabAt(i).setIcon(drawables[i]);
        }


    }

    private void setUpMaterialDrawer(Toolbar toolbar) {

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
                            leaveGroup();
                            break;

                        case 2:
                            manageAccount();
                            break;

                        case 3:
                            logout();
                            break;
                    }
                    return true;
                }))
                .build();

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.leave_group).withIcon(R.drawable.leave_group_icon).withIdentifier(1));
        drawer.addItem(new PrimaryDrawerItem().withName(R.string.manage_account).withIcon(R.drawable.settings_icon).withIdentifier(2));
        drawer.addItem(new DividerDrawerItem());
        drawer.addItem(new PrimaryDrawerItem().withName(R.string.log_out).withIcon(R.drawable.logout_icon).withIdentifier(3).withSelectable(false));

    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void manageAccount() {
    }

    private void leaveGroup() {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_group, menu);
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        UpdateFragment updateFragment = UpdateFragment.newInstance(mGroup);
        MeetingFragment meetingFragment = MeetingFragment.newInstance(mGroup);
        UpdateFragment updateFragment2 = UpdateFragment.newInstance(mGroup);
        UpdateFragment updateFragment3 = UpdateFragment.newInstance(mGroup);
        UpdateFragment updateFragment4 = UpdateFragment.newInstance(mGroup);
        adapter.addFragment(updateFragment, "");
        adapter.addFragment(meetingFragment, "");
        adapter.addFragment(updateFragment2, "");
        adapter.addFragment(updateFragment3, "");
        adapter.addFragment(updateFragment4, "");
        viewPager.setAdapter(adapter);
    }


}
