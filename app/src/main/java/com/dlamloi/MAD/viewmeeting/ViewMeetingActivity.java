package com.dlamloi.MAD.viewmeeting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.home.meetings.MeetingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewMeetingActivity extends AppCompatActivity implements ViewMeetingContract.View{

    @BindView(R.id.view_meeting_title_textview)
    TextView mMeetingTitleTv;
    @BindView(R.id.view_meeting_date_textview)
    TextView mMeetingDateTv;
    @BindView(R.id.view_meeting_time_textview)
    TextView mMeetingTimeTv;
    @BindView(R.id.view_meeting_location_textview)
    TextView mMeetingLocationTv;
    @BindView(R.id.view_meeting_agenda_textview)
    TextView mMeetingAgendaTv;

    private ViewMeetingContract.Presenter mViewMeetingPresenter;
    private String mGroupId;
    private String mMeetingId;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cross_icon);
        setTitle(getString(R.string.view_meeting));
        ButterKnife.bind(this);
        mGroupId = getIntent().getStringExtra(GroupHomeActivity.GROUP_KEY);
        mMeetingId = getIntent().getStringExtra(MeetingFragment.MEETING_ID);
        mViewMeetingPresenter = new ViewMeetingPresenter(this, mGroupId, mMeetingId);
        mViewMeetingPresenter.initData();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindData(String meetingTitle, String meetingTime, String meetingDate, String meetingLocation, String agenda) {
        mMeetingTitleTv.setText(meetingTitle);
        mMeetingDateTv.setText(meetingDate);
        mMeetingTimeTv.setText(meetingTime);
        mMeetingLocationTv.setText(meetingLocation);
        mMeetingAgendaTv.setText(agenda);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leave() {
        finish();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mViewMeetingPresenter.leave();
        }

        return super.onOptionsItemSelected(item);
    }
}
