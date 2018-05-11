package com.dlamloi.MAD.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dlamloi.MAD.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;


import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateMeetingActivity extends AppCompatActivity {

    public static final int PLACE_PICKER_REQUEST = 1;

    @BindView(R.id.meeting_name_edittext) EditText mMeetingNameEt;
    @BindView(R.id.meeting_date_edittext) EditText mMeetingDateEt;
    @BindView(R.id.meeting_time_edittext) EditText mMeetingTimeEt;
    @BindView(R.id.meeting_location_edittext) EditText mMeetingLocationEt;
    @BindView(R.id.meeting_agenda_edittext) EditText mMeetingAgendaEt;


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
        mMeetingLocationEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    try {
                        selectLocation();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void selectLocation() throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        //Select location on tap of edittext :)
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

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
                createMeeting();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_REQUEST:
                    Place place = PlacePicker.getPlace(this, data);
                    break;
            }
        }
    }

    private void createMeeting() {
        //Do some database stuff
        finish();
    }

    /**
     * Returns true if any edittexts on the UI are empty otherwise false
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
}

