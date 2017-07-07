package com.gouiranlink.franois.gouiranlinkproject.Recherche;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.Image_N;
import com.gouiranlink.franois.gouiranlinkproject.Object.Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Product_Category_Tag;
import com.gouiranlink.franois.gouiranlinkproject.Object.Product_Category_WithoutTree;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Schedule;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Subscription_Type;
import com.gouiranlink.franois.gouiranlinkproject.Object.Resource;
import com.gouiranlink.franois.gouiranlinkproject.Professional_View.InformationsProfessional;
import com.gouiranlink.franois.gouiranlinkproject.Professional_View.ProfessionalView;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class MapPane2 extends Fragment{

    ProgressDialog mProgressDialog;


    private FileOutputStream fileOutputStream = null;

    //Liste des professionnels trouvé
    private String[] shopIdList = new String[10];
    private String[] shopNameList = new String[10];
    private String[] shopImageList= new String[10];
    private String[] shopFavorisList= new String[10];
    private String[] shopAvisList= new String[10];
    private String[] LatitudeList= new String[10];
    private String[] LongitudeList= new String[10];

    String[] getShopNameList() {
        return shopNameList;
    }

    String[] getShopImageList() {
        return shopImageList;
    }

    Intent intentThatCalled;
    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;
    public boolean recherche_bool = false;

    private MapView mMap;
    private static final int REQUEST_CODE_LOCATION = 123;
    private LocationManager lManager;
    private Location location;

    ListView mesresultats_map;
    TextView shopname_resultat;
    ImageView logo_entreprise;
    RatingBar rtbProductRating;
    TextView text_avis;
    TextView text_favoris;
    LinearLayout linear_button_professionnal;
    LinearLayout linear_text_cacher;




    private Marker currentMarker = null;



    //Les variables qu'on doit récupérer
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    private Professional PremierProfessionnal = new Professional();
    private Professional_Product[] PremierProfessionalProduct;
    private Customer customer;
    private String token;
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
    HashMap<String, List<String>> expandableListDetailAutrePrestation = new HashMap<String, List<String>>();
    ArrayList<String> Shop_image;
    private Resource[] ResourceProfessional;
    String fragment_precedent;


    GetRequest getRequest;


    //VARIABLES LOCALES
    private Product_Category_WithoutTree[] PremierProfessionnalProduits;
    private Product_Category_Tag[] PremierProfessionnalProduitsTag;
    private Professional_Schedule[] ProfessionnalGenericSchedule;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

                shopIdList = (String[]) getArguments().getStringArray("liste_id_professionnal");
                shopImageList = (String[]) getArguments().getSerializable("liste_image_professionnal");
                shopNameList = (String[]) getArguments().getSerializable("liste_name_professionnal");
                shopFavorisList = (String[]) getArguments().getSerializable("liste_favoris_professionnal");
                shopAvisList = (String[]) getArguments().getSerializable("liste_avis_professionnal");
                LatitudeList = (String[]) getArguments().getSerializable("liste_latitude_professionnal");
                LongitudeList = (String[]) getArguments().getSerializable("liste_longitude_professionnal");


            recherche_bool =(boolean)getArguments().getBoolean("recherche_boolean");
            System.out.println("Boooooooooooooooolean" + recherche_bool);
            if (!recherche_bool) {
                Professional PremierProfessionnal = new Professional();

            } else {



                //Ce qu'on récupere
                PremierProfessionnal = (Professional) getArguments().getSerializable("Professionnal");
                PremierProfessionalProduct = (Professional_Product[]) getArguments().getSerializable("ProfessionnalProduct");
                ResourceProfessional = (Resource[]) getArguments().getSerializable("ResourceProfessional");
                customer = (Customer) getArguments().getSerializable("Customer");
                Shop_image = (ArrayList<String>) getArguments().getSerializable("Shop_image");
                expandableListDetail = (HashMap<String, List<String>>) getArguments().getSerializable("ExpandableListDetail");
                expandableListDetailAutrePrestation = (HashMap<String, List<String>>) getArguments().getSerializable("expandableListDetailAutrePrestation");
                token = (String) getArguments().getString("token");
                fragment_precedent = (String) getArguments().getString("Fragment");
            }



            //System.out.println("ILMEFAUTUNCUSTOMER : "+customer.getName());

            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                // mMap.setMyLocationEnabled(false);
                System.out.println("maaaaaaaaaaaaaaaaarche localisation :)");
            } else {
                String[] permissionRequested = {android.Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissionRequested, REQUEST_CODE_LOCATION);
                System.out.println("maaaaaaaaaaaaaaaaarche pas localisation");
            }


        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.cartes2, container, false);

        for (int i=0; i < 3; i++)
        {
            Toast.makeText(getActivity(), "Cliquez sur vos Professionnels", Toast.LENGTH_LONG).show();
        }

            shopname_resultat = (TextView) rootView.findViewById(R.id.shopname_resultat);
            logo_entreprise = (ImageView) rootView.findViewById(R.id.logo_entreprise);
            rtbProductRating = (RatingBar) rootView.findViewById(R.id.rtbProductRating);
            text_avis = (TextView) rootView.findViewById(R.id.text_avis);
            text_favoris = (TextView) rootView.findViewById(R.id.text_favoris);
            linear_button_professionnal = (LinearLayout) rootView.findViewById(R.id.linear_button_professionnal);
            linear_text_cacher = (LinearLayout) rootView.findViewById(R.id.linear_text_cacher);


            //Initialiser la vue
            linear_text_cacher.setVisibility(true ? View.VISIBLE : View.GONE);
            linear_button_professionnal.setVisibility(true ? View.GONE : View.VISIBLE);

        mesresultats_map = (ListView) rootView.findViewById(R.id.mesresultats_map);

        mMap = (MapView) rootView.findViewById(R.id.map);
        mMap.onCreate(savedInstanceState);

        mMap.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {


                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                } else {


                    if (!recherche_bool) {
                        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        mMap.setMyLocationEnabled(true);
                        criteria = new Criteria();
                        bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true));
                        Location location1 = locationManager.getLastKnownLocation(bestProvider);
                        latitude = location1.getLatitude();
                        longitude = location1.getLongitude();

                        LatLng latLng = new LatLng(latitude, longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

                        System.out.println("latitude " + latitude + " longitude " + longitude);

                        /*mProgressDialog = ProgressDialog.show(getActivity(), "Please wait",
                                "Long operation starts...", true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                recherche_autour(latitude, longitude);
                                mProgressDialog.cancel();
                            }
                        }).start();*/

                        // ...

                        //recherche_autour(latitude, longitude);

                        //shopImageList = new String[5];
                        //shopNameList = new String[5];
                        for (int i = 0; i < LatitudeList.length; i++) {
                            if (LatitudeList[i] == null) {

                            } else {
                                System.out.println("LATITUDE de " + i + " : " + LatitudeList[i]);
                                System.out.println("Longitude de " + i + " : " + LongitudeList[i]);
                                LatLng premier_trouve = new LatLng(Double.parseDouble(LatitudeList[i]), Double.parseDouble(LongitudeList[i]));

                                //MARCHE , ne pas supprimer
                                mMap.addMarker(new MarkerOptions().position(premier_trouve)
                                        //.title(shopNameList[i]+"////"+shopImageList[i]+"////"+shopAvisList[i]+"////"+shopFavorisList[i])
                                        .title(String.valueOf(i))
                                        .icon(getMarkerIcon("#d16677")));

                                //Marche autre façon de faire un marquerur
                                //mMap.addMarker(new MarkerOptions()
                                //       .position(premier_trouve)
                                //       .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.cb650f))));


                                //Click d'un marKer
                                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {

                /*if (marker.getPosition().equals(userMarker.getPosition())) {
                    return true;
                }*/
                                        //arg0.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                                        if (currentMarker == null) {
                                            marker.setIcon(getMarkerIcon("#7699a1"));
                                            currentMarker = marker;
                                            linear_button_professionnal.setVisibility(true ? View.VISIBLE : View.GONE);
                                            linear_text_cacher.setVisibility(true ? View.GONE : View.VISIBLE);
                                        } else if (currentMarker.getTitle().equals(marker.getTitle())) {
                                            marker.setIcon(getMarkerIcon("#d16677"));
                                            currentMarker = null;
                                            linear_text_cacher.setVisibility(true ? View.VISIBLE : View.GONE);
                                            linear_button_professionnal.setVisibility(true ? View.GONE : View.VISIBLE);
                                        } else {
                                            currentMarker.setIcon(getMarkerIcon("#d16677"));
                                            marker.setIcon(getMarkerIcon("#7699a1"));
                                            currentMarker = marker;
                                        }

                                        //String[] separated_position = marker.getTitle().split("////");
                                        final int numero = Integer.parseInt(marker.getTitle());
                                        shopname_resultat.setText(shopNameList[numero]);
                                        if(shopImageList[numero] == ""){
                                            logo_entreprise.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
                                        }else{
                                            Picasso.with(getContext()).load(shopImageList[numero])
                                                    .into(logo_entreprise);
                                        }

                                        rtbProductRating.setRating(Float.parseFloat(shopAvisList[numero]));
                                        text_avis.setText(" " + shopAvisList[numero] + "/5");
                                        text_favoris.setText(" " + shopFavorisList[numero] + " FAVORIS");


                                        linear_button_professionnal.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(getActivity(), "Bonjjjjjjjjour" + shopIdList[numero], Toast.LENGTH_SHORT).show();


                                                System.out.println("On viens de cliquer ooooh");
                                                final Fragment[] fragment = {null};
                                                final FragmentTransaction[] ft = {null};

                                                final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Svp Veuillez patienter ...", "chargement des données...", true);


                                                Thread timer = new Thread() {
                                                    @Override
                                                    public void run() {

                                                        try {

                                                            try {
                                                                fileOutputStream = getContext().openFileOutput("GouiranLink", Context.MODE_APPEND);
                                                                //fileOutputStream.write(listView.getItemAtPosition(position).toString().getBytes());
                                                                fileOutputStream.write(12);
                                                                fileOutputStream.write("`".getBytes());
                                                                fileOutputStream.close();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }


                                                            //System.out.println(listView.getItemAtPosition(position).toString());


                                                            //PREMIERE METHODE
                                                            //ResearchTask3 researchTask3 = new ResearchTask3(listView.getItemAtPosition(position).toString());
                                                            //String ls2 = "";
                                                            //ArrayList<String> recup2 = new ArrayList<String>();
                                                            //recup2 = jsonparser2(ls2);


                                                            //DEUXIEME METHODE
                                                            //Recupérer liste des produits
                                                            //System.out.println("DEBUT DONNEE GENERALE");
                                                            //System.out.println("D "+ recup2.get(0).get(0));
                                                            //System.out.println("D" + position);
                                                            //donneegeneral_jsonparser(recherche_donneegeneral(listView.getItemAtPosition(position).toString()));


                                                            donneegeneral_jsonparser(recherche_donneegeneral(shopIdList[numero]));
                                                            String id = String.valueOf(shopIdList[numero]);

                                                            System.out.println("DEBUT PRODUCT");
                                                            System.out.println("D " + id);
                                                            products_jsonparser(recherche_product(id));
                                                            for (int i = 0; i < PremierProfessionalProduct.length; i++) {
                                                                System.out.println("PremierProfesionnelProduct : " + PremierProfessionalProduct[i].getName() + PremierProfessionalProduct[i].getPrice());
                                                            }

                                                            System.out.println("DEBUT SCHEDULE");
                                                            schedule_jsonparser(recherche_schedule(id));
                                                            PremierProfessionnal.setSchedule(ProfessionnalGenericSchedule);
                                                            for (int i = 0; i < ProfessionnalGenericSchedule.length; i++) {
                                                                System.out.println("PremierProfesionnelSchedule : " + ProfessionnalGenericSchedule[i].getWeekday() + ProfessionnalGenericSchedule[i].getBegin_time());
                                                            }

                                                            System.out.println("DEBUT SHOP_IMAGE");
                                                            shop_image_jsonparser(recherche_shop_image(id));
                                                            for (int i = 0; i < Shop_image.size(); i++) {
                                                                System.out.println("Shop_image : " + Shop_image.get(i));
                                                            }


                                                            System.out.println("DEBUT RESOURCE");
                                                            ressource_jsonparser(recherche_ressource(id));
                                                            for (int i = 0; i < ResourceProfessional.length; i++) {
                                                                System.out.println("Resource : " + ResourceProfessional[i]);
                                                            }


                                                            if (PremierProfessionnal.getProfessional_subscription_type().getName().equals("Full")) {
                                                                //Fragment fragment = null;
                                                                Bundle args = new Bundle();
                                                                args.putSerializable("Professionnal", PremierProfessionnal);
                                                                args.putSerializable("ProfessionnalProduct", PremierProfessionalProduct);
                                                                args.putSerializable("Customer", customer);
                                                                args.putSerializable("ExpandableListDetail", expandableListDetail);
                                                                args.putSerializable("expandableListDetailAutrePrestation", expandableListDetailAutrePrestation);
                                                                args.putSerializable("ResourceProfessional", ResourceProfessional);
                                                                args.putSerializable("Shop_image", Shop_image);
                                                                args.putSerializable("token", token);
                                                                //System.out.println("CUSTOMER :" + customer.getName());
                                                                args.putSerializable("Fragment", "Research2Fragment");

                                                                FragmentManager fm = getFragmentManager();
                                                                //FragmentTransaction ft = fm.beginTransaction();
                                                                ft[0] = fm.beginTransaction();
                                                                //getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);

                                                                fragment[0] = new ProfessionalView();
                                                                fragment[0].setArguments(args);

                                                                //ft.replace(R.id.fragment_remplace, fragment).addToBackStack(null);

                                                                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                                                //ft.commit();
                                                            } else if (PremierProfessionnal.getProfessional_subscription_type().getName().equals("Free")) {
                                                                //Fragment fragment = null;
                                                                Bundle args = new Bundle();
                                                                args.putSerializable("Professionnal", PremierProfessionnal);
                                                                args.putSerializable("ProfessionnalProduct", PremierProfessionalProduct);
                                                                args.putSerializable("Customer", customer);
                                                                args.putSerializable("token", token);

                                                                FragmentManager fm = getFragmentManager();
                                                                //FragmentTransaction ft = fm.beginTransaction();
                                                                ft[0] = fm.beginTransaction();
                                                                //getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);

                                                                fragment[0] = new InformationsProfessional();
                                                                fragment[0].setArguments(args);

                                                                //ft.replace(R.id.fragment_remplace, fragment).addToBackStack(null);

                                                                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                                                //ft.commit();
                                                            }


                                                            ft[0].replace(R.id.content_frame, fragment[0]).addToBackStack(null);
                                                            //ft[0].replace(R.id.content_frame, fragment[0]).addToBackStack("recherche");
                                                            ft[0].setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                                            ft[0].commit();
                                                            ringProgressDialog.cancel();
                                                        } catch (Exception e) {
                                                            System.out.println("PASSSSSSSSSSSSSSSSSSSSSSSSSSSSE PAS");
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                };
                                                timer.start();
                                                ringProgressDialog.setCancelable(false);

                                                //Obligé de faire ça après !
                                                //getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);


                                            }
                                        });


                                        //if(arg0.getTitle().equals("MyHome")) // if marker source is clicked
                                        //if(true){
                                        //arg0.showInfoWindow();
                                        //arg0.setAnchor(0.5f, 1.0f);
                                        //Toast.makeText(MapPane.this, marker.getTitle(),Toast.LENGTH_SHORT).show();
                                        // if marker source is clicked
                                        //}
                                        // display toast
                                        return true;
                                    }

                                });







                        /*Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                        Bitmap bmp = Bitmap.createBitmap(80, 80, conf);
                        Canvas canvas1 = new Canvas(bmp);

                        // paint defines the text color, stroke width and size
                        Paint color = new Paint();
                        color.setTextSize(35);
                        color.setColor(Color.BLACK);

                        // modify canvas
                        canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                                R.drawable.cb650f), 0,0, color);
                        canvas1.drawText("User Name!", 30, 40, color);
                        // add marker to Map
                        mMap.addMarker(new MarkerOptions().position(premier_trouve)
                                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                                // Specifies the anchor to be at a particular point in the marker image.
                                .anchor(0.5f, 1));*/


                            }
                        }
                        String[] results = shopNameList;
                        final List<String> resultsList = new ArrayList<String>(Arrays.asList(results));
                        ArrayAdapter<String> tableau = new ArrayAdapter<String>(getActivity(), R.layout.services, resultsList);
                        mesresultats_map.setAdapter(tableau);

                        System.out.println("TAIIIIIIIIIIILLE" + shopNameList.length + "   " + results.length + "   " + resultsList.size());


                    } else {
                        System.out.println("Allo2222");

                        String[] tableau_shop_name = new String[10];
                        String[] tableau_latitude = new String[10];
                        String[] tableau_longitude = new String[10];
                        for (int i = 0; i < shopIdList.length; i++) {
                            tableau_latitude[i] = LatitudeList[i];
                            tableau_longitude[i] = LongitudeList[i];
                            tableau_shop_name[i] = shopNameList[i];
                        }

                        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        mMap.setMyLocationEnabled(true);
                        criteria = new Criteria();
                        bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
                        Location location1 = locationManager.getLastKnownLocation(bestProvider);
                        latitude = location1.getLatitude();
                        longitude = location1.getLongitude();

                        LatLng latLng = new LatLng(latitude, longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

                        System.out.println("latitude " + latitude + " longitude " + longitude);
                        //recherche_autour(latitude,longitude);
                        //shopImageList = new String[5];
                        //shopNameList = new String[5];
                        for (int i = 0; i < 10; i++) {
                            if (tableau_latitude[i] == null) {

                            } else {
                                System.out.println("LATITUDE de " + i + " : " + tableau_latitude[i]);
                                System.out.println("Longitude de " + i + " : " + tableau_longitude[i]);
                                LatLng premier_trouve = new LatLng(Double.parseDouble(tableau_latitude[i]), Double.parseDouble(tableau_longitude[i]));

                                //Marche bien, ne pas supprimer
                                //mMap.addMarker(new MarkerOptions().position(premier_trouve).title(tableau_shop_name[i]));
                                mMap.addMarker(new MarkerOptions().position(premier_trouve)
                                        .title(String.valueOf(i))
                                        .icon(getMarkerIcon("#d16677")));


                                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {

                /*if (marker.getPosition().equals(userMarker.getPosition())) {
                    return true;
                }*/
                                        //arg0.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                                        if (currentMarker == null) {
                                            marker.setIcon(getMarkerIcon("#7699a1"));
                                            currentMarker = marker;
                                            linear_button_professionnal.setVisibility(true ? View.VISIBLE : View.GONE);
                                            linear_text_cacher.setVisibility(true ? View.GONE : View.VISIBLE);
                                        } else if (currentMarker.getTitle().equals(marker.getTitle())) {
                                            marker.setIcon(getMarkerIcon("#d16677"));
                                            currentMarker = null;
                                            linear_text_cacher.setVisibility(true ? View.VISIBLE : View.GONE);
                                            linear_button_professionnal.setVisibility(true ? View.GONE : View.VISIBLE);
                                        } else {
                                            currentMarker.setIcon(getMarkerIcon("#d16677"));
                                            marker.setIcon(getMarkerIcon("#7699a1"));
                                            currentMarker = marker;
                                        }


                                        final int numero = Integer.parseInt(marker.getTitle());
                                        shopname_resultat.setText(shopNameList[numero]);

                                        if(shopImageList[numero] == ""){
                                            logo_entreprise.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
                                        }else{
                                            Picasso.with(getContext()).load(shopImageList[numero])
                                                    .into(logo_entreprise);
                                        }

                                        rtbProductRating.setRating(Float.parseFloat(shopAvisList[numero]));
                                        text_avis.setText(" " + shopAvisList[numero] + "/5");
                                        text_favoris.setText(" " + shopFavorisList[numero] + " FAVORIS");


                                        linear_button_professionnal.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(getActivity(), "Bonjjjjjjjjour" + shopIdList[numero], Toast.LENGTH_SHORT).show();


                                                System.out.println("On viens de cliquer ooooh");
                                                final Fragment[] fragment = {null};
                                                final FragmentTransaction[] ft = {null};

                                                final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Svp Veuillez patienter ...", "chargement des données...", true);


                                                Thread timer = new Thread() {
                                                    @Override
                                                    public void run() {

                                                        try {

                                                            try {
                                                                fileOutputStream = getContext().openFileOutput("GouiranLink", Context.MODE_APPEND);
                                                                //fileOutputStream.write(listView.getItemAtPosition(position).toString().getBytes());
                                                                fileOutputStream.write(12);
                                                                fileOutputStream.write("`".getBytes());
                                                                fileOutputStream.close();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }


                                                            //System.out.println(listView.getItemAtPosition(position).toString());


                                                            //PREMIERE METHODE
                                                            //ResearchTask3 researchTask3 = new ResearchTask3(listView.getItemAtPosition(position).toString());
                                                            //String ls2 = "";
                                                            //ArrayList<String> recup2 = new ArrayList<String>();
                                                            //recup2 = jsonparser2(ls2);


                                                            //DEUXIEME METHODE
                                                            //Recupérer liste des produits
                                                            //System.out.println("DEBUT DONNEE GENERALE");
                                                            //System.out.println("D "+ recup2.get(0).get(0));
                                                            //System.out.println("D" + position);
                                                            //donneegeneral_jsonparser(recherche_donneegeneral(listView.getItemAtPosition(position).toString()));


                                                            donneegeneral_jsonparser(recherche_donneegeneral(shopIdList[numero]));
                                                            String id = String.valueOf(shopIdList[numero]);

                                                            System.out.println("DEBUT PRODUCT");
                                                            System.out.println("D " + id);
                                                            products_jsonparser(recherche_product(id));
                                                            for (int i = 0; i < PremierProfessionalProduct.length; i++) {
                                                                System.out.println("PremierProfesionnelProduct : " + PremierProfessionalProduct[i].getName() + PremierProfessionalProduct[i].getPrice());
                                                            }

                                                            System.out.println("DEBUT SCHEDULE");
                                                            schedule_jsonparser(recherche_schedule(id));
                                                            PremierProfessionnal.setSchedule(ProfessionnalGenericSchedule);
                                                            for (int i = 0; i < ProfessionnalGenericSchedule.length; i++) {
                                                                System.out.println("PremierProfesionnelSchedule : " + ProfessionnalGenericSchedule[i].getWeekday() + ProfessionnalGenericSchedule[i].getBegin_time());
                                                            }

                                                            System.out.println("DEBUT SHOP_IMAGE");
                                                            shop_image_jsonparser(recherche_shop_image(id));
                                                            for (int i = 0; i < Shop_image.size(); i++) {
                                                                System.out.println("Shop_image : " + Shop_image.get(i));
                                                            }


                                                            System.out.println("DEBUT RESOURCE");
                                                            ressource_jsonparser(recherche_ressource(id));
                                                            for (int i = 0; i < ResourceProfessional.length; i++) {
                                                                System.out.println("Resource : " + ResourceProfessional[i]);
                                                            }


                                                            if (PremierProfessionnal.getProfessional_subscription_type().getName().equals("Full")) {
                                                                //Fragment fragment = null;
                                                                Bundle args = new Bundle();
                                                                args.putSerializable("Professionnal", PremierProfessionnal);
                                                                args.putSerializable("ProfessionnalProduct", PremierProfessionalProduct);
                                                                args.putSerializable("Customer", customer);
                                                                args.putSerializable("ExpandableListDetail", expandableListDetail);
                                                                args.putSerializable("expandableListDetailAutrePrestation", expandableListDetailAutrePrestation);
                                                                args.putSerializable("ResourceProfessional", ResourceProfessional);
                                                                args.putSerializable("Shop_image", Shop_image);
                                                                args.putSerializable("token", token);
                                                                //System.out.println("CUSTOMER :" + customer.getName());
                                                                args.putSerializable("Fragment", "Research2Fragment");

                                                                FragmentManager fm = getFragmentManager();
                                                                //FragmentTransaction ft = fm.beginTransaction();
                                                                ft[0] = fm.beginTransaction();
                                                                //getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);

                                                                fragment[0] = new ProfessionalView();
                                                                fragment[0].setArguments(args);

                                                                //ft.replace(R.id.fragment_remplace, fragment).addToBackStack(null);

                                                                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                                                //ft.commit();
                                                            } else if (PremierProfessionnal.getProfessional_subscription_type().getName().equals("Free")) {
                                                                //Fragment fragment = null;
                                                                Bundle args = new Bundle();
                                                                args.putSerializable("Professionnal", PremierProfessionnal);
                                                                args.putSerializable("ProfessionnalProduct", PremierProfessionalProduct);
                                                                args.putSerializable("Customer", customer);
                                                                args.putSerializable("token", token);

                                                                FragmentManager fm = getFragmentManager();
                                                                //FragmentTransaction ft = fm.beginTransaction();
                                                                ft[0] = fm.beginTransaction();
                                                                //getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);

                                                                fragment[0] = new InformationsProfessional();
                                                                fragment[0].setArguments(args);

                                                                //ft.replace(R.id.fragment_remplace, fragment).addToBackStack(null);

                                                                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                                                //ft.commit();
                                                            }


                                                            ft[0].replace(R.id.content_frame, fragment[0]).addToBackStack("recherche");
                                                            //ft[0].replace(R.id.content_frame, fragment[0]).addToBackStack("recherche");
                                                            ft[0].setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                                            ft[0].commit();
                                                            ringProgressDialog.cancel();
                                                        } catch (Exception e) {
                                                            System.out.println("PASSSSSSSSSSSSSSSSSSSSSSSSSSSSE PAS");
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                };
                                                timer.start();
                                                ringProgressDialog.setCancelable(true);

                                                //Obligé de faire ça après !
                                                //getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);


                                            }
                                        });


                                        return true;
                                    }
                                });


                        /*mMap.addMarker(new MarkerOptions()
                                .position(premier_trouve)
                                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.cb650f))));*/


                                //Créer son propre marqueur
                                /*Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                                Bitmap bmp = Bitmap.createBitmap(80, 80, conf);
                                Canvas canvas1 = new Canvas(bmp);

                                // paint defines the text color, stroke width and size
                                Paint color = new Paint();
                                color.setTextSize(35);
                                color.setColor(Color.BLACK);

                                // modify canvas
                                canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                                        R.drawable.cb650f), 0, 0, color);
                                canvas1.drawText("User Name!", 30, 40, color);
                                // add marker to Map
                                mMap.addMarker(new MarkerOptions().position(premier_trouve)
                                        .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                                        // Specifies the anchor to be at a particular point in the marker image.
                                        .anchor(0.5f, 1));*/


                            }
                        }

                    }


                    //mMap.setMyLocationEnabled(true);

                    // Add a marker in Sydney and move the cameramgr
                    //LatLng sydney = new LatLng(-34, 151);


                    //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                    //mMap.addMarker(new MarkerOptions().position(moi).title("Marker in Sydney"));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                }
            }
        });
        return rootView;

        };




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                //TROUVER SA POSITION
                //mMap.setMyLocationEnabled(true);

                Toast.makeText(getActivity(), "geolocaliser le portable utilisateur", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "impossible de geolocaliser le portable utilisateur", Toast.LENGTH_SHORT).show();
                // Permission was denied. Display an error message.
            }
        }
    }

    // method definition
    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }




    public void onLocationChanged(Location location) {
        //Lorsque la position change...
        Log.i("Tuto géolocalisation", "La position a changé.");
        //... on stop le cercle de chargement
        //setProgressBarIndeterminateVisibility(false);
        //... on active le bouton pour afficher l'adresse
        //findViewById(R.id.afficherAdresse).setEnabled(true);
        //... on sauvegarde la position
        this.location = location;
        //... on l'affiche
        //afficherLocation();
        //... et on spécifie au service que l'on ne souhaite plus avoir de mise à jour
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lManager.removeUpdates((LocationListener) this);
    }


    protected void getLocation() {
        if (true) {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                Log.e("TAG", "GPS is on");
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(getActivity(), "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
                //searchNearestPlace(voice2text);
            }
            else{
                //This is what you need:
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, (LocationListener) this);
            }
        }
        else
        {
            //prompt user to enable location....
            //.................
        }
    }

    public void recherche_autour(double latitude, double longitude){


        String resp = null;
        JSONObject obj;
        JSONArray arr;

        shopImageList = new String[10];
        shopNameList = new String[10];
        LatitudeList = new String[10];
        LongitudeList = new String[10];


        GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/?query[geoloc][latitude]=" + String.valueOf(latitude)
                + "&query[geoloc][longitude]=" + String.valueOf(longitude)+"&query[city]=");

        try {
            resp = getRequest.execute().get();
            obj = new JSONObject(resp);
            arr = obj.getJSONArray("data");

            for (int i = 0; i < arr.length() && i < 10; i++) {

                if (arr.getJSONObject(i).getString("shop_name") != null) {
                    String id = arr.getJSONObject(i).getString("id");
                    shopIdList[i] = id;
                    shopNameList[i] = arr.getJSONObject(i).getString("shop_name");

                    //premiere image uniquement
                    ArrayList<String> tableau_image = new ArrayList<String>();
                    tableau_image = shop_image_jsonparser2(recherche_shop_image(id));
                    shopImageList[i] = tableau_image.get(0);

                    LatitudeList[i] = arr.getJSONObject(i).getString("geoloc_latitude");
                    LongitudeList[i] = arr.getJSONObject(i).getString("geoloc_longitude");
                    shopFavorisList[i] = recherche_favoris(id);
                    ArrayList<String> avis = avis_jsonparser(recherche_avis(id));

                    //Moyenne des avis
                    double moyenne = 0;
                    if(avis.size() == 0){

                    }else{
                        for(int y =0;y<avis.size();y++){
                            moyenne = moyenne + Double.parseDouble(avis.get(y));
                        }
                        moyenne = moyenne/avis.size();
                    }
                    String ss  = String.format("%1$s", moyenne(String.valueOf(moyenne)));
                    shopAvisList[i] = String.valueOf(ss);
                    //System.out.println("moyeeeeeeennne : "+moyenne);
                    //ratingbar.setRating(Float.parseFloat(moyenne(String.valueOf(moyenne))));
                    //System.out.println("moyeeeeeeennne : "+Float.parseFloat(moyenne(String.valueOf(moyenne))));


                }


            }


        } catch (InterruptedException | JSONException | ExecutionException e) {
            e.printStackTrace();
        }


    }







    //RECUPERATION DONNEE
    public String recherche_product(String query) {
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional-product/professional/"+Integer.parseInt(query)+"/?query[show_all]=true");

        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public ArrayList<Professional_Product> products_jsonparser(String jsonStr) {
        //Professional_Product product = new Professional_Product();
        ArrayList<Professional_Product> liste_product = new ArrayList<Professional_Product>();
        List<String> liste_coifure_femme = new ArrayList<String>();
        List<String> liste_coifure_femme2 = new ArrayList<String>();
        List<String> liste_bien_etre = new ArrayList<String>();
        List<String> liste_bien_etre2 = new ArrayList<String>();
        List<String> liste_beaute = new ArrayList<String>();
        List<String> liste_beaute2 = new ArrayList<String>();
        List<String> liste_homme = new ArrayList<String>();
        List<String> liste_homme2 = new ArrayList<String>();
        List<String> photo = new ArrayList<String>();

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray contacts = jsonObj.getJSONArray("data");

            PremierProfessionalProduct = new Professional_Product[contacts.length()];
            for (int i = 0; i < contacts.length(); i++) {
                Professional_Product PremierProfessionalProduct2 = new Professional_Product();
                Product product = new Product();
                Product_Category_WithoutTree product_category_withoutTree = new Product_Category_WithoutTree();
                JSONObject p2 = contacts.getJSONObject(i);
                String id_resource_manager = p2.getString("id");
                String name_resource_manager = p2.getString("name");
                String price_resource_manager = p2.getString("price");
                String currency_resource_manager = p2.getString("currency");

                String duration_resource_manager = p2.getString("duration");
                String description_resource_manager = p2.getString("description");

                String deleted_at_resource_manager = p2.getString("deleted_at");
                String created_at_resource_manager = p2.getString("created_at");
                String updated_at_resource_manager = p2.getString("updated_at");

                String product_resource_manager = p2.getString("product");
                String discounts_resource_manager = p2.getString("discounts");

                System.out.println("AFFICHEEEEEER au moins un truc");
                String discounts = "";
                System.out.println("AFFICHEEEEEER DISCOUNT :" + discounts_resource_manager);
                if(!discounts_resource_manager.equals("[]")){
                    JSONArray discounts_tableau = p2.getJSONArray("discounts");
                    discounts = discounts_tableau.getJSONObject(0).getString("amount");
                    System.out.println("AFFICHEEEEEER DISCOUNT :" + discounts);
                }



                //System.out.println("LES PRODUCT RESOURCE MANAGER PRODUCT :"+product_resource_manager);


                //Remplissage des produits
                PremierProfessionalProduct2.setId(Integer.parseInt(id_resource_manager));
                PremierProfessionalProduct2.setName(name_resource_manager);
                PremierProfessionalProduct2.setPrice(Double.parseDouble(price_resource_manager));
                PremierProfessionalProduct2.setCurrency(currency_resource_manager);
                PremierProfessionalProduct2.setDuration(duration_resource_manager);
                PremierProfessionalProduct2.setDescription(description_resource_manager);
                PremierProfessionalProduct2.setDescription(description_resource_manager);
                PremierProfessionalProduct2.setDeleted_at(deleted_at_resource_manager);
                PremierProfessionalProduct2.setCreated_at(created_at_resource_manager);
                PremierProfessionalProduct2.setUpdated_at(updated_at_resource_manager);


                System.out.println("LES PRODUCT RESOURCE MANAGER PRODUCT 2222 :" + product_resource_manager);
                JSONObject json2 = p2.getJSONObject("product");
                int product_id = (int) json2.get("id");

                //////////////NE pas utiliser, ceci est l'ancien nom
                String product_name = (String) json2.get("name");
                /////////////////////////////////////////////


                String description = (String) json2.get("description");

                JSONObject json3 = json2.getJSONObject("category");
                int product_category_id = (int) json3.get("id");
                //String product_category_name = (String) json3.get("name");
                String product_category_name = (String) json3.getJSONObject("parent").get("name");
                String product_category_created_at = (String) json3.get("created_at");
                String product_category_updated_at = (String) json3.get("updated_at");

                product_category_withoutTree.setId(product_category_id);
                product_category_withoutTree.setName(product_category_name);
                product_category_withoutTree.setCreated_at(product_category_created_at);
                product_category_withoutTree.setUpdated_at(product_category_updated_at);

                product.setDescription(description);
                product.setId(product_id);
                product.setName(product_name);
                product.setCategory(product_category_withoutTree);

                PremierProfessionalProduct2.setProduct(product);

                PremierProfessionalProduct[i] = PremierProfessionalProduct2;


                // pourquoi //// ? parce que / est deja utiliser dans les noms de products
                if(product_category_name.equals("Coiffure Femme")){
                    liste_coifure_femme.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                    liste_coifure_femme2.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                }else if(product_category_name.equals("Bien-Être")){
                    liste_bien_etre.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                    liste_bien_etre2.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                }else if(product_category_name.equals("Beauté")){
                    liste_beaute.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                    liste_beaute2.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                }else if(product_category_name.equals("Homme")){
                    liste_homme.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                    liste_homme2.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                }


            }


            expandableListDetail = new HashMap<String, List<String>>();
            expandableListDetailAutrePrestation = new HashMap<String, List<String>>();


            if(!liste_coifure_femme.isEmpty()) {
                expandableListDetail.put("Coiffure Femme", liste_coifure_femme);
                expandableListDetailAutrePrestation.put("Coiffure Femme", liste_coifure_femme2);
            }
            if(!liste_bien_etre.isEmpty()) {
                expandableListDetail.put("Bien-Être", liste_bien_etre);
                expandableListDetailAutrePrestation.put("Bien-Être", liste_bien_etre2);
            }
            if(!liste_beaute.isEmpty()) {
                expandableListDetail.put("Beauté", liste_beaute);
                expandableListDetailAutrePrestation.put("Beauté", liste_beaute2);
            }
            if(!liste_homme.isEmpty()) {
                expandableListDetail.put("Homme", liste_homme);
                expandableListDetailAutrePrestation.put("Homme", liste_homme2);
            }
            expandableListDetail.put("PHOTOOOOOOOO TEAMS", photo);

        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

        return liste_product;
    }




    public String recherche_donneegeneral(String query) {
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/"+query+"/");

        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }


    public void donneegeneral_jsonparser(String jsonStr) {
        String ls = "";
        ArrayList<String> specialite = new ArrayList<>();
        ArrayList<String> premierprofessionel = new ArrayList<>();
        ArrayList<String> listeprestationpremierprofessionnel = new ArrayList<>();
        ArrayList<String> listelabelprestationpremierprofessionnel = new ArrayList<>();

        if (jsonStr != null) {
            if (jsonStr.contains("{")) {
                try {


                    //JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject c = new JSONObject(jsonStr);

                    //JSONArray contacts = jsonObj.getJSONArray("data");

                    // looping through All Contacts
                    //for (int i = 0; i < contacts.length(); i++) {
                    //JSONObject c = contacts.getJSONObject(i);

                    //String image = c.getString("shop_images");
                    String id_professional = c.getString("id");
                        /*JSONArray pictures = c.getJSONArray("shop_images");

                    Image_N image = new Image_N();
                    ArrayList<String[]> thumbnails = null;
                        String picture = "";
                    for(int i= 0;i<pictures.length();i++){
                        JSONObject p = pictures.getJSONObject(i);
                        if(i==0){
                            picture = p.getJSONObject("image").getString("url");
                        }


                }

                image.setUrl(picture);*/

                    //if (i == 0) {
                            /*String shop_images = c.getString("shop_images");
                            String resource_manager = c.getString("resource_manager");
                            String avg_grade = c.getString("avg_grade");
                            String comments_count = c.getString("comments_count");
                            premierprofessionel.add(shop_images);
                            premierprofessionel.add(resource_manager);
                            premierprofessionel.add(avg_grade);
                            premierprofessionel.add(comments_count);*/


                    String id = c.getString("id");
                    String acquisition = c.getString("acquisition");
                    String geoloc_latitude = c.getString("geoloc_latitude");
                    String geoloc_longitude = c.getString("geoloc_longitude");
                    premierprofessionel.add(id);
                    premierprofessionel.add(acquisition);
                    premierprofessionel.add(geoloc_latitude);
                    premierprofessionel.add(geoloc_longitude);

                    String max_intervention_distance = c.getString("max_intervention_distance");
                    String logo_image = c.getString("logo_image");
                    String url = "null";
                    if(c.getString("logo_image").equals("null")){
                        url = "null";
                    }else{
                        url = c.getJSONObject("logo_image").getString("url");
                    }

                    Image_N image = new Image_N();
                    ArrayList<String[]> thumbnails = null;
                    image.setUrl(url);

                    System.out.println("URL : +++ "+url);
                    PremierProfessionnal = new Professional();

                    //MArche pas ??????????????????
                    PremierProfessionnal.setLogo_image(image);
                    //PremierProfessionnal.setLogo_image(image);


                    String automatic_booking_confirmation = c.getString("automatic_booking_confirmation");
                    String customer_can_choose_resource_booking = c.getString("customer_can_choose_resource_booking");
                    premierprofessionel.add(max_intervention_distance);
                    premierprofessionel.add(logo_image);
                    premierprofessionel.add(automatic_booking_confirmation);
                    premierprofessionel.add(customer_can_choose_resource_booking);

                    String created_at = c.getString("created_at");
                    String updated_at = c.getString("updated_at");
                    String current_subscription_type = c.getString("current_subscription_type");
                    String notifications_preferences_sms = c.getString("notifications_preferences_sms");
                    premierprofessionel.add(created_at);
                    premierprofessionel.add(updated_at);
                    premierprofessionel.add(current_subscription_type);
                    premierprofessionel.add(notifications_preferences_sms);


                    /////////////////SUBSCRIPTION ACCOUNT (FREE Ou PREMIUM)///////////////////
                    Professional_Subscription_Type professional_subscription_type = new Professional_Subscription_Type();
                    JSONObject jsonObj_resource_manager = new JSONObject(current_subscription_type);
                    int id_current_subscription_type = (int) jsonObj_resource_manager.get("id");
                    String name_current_subscription_type = (String) jsonObj_resource_manager.get("name");
                    professional_subscription_type.setId(id_current_subscription_type);
                    professional_subscription_type.setName(name_current_subscription_type);
                    PremierProfessionnal.setProfessional_subscription_type(professional_subscription_type);
                    /////////////////////////////////////////////////////////////////////////


                    String sms_happybirthday_enabled = c.getString("sms_happybirthday_enabled");
                    String sms_happybirthday_sender = c.getString("sms_happybirthday_sender");
                    String sms_happybirthday_content = c.getString("sms_happybirthday_content");
                    String sms_fidelity_enabled = c.getString("sms_fidelity_enabled");
                    String sms_fidelity_sender = c.getString("sms_fidelity_sender");
                    String sms_fidelity_content = c.getString("sms_fidelity_content");
                    String sms_remember_booking_enabled = c.getString("sms_remember_booking_enabled");
                    String discount_exclusivity = c.getString("discount_exclusivity");
                    premierprofessionel.add(sms_happybirthday_enabled);
                    premierprofessionel.add(sms_happybirthday_sender);
                    premierprofessionel.add(sms_happybirthday_content);
                    premierprofessionel.add(sms_fidelity_enabled);
                    premierprofessionel.add(sms_fidelity_sender);
                    premierprofessionel.add(sms_fidelity_content);
                    premierprofessionel.add(sms_remember_booking_enabled);
                    premierprofessionel.add(discount_exclusivity);

                    String preference_resource_type = c.getString("preference_resource_type");
                    String sponsoring_key = c.getString("sponsoring_key");
                    String show_discount = c.getString("show_discount");
                    String shop_name = c.getString("shop_name");
                    premierprofessionel.add(preference_resource_type);
                    premierprofessionel.add(sponsoring_key);
                    premierprofessionel.add(show_discount);
                    premierprofessionel.add(shop_name);

                    String tags = c.getString("tags");
                    String shop_description = c.getString("shop_description");
                    String specialty = c.getString("specialty");
                    String address = c.getString("address");
                    premierprofessionel.add(tags);
                    premierprofessionel.add(shop_description);
                    premierprofessionel.add(specialty);
                    premierprofessionel.add(address);

                    String post_code = c.getString("post_code");
                    String city = c.getString("city");
                    String country = c.getString("country");
                    String type = c.getString("type");
                    premierprofessionel.add(post_code);
                    premierprofessionel.add(city);
                    premierprofessionel.add(country);
                    premierprofessionel.add(type);

                    String shop_phone = c.getString("shop_phone");
                    String shop_email = c.getString("shop_email");
                    String website_link = c.getString("website_link");
                    String facebook_link = c.getString("facebook_link");
                    premierprofessionel.add(shop_phone);
                    premierprofessionel.add(shop_email);
                    premierprofessionel.add(website_link);
                    premierprofessionel.add(facebook_link);

                    String instagram_link = c.getString("instagram_link");
                    String pinterest_link = c.getString("pinterest_link");
                    String product_categories = c.getString("product_categories");
                    premierprofessionel.add(instagram_link);
                    premierprofessionel.add(pinterest_link);
                    premierprofessionel.add(product_categories);


                    String recup = "{" + '"' + "Recup" + '"' + " : [{" + '"' + "parent" + '"';
                    String ahbon = product_categories.replace("[{" + '"' + "parent" + '"', recup);
                    String ahbon2 = ahbon.replace("Z" + '"' + "}]", "Z" + '"' + "}]}");

                    System.out.println("AHBON2 :" + ahbon2);
                    JSONObject jsonObj2 = new JSONObject(ahbon2);
                    JSONArray prestations = jsonObj2.getJSONArray("Recup");

                    PremierProfessionnalProduits = new Product_Category_WithoutTree[prestations.length()];
                    // looping through All Contacts
                    for (int j = 0; j < prestations.length(); j++) {
                        Product_Category_WithoutTree services = new Product_Category_WithoutTree();
                        JSONObject p = prestations.getJSONObject(j);


                        String parent = p.getString("parent");
                        String id_prest = p.getString("id");
                        String name_prest = p.getString("name");
                        String tags_prest = p.getString("tags");
                        String created_at_prest = p.getString("created_at");
                        String updated_at_prest = p.getString("updated_at");
                        listeprestationpremierprofessionnel.add(parent);
                        listeprestationpremierprofessionnel.add(id_prest);
                        listeprestationpremierprofessionnel.add(tags_prest);


                        ////////////////////////PRODUCT CATEGORY
                        String recup2 = "{" + '"' + "Recup2" + '"' + " : [{";
                        String ahbon3 = tags_prest.replace("[{", recup2);
                        String ahbon4 = ahbon3.replace("}]", "}]}");

                        System.out.println("AHBON3 :" + ahbon4);
                        JSONObject jsonObj3 = new JSONObject(ahbon4);
                        JSONArray prestations2 = jsonObj3.getJSONArray("Recup2");
                        PremierProfessionnalProduitsTag = new Product_Category_Tag[prestations2.length()];
                        // looping through All Contacts
                        for (int g = 0; g < prestations2.length(); g++) {
                            Product_Category_Tag tag = new Product_Category_Tag();
                            JSONObject p2 = prestations2.getJSONObject(g);
                            String id_label = p2.getString("id");
                            String label = p2.getString("label");
                            listelabelprestationpremierprofessionnel.add(label);

                            //Remplissage des services
                            tag.setId(Integer.parseInt(id_label));
                            tag.setLabel(label);
                            PremierProfessionnalProduitsTag[g] = tag;

                        }


                        services.setId(Integer.parseInt(id_prest));
                        services.setName(name_prest);
                        services.setTags(PremierProfessionnalProduitsTag);
                        services.setCreated_at(created_at_prest);
                        services.setUpdated_at(updated_at_prest);
                        PremierProfessionnalProduits[j] = services;

                    }


                    String awards = c.getString("awards");
                    String unavailabilities = c.getString("unavailabilities");
                    String schedule = c.getString("schedule");
                    premierprofessionel.add(awards);
                    premierprofessionel.add(unavailabilities);
                    premierprofessionel.add(schedule);


                    //Remplissage du public professionnal
                    PremierProfessionnal.setShop_name(shop_name);
                    PremierProfessionnal.setShop_description(shop_description);
                    PremierProfessionnal.setAddress(address);
                    PremierProfessionnal.setPost_code(post_code);
                    PremierProfessionnal.setCity(city);
                    PremierProfessionnal.setCountry(country);
                    PremierProfessionnal.setShop_phone(shop_phone);
                    PremierProfessionnal.setShop_email(shop_email);
                    PremierProfessionnal.setWebsite_link(website_link);
                    PremierProfessionnal.setFacebook_link(facebook_link);
                    PremierProfessionnal.setInstagram_link(instagram_link);
                    PremierProfessionnal.setPinterest_link(pinterest_link);


                    PremierProfessionnal.setProduct_categories(PremierProfessionnalProduits);
                    PremierProfessionnal.setSchedule(ProfessionnalGenericSchedule);


                    //Remplissage du professionnal
                    PremierProfessionnal.setId(Integer.valueOf(id));
                    PremierProfessionnal.setGeoloc_latitude(Double.parseDouble(geoloc_latitude));
                    PremierProfessionnal.setGeoloc_longitude(Double.parseDouble(geoloc_longitude));
                    if (!max_intervention_distance.equals("null"))
                        PremierProfessionnal.setMax_intervention_distance(Integer.parseInt(max_intervention_distance));
                    else
                        PremierProfessionnal.setMax_intervention_distance(0);
                    PremierProfessionnal.setAutomatic_booking_confirmation(Boolean.parseBoolean(automatic_booking_confirmation));
                    PremierProfessionnal.setCustomer_can_choose_resource_booking(Boolean.parseBoolean(customer_can_choose_resource_booking));
                    PremierProfessionnal.setCreated_at(created_at);
                    PremierProfessionnal.setUpdated_at(updated_at);

                    PremierProfessionnal.setNotification_preferences_sms(notifications_preferences_sms);
                    PremierProfessionnal.setSms_happybirthday_enabled(Boolean.parseBoolean(sms_happybirthday_enabled));
                    PremierProfessionnal.setSms_happybirthday_sender(sms_happybirthday_sender);
                    PremierProfessionnal.setSms_happybirthday_content(sms_happybirthday_content);

                    PremierProfessionnal.setSms_fidelity_enabled(Boolean.parseBoolean(sms_fidelity_enabled));
                    PremierProfessionnal.setSms_fidelity_sender(sms_fidelity_sender);
                    PremierProfessionnal.setSms_fidelity_content(sms_fidelity_content);
                    PremierProfessionnal.setSms_remember_booking_enabled(Boolean.parseBoolean(sms_remember_booking_enabled));
                    PremierProfessionnal.setSponsoring_key(sponsoring_key);


                    System.out.println(PremierProfessionnal.toString());


                    //}

                    specialite.add(id_professional);
                    System.out.println("VAAAAAAAAAAAAAALUE " + id_professional);

                    //}
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }


            }
        }
    }

    public String recherche_schedule(String query) {
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee SCHEDULE");
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional-schedule/professional/"+query+"/");
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee SCHEDULE");
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }


    public void schedule_jsonparser(String jsonStr) {
        if(jsonStr.equals("[]")){
            ProfessionnalGenericSchedule = new Professional_Schedule[0];
        }else {
            try {

                String recup_schedule = "{" + '"' + "data" + '"' + " : [{";
                String schedule1 = jsonStr.replace("[{", recup_schedule);
                String schedule2 = schedule1.replace("}]", "}]}");

                if (!jsonStr.equals("[]")) {
                    JSONObject jsonObj = new JSONObject(schedule2);
                    //JSONArray contacts = jsonObj.getJSONArray("data");
                    JSONArray Professional_Schedule = jsonObj.getJSONArray("data");

                    System.out.println("AHBON3 :" + jsonStr);

                    ProfessionnalGenericSchedule = new Professional_Schedule[Professional_Schedule.length()];
                    for (int j = 0; j < Professional_Schedule.length(); j++) {
                        Professional_Schedule pro_schedule = new Professional_Schedule();
                        JSONObject p2 = Professional_Schedule.getJSONObject(j);
                        String id_schedule = p2.getString("id");
                        String weekeday_schedule = p2.getString("weekday");
                        String begin_time_schedule = p2.getString("begin_time");
                        String end_time_schedule = p2.getString("end_time");

                        //Remplissage des horaires d'ouverture
                        pro_schedule.setId(Integer.parseInt(id_schedule));
                        pro_schedule.setWeekday(Integer.parseInt(weekeday_schedule));
                        pro_schedule.setBegin_time(begin_time_schedule);
                        pro_schedule.setEnd_time(end_time_schedule);
                        ProfessionnalGenericSchedule[j] = pro_schedule;
                    }
                }

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }

    }


    //FAVORIS
    public String recherche_favoris(String query) {
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee Resource");
        GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/favoris/"+query+"/");
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee Resource");
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }

    //AVIS
    public String recherche_avis(String query) {
        GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/comment/professional/"+query+"/");
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }


    public ArrayList<String> avis_jsonparser(String str) {
        ArrayList<String> tableau_commentaire = new ArrayList<>();
        int count_commentaire = 0;
        try {

            if (!str.equals("[]")) {
                JSONObject jsonObj = new JSONObject(str);
                JSONArray tab_comment = jsonObj.getJSONArray("data");


                count_commentaire = tab_comment.length();


                //Remplir la liste des commentaires
                for (int j = 0; j < tab_comment.length(); j++) {
                    JSONObject p2 = tab_comment.getJSONObject(j);

                    String comment_grade = p2.getJSONObject("booking").getJSONObject("comment").getString("grade");
                    tableau_commentaire.add(comment_grade);
                }
            }else{
                count_commentaire=0;
            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

        return tableau_commentaire;

    }


    //SHOP IMAGE
    public ArrayList<String> shop_image_jsonparser2(String jsonStr) {
        ArrayList<String> tableau_image = new ArrayList<>();

        if(jsonStr.equals("[]")){
            tableau_image = new ArrayList<>();
        }else {
            try {

                if (!jsonStr.equals("[]")) {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray Professional_Shop_image = jsonObj.getJSONArray("data");

                    System.out.println("AHBON3 :" + jsonStr);


                    tableau_image = new ArrayList<>();
                    for (int j = 0; j < Professional_Shop_image.length(); j++) {
                        JSONObject p2 = Professional_Shop_image.getJSONObject(j);
                        String url = p2.getJSONObject("image").getString("url");

                        tableau_image.add(url);
                    }
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
        return tableau_image;
    }


    public String recherche_shop_image(String query) {
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee SCHEDULE");
        GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/shop-image/professional/"+query+"/");
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee SCHEDULE");
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }


    public void shop_image_jsonparser(String jsonStr) {
        if(jsonStr.equals("[]")){
            Shop_image = new ArrayList<>();
        }else {
            try {

            /*String recup_schedule = "{" + '"' + "data" + '"' + " : [{";
            String schedule1 = jsonStr.replace("[{", recup_schedule);
            String schedule2 = schedule1.replace("}]", "}]}");*/

                if (!jsonStr.equals("[]")) {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    //JSONArray contacts = jsonObj.getJSONArray("data");
                    JSONArray Professional_Shop_image = jsonObj.getJSONArray("data");

                    System.out.println("AHBON3 :" + jsonStr);


                    Shop_image = new ArrayList<>();
                    //ProfessionnalGenericSchedule = new Professional_Schedule[Professional_Schedule.length()];
                    for (int j = 0; j < Professional_Shop_image.length(); j++) {
                        //Professional_Schedule pro_schedule = new Professional_Schedule();
                        JSONObject p2 = Professional_Shop_image.getJSONObject(j);
                        String url = p2.getJSONObject("image").getString("url");
                        //String weekeday_schedule = p2.getString("weekday");
                        //String begin_time_schedule = p2.getString("begin_time");
                        //String end_time_schedule = p2.getString("end_time");

                        //Remplissage des horaires d'ouverture
                    /*pro_schedule.setId(Integer.parseInt(id_schedule));
                    pro_schedule.setWeekday(Integer.parseInt(weekeday_schedule));
                    pro_schedule.setBegin_time(begin_time_schedule);
                    pro_schedule.setEnd_time(end_time_schedule);
                    ProfessionnalGenericSchedule[j] = pro_schedule;*/

                        Shop_image.add(url);
                    }
                }

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }

    }


    //RESSOURCE
    public String recherche_ressource(String query) {
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee Resource");
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/resource/professional/"+query+"/");
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee Resource");
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }


    public void ressource_jsonparser(String jsonStr) {
        if(jsonStr.equals("[]")){
            ResourceProfessional = new Resource[0];
        }else {
            try {

                String recup_schedule = "{" + '"' + "data" + '"' + " : [{";
                String schedule1 = jsonStr.replace("[{", recup_schedule);
                String schedule2 = schedule1.replace("}]", "}]}");

                if (!jsonStr.equals("[]")) {
                    JSONObject jsonObj = new JSONObject(schedule2);
                    //JSONArray contacts = jsonObj.getJSONArray("data");
                    JSONArray Professional_Shop_image = jsonObj.getJSONArray("data");
                    System.out.println("AHBON3 :" + jsonStr);
                    ResourceProfessional = new Resource[Professional_Shop_image.length()];

                    for (int j = 0; j < Professional_Shop_image.length(); j++) {
                        Resource resource = new Resource();
                        JSONObject p2 = Professional_Shop_image.getJSONObject(j);
                        String id = p2.getString("id");
                        String type = p2.getJSONObject("type").getString("name");
                        String name = p2.getString("name");
                        String surname = p2.getString("surname");

                        resource.setId(Integer.parseInt(id));
                        resource.setName(name);
                        resource.setSurname(surname);

                        ResourceProfessional[j] = resource;
                    }
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }

    }



    /*private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }*/


    public String moyenne(String moy){
        String moyenn = null;

        System.out.println(moy.substring(0,1));
        System.out.println(moy.substring(1,2));
        System.out.println(moy.substring(2,3));
        if(Integer.parseInt(moy.substring(2,3)) <= 3){
            moyenn = moy.substring(0,1);
        }else if(Integer.parseInt(moy.substring(2,3)) >= 4 && Integer.parseInt(moy.substring(2,3)) <= 7){
            moyenn = moy.substring(0,1) + ".5";
        }else if(Integer.parseInt(moy.substring(2,3)) >= 8){
            moyenn = String.valueOf(Integer.parseInt(moy.substring(0,1))+1);
        }


        return moyenn;
    }



}
