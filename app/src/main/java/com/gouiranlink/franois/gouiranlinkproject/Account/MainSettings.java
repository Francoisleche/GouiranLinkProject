package com.gouiranlink.franois.gouiranlinkproject.Account;

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
import android.widget.TextView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;

/*
Fragment which shows the settings' page
 */

public class MainSettings extends Fragment {
    private Customer customer;

    FragmentManager fragmentManager;
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
            customer = (Customer)getArguments().getSerializable("Customer");
        }
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_settings, container, false);

        TextView textView = (TextView) root.findViewById(R.id.identifiantcustomer);
        String name = "";
        if (customer.getName().equals("null"))
            name += getString(R.string.not_known);
        else
            name += customer.getName();
        name += " ";
        if (customer.getSurname().equals("null"))
            name += getString(R.string.not_known);
        else
            name += customer.getSurname();
        textView.setText(name);




        Button inviteFriends = (Button)root.findViewById(R.id.invite_friends);
        inviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "TEXTE", Toast.LENGTH_LONG).show();

                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.gouiran_link_invitation));
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.gouiran_link_invite_mail));
                getContext().startActivity(Intent.createChooser(emailIntent, getString(R.string.send_mail)));
            }
        });

        Button settingsButton = (Button)root.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NestedSettingsFragment();
                Bundle args = new Bundle();
                args.putSerializable("Customer", customer);
                fragment.setArguments(args);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment).addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().findViewById(R.id.frame_layout).setVisibility(View.GONE);
            }
        });

        Button modifyAccountButton = (Button)root.findViewById(R.id.edit_profile);
        modifyAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment fragment = new EditAccountFragment();
                Bundle args = new Bundle();
                args.putSerializable("Customer", customer);
                fragment.setArguments(args);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment).addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().findViewById(R.id.frame_layout).setVisibility(View.GONE);
            }
        });

        Button aboutButton = (Button)root.findViewById(R.id.about);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment fragment = new AboutFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment).addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().findViewById(R.id.frame_layout).setVisibility(View.GONE);
            }
        });

        Button tellUsButton = (Button)root.findViewById(R.id.tell_us);
        tellUsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment fragment = new TellUsFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment).addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().findViewById(R.id.frame_layout).setVisibility(View.GONE);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
