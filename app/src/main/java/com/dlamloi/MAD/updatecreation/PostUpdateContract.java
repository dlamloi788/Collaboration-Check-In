package com.dlamloi.MAD.updatecreation;

/**
 * Created by Don on 17/05/2018.
 */

/**
 * The contract between the post update view and presenter
 */
public interface PostUpdateContract {

    /**
     * The associated view in the contract
     */
    interface View {

        /**
         * Leaves the activity
         */
        void leave();

        /**
         * Disables the publish menu button
         */
        void disablePublishButton();

        /**
         * Enables the publish menu button
         */
        void enablePublishButton();

        /**
         * Shows the leave confirmation alert dialog
         */
        void showAlertDialog();
    }

    /**
     * The associated presenter in the contract
     */
    interface Presenter {

        /**
         * Creates the update and calls the repository manager to save it
         *
         * @param updateTitle
         * @param updateInformation
         */
        void publishUpdate(String updateTitle, String updateInformation);

        /**
         * Enables the publish button if the update title is not empty otherwise it is disabled
         *
         * @param updateTitle the title of the update
         */
        void shouldPublishBeEnabled(String updateTitle);

        /**
         * Shows a leave confirmation dialog if the update title is empty
         * otherwise it just leaves
         *
         * @param updateTitle the title of the update
         */
        void homeButtonPressed(String updateTitle);
    }

}
