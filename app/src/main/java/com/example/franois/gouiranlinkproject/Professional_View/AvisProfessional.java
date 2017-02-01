package com.example.franois.gouiranlinkproject.Professional_View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franois.gouiranlinkproject.R;

/**
 * Created by François on 01/02/2017.
 */

public class AvisProfessional extends Fragment{

    public AvisProfessional(){
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avis_professional, container, false);
    }


}
