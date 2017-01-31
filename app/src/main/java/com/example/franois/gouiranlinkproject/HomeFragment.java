package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadFactory;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import static com.example.franois.gouiranlinkproject.R.id.imageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements ConnectionCallbacks, OnConnectionFailedListener{

    //The minimum distance to change updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = -10; // 10 meters
    //The minimum time beetwen updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 0;//1000 * 60 * 1; // 1 minute
    private LocationManager locationManager = null;
    private MyLocationListener locationListener = null;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    double lastLatitude = 0;
    double lastLongitude = 0;

    Boolean connected;
    TextView welcomeUser;
    String username = null;
    String text;
    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance(int instance, String username) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        args.putString("username", username);

        HomeFragment firstFragment = new HomeFragment();
        firstFragment.setArguments(args);
        return firstFragment;
    }

    protected synchronized void buildGoogleApiClient() {
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

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        lastLatitude = mLastLocation.getLatitude();
        lastLongitude = mLastLocation.getLongitude();

        /*Toast.makeText(getActivity(), "Latitude: " + String.valueOf(mLastLocation.getLatitude()) + "Longitude: " +
                String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_SHORT).show();

        Log.d("POSITION", "Latitude: " + String.valueOf(mLastLocation.getLatitude()) + "Longitude: " +
                String.valueOf(mLastLocation.getLongitude()));*/

        final Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Acrom W00 Medium.ttf");

        generateRecentResearches(font);
        generateAroundMe(font);
        generateGouiranLinkSelection(font);


    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Toast.makeText(getActivity(), "Connection suspended...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString("username");
            connected = getArguments().getBoolean("connected");
        }
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        buildGoogleApiClient();

        if(mGoogleApiClient!= null){
            mGoogleApiClient.connect();
        }
        else
            Toast.makeText(getActivity(), "Not connected...", Toast.LENGTH_SHORT).show();

        //Intent i = new Intent(getActivity(), MainHomePage.class);
        //startActivity(i);

    }

    /*---------Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {

        //LocationResult[] locationResult = new LocationResult[5];

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

            /*String gps = "GPS:" + latitude + ", " + longitude;
            Toast.makeText(getActivity(), gps, Toast.LENGTH_SHORT).show();
            Log.d("GPS: ",latitude+", "+longitude);*/

            /*String resp;
            GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/?query[geoloc][latitude]=" + String.valueOf((int)latitude)
                    + "&query[geoloc][longitude]=" + String.valueOf((int)longitude));
            Log.d("REQUEST", "https://www.gouiran-beaute.com/link/api/v1/professional/?query[geoloc][latitude]=" + String.valueOf((int)latitude)
            + "&query[geoloc][longitude]=" + String.valueOf((int)longitude));
            try {
                resp = getRequest.execute().get();
                obj = new JSONObject(resp);
                arr = obj.getJSONArray("data");
                Log.d("ARRAY=", String.valueOf(arr));
                Log.d("TAG", resp);
                int i = 0;
                for (i = 0; i < arr.length() && i < 5; i++) {
                    locationResult[i] = new LocationResult();


                    locationResult[i].setShop_name(arr.getJSONObject(i).getString("shop_name"));
                    locationResult[i].setLogo_image_url(arr.getJSONObject(i).getJSONObject("logo_image").getJSONObject("thumbnails").getJSONObject("search").getString("url"));
                    Log.d("TAG3", locationResult[i].getShop_name());
                    Log.d("TAG3", locationResult[i].getLogo_image_url());
                    //Log.d("TAG3", locationResult[i].getLogo_image_url());
                    //Log.d("TAG3", locationResult[i].getShop_name());
                }

                Log.d("TAG4", String.valueOf(i));
            } //catch (InterruptedException | ExecutionException | JSONException e) {
            catch (JSONException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }*/


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

        /*public LocationResult[] getLocationResult() {
            return locationResult;
        }

        public void setLocationResult(LocationResult[] locationResult) {
            this.locationResult = locationResult;
        }*/
    }

    private void generateRecentResearches(Typeface font) {
        RelativeLayout myRecentResearchesProposal = (RelativeLayout)getActivity().findViewById(R.id.my_recent_researches_proposal);

        TextView textView = (TextView)getActivity().findViewById(R.id.my_recent_researches);
        textView.setTypeface(font);
        if (connected)
            textView.setText(R.string.top_recherche);

        TextView textView1 = new TextView(getActivity());
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView1.setPadding(5, 0, 5, 0);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView1.setTextColor(getResources().getColor(R.color.black));
        textView1.setText(R.string.dynamic_recent_1);
        textView1.setLayoutParams(params1);
        textView1.setId(1);
        textView1.setTypeface(font);
        myRecentResearchesProposal.addView(textView1);

        TextView textView2 = new TextView(getActivity());
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView2.setPadding(5, 0, 5, 0);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView2.setTextColor(getResources().getColor(R.color.black));
        textView2.setText(R.string.dynamic_recent_2);
        params2.addRule(RelativeLayout.END_OF, 1);
        textView2.setLayoutParams(params2);
        textView2.setId(2);
        textView2.setTypeface(font);
        myRecentResearchesProposal.addView(textView2);

        TextView textView3 = new TextView(getActivity());
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView3.setPadding(5, 0, 5, 0);
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView3.setTextColor(getResources().getColor(R.color.black));
        textView3.setText(R.string.dynamic_recent_3);
        params3.addRule(RelativeLayout.END_OF, 1);
        params3.addRule(RelativeLayout.BELOW, 2);
        textView3.setLayoutParams(params3);
        textView3.setId(3);
        textView3.setTypeface(font);
        myRecentResearchesProposal.addView(textView3);

        TextView textView4 = new TextView(getActivity());
        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView4.setPadding(5, 0, 5, 0);
        textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView4.setTextColor(getResources().getColor(R.color.black));
        textView4.setText(R.string.dynamic_recent_4);
        params4.addRule(RelativeLayout.END_OF, 2);
        textView4.setLayoutParams(params4);
        textView4.setId(4);
        textView4.setTypeface(font);
        myRecentResearchesProposal.addView(textView4);

        TextView textView5 = new TextView(getActivity());
        RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView5.setPadding(5, 0, 5, 0);
        textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView5.setTextColor(getResources().getColor(R.color.black));
        textView5.setText(R.string.dynamic_recent_5);
        params5.addRule(RelativeLayout.END_OF, 4);
        textView5.setLayoutParams(params5);
        textView5.setId(5);
        textView5.setTypeface(font);
        myRecentResearchesProposal.addView(textView5);

        TextView textView6 = new TextView(getActivity());
        RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView6.setPadding(5, 0, 5, 0);
        textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView6.setTextColor(getResources().getColor(R.color.black));
        textView6.setText(R.string.dynamic_recent_6);
        params6.addRule(RelativeLayout.END_OF, 4);
        params6.addRule(RelativeLayout.BELOW, 5);
        textView6.setLayoutParams(params6);
        textView6.setId(6);
        textView6.setTypeface(font);
        myRecentResearchesProposal.addView(textView6);

        TextView textView7 = new TextView(getActivity());
        RelativeLayout.LayoutParams params7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView7.setPadding(5, 0, 5, 0);
        textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView7.setTextColor(getResources().getColor(R.color.black));
        textView7.setText(R.string.dynamic_recent_7);
        params7.addRule(RelativeLayout.END_OF, 5);
        textView7.setLayoutParams(params7);
        textView7.setId(7);
        textView7.setTypeface(font);
        myRecentResearchesProposal.addView(textView7);

        TextView textView8 = new TextView(getActivity());
        RelativeLayout.LayoutParams params8 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView8.setPadding(5, 0, 5, 0);
        textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView8.setTextColor(getResources().getColor(R.color.black));
        textView8.setText(R.string.dynamic_recent_8);
        params8.addRule(RelativeLayout.END_OF, 7);
        textView8.setLayoutParams(params8);
        textView8.setId(8);
        textView8.setTypeface(font);
        myRecentResearchesProposal.addView(textView8);

        TextView textView9 = new TextView(getActivity());
        RelativeLayout.LayoutParams params9 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView9.setPadding(5, 0, 5, 0);
        textView9.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView9.setTextColor(getResources().getColor(R.color.black));
        textView9.setText(R.string.dynamic_recent_9);
        params9.addRule(RelativeLayout.END_OF, 7);
        params9.addRule(RelativeLayout.BELOW, 8);
        textView9.setLayoutParams(params9);
        textView9.setId(9);
        textView9.setTypeface(font);
        myRecentResearchesProposal.addView(textView9);

    }

    private void generateAroundMe(Typeface font) {



        Toast.makeText(getActivity(), "Latitude: " + String.valueOf(lastLatitude) + "Longitude: " +
                String.valueOf(lastLongitude), Toast.LENGTH_SHORT).show();


        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //alertbox("Gps Status!!", "Your GPS is: OFF");
            System.out.println("Your GPS is: OFF");
        }

        Log.d("TAG1", "avant");
        /*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);*/
        Log.d("TAG1", "après");

        /*= new LocationResult[5];
        locationResult = locationListener.getLocationResult();*/

        TextView textView = (TextView) getActivity().findViewById(R.id.around_me);
        textView.setTypeface(font);

        ImageView imageView;

        /*if (locationResult != null) {
            Log.d("TAGTAGTAG", String.valueOf(locationResult.length));
            if (locationResult.length >= 1) {
                imageView = (ImageView) getActivity().findViewById(R.id.around_1);
                textView = (TextView) getActivity().findViewById(R.id.around_1_text);
                //new DownloadImageTask(imageView).execute("http://polaris.hs.llnwd.net/o40/vic/2017/img/motorcycles/my17-motorcycles-page/cruisers-en-us.png");
                if (locationResult[0].getLogo_image_url() != null) {
                    new DownloadImageTask(imageView).execute(locationResult[0].getLogo_image_url());
                }
                //textView.setText(R.string.dynamic_around_1);
                if (locationResult[0].getShop_name() != null) {
                    textView.setText(locationResult[0].getShop_name());
                }
                textView.setTypeface(font);
            }

            if (locationResult.length >= 2) {
                imageView = (ImageView) getActivity().findViewById(R.id.around_2);
                textView = (TextView) getActivity().findViewById(R.id.around_2_text);
                //new DownloadImageTask(imageView).execute("http://i2.cdn.cnn.com/cnnnext/dam/assets/160201113353-modern-motorcycle-style-13-super-169.jpg");
                if (locationResult[0].getLogo_image_url() != null) {
                    new DownloadImageTask(imageView).execute(locationResult[1].getLogo_image_url());
                }
                //textView.setText(R.string.dynamic_around_2);
                if (locationResult[0].getShop_name() != null) {
                    textView.setText(locationResult[1].getShop_name());
                }
                textView.setTypeface(font);
            }

            if (locationResult.length >= 3) {
                imageView = (ImageView) getActivity().findViewById(R.id.around_3);
                textView = (TextView) getActivity().findViewById(R.id.around_3_text);
                //new DownloadImageTask(imageView).execute("http://t3.gstatic.com/images?q=tbn:ANd9GcRRq4kyBQRB-TiUZAW3oSBJ5Z6hRluE8qkGogx8VhrSEgUDB64tjXfnxHg");
                if (locationResult[0].getLogo_image_url() != null) {
                    new DownloadImageTask(imageView).execute(locationResult[2].getLogo_image_url());
                }
                //textView.setText(R.string.dynamic_around_3);
                if (locationResult[0].getShop_name() != null) {
                    textView.setText(locationResult[2].getShop_name());
                }
                textView.setTypeface(font);
            }

            if (locationResult.length >= 4) {
                imageView = (ImageView) getActivity().findViewById(R.id.around_4);
                textView = (TextView) getActivity().findViewById(R.id.around_4_text);
                //new DownloadImageTask(imageView).execute("https://i.ytimg.com/vi/Fw8agSotU-M/maxresdefault.jpg");
                if (locationResult[0].getLogo_image_url() != null) {
                    new DownloadImageTask(imageView).execute(locationResult[3].getLogo_image_url());
                }
                //textView.setText(R.string.dynamic_around_4);
                if (locationResult[0].getShop_name() != null) {
                    textView.setText(locationResult[3].getShop_name());
                }
                textView.setTypeface(font);
            }

            if (locationResult.length >= 5) {
                imageView = (ImageView) getActivity().findViewById(R.id.around_5);
                textView = (TextView) getActivity().findViewById(R.id.around_5_text);
                //new DownloadImageTask(imageView).execute("https://i.ytimg.com/vi/VX3RXiEUuWw/hqdefault.jpg");
                if (locationResult[0].getLogo_image_url() != null) {
                    new DownloadImageTask(imageView).execute(locationResult[4].getLogo_image_url());
                }
                //textView.setText(R.string.dynamic_around_5);
                if (locationResult[0].getShop_name() != null) {
                    textView.setText(locationResult[4].getShop_name());
                }
                textView.setTypeface(font);
            }
        }*/
        NearbyProfessionals nearbyProfessionals = new NearbyProfessionals((int)mLastLocation.getLatitude(), (int)mLastLocation.getLongitude());
        String[] shopImageList = nearbyProfessionals.getShopImageList();
        String[] shopNameList = nearbyProfessionals.getShopNameList();
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
        RelativeLayout gouiranLinkSelectionProposals = (RelativeLayout)getActivity().findViewById(R.id.gouiran_link_selection_proposals);

        TextView textView = (TextView)getActivity().findViewById(R.id.gouiran_link_selection);
        textView.setTypeface(font);

        TextView textView1 = new TextView(getActivity());
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView1.setPadding(5, 0, 5, 0);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView1.setTextColor(getResources().getColor(R.color.black));
        textView1.setText(R.string.dynamic_selection_1);
        textView1.setLayoutParams(params1);
        textView1.setId(1);
        textView1.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView1);

        TextView textView2 = new TextView(getActivity());
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView2.setPadding(5, 0, 5, 0);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView2.setTextColor(getResources().getColor(R.color.black));
        textView2.setText(R.string.dynamic_selection_2);
        params2.addRule(RelativeLayout.END_OF, 1);
        textView2.setLayoutParams(params2);
        textView2.setId(2);
        textView2.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView2);

        TextView textView3 = new TextView(getActivity());
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView3.setPadding(5, 0, 5, 0);
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView3.setTextColor(getResources().getColor(R.color.black));
        textView3.setText(R.string.dynamic_selection_3);
        params3.addRule(RelativeLayout.END_OF, 1);
        params3.addRule(RelativeLayout.BELOW, 2);
        textView3.setLayoutParams(params3);
        textView3.setId(3);
        textView3.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView3);

        TextView textView4 = new TextView(getActivity());
        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView4.setPadding(5, 0, 5, 0);
        textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView4.setTextColor(getResources().getColor(R.color.black));
        textView4.setText(R.string.dynamic_selection_4);
        params4.addRule(RelativeLayout.END_OF, 2);
        textView4.setLayoutParams(params4);
        textView4.setId(4);
        textView4.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView4);

        TextView textView5 = new TextView(getActivity());
        RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView5.setPadding(5, 0, 5, 0);
        textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView5.setTextColor(getResources().getColor(R.color.black));
        textView5.setText(R.string.dynamic_selection_5);
        params5.addRule(RelativeLayout.END_OF, 4);
        textView5.setLayoutParams(params5);
        textView5.setId(5);
        textView5.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView5);

        TextView textView6 = new TextView(getActivity());
        RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView6.setPadding(5, 0, 5, 0);
        textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView6.setTextColor(getResources().getColor(R.color.black));
        textView6.setText(R.string.dynamic_selection_6);
        params6.addRule(RelativeLayout.END_OF, 4);
        params6.addRule(RelativeLayout.BELOW, 5);
        textView6.setLayoutParams(params6);
        textView6.setId(6);
        textView6.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView6);

        TextView textView7 = new TextView(getActivity());
        RelativeLayout.LayoutParams params7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView7.setPadding(5, 0, 5, 0);
        textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView7.setTextColor(getResources().getColor(R.color.black));
        textView7.setText(R.string.dynamic_selection_7);
        params7.addRule(RelativeLayout.END_OF, 5);
        textView7.setLayoutParams(params7);
        textView7.setId(7);
        textView7.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView7);

        TextView textView8 = new TextView(getActivity());
        RelativeLayout.LayoutParams params8 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView8.setPadding(5, 0, 5, 0);
        textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView8.setTextColor(getResources().getColor(R.color.black));
        textView8.setText(R.string.dynamic_selection_8);
        params8.addRule(RelativeLayout.END_OF, 7);
        textView8.setLayoutParams(params8);
        textView8.setId(8);
        textView8.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView8);

        TextView textView9 = new TextView(getActivity());
        RelativeLayout.LayoutParams params9 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView9.setPadding(5, 0, 5, 0);
        textView9.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView9.setTextColor(getResources().getColor(R.color.black));
        textView9.setText(R.string.dynamic_selection_9);
        params9.addRule(RelativeLayout.END_OF, 7);
        params9.addRule(RelativeLayout.BELOW, 8);
        textView9.setLayoutParams(params9);
        textView9.setId(9);
        textView9.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView9);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return (root);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Resources res = getResources();
        getActivity().getAssets();
        TextView textView;
        final Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Acrom W00 Medium.ttf");

        /* Editing username */
        //username = "XXXXXXXXXX";
        if (connected)
            text = String.format(res.getString(R.string.welcome_user), username);
        else
            text = "Bonjour,";
        welcomeUser = new TextView(getActivity());
        welcomeUser = (TextView)getActivity().findViewById(R.id.welcome_user);
        welcomeUser.setText(text);
        welcomeUser.setTypeface(font);
        welcomeUser.setTextColor(res.getColor(R.color.GouiranDarkBlue));

        /* Invitez des amis */
        textView = (TextView)getActivity().findViewById(R.id.invite_your_friends);
        textView.setTypeface(font);



        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Invitation Gouiran Link");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, je viens de découvrir l'application mobile Gouiran Link, elle est géniale!\nIl faudrait que tu l'essaye toi aussi!");

        Button button = (Button) view.findViewById(R.id.invite_your_friends_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
    }
}
