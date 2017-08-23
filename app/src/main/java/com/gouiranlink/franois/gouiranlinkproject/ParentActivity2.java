package com.gouiranlink.franois.gouiranlinkproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.gouiranlink.franois.gouiranlinkproject.Account.AccountFragment;
import com.gouiranlink.franois.gouiranlinkproject.Account.NestedSettingsFragment;
import com.gouiranlink.franois.gouiranlinkproject.Favourites.Favoris2Fragment;
import com.gouiranlink.franois.gouiranlinkproject.Favourites.FavouritesFragment;
import com.gouiranlink.franois.gouiranlinkproject.Gallery.FragmentGallery3;
import com.gouiranlink.franois.gouiranlinkproject.Gallery.GalleryFragment;
import com.gouiranlink.franois.gouiranlinkproject.Homepage.HomeFragment;
import com.gouiranlink.franois.gouiranlinkproject.Homepage.HomeFragment2;
import com.gouiranlink.franois.gouiranlinkproject.InsciptionConnexion.LoginActivity;
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Recherche.Research2Fragment;
import com.gouiranlink.franois.gouiranlinkproject.Recherche.ResearchFragment;
import com.gouiranlink.franois.gouiranlinkproject.Reservation.Reservation2Fragment;
import com.gouiranlink.franois.gouiranlinkproject.Reservation.ReservationFragment;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.CircleTransform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.Manifest.permission.SEND_SMS;

/**
 * Created by François on 27/04/2017.
 */

