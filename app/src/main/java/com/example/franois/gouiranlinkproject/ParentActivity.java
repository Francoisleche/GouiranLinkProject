package com.example.franois.gouiranlinkproject;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;


public class ParentActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, ResearchFragment.OnFragmentInteractionListener,
        ReservationFragment.OnFragmentInteractionListener, FavouritesFragment.OnFragmentInteractionListener,
        GalleryFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private android.support.v4.app.ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    CustomDrawerAdapter adapter;

    List<DrawerItem> dataList;


    private MyCustomer myCustomer;

    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            myCustomer = (MyCustomer)getIntent().getSerializableExtra("MyCustomer");
            /*String surname = myCustomer.getSurname();
            String name = myCustomer.getName();
            Boolean facebook = myCustomer.getArray()[0];*/

            String access_token = b.getString("access_token");
            String mEmail = b.getString("username");
            boolean connected = b.getBoolean("connected");
        }

        // Initializing
        dataList = new ArrayList<DrawerItem>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        // Add Drawer Item to dataList
        dataList.add(new DrawerItem("Accueil", R.drawable.ic_home_black_24dp));
        dataList.add(new DrawerItem("Recherche", R.drawable.ic_search_black_24dp));
        dataList.add(new DrawerItem("Mes Réservations", R.drawable.ic_grid_on_black_24dp));
        dataList.add(new DrawerItem("Mes Favoris", R.drawable.ic_favorite_black_24dp));
        dataList.add(new DrawerItem("Ma Galerie", R.drawable.ic_camera_alt_black_24dp));
        dataList.add(new DrawerItem("Mon Compte", R.drawable.ic_person_black_24dp));

        adapter = new CustomDrawerAdapter(this, R.xml.custom_drawer_item,
                dataList);


        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.logo_gouiran_link_24dp);


        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerToggle = new android.support.v4.app.ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            SelectItem(0);
        }

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Gouiran Link");
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    public void SelectItem(int possition) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        args.putSerializable("MyCustomer", myCustomer);

        switch (possition) {
            case 0:
                fragment = new HomeFragment();
                fragment.setArguments(args);
                /*args.putString(HomeFragment.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(HomeFragment.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());*/
                break;
            case 1:
                fragment = new ResearchFragment();
                /*args.putString(ResearchFragment.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(ResearchFragment.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());*/
                break;
            case 2:
                fragment = new ReservationFragment();
                /*args.putString(ReservationFragment.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(ReservationFragment.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());*/
                break;
            case 3:
                fragment = new FavouritesFragment();
                /*args.putString(FavouritesFragment.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(FavouritesFragment.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());*/
                break;
            case 4:
                fragment = new GalleryFragment();
                /*args.putString(GalleryFragment.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(GalleryFragment.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());*/
                break;
            case 5:
                fragment = new AccountFragment();
                fragment.setArguments(args);
                /*args.putString(AccountFragment.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(AccountFragment.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());*/
                break;
            default:
                break;
        }

        fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();

        frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("tag").commit();

        mDrawerList.setItemChecked(possition, true);
        setTitle(dataList.get(possition).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        return mDrawerToggle.onOptionsItemSelected(item);

    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            SelectItem(position);

        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getSupportActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "app not available", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    /*private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("POSITION", String.valueOf(position));
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new Fragment();
        Bundle b = new Bundle();
        b.putSerializable("MyCustomer", myCustomer);

        switch (position) {
            case (0):
                fragment = new HomeFragment();
                fragment.setArguments(b);
                break;
            case (1):
                fragment = new ResearchFragment();
                break;
            case (2):
                fragment = new ReservationFragment();
                break;
            case (3):
                fragment = new FavouritesFragment();
                break;
            case (4):
                fragment = new GalleryFragment();
                break;
            case (5):
                fragment = new AccountFragment();
                fragment.setArguments(b);
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentContainer, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mTabTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }*/

    @Override
    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Parent Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else if (count > 1) {
            getSupportFragmentManager().popBackStack();
            if (findViewById(R.id.frame_layout) != null)
                findViewById(R.id.frame_layout).setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getSupportActionBar()
                .setTitle(R.string.gouiran_link);
    }
}
