package com.gouiranlink.franois.gouiranlinkproject.Homepage;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.Recherche.Research2Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.BaseFragment.ARGS_INSTANCE;

/**
 * Created by François on 27/04/2017.
 */

public class HomeFragment2 extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private LocationManager locationManager = null;
    private MyLocationListener locationListener = null;
    private Boolean connected;
    private String text;
    private FileInputStream fileInputStream = null;
    private GoogleApiClient mGoogleApiClient;
    private Customer customer;
    private String token;

    private String[] place,autocomplete;

    private HomeFragment2.OnFragmentInteractionListener mListener;

    public HomeFragment2() {
        // Required empty public constructor
    }

    public static HomeFragment2 newInstance(int instance, String username, Boolean connected) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        args.putString("username", username);

        args.putBoolean("connected", connected);

        HomeFragment2 firstFragment = new HomeFragment2();
        firstFragment.setArguments(args);
        return firstFragment;
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String username = getArguments().getString("username");
            connected = getArguments().getBoolean("connected");
            token = getArguments().getString("token");
            customer = (Customer) getArguments().getSerializable("Customer");
            place = (String[]) getArguments().getStringArray("place");
            autocomplete = (String[]) getArguments().getStringArray("autocomplete");
            System.out.println("USeeeeeeeeeeeeeeeeeeeeeeeer : "+username);
            System.out.println("USeeeeeeeeeeeeeeeeeeeeeeeer : "+connected);
            if (connected)
                text = String.format(getResources().getString(R.string.welcome_user), username);
            else
                text = "Bonjour,";

            if (customer != null && (customer.ismGouiranLink() || customer.ismFacebook() || customer.ismGoogle())) {
                System.out.println("On passe dedans du coup !");
                text = String.format(getResources().getString(R.string.welcome_user), customer.getSurname());
                connected = true;
            } else
                connected = false;
        }

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new HomeFragment2.MyLocationListener();
        buildGoogleApiClient();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(getActivity(), "Not connected...", Toast.LENGTH_SHORT).show();

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Cherche le dossier "GouiranLink"
        /*try {
            fileInputStream = getContext().openFileInput("GouiranLink");

            Log.d("OUTPUT", "FOUND");
        } catch (FileNotFoundException e) {
            fileInputStream = null;
            Log.d("OUTPUT", "NOT FOUND");
            e.printStackTrace();
        }*/

        View view = inflater.inflate(R.layout.fragment_home2, container, false);


        final Fragment[] fragment = {null};
        final Bundle args = new Bundle();
        args.putSerializable("Customer", customer);
        args.putSerializable("token", token);


        ImageView img1 = (ImageView) view.findViewById(R.id.home_coiffure);
        img1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                args.putSerializable("homepage_click_imageview", "Coiffure");
                args.putSerializable("place", place);
                args.putSerializable("autocomplete", autocomplete);
                fragment[0] = new Research2Fragment();
                fragment[0].setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment[0]).addToBackStack("tag").commit();
            }
        });

        ImageView img2 = (ImageView) view.findViewById(R.id.home_manucure);
        img2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                args.putSerializable("place", place);
                args.putSerializable("autocomplete", autocomplete);
                args.putSerializable("homepage_click_imageview", "Manucure");
                fragment[0] = new Research2Fragment();
                fragment[0].setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment[0]).addToBackStack("tag").commit();
            }
        });

        ImageView img3 = (ImageView) view.findViewById(R.id.home_epilation);
        img3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                args.putSerializable("place", place);
                args.putSerializable("autocomplete", autocomplete);
                args.putSerializable("homepage_click_imageview", "Epilation");
                fragment[0] = new Research2Fragment();
                fragment[0].setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment[0]).addToBackStack("tag").commit();
            }
        });

        ImageView img4 = (ImageView) view.findViewById(R.id.home_coloration);
        img4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                args.putSerializable("place", place);
                args.putSerializable("autocomplete", autocomplete);
                args.putSerializable("homepage_click_imageview", "Coloration");
                fragment[0] = new Research2Fragment();
                fragment[0].setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment[0]).addToBackStack("tag").commit();
            }
        });

        ImageView img5 = (ImageView) view.findViewById(R.id.home_maquillage);
        img5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                args.putSerializable("place", place);
                args.putSerializable("autocomplete", autocomplete);
                args.putSerializable("homepage_click_imageview", "Maquillage");
                fragment[0] = new Research2Fragment();
                fragment[0].setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment[0]).addToBackStack("tag").commit();
            }
        });

        ImageView img6 = (ImageView) view.findViewById(R.id.home_soins_visage);
        img6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                args.putSerializable("place", place);
                args.putSerializable("autocomplete", autocomplete);
                args.putSerializable("homepage_click_imageview", "Soins visage");
                fragment[0] = new Research2Fragment();
                fragment[0].setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment[0]).addToBackStack("tag").commit();
            }
        });

        ImageView img7 = (ImageView) view.findViewById(R.id.home_barbier);
        img7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                args.putSerializable("place", place);
                args.putSerializable("autocomplete", autocomplete);
                args.putSerializable("homepage_click_imageview", "Barbier");
                fragment[0] = new Research2Fragment();
                fragment[0].setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment[0]).addToBackStack("tag").commit();
            }
        });






        /*TextView titre_home_coiffure = (TextView) view.findViewById(R.id.titre_home_coiffure);
        titre_home_coiffure.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // do whatever we wish!
            }
        });*/

        return view;


    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView welcomeUser = new TextView(getActivity());
        welcomeUser = (TextView) getActivity().findViewById(R.id.welcome_user);
        welcomeUser.setText(text);
        getActivity().findViewById(R.id.fragmenthomepage).setVisibility(View.VISIBLE);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragment.OnFragmentInteractionListener) {
            mListener = (HomeFragment2.OnFragmentInteractionListener) context;
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
        getActivity().setTitle(R.string.gouiranlinktitle);
    }




    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(getActivity(), "connect", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getActivity(), "connection suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "Failed to connect", Toast.LENGTH_SHORT).show();
    }













    /*---------Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {

            double longitude = loc.getLongitude();
            double latitude = loc.getLatitude();
            JSONObject obj;
            JSONArray arr;

    /*----------to get City-Name from coordinates ------------- */
            Geocoder gcd = new Geocoder(getActivity().getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(), loc
                        .getLongitude(), 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
        }

    }



}
