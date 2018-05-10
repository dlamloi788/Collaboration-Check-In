package com.dlamloi.MAD.activity;

import android.content.DialogInterface;
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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dlamloi.MAD.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateMeetingActivity extends AppCompatActivity {

    @BindView(R.id.meeting_name_edittext) EditText mMeetingNameEt;
    @BindView(R.id.meeting_date_edittext) EditText mMeetingDateEt;
    @BindView(R.id.meeting_time_edittext) EditText mMeetingTimeEt;
    @BindView(R.id.meeting_location_edittext) EditText mMeetingLocationEt;
    @BindView(R.id.meeting_agenda_edittext) EditText mMeetingAgendaEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.new_meeting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.cross_icon));
        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

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

