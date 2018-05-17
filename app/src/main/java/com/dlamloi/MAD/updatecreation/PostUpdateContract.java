package com.dlamloi.MAD.updatecreation;

/**
 * Created by Don on 17/05/2018.
 */

public interface PostUpdateContract {

    interface View {

        void leave();
    }

     interface Presenter {

         void publishUpdate(String updateTitle, String updateInformation);
     }

}
