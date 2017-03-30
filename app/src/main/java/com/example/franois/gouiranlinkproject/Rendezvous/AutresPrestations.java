package com.example.franois.gouiranlinkproject.Rendezvous;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.franois.gouiranlinkproject.CamTestActivity;
import com.example.franois.gouiranlinkproject.Object.Customer;
import com.example.franois.gouiranlinkproject.Object.Professional;
import com.example.franois.gouiranlinkproject.Object.Professional_Product;
import com.example.franois.gouiranlinkproject.Professional_View.ServicesAdapter;
import com.example.franois.gouiranlinkproject.R;



/**
 * Created by François on 02/02/2017.
 */

public class AutresPrestations extends Fragment {
    private Professional professional;
    private Customer customer;
    private Professional_Product[] liste_prestations_selectionne;
    private Professional_Product[] courante_liste_prestations;
    public ListView lstview;
    public TextView plusdeservices;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            professional = (Professional) getArguments().getSerializable("Professionnal");
            liste_prestations_selectionne = (Professional_Product[]) getArguments().getSerializable("liste_prestations_selectionne");
            courante_liste_prestations = (Professional_Product[]) getArguments().getSerializable("courante_liste_prestations");
            customer = (Customer) getArguments().getSerializable("Customer");

        }
        System.out.println("Maaaaaaaaaaaaaaaarche bien :" + professional.toString());
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_autres_prestations, container, false);

        plusdeservices = (TextView) v.findViewById(R.id.plusdeservices);

        lstview = (ListView) v.findViewById(R.id.malisteautresprestations);

        System.out.println("AUTRES PRESTATIONS : " + courante_liste_prestations.length);

        String[] items = new String[courante_liste_prestations.length];
        String[] items1 = new String[courante_liste_prestations.length];
        String[] items2 = new String[courante_liste_prestations.length];

        /*
        if (courante_liste_prestations.length != 0) {
            int j = 0;
            for (int i = 0; i < professional_product.length; i++){
                boolean trouve = false;
                for (int g = 0; g < liste_prestations_selectionne.length;g++){
                    if (professional_product[i] == liste_prestations_selectionne[g]) {
                        trouve =true;
                    }
                }
                if(!trouve){
                    System.out.println("Prestation : " + professional_product[i].getName());


                    items[j] = ("Prestation : " + professional_product[i].getName());


                    items1[j] = ("Prix : " + professional_product[i].getPrice() + professional_product[i].getCurrency());
                    items2[j] = ("Durée : " + professional_product[i].getDuration());
                    j++;
                }

            }
        } else {
            for (int i = 0; i < 40; i++) {
                items[i] = ("Category :" + i);
            }
        }*/

        if (courante_liste_prestations.length != 0) {
            int j = 0;
            for (int i = 0; i < courante_liste_prestations.length; i++){
                System.out.println("AUTRES PRESTATIONS :"+i);
                System.out.println("AUTRES PRESTATIONS :"+courante_liste_prestations[i].getName());
                System.out.println("AUTRES PRESTATIONS :"+courante_liste_prestations.length);

                    items[i] = ("Prestation : " + courante_liste_prestations[i].getName());
                    items1[i] = ("Prix : " + courante_liste_prestations[i].getPrice() + " "+ courante_liste_prestations[i].getCurrency());
                    items2[i] = ("Durée : " + courante_liste_prestations[i].getDuration());



            }
        } else {
            plusdeservices.setText("PLUS DE SERVICE !");
            plusdeservices.setTextSize(50);
            /*for (int i = 0; i < 1; i++) {
                items[i] = ("Plus de service");
            }*/
        }



        int id = R.id.txt;
        int layout = R.layout.services2;


        ServicesAdapter adapter = new ServicesAdapter(getActivity(), layout, id, items, items1, items2);
        // Bind data to the ListView
        lstview.setAdapter(adapter);

        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println("ON A CLIQUEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

                Fragment fragment = null;
                Bundle args = new Bundle();
                args.putSerializable("Professionnal", professional);
                args.putSerializable("Customer",customer);
                args.putSerializable("liste_prestations_selectionne", liste_prestations_selectionne);
                args.putSerializable("courante_liste_prestations", courante_liste_prestations);
                //args.putSerializable("position_list_clique", lstview.getSelectedItem());
                args.putSerializable("position_list_clique", position);
                args.putSerializable("service", "AutresPrestations");
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                //getActivity().findViewById(R.id.fragment_services_professional).setVisibility(View.GONE);

                fragment = new PrendreRdv();
                fragment.setArguments(args);

                ft.replace(R.id.fragment_remplace, fragment);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

        });


        Button selfie = (Button) v.findViewById(R.id.sauvegarder_autres_prestations);
        selfie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Toast.makeText(this,"Yes",Toast.LENGTH_SHORT).show();
                //CamTestActivity cam = new CamTestActivity();
                //Intent intent = new Intent(getContext(), PrendreRdv.class);
                //startActivity(intent);
            }
        });

        return v;
    }


}
