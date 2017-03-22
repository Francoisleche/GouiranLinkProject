package com.example.franois.gouiranlinkproject.Rendezvous;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.franois.gouiranlinkproject.CamTestActivity;
import com.example.franois.gouiranlinkproject.Object.Professional;
import com.example.franois.gouiranlinkproject.Object.Professional_Product;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.Reservation.ReservationFragment;

/**
 * Created by François on 03/02/2017.
 */

public class Recapitulatif extends Fragment {

    private Professional professional;
    private Professional_Product[] liste_prestations_selectionne;
    private int position_list_clique;
    private String[] recap;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            professional = (Professional)getArguments().getSerializable("Professionnal");
            recap = (String[])getArguments().getSerializable("recap");
            position_list_clique = (int)getArguments().getInt("position_list_clique");
            liste_prestations_selectionne = (Professional_Product[])getArguments().getSerializable("liste_prestations_selectionne");
        }
        System.out.println("Maaaaaaaaaaaaaaaarche bien :"+professional.toString());
        //

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_recapitulatif, container, false);

        TableLayout table = (TableLayout) v.findViewById(R.id.idTable);
        TableRow row;

        TextView tv1,tv2;

        final TextView horairedateprestation = (TextView) v.findViewById(R.id.horairedateprestation);
        final TextView duree = (TextView) v.findViewById(R.id.dureetotal);
        final TextView tarif = (TextView) v.findViewById(R.id.tariftotal);
        final TextView nomprestataire = (TextView) v.findViewById(R.id.nomprestataire);
        final TextView adresseprestataire = (TextView) v.findViewById(R.id.adresseprestataire);

        row = new TableRow(getActivity());
        tv1 = new TextView(getActivity());
        tv2 = new TextView(getActivity());
        tv1.setText("Services");
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setGravity(Gravity.CENTER);
        tv1.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
        tv2.setText("Prix");
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setGravity(Gravity.CENTER);
        tv2.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
        tv1.setBackgroundResource(R.drawable.back);
        tv2.setBackgroundResource(R.drawable.back);
        row.addView(tv1);
        row.addView(tv2);
        table.addView(row);

        for(int i=0;i<liste_prestations_selectionne.length;i++) {
            row = new TableRow(getActivity()); // création d'une nouvelle ligne

            tv1 = new TextView(getActivity()); // création cellule
            tv1.setText(liste_prestations_selectionne[i].getName()); // ajout du texte
            tv1.setGravity(Gravity.CENTER); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            //tv1.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tv1.setLayoutParams( new TableRow.LayoutParams(50,100));

            // idem 2ème cellule
            tv2 = new TextView(getActivity());
            tv2.setText(String.valueOf(liste_prestations_selectionne[i].getPrice())+liste_prestations_selectionne[i].getCurrency());
            tv2.setGravity(Gravity.CENTER);
            //tv2.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tv2.setLayoutParams( new TableRow.LayoutParams(50,100));

            tv1.setBackgroundResource(R.drawable.back);
            tv2.setBackgroundResource(R.drawable.back);
            // ajout des cellules à la ligne
            row.addView(tv1);
            row.addView(tv2);

            // ajout de la ligne au tableau
            table.addView(row);
        }


        System.out.println("RECAPITULATIF : "+recap[0]+" "+recap[1]);

        tarif.setText(recap[0]);
        duree.setText(recap[1]);
        nomprestataire.setText(recap[2]);
        adresseprestataire.setText(recap[3]);
        horairedateprestation.setText(recap[4]);

        Button recapitulatif = (Button) v.findViewById(R.id.valider_reservation);
        recapitulatif.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Toast.makeText(this,"Yes",Toast.LENGTH_SHORT).show();
                CamTestActivity cam = new CamTestActivity();
                Intent intent = new Intent(getContext(), ReservationFragment.class);
                startActivity(intent);
            }
        });


        return v;
    }



}
