package com.dlamloi.MAD.viewtask;

import com.dlamloi.MAD.model.Task;

/**
 * Created by Don on 30/05/2018.
 */

public interface ViewTaskContract {

    interface View {


        void bindTaskData(String title, String detail, String dueDate);

        void leave();

        void showTaskCompleteError();

        void showTaskCompleteToast();
    }


    interface Presenter {

        void initTaskData();

        void onDataReceive(Task task);

        void leave();

        void taskCompleted();

    }

    interface TaskDataListener {

    }

}
