package com.gouiranlink.franois.gouiranlinkproject.Favourites;

/**
 * Created by François on 14/04/2017.
 */
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;


public class Favoris2Fragment extends Fragment {

        FrameLayout simpleFrameLayout;
        TabLayout tabLayout;
        private Customer customer;




        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (getArguments() != null) {
                customer = (Customer)getArguments().getSerializable("Customer");
            }


            Fragment fragment = null;
            Bundle args = new Bundle();
            //args.putSerializable("Professionnal", professional);
            //args.putSerializable("ProfessionnalProduct", professional_product);
            args.putSerializable("Customer",customer);
            fragment = new MyProsFragment();
            fragment.setArguments(args);

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.simpleFrameLayout,fragment).addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();

        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment2_mesfavoris,null);


            simpleFrameLayout = (FrameLayout) v.findViewById(R.id.simpleFrameLayout);
            tabLayout = (TabLayout) v.findViewById(R.id.professional_tabs);

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
                    Bundle args = new Bundle();
                    args.putSerializable("Customer",customer);
                    System.out.println("CUSTOMER 1 :"+ customer.getName());
                    Log.i("TAG", "onTabSelected: " + tab.getPosition());
                    switch (tab.getPosition()) {
                        case 0:
                            fragment = new MyProsFragment();
                            fragment.setArguments(args);
                            break;
                        case 1:
                            fragment = new MyCrushes();
                            fragment.setArguments(args);
                            break;
                    }

                    FragmentManager fm = getFragmentManager();
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

            return v;
        }

        @Override
        public void onViewCreated(View view ,Bundle savedInstanceState) {
            getActivity().findViewById(R.id.simpleFrameLayout).setVisibility(View.VISIBLE);

        }



        public interface OnFragmentInteractionListener {
        }

    }

