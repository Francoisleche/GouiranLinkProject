package com.gouiranlink.franois.gouiranlinkproject.Homepage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.gouiranlink.franois.gouiranlinkproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.BaseFragment.ARGS_INSTANCE;

/**
 * Created by François on 27/04/2017.
 */

public class HomeFragment2 extends Fragment {

    private FileInputStream fileInputStream = null;

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

        return (inflater.inflate(R.layout.fragment_home2, container, false));



    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

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
        getActivity().setTitle(R.string.home);
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
