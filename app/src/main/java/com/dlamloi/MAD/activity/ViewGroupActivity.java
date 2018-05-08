package com.dlamloi.MAD.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlamloi.MAD.HomeFragment;
import com.dlamloi.MAD.R;
import com.dlamloi.MAD.adapter.ViewPagerAdapter;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.model.Update;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewGroupActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


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
        mGroup = (Group) getIntent().getParcelableExtra(GROUP_KEY);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


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



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        ImageView profilePictureIv = headerLayout.findViewById(R.id.profilePictureIv);
        TextView usernameTv = headerLayout.findViewById(R.id.first_name_textview);
        TextView emailTv = headerLayout.findViewById(R.id.profile_email_textview);

        //Insert user profile picture into imageview by using glide
        //and the picture URI
        Glide.with(this)
                .load(mUser.getPhotoUrl())
                .into(profilePictureIv);

        usernameTv.setText(mUser.getDisplayName());
        emailTv.setText(mUser.getEmail());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        HomeFragment homeFragment = HomeFragment.newInstance(mGroup.getUpdates(), mGroup);
        HomeFragment homeFragment1 = HomeFragment.newInstance(mGroup.getUpdates(), mGroup);
        HomeFragment homeFragment2 = HomeFragment.newInstance(mGroup.getUpdates(), mGroup);
        HomeFragment homeFragment3 = HomeFragment.newInstance(mGroup.getUpdates(), mGroup);
        HomeFragment homeFragment4 = HomeFragment.newInstance(mGroup.getUpdates(), mGroup);
        adapter.addFragment(homeFragment, "");
        adapter.addFragment(homeFragment1, "");
        adapter.addFragment(homeFragment2, "");
        adapter.addFragment(homeFragment3, "");
        adapter.addFragment(homeFragment4, "");
        viewPager.setAdapter(adapter);
    }


}
