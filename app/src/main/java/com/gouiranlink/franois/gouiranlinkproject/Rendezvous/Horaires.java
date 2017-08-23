package com.gouiranlink.franois.gouiranlinkproject.Rendezvous;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Schedule;
import com.gouiranlink.franois.gouiranlinkproject.Object.Resource;
import com.gouiranlink.franois.gouiranlinkproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by François on 10/05/2017.
 */

public class Horaires extends Fragment{

    private Double prix;
    private int duree;
    private CalendarView calendar;
    ArrayList<String> horaires_indisponibilites;
    private ArrayList<String> recup;
    private ListView listView;

    private IndisponibiliteAdapter adapter;

    private int layout = R.layout.services4_horaires;
    private int id = R.id.horaire;

    private Professional professional;
    private Customer customer;
    private Professional_Product[] liste_prestations_selectionne;
    private Professional_Product[] courante_liste_prestations;
    private Resource[] ResourceProfessional;


    private String dday;
    private String mmonth;
    private String yyear;

    String dayName ="";

    String heure_deja_choisi = "";
    String heure_deja_choisi2 = "";

    Button button_clique = null;
    Button button_clique2 = null;
    Button buton1;
    Button buton2;
    Button buton3;
    Button buton4;
    Button buton5;
    Button buton6;
    Button buton7;
    Button buton8;
    Button buton9;
    Button buton10;
    Button buton11;
    Button buton12;
    Button buton13;
    Button buton14;
    Button buton15;
    Button buton16;
    Button buton17;

    Button buton00;
    Button buton01;
    Button buton02;
    Button buton03;
    Button suivant_horaire;


    public Horaires() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            horaires_indisponibilites = (ArrayList<String>) getArguments().getSerializable("horaires_indisponibilites");
            professional = (Professional) getArguments().getSerializable("Professionnal");
            liste_prestations_selectionne = (Professional_Product[]) getArguments().getSerializable("liste_prestations_selectionne");
            courante_liste_prestations = (Professional_Product[]) getArguments().getSerializable("courante_liste_prestations");
            customer = (Customer) getArguments().getSerializable("Customer");
            prix = (Double) getArguments().getDouble("prix");
            duree = (int) getArguments().getInt("duree");
            ResourceProfessional = (Resource[])getArguments().getSerializable("ResourceProfessional");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //recup = new ArrayList<String>();

        //int dayofweek3 = calendar.getFirstDayOfWeek();
        //System.out.println("44444444444444444444 :::: "+ dayofweek3);
        SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
        Date date = new Date();
        dayName = sdf_.format(date);
        System.out.println("" + dayName);

        int jour_semaine = 0;
        if(dayName.equals("lundi")){
            jour_semaine=1;
        }else if(dayName.equals("mardi")){
            jour_semaine=2;
        }else if(dayName.equals("mercredi")){
            jour_semaine=3;
        }else if(dayName.equals("jeudi")){
            jour_semaine=4;
        }else if(dayName.equals("vendredi")){
            jour_semaine=5;
        }else if(dayName.equals("samedi")){
            jour_semaine=6;
        }else if(dayName.equals("dimanche")){
            jour_semaine=7;
        }


        recup = new ArrayList<String>();
        String debut ="";
        String fin = "";

        Professional_Schedule[] schedule2 = professional.getSchedule();
        for(int j =0 ;j<schedule2.length;j++){
            if(schedule2[j].getWeekday() == jour_semaine){
                debut=schedule2[j].getBegin_time();
                fin=schedule2[j].getEnd_time();
            }
        }

        System.out.println("Oooooooooooooooooooh" + jour_semaine + " "+debut+" "+fin + " duréééééééé : "+duree);



        int dureetotal = duree;
        int heuretotal = 0 ;
        int minutetotal = 0;

