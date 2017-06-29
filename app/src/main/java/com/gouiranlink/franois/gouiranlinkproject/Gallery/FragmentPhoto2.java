package com.gouiranlink.franois.gouiranlinkproject.Gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gouiranlink.franois.gouiranlinkproject.R;

/**
 * Created by François on 25/06/2017.
 */

public class FragmentPhoto2 extends Fragment {

    String image_affiche;

    public FragmentPhoto2() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            image_affiche = (String) getArguments().getString("Pathimage");
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_photo2, container, false);

        ImageView image_gallery = (ImageView) view.findViewById(R.id.image_gallery);
        Glide.with(getContext()).load("file://" + image_affiche)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(image_gallery);





        return view;
    }



}
