package com.example.franois.gouiranlinkproject.ToolsClasses;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

class ViewPagerAdapter extends FragmentPagerAdapter{

    final private ArrayList<Fragment> fragments = new ArrayList<>();
    final private ArrayList<String> tabTitles = new ArrayList<>();

    public void addFragments(Fragment fragments, String titles) {
        this.fragments.add(fragments);
        this.tabTitles.add(titles);
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return (fragments.get(position));
    }

    @Override
    public int getCount() {
        return (fragments.size());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (tabTitles.get(position));
    }
}
