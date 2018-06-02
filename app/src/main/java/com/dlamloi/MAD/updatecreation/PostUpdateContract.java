package com.dlamloi.MAD.updatecreation;

/**
 * Created by Don on 17/05/2018.
 */

public interface PostUpdateContract {

    interface View {

        void leave();

        void disablePublishButton();

        void enablePublishButton();

        void showAlertDialog();
    }

     interface Presenter {

         void publishUpdate(String updateTitle, String updateInformation);

         void shouldPublishBeEnabled(String updateTitle);

         void homeButtonPressed(String s);
     }

}
