package com.gouiranlink.franois.gouiranlinkproject.Favourites;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;

import java.util.ArrayList;
import java.util.List;

import static com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.BaseFragment.ARGS_INSTANCE;

/*
Fragment which contains "Mes coups de coeur" and "Mes pros"
 */

public class FavouritesFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    Customer customer;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    public static FavouritesFragment newInstance(int instance) {
        System.out.println("BONJOUR BONJOUR BONJOUR BONJOUR");
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        FavouritesFragment firstFragment = new FavouritesFragment();
        firstFragment.setArguments(args);
        return firstFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = (Customer) getArguments().getSerializable("Customer");
        }

        /*Fragment fragment = null;
        fragment = new MyProsFragment();
        Bundle args = new Bundle();
        args.putSerializable("Customer", customer);
        fragment.setArguments(args);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.favourites_viewpager,fragment).addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        System.out.println("BONJOUR BONJOUR BONJOUR BONJOUR1");*/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewPager viewPager = new ViewPager(getContext());
        viewPager = (ViewPager) getActivity().findViewById(R.id.favourites_viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.favourites_tabs);
        tabLayout.setupWithViewPager(viewPager);
        System.out.println("BONJOUR BONJOUR BONJOUR BONJOUR2");*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_favourites, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewPager viewPager;
        viewPager = (ViewPager) getActivity().findViewById(R.id.favourites_viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.favourites_tabs);
        tabLayout.setupWithViewPager(viewPager);

        System.out.println("BONJOUR BONJOUR BONJOUR BONJOUR4");
        return view;
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.outer_fragment, container, false);
        setHasOptionsMenu(true);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        Log.v("Layout","Tabs");
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#707070"), Color.parseColor("#FFFFFF"));
        assert viewPager != null;
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }*/

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        //ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Fragment fragment1 = null;
        Fragment fragment2 = null;
        fragment1 = new MyProsFragment();
        fragment2 = new MyCrushes();
        adapter.addFragment(fragment1, getString(R.string.my_pros));
        adapter.addFragment(fragment2, getString(R.string.my_crushes));
        viewPager.setAdapter(adapter);
        System.out.println("BONJOUR BONJOUR BONJOUR BONJOUR3");
    }





    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            Bundle args = new Bundle();
            args.putSerializable("Customer", customer);
            fragment.setArguments(args);
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            System.out.println("BONJOUR BONJOUR BONJOUR BONJOUR5");
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.myFavourites);
    }
}
