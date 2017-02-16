package com.example.franois.gouiranlinkproject.Professional_View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

<<<<<<< HEAD
=======
import com.example.franois.gouiranlinkproject.CamTestActivity;
>>>>>>> Maxime
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.Rendezvous.PrendreRdv;

import java.util.List;

/**
 * Created by François on 01/02/2017.
 */

public class ServicesProfessional extends Fragment {

    public final static String AGE = "sdz.chapitreTrois.intent.example.AGE";
    private List<String> resultList;
    public Button button ;

    public ServicesProfessional(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
/*
    public final static String AGE = "sdz.chapitreTrois.intent.example.AGE";
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

        View v = inflater.inflate(R.layout.fragment_services_professional, container, false);


        ListView listView = (ListView) v.findViewById(R.id.maliste);

        ArrayAdapter<String> tableau = new ArrayAdapter<String>(
                listView.getContext(),R.layout.services);

        for(int i = 0 ; i<40;i++){
            tableau.add("Prestation n°"+i);

        }
        listView.setAdapter(tableau);

        final int taille = 40;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                for(int i=0;i<= taille ;i++){
                    Intent intent = new Intent(getActivity(),PrendreRdv.class);
                    intent.putExtra(AGE,position);
                    startActivity(intent);
                }
            }
        });


        button = (Button) v.findViewById(R.id.prendre_rdv);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                        "Yes",
                        Toast.LENGTH_SHORT).show();
                CamTestActivity cam = new CamTestActivity();
                Intent intent = new Intent(getActivity(),PrendreRdv.class);
                getActivity().startActivity(intent);

            }
        });


        return v;
    }*/





    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_services_professional, container, false);

        ListView lstview=(ListView) v.findViewById(R.id.maliste);
        /*lstview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(context, "An item of the ListView is clicked.", Toast.LENGTH_LONG).show();
            }
        });*/



        /*
        ArrayAdapter<String> tableau = new ArrayAdapter<String>(
                lstview.getContext(),R.layout.services2);

        for(int i = 0 ; i<40;i++){
            tableau.add("Prestation n°"+i);

        }
        lstview.setAdapter(tableau);*/



        String[] items = new String[40];
        for(int i = 0 ; i<40;i++){
            items[i]=("Prestation n°"+i);

        }

        int layout = R.layout.services2;
        int id = R.id.txt;
        int id2 = R.id.bt;

        ServicesAdapter adapter=new ServicesAdapter(getActivity(),layout,id,items);
        // Bind data to the ListView
        lstview.setAdapter(adapter);

        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println("ON A CLIQUEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                Toast.makeText(getContext(), "An item of the ListView is clicked.", Toast.LENGTH_LONG).show();
                //for(int i=0;i<= 40 ;i++){
                    Intent intent = new Intent(getActivity(),PrendreRdv.class);
                    intent.putExtra(AGE,position);
                    startActivity(intent);
                //}
            }
        });

    return v;
    }
/*
    public void clickMe(View view){
        Button bt=(Button)view;
        Toast.makeText(getActivity(), "Button "+bt.getText().toString(),Toast.LENGTH_LONG).show();
    }*/

    public void editActions(View v) {
        Toast.makeText(getActivity(), "Layout", Toast.LENGTH_SHORT).show();
    }

    public void clickMe(View v) {
        Toast.makeText(getActivity(), "clickMe", Toast.LENGTH_SHORT).show();
    }









}
