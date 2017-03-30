package com.example.franois.gouiranlinkproject.Homepage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.Object.Customer;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.ToolsClasses.DownloadImageTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.example.franois.gouiranlinkproject.ToolsClasses.BaseFragment.ARGS_INSTANCE;

/*
Homepage of the application
 */

public class HomeFragment extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener {

    private LocationManager locationManager = null;
    private MyLocationListener locationListener = null;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private FileInputStream fileInputStream = null;

    private Boolean connected;
    private String text;
    private final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(int instance, String username, Boolean connected) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        args.putString("username", username);

        args.putBoolean("connected", connected);

        HomeFragment firstFragment = new HomeFragment();
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
    public void onConnectionFailed(ConnectionResult arg0) {
        Toast.makeText(getActivity(), "Failed to connect...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnected(Bundle arg0) {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        final Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Acrom W00 Medium.ttf");

        if (mLastLocation != null) {
            generateAroundMe(font);
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Toast.makeText(getActivity(), "Connection suspended...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String username = getArguments().getString("username");
            connected = getArguments().getBoolean("connected");
            Customer customer = (Customer) getArguments().getSerializable("Customer");
            if (connected)
                text = String.format(getResources().getString(R.string.welcome_user), username);
            else
                text = "Bonjour,";

            if (customer != null && (customer.ismGouiranLink() || customer.ismFacebook() || customer.ismGoogle())) {
                text = String.format(getResources().getString(R.string.welcome_user), customer.getSurname());
                connected = true;
            } else
                connected = false;
        }

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        buildGoogleApiClient();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(getActivity(), "Not connected...", Toast.LENGTH_SHORT).show();

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

    private String[] revertArray(String array[]) {
        String res[] = new String[] {};
        List<String> list = Arrays.asList(array);
        Collections.reverse(list);
        res = (String [])list.toArray();
        return (res);
    }

    private void generateRecentResearches(Typeface font) {
        RelativeLayout myRecentResearchesProposal = (RelativeLayout) getActivity().findViewById(R.id.my_recent_researches_proposal);

        byte[] buffer = new byte[1024];
        String fileContent = "";
        int n;
        int i = 0;
        try {
            while (fileInputStream != null && (n = fileInputStream.read(buffer)) != -1) {
                fileContent += new String(buffer, 0, n);
                i++;
            }
            if (fileInputStream != null)
                fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextView textView = (TextView) getActivity().findViewById(R.id.my_recent_researches);
        textView.setTypeface(font);
        if (connected != null && !connected)
            textView.setText(R.string.top_recherche);
        else {
            String[] parts = fileContent.split("`");
            parts = revertArray(parts);
            if (i > 2) {
                try {
                    FileOutputStream fileOutputStream = getContext().openFileOutput("GouiranLink", MODE_PRIVATE);

                    for (int j = 0; j < 10; j++) {
                        fileOutputStream.write(parts[j].getBytes());
                    }
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            ImageView imageView1 = new ImageView(getActivity());
            ImageView imageView2 = new ImageView(getActivity());
            ImageView imageView3 = new ImageView(getActivity());
            ImageView imageView4 = new ImageView(getActivity());
            ImageView imageView5 = new ImageView(getActivity());
            ImageView imageView6 = new ImageView(getActivity());
            ImageView imageView7 = new ImageView(getActivity());
            ImageView imageView8 = new ImageView(getActivity());
            ImageView imageView9 = new ImageView(getActivity());
            TextView textView1 = new TextView(getActivity());
            TextView textView2 = new TextView(getActivity());
            TextView textView3 = new TextView(getActivity());
            TextView textView4 = new TextView(getActivity());
            TextView textView5 = new TextView(getActivity());
            TextView textView6 = new TextView(getActivity());
            TextView textView7 = new TextView(getActivity());
            TextView textView8 = new TextView(getActivity());
            TextView textView9 = new TextView(getActivity());

            if (parts.length >= 1 && parts[0] != null && !Objects.equals(parts[0], "")) {
                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                /*imageView1.setImageResource(R.drawable.coloration1);
                imageView1.setId(View.generateViewId());
                imageView1.setLayoutParams(params1);*/


                textView1.setPadding(5, 0, 5, 0);
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textView1.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                //textView1.setText(R.string.dynamic_recent_1);
                textView1.setText(parts[0]);
                textView1.setLayoutParams(params1);
                textView1.setId(View.generateViewId());
                textView1.setTypeface(font);
                //textView1.setTextAlignment(imageView1.getId());

                myRecentResearchesProposal.addView(textView1);
            }

             /*                   <ImageView
            android:id="@+id/around_1"
            android:layout_width="180dp"
            android:layout_height="160dp"
            android:onClick="onClick"
            android:clickable="true"/>
                    <TextView
            android:id="@+id/around_1_text"
            android:layout_alignStart="@id/around_1"
            android:layout_alignTop="@id/around_1"
            android:layout_alignEnd="@id/around_1"
            android:layout_alignBottom="@id/around_1"
            style="@style/AroundMeProposals"/>*/

            if (parts.length >= 2 && parts[1] != null && !Objects.equals(parts[1], "")) {
                RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                textView2.setPadding(5, 0, 5, 0);
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView2.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                textView2.setText(parts[1]);
                //textView2.setText(R.string.dynamic_recent_2);
                params2.addRule(RelativeLayout.END_OF, textView1.getId());
                textView2.setLayoutParams(params2);
                textView2.setId(View.generateViewId());
                textView2.setTypeface(font);
                myRecentResearchesProposal.addView(textView2);
            }

            if (parts.length >= 3 && parts[2] != null && !Objects.equals(parts[2], "")) {
                RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                textView3.setPadding(5, 0, 5, 0);
                textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView3.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                textView3.setText(parts[2]);
                //textView3.setText(R.string.dynamic_recent_3);
                params3.addRule(RelativeLayout.END_OF, textView1.getId());
                params3.addRule(RelativeLayout.BELOW, textView2.getId());
                textView3.setLayoutParams(params3);
                textView3.setId(View.generateViewId());
                textView3.setTypeface(font);
                myRecentResearchesProposal.addView(textView3);
            }

            if (parts.length >= 4 && parts[3] != null && !Objects.equals(parts[3], "")) {
                RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                textView4.setPadding(5, 0, 5, 0);
                textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textView4.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                textView4.setText(parts[3]);
                //textView4.setText(R.string.dynamic_recent_4);
                params4.addRule(RelativeLayout.END_OF, textView2.getId());
                textView4.setLayoutParams(params4);
                textView4.setId(View.generateViewId());
                textView4.setTypeface(font);
                myRecentResearchesProposal.addView(textView4);
            }

            if (parts.length >= 5 && parts[4] != null && !Objects.equals(parts[4], "")) {
                RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                textView5.setPadding(5, 0, 5, 0);
                textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView5.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                textView5.setText(parts[4]);
                //textView5.setText(R.string.dynamic_recent_5);
                params5.addRule(RelativeLayout.END_OF, textView4.getId());
                textView5.setLayoutParams(params5);
                textView5.setId(View.generateViewId());
                textView5.setTypeface(font);
                myRecentResearchesProposal.addView(textView5);
            }

            if (parts.length >= 6 && parts[5] != null && !Objects.equals(parts[5], "")) {
                RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                textView6.setPadding(5, 0, 5, 0);
                textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView6.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                textView6.setText(parts[5]);
                //textView6.setText(R.string.dynamic_recent_6);
                params6.addRule(RelativeLayout.END_OF, textView4.getId());
                params6.addRule(RelativeLayout.BELOW, textView5.getId());
                textView6.setLayoutParams(params6);
                textView6.setId(View.generateViewId());
                textView6.setTypeface(font);
                myRecentResearchesProposal.addView(textView6);
            }

            if (parts.length >= 7 && parts[6] != null && !Objects.equals(parts[6], "")) {
                RelativeLayout.LayoutParams params7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                textView7.setPadding(5, 0, 5, 0);
                textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textView7.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                textView7.setText(parts[6]);
                //textView7.setText(R.string.dynamic_recent_7);
                params7.addRule(RelativeLayout.END_OF, textView5.getId());
                textView7.setLayoutParams(params7);
                textView7.setId(View.generateViewId());
                textView7.setTypeface(font);
                myRecentResearchesProposal.addView(textView7);
            }

            if (parts.length >= 8 && parts[7] != null && !Objects.equals(parts[7], "")) {
                RelativeLayout.LayoutParams params8 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                textView8.setPadding(5, 0, 5, 0);
                textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView8.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                textView8.setText(parts[7]);
                //textView8.setText(R.string.dynamic_recent_8);
                params8.addRule(RelativeLayout.END_OF, textView7.getId());
                textView8.setLayoutParams(params8);
                textView8.setId(View.generateViewId());
                textView8.setTypeface(font);
                myRecentResearchesProposal.addView(textView8);
            }

            if (parts.length >= 9 && parts[8] != null && !Objects.equals(parts[8], "")) {
                RelativeLayout.LayoutParams params9 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                textView9.setPadding(5, 0, 5, 0);
                textView9.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView9.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                textView9.setText(parts[8]);
                //textView9.setText(R.string.dynamic_recent_9);
                params9.addRule(RelativeLayout.END_OF, textView7.getId());
                params9.addRule(RelativeLayout.BELOW, textView8.getId());
                textView9.setLayoutParams(params9);
                textView9.setId(View.generateViewId());
                textView9.setTypeface(font);
                myRecentResearchesProposal.addView(textView9);
            }
        }

    }

    private void generateAroundMe(Typeface font) {

        TextView textView = (TextView) getActivity().findViewById(R.id.around_me);
        ImageView imageView;

        NearbyProfessionals nearbyProfessionals = new NearbyProfessionals((int) mLastLocation.getLatitude(), (int) mLastLocation.getLongitude());
        String[] shopImageList = nearbyProfessionals.getShopImageList();
        String[] shopNameList = nearbyProfessionals.getShopNameList();

        textView.setTypeface(font);

        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Votre GPS est désactivé.", Toast.LENGTH_SHORT).show();
        }

        if (shopImageList[0] != null && shopNameList[0] != null) {
            imageView = (ImageView) getActivity().findViewById(R.id.around_1);
            textView = (TextView) getActivity().findViewById(R.id.around_1_text);
            if (shopImageList[0] != null) {
                new DownloadImageTask(imageView).execute(shopImageList[0]);
            }
            if (shopNameList[0] != null) {
                textView.setText(shopNameList[0]);
            }
            textView.setTypeface(font);
        }

        if (shopImageList[1] != null && shopNameList[1] != null) {
            imageView = (ImageView) getActivity().findViewById(R.id.around_2);
            textView = (TextView) getActivity().findViewById(R.id.around_2_text);
            if (shopImageList[1] != null) {
                new DownloadImageTask(imageView).execute(shopImageList[1]);
            }
            if (shopNameList[1] != null) {
                textView.setText(shopNameList[1]);
            }
            textView.setTypeface(font);
        }
        if (shopImageList[2] != null && shopNameList[2] != null) {
            imageView = (ImageView) getActivity().findViewById(R.id.around_3);
            textView = (TextView) getActivity().findViewById(R.id.around_3_text);
            if (shopImageList[2] != null) {
                new DownloadImageTask(imageView).execute(shopImageList[2]);
            }
            if (shopNameList[2] != null) {
                textView.setText(shopNameList[2]);
            }
            textView.setTypeface(font);
        }
        if (shopImageList[3] != null && shopNameList[3] != null) {
            imageView = (ImageView) getActivity().findViewById(R.id.around_4);
            textView = (TextView) getActivity().findViewById(R.id.around_4_text);
            if (shopImageList[3] != null) {
                new DownloadImageTask(imageView).execute(shopImageList[3]);
            }
            if (shopNameList[3] != null) {
                textView.setText(shopNameList[3]);
            }
            textView.setTypeface(font);
        }
        if (shopImageList[4] != null && shopNameList[4] != null) {
            imageView = (ImageView) getActivity().findViewById(R.id.around_5);
            textView = (TextView) getActivity().findViewById(R.id.around_5_text);
            if (shopImageList[4] != null) {
                new DownloadImageTask(imageView).execute(shopImageList[4]);
            }
            if (shopNameList[4] != null) {
                textView.setText(shopNameList[4]);
            }
            textView.setTypeface(font);
        }

    }

    private void generateGouiranLinkSelection(Typeface font) {
        RelativeLayout gouiranLinkSelectionProposals = (RelativeLayout) getActivity().findViewById(R.id.gouiran_link_selection_proposals);

        TextView textView = (TextView) getActivity().findViewById(R.id.gouiran_link_selection);
        textView.setTypeface(font);

        TextView textView1 = new TextView(getActivity());
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView1.setPadding(5, 0, 5, 0);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView1.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        textView1.setText(R.string.dynamic_selection_1);
        textView1.setLayoutParams(params1);
        textView1.setId(View.generateViewId());
        textView1.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView1);

        TextView textView2 = new TextView(getActivity());
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView2.setPadding(5, 0, 5, 0);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView2.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        textView2.setText(R.string.dynamic_selection_2);
        params2.addRule(RelativeLayout.END_OF, textView1.getId());
        textView2.setLayoutParams(params2);
        textView2.setId(View.generateViewId());
        textView2.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView2);

        TextView textView3 = new TextView(getActivity());
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView3.setPadding(5, 0, 5, 0);
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView3.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        textView3.setText(R.string.dynamic_selection_3);
        params3.addRule(RelativeLayout.END_OF, textView1.getId());
        params3.addRule(RelativeLayout.BELOW, textView2.getId());
        textView3.setLayoutParams(params3);
        textView3.setId(View.generateViewId());
        textView3.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView3);

        TextView textView4 = new TextView(getActivity());
        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView4.setPadding(5, 0, 5, 0);
        textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView4.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        textView4.setText(R.string.dynamic_selection_4);
        params4.addRule(RelativeLayout.END_OF, textView2.getId());
        textView4.setLayoutParams(params4);
        textView4.setId(View.generateViewId());
        textView4.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView4);

        TextView textView5 = new TextView(getActivity());
        RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView5.setPadding(5, 0, 5, 0);
        textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView5.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        textView5.setText(R.string.dynamic_selection_5);
        params5.addRule(RelativeLayout.END_OF, textView4.getId());
        textView5.setLayoutParams(params5);
        textView5.setId(View.generateViewId());
        textView5.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView5);

