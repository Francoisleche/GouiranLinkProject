package com.example.franois.gouiranlinkproject;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.ncapdevi.fragnav.FragNavController;
import com.example.franois.gouiranlinkproject.HomeFragment;
import com.example.franois.gouiranlinkproject.ResearchFragment;
import com.example.franois.gouiranlinkproject.ReservationFragment;
import com.example.franois.gouiranlinkproject.FavouritesFragment;
import com.example.franois.gouiranlinkproject.GalleryFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class ParentActivity extends AppCompatActivity implements FragNavController.TransactionListener, FragNavController.RootFragmentListener,
        HomeFragment.OnFragmentInteractionListener, ResearchFragment.OnFragmentInteractionListener,
        ReservationFragment.OnFragmentInteractionListener, FavouritesFragment.OnFragmentInteractionListener,
        GalleryFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener {

    private BottomBar mBottomBar;

    private FragNavController fragNavController;
    private final int TAB_HOME = FragNavController.TAB1;
    private final int TAB_RESEARCH = FragNavController.TAB2;
    private final int TAB_RESERVATIONS = FragNavController.TAB3;
    private final int TAB_FAVOURITES = FragNavController.TAB4;
    private final int TAB_GALLERY = FragNavController.TAB5;
    private final int TAB_ACCOUNT = 5;
    private boolean compte = false;
    private String access_token = "";
    private String mEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        Bundle b = getIntent().getExtras();
        if (b != null)
        {
            access_token = b.getString("access_token");
            mEmail = b.getString("email");
        }

        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mBottomBar.selectTabAtPosition(TAB_HOME);
        fragNavController = new FragNavController(savedInstanceState, getSupportFragmentManager(), R.id.contentContainer, this, 6, TAB_HOME);
        fragNavController.setTransitionMode(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragNavController.setTransactionListener(this);

        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        fragNavController.switchTab(TAB_HOME);
                        compte = false;
                        System.out.println("Compte = "+compte);
                        break;
                    case R.id.tab_research:
                        fragNavController.switchTab(TAB_RESEARCH);
                        compte = false;
                        System.out.println("Compte = "+compte);
                        break;
                    case R.id.tab_reservations:
                        fragNavController.switchTab(TAB_RESERVATIONS);
                        compte = false;
                        System.out.println("Compte = "+compte);
                        break;
                    case R.id.tab_favourites:
                        fragNavController.switchTab(TAB_FAVOURITES);
                        compte = false;
                        System.out.println("Compte = "+compte);
                        break;
                    case R.id.tab_gallery:
                        fragNavController.switchTab(TAB_GALLERY);
                        compte = false;
                        System.out.println("Compte = "+compte);
                        System.out.println("Compte = "+TAB_GALLERY);
                        break;
                    case R.id.tab_account:
                        compte = true;
                        fragNavController.switchTab(TAB_ACCOUNT);
                        System.out.println("Compte = "+compte);
                        System.out.println("TAB_ACCOUNT = "+TAB_ACCOUNT);
                        break;
                }
            }
        });

        Toast.makeText(this,"Compte = "+compte,Toast.LENGTH_SHORT).show();


        mBottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId){
                fragNavController.clearStack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (fragNavController.canPop()) {
            fragNavController.pop();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragNavController != null) {
            fragNavController.onSaveInstanceState(outState);
        }
    }

    public void pushFragment(Fragment fragment) {
        if (fragNavController != null) {
            fragNavController.push(fragment);
        }
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        // If we have a backstack, show the back button
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(fragNavController.canPop());
        }
    }

    @Override
    public void onFragmentTransaction(Fragment fragment) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(fragNavController.canPop());
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case TAB_HOME:
                return HomeFragment.newInstance(0, mEmail);
            case TAB_RESEARCH:
                return ResearchFragment.newInstance(0);
            case TAB_RESERVATIONS:
                return ReservationFragment.newInstance(0);
            case TAB_FAVOURITES:
                return FavouritesFragment.newInstance(0);
            case TAB_GALLERY:
                return GalleryFragment.newInstance(0);
            case 5:
                if(compte == true){
                    Toast.makeText(this, "ça marche", Toast.LENGTH_SHORT).show();
                    return AccountFragment.newInstance(0);
                }
        }
        throw new IllegalStateException("Need to send an index that we know");
    }


    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
    //messageView = (TextView) findViewById(R.id.messageView);

}
