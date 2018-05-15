package com.dlamloi.MAD.viewgroups;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dlamloi.MAD.model.Group;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by Don on 14/05/2018.
 */

public interface ViewGroupContract {

    interface View {

        void viewGroup(Group group);

        void setLoadingProgressBarVisibility(boolean visibility);

        void logout();

        void setProfileImage(String URL);

        void setDisplayName(String displayName);

        void setEmail(String surname);

        void notifyItemInserted(int position);

        void notifyItemChanged(int index);
    }

    interface Presenter {

        void onBindGroupViewAtPosition(GroupAdapter.ViewHolder holder, int position);

        int getGroupCount();

        void dataAdded();

        void logout();

        void loadProfileData();

        boolean onDrawerItemClicked(int position, IDrawerItem drawerItem);
    }

    interface RowView {

        void setGroupName(String name);


    }



}
