package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static com.example.franois.gouiranlinkproject.BaseFragment.ARGS_INSTANCE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment implements View.OnClickListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public Button button_settings;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        button_settings = (Button) inflater.inflate(R.layout.fragment_account, container, false).findViewById(R.id.settings_button);
        button_settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Toast.makeText(getActivity(),
                        "Yes",
                        Toast.LENGTH_SHORT).show();
                //GalleryFragment.newInstance(0);
            }
        });

        //Button imageView = (Button) view
        View view = inflater.inflate(R.layout.fragment_account, container, false).findViewById(R.id.settings_button);
        return view;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(),
                "Yes",
                Toast.LENGTH_SHORT).show();
        //GalleryFragment.newInstance(0);
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
