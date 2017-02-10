package com.example.franois.gouiranlinkproject;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.franois.gouiranlinkproject.Professional_View.AvisProfessional;
import com.example.franois.gouiranlinkproject.Professional_View.InformationsProfessional;
import com.example.franois.gouiranlinkproject.Professional_View.ServicesProfessional;



/**
 * Created by François on 01/02/2017.
 */

public class ProfessionalView extends AppCompatActivity {

    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_professional_view);


        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.professional_tabs);

        //AJOUTE DES PARTIES AU TABLAYOUT
        //tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        //tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // LES 2 fonctionnent !
        //tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            Fragment fragment = null;
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
                Log.i("TAG", "onTabSelected: " + tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new ServicesProfessional();
                        break;
                    case 1:
                        fragment = new AvisProfessional();
                        break;
                    case 2:
                        fragment = new InformationsProfessional();
                        break;
                }

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout,fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.i("TAG", "onTabUnselected: " + tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i("TAG", "onTabReselected: " + tab.getPosition());
            }
        });
    }



}

