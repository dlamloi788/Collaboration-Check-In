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

        void viewGroup(Group group);

        void setLoadingProgressBarVisibility(boolean visibility);

        void logout();

        void setProfileImage(String url);

        void setDisplayName(String displayName);

        void setEmail(String surname);

        void notifyItemInserted(int position);

        void notifyItemChanged(int index);

        void setRecyclerViewData(ArrayList<Group> groups);
    }

    interface Presenter extends BasePresenter {

        void dataAdded();

        void logout();

        void loadProfileData();

        boolean onDrawerItemClicked(int position, IDrawerItem drawerItem);

        void loadAdapterData();
    }




}
