package com.example.khokhlovart_loftschool.moneytracker;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Dom on 04.11.2017.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private String[] titels;

    public MainPagerAdapter(FragmentManager fm, Resources res) {
        super(fm);
        titels = res.getStringArray(R.array.tabs_name);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ItemsFragment.CreateItemsFragment(ItemsFragment.PAGE_EXPENSE);
            case 1:
                return ItemsFragment.CreateItemsFragment(ItemsFragment.PAGE_INCOMES);
            case 2:
                return new BalanceFragment();
            default:
                return ItemsFragment.CreateItemsFragment(ItemsFragment.PAGE_UNKNOWN);
                //return new ErrorFragment();
        }
    }

    @Override
    public int getCount() {
        return titels.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titels[position];
    }
}
