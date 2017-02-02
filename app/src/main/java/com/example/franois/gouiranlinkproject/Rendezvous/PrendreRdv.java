package com.example.franois.gouiranlinkproject.Rendezvous;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.AndroidCameraApi;
import com.example.franois.gouiranlinkproject.CamTestActivity;
import com.example.franois.gouiranlinkproject.R;

/**
 * Created by François on 02/02/2017.
 */

public class PrendreRdv extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prendre_rdv);


        Button selfie = (Button) findViewById(R.id.autres_prestations);
        selfie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Toast.makeText(this,"Yes",Toast.LENGTH_SHORT).show();
                CamTestActivity cam = new CamTestActivity();
                Intent intent = new Intent(getApplicationContext(), AutresPrestations.class);
                startActivity(intent);
            }
        });
    }


}
