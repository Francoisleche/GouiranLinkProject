package com.gouiranlink.franois.gouiranlinkproject.Rendezvous;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.Homepage.HomeFragment2;
import com.gouiranlink.franois.gouiranlinkproject.Object.Comment;
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.CustomerBooking;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Resource;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.Recherche.Research2Fragment;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetCustomerProfile;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.PostRequest;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.PutRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

/**
 * Created by François on 03/02/2017.
 */

public class Recapitulatif extends Fragment {

    private String m_Text = "";

    private Professional professional;
    private Customer customer;
    private Resource resource;
    private ArrayList<Professional_Product> liste_prestations_selectionne;
    private int position_list_clique;
    private String[] recap;

    private String employe_selectionne;
    private String date,date2,horaire;


    private Research2Fragment.ResearchTask mAuthTask = null;
    private GetRequest getRequest;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            customer = (Customer)getArguments().getSerializable("Customer");
            resource = (Resource)getArguments().getSerializable("Resource");
            professional = (Professional)getArguments().getSerializable("Professionnal");
            recap = (String[])getArguments().getSerializable("recap");
            //position_list_clique = (int)getArguments().getInt("position_list_clique");
            liste_prestations_selectionne = (ArrayList<Professional_Product>)getArguments().getSerializable("liste_prestations_selectionne");
        }
        System.out.println("Maaaaaaaaaaaaaaaarche bien :"+professional.toString());
        System.out.println("Numero TELEPHONE :"+customer.getPhone());
        System.out.println("Resource Name :"+resource.getName());
        //

        for(int i=0;i<liste_prestations_selectionne.size();i++){
            System.out.println("RECAPITULATIF     : "+liste_prestations_selectionne.get(i).getName());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View v = inflater.inflate(R.layout.activity_recapitulatif, container, false);
        final View v = inflater.inflate(R.layout.fragment_recapitulatif2, container, false);
        final EditText recapitulatif_commentaire = (EditText) v.findViewById(R.id.recapitulatif_commentaire);


        final TextView horairedateprestation = (TextView) v.findViewById(R.id.horairedateprestation);
        final TextView duree = (TextView) v.findViewById(R.id.dureetotal);
        final TextView tarif = (TextView) v.findViewById(R.id.tariftotal);
        final TextView nomprestataire = (TextView) v.findViewById(R.id.nomprestataire);
        final TextView adresseprestataire = (TextView) v.findViewById(R.id.adresseprestataire);
        final TextView nomemploye = (TextView) v.findViewById(R.id.nomemploye);



        TextView textviewrecap = (TextView) v.findViewById(R.id.textview_recapitulatif);
        textviewrecap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v2) {
                recapitulatif_commentaire.setVisibility(true ? View.VISIBLE : View.GONE);
            }
        });

        LinearLayout complement_adresse = (LinearLayout) v.findViewById(R.id.complement_adresse);
        if(professional.getType().getName().contains("domicile")){
            complement_adresse.setVisibility(true ? View.VISIBLE : View.GONE);
            adresseprestataire.setText(customer.getAddress());
        }else{
            adresseprestataire.setText(recap[4]);
        }






        LinearLayout myRoot = (LinearLayout) v.findViewById(R.id.liste_service_recapitulatif);

        for(int i= 0;i<liste_prestations_selectionne.size();i++){
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.services_recapitulatif,null);
            TextView t = (TextView) view.findViewById(R.id.text_recap);
            t.setText(liste_prestations_selectionne.get(i).getName());
            myRoot.addView(view);
        }


        /*final EditText recapitulatif_commentaire = (EditText) v.findViewById(R.id.recapitulatif_commentaire);
        recapitulatif_commentaire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                recapitulatif_commentaire.setVisibility(true ? View.VISIBLE : View.GONE);
            }
        });*/






        /*TableLayout table = (TableLayout) v.findViewById(R.id.idTable);
        TableRow row;

        TextView tv1,tv2;
        row = new TableRow(getActivity());
        tv1 = new TextView(getActivity());
        tv2 = new TextView(getActivity());
        tv1.setText("Services");
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setGravity(Gravity.LEFT);
        tv1.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
        tv2.setText("Prix");
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setGravity(Gravity.RIGHT);
        tv2.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
        //tv1.setBackgroundResource(R.drawable.back);
        //tv2.setBackgroundResource(R.drawable.back);
        row.addView(tv1);
        row.addView(tv2);
        table.addView(row);

        for(int i=0;i<liste_prestations_selectionne.size();i++) {
            row = new TableRow(getActivity()); // création d'une nouvelle ligne

            tv1 = new TextView(getActivity()); // création cellule
            tv1.setText(liste_prestations_selectionne.get(i).getName()); // ajout du texte
            tv1.setGravity(Gravity.LEFT); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            //tv1.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tv1.setLayoutParams( new TableRow.LayoutParams(0,100,1));

            // idem 2ème cellule
            tv2 = new TextView(getActivity());
            tv2.setText(String.valueOf(liste_prestations_selectionne.get(i).getPrice())+liste_prestations_selectionne.get(i).getCurrency());
            tv2.setGravity(Gravity.RIGHT);
            //tv2.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tv2.setLayoutParams( new TableRow.LayoutParams(0,50,1));

            //tv1.setBackgroundResource(R.drawable.back);
            //tv2.setBackgroundResource(R.drawable.back);
            // ajout des cellules à la ligne
            row.addView(tv1);
            row.addView(tv2);
            // ajout de la ligne au tableau
            table.addView(row);
        }*/


        System.out.println("RECAPITULATIF : "+recap[0]+" "+recap[1]);
        System.out.println("RECAPITULATIF2 : "+recap[2]+" "+recap[3]);
        System.out.println("RECAPITULATIF3 : "+recap[4]+" "+recap[4]);
        System.out.println("RECAPITULATIF4 : "+recap[5]+" "+recap[6]);

        tarif.setText(recap[0]+recap[1]);
        duree.setText(recap[2]);
        nomprestataire.setText(professional.getShop_name());

        //horairedateprestation.setText(recap[5] + " - " + recap[6]);
        String[] part_5 = recap[5].split("////");


        //horairedateprestation.setText(part_5[0]+" "+part_5[1]+ " à "+part_5[2].substring(0,5));
        horairedateprestation.setText(part_5[0]+" "+part_5[1]+ " à "+ recap[6]);
        date=part_5[1];
        date2=recap[6];
        horaire=recap[2];


        nomemploye.setText(resource.getName());

        Button recapitulatif = (Button) v.findViewById(R.id.valider_reservation);
        recapitulatif.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //A DOMICILE SANS ADRESSE
                System.out.println("On arrete les betises : " + customer.getAddress() +" / "+ professional.getType().getName());
                if ((customer.getAddress().equals("null") || customer.getAddress().equals("")) && professional.getType().getName().contains("domicile")) {

                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                    builder2.setMessage("Erreur, vous devez d'abord renseigner votre adresse !").setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Entrez votre adresse !");

                            // Set up the input
                            final EditText input = new EditText(getActivity());
                            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                            //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            input.setInputType(InputType.TYPE_CLASS_TEXT);
                            builder.setView(input);

                            // Set up the buttons
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    m_Text = input.getText().toString();
                                    System.out.println("Puuuuuuuuut : "+m_Text);

                                    try{

                                        String json = "{\n" +
                                                "\"email\":\"" + customer.getEmail() + "\",\n" +
                                                "\"name\":\"" + customer.getName() + "\",\n" +
                                                "\"surname\":\"" + customer.getSurname() + "\",\n" +
                                                "\"address\":\"" + m_Text + "\""+
                                                "}\n";
                                        String resp;
                                        PutRequest putRequest = new PutRequest("https://www.gouiran-beaute.com/link/api/v1/customer/" + customer.getId() + "/", json, "Authorization", "Token " + customer.getToken());
                                        resp = putRequest.execute().get();
                                        System.out.println("Puuuuuuuuut : "+resp);
                                        //System.out.println("Puuuuuuuuut : "+city);
                                        //System.out.println("Puuuuuuuuut : "+birth_date);
                                        //System.out.println("Puuuuuuuuut : "+resp);
                                        //Toast.makeText(getApplicationContext(), json, Toast.LENGTH_LONG).show();
                                        //Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_LONG).show();
                                        Log.d("reponse", resp);
                                        customer = new GetCustomerProfile().getCustomerProfile(customer.getToken(), customer);
                                        //onCreateView(inflater, container, savedInstanceState);
                                        duree.setText(customer.getAddress());

                                    } catch (InterruptedException | ExecutionException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();

                        }
                    });
                    AlertDialog dialog = builder2.create();
                    dialog.show();

                }









                    //SANS NUMERO DE TELEPHONE
                    if (customer.getMobilephone().equals("null")) {

                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                        builder2.setMessage("Erreur, vous devez d'abord renseigner un numéro de téléphone !").setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Entrez votre numéro de téléphone portable !");

                                // Set up the input
                                final EditText input = new EditText(getActivity());
                                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                input.setInputType(InputType.TYPE_CLASS_PHONE);
                                builder.setView(input);

                                // Set up the buttons
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        m_Text = input.getText().toString();

                                        try {

                                    /*String json = "{\n" +
                                            "\"email\":\"" + customer.getEmail() + "\",\n" +
                                            "\"name\":\"" + customer.getName() + "\",\n" +
                                            "\"surname\":\"" + customer.getSurname() + "\",\n" +
                                            "\"birthday_date\":\"" + customer.getBirthday_date() + "\",\n" +
                                            "\"phone\":\"" + customer.getPhone() + "\",\n" +
                                            "\"gender\":\"" + customer.getGender() + "\",\n" +
                                            "\"mobilephone\":\"" + m_Text + "\",\n" +
                                            "\"country\":\"" + customer.getCountry() + "\",\n" +
                                            "\"city\":\"" + customer.getCity() + "\",\n" +
                                            "\"address\":\"" + customer.getAddress() + "\",\n" +
                                            //TODO "\"profession\":\"" + profession + "\",\n" +
                                            "\"post_code\":\"" + customer.getPost_code() + "\"\n" +
                                            "}\n";*/

                                            String json = "{\n" +
                                                    "\"email\":\"" + customer.getEmail() + "\",\n" +
                                                    "\"name\":\"" + customer.getName() + "\",\n" +
                                                    "\"surname\":\"" + customer.getSurname() + "\",\n" +
                                                    "\"mobilephone\":\"" + m_Text + "\"" +
                                                    "}\n";
                                            String resp;
                                            PutRequest putRequest = new PutRequest("https://www.gouiran-beaute.com/link/api/v1/customer/" + customer.getId() + "/", json, "Authorization", "Token " + customer.getToken());
                                            resp = putRequest.execute().get();
                                            System.out.println("Puuuuuuuuut : "+resp);
                                            //System.out.println("Puuuuuuuuut : "+birth_date);
                                            //System.out.println("Puuuuuuuuut : "+resp);
                                            //Toast.makeText(getApplicationContext(), json, Toast.LENGTH_LONG).show();
                                            //Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_LONG).show();
                                            Log.d("reponse", resp);
                                            customer = new GetCustomerProfile().getCustomerProfile(customer.getToken(), customer);
                                            //onCreateView(inflater, container, savedInstanceState);
                                        } catch (InterruptedException | ExecutionException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                builder.show();


                            }
                        });
                        AlertDialog dialog = builder2.create();
                        dialog.show();

                    }


                System.out.println("Pourquoi on passe : " + customer.getAddress() + "    " + customer.getMobilephone() + "     "+professional.getType().getName());
                    if(((!customer.getAddress().equals("") && !customer.getAddress().equals("null")) && professional.getType().getName().contains("domicile") && (!customer.getMobilephone().equals("null") && !customer.getMobilephone().equals("")))
                            || (!professional.getType().getName().contains("domicile") && (!customer.getMobilephone().equals("null") && !customer.getMobilephone().equals("")))){


                        // Perform action on click
                        //Toast.makeText(this,"Yes",Toast.LENGTH_SHORT).show();
                        //CamTestActivity cam = new CamTestActivity();
                        //startActivity(intent);


                        CustomerBooking booking = new CustomerBooking();
                        booking.setId(1000);
                        booking.setCreated_at("");
                        booking.setUpdated_at("");
                        booking.setBegin_date(recap[5]);
                        booking.setEnd_date(recap[5]);
                        booking.setPrice(Double.parseDouble(recap[0]));
                        booking.setCurrency(recap[1]);
                        booking.setConfirmed(true);
                        booking.setCancelled(false);
                        booking.setNo_show(false);
                        booking.setNo_show_comment("");
                        booking.setFirst_booking(false);
                        booking.setFirst_booking_comment("");
                        booking.setHome_address(professional.getAddress());
                        booking.setHome_post_code(professional.getPost_code());
                        booking.setHome_city(professional.getCity());
                        booking.setHome_country(professional.getCountry());
                        booking.setPhone(professional.getShop_phone());
                        booking.setProfessional(professional);


                        Comment comment = new Comment();


                        booking.setProfessional(professional);

                        ResearchTask researchTask = null;
                        try {
                            researchTask = new ResearchTask(booking, customer);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String ls2 = "";
                        ls2 = researchTask.getResponse();
                        System.out.println("Resultat recapitulatif : " + ls2);


                        addNotification();
                        //Retour à la homepage après avoir validé le rendez vous
                        Fragment fragment = null;
                        fragment = new HomeFragment2();
                        FragmentManager frgManager = getFragmentManager();
                        frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("tag").commit();

                    }

            }
        });

        return v;
    }











    /* Prend en paramètre une date au format DD/MM/YYYY et une heure au format HHhMM
    *  Renvoie dans string[0] Date et heure de début au format YYYY-MM-DDTHH:MM:SSZ
    *  Renvoie dans string[1] Date et heure de fin au format YYYY-MM-DDTHH:MM:SSZ
    */
    private String[] getDateInformations(String dateOld, String hourOld) {
        String dateConverted = "";
        String hourConverted = "";
        String endHourConverted = "";
        String[] parts;
        String number;
        String month;
        String year;
        String hour;
        String minute;
        String cpy;
        int durationMinute;
        int durationHour;
        String[] informations = new String[2];

        System.out.print("Ooooooooooooh : "+recap[2]);
        cpy = recap[2];
        durationHour = Integer.valueOf(cpy.substring(0, cpy.indexOf('h')));
        cpy = recap[2];
        cpy = cpy.substring(cpy.indexOf("h") + 1);
        durationMinute = Integer.valueOf(cpy.substring(0, cpy.indexOf("m")));
        parts = dateOld.split("/");
        Calendar c = Calendar.getInstance();
        number = String.format("%02d", Integer.parseInt(parts[0]));
        month = String.format("%02d", Integer.parseInt(parts[1]));
        year = String.format("%04d", Integer.parseInt(parts[2]));

        parts = hourOld.split("-");
        parts = parts[0].split("h");
        hour = String.format("%02d", Integer.parseInt(parts[0]));
        minute = String.format("%02d", Integer.parseInt(parts[1]));

        dateConverted = year + "-" + month + "-" + number;
        hourConverted = hour + ":" + minute + ":" + "00";


        informations[0] = dateConverted + "T" + hourConverted + "Z";
        hour = String.format("%02d", Integer.parseInt(hour) + durationHour);
        minute = String.format("%02d", Integer.parseInt(minute) + durationMinute);

        endHourConverted = hour + ":" + minute + ":" + "00";
        informations[1] = dateConverted + "T" + endHourConverted + "Z";

        return (informations);
    }


    public class ResearchTask extends AsyncTask<Void, Void, Boolean> {

        private String response = "";

        //private final String mQuery;
        //private final int mLimit;
        //private final String json;
        //private final GetRequest getRequest;
        //private final Boolean connected;

        //private final String mEmail;
        private String json,json2,res_json;
        private final PostRequest postRequest;
        //private String resp;
        String headerKey;
        String headerValue;

        //TODO ID prestation
        ResearchTask(CustomerBooking booking, Customer customer) throws ExecutionException, InterruptedException {
            //mEmail = email;
            //TODO Nombre de réservation
            String prestDetails = "";
            for (int i = 0; i < liste_prestations_selectionne.size(); i++) {
                GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional-product/" + String.valueOf(liste_prestations_selectionne.get(i).getId()) + "/");
                prestDetails += getRequest.execute().get();
                if (i + 1 < liste_prestations_selectionne.size())
                    prestDetails += ",\n";
            }
            //String[] informations = getDateInformations(recap[5], recap[6]);


            String s = date;
            String s2 = date2;

            String[] informations = new String[2];
            //informations[0] = "2017-05-27T12:00:00Z";
            //informations[1] = "2017-05-27T14:00:00Z";

            String[] separated = horaire.split("h");
            int heure = Integer.parseInt(date2.substring(0,2))+Integer.parseInt(separated[0]);
            int min = Integer.parseInt(date2.substring(3,5))+Integer.parseInt(separated[1].substring(0,2));



            int dureetotal = heure*60+min;
            //dureetotal = dureetotal + Integer.parseInt(horaire.substring(0,2))*60+Integer.parseInt(horaire.substring(3,4));
            int heuretotal = (int) dureetotal/60;
            int minutetotal = (int) dureetotal%60;
            String heuret = String.valueOf(heuretotal);
            String minutet = String.valueOf(minutetotal);


            informations[0] = date.substring(6,10)+"-"+date.substring(3,5)+"-"+date.substring(0,2)+"T"+date2.substring(0,2)+":"+date2.substring(3,5)+":00Z";
            if(minutetotal<10){
                minutet = new String("0"+String.valueOf(minutetotal));
            }
            if(heuretotal<10){
                heuret = new String("0"+String.valueOf(heuretotal));
            }
                informations[1] = date.substring(6, 10) + "-" + date.substring(3, 5) + "-" + date.substring(0, 2) + "T" + heuret + ":" + minutet + ":00Z";

            System.out.println("DEBUT RESOURCE");
            System.out.println("DATE :::::: "+informations[0]);
            System.out.println("DATE :::::: "+informations[1]);
            System.out.println("FIN RESOURCE");

            System.out.println("DEBUT RESOURCE");
            ressource_jsonparser(recherche_ressource(String.valueOf(professional.getId())));
            System.out.println("DEBUT EMPLOYE SELECTIONNE" + employe_selectionne);
            System.out.println("FIN RESOURCE");




            json = "{\n" +

                    "\"begin_date\":\"" + informations[0] + "\"," +
                    "\"end_date\":\"" + informations[1] + "\"," +
                    "\"phone\":\"" + customer.getMobilephone() + "\"," +
                    "\"resource\":" + employe_selectionne + "\n," +
                    "\"products\":[\n" + prestDetails +
                    "]\n" +
                    "}\n";

            res_json = "{\n" +
                    "\"id\":" + resource.getId() + "," +
                    "\"type\":" + "{" +
                    "\"id\":" + resource.getType().getId() + "," +
                    "\"name\": \"" + resource.getType().getName() + "\"" +
                    "}," +
                    "\"surname\":\"" + resource.getSurname() + "\""+
                    "}\n";

            json2 = "{\n" +
                    "\"begin_date\":\"" + informations[0] + "\"," +
                    "\"end_date\":\"" + informations[1] + "\"," +
                    "\"phone\":\"" + customer.getMobilephone() + "\"," +
                    "\"resource\":" + res_json + "\n," +
                    "\"products\":[\n" + prestDetails +
                    "]\n" +
                    "}\n";

            /*json = "{\n" +

                    "\"begin_date\":\"" + informations[0] + "\"," +
                    "\"end_date\":\"" + informations[1] + "\"," +
                    "\"phone\":\"" + customer.getMobilephone() + "\"," +
                    "\"products\":[\n" + prestDetails +
                    "],\n" +
                    "\"resource\":" + employe_selectionne  +
                    "}\n";*/


            //CElui la c'est le bon, c'est lui qui fonctionne
            /*json = "{\n" +

                    "\"begin_date\":\"" + informations[0] + "\"," +
                    "\"end_date\":\"" + informations[1] + "\"," +
                    "\"phone\":\"" + customer.getMobilephone() + "\"," +
                    "\"products\":[\n" + prestDetails +
                    "]\n" +
                    "}\n";*/


            headerKey = "Authorization";
            System.out.println("bigJson=" + json2);
            headerValue = "Token " + String.valueOf(customer.getToken());
            postRequest = new PostRequest("https://www.gouiran-beaute.com/link/api/v1/booking/customer/professional/" + String.valueOf(customer.getId()) + "/" + String.valueOf(professional.getId()) + "/", json2, headerKey, headerValue);
            String resp = null;
            try {
                resp = postRequest.execute().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            response = resp;
            System.out.println("POSTTTTT=" + resp);
        }

        String getResponse() {
            return response;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            //return (true);

            //return (connected);
            return true;
/*            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;*/
        }


        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            //Toast.makeText(getContext(), "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();

            if (success) {
                //Intent i = new Intent();
                //Bundle b = new Bundle();
                //b.putString("token_access", "token_access");
                //b.putString("email", mEmail);
                //i.putExtras(b);
                //startActivity(i);
                //finish();
            } else {
                //mPasswordView.setError(getString(R.string.error_incorrect_password));
                //mPasswordView.requestFocus();
            }
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }


    }









    private void addNotification() {
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        //if(professional.getautomatic_booking_confirmation = true);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.icon_gouiranlink_contour)
                        .setContentTitle("Votre demande de réservation")
                        .setContentText("Votre demande a bien été pris en compte, elle sera validée dans les 24h.");


        Intent notificationIntent = new Intent(getActivity(), Recapitulatif.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), m, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(m, builder.build());
    }



    //RESSOURCE
    public String recherche_ressource(String query) {
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee Resource");
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/resource/professional/"+query+"/");
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee Resource");
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


    public void ressource_jsonparser(String jsonStr) {
        if(jsonStr.equals("[]")){

        }else {
            try {

                String recup_schedule = "{" + '"' + "data" + '"' + " : [{";
                String schedule1 = jsonStr.replace("[{", recup_schedule);
                String schedule2 = schedule1.replace("}]", "}]}");

                if (!jsonStr.equals("[]")) {
                    JSONObject jsonObj = new JSONObject(schedule2);
                    //JSONArray contacts = jsonObj.getJSONArray("data");
                    JSONArray Professional_resource = jsonObj.getJSONArray("data");
                    System.out.println("AHBON3 :" + jsonStr);

                    for (int j = 0; j < Professional_resource.length(); j++) {
                        JSONObject p2 = Professional_resource.getJSONObject(j);
                        String id = p2.getString("id");
                        System.out.println("IDENTIFIANT BORDEL : "+ id+ "      " + resource.getId());

                        if(id.equals(String.valueOf(resource.getId()))){
                            employe_selectionne = p2.toString();
                        }
                    }
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }

    }






}
