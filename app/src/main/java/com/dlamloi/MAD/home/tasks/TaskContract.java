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


        ArrayList<Task> getTasks();

        void taskCompleted(int index);
    }

    interface Presenter extends BasePresenter{
        
    }

    interface Interactor extends BaseInteractor {



    }

    interface TaskListener {
        void onTaskAdd(Task tasks);


        void onTaskComplete(String taskId);
    }

    interface TaskItemClickListener {

        void taskClick(String taskId);
    }
}
