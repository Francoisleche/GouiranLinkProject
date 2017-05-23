package com.gouiranlink.franois.gouiranlinkproject.Rendezvous;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Schedule;
import com.gouiranlink.franois.gouiranlinkproject.Object.Resource;
import com.gouiranlink.franois.gouiranlinkproject.R;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

        View v = inflater.inflate(R.layout.fragment_horaires, container, false);

        calendar = (CalendarView) v.findViewById(R.id.jourouverture_horaire);
        initializeCalendar();

        listView = (ListView) v.findViewById(R.id.meshoraires);

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
         });








        String[] items = new String[recup.size()];
        for(int i =0;i<recup.size();i++){
            items[i]=recup.get(i);
            System.out.println("  items[i] :"+  items[i] +"/ "+recup.get(i));
        }


        adapter = new IndisponibiliteAdapter(getActivity(),layout,id, items,String.valueOf(prix));
        listView.setAdapter(adapter);



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
                    if(schedule2[j].getWeekday() == jour_semaine){
                        debut=schedule2[j].getBegin_time();
                        fin=schedule2[j].getEnd_time();
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



                /*recup.add("08h");
                recup.add("09h");
                recup.add("10h");
                recup.add("11h");
                recup.add("12h");
                recup.add("13h");
                recup.add("14h");
                recup.add("15h");
                recup.add("16h");
                recup.add("17h");
                recup.add("18h");
                recup.add("19h");
                recup.add("20h");*/


                boolean trouve = false;

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



                ArrayList<String> recup2 = recup;
                System.out.println("recup2 size :" + recup2.size());
                for(int y = 0;y<liste_indisponibilite.length;y++) {

                    System.out.println("1");
                    System.out.println("1111111 :" + Integer.parseInt(liste_indisponibilite[y].getAnnee()) +"   "+year +"     "+Integer.parseInt(liste_indisponibilite[y].getMois())+"     "+ (month+1) + "     "+Integer.parseInt(liste_indisponibilite[y].getJour())+"     "+day);
                    if (Integer.parseInt(liste_indisponibilite[y].getAnnee()) == year && Integer.parseInt(liste_indisponibilite[y].getMois()) == (month+1) && Integer.parseInt(liste_indisponibilite[y].getJour()) == day) {
                        trouve = true;
                        System.out.println("1111111 :" + Integer.parseInt(liste_indisponibilite[y].getAnnee()) +"   "+year +"     "+Integer.parseInt(liste_indisponibilite[y].getMois())+"     "+ month + "     "+Integer.parseInt(liste_indisponibilite[y].getJour())+"     "+day);
                        int taille = 0;
                        int length = recup2.size();
                        for (int o = 0; o < length; o++) {
                            //System.out.println("2 :"+recup2.get(o));
                            if (Integer.parseInt(liste_indisponibilite2[y].getHeure()) >= Integer.parseInt(recup2.get(taille).substring(0,2)) && Integer.parseInt(liste_indisponibilite[y].getHeure()) <= Integer.parseInt(recup2.get(taille).substring(0,2))) {
                                System.out.println("3 : " + liste_indisponibilite2[y].getHeure() + "     " + Integer.parseInt(recup2.get(taille).substring(0, 2)) + "     " + taille + "      " + o);
                                recup2.remove(taille);
                            }
                            else{
                                taille++;
                            }
                        }
                    }
                }




                if(trouve){
                    String[] items = new String[recup2.size()];
                    for(int p =0;p<recup2.size();p++){
                        items[p]=recup2.get(p);
                    }

                    /*for(int p =0;p<items.length;p++){
                        System.out.println("4 :"+items[p]);
                    }*/

                    // update data in our adapter
                    //adapter.getHoraire().
                    adapter.setHoraire(items);
                    //IndisponibiliteAdapter adapter = new IndisponibiliteAdapter(getActivity(),layout,id, items,prix);
                    adapter.notifyDataSetChanged();

                    trouve = false;
                }else{



                    String[] items = new String[recup.size()];
                    for(int p =0;p<recup.size();p++){
                        items[p]=recup.get(p);
                    }

                    adapter.setHoraire(items);
                    //IndisponibiliteAdapter adapter = new IndisponibiliteAdapter(getActivity(),layout,id, items,prix);
                    adapter.notifyDataSetChanged();

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
