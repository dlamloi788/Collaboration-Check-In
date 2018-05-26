package com.dlamloi.MAD.viewgroups;

import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Group;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

/**
 * Created by Don on 14/05/2018.
 */

public interface ViewGroupContract {

    interface View extends BaseView<Group> {

        void setLoadingProgressBarVisibility(boolean visibility);

        void logout();

        void setProfileImage(String url);

        void setDisplayName(String displayName);

        void setEmail(String surname);

        void notifyItemInserted(int position);

        void notifyItemChanged(int index);

        void populateRecyclerView(ArrayList<Group> groups);
    }

    interface Presenter {

        void dataAdded();

        void logout();

        void loadProfileData();

        boolean onDrawerItemClicked(int position, IDrawerItem drawerItem);

    }

    interface Interactor {

        void onGroupReceive(Group group);

        String[] retrieveUserInformation();

        void signout();

    }

    interface ViewGroupListener {

        void onGroupAdd(ArrayList<Group> groups);

    }




}
