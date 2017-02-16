package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;


public class NestedSettingsFragment extends Fragment {
    private MyCustomer myCustomer;

    /*final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this).addApi(Plus.API)
            .addScope(Plus.SCOPE_PLUS_LOGIN).build();*/


    private OnFragmentInteractionListener mListener;

    public NestedSettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NestedSettingsFragment newInstance(String param1, String param2) {
        NestedSettingsFragment fragment = new NestedSettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myCustomer = (MyCustomer)getArguments().getSerializable("MyCustomer");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_nested_settings, container, false);

        Button logoutButton = (Button)root.findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myCustomer != null && myCustomer.ismFacebook()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                    LoginManager.getInstance().logOut();
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(login);
                    getActivity().finish();
                }
                else if (myCustomer != null && myCustomer.ismGoogle()) {
                    LoginActivity.signOut();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Not Logged Out", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return (root);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
