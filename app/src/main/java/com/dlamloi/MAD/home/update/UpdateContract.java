package com.dlamloi.MAD.home.update;

import com.dlamloi.MAD.base.BaseInteractor;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Update;

/**
 * Created by Don on 16/05/2018.
 */

public interface UpdateContract {

    interface View extends BaseView<Update> {


    }

    interface Presenter {

    }

    interface Interactor extends BaseInteractor {
    }


    interface UpdateListener {

        void onUpdateAdd(Update update);
    }
}
