package com.example.franois.gouiranlinkproject.ToolsClasses;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.franois.gouiranlinkproject.MyCrushes;
import com.example.franois.gouiranlinkproject.MyProsFragment;

class PagerAdapter extends FragmentStatePagerAdapter {
    final private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new MyProsFragment();
            case 1:
                return new MyCrushes();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}