        //String s = debut.substring(0,5);
        String s = "";
        for(int i = Integer.parseInt(debut.substring(0,2));i<=Integer.parseInt(fin.substring(0,2));i++){
            //int o = Integer.parseInt(debut.substring(0,2));
            if(i<10){
                s = "0"+String.valueOf(i);
            }else{
                s = String.valueOf(i);
            }
            s = s + ":00";
            recup.add(String.valueOf(s));
        }
        /*Professional_Schedule[] schedule = professional.getSchedule();
        for(int j =0 ;j<schedule.length;j++){
            String s = "";
            if(schedule[j].getWeekday() == 1){
                s = schedule[j].getBegin_time().substring(0,5);
                for(int i =0;i<Integer.parseInt(schedule[j].getEnd_time().substring(0,2));i++){
                    recup.add(String.valueOf(s));
                    s = String.valueOf(Integer.parseInt(schedule[j].getBegin_time().substring(0,2))+i*10);
                    s = s + ":00";
                }
            }else if(schedule[j].getWeekday() == 2){
                s = schedule[j].getBegin_time().substring(0,5);
                for(int i =0;i<Integer.parseInt(schedule[j].getEnd_time().substring(0,2));i++){
                    recup.add(String.valueOf(s));
                    s = String.valueOf(Integer.parseInt(schedule[j].getBegin_time().substring(0,2))+i*10);
                    s = s + ":00";
                }
            }else if(schedule[j].getWeekday() == 3){
                s = schedule[j].getBegin_time().substring(0,5);
                for(int i =0;i<Integer.parseInt(schedule[j].getEnd_time().substring(0,2));i++){
                    recup.add(String.valueOf(s));
                    s = String.valueOf(Integer.parseInt(schedule[j].getBegin_time().substring(0,2))+i*10);
                    s = s + ":00";
                }
            }else if(schedule[j].getWeekday() == 4){
                s = schedule[j].getBegin_time().substring(0,5);
                for(int i =0;i<Integer.parseInt(schedule[j].getEnd_time().substring(0,2));i++){
                    recup.add(String.valueOf(s));
                    s = String.valueOf(Integer.parseInt(schedule[j].getBegin_time().substring(0,2))+i*10);
                    s = s + ":00";
                }
            }else if(schedule[j].getWeekday() == 5){
                s = schedule[j].getBegin_time().substring(0,5);
                for(int i =0;i<Integer.parseInt(schedule[j].getEnd_time().substring(0,2));i++){
                    recup.add(String.valueOf(s));
                    s = String.valueOf(Integer.parseInt(schedule[j].getBegin_time().substring(0,2))+i*10);
                    s = s + ":00";
                }
            }else if(schedule[j].getWeekday() == 6){
                s = schedule[j].getBegin_time().substring(0,5);
                for(int i =0;i<Integer.parseInt(schedule[j].getEnd_time().substring(0,2));i++){
                    recup.add(String.valueOf(s));
                    s = String.valueOf(Integer.parseInt(schedule[j].getBegin_time().substring(0,2))+i*10);
                    s = s + ":00";
                }
            }else if(schedule[j].getWeekday() == 7){
                s = schedule[j].getBegin_time().substring(0,5);
                for(int i =0;i<Integer.parseInt(schedule[j].getEnd_time().substring(0,2));i++){
                    recup.add(String.valueOf(s));
                    s = String.valueOf(Integer.parseInt(schedule[j].getBegin_time().substring(0,2))+i*10);
                    s = s + ":00";
                }
            }
        }*/


        /*recup.add("08:00");
        recup.add("09:00");
        recup.add("10:00");
        recup.add("11:00");
        recup.add("12:00");
        recup.add("13:00");
        recup.add("14:00");
        recup.add("15:00");
        recup.add("16:00");
        recup.add("17:00");
        recup.add("18:00");
        recup.add("19:00");
        recup.add("20:00");*/

        final View v = inflater.inflate(R.layout.fragment_horaires, container, false);

        buton1 = (Button) v.findViewById(R.id.text7h);
        buton2 = (Button) v.findViewById(R.id.text8h);
        buton3 = (Button) v.findViewById(R.id.text9h);
        buton4 = (Button) v.findViewById(R.id.text10h);
        buton5 = (Button) v.findViewById(R.id.text11h);
        buton6 = (Button) v.findViewById(R.id.text12h);
        buton7 = (Button) v.findViewById(R.id.text13h);
        buton8 = (Button) v.findViewById(R.id.text14h);
        buton9 = (Button) v.findViewById(R.id.text15h);
        buton10 = (Button) v.findViewById(R.id.text16h);
        buton11 = (Button) v.findViewById(R.id.text17h);
        buton12 = (Button) v.findViewById(R.id.text18h);
        buton13 = (Button) v.findViewById(R.id.text19h);
        buton14 = (Button) v.findViewById(R.id.text20h);
        buton15 = (Button) v.findViewById(R.id.text21h);
        buton16 = (Button) v.findViewById(R.id.text22h);
        buton17 = (Button) v.findViewById(R.id.text23h);

