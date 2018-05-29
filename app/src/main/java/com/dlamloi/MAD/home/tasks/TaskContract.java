package com.dlamloi.MAD.home.tasks;

import com.dlamloi.MAD.base.BaseInteractor;
import com.dlamloi.MAD.base.BasePresenter;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Task;

/**
 * Created by Don on 16/05/2018.
 */

public interface TaskContract {

    interface View extends BaseView<Task> {

    }

    interface Presenter extends BasePresenter{
        
    }

    interface Interactor extends BaseInteractor {



    }

    interface TaskListener {
        void onTaskAdd(Task tasks);
    }
}
