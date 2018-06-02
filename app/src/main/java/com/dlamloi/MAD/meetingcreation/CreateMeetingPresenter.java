package com.dlamloi.MAD.meetingcreation;

import android.app.Activity;
import android.content.Intent;

import com.dlamloi.MAD.model.Meeting;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;
import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;
import com.dlamloi.MAD.utilities.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Don on 17/05/2018.
 */

/**
 * This class handles the presentation logic when creating a meeting
 */
public class CreateMeetingPresenter implements CreateMeetingContract.Presenter {

    private final CreateMeetingContract.View mView;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;
    public static final String DATE_PATTERN = "dd MMMM yyyy";

    /**
     * Creates an instance the create meeting presenter
     *
     * @param view the view that the presenter is moderating
     * @param groupId the id of the group that the user is currently in
     */
    public CreateMeetingPresenter(CreateMeetingContract.View view, String groupId) {
        mView = view;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager(groupId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectLocation(String location) {
        if (location.isEmpty()) {
            mView.startSelectLocation();
        } else {
            mView.showBottomSheetDialog();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CreateMeetingActivity.PLACE_PICKER_REQUEST:
                    mView.setPlaceText(data);

            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void meetingTimeClick() {
        Calendar date = Calendar.getInstance();
        int hourOfDay = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        mView.showTimeDialog(hourOfDay, minute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timePicked(int hourOfDay, int minute) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
        date.set(Calendar.MINUTE, minute);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String time = timeFormat.format(date.getTime());
        mView.setMeetingTime(time);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shouldCreateBeEnabled(String name, String date, String time, String location) {
        if (Utility.areAnyRequiredFieldsEmpty(name, date, time, location)) {
            mView.disableCreateButton();
        } else {
            mView.enableCreateButton();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createMeeting(String meetingTitle, String meetingDate, String meetingTime, String meetingLocation, String meetingAgenda) {
        String meetingCreator = new FirebaseAuthenticationManager().getCurrentUserDisplayName();
        Meeting meeting = new Meeting(meetingCreator, meetingTitle, meetingDate, meetingTime,
                meetingLocation, meetingAgenda);
        mFirebaseRepositoryManager.addMeeting(meeting);
        mView.leave();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void homeButtonPressed(String name, String date, String time, String location, String agenda) {
        if (areAllFieldsEmpty(name, date, time, location, agenda)) {
            mView.leave();
        } else {
            mView.showLeaveConfirmationDialog();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void meetingDateEtClicked() {
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        mView.showDateDialog(year, month, day);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void datePicked(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String date = dateFormat.format(calendar.getTime());
        mView.setMeetingDate(date);
    }

    /**
     * Determines if there are any empty edittexts
     *
     * @param texts the text from the edittexts
     * @return true all provided texts are empty; otherwise false
     */
    private boolean areAllFieldsEmpty(String... texts) {
        for (String text : texts) {
            if (!text.isEmpty()) {
                return false;
            }
        }
        return true;
    }


}
