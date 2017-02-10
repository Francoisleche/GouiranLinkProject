package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.example.franois.gouiranlinkproject.BaseFragment.ARGS_INSTANCE;


public class AccountFragment extends Fragment implements View.OnClickListener{
    public Button button_settings;
    private MyCustomer myCustomer;


    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        AccountFragment firstFragment = new AccountFragment();
        firstFragment.setArguments(args);
        return firstFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (getArguments() != null) {
            myCustomer = (MyCustomer)getArguments().getSerializable("MyCustomer");
        }

        Fragment fragment = new MainSettings();
        Bundle args = new Bundle();
        args.putSerializable("MyCustomer", myCustomer);
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.content_frame, fragment).addToBackStack("tag").commit();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        //ImageView editProfile = (ImageView) root.findViewById(R.id.edit_profile);
        //editProfile.setOnClickListener(new View.OnClickListener() {
        // TODO L'UN OU L'AUTRE A TESTER
        /*Button settingsButton = (Button)root.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //getActivity().setContentView(R.layout.fragment_settings);
                fragmentTransaction.replace(R.id.frameLayout, new NestedSettingsFragment()).addToBackStack("tag").commit();
            }
        });*/

        /*Button inviteFriends = (Button)root.findViewById(R.id.invite_friends);
        inviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Invitation Gouiran Link");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, je viens de découvrir l'application mobile Gouiran Link, elle est géniale!\nIl faudrait que tu l'essaye toi aussi!");
                getContext().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });*/

        return (root);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case (R.id.settings_button):
                break;
            case (R.id.edit_profile):
                break;
            case (R.id.about):
                break;
            case (R.id.tell_us):
                break;
            case (R.id.invite_friends):
                break;
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    /*@Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case 1:
                return HomeFragment.newInstance(0);
            case 2:
                return ResearchFragment.newInstance(0);
            case 3:
                return ReservationFragment.newInstance(0);
            case 4:
                return FavouritesFragment.newInstance(0);
            case 5:
                return GalleryFragment.newInstance(0);
            case 6:
                if(compte == true){
                    Toast.makeText(this, "ça marche", Toast.LENGTH_SHORT).show();
                    return AccountFragment.newInstance(0);
                }
        }
        throw new IllegalStateException("Need to send an index that we know");
    }*/

    @Override
    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity()
                .setTitle(R.string.myAccount);
    }
}
