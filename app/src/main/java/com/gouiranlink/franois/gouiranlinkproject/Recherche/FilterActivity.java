package com.gouiranlink.franois.gouiranlinkproject.Recherche;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gouiranlink.franois.gouiranlinkproject.R;

/**
 * Created by François on 30/01/2017.
 */

public class FilterActivity extends Activity {

    static final String[] specialite = new String[] {"Specialite 1","Specialite 2","Specialite 3","Specialite 4","Specialite 5","Specialite 6"};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_filtres);


        Filter_constant o = new Filter_constant();

        Spinner spinner = (Spinner)findViewById(R.id.liste_specialite);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,specialite);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    }




}
