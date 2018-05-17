package com.dlamloi.MAD.groupcreation;

import java.util.ArrayList;

/**
 * Created by Don on 17/05/2018.
 */

public class CreateGroupContract {

    interface View {

        void groupCreated();

        void showNoEmailEnteredToast();
    }

    interface Presenter {

        void createGroup(String groupName, ArrayList<String> userEmails);
    }

}
