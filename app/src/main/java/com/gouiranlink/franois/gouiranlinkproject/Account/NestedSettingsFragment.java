package com.gouiranlink.franois.gouiranlinkproject.Account;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.gouiranlink.franois.gouiranlinkproject.InsciptionConnexion.LoginActivity;
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.Recherche.Research2Fragment;
import com.gouiranlink.franois.gouiranlinkproject.ShakeListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

import static com.google.android.gms.wearable.DataMap.TAG;

public class NestedSettingsFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private Customer customer;
    private Context mContext;

    private Switch mySwitch,mySwitch2;
    private ShakeListener mShaker;

    private LinkedList<String> data = new LinkedList<String>();
    private String FILENAME = "memo";

    private OnFragmentInteractionListener mListener;

    private GoogleApiClient mGoogleApiClient = null;

    public NestedSettingsFragment() {
        // Required empty public constructor
    }

    public static NestedSettingsFragment newInstance() {
        NestedSettingsFragment fragment = new NestedSettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = (Customer) getArguments().getSerializable("Customer");
        }
        mContext = getContext();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nested_settings, container, false);


        mySwitch = (Switch) root.findViewById(R.id.secouer_recherche);
        mySwitch2 = (Switch) root.findViewById(R.id.notifications);



        //LIRE FICHIER CONFIGURATION
        //lectureFichier();



        //Si la fonction secouer est checked à oui alors on peut utiliser la fonction secouer pour rechercher

            System.out.println("secoueeeeer");
            Toast.makeText(getActivity(), "Shake " , Toast.LENGTH_LONG).show();
            mShaker = new ShakeListener(getActivity());
            mShaker.setOnShakeListener(new ShakeListener.OnShakeListener (){
                public void onShake()
                {
                    if(mySwitch.isChecked()) {
                        Toast.makeText(getActivity(), "Shake ", Toast.LENGTH_LONG).show();
                        Fragment fragment = null;
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        //getActivity().findViewById(R.id.fragment_services_professional).setVisibility(View.GONE);
                        fragment = new Research2Fragment();
                        //ft.replace(R.id.frameLayout, fragment);
                        ft.replace(R.id.content_frame, fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.commit();
                    }
                }
            });


        //set the switch to ON
        //mySwitch.setChecked(true);
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    System.out.println("Switch is currently ON");
                }else{
                    System.out.println("Switch is currently OFF");
                }

                //ajouter("hello");



            }
        });



        //set the switch to ON
        //mySwitch.setChecked(true);
        //attach a listener to check for changes in state
        mySwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    System.out.println("Switch is currently ON");
                }else{
                    System.out.println("Switch is currently OFF");
                }

            }
        });




        //check the current state before we display the screen
        if(mySwitch.isChecked()){
            System.out.println("Switch is currently ON1");
        }
        else {
            System.out.println("Switch is currently OFF1");
        }


        Button logoutButton = (Button) root.findViewById(R.id.logout);

        TextView textViewversion = (TextView) root.findViewById(R.id.versionsmartphone);
        TextView textViewmodel = (TextView) root.findViewById(R.id.modelsmartphone);
        TextView textViewnom = (TextView) root.findViewById(R.id.nomsmartphone);
        TextView textViewmanufacturer = (TextView) root.findViewById(R.id.manufacturersmartphone);


        //Information du smartphone
        System.out.println(Build.HARDWARE);
        System.out.println(Build.VERSION.CODENAME);
        System.out.println(Build.VERSION.BASE_OS);
        System.out.println(Build.VERSION.INCREMENTAL);
        System.out.println(Build.VERSION.SDK_INT);
        System.out.println(Build.VERSION.SDK);

        System.out.println(Build.HOST);
        System.out.println(Build.ID);
        System.out.println(Build.DEVICE);
        System.out.println(Build.DISPLAY);
        System.out.println(Build.MANUFACTURER);
        System.out.println(Build.MODEL);
        System.out.println(Build.PRODUCT);
        System.out.println(Build.SERIAL);

        //String s = Build.HARDWARE;
        String sdk = Build.VERSION.SDK;
        String model = Build.MODEL;
        String nom = Build.HOST;
        String manufacturer = Build.MANUFACTURER;

        textViewversion.setText("Version "+ sdk);
        textViewmodel.setText("Model : "+ model);
        textViewnom.setText("Nom du telephone : "+ nom);
        textViewmanufacturer.setText("Constructeur : "+ manufacturer);




        if (customer != null && !customer.ismGouiranLink() && !customer.ismFacebook() && !customer.ismGoogle()) {
            logoutButton.setVisibility(View.GONE);
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        if (customer != null && customer.ismFacebook())
            logoutButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.com_facebook_blue));
        else if (customer != null && customer.ismGoogle())
            logoutButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.GoogleRed));
        else if (customer != null && customer.ismGouiranLink())
            logoutButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.GouiranDarkPink));
            logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customer != null && customer.ismFacebook()) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.logged_out_facebook, Toast.LENGTH_SHORT).show();
                    LoginManager.getInstance().logOut();
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(login);
                    getActivity().finish();
                } else if (customer != null && customer.ismGoogle()) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.logged_out_google, Toast.LENGTH_SHORT).show();
                    LoginActivity.signOut(mGoogleApiClient);
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else if (customer != null && customer.ismGouiranLink()) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.logged_out_gouiran_link, Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(login);
                    getActivity().finish();
                }
            }
        });
        return (root);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onPause() {
        super.onPause();

        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }/* else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }










    //CONFIGURATION
    public void lire_fichier_config(){

        StringBuilder builder = new StringBuilder();
        AssetManager assetManager= getActivity().getAssets();
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

        String[] separated_position = builder.toString().split(";");

        ArrayList<String[]> separated_position3 = null;
        for(int i=0;i<separated_position.length;i++){
            String[] separated_position2 = separated_position[i].split(":");
            separated_position3.add(separated_position2);
        }

        for(int i =0;i<separated_position3.size();i++){
            System.out.println("OOOoh : " + separated_position3.get(i)[0] + " - " + separated_position3.get(i)[1]);
            if(separated_position3.get(i)[0].equals("notifications")){
                if (separated_position3.get(i)[1].equals("false")) {
                    mySwitch2.setChecked(false);
                }else{
                    mySwitch2.setChecked(true);
                }
            }
            if(separated_position3.get(i)[0].equals("secouer")){
                if (separated_position3.get(i)[1].equals("false")) {
                    mySwitch.setChecked(false);
                }else{
                    mySwitch.setChecked(true);
                }
            }
        }








        //FichierConfig fichierconfig = new FichierConfig();

        // FIN CONFIGURATION
    }


    public void lectureFichier(){
        try
        {
            String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            File f = new File(baseDir + File.separator + "MyAPK/TextFiles/configuration.txt");
            FileInputStream fiStream = new FileInputStream(f);
            InputStreamReader inputreader = new InputStreamReader(fiStream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            /*StringBuilder out = new StringBuilder();
            String line;
            String tmp;
            List<String> champs = new ArrayList<String>();
            while (( line = buffreader.readLine()) != null)
            {
                out = new StringBuilder();
                out.append(line);
                tmp = out.toString();
                champs = Arrays.asList(tmp.split("~"));
                System.out.println(champs.get(0), champs.get(1));
                //liste.add(new Produit(champs.get(0), champs.get(1), champs.get(2), champs.get(3), champs.get(4), champs.get(5), champs.get(6)));
            }*/
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(inputreader);
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

            String[] separated_position = builder.toString().split(";");

            ArrayList<String[]> separated_position3 = null;
            for(int i=0;i<separated_position.length;i++){
                String[] separated_position2 = separated_position[i].split(":");
                separated_position3.add(separated_position2);
            }

            for(int i =0;i<separated_position3.size();i++){
                System.out.println("OOOoh : " + separated_position3.get(i)[0] + " - " + separated_position3.get(i)[1]);
                if(separated_position3.get(i)[0].equals("notifications")){
                    if (separated_position3.get(i)[1].equals("false")) {
                        mySwitch2.setChecked(false);
                    }else{
                        mySwitch2.setChecked(true);
                    }
                }
                if(separated_position3.get(i)[0].equals("secouer")){
                    if (separated_position3.get(i)[1].equals("false")) {
                        mySwitch.setChecked(false);
                    }else{
                        mySwitch.setChecked(true);
                    }
                }
            }

            fiStream.close();
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), e.getMessage() , Toast.LENGTH_LONG).show();
        }
    }


    public void ajouter(String s){
        try
        {
            FileWriter writer = null;
            String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            String texte = s + System.getProperty("line.separator");

            writer = new FileWriter(baseDir + File.separator + "MyAPK/TextFiles/configuration.txt", true);
            writer.write(texte, 0, texte.length());
            writer.close();
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    /*public void readData() {
        try {
            InputStream fis = openFileInput(FILENAME);
            BufferedReader r = new BufferedReader(new InputStreamReader(fis));

            String line;
            while ((line = r.readLine()) != null) {
                data.add(line+"\n");
            }

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        FileOutputStream fos;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (String str : data) {
                fos.write(str.getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


}
