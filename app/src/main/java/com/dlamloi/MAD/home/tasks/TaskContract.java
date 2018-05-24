package com.dlamloi.MAD.home.tasks;

import com.dlamloi.MAD.base.BaseInteractor;
import com.dlamloi.MAD.base.BasePresenter;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Task;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

public interface TaskContract {

    interface View extends BaseView<Task> {

    }

    interface Presenter extends BasePresenter<Task> {
        
    }

    interface Interactor extends BaseInteractor {



    }

    interface OnTaskListener {
        void onTaskAdd(ArrayList<Task> tasks);
    }
}