        TextView textView6 = new TextView(getActivity());
        RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView6.setPadding(5, 0, 5, 0);
        textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView6.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        textView6.setText(R.string.dynamic_selection_6);
        params6.addRule(RelativeLayout.END_OF, textView4.getId());
        params6.addRule(RelativeLayout.BELOW, textView5.getId());
        textView6.setLayoutParams(params6);
        textView6.setId(View.generateViewId());
        textView6.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView6);

        TextView textView7 = new TextView(getActivity());
        RelativeLayout.LayoutParams params7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView7.setPadding(5, 0, 5, 0);
        textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView7.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        textView7.setText(R.string.dynamic_selection_7);
        params7.addRule(RelativeLayout.END_OF, textView5.getId());
        textView7.setLayoutParams(params7);
        textView7.setId(View.generateViewId());
        textView7.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView7);

        TextView textView8 = new TextView(getActivity());
        RelativeLayout.LayoutParams params8 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView8.setPadding(5, 0, 5, 0);
        textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView8.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        textView8.setText(R.string.dynamic_selection_8);
        params8.addRule(RelativeLayout.END_OF, textView7.getId());
        textView8.setLayoutParams(params8);
        textView8.setId(View.generateViewId());
        textView8.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView8);

        TextView textView9 = new TextView(getActivity());
        RelativeLayout.LayoutParams params9 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView9.setPadding(5, 0, 5, 0);
        textView9.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView9.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        textView9.setText(R.string.dynamic_selection_9);
        params9.addRule(RelativeLayout.END_OF, textView7.getId());
        params9.addRule(RelativeLayout.BELOW, textView8.getId());
        textView9.setLayoutParams(params9);
        textView9.setId(View.generateViewId());
        textView9.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView9);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            fileInputStream = getContext().openFileInput("GouiranLink");

            Log.d("OUTPUT", "FOUND");
        } catch (FileNotFoundException e) {
            fileInputStream = null;
            Log.d("OUTPUT", "NOT FOUND");
            e.printStackTrace();
        }

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        buildGoogleApiClient();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(getActivity(), "Not connected...", Toast.LENGTH_SHORT).show();

        getActivity().getAssets();
        TextView textView;
        final Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Acrom W00 Medium.ttf");
        generateRecentResearches(font);
        generateGouiranLinkSelection(font);

        TextView welcomeUser = new TextView(getActivity());
        welcomeUser = (TextView) getActivity().findViewById(R.id.welcome_user);
        welcomeUser.setText(text);
        welcomeUser.setTypeface(font);
        welcomeUser.setTextColor(ContextCompat.getColor(getContext(), R.color.GouiranDarkBlue));

        textView = (TextView) getActivity().findViewById(R.id.invite_your_friends);
        textView.setTypeface(font);


        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.invitation_gouiran_link));
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.invation_text));

        Button button = (Button) view.findViewById(R.id.invite_your_friends_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });

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
        getActivity().setTitle(R.string.home);
    }
}
