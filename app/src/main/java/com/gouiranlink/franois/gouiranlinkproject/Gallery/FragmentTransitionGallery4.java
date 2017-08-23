package com.gouiranlink.franois.gouiranlinkproject.Gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by François on 03/08/2017.
 */

public class FragmentTransitionGallery4 extends Fragment {

    public FragmentTransitionGallery4() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new ActivityGallery();
        Fragment fragment = null;
        Bundle args = new Bundle();

        //fragment = new ServicesProfessional();
        Intent gallery = new Intent(getActivity(),FragmentGallery4.class);
        startActivity(gallery);

    }



    /*public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_transition_gallery, container, false);



        return view;
    }*/









}
