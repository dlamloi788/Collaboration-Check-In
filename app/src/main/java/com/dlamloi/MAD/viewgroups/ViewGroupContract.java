package com.dlamloi.MAD.viewgroups;

import com.dlamloi.MAD.base.BasePresenter;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Group;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

/**
 * Created by Don on 14/05/2018.
 */

/**
 * The contract between the groups view and presenter
 */
public interface ViewGroupContract {

    /**
     * The associated view in the contract
     */
    interface View extends BaseView<Group> {

        /**
         * Hides the loading progressbar
         */
        void hideLoadingProgressBar();

        /**
         * Signs the user out
         */
        void logout();

        /**
         * Sets the profile picture imageview with the an image from provided url
         *
         * @param url the current users profile picture url
         */
        void setProfileImage(String url);

        /**
         * Sets the profile display name with the provided displayname
         *
         * @param displayName the current users displayname
         */
        void setDisplayName(String displayName);

        /**
         * Sets the profile email with the provided email
         *
         * @param email the current users email
         */
        void setEmail(String email);

        /**
         * Notifies the adapter that an item has been inserted at a position
         *
         * @param position the position the item was inserted at
         */
        void notifyItemInserted(int position);

        /**
         * Populates the recyclerview
         *
         * @param groups
         */
        void populateRecyclerView(ArrayList<Group> groups);

        /**
         * Starts the create group activity
         */
        void navigateToCreateGroup();
    }

    /**
     * The associated presenter in the contract
     */
    interface Presenter extends BasePresenter {

        /**
         * Hides the progressbar and shows the floating action button when data has been added to the
         * recyclerview
         */
        void dataAdded();

        /**
         * Signs the user out
         */
        void logout();

        /**
         * Loads the account header information from the database
         */
        void loadProfileData();

        /**
         * Handles item drawer clicks depending on which and where the item was selected
         *
         * @param position the position the item was selected at
         * @param drawerItem the item that was selected
         * @return true if successful
         */
        boolean onDrawerItemClicked(int position, IDrawerItem drawerItem);

        /**
         * Notifies the presenter that the recyclerview scroll state is different
         *
         * @param newState the new state of the recyclerview
         */
        void scrollStateChanged(int newState);

        /**
         * Calls the view to start the create group activity
         */
        void createGroupClicked();
    }

    /**
     * Listens for new groups added to the group
     */
    interface ViewGroupListener {

        /**
         * Notifies that a group was added to the database
         *
         * @param group the group that was recently added
         */
        void onGroupAdd(Group group);

    }

    /**
     * Listens for recyclerview row taps
     */
    interface GroupItemClickListener {

        /**
         * Notifies that a recyclerview row was tapped
         *
         * @param id the id of the group that was tapped
         * @param title the title of the group that was tapped
         */
        void groupClick(String id, String title);

    }
}
