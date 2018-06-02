package com.dlamloi.MAD.meetingcreation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.groupcreation.CreateGroupContract;
import com.dlamloi.MAD.groupcreation.CreateGroupPresenter;
import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.taskcreation.CreateTaskContract;
import com.dlamloi.MAD.utilities.Utility;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * This class is reponsible for showing the create meeting activity UI
 */
public class CreateMeetingActivity extends AppCompatActivity implements CreateMeetingContract.View {

    public static final int PLACE_PICKER_REQUEST = 1;

    private String mGroupId;
    private CreateMeetingContract.Presenter mCreateMeetingPresenter;

    @BindView(R.id.meeting_name_edittext)
    EditText mMeetingNameEt;
    @BindView(R.id.meeting_date_edittext)
    EditText mMeetingDateEt;
    @BindView(R.id.meeting_time_edittext)
    EditText mMeetingTimeEt;
    @BindView(R.id.meeting_location_edittext)
    EditText mMeetingLocationEt;
    @BindView(R.id.meeting_agenda_edittext)
    EditText mMeetingAgendaEt;

    private Menu mMenu;
    private BottomSheetDialog mBottomSheetDialog;
    private Intent placePickerIntent;
    private AlertDialog mLeaveAlertDialog;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.new_meeting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.cross_icon));
        ButterKnife.bind(this);
        setUpPlacePickerIntent();
        mGroupId = getIntent().getStringExtra(GroupHomeActivity.GROUP_KEY);
        mCreateMeetingPresenter = new CreateMeetingPresenter(this, mGroupId);
        setUpBottomSheetDialog();
        mLeaveAlertDialog = Utility.setUpLeaveAlertDialog(this, getString(R.string.quit_scheduling_meeting));
    }

    /**
     * Sets up place picker intent
     */
    private void setUpPlacePickerIntent() {
        try {
            placePickerIntent = new PlacePicker.IntentBuilder().build(CreateMeetingActivity.this);
        } catch (GooglePlayServicesRepairableException e) {
            placePickerIntent = null;
        } catch (GooglePlayServicesNotAvailableException e) {
            placePickerIntent = null;
        }
    }

    /**
     * Opens google picker map when the meeting location edittext has been tapped
     */
    @OnClick(R.id.meeting_location_edittext)
    public void meetingLocationEtClicked() {
        mCreateMeetingPresenter.selectLocation(mMeetingLocationEt.getText().toString());
    }

    /**
     * Opens the date dialog when the meeting date edittext is tapped on
     */
    @OnClick(R.id.meeting_date_edittext)
    public void meetingDateEtClicked() {
        mCreateMeetingPresenter.meetingDateEtClicked();
    }

    /**
     * Opens the time dialog when the meeting time edittext is tapped on
     */
    @OnClick(R.id.meeting_time_edittext)
    public void meetingTimeEtClicked() {
        mCreateMeetingPresenter.meetingTimeClick();
    }

    /**
     * Determines whether the create menu button should be enabled, if all text is empty disabled
     * otherwise enabled
     */
    @OnTextChanged(value = {R.id.meeting_name_edittext, R.id.meeting_date_edittext,
            R.id.meeting_time_edittext, R.id.meeting_location_edittext, R.id.meeting_agenda_edittext},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void shouldCreateBeEnabled() {
        mCreateMeetingPresenter.shouldCreateBeEnabled(
                mMeetingNameEt.getText().toString(),
                mMeetingDateEt.getText().toString(),
                mMeetingTimeEt.getText().toString(),
                mMeetingLocationEt.getText().toString()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_meeting_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mCreateMeetingPresenter.homeButtonPressed(mMeetingNameEt.getText().toString(),
                        mMeetingDateEt.getText().toString(),
                        mMeetingTimeEt.getText().toString(),
                        mMeetingLocationEt.getText().toString(),
                        mMeetingAgendaEt.getText().toString());
                break;

            case R.id.create_meeting_menu_button:
                String meetingTitle = mMeetingNameEt.getText().toString();
                String meetingDate = mMeetingDateEt.getText().toString();
                String meetingTime = mMeetingTimeEt.getText().toString();
                String meetingLocation = mMeetingLocationEt.getText().toString();
                String meetingAgenda = mMeetingAgendaEt.getText().toString();

                mCreateMeetingPresenter.createMeeting(meetingTitle, meetingDate, meetingTime,
                        meetingLocation, meetingAgenda);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCreateMeetingPresenter.result(requestCode, resultCode, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlaceText(Intent data) {
        Place place = PlacePicker.getPlace(this, data);
        mMeetingLocationEt.setText(place.getAddress());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDateDialog(int currentYear, int currentMonth, int currentDayOfMonth) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    mCreateMeetingPresenter.datePicked(year, month, dayOfMonth);
                }, currentYear, currentMonth, currentDayOfMonth);

        datePickerDialog.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMeetingDate(String date) {
        mMeetingDateEt.setText(date);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showTimeDialog(int currentHourOfDay, int currentMinute) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    mCreateMeetingPresenter.timePicked(hourOfDay, minute);
                }, currentHourOfDay, currentMinute, false);
        timePickerDialog.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMeetingTime(String time) {
        mMeetingTimeEt.setText(time);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enableCreateButton() {
        mMenu.findItem(R.id.create_meeting_menu_button).setEnabled(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableCreateButton() {
        mMenu.findItem(R.id.create_meeting_menu_button).setEnabled(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showBottomSheetDialog() {
        mBottomSheetDialog.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startSelectLocation() {
        startActivityForResult(placePickerIntent, PLACE_PICKER_REQUEST);
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
    public void showLeaveConfirmationDialog() {
        mLeaveAlertDialog.show();
    }


    /**
     * Opens up a menu at the bottom of the screen if the user attempts to
     * change the location after a location has been chosen
     */
    private void setUpBottomSheetDialog() {
        mBottomSheetDialog =
                new BottomSheetDialog(this);
        View bottomSheetView = CreateMeetingActivity.this.getLayoutInflater()
                .inflate(R.layout.bottom_sheet_dialog, null);

        mBottomSheetDialog.setContentView(bottomSheetView);

        LinearLayout removeLocationLinearLayout = mBottomSheetDialog.findViewById(R.id.remove_meeting_location_linearlayout);
        removeLocationLinearLayout.setOnClickListener(v -> {
            mMeetingLocationEt.getText().clear();
            mBottomSheetDialog.dismiss();
        });

        LinearLayout changeLocationLinearLayout = mBottomSheetDialog.findViewById(R.id.change_meeting_location_linearlayout);
        changeLocationLinearLayout.setOnClickListener(v -> {
            mCreateMeetingPresenter.selectLocation(mMeetingLocationEt.getText().toString());
            mBottomSheetDialog.dismiss();
        });
    }
}

