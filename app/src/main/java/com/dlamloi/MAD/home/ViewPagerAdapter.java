package com.dlamloi.MAD.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Don on 21/04/2018.
 */

/**
 * This class is reponsible for enabling users to slide through pages
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Adds a fragment to the adapter
     *
     * @param fragment the fragment to be stored
     * @param title the title of the fragment
     */
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
