package com.example.franois.gouiranlinkproject.Rendezvous;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.franois.gouiranlinkproject.CamTestActivity;
import com.example.franois.gouiranlinkproject.R;

/**
 * Created by François on 02/02/2017.
 */

public class PrendreRdv extends Activity{

    static final String[] employe = new String[] {"Jacky 1","Jacky 2","Jacky 3","Jacky 4","Jacky 5","Jacky 6"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prendre_rdv);

        Spinner spinner = (Spinner)findViewById(R.id.liste_specialite);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,employe);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        Button autresprestations = (Button) findViewById(R.id.autres_prestations);
        autresprestations.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Toast.makeText(this,"Yes",Toast.LENGTH_SHORT).show();
                CamTestActivity cam = new CamTestActivity();
                Intent intent = new Intent(getApplicationContext(), AutresPrestations.class);
                startActivity(intent);
            }
        });

        Button reserver = (Button) findViewById(R.id.reserver);
        reserver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Toast.makeText(this,"Yes",Toast.LENGTH_SHORT).show();
                CamTestActivity cam = new CamTestActivity();
                Intent intent = new Intent(getApplicationContext(), Recapitulatif.class);
                startActivity(intent);
            }
        });
    }


}
