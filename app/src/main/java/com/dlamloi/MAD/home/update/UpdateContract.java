package com.dlamloi.MAD.home.update;

import com.dlamloi.MAD.base.BasePresenter;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Update;

/**
 * Created by Don on 16/05/2018.
 */

/**
 * The contract between the update view and presenter
 */
public interface UpdateContract {

    /**
     * The associated view in the contract
     */
    interface View extends BaseView<Update> {


    }

    /**
     * The associated presenter in the contract
     */
    interface Presenter extends BasePresenter {

    }

    /**
     * The listener for updates being added to the database
     */
    interface UpdateListener {

        /**
         * Notifies that an update has been added to the database
         *
         * @param update the update that was recently added
         */
        void onUpdateAdd(Update update);
    }
}