        buton00 = (Button) v.findViewById(R.id.text00);
        buton01 = (Button) v.findViewById(R.id.text01);
        buton02 = (Button) v.findViewById(R.id.text02);
        buton03 = (Button) v.findViewById(R.id.text03);

        calendar = (CalendarView) v.findViewById(R.id.jourouverture_horaire);
        initializeCalendar();

        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.MONTH, 11);


        /*listView = (ListView) v.findViewById(R.id.meshoraires);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                   args.putSerializable("service", "Horaires");
                   args.putSerializable("ResourceProfessional",ResourceProfessional);
                   //String date = String.valueOf(dday) + "/" + String.valueOf(mmonth + 1) + "/" + String.valueOf(yyear) + "////"+ listView.getItemAtPosition(position);

                   String heure1 = listView.getItemAtPosition(position).toString().replace(":","h");
                   String heure2 = listView.getItemAtPosition(position+1).toString().replace(":","h");
                   String date = dayName +"////" +dday + "/" + mmonth + "/" + yyear + "////"+heure1+"-"+heure2;
                   args.putSerializable("horaire",date);
                   FragmentManager fm = getFragmentManager();
                   FragmentTransaction ft = fm.beginTransaction();
                   //getActivity().findViewById(R.id.fragment_services_professional).setVisibility(View.GONE);

                   fragment = new PrendreRdv();
                   fragment.setArguments(args);

                   ft.replace(R.id.fragment_remplace, fragment);

                   ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                   ft.commit();



               }
         });*/








        String[] items = new String[recup.size()];
        for(int i =0;i<recup.size();i++){
            items[i]=recup.get(i);
            System.out.println("  items[i] :"+  items[i] +"/ "+recup.get(i));
        }


        adapter = new IndisponibiliteAdapter(getActivity(),layout,id, items,String.valueOf(prix));
        listView.setAdapter(adapter);


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                Log.d("scroll", "scroll: " + listView.getScrollY());
            }



            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0)
                    Log.d("scroll", "scrolling stopped");
            }
        });



        /*HorizontalScrollView hsl = (HorizontalScrollView) v.findViewById(R.id.scrollView);
        final LinearLayout l1 = (LinearLayout)v.findViewById(R.id.horizontalbar);
        final ArrayList<Button> l11 = new ArrayList<Button>();
        final LinearLayout l2 = (LinearLayout)v.findViewById(R.id.horizontalbar2);
        final LinearLayout calendrier4 = (LinearLayout)v.findViewById(R.id.calendrier4);
        final String[] horaire_recup = {""};

        final Button[] button1 = new Button[1];
        final Button[] button2 = new Button[1];


        for(int i = 7; i < 23; i++)
        {
            final Button b = new Button(getActivity());
            b.setText(i+"h");
            b.setBackgroundColor(Color.parseColor("#d16677"));
            b.setTextColor(Color.parseColor("#ffffff"));
            b.setTextSize(15);
            b.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            l1.addView(b);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("heure_deja_choisi : "+heure_deja_choisi);
                    System.out.println("heure_deja_choisi : "+button1[0].getText());
                    if(heure_deja_choisi.equals(b.getText())){
                        button1[0].setBackgroundColor(Color.parseColor("#ffffff"));
                        button1[0].setTextColor(Color.parseColor("#d16677"));

                        b.setBackgroundColor(Color.parseColor("#d16677"));
                        b.setTextColor(Color.parseColor("#ffffff"));
                        horaire_recup[0] = "";
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                        heure_deja_choisi="";
                    }else{
                        button1[0].setBackgroundColor(Color.parseColor("#ffffff"));
                        button1[0].setTextColor(Color.parseColor("#d16677"));
                        button1[0].setText(b.getText());

                        b.setBackgroundColor(Color.parseColor("#ffffff"));
                        b.setTextColor(Color.parseColor("#d16677"));
                        horaire_recup[0] = (String) b.getText();
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        heure_deja_choisi=(String) b.getText();
                    }



                    //final LinearLayout l2 = (LinearLayout)v.findViewById(R.id.horizontalbar2);

                    for(int i = 0; i <= 3; i++)
                    {
                        final Button b2 = new Button(getActivity());
                        b2.setText(horaire_recup[0]+(((i+1)*15)-15)+"min");
                        b2.setBackgroundColor(Color.parseColor("#d16677"));
                        b2.setTextColor(Color.parseColor("#ffffff"));
                        b2.setTextSize(15);
                        b2.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                        l2.addView(b2);

                        b2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("heure_deja_choisi : "+heure_deja_choisi2);
                                if(heure_deja_choisi2.equals(b2.getText())){
                                    b2.setBackgroundColor(Color.parseColor("#d16677"));
                                    b2.setTextColor(Color.parseColor("#ffffff"));
                                    horaire_recup[0] = (String) b.getText();
                                    heure_deja_choisi2="";

                                }else{
                                    b2.setBackgroundColor(Color.parseColor("#ffffff"));
                                    b2.setTextColor(Color.parseColor("#d16677"));
                                    horaire_recup[0] = (String) b2.getText();
                                    heure_deja_choisi2 = (String) b2.getText();
                                }

                            }
                        });

                    }

                }
            });

        }*/








        final LinearLayout calendrier4 = (LinearLayout)v.findViewById(R.id.calendrier4);

        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton1.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton1.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton1;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton1.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton1.setBackgroundColor(Color.parseColor("#d16677"));
                        buton1.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton1.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton1.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton1;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });

        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton2.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton2.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton2;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton2.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton2.setBackgroundColor(Color.parseColor("#d16677"));
                        buton2.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton2.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton2.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton2;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton3.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton3.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton3;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton3.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton3.setBackgroundColor(Color.parseColor("#d16677"));
                        buton3.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton3.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton3.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton3;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton4.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton4.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton4;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton4.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton4.setBackgroundColor(Color.parseColor("#d16677"));
                        buton4.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton4.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton4.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton4;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton5.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton5.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton5;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton5.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton5.setBackgroundColor(Color.parseColor("#d16677"));
                        buton5.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton5.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton5.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton5;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton6.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton6.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton6;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton6.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton6.setBackgroundColor(Color.parseColor("#d16677"));
                        buton6.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton6.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton6.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton6;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton7.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton7.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton7;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton7.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton7.setBackgroundColor(Color.parseColor("#d16677"));
                        buton7.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton7.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton7.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton7;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton8.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton8.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton8;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton8.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton8.setBackgroundColor(Color.parseColor("#d16677"));
                        buton8.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton8.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton8.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton8;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton9.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton9.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton9;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton9.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton9.setBackgroundColor(Color.parseColor("#d16677"));
                        buton9.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton9.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton9.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton9;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton10.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton10.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton10;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton10.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton10.setBackgroundColor(Color.parseColor("#d16677"));
                        buton10.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton10.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton10.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton10;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton11.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton11.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton11;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton11.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton11.setBackgroundColor(Color.parseColor("#d16677"));
                        buton11.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton11.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton11.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton11;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton12.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton12.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton12;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton12.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton12.setBackgroundColor(Color.parseColor("#d16677"));
                        buton12.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton12.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton12.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton12;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton13.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton13.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton13;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton13.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton13.setBackgroundColor(Color.parseColor("#d16677"));
                        buton13.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton13.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton13.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton13;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton14.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton14.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton14;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton14.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton14.setBackgroundColor(Color.parseColor("#d16677"));
                        buton14.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton14.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton14.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton14;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton15.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton15.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton15;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton15.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton15.setBackgroundColor(Color.parseColor("#d16677"));
                        buton15.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton15.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton15.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton15;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton16.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton16.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton16;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton16.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton16.setBackgroundColor(Color.parseColor("#d16677"));
                        buton16.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton16.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton16.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton16;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });
        buton17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique == null) {
                    buton17.setBackgroundColor(Color.parseColor("#ffffff"));
                    buton17.setTextColor(Color.parseColor("#d16677"));
                    button_clique = buton17;
                    calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                    buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                    buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                    buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                    buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                }else{
                    if(buton17.getText().equals(button_clique.getText())){
                        System.out.println("Enplus1 : "+button_clique.getText());
                        buton17.setBackgroundColor(Color.parseColor("#d16677"));
                        buton17.setTextColor(Color.parseColor("#ffffff"));
                        button_clique=null;
                        calendrier4.setVisibility(true ? View.GONE : View.VISIBLE);
                    }else{
                        System.out.println("Enplus2 : "+button_clique.getText());
                        button_clique.setBackgroundColor(Color.parseColor("#d16677"));
                        button_clique.setTextColor(Color.parseColor("#ffffff"));
                        buton17.setBackgroundColor(Color.parseColor("#ffffff"));
                        buton17.setTextColor(Color.parseColor("#d16677"));
                        button_clique = buton17;
                        calendrier4.setVisibility(true ? View.VISIBLE : View.GONE);
                        buton00.setText(button_clique.getText().toString().substring(0,3)+"00");
                        buton01.setText(button_clique.getText().toString().substring(0,3)+"15");
                        buton02.setText(button_clique.getText().toString().substring(0,3)+"30");
                        buton03.setText(button_clique.getText().toString().substring(0,3)+"45");
                    }
                }
            }
        });





        buton00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique2 == null) {
                    buton00.setTextColor(Color.parseColor("#d16677"));
                    button_clique2 = buton00;
                }else{
                    if(buton00.getText().equals(button_clique2.getText())){
                        System.out.println("Enplus1 : "+button_clique2.getText());
                        buton00.setTextColor(Color.parseColor("#000000"));
                        button_clique2=null;
                    }else{
                        System.out.println("Enplus2 : "+button_clique2.getText());
                        button_clique2.setTextColor(Color.parseColor("#000000"));
                        buton00.setTextColor(Color.parseColor("#d16677"));
                        button_clique2 = buton00;
                    }
                }
            }
        });
        buton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique2 == null) {
                    buton01.setTextColor(Color.parseColor("#d16677"));
                    button_clique2 = buton01;
                }else{
                    if(buton01.getText().equals(button_clique2.getText())){
                        System.out.println("Enplus1 : "+button_clique2.getText());
                        buton01.setTextColor(Color.parseColor("#000000"));
                        button_clique2=null;
                    }else{
                        System.out.println("Enplus2 : "+button_clique2.getText());
                        button_clique2.setTextColor(Color.parseColor("#000000"));
                        buton01.setTextColor(Color.parseColor("#d16677"));
                        button_clique2 = buton01;
                    }
                }
            }
        });
        buton02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique2 == null) {
                    buton02.setTextColor(Color.parseColor("#d16677"));
                    button_clique2 = buton02;
                }else{
                    if(buton02.getText().equals(button_clique2.getText())){
                        System.out.println("Enplus1 : "+button_clique2.getText());
                        buton02.setTextColor(Color.parseColor("#000000"));
                        button_clique2=null;
                    }else{
                        System.out.println("Enplus2 : "+button_clique2.getText());
                        button_clique2.setTextColor(Color.parseColor("#000000"));
                        buton02.setTextColor(Color.parseColor("#d16677"));
                        button_clique2 = buton02;
                    }
                }
            }
        });
        buton03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_clique2 == null) {
                    buton03.setTextColor(Color.parseColor("#d16677"));
                    button_clique2 = buton03;
                }else{
                    if(buton03.getText().equals(button_clique2.getText())){
                        System.out.println("Enplus1 : "+button_clique2.getText());
                        buton03.setTextColor(Color.parseColor("#000000"));
                        button_clique2=null;
                    }else{
                        System.out.println("Enplus2 : "+button_clique2.getText());
                        button_clique2.setTextColor(Color.parseColor("#000000"));
                        buton03.setTextColor(Color.parseColor("#d16677"));
                        button_clique2 = buton03;
                    }
                }
            }
        });




        suivant_horaire = (Button) v.findViewById(R.id.suivant_horaire);
        suivant_horaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                Bundle args = new Bundle();
                args.putSerializable("Professionnal", professional);
                args.putSerializable("Customer",customer);
                args.putSerializable("liste_prestations_selectionne", liste_prestations_selectionne);
                args.putSerializable("courante_liste_prestations", courante_liste_prestations);
                //args.putSerializable("position_list_clique", lstview.getSelectedItem());
                args.putSerializable("position_list_clique", 2);
                args.putSerializable("service", "Horaires");
                args.putSerializable("ResourceProfessional",ResourceProfessional);
                //String date = String.valueOf(dday) + "/" + String.valueOf(mmonth + 1) + "/" + String.valueOf(yyear) + "////"+ listView.getItemAtPosition(position);

                //String heure1 = listView.getItemAtPosition(position).toString().replace(":","h");
                //String heure2 = listView.getItemAtPosition(position+1).toString().replace(":","h");

                String heure1 = button_clique2.getText().toString().replace(":","h");
                String heure2 = String.valueOf(Integer.parseInt(button_clique2.getText().toString().substring(0,2))+1)+"h"+button_clique2.getText().toString().substring(3,5);
                String date = dayName +"////" +dday + "/" + mmonth + "/" + yyear + "////"+heure1+"-"+heure2;
                args.putSerializable("horaire",date);
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


                int dayofweek3 = calendar.getFirstDayOfWeek();
                System.out.println("44444444444444444444 :::: "+ dayofweek3);
                SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
                //Date date = new Date();
                dayName = sdf_.format(calendar.getDate());
                System.out.println("" + dayName + " " + year+month+day + "");

                int jour_semaine = 0;
                if(dayName.equals("lundi")){
                    jour_semaine=1;
                }else if(dayName.equals("mardi")){
                    jour_semaine=2;
                }else if(dayName.equals("mercredi")){
                    jour_semaine=3;
                }else if(dayName.equals("jeudi")){
                    jour_semaine=4;
                }else if(dayName.equals("vendredi")){
                    jour_semaine=5;
                }else if(dayName.equals("samedi")){
                    jour_semaine=6;
                }else if(dayName.equals("dimanche")){
                    jour_semaine=7;
                }


                recup = new ArrayList<String>();
                String debut ="";
                String fin = "";

                Professional_Schedule[] schedule2 = professional.getSchedule();




                for(int j =0 ;j<schedule2.length;j++){
                    System.out.println("Date schedule : " +schedule2[j].getWeekday());
                    if(schedule2[j].getWeekday() == jour_semaine){
                        if(schedule2[j].getBegin_time().equals("")){
                            debut="07";
                            fin="23";
                        }else{
                            debut=schedule2[j].getBegin_time();
                            fin=schedule2[j].getEnd_time();
                        }
                    }else{
                        debut="23";
                        fin="23";
                    }
                }


                System.out.println("Oooooooooooooooooooh" + jour_semaine + " "+debut+" "+fin);

                //String s = debut.substring(0,5);
                String s = "";
                for(int i = Integer.parseInt(debut.substring(0,2));i<=Integer.parseInt(fin.substring(0,2));i++){
                    //int o = Integer.parseInt(debut.substring(0,2));
                    if(i<10){
                        s = "0"+String.valueOf(i);
                    }else{
                        s = String.valueOf(i);
                    }
                    s = s + ":00";
                    recup.add(String.valueOf(s));
                }






                Indisponibilite[] liste_indisponibilite = new Indisponibilite[horaires_indisponibilites.size()];
                Indisponibilite[] liste_indisponibilite2 = new Indisponibilite[horaires_indisponibilites.size()];

                for(int i =0;i<horaires_indisponibilites.size();i++) {
                    String CurrentString = horaires_indisponibilites.get(i);
                    String[] separated = CurrentString.split("////");
                    System.out.println("INDISPONIBILITE 1 :" + separated[0]);
                    String result1 = separated[0].substring(0, 10);
                    String[] date1 = result1.split("-");
                    String result2 = separated[0].substring(11, 18);
                    String[] date2 = result2.split(":");
                    Indisponibilite indisponibilite1 = new Indisponibilite(date1[0], date1[1], date1[2], date2[0], date2[1]);
                    System.out.println("INDISPONIBILITE 1 :" + indisponibilite1.toString());

                    System.out.println("INDISPONIBILITE 2 :" + separated[1]);
                    String result3 = separated[1].substring(0, 10);
                    String[] date3 = result3.split("-");
                    String result4 = separated[1].substring(11, 18);
                    String[] date4 = result4.split(":");
                    Indisponibilite indisponibilite2 = new Indisponibilite(date3[0], date3[1], date3[2], date4[0], date4[1]);
                    System.out.println("INDISPONIBILITE 2 :" + indisponibilite2.toString());

                    liste_indisponibilite[i]=indisponibilite1;
                    liste_indisponibilite2[i]=indisponibilite2;
                }

                for(int y =0;y<recup.size();y++){
                    System.out.println("recup22222222222222  :" + recup.get(y));
                }



                boolean trouve = false;

                for(int y = 0;y<liste_indisponibilite.length;y++) {
                    if (Integer.parseInt(liste_indisponibilite[y].getAnnee()) == year && Integer.parseInt(liste_indisponibilite[y].getMois()) == (month + 1) && Integer.parseInt(liste_indisponibilite[y].getJour()) == day) {
                        trouve = true;
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 7 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 7){
                            buton1.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("1");
                        }else{
                            System.out.println("1");
                            buton1.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 8 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 8){
                            buton2.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("2");
                        }else{
                            buton2.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >=9 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 9){
                            buton3.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("3");
                        }else{
                            buton3.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 10 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 10){
                            buton4.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("4");
                        }else{
                            buton4.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 11 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 11){
                            buton5.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("5");
                        }else{
                            buton5.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 12 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 12){
                            buton6.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("6");
                        }else{
                            buton6.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 13 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 13){
                            buton7.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("7");
                        }else{
                            buton7.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 14 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 14){
                            buton8.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("8");
                        }else{
                            buton8.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 15 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 15){
                            buton9.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("9");
                        }else{
                            buton9.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 16 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 16){
                            buton10.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("10");
                        }else{
                            buton10.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 17 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 17){
                            buton11.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("11");
                        }else{
                            buton11.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 18 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 18){
                            buton12.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("12");
                        }else{
                            buton12.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 19 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 19){
                            buton13.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("13");
                        }else{
                            buton13.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 20 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 20){
                            buton14.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("14");
                        }else{
                            buton14.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 21 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 21){
                            buton15.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("15");
                        }else{
                            buton15.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 22 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 22){
                            buton16.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("16");
                        }else{
                            buton16.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        if(Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= 23 && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= 23){
                            buton17.setVisibility(true ? View.GONE : View.VISIBLE);
                            System.out.println("17");
                        }else{
                            buton17.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                        break;
                    }
                    else {
                        if (!trouve) {
                            System.out.println("ONPASSEPARLA OMG ?????");
                            System.out.println("ONPASSEPARLA OMG ?????" + Integer.parseInt(liste_indisponibilite[y].getAnnee()) + "   " + year + "     " + Integer.parseInt(liste_indisponibilite[y].getMois()) + "     " + (month + 1) + "     " + Integer.parseInt(liste_indisponibilite[y].getJour()) + "     " + day);
                            buton1.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton2.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton3.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton4.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton5.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton6.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton7.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton8.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton9.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton10.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton11.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton12.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton13.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton14.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton15.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton16.setVisibility(true ? View.VISIBLE : View.GONE);
                            buton17.setVisibility(true ? View.VISIBLE : View.GONE);
                        }
                    }
                }



                if(day<10)
                    dday=0+String.valueOf(day);
                else
                    dday=String.valueOf(day);

                if((month+1)<10)
                    mmonth=0+String.valueOf(month+1);
                else
                    mmonth=0+String.valueOf(month+1);

                yyear = String.valueOf(year);


                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();

                long daaaate = calendar.getDate();
                System.out.println("Daaaaaaaate : "+ daaaate);
            }

        });

    }







}
