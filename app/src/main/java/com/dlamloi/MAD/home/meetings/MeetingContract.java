package com.dlamloi.MAD.home.meetings;

import com.dlamloi.MAD.base.BaseInteractor;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Meeting;

/**
 * Created by Don on 16/05/2018.
 */

public interface MeetingContract {

    interface View extends BaseView<Meeting> {

    }

    interface Presenter {

    }

    interface MeetingListener {

        void onMeetingAdd(Meeting meeting);

    }

    interface Interactor extends BaseInteractor {
    }
}
