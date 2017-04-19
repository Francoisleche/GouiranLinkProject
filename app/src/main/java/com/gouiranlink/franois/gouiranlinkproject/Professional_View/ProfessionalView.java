package com.gouiranlink.franois.gouiranlinkproject.Professional_View;

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
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.R;


/**
 * Created by François on 01/02/2017.
 */

public class ProfessionalView extends Fragment {

    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    private Professional professional;
    private Professional_Product[] professional_product;
    private Customer customer;
    private String token;




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            professional = (Professional)getArguments().getSerializable("Professionnal");
            professional_product = (Professional_Product[])getArguments().getSerializable("ProfessionnalProduct");
            customer = (Customer)getArguments().getSerializable("Customer");
            token = (String)getArguments().getString("token");
            System.out.println("Toooooooooooooken"+token);
        }

        System.out.println("Maaaaaaaaaaaaaaaarche bien :"+professional.toString());


        Fragment fragment = null;
        Bundle args = new Bundle();
        args.putSerializable("Professionnal", professional);
        args.putSerializable("ProfessionnalProduct", professional_product);
        args.putSerializable("Customer",customer);
        fragment = new ServicesProfessional();
        fragment.setArguments(args);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout,fragment).addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_professional_view,null);


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
                args.putSerializable("Professionnal", professional);
                args.putSerializable("ProfessionnalProduct",professional_product);
                args.putSerializable("Customer",customer);
                args.putSerializable("token",token);
                System.out.println("CUSTOMER 1 :"+ customer.getName());

                Log.i("TAG", "onTabSelected: " + tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new ServicesProfessional();
                        fragment.setArguments(args);
                        break;
                    case 1:
                        fragment = new AvisProfessional();
                        fragment.setArguments(args);
                        break;
                    case 2:
                        fragment = new InformationsProfessional();
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
