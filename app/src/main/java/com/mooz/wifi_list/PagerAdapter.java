package com.mooz.wifi_list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.media.session.PlaybackStateCompat;

import com.mooz.wifi_list.RegisteredPoint.RegisteredFragment;

public class PagerAdapter extends FragmentPagerAdapter {


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return APListFragment.newInstance();
            case 1:
                return RegisteredFragment.newInstance();
            default:
                throw new IllegalArgumentException("position : " + position +
                " is unsupported.");
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return "page " + position;
    }
}
