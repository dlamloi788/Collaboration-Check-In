package com.dlamloi.MAD.meetingcreation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.GroupHomeActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class CreateMeetingActivity extends AppCompatActivity implements CreateMeetingContract.View {

    public static final int PLACE_PICKER_REQUEST = 1;

    private String mGroupId;
    private CreateMeetingPresenter mCreateMeetingPresenter;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.new_meeting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.cross_icon));
        ButterKnife.bind(this);
        mGroupId = getIntent().getStringExtra(GroupHomeActivity.GROUP_KEY);
        mCreateMeetingPresenter = new CreateMeetingPresenter(this, mGroupId);
        setUpPlacePickerIntent();
        setUpBottomSheetDialog();
    }

    private void setUpPlacePickerIntent() {
        try {
            placePickerIntent = new PlacePicker.IntentBuilder().build(this);
        } catch (GooglePlayServicesRepairableException e) {
            placePickerIntent = null;
        } catch (GooglePlayServicesNotAvailableException e) {
            placePickerIntent = null;
        }

    }

    @OnClick(R.id.meeting_location_edittext)
    public void meetingLocationEtClicked() {
        mCreateMeetingPresenter.selectLocation(mMeetingLocationEt.getText().toString());
    }

    @OnClick(R.id.meeting_date_edittext)
    public void meetingDateEtClicked() {
        mCreateMeetingPresenter.meetingDateEtClicked();
    }

    @OnClick(R.id.meeting_time_edittext)
    public void meetingTimeEtClicked() {
        mCreateMeetingPresenter.meetingTimeClick();
    }

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_meeting_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCreateMeetingPresenter.result(requestCode, resultCode, data);
    }

    @Override
    public void setPlaceText(Intent data) {
        Place place = PlacePicker.getPlace(this, data);
        mMeetingLocationEt.setText(place.getAddress());
    }

    @Override
    public void showDateDialog(int currentYear, int currentMonth, int currentDayOfMonth) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    mCreateMeetingPresenter.datePicked(year, month, dayOfMonth);
                }, currentYear, currentMonth, currentDayOfMonth);

        datePickerDialog.show();
    }

    @Override
    public void setMeetingDate(String date) {
        mMeetingDateEt.setText(date);
    }

    @Override
    public void onBackPressed() {
        AlertDialog leaveConfirmationDialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert).create();
        mCreateMeetingPresenter.onBackPressed(leaveConfirmationDialog,
                mMeetingNameEt.getText().toString(),
                mMeetingDateEt.getText().toString(),
                mMeetingTimeEt.getText().toString(),
                mMeetingLocationEt.getText().toString(),
                mMeetingAgendaEt.getText().toString()
        );
    }

    @Override
    public void showTimeDialog(int currentHourOfDay, int currentMinute) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    mCreateMeetingPresenter.timePicked(hourOfDay, minute);
                }, currentHourOfDay, currentMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void setTimeText(String time) {
        mMeetingTimeEt.setText(time);
    }

    @Override
    public void enableCreateButton() {
        mMenu.findItem(R.id.create_meeting_menu_button).setEnabled(true);
    }

    @Override
    public void disableCreateButton() {
        mMenu.findItem(R.id.create_meeting_menu_button).setEnabled(false);
    }

    @Override
    public void showBottomSheetDialog() {
        mBottomSheetDialog.show();
    }

    @Override
    public void startSelectLocation() {
        startActivityForResult(placePickerIntent, PLACE_PICKER_REQUEST);
    }

    @Override
    public void meetingPublished() {
        finish();
    }

    @Override
    public void leave() {
        finish();
    }

    @Override
    public void showLeaveConfirmationDialog(AlertDialog leaveConfirmationDialog) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
        leaveConfirmationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        leaveConfirmationDialog.setCancelable(false);
        leaveConfirmationDialog.setMessage(getString(R.string.quit_scheduling_meeting));

        leaveConfirmationDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.yes),
                (dialog, which) -> finish());

        leaveConfirmationDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.no),
                (dialog, which) -> mCreateMeetingPresenter.leaveDialogNoClick(leaveConfirmationDialog));

        leaveConfirmationDialog.show();
    }

    @Override
    public void hideLeaveDialog(AlertDialog leaveConfirmationDialog) {
        leaveConfirmationDialog.dismiss();
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

