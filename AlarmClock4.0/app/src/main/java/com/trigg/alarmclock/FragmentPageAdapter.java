package com.trigg.alarmclock;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by encore on 2015/7/20.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter {


    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();

            default:
                break;

        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
