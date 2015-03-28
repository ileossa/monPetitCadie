package com.esgi.iam.mpc.adapter;

import com.esgi.iam.mpc.fragments.FavoriesFragment;
import com.esgi.iam.mpc.fragments.MyListFragment;
import com.esgi.iam.mpc.fragments.ProductFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabsPagerAdapter extends FragmentPagerAdapter {
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // MyListFragment activity
        	
            return new MyListFragment();
        case 1:
            // ProductFragment activity
            return new ProductFragment();
        case 2:
            // FavoriesFragment activity
            return new FavoriesFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
 
}