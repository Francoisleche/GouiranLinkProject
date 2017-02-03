package com.example.franois.gouiranlinkproject.Rendezvous;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.franois.gouiranlinkproject.CamTestActivity;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.ReservationFragment;

/**
 * Created by François on 03/02/2017.
 */

public class Recapitulatif extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recapitulatif);



        Button recapitulatif = (Button) findViewById(R.id.valider_reservation);
        recapitulatif.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Toast.makeText(this,"Yes",Toast.LENGTH_SHORT).show();
                CamTestActivity cam = new CamTestActivity();
                Intent intent = new Intent(getApplicationContext(), ReservationFragment.class);
                startActivity(intent);
            }
        });
    }



}
