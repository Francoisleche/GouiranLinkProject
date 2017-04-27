package com.gouiranlink.franois.gouiranlinkproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Account.AccountFragment;
import com.gouiranlink.franois.gouiranlinkproject.Account.NestedSettingsFragment;
import com.gouiranlink.franois.gouiranlinkproject.Favourites.Favoris2Fragment;
import com.gouiranlink.franois.gouiranlinkproject.Favourites.FavouritesFragment;
import com.gouiranlink.franois.gouiranlinkproject.Gallery.ActivityGallery;
import com.gouiranlink.franois.gouiranlinkproject.Gallery.GalleryFragment;
import com.gouiranlink.franois.gouiranlinkproject.Homepage.HomeFragment;
import com.gouiranlink.franois.gouiranlinkproject.Homepage.HomeFragment2;
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Recherche.Research2Fragment;
import com.gouiranlink.franois.gouiranlinkproject.Recherche.ResearchFragment;
import com.gouiranlink.franois.gouiranlinkproject.Reservation.Reservation2Fragment;
import com.gouiranlink.franois.gouiranlinkproject.Reservation.ReservationFragment;

/**
 * Created by François on 27/04/2017.
 */

public class ParentActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,

        HomeFragment.OnFragmentInteractionListener, HomeFragment2.OnFragmentInteractionListener, ResearchFragment.OnFragmentInteractionListener, Research2Fragment.OnFragmentInteractionListener,
        ReservationFragment.OnFragmentInteractionListener, FavouritesFragment.OnFragmentInteractionListener,
        GalleryFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener {


    private Customer customer;
    private String token;
    //private Intent emailIntent = null;// = new Intent(android.content.Intent.ACTION_SEND);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            customer = (Customer) getIntent().getSerializableExtra("Customer");
            token = (String) getIntent().getStringExtra("token");
            System.out.println("Toooooooooooooken" + token);
        }



        setContentView(R.layout.parent1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        Fragment fragment = null;
        Bundle args = new Bundle();
        args.putSerializable("Customer", customer);
        args.putSerializable("token", token);

        fragment = new HomeFragment2();
        fragment.setArguments(args);

        fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("tag").commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_parent4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Bundle args = new Bundle();
        args.putSerializable("Customer", customer);
        args.putSerializable("token", token);


        if (id == R.id.nav_home) {

            fragment = new HomeFragment();
            fragment.setArguments(args);

        } else if (id == R.id.nav_research) {

            fragment = new Research2Fragment();
            fragment.setArguments(args);

        } else if (id == R.id.nav_reservation) {

            if (customer != null && (customer.ismFacebook() || customer.ismGoogle() || customer.ismGouiranLink())) {
                fragment = new Reservation2Fragment();
                fragment.setArguments(args);
            }
            else {
                Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
                fragment = new HomeFragment();
                fragment.setArguments(args);
            }

        } else if (id == R.id.nav_favourites) {

            if (customer != null && (customer.ismFacebook() || customer.ismGoogle() || customer.ismGouiranLink())) {
                //fragment = new FavouritesFragment();
                fragment = new Favoris2Fragment();
                fragment.setArguments(args);
            }
            else {
                Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
                fragment = new HomeFragment();
                fragment.setArguments(args);
            }

        } else if (id == R.id.nav_gallery) {

            fragment = new ActivityGallery();

        } else if (id == R.id.nav_compte) {
            if (customer != null && (customer.ismFacebook() || customer.ismGoogle() || customer.ismGouiranLink())) {
                fragment = new AccountFragment();
                fragment.setArguments(args);
            }
            else {
                Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
                fragment = new HomeFragment();
                fragment.setArguments(args);
            }
        } else if (id == R.id.nav_share) {

            Intent emailIntent = new Intent(Intent.ACTION_SEND,Uri.fromParts(
                    "mailto","abc@gmail.com", null));
            //emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.invitation_gouiran_link));
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.invation_text));
            emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        } else if (id == R.id.nav_send) {

            if (customer != null && (customer.ismFacebook() || customer.ismGoogle() || customer.ismGouiranLink())) {
                fragment = new NestedSettingsFragment();
                fragment.setArguments(args);
            }
            else {
                Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
                fragment = new HomeFragment();
                fragment.setArguments(args);
            }

        }

        fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("tag").commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

