package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static com.example.franois.gouiranlinkproject.BaseFragment.ARGS_INSTANCE;

public class AccountFragment extends Fragment implements View.OnClickListener{
    public Button button_settings;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        return (root);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case (R.id.settings_button):
                Log.d("SETTINGS", "SETTINGS3");
                getActivity().setContentView(R.layout.fragment_settings);
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

}
