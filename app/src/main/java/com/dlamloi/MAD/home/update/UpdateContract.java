package com.dlamloi.MAD.home.update;

import android.content.Intent;

import com.dlamloi.MAD.model.Group;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by Don on 15/05/2018.
 */

public interface UpdateContract {

    interface View {

        void setGroupTitle(String title);

        void setProfileImage(String url);

        void setDisplayName(String displayName);

        void setEmail(String surname);

        void logout();

        void setUpViewPager(Group group);

    }

    interface Presenter {

        void setup(Intent intent);

        boolean onDrawerItemClicked(int position, IDrawerItem drawerItem);

        void logout();

        void loadProfileData();

    }

}
