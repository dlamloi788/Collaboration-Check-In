package com.dlamloi.MAD.home.meetings;

import com.dlamloi.MAD.base.BaseInteractor;
import com.dlamloi.MAD.base.BasePresenter;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Meeting;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

public interface MeetingContract {

    interface View extends BaseView<Meeting> {

    }

    interface Presenter extends BasePresenter<Meeting> {

    }

    interface OnMeetingListener {

        void onMeetingAdd(ArrayList<Meeting> meetings);

    }

    interface Interactor extends BaseInteractor {
    }
}
