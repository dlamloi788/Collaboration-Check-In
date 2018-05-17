package com.dlamloi.MAD.meetingcreation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.meetings.MeetingFragment;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.model.Meeting;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateMeetingActivity extends AppCompatActivity implements CreateMeetingContract.View {

    public static final int PLACE_PICKER_REQUEST = 1;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Group mGroup;
    private Place place;
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
    @BindView(R.id.create_meeting_layout)
    CoordinatorLayout mCreateMeetingLayout;


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
        mGroup = getIntent().getParcelableExtra(MeetingFragment.GROUP_KEY);
        mCreateMeetingPresenter = new CreateMeetingPresenter(this, mGroup.getId());


    }

    @OnClick(R.id.meeting_location_edittext)
    public void meetingLocationEtClicked() {
        if (mMeetingLocationEt.getText().toString().isEmpty()) {
            mCreateMeetingPresenter.selectLocation();
        } else {
            setUpBottomSheetDialog();
        }
    }

    /**
     * Starts the PlacePicker Intent to allow the user to select a location
     */
    private void selectLocation() {
        //Select location on tap of edittext :)
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    /**
     * Opens up a menu at the bottom of the screen if the user attempts to
     * change the location after a location has been chosen
     */
    private void setUpBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog =
                new BottomSheetDialog(this);
        View bottomSheetView = CreateMeetingActivity.this.getLayoutInflater()
                .inflate(R.layout.bottom_sheet_dialog, null);

        bottomSheetDialog.setContentView(bottomSheetView);

        LinearLayout removeLocationLinearLayout =
                bottomSheetDialog.findViewById(R.id.remove_meeting_location_linearlayout);
        removeLocationLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place = null;
                mMeetingLocationEt.getText().clear();
                bottomSheetDialog.dismiss();
            }
        });

        LinearLayout changeLocationLinearLayout =
                bottomSheetDialog.findViewById(R.id.change_meeting_location_linearlayout);
        changeLocationLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLocation();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    @OnClick(R.id.meeting_date_edittext)
    public void meetingDateEtClicked() {
        Calendar date = Calendar.getInstance();
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar date = Calendar.getInstance();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY");
                                date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                date.set(Calendar.MONTH, month);
                                date.set(Calendar.YEAR, year);
                                String dateString = dateFormat.format(date.getTime());
                                mMeetingDateEt.setText(dateString);
                            }
                        },
                        date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.DAY_OF_MONTH)
                );
        datePickerDialog.show();
    }

    @OnClick(R.id.meeting_time_edittext)
    public void meetingTimeEtClicked() {
        mCreateMeetingPresenter.meetingTimeClick();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_meeting_menu, menu);
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
    public void startShowLocation(PlacePicker.IntentBuilder builder) throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        Intent intent = builder.build(this);
        startActivityForResult(intent, PLACE_PICKER_REQUEST);
    }

    @Override
    public void setPlaceText(Intent data) {
        Place place = PlacePicker.getPlace(this, data);
        mMeetingLocationEt.setText(place.getAddress());
    }



    /**
     * Returns true if any edittexts on the UI are empty; otherwise false
     *
     * @return true if no edittexts are empty; false otherwise.
     */
    private boolean areAllFieldsEmpty() {
        EditText[] editTexts = {mMeetingNameEt, mMeetingDateEt,
                mMeetingTimeEt, mMeetingLocationEt, mMeetingAgendaEt};

        for (EditText editText : editTexts) {
            if (!editText.getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private String getSuburbName() {
        Geocoder geocoder = new Geocoder(this);
        String suburb;
        try {
            List<Address> address = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
            suburb = address.get(0).getLocality();

        } catch (IOException e) {
            suburb = "";
        }
        return suburb;
    }


    @Override
    public void onBackPressed() {
        if (!areAllFieldsEmpty()) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            AlertDialog leaveConfirmationDialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert).create();
            leaveConfirmationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            leaveConfirmationDialog.setCancelable(false);
            leaveConfirmationDialog.setMessage(getString(R.string.quit_scheduling_meeting));

            leaveConfirmationDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            leaveConfirmationDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            leaveConfirmationDialog.dismiss();
                        }
                    });

            leaveConfirmationDialog.show();
        } else {
            finish();
        }
    }

    @Override
    public void showDateCalendar() {
        Calendar date = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                    Calendar time = Calendar.getInstance();
                    time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    time.set(Calendar.MINUTE, minute);
                    mMeetingTimeEt.setText(timeFormat.format(time.getTime()));
                },
                date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE),
                false);
        timePickerDialog.show();
    }

    @Override
    public void meetingPublished() {
        finish();
    }


}
