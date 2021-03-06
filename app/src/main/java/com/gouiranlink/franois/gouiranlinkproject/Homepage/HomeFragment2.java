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
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.Recherche.Research2Fragment;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;
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

    private String[] articleIdList = new String[10];
    private String[] articleTextList = new String[10];
    private String[] articlePhotoList = new String[10];

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
        } else{
            //Toast.makeText(getActivity(), "Not connected...", Toast.LENGTH_SHORT).show();
        }



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




        final LinearLayout myRoot = (LinearLayout) view.findViewById(R.id.articles);
        final List<Button> list = new ArrayList<Button>();

        json_parser_article(article_api());

        for (int i = 0; i < articleIdList.length; i++) {
            System.out.println("COMBIEN DE FOIS : " + i);
            final View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.services_layout_article, null);
            Button button1 = (Button) view2.findViewById(R.id.lire_plus);
            view2.setId(i);
            myRoot.setId(i);
            TextView text_article = (TextView) view2.findViewById(R.id.text_article);
            final ImageView image_article = (ImageView) view2.findViewById(R.id.image_article);


            if (articlePhotoList[i] == "") {
                image_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
            } else {
                Picasso.with(view2.getContext()).load(articlePhotoList[i])
                        .into(image_article);
            }

            text_article.setText(articleTextList[i]);
            button1.setId(i);
            list.add(button1);
            myRoot.addView(view2);
        }


        for (final Button button11111 : list) {
            button11111.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    Fragment fragment = null;
                    FragmentTransaction ft = null;
                    Bundle args = new Bundle();
                    FragmentManager fm = getFragmentManager();
                    //FragmentTransaction ft = fm.beginTransaction();
                    ft = fm.beginTransaction();
                    //getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);
                    args.putSerializable("numero", button11111.getId());
                    fragment = new ArticleFragment();
                    fragment.setArguments(args);
                    ft.replace(R.id.content_frame, fragment).addToBackStack("article");
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();


                }
            });
        }






        //Bouton retour
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                }
                return true;
            }
        });

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
        if (context instanceof HomeFragment2.OnFragmentInteractionListener) {
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
        //Toast.makeText(getActivity(), "connect", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Toast.makeText(getActivity(), "connection suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Toast.makeText(getActivity(), "Failed to connect", Toast.LENGTH_SHORT).show();
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


    public String article_api(){
        GetRequest getRequest = new GetRequest("http://gouiran.link.free.fr/getallphotos.php");
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Reeeeeeeeeeeeeeeeeeeeeeesp");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }




    public void json_parser_article(String str){

        if (str != null) {
            try {

                JSONObject jsonObj = new JSONObject(str);
                JSONArray nb_article = jsonObj.getJSONArray("data");
                articleIdList = new String[nb_article.length()];
                articleTextList = new String[nb_article.length()];
                articlePhotoList = new String[nb_article.length()];

                for (int i = 0; i < nb_article.length(); i++) {

                    JSONObject c = nb_article.getJSONObject(i);

                    articleIdList[i]=c.getString("id");
                    articleTextList[i]=c.getString("text");
                    articlePhotoList[i]=c.getString("photo");
                }

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }


    }



}
