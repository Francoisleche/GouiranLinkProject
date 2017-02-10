package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;


public class MainSettings extends Fragment {
    private MyCustomer myCustomer;
    private GoogleApiClient mGoogleApiClient;

    private FragmentTransaction fragmentTransaction;

    private OnFragmentInteractionListener mListener;

    public MainSettings() {
        // Required empty public constructor
    }

    public static MainSettings newInstance() {
        MainSettings fragment = new MainSettings();
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
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main_settings, container, false);

        Button inviteFriends = (Button)root.findViewById(R.id.invite_friends);
        inviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Invitation Gouiran Link");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, je viens de découvrir l'application mobile Gouiran Link, elle est géniale!\nIl faudrait que tu l'essaye toi aussi!");
                getContext().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });

        Button settingsButton = (Button)root.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //getActivity().setContentView(R.layout.fragment_settings);
                View root = inflater.inflate(R.layout.fragment_account, container, false);
                Fragment fragment = new NestedSettingsFragment();
                Bundle args = new Bundle();
                args.putSerializable("MyCustomer", myCustomer);
                fragment.setArguments(args);
                fragmentTransaction.replace(R.id.frameLayout, fragment).addToBackStack("tag").commit();
                getActivity().findViewById(R.id.frame_layout).setVisibility(View.GONE);
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
        } /*else {
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
