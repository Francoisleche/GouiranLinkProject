package com.gouiranlink.franois.gouiranlinkproject.Rendezvous;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Resource;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by François on 02/02/2017.
 */

public class PrendreRdv extends Fragment {

    static String[] employe = new String[] {"Jacky 1","Jacky 2","Jacky 3","Jacky 4","Jacky 5","Jacky 6"};

    private Professional professional;
    private Professional_Product[] professional_product;
    private Professional_Product[] courante_liste_prestations;
    private Professional_Product[] liste_prestations_selectionne;
    private Customer customer;
    private String service_selectionne;
    private String service_selectionne_expandablelistview;
    private String horaire = "";

    private Resource[] ResourceProfessional;
    private Resource resource;

    private Professional_Product professional_product_selectionne;
    private int position_list_clique;
    private String[] recap = new String[40];


    private boolean check = false;
    private TextView heure_selectionne;

    private GetRequest getRequest;

    private ArrayList<String> horaires_indisponibilites;
    double tariftotal = 0.00;
    int dureetotal = 0;
    int heuretotal = 0 ;
    int minutetotal = 0;


    private CalendarView calendar;
    private int jour_selectionne;
    private int mois_selectionne;
    private int annee_selectionne;

    private String[] horaires_date =null;

    public PrendreRdv(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            professional = (Professional)getArguments().getSerializable("Professionnal");
            professional_product = (Professional_Product[])getArguments().getSerializable("ProfessionnalProduct");
            ResourceProfessional = (Resource[])getArguments().getSerializable("ResourceProfessional");
            service_selectionne = (String)getArguments().getString("service");
            service_selectionne_expandablelistview = (String)getArguments().getString("service_selectionne_expandablelistview");
            position_list_clique = (int)getArguments().getInt("position_list_clique");
            customer = (Customer)getArguments().getSerializable("Customer");


            //
            System.out.println("PRRRRRENDRE RDV : " + service_selectionne_expandablelistview);

            //

            if(service_selectionne.contains("ServicesProfessional")){
                //Recupération de l'élement selectionné et changement de la nouvelle liste des prestations
                liste_prestations_selectionne = new Professional_Product[1];
                //liste_prestations_selectionne[0] = professional_product[position_list_clique];


                //Récupération de l'élément cliqué grace à service_selectionne_expandablelistview (attention au getname et getproduct().getname)
                int iterateur2=0;
                for(int iterateur = 0;iterateur<professional_product.length;iterateur++){
                    if(service_selectionne_expandablelistview.equals(professional_product[iterateur].getProduct().getName())){
                        liste_prestations_selectionne[0] = professional_product[position_list_clique];
                        System.out.println("professional_selectionnnnnnnnne :: " + iterateur +" :::: "+ liste_prestations_selectionne[0].getProduct().getName());
                        iterateur2=iterateur;
                    }
                    System.out.println("professional_product ::::: " +professional_product[iterateur].getName());
                }
                //liste_prestations_selectionne[0] = professional_product[position_list_clique];


                //Initialisation de la liste courante, c'est à dire le reste de la liste de prestations que le client n'a pas encore choisis
                courante_liste_prestations = new Professional_Product[professional_product.length-1];
                int j =0;
                for(int i=0;i<professional_product.length-1;i++){
                    //if(i!=position_list_clique){
                    if(i!=iterateur2){
                        courante_liste_prestations[i] = professional_product[j];
                        System.out.println("PRENDRERDV "+ courante_liste_prestations[i].getName() +" - " + professional_product[j].getName());
                        j++;
                    }else{
                        j++;
                        courante_liste_prestations[i] = professional_product[j];
                        j++;
                    }

                }

                for(int o =0;o<courante_liste_prestations.length;o++){
                    System.out.println("courantelisteprestations :::: " +courante_liste_prestations[o].getName());
                }
                //courante_liste_prestations[courante_liste_prestations.length]=professional_product[j+1];

            }else if(service_selectionne.contains("AutresPrestations")) {
                //Recupération de l'ancienne liste, de l'élément selectionné, et ajout dans la liste des prestations selectionnées
                courante_liste_prestations = (Professional_Product[])getArguments().getSerializable("courante_liste_prestations");
                    liste_prestations_selectionne = (Professional_Product[]) getArguments().getSerializable("liste_prestations_selectionne");
                    System.out.println("COMPRENDPLUS: " + liste_prestations_selectionne.length);
                    int taille = liste_prestations_selectionne.length + 1;
                    Professional_Product[] nouvelle_liste = new Professional_Product[taille];
                    Professional_Product prof = courante_liste_prestations[position_list_clique];
                    for (int i = 0; i < liste_prestations_selectionne.length; i++) {
                        nouvelle_liste[i] = liste_prestations_selectionne[i];
                    }

                    liste_prestations_selectionne = new Professional_Product[taille];
                    liste_prestations_selectionne = nouvelle_liste;
                    System.out.println("COMPRENDPLUS: " + liste_prestations_selectionne.length);
                    liste_prestations_selectionne[taille - 1] = prof;


                    //Recréer la liste courante moins l'élément sélectionné
                    int taille2 = courante_liste_prestations.length - 1;
                    Professional_Product[] ancienn_liste = new Professional_Product[taille2];
                    int j = 0;
                /*for(int i=0;i<taille2;i++){
                    if(courante_liste_prestations[j]!=prof){
                        System.out.println("SA ME GONFLE : " + courante_liste_prestations[j].getName());
                        ancienn_liste[i]=courante_liste_prestations[j];
                        System.out.println("SA ME GONFLE : " + ancienn_liste[i].getName());
                    }

                    j++;
                }*/
                    for (int i = 0; i < courante_liste_prestations.length - 1; i++) {
                        if (i != position_list_clique) {
                            ancienn_liste[i] = courante_liste_prestations[j];
                            System.out.println("PRENDRERDV " + ancienn_liste[i].getName());
                            j++;
                        } else {
                            j++;
                            ancienn_liste[i] = courante_liste_prestations[j];
                            j++;
                        }
                    }


                    courante_liste_prestations = new Professional_Product[taille2];
                    courante_liste_prestations = ancienn_liste;
                    //System.out.println("SA ME GONFLE : " + courante_liste_prestations[0].getName());


            }else if(service_selectionne.contains("Horaires")) {
                //recupere date
                horaire = (String)getArguments().getString("horaire");
                horaires_date = horaire.split("////");
                System.out.println("DATE :"+horaires_date[1] + " "+ horaires_date[1] + "/ Horaires :"+horaires_date[2]);
                courante_liste_prestations = (Professional_Product[])getArguments().getSerializable("courante_liste_prestations");
                liste_prestations_selectionne = (Professional_Product[]) getArguments().getSerializable("liste_prestations_selectionne");




            }
        }
        System.out.println("Maaaaaaaaaaaaaaaarche bien :"+professional.toString());
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_prendre_rdv, container, false);
        final LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.activity_prendrerdv);

        if(!horaire.equals("")) {
            TextView textview_horaire = (TextView) v.findViewById(R.id.textview_horaire);
            textview_horaire.setText("DATE : " + horaires_date[0] + " " + horaires_date[1] + "\n Horaire : " + horaires_date[2]);
        }

        final Button buton_annuler = (Button) v.findViewById(R.id.annuler);
        buton_annuler.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //getActivity().getSupportFragmentManager().beginTransaction().remove().commit();
                System.out.println("Maaaaaaaaaaaaaaaarche ???????????");
                //Fragment myFragment = (Fragment)getFragmentManager().findFragmentByTag("service2");


                linearLayout.setVisibility(View.GONE);

                FragmentManager fm2 = getActivity().getSupportFragmentManager();
                fm2.popBackStack();



                /*Fragment fragment = new PrendreRdv();
                fm2.beginTransaction().replace(R.id.fragment_remplace, fragment);*/


                /*onDestroyView();
                FragmentManager fm= getFragmentManager();
                if(fm.getBackStackEntryCount()>0){
                    fm.popBackStack();
                }*/

                //getActivity().getFragmentManager().popBackStack();

                //Fragment rSum = getActivity().getSupportFragmentManager().findFragmentByTag("service2");
                //getActivity().getSupportFragmentManager().beginTransaction().remove(rSum).commit();
                //getActivity().finish();


                /*Fragment fragment = null;
                fragment = new HomeFragment2();
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("tag").commit();*/

            }
        });

        //////////////////////////Calcul du Prix et du temps de ou des prestations

        for(int i=0; i < liste_prestations_selectionne.length;i++){
            tariftotal = tariftotal + liste_prestations_selectionne[i].getPrice();
        }




        System.out.println("liste_prestations_selectionne[i].getPrice()"+liste_prestations_selectionne[0].getPrice());


        for(int i = 0; i < liste_prestations_selectionne.length;i++){
            StringTokenizer tokens = new StringTokenizer(liste_prestations_selectionne[i].getDuration(), ":");
            String first = tokens.nextToken();
            String second = tokens.nextToken();
            String third = tokens.nextToken();
            dureetotal = dureetotal + Integer.parseInt(first)*60+Integer.parseInt(second);
        }

        heuretotal = (int) dureetotal/60;
        minutetotal = (int) dureetotal%60;

        /*calendar = (CalendarView) v.findViewById(R.id.jourouverture);
        initializeCalendar();*/



        employe = new String[ResourceProfessional.length];
        for(int i=0;i<ResourceProfessional.length;i++){
            employe[i]=ResourceProfessional[i].getName()+ResourceProfessional[i].getSurname();
        }




        final Spinner spinner = (Spinner)v.findViewById(R.id.liste_employe);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,employe);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);



        /*String id2 = spinner.getSelectedItem().toString();
        int position2 = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            String value = spinner.getItemAtPosition(i).toString();
            if (value.equals(id2)) {
                position2 = i;
            }
        }
        System.out.println("Spinnnnner position"+position2);
        resource = ResourceProfessional[0];*/






        final Button appuie_horaire = (Button) v.findViewById(R.id.appuie_horaire);
        appuie_horaire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "on a appuyé", Toast.LENGTH_SHORT).show();
                System.out.println("liste_prestations_selectionne :"+ liste_prestations_selectionne.length);
                System.out.println("liste_prestations_selectionne :"+ liste_prestations_selectionne[0].getName());
                ArrayList<String> products = new ArrayList<String>();
                for(int i =0;i<liste_prestations_selectionne.length;i++){
                    products.add(String.valueOf(liste_prestations_selectionne[i].getId()));
                }



                String id = spinner.getSelectedItem().toString();
                int position = 0;
                for (int i = 0; i < spinner.getCount(); i++) {
                    String value = spinner.getItemAtPosition(i).toString();
                    if (value.equals(id)) {
                        position = i;
                    }
                }
                System.out.println("Spinnnnner position"+position);
                resource = ResourceProfessional[position];
                System.out.println("resource position"+resource.getName());

                Resource pivot = ResourceProfessional[position];
                ResourceProfessional[position] = ResourceProfessional[0];
                ResourceProfessional[0]=pivot;





                horaires_jsonparser(recherche_horaires(String.valueOf(professional.getId()),"2017-05-05T00:00:00Z","2017-08-17T00:00:00Z",String.valueOf(ResourceProfessional[position].getId()),products));


                Fragment fragment = null;
                Bundle args = new Bundle();
                args.putSerializable("horaires_indisponibilites", horaires_indisponibilites);
                args.putSerializable("prix", tariftotal);
                args.putSerializable("duree", dureetotal);
                args.putSerializable("id_employe", id);
                args.putSerializable("ResourceProfessional",ResourceProfessional);


                args.putSerializable("Professionnal", professional);
                args.putSerializable("liste_prestations_selectionne", liste_prestations_selectionne);
                args.putSerializable("courante_liste_prestations", courante_liste_prestations);
                args.putSerializable("Customer", customer);


                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                fragment = new Horaires();
                fragment.setArguments(args);
                ft.replace(R.id.fragment_remplace, fragment).addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });



        final TextView duree = (TextView) v.findViewById(R.id.duree_prestations);
        final TextView somme = (TextView) v.findViewById(R.id.somme_prestations);
        //TextView heure = (TextView)v.findViewById(R.id.heure);
        //heure.setText("choississez une heure : "+ service_selectionne);



        /*final TextView matinheure1 = (TextView) v.findViewById(R.id.matinheure1);
        final TextView matinheure2 = (TextView) v.findViewById(R.id.matinheure2);
        final TextView matinheure3 = (TextView) v.findViewById(R.id.matinheure3);
        final TextView matinheure4 = (TextView) v.findViewById(R.id.matinheure4);
        final TextView matinheure5 = (TextView) v.findViewById(R.id.matinheure5);
        final TextView matinheure6 = (TextView) v.findViewById(R.id.matinheure6);
        final TextView matinheure7 = (TextView) v.findViewById(R.id.matinheure7);
        final TextView matinheure8 = (TextView) v.findViewById(R.id.matinheure8);*/


        final int color = R.color.green;
        final int color2 = R.color.white;
        /*matinheure1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("J4AI CLIQUER OMG");
                if (check) {
                    if (!heure_selectionne.getText().equals("8h30-9h30")) {
                        System.out.println("LA1");
                        matinheure1.setBackgroundColor(getResources().getColor(color));
                        heure_selectionne.setBackgroundColor(getResources().getColor(color2));
                        heure_selectionne = matinheure1;
                    }else{
                        System.out.println("LA2");
                        matinheure1.setBackgroundColor(getResources().getColor(color2));
                        check = false;
                        heure_selectionne = null;
                    }
                }
                else {
                    System.out.println("LA3");
                    matinheure1.setBackgroundColor(getResources().getColor(color));
                    heure_selectionne = matinheure1;
                    check = true;
                }
            }
        });

        matinheure2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("J4AI CLIQUER OMG");
                if (check) {
                    if (!heure_selectionne.getText().equals("9h30-10h30")) {
                        System.out.println("LA1");
                        matinheure2.setBackgroundColor(getResources().getColor(color));
                        heure_selectionne.setBackgroundColor(getResources().getColor(color2));
                        heure_selectionne = matinheure2;
                    }else{
                        System.out.println("LA2");
                        matinheure2.setBackgroundColor(getResources().getColor(color2));
                        check = false;
                        heure_selectionne = null;
                    }
                }
                else {
                    System.out.println("LA3");
                    matinheure2.setBackgroundColor(getResources().getColor(color));
                    heure_selectionne = matinheure2;
                    check = true;
                }
            }
        });

        matinheure3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("J4AI CLIQUER OMG");
                if (check) {
                    if (!heure_selectionne.getText().equals("10h30-11h30")) {
                        System.out.println("LA1");
                        matinheure3.setBackgroundColor(getResources().getColor(color));
                        heure_selectionne.setBackgroundColor(getResources().getColor(color2));
                        heure_selectionne = matinheure3;
                    }else{
                        System.out.println("LA2");
                        matinheure3.setBackgroundColor(getResources().getColor(color2));
                        check = false;
                        heure_selectionne = null;
                    }
                }
                else {
                    System.out.println("LA3");
                    matinheure3.setBackgroundColor(getResources().getColor(color));
                    heure_selectionne = matinheure3;
                    check = true;
                }
            }
        });

        matinheure4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("J4AI CLIQUER OMG");
                if (check) {
                    if (!heure_selectionne.getText().equals("11h30-12h30")) {
                        System.out.println("LA1");
                        matinheure4.setBackgroundColor(getResources().getColor(color));
                        heure_selectionne.setBackgroundColor(getResources().getColor(color2));
                        heure_selectionne = matinheure4;
                    }else{
                        System.out.println("LA2");
                        matinheure4.setBackgroundColor(getResources().getColor(color2));
                        check = false;
                        heure_selectionne = null;
                    }
                }
                else {
                    System.out.println("LA3");
                    matinheure4.setBackgroundColor(getResources().getColor(color));
                    heure_selectionne = matinheure4;
                    check = true;
                }
            }
        });

        matinheure5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("J4AI CLIQUER OMG");
                if (check) {
                    if (!heure_selectionne.getText().equals("14h30-15h30")) {
                        System.out.println("LA1");
                        matinheure5.setBackgroundColor(getResources().getColor(color));
                        heure_selectionne.setBackgroundColor(getResources().getColor(color2));
                        heure_selectionne = matinheure5;
                    }else{
                        System.out.println("LA2");
                        matinheure5.setBackgroundColor(getResources().getColor(color2));
                        check = false;
                        heure_selectionne = null;
                    }
                }
                else {
                    System.out.println("LA3");
                    matinheure5.setBackgroundColor(getResources().getColor(color));
                    heure_selectionne = matinheure5;
                    check = true;
                }
            }
        });

        matinheure6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("J4AI CLIQUER OMG");
                if (check) {
                    if (!heure_selectionne.getText().equals("15h30-16h30")) {
                        System.out.println("LA1");
                        matinheure6.setBackgroundColor(getResources().getColor(color));
                        heure_selectionne.setBackgroundColor(getResources().getColor(color2));
                        heure_selectionne = matinheure6;
                    }else{
                        System.out.println("LA2");
                        matinheure6.setBackgroundColor(getResources().getColor(color2));
                        check = false;
                        heure_selectionne = null;
                    }
                }
                else {
                    System.out.println("LA3");
                    matinheure6.setBackgroundColor(getResources().getColor(color));
                    heure_selectionne = matinheure6;
                    check = true;
                }
            }
        });

        matinheure7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("J4AI CLIQUER OMG");
                if (check) {
                    if (!heure_selectionne.getText().equals("16h30-17h30")) {
                        System.out.println("LA1");
                        matinheure7.setBackgroundColor(getResources().getColor(color));
                        heure_selectionne.setBackgroundColor(getResources().getColor(color2));
                        heure_selectionne = matinheure7;
                    }else{
                        System.out.println("LA2");
                        matinheure7.setBackgroundColor(getResources().getColor(color2));
                        check = false;
                        heure_selectionne = null;
                    }
                }
                else {
                    System.out.println("LA3");
                    matinheure7.setBackgroundColor(getResources().getColor(color));
                    heure_selectionne = matinheure7;
                    check = true;
                }
            }
        });

        matinheure8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("J4AI CLIQUER OMG");
                if (check) {
                    if (!heure_selectionne.getText().equals("17h30-18h30")) {
                        System.out.println("LA1");
                        matinheure8.setBackgroundColor(getResources().getColor(color));
                        heure_selectionne.setBackgroundColor(getResources().getColor(color2));
                        heure_selectionne = matinheure8;
                    }else{
                        System.out.println("LA2");
                        matinheure8.setBackgroundColor(getResources().getColor(color2));
                        check = false;
                        heure_selectionne = null;
                    }
                }
                else {
                    System.out.println("LA3");
                    matinheure8.setBackgroundColor(getResources().getColor(color));
                    heure_selectionne = matinheure8;
                    check = true;
                }
            }
        });*/










        //////////////////////////Calcul du Prix et du temps de ou des prestations

        //int dureetotal = 0;
        //int heuretotal = 0 ;
        //int minutetotal = 0;

        /*System.out.println("liste_prestations_selectionne[i].getPrice()"+liste_prestations_selectionne[0].getPrice());


        for(int i = 0; i < liste_prestations_selectionne.length;i++){
            System.out.println("Dureeeeeeeeeeeeeeeeeeeee :"+liste_prestations_selectionne[i].getDuration());
            StringTokenizer tokens = new StringTokenizer(liste_prestations_selectionne[i].getDuration(), ":");
            String first = tokens.nextToken();
            String second = tokens.nextToken();
            String third = tokens.nextToken();
            dureetotal = dureetotal + Integer.parseInt(first)*60+Integer.parseInt(second);
        }

        heuretotal = (int) dureetotal/60;
        minutetotal = (int) dureetotal%60;*/


        //Ecriture sur les TextViews

        duree.setText(String.valueOf(heuretotal)+"h"+String.valueOf(minutetotal)+"min");
        somme.setText(String.valueOf(tariftotal)+liste_prestations_selectionne[0].getCurrency());



        /////////////////////////////////////////////




        //AUTRES PRESTATIONS
        Button autresprestations = (Button) v.findViewById(R.id.autres_prestations);

        if(courante_liste_prestations.length == 0 ){
            autresprestations.setEnabled(false);
        }

        autresprestations.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Toast.makeText(this,"Yes",Toast.LENGTH_SHORT).show();
                Fragment fragment = null;
                Bundle args = new Bundle();
                args.putSerializable("Professionnal", professional);
                args.putSerializable("ResourceProfessional",ResourceProfessional);
                //args.putSerializable("position_list_clique", position_list_clique);
                args.putSerializable("liste_prestations_selectionne", liste_prestations_selectionne);
                //System.out.println("ESSAYE DE COMPRENDRE : "+ courante_liste_prestations[1].getName());
                args.putSerializable("courante_liste_prestations", courante_liste_prestations);
                args.putSerializable("Customer", customer);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                //getActivity().findViewById(R.id.fragment_services_professional).setVisibility(View.GONE);

                fragment = new AutresPrestations();
                fragment.setArguments(args);

                //ft.replace(R.id.fragment_remplace, fragment);
                ft.replace(R.id.fragment_remplace, fragment).addToBackStack(null);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        Button reserver = (Button) v.findViewById(R.id.reserver);
        final int finalHeuretotal = heuretotal;
        final int finalMinutetotal = minutetotal;
        final double finalTariftotal = tariftotal;
        reserver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Si l'utilisateur n'a pas renseigné de numéro de téléphone
                if (customer.getMobilephone().equals("null")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Erreur, vous devez d'abord renseigner un numéro de téléphone !").setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else{
                    System.out.println("Daaaaaaaaaaaaate" + jour_selectionne);
                if (horaires_date != null) {
                    Fragment fragment = null;
                    Bundle args = new Bundle();
                    args.putSerializable("Professionnal", professional);
                    args.putSerializable("Customer", customer);
                    System.out.println("RESSSSSSSSSSSSSSSOURCE : "+ResourceProfessional[0]);
                    args.putSerializable("Resource", ResourceProfessional[0]);
                    recap[0] = String.valueOf(finalTariftotal);
                    recap[1] = liste_prestations_selectionne[0].getCurrency();
                    recap[2] = String.valueOf(finalHeuretotal) + "h" + String.valueOf(finalMinutetotal) + "min";
                    recap[3] = professional.getShop_name();
                    recap[4] = professional.getAddress() + " - " + professional.getCity() + " - " + professional.getCountry();
                    //recap[5] = String.valueOf(jour_selectionne) + "/" + String.valueOf(mois_selectionne + 1) + "/" + String.valueOf(annee_selectionne);
                    recap[5] = horaires_date[1];
                    //recap[6] = heure_selectionne.getText().toString();
                    recap[6] = horaires_date[2];



                    //System.out.println("RECAPITULATIF : "+recap[0]+" "+recap[1]);
                    args.putSerializable("recap", recap);
                    args.putSerializable("position_list_clique", position_list_clique);
                    args.putSerializable("liste_prestations_selectionne", liste_prestations_selectionne);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    //getActivity().findViewById(R.id.fragment_services_professional).setVisibility(View.GONE);

                    fragment = new Recapitulatif();
                    fragment.setArguments(args);

                    ft.replace(R.id.fragment_remplace, fragment).addToBackStack(null);

                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                } else {
                    appuie_horaire.setError("Selectionner une horaire avant de prendre un rendez vous");
                    Toast.makeText(getApplicationContext(), "Veuillez selectionner une date", Toast.LENGTH_LONG).show();
                }
            }

            }
        });


        TableLayout table = (TableLayout) v.findViewById(R.id.idtableserviceselectionne);
        TableRow row;

        TextView tv1,tv2;

        row = new TableRow(getActivity());
        tv1 = new TextView(getActivity());
        tv2 = new TextView(getActivity());
        tv1.setText("Mon moment beauté :");
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setGravity(Gravity.LEFT);
        tv1.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
        /*tv2.setText("Prix");
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setGravity(Gravity.RIGHT);
        tv2.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );*/

        row.addView(tv1);
        //row.addView(tv2);
        table.addView(row);

        for(int i=0;i<liste_prestations_selectionne.length;i++) {
            Button b1 = new Button(getActivity());
            b1.setText("Moins");


            row = new TableRow(getActivity()); // création d'une nouvelle ligne

            tv1 = new TextView(getActivity()); // création cellule
            tv1.setText(liste_prestations_selectionne[i].getName()); // ajout du texte
            tv1.setGravity(Gravity.LEFT); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            //tv1.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tv1.setLayoutParams( new TableRow.LayoutParams(0,100,1));

            // idem 2ème cellule
            tv2 = new TextView(getActivity());
            tv2.setText(String.valueOf(liste_prestations_selectionne[i].getPrice())+"€");
            tv2.setGravity(Gravity.RIGHT);
            //tv2.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tv2.setLayoutParams( new TableRow.LayoutParams(0,50,1));

            //tv1.setBackgroundResource(R.drawable.back);
            //tv2.setBackgroundResource(R.drawable.back);
            // ajout des cellules à la ligne
            row.addView(tv1);
            row.addView(b1);
            row.addView(tv2);
            // ajout de la ligne au tableau
            table.addView(row);
        }



        return v;
    }

    public void initializeCalendar() {
        // sets whether to show the week number.
        calendar.setShowWeekNumber(false);
        // sets the first day of week according to Calendar.
        // here we set Monday as the first day of the Calendar
        calendar.setFirstDayOfWeek(2);
        //The background color for the selected week.
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));
        //sets the color for the dates of an unfocused month.
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));
        //sets the color for the separator line between weeks.
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));
        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
        calendar.setSelectedDateVerticalBar(R.color.darkgreen);
        //sets the listener to be notified upon selected date change.

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

        //show the selected date as a toast

            @Override

            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {

                Toast.makeText(getApplicationContext(), day + "/" + month+1 + "/" + year, Toast.LENGTH_LONG).show();
                jour_selectionne = day;
                mois_selectionne = month;
                annee_selectionne = year;
                long daaaate = calendar.getDate();
                System.out.println("Daaaaaaaate : "+ daaaate);
            }

        });

    }



    //Horaires
    public String recherche_horaires(String id_prof,String date_debut,String date_fin,String id_employe,ArrayList<String> id_product) {

        String json = "";
        for(int i=0;i<id_product.size();i++){
            json = json + "&query[products]["+i+"]="+ id_product.get(i);
        }
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee JSON"+json);
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/booking-unavailabilities/professional/" +
                id_prof+"/?query[begin_date]="+date_debut+"&query[end_date]="+date_fin+"&query[resource]="+id_employe+json);
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee JSON"+json);
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public void horaires_jsonparser(String jsonStr) {
        try {

            String recup_schedule = "{" + '"' + "data" + '"' + " : [{";
            String schedule1 = jsonStr.replace("[{", recup_schedule);
            String schedule2 = schedule1.replace("}]", "}]}");

            if (!jsonStr.equals("[]")) {
                JSONObject jsonObj = new JSONObject(schedule2);
                //JSONArray contacts = jsonObj.getJSONArray("data");
                JSONArray Professional_Shop_image = jsonObj.getJSONArray("data");


                horaires_indisponibilites = new ArrayList<String>();

                for (int j = 0; j < Professional_Shop_image.length(); j++) {
                    JSONObject p2 = Professional_Shop_image.getJSONObject(j);
                    String begin_date = p2.getString("begin_date");
                    String end_date = p2.getString("end_date");


                    String s = begin_date +"////"+end_date;
                    horaires_indisponibilites.add(s);
                }
            }

        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

    }




}
