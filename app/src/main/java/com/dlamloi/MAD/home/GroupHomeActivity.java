package com.dlamloi.MAD.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlamloi.MAD.BuildConfig;
import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.chat.MessagesFragment;
import com.dlamloi.MAD.home.files.FileFragment;
import com.dlamloi.MAD.home.meetings.MeetingFragment;
import com.dlamloi.MAD.home.tasks.TaskFragment;
import com.dlamloi.MAD.home.update.UpdateFragment;
import com.dlamloi.MAD.login.LoginActivity;
import com.dlamloi.MAD.meetingcreation.CreateMeetingActivity;
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
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class GroupHomeActivity extends AppCompatActivity implements GroupHomeContract.View {


    public static final String GROUP_KEY = "group";
    public static final String FILE_TYPE = "*/*";
    public static final int FILE_MANAGER_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 2;
    private static final String IMAGE_DIRECTORY = "Image Directory";

    private GroupHomePresenter mGroupHomePresenter;
    private ViewPagerAdapter mViewPagerAdapter;
    private CircleImageView mProfileImageIv;
    private TextView mFirstNameTv;
    private TextView mEmailTv;

    private String mGroupId;
    private String mPhotoPath;

    @BindView(R.id.group_home_rootview)
    CoordinatorLayout mGroupHomeCoordinatorLayout;
    @BindView(R.id.container_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.floating_actions_menu)
    FloatingActionsMenu mFloatingActionsMenu;
    @BindView(R.id.post_update_button)
    FloatingActionButton mPostUpdateBtn;
    @BindView(R.id.schedule_meeting_button)
    FloatingActionButton mScheduleMeetingBtn;
    @BindView(R.id.assign_task_button)
    FloatingActionButton mAssignTaskBtn;
    @BindView(R.id.upload_file_button)
    FloatingActionButton mUploadFileBtn;
    @BindView(R.id.upload_camera_button)
    FloatingActionButton mCameraUploadBtn;
    @BindView(R.id.shadow_view)
    View mShadowView;
    @BindView(R.id.upload_progressbar)
    CircularProgressBar mUploadCPb;


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
        mGroupHomePresenter.postUpdate();
        mGroupHomePresenter.onActionMenuItemSelected();
    }

    @OnClick(R.id.schedule_meeting_button)
    public void scheduleMeetingButtonClick() {
        mGroupHomePresenter.scheduleMeeting();
        mGroupHomePresenter.onActionMenuItemSelected();

    }

    @OnClick(R.id.assign_task_button)
    public void assignTaskButtonClick() {
        mGroupHomePresenter.assignTask();
        mGroupHomePresenter.onActionMenuItemSelected();
    }

    @OnClick(R.id.upload_file_button)
    public void uploadFileButtonClick() {
        mGroupHomePresenter.uploadFile();
        mGroupHomePresenter.onActionMenuItemSelected();
    }

    @OnClick(R.id.upload_camera_button)
    public void uploadCameraButtonClick() {
        mGroupHomePresenter.cameraUpload();
        mGroupHomePresenter.onActionMenuItemSelected();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_MANAGER_REQUEST_CODE:
                mGroupHomePresenter.uploadFile(resultCode, data);
                break;

            case CAMERA_REQUEST_CODE:
                mGroupHomePresenter.cameraUpload(resultCode, mPhotoPath);
                break;
        }
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

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mGroupHomePresenter.shouldFabBeHidden(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


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

    @Override
    public void setUpViewPager(String groupId) {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        UpdateFragment updateFragment = UpdateFragment.newInstance(groupId);
        MeetingFragment meetingFragment = MeetingFragment.newInstance(groupId);
        TaskFragment taskFragment = TaskFragment.newInstance(groupId);
        FileFragment fileFragment = FileFragment.newInstance(groupId);
        MessagesFragment messagesFragment = MessagesFragment.newInstance(groupId);
        mViewPagerAdapter.addFragment(updateFragment, "");
        mViewPagerAdapter.addFragment(meetingFragment, "");
        mViewPagerAdapter.addFragment(taskFragment, "");
        mViewPagerAdapter.addFragment(fileFragment, "");
        mViewPagerAdapter.addFragment(messagesFragment, "");
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void showShadow() {
        mShadowView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideShadow() {
        mShadowView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void collapseActionMenu() {
        mFloatingActionsMenu.collapse();
    }

    @Override
    public void showProgressbar() {
        mUploadCPb.setProgress(0);
        mUploadCPb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        mUploadCPb.setVisibility(View.INVISIBLE);
    }


    @Override
    public void updateProgressBar(int currentProgress) {
        mUploadCPb.setProgressWithAnimation(currentProgress, 500);
    }

    @Override
    public void showUploadCompleteToast() {
        Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSetFileNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View uploadDialogView = LayoutInflater.from(this).inflate(R.layout.upload_file_dialog, null);
        builder.setView(uploadDialogView);

        EditText fileNameEt = uploadDialogView.findViewById(R.id.upload_file_name_edittext);
        Button cancelButton = uploadDialogView.findViewById(R.id.upload_cancel_button);
        Button uploadButton = uploadDialogView.findViewById(R.id.upload_upload_button);

        AlertDialog uploadDialog = builder.create();
        uploadDialog.setCancelable(false);

        cancelButton.setOnClickListener(view -> uploadDialog.dismiss());
        uploadButton.setOnClickListener(view -> {
            String fileName = fileNameEt.getText().toString().trim();
            mGroupHomePresenter.uploadFile(fileName);
            uploadDialog.dismiss();
        });

        uploadDialog.show();
    }

    @Override
    public File getImageDirectory() {
        return getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Override
    public void navigateToPostUpdate() {
        Intent intent = new Intent(this, PostUpdateActivity.class);
        intent.putExtra(GROUP_KEY, mGroupId);
        Utility.startIntent(this, intent);


    }

    @Override
    public void navigateToScheduleMeeting() {
        Intent intent = new Intent(this, CreateMeetingActivity.class);
        intent.putExtra(GROUP_KEY, mGroupId);
        Utility.startIntent(this, intent);


    }

    @Override
    public void navigateToAssignTask() {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        intent.putExtra(GROUP_KEY, mGroupId);
        Utility.startIntent(this, intent);

    }

    @Override
    public void navigateToUploadFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(FILE_TYPE);
        startActivityForResult(intent, FILE_MANAGER_REQUEST_CODE);
    }

    @Override
    public void navigateToCameraUpload(String timeStamp) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);

        } else {
            Log.d(IMAGE_DIRECTORY, getImageDirectory().getPath());
            File filename = new File(getImageDirectory().getPath() + "_" + timeStamp + ".jpg");
            mPhotoPath = filename.getPath();
            Uri imageUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", filename);

            Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(captureImageIntent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void showFab() {
        mFloatingActionsMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFab() {
        mFloatingActionsMenu.setVisibility(View.INVISIBLE);
    }

}
