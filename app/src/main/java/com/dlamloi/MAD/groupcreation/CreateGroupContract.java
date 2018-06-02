package com.dlamloi.MAD.groupcreation;

import java.util.ArrayList;

/**
 * Created by Don on 17/05/2018.
 */

/**
 * A contract between the view and presenter
 */
public interface CreateGroupContract {

    /**
     * The associated view in the contract
     */
    interface View {

        /**
         * Shows the floating action buttons
         */
        void showFab();

        /**
         * Hides the floating action button
         */
        void hideFab();

        /**
         * Displays a toast with a provided error message
         *
         * @param error the error message
         */
        void showEmailError(String error);

        /**
         * Leaves the activity
         */
        void leave();

        /**
         * Shows a dialog confirming the users depature
         */
        void showLeaveDialog();
    }

    /**
     * The associated presenter in the contract
     */
    interface Presenter {

        /**
         * Creates a group with the provided data
         *
         * @param groupName  the name of the group
         * @param userEmails the emails of the members of the group
         */
        void createGroup(String groupName, ArrayList<String> userEmails);

        /**
         * Determines whether the create menu button should be enabled
         *
         * @param groupName the name of the group
         * @param emails    the emails of the members of the group
         */
        void shouldCreateBeEnabled(String groupName, ArrayList<String> emails);

        /**
         * Determines whether a dialog confirming the users depature should be displayed
         *
         * @param groupName the group name
         * @param emails the emails of the members of the group
         */
        void homeButtonClicked(String groupName, ArrayList<String> emails);
    }

}
