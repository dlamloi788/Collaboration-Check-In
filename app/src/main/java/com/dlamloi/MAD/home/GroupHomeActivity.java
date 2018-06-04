package com.dlamloi.MAD.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
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

/**
 * This class is responsible for handling the home page of the selected group
 */
public class GroupHomeActivity extends AppCompatActivity implements GroupHomeContract.View {


    public static final String GROUP_KEY = "group";
    public static final String FILE_TYPE = "*/*";
    public static final int FILE_MANAGER_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 2;

    private GroupHomeContract.Presenter mGroupHomePresenter;
    private ViewPagerAdapter mViewPagerAdapter;
    private CircleImageView mProfileImageIv;
    private TextView mFirstNameTv;
    private TextView mEmailTv;
    private AlertDialog mUploadAlertDialog;
    private EditText mFileNameEditText;
    private TextView mUploadDialogErrorTv;

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
    @BindView(R.id.download_progressbar)
    CircularProgressBar mDownloadCPb;

    /**
     * {@inheritDoc}
     */
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
        initTabLayout();
        mGroupId = getIntent().getStringExtra(ViewGroupsActivity.GROUP_ID_KEY);
        setupUploadDialog();
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


    /**
     * Handles the post update button click
     */
    @OnClick(R.id.post_update_button)
    public void postUpdateButtonClick() {
        mGroupHomePresenter.postUpdate();
        mGroupHomePresenter.onActionMenuItemSelected();
    }

    /**
     * Handles the schedule meeting button click
     */
    @OnClick(R.id.schedule_meeting_button)
    public void scheduleMeetingButtonClick() {
        mGroupHomePresenter.scheduleMeeting();
        mGroupHomePresenter.onActionMenuItemSelected();

    }

    /**
     * Handles the assign task button click
     */
    @OnClick(R.id.assign_task_button)
    public void assignTaskButtonClick() {
        mGroupHomePresenter.assignTask();
        mGroupHomePresenter.onActionMenuItemSelected();
    }

    /**
     * Handles the upload file button click
     */
    @OnClick(R.id.upload_file_button)
    public void uploadFileButtonClick() {
        mGroupHomePresenter.uploadFile();
        mGroupHomePresenter.onActionMenuItemSelected();
    }

    /**
     * Handles the camera upload button click
     */
    @OnClick(R.id.upload_camera_button)
    public void uploadCameraButtonClick() {
        mGroupHomePresenter.generateImageURI(this);
        mGroupHomePresenter.onActionMenuItemSelected();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_MANAGER_REQUEST_CODE:
                mGroupHomePresenter.uploadFile(resultCode, data);
                break;

            case CAMERA_REQUEST_CODE:
                mGroupHomePresenter.generateImageURI(resultCode, mPhotoPath);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGroupTitle(String title) {
        setTitle(title);
    }

    /**
     * Sets up the tablayout with a viewpager and attaches specified icons
     */
    private void initTabLayout() {
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

    /**
     * Sets up the material drawer in the toolbar
     *
     * @param toolbar the toolbar which the drawer is attached to
     */
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

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.view_your_groups).withIcon(R.drawable.group_icon).withIdentifier(1).withSelectable(false));
        drawer.addItem(new DividerDrawerItem());
        drawer.addItem(new PrimaryDrawerItem().withName(R.string.log_out).withIcon(R.drawable.logout_icon).withIdentifier(2).withSelectable(false));

    }

    /**
     * Sets up the upload dialog to prevent instantiation every time show dialog
     * is called
     */
    private void setupUploadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View uploadDialogView = LayoutInflater.from(this).inflate(R.layout.upload_file_dialog, null);
        builder.setView(uploadDialogView);

        mFileNameEditText = uploadDialogView.findViewById(R.id.upload_file_name_edittext);
        Button cancelButton = uploadDialogView.findViewById(R.id.upload_cancel_button);
        Button uploadButton = uploadDialogView.findViewById(R.id.upload_upload_button);
        mUploadDialogErrorTv = uploadDialogView.findViewById(R.id.upload_dialog_error_textview);

        mUploadAlertDialog = builder.create();
        mUploadAlertDialog.setCancelable(false);

        cancelButton.setOnClickListener(view -> {
            mUploadAlertDialog.dismiss();
            clearFileName();
            hideFileNameError();
        });
        uploadButton.setOnClickListener(view -> {
            String fileName = mFileNameEditText.getText().toString().trim();
            mGroupHomePresenter.uploadFile(fileName);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProfileImage(String url) {
        Glide.with(this).load(url).into(mProfileImageIv);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDisplayName(String displayName) {
        mFirstNameTv.setText(displayName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEmail(String email) {
        mEmailTv.setText(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void showShadow() {
        mShadowView.setVisibility(View.VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideShadow() {
        mShadowView.setVisibility(View.INVISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collapseActionMenu() {
        mFloatingActionsMenu.collapse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showUploadProgressbar() {
        mUploadCPb.setProgress(0);
        mUploadCPb.setVisibility(View.VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideUploadProgressbar() {
        mUploadCPb.setVisibility(View.GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDownloadProgressbar() {
        mDownloadCPb.setProgress(0);
        mDownloadCPb.setVisibility(View.VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideDownloadProgressbar() {
        mDownloadCPb.setVisibility(View.GONE);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUploadProgressBar(int currentProgress) {
        mUploadCPb.setProgressWithAnimation(currentProgress, 500);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDownloadProgressBar(int currentProgress) {
        mDownloadCPb.setProgressWithAnimation(currentProgress, 500);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void showUploadCompleteToast() {
        Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showSetFileNameDialog() {
        mUploadAlertDialog.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void navigateToPostUpdate() {
        Intent intent = new Intent(this, PostUpdateActivity.class);
        intent.putExtra(GROUP_KEY, mGroupId);
        Utility.startIntent(this, intent);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void navigateToScheduleMeeting() {
        Intent intent = new Intent(this, CreateMeetingActivity.class);
        intent.putExtra(GROUP_KEY, mGroupId);
        Utility.startIntent(this, intent);


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void navigateToAssignTask() {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        intent.putExtra(GROUP_KEY, mGroupId);
        Utility.startIntent(this, intent);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void navigateToUploadFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(FILE_TYPE);
        startActivityForResult(intent, FILE_MANAGER_REQUEST_CODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void navigateToCameraUpload(String timeStamp) {
        String imageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        File filename = new File(imageDirectory + "_" + timeStamp + ".jpg");
        mPhotoPath = filename.getPath();
        Uri imageUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", filename);

        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(captureImageIntent, CAMERA_REQUEST_CODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showFab() {
        mFloatingActionsMenu.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideFab() {
        mFloatingActionsMenu.animate().translationY(mFloatingActionsMenu.getHeight() + 16).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goBackToGroups() {
        finish();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void hideEnterFileNameDialog() {
        mUploadAlertDialog.hide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shakeUploadFileName() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        mFileNameEditText.startAnimation(shake);
    }

    @Override
    public void showFileNameError() {
        mUploadDialogErrorTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFileNameError() {
        mUploadDialogErrorTv.setVisibility(View.GONE);
    }

    @Override
    public void clearFileName() {
        mFileNameEditText.getText().clear();
    }
}
