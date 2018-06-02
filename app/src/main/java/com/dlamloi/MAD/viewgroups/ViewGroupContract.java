package com.dlamloi.MAD.viewgroups;

import com.dlamloi.MAD.base.BasePresenter;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Group;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

/**
 * Created by Don on 14/05/2018.
 */

public interface ViewGroupContract {

    interface View extends BaseView<Group> {



        void hideLoadingProgressBar();

        void logout();

        void setProfileImage(String url);

        void setDisplayName(String displayName);

        void setEmail(String surname);

        void notifyItemInserted(int position);

        void populateRecyclerView(ArrayList<Group> groups);

    }

    interface Presenter extends BasePresenter{

        void dataAdded();

        void logout();

        void loadProfileData();

        boolean onDrawerItemClicked(int position, IDrawerItem drawerItem);

        void scrollStateChanged(int newState);
    }

    interface Interactor {

        void onGroupReceive(Group group);


        void signout();

    }

    interface ViewGroupListener {

        void onGroupAdd(ArrayList<Group> groups);

    }

    
    interface GroupItemClickListener {

        void groupClick(String id, String title);

    }
}
