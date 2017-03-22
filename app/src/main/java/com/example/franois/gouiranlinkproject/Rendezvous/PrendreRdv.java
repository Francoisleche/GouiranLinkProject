package com.example.franois.gouiranlinkproject.Rendezvous;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.CamTestActivity;
import com.example.franois.gouiranlinkproject.Object.Professional;
import com.example.franois.gouiranlinkproject.Object.Professional_Product;
import com.example.franois.gouiranlinkproject.R;

import java.util.StringTokenizer;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by François on 02/02/2017.
 */

public class PrendreRdv extends Fragment {

    static final String[] employe = new String[] {"Jacky 1","Jacky 2","Jacky 3","Jacky 4","Jacky 5","Jacky 6"};

    private Professional professional;
    private Professional_Product[] professional_product;
    private Professional_Product[] courante_liste_prestations;
    private Professional_Product[] liste_prestations_selectionne;
    private String service_selectionne;

    private Professional_Product professional_product_selectionne;
    private int position_list_clique;
    private String[] recap = new String[40];


    private CalendarView calendar;
    private int jour_selectionne;
    private int mois_selectionne;
    private int annee_selectionne;

    public PrendreRdv(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            professional = (Professional)getArguments().getSerializable("Professionnal");
            professional_product = (Professional_Product[])getArguments().getSerializable("ProfessionnalProduct");
            service_selectionne = (String)getArguments().getString("service");
            position_list_clique = (int)getArguments().getInt("position_list_clique");

            if(service_selectionne.contains("ServicesProfessional")){
                //Recupération de l'élement selectionné et changement de la nouvelle liste des prestations
                liste_prestations_selectionne = new Professional_Product[1];
                liste_prestations_selectionne[0] = professional_product[position_list_clique];
                courante_liste_prestations = new Professional_Product[professional_product.length-1];
                int j =0;
                for(int i=0;i<professional_product.length-1;i++){
                    if(i!=position_list_clique){
                        courante_liste_prestations[i] = professional_product[j];
                        System.out.println("PRENDRERDV "+ courante_liste_prestations[i].getName() +" - " + professional_product[j].getName());
                        j++;
                    }else{
                        j++;
                        courante_liste_prestations[i] = professional_product[j];
                        j++;
                    }

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


            }
        }
        System.out.println("Maaaaaaaaaaaaaaaarche bien :"+professional.toString());
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_prendre_rdv, container, false);

        calendar = (CalendarView) v.findViewById(R.id.jourouverture);
        //Initialisation du calendrier
        initializeCalendar();

        final TextView duree = (TextView) v.findViewById(R.id.duree_prestations);
        final TextView somme = (TextView) v.findViewById(R.id.somme_prestations);
        TextView heure = (TextView)v.findViewById(R.id.heure);
        heure.setText("choississez une heure : "+ service_selectionne);

        Spinner spinner = (Spinner)v.findViewById(R.id.liste_employe);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,employe);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);



        //////////////////////////Calcul du Prix et du temps de ou des prestations
        double tariftotal = 0.00;
        int dureetotal = 0;
        int heuretotal = 0 ;
        int minutetotal = 0;

        for(int i=0; i < liste_prestations_selectionne.length;i++){
            tariftotal = tariftotal + liste_prestations_selectionne[i].getPrice();
        }

        for(int i = 0; i < liste_prestations_selectionne.length;i++){
            StringTokenizer tokens = new StringTokenizer(liste_prestations_selectionne[i].getDuration(), ":");
            String first = tokens.nextToken();
            String second = tokens.nextToken();
            String third = tokens.nextToken();
            dureetotal = dureetotal + Integer.parseInt(first)*60+Integer.parseInt(second);
        }

        heuretotal = (int) dureetotal/60;
        minutetotal = (int) dureetotal%60;

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
                //args.putSerializable("position_list_clique", position_list_clique);
                args.putSerializable("liste_prestations_selectionne", liste_prestations_selectionne);
                //System.out.println("ESSAYE DE COMPRENDRE : "+ courante_liste_prestations[1].getName());
                args.putSerializable("courante_liste_prestations", courante_liste_prestations);


                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                //getActivity().findViewById(R.id.fragment_services_professional).setVisibility(View.GONE);

                fragment = new AutresPrestations();
                fragment.setArguments(args);

                ft.replace(R.id.fragment_remplace, fragment);

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
                // Perform action on click
                //Toast.makeText(this,"Yes",Toast.LENGTH_SHORT).show();
                //CamTestActivity cam = new CamTestActivity();
                //Intent intent = new Intent(getApplicationContext(), Recapitulatif.class);
                //startActivity(intent);
                Fragment fragment = null;
                Bundle args = new Bundle();
                args.putSerializable("Professionnal", professional);
                recap[0] = String.valueOf(finalTariftotal)+liste_prestations_selectionne[0].getCurrency();
                recap[1] = String.valueOf(finalHeuretotal)+"h"+String.valueOf(finalMinutetotal)+"min";
                recap[2] = professional.getShop_name();
                recap[3] = professional.getAddress() + " - " + professional.getCity() + " - " + professional.getCountry();
                recap[4] = String.valueOf(jour_selectionne)+"/"+String.valueOf(mois_selectionne+1)+"/"+String.valueOf(annee_selectionne);


                //System.out.println("RECAPITULATIF : "+recap[0]+" "+recap[1]);
                args.putSerializable("recap", recap);
                args.putSerializable("position_list_clique", position_list_clique);
                args.putSerializable("liste_prestations_selectionne", liste_prestations_selectionne);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                //getActivity().findViewById(R.id.fragment_services_professional).setVisibility(View.GONE);

                fragment = new Recapitulatif();
                fragment.setArguments(args);

                ft.replace(R.id.fragment_remplace, fragment);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

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




}
