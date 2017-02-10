package com.example.franois.gouiranlinkproject.Professional_View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.CamTestActivity;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.Rendezvous.PrendreRdv;

import java.util.List;

/**
 * Created by François on 01/02/2017.
 */

public class ServicesProfessional extends Fragment {

    private List<String> resultList;
    public Button button ;

    public ServicesProfessional(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_services_professional, container, false);

        View v = inflater.inflate(R.layout.fragment_services_professional, container, false);


        ListView listView = (ListView) v.findViewById(R.id.maliste);

        ArrayAdapter<String> tableau = new ArrayAdapter<String>(
                listView.getContext(),R.layout.services);

        for(int i = 0 ; i<40;i++){
            tableau.add("Prestation n°"+i);

        }
        listView.setAdapter(tableau);

/*
        button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });*/

        button = (Button) v.findViewById(R.id.prendre_rdv);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Toast.makeText(getActivity(),
                        "Yes",
                        Toast.LENGTH_SHORT).show();
                CamTestActivity cam = new CamTestActivity();
                Intent intent = new Intent(getActivity(),PrendreRdv.class);
                getActivity().startActivity(intent);
                //cam.open();

                //getCameraInstance();
                //GalleryFragment.newInstance(0);
            }
        });


        return v;
    }





}
