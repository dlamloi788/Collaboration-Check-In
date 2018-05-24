package com.dlamloi.MAD.home.update;

import com.dlamloi.MAD.base.BaseInteractor;
import com.dlamloi.MAD.base.BasePresenter;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Update;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

public interface UpdateContract {

    interface View extends BaseView<Update> {


    }

    interface Presenter extends BasePresenter<Update> {

    }

    interface Interactor extends BaseInteractor {
    }


    interface OnUpdateListener {

        void onUpdateAdd(ArrayList<Update> updates);
    }
}
