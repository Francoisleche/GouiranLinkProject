package com.example.franois.gouiranlinkproject.Account;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.InsciptionConnexion.LoginActivity;
import com.example.franois.gouiranlinkproject.Object.Customer;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.ToolsClasses.App;
import com.example.franois.gouiranlinkproject.ToolsClasses.MyCustomer;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;

import static com.google.android.gms.wearable.DataMap.TAG;

public class NestedSettingsFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private Customer customer;
    private Context mContext;

    private OnFragmentInteractionListener mListener;

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
        textViewmodel.setText("Model "+ model);
        textViewnom.setText("Nom du telephone : "+ nom);
        textViewmanufacturer.setText("Constructeur "+ manufacturer);




        if (customer != null && !customer.ismGouiranLink() && !customer.ismFacebook() && !customer.ismGoogle()) {
            logoutButton.setVisibility(View.GONE);
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        if (customer != null && customer.ismFacebook())
            logoutButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.com_facebook_blue));
        else if (customer != null && customer.ismGoogle())
            logoutButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.GoogleRed));
        else if (customer != null && customer.ismGouiranLink())
            logoutButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.GouiranLightPink));
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
}
