package com.example.franois.gouiranlinkproject.ToolsClasses;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.franois.gouiranlinkproject.MyCrushes;
import com.example.franois.gouiranlinkproject.MyProsFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        switch (position) {
            case 0:
                MyProsFragment tab1 = new MyProsFragment();
                return tab1;
            case 1:
                MyCrushes tab2 = new MyCrushes();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}