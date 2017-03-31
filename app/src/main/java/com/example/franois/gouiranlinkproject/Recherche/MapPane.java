package com.example.franois.gouiranlinkproject.Recherche;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MapPane extends FragmentActivity implements OnMapReadyCallback {

    private String[] shopNameList = new String[10];
    private String[] shopImageList= new String[10];
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

    private GoogleMap mMap;
    private static final int REQUEST_CODE_LOCATION = 123;
    private LocationManager lManager;
    private Location location;

    ArrayList<ArrayList<String>> thumbs2 = new ArrayList<ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartes2);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String[] thumbs=(String[])bundle.getSerializable("tableau");
        thumbs2=(ArrayList<ArrayList<String>>)bundle.getSerializable("donnée_geolocalise");
        recherche_bool =(boolean)bundle.getBoolean("recherche_boolean");


        ListView mesresultats_map = (ListView)findViewById(R.id.mesresultats_map);
        System.out.println("Boooooooooooooooolean"+recherche_bool);
        if(!recherche_bool){
            String[] results = thumbs;
            final List<String> resultsList = new ArrayList<String>(Arrays.asList(results));
            ArrayAdapter<String> tableau = new ArrayAdapter<String>(this, R.layout.services, resultsList);
            mesresultats_map.setAdapter(tableau);
        }else{
            String[] results = new String[5];
            for(int i =0; i<thumbs2.size();i++){
                System.out.println("Boooooooooooooooolean et thumbs2 :"+thumbs2.get(i).get(3));
                results[i] =  thumbs2.get(i).get(3);
            }
            final List<String> resultsList = new ArrayList<String>(Arrays.asList(results));
            ArrayAdapter<String> tableau = new ArrayAdapter<String>(this, R.layout.services, resultsList);
            mesresultats_map.setAdapter(tableau);
        }



        lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // mMap.setMyLocationEnabled(false);
            System.out.println("maaaaaaaaaaaaaaaaarche localisation :)");
        } else {
            String[] permissionRequested = {android.Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissionRequested, REQUEST_CODE_LOCATION);
            System.out.println("maaaaaaaaaaaaaaaaarche pas localisation");
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }else {


            if(!recherche_bool){
                locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                mMap.setMyLocationEnabled(true);
                criteria = new Criteria();
                bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true));
                Location location1 = locationManager.getLastKnownLocation(bestProvider);
                latitude = location1.getLatitude();
                longitude = location1.getLongitude();

                LatLng latLng = new LatLng(latitude, longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));

                System.out.println("latitude " + latitude +" longitude "+ longitude);
                recherche_autour(latitude,longitude);
                //shopImageList = new String[5];
                //shopNameList = new String[5];
                for(int i =0;i<10;i++){
                    if (LatitudeList[i]==null) {

                    }
                    else{
                        System.out.println("LATITUDE de " + i +" : "+ LatitudeList[i]);
                        System.out.println("Longitude de " + i +" : "+ LongitudeList[i]);
                        LatLng premier_trouve = new LatLng(Double.parseDouble(LatitudeList[i]),Double.parseDouble(LongitudeList[i]));
                        mMap.addMarker(new MarkerOptions().position(premier_trouve).title(shopNameList[i]));
                    }
                }
            }else{
                System.out.println("Allo2222");

                String[] tableau_shop_name = new String[5];
                String[] tableau_latitude = new String[5];
                String[] tableau_longitude = new String[5];
                for (int i = 0; i < thumbs2.size(); i++) {
                    tableau_latitude[i] = thumbs2.get(i).get(1);
                    tableau_longitude[i] = thumbs2.get(i).get(2);
                    tableau_shop_name[i] = thumbs2.get(i).get(3);
                }

                locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
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
                for (int i = 0; i < 5; i++) {
                    if (tableau_latitude[i] == null) {

                    } else {
                        System.out.println("LATITUDE de " + i + " : " + tableau_latitude[i]);
                        System.out.println("Longitude de " + i + " : " + tableau_longitude[i]);
                        LatLng premier_trouve = new LatLng(Double.parseDouble(tableau_latitude[i]), Double.parseDouble(tableau_longitude[i]));
                        mMap.addMarker(new MarkerOptions().position(premier_trouve).title(tableau_shop_name[i]));
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "impossible de geolocaliser le portable utilisateur", Toast.LENGTH_SHORT).show();
                // Permission was denied. Display an error message.
            }
        }
    }


    public void onLocationChanged(Location location) {
        //Lorsque la position change...
        Log.i("Tuto géolocalisation", "La position a changé.");
        //... on stop le cercle de chargement
        setProgressBarIndeterminateVisibility(false);
        //... on active le bouton pour afficher l'adresse
        //findViewById(R.id.afficherAdresse).setEnabled(true);
        //... on sauvegarde la position
        this.location = location;
        //... on l'affiche
        //afficherLocation();
        //... et on spécifie au service que l'on ne souhaite plus avoir de mise à jour
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                Toast.makeText(MapPane.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
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
                shopNameList[i] = arr.getJSONObject(i).getString("shop_name");
                LatitudeList[i] = arr.getJSONObject(i).getString("geoloc_latitude");
                LongitudeList[i] = arr.getJSONObject(i).getString("geoloc_longitude");
            }

            //if(arr.getJSONObject(i).getJSONObject("current_subscription_type").getString("name").equals)
            /*for (int j = 0; j < arr.getJSONObject(i).getJSONArray("shop_images").length(); j++) {
                if(!arr.getJSONObject(i).getJSONArray("shop_images").equals("[]")) {
                    if (arr.getJSONObject(i).getJSONArray("shop_images").getJSONObject(j).getJSONObject("image").getJSONObject("thumbnails").getJSONObject("standard").getString("url") != null) {
                        shopImageList[i] = arr.getJSONObject(i).getJSONArray("shop_images").getJSONObject(j).getJSONObject("image").getJSONObject("thumbnails").getJSONObject("search").getString("url");
                    }
                }
            }*/

        }

    } catch (InterruptedException | JSONException | ExecutionException e) {
        e.printStackTrace();
    }
}



}