public class ParentActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,

        HomeFragment.OnFragmentInteractionListener, HomeFragment2.OnFragmentInteractionListener, ResearchFragment.OnFragmentInteractionListener, Research2Fragment.OnFragmentInteractionListener,
        ReservationFragment.OnFragmentInteractionListener, FavouritesFragment.OnFragmentInteractionListener,
        GalleryFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener {


    private NavigationView navigationView;
    private View navHeader;
    private TextView txtName, txtWebsite;
    private Customer customer;
    private String token;
    private String[] place,autocomplete;
    private static final String urlProfileImg = "R.drawable.image_inconnu";
    private ImageView imgProfile;


    static final int PICK_CONTACT_REQUEST = 1;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            customer = (Customer) getIntent().getSerializableExtra("Customer");
            token = (String) getIntent().getStringExtra("token");
            place = (String[]) getIntent().getStringArrayExtra("place");
            autocomplete = (String[]) getIntent().getStringArrayExtra("autocomplete");
            System.out.println("Toooooooooooooken" + token);
        }

        System.out.println("Booooooooooooooooonjour" + token);








        //CONFIGURATION

        StringBuilder builder = new StringBuilder();
        AssetManager assetManager= getAssets();
        InputStream inputStream = null;
        try {
            System.out.println("gg");
            inputStream = assetManager.open("configuration.txt");
        } catch (IOException e) {
            System.out.println("non");
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Configuration : " + builder.toString());

        //FichierConfig fichierconfig = new FichierConfig();

        // FIN CONFIGURATION



        //CONFIGURATION 2
        String fichier = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "configuration_test"+ File.separator +"configuration.txt";
        BufferedReader In=null;
        try{
            In = new BufferedReader(new FileReader(fichier));
            System.out.println("Le fichier exist !");
            //si le fichier existe, les instructions qui suivent seront exécutées.
            String line2 = null;
            try {
                while ((line2 = In.readLine()) != null) {
                    System.out.println(line2);
                    builder.append(line2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException fnfe) {
            //si le fichier n'existe pas tu arrives ici.
            File myFile = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "configuration_test","configuration.txt"); //on déclare notre futur fichier

            File myDir = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "configuration_test"); //pour créer le repertoire dans lequel on va mettre notre fichier
            Boolean success=true;
            if (!myDir.exists()) {
                success = myDir.mkdir(); //On crée le répertoire (s'il n'existe pas!!)
            }
            if (success){

                String data= "Ce que je veux ecrire dans mon fichier \r\n";

                try {
                    FileOutputStream output = new FileOutputStream(myFile, true); //le true est pour écrire en fin de fichier, et non l'écraser
                    output.write(data.getBytes());
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                Log.e("TEST1","ERROR DE CREATION DE DOSSIER");
            }

        }
        //FIN CONFIGURATION 2









        setContentView(R.layout.parent1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.surname_customer);
        txtWebsite = (TextView) navHeader.findViewById(R.id.mail_customer);
        imgProfile = (ImageView) navHeader.findViewById(R.id.imageView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Ouvre une snackbar (barre du bas)
                //Snackbar.make(view, "", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent email = new Intent(Intent.ACTION_SEND,Uri.fromParts(
                        "mailto","abc@gmail.com", null));
                email.setType("text/plain");

                //Obligatoire d'avoir un tableau
                email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "Contact@gouiran-link.com" });
                email.putExtra(Intent.EXTRA_SUBJECT, "Contacter Gouiran Link");
                email.putExtra(Intent.EXTRA_TEXT, "Saisir votre demande ici...");
                email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(email, "Choisir le logiciel"));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        if (customer != null) {
            // load nav menu header data
            txtName.setText(customer.getSurname()+" "+customer.getName());
            txtWebsite.setText(customer.getEmail());

            // loading header background image
            /*Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);*/

            // Loading profile image
            Glide.with(this).load(customer.getImage().getThumbnails().get(0)[2])
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfile);
        }else{
            Glide.with(this).load(R.drawable.image_inconnu)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfile);
        }



        Fragment fragment = null;
        Bundle args = new Bundle();
        args.putSerializable("Customer", customer);
        args.putSerializable("token", token);
        args.putSerializable("place", place);
        args.putSerializable("autocomplete", autocomplete);

        //System.out.println("USeeeeeeeeeeeeeeeeeeeeeeeer : "+customer.getName());
        //System.out.println("USeeeeeeeeeeeeeeeeeeeeeeeer : "+token);

        fragment = new HomeFragment2();
        fragment.setArguments(args);

        fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("tag").commit();




        //PERMISSION
        if (ContextCompat.checkSelfPermission(this,
                SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    SEND_SMS)) {
                //Cela signifie que la permission à déjà était
                //demandé et l'utilisateur l'a refusé
                //Vous pouvez aussi expliquer à l'utilisateur pourquoi
                //cette permission est nécessaire et la redemander
            } else {
                //Sinon demander la permission
                ActivityCompat.requestPermissions(this,
                        new String[]{SEND_SMS},
                        123);
            }
        }


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


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(ParentActivity2.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_deconnexion) {
                    if (customer != null && customer.ismFacebook()) {
                        Toast.makeText(this.getApplicationContext(), R.string.logged_out_facebook, Toast.LENGTH_SHORT).show();
                        LoginManager.getInstance().logOut();
                        Intent login = new Intent(this, LoginActivity.class);
                        startActivity(login);
                        this.finish();
                    } else if (customer != null && customer.ismGoogle()) {
                        Toast.makeText(this.getApplicationContext(), R.string.logged_out_google, Toast.LENGTH_SHORT).show();
                        LoginActivity.signOut(mGoogleApiClient);
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        this.finish();
                    } else if (customer != null && customer.ismGouiranLink()) {
                        Toast.makeText(this.getApplicationContext(), R.string.logged_out_gouiran_link, Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(this, LoginActivity.class);
                        startActivity(login);
                        this.finish();
                    }

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
        args.putSerializable("place", place);
        args.putSerializable("autocomplete", autocomplete);
        args.putSerializable("homepage_click_imageview", "");


        if (id == R.id.nav_home) {

            fragment = new HomeFragment2();
            fragment.setArguments(args);

        } else if (id == R.id.nav_research) {

            fragment = new Research2Fragment();
            fragment.setArguments(args);

        } else if (id == R.id.nav_reservation) {

            if (customer != null && (customer.ismFacebook() || customer.ismGoogle() || customer.ismGouiranLink())) {
                args.putSerializable("Retour","");
                fragment = new Reservation2Fragment();
                fragment.setArguments(args);
            }
            else {
                Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
                fragment = new HomeFragment2();
                fragment.setArguments(args);
            }

        } else if (id == R.id.nav_favourites) {

            if (customer != null && (customer.ismFacebook() || customer.ismGoogle() || customer.ismGouiranLink())) {
                //fragment = new FavouritesFragment();
                args.putSerializable("Retour","");
                fragment = new Favoris2Fragment();
                fragment.setArguments(args);
            }
            else {
                Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
                fragment = new HomeFragment2();
                fragment.setArguments(args);
            }

        } else if (id == R.id.nav_gallery) {
            //ABANDONNE
            //fragment = new ActivityGallery();

            //Marchepresque
            fragment = new FragmentGallery3();

            //Intent gallery = new Intent(this,FragmentGallery4.class);
            //startActivity(gallery);

            //Fonctionne
            //fragment = new FragmentTransitionGallery4();

            //Essaie
            //fragment = new FragmentGallery5();

            //Essaie2
            //fragment = new FragmentGallery6();

        } else if (id == R.id.nav_compte) {
            if (customer != null && (customer.ismFacebook() || customer.ismGoogle() || customer.ismGouiranLink())) {
                fragment = new AccountFragment();
                fragment.setArguments(args);
            }
            else {
                Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
                fragment = new HomeFragment2();
                fragment.setArguments(args);
            }
        } else if (id == R.id.nav_share) {




            //emailIntent = new Intent(android.content.Intent.ACTION_SEND);

            /*Intent emailIntent = new Intent(Intent.ACTION_SEND,Uri.fromParts(
                    "mailto","abc@gmail.com", null));
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.invitation_gouiran_link));
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.invation_text));
            emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));*/

            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setMessage("Choisissez votre méthode d'envoi !");
            builder2.setPositiveButton("SMS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            chooseContacts();

                        }
            });
            builder2.setNegativeButton("EMAIL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND,Uri.fromParts(
                            "mailto","abc@gmail.com", null));
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.invitation_gouiran_link));
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.invation_text));
                    emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                }
            });

            /*builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
            });*/

            AlertDialog dialog = builder2.create();
            dialog.show();






            //SmsManager.getDefault().sendTextMessage("0618757417", null, "Coucou", null, null);
            //


        } else if (id == R.id.nav_send) {

            if (customer != null && (customer.ismFacebook() || customer.ismGoogle() || customer.ismGouiranLink())) {
                fragment = new NestedSettingsFragment();
                fragment.setArguments(args);
            }
            else {
                Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
                fragment = new HomeFragment2();
                fragment.setArguments(args);
            }

        }



        if((id != R.id.nav_share)) {
            fragment.setArguments(args);
            FragmentManager frgManager = getSupportFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("tag").commit();


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private void chooseContacts() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {






        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);

                System.out.println("Nummmmmmmmmber : "+number);

                // Do something with the phone number...



                //A essayer avec ou sans "address"
                String msg = getResources().getString(R.string.invation_text);
                //Uri smsUri = Uri.parse("tel:0618757417");
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                //i.putExtra("address","0611223344";0622113344;”);
                sendIntent.putExtra("address",number);
                sendIntent.putExtra("sms_body", msg);
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivity(sendIntent);

                //Autre méthode
                //String msg = getResources().getString(R.string.invation_text);
                //SmsManager.getDefault().sendTextMessage(number, null, msg, null, null);

            }
        }
    }



}

