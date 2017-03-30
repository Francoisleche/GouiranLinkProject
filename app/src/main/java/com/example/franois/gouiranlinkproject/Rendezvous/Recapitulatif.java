package com.example.franois.gouiranlinkproject.Rendezvous;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.CamTestActivity;
import com.example.franois.gouiranlinkproject.Object.Booking;
import com.example.franois.gouiranlinkproject.Object.Comment;
import com.example.franois.gouiranlinkproject.Object.Customer;
import com.example.franois.gouiranlinkproject.Object.CustomerBooking;
import com.example.franois.gouiranlinkproject.Object.Professional;
import com.example.franois.gouiranlinkproject.Object.Professional_Customer;
import com.example.franois.gouiranlinkproject.Object.Professional_Product;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.Recherche.ResearchFragment;
import com.example.franois.gouiranlinkproject.Reservation.ReservationFragment;
import com.example.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.example.franois.gouiranlinkproject.ToolsClasses.PostRequest;

import java.util.concurrent.ExecutionException;

/**
 * Created by François on 03/02/2017.
 */

public class Recapitulatif extends Fragment {

    private Professional professional;
    private Customer customer;
    private Professional_Product[] liste_prestations_selectionne;
    private int position_list_clique;
    private String[] recap;


    private ResearchFragment.ResearchTask mAuthTask = null;
    private GetRequest getRequest;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            customer = (Customer)getArguments().getSerializable("Customer");
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



        final TextView horairedateprestation = (TextView) v.findViewById(R.id.horairedateprestation);
        final TextView duree = (TextView) v.findViewById(R.id.dureetotal);
        final TextView tarif = (TextView) v.findViewById(R.id.tariftotal);
        final TextView nomprestataire = (TextView) v.findViewById(R.id.nomprestataire);
        final TextView adresseprestataire = (TextView) v.findViewById(R.id.adresseprestataire);

        TableLayout table = (TableLayout) v.findViewById(R.id.idTable);
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

        for(int i=0;i<liste_prestations_selectionne.length;i++) {
            row = new TableRow(getActivity()); // création d'une nouvelle ligne

            tv1 = new TextView(getActivity()); // création cellule
            tv1.setText(liste_prestations_selectionne[i].getName()); // ajout du texte
            tv1.setGravity(Gravity.LEFT); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            //tv1.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tv1.setLayoutParams( new TableRow.LayoutParams(0,100,1));

            // idem 2ème cellule
            tv2 = new TextView(getActivity());
            tv2.setText(String.valueOf(liste_prestations_selectionne[i].getPrice())+liste_prestations_selectionne[i].getCurrency());
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
        }


        System.out.println("RECAPITULATIF : "+recap[0]+" "+recap[1]);

        tarif.setText(recap[0]+recap[1]);
        duree.setText(recap[2]);
        nomprestataire.setText(recap[3]);
        adresseprestataire.setText(recap[4]);
        horairedateprestation.setText(recap[5] + " - " + recap[6]);

        Button recapitulatif = (Button) v.findViewById(R.id.valider_reservation);
        recapitulatif.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Toast.makeText(this,"Yes",Toast.LENGTH_SHORT).show();
                //CamTestActivity cam = new CamTestActivity();
                //Intent intent = new Intent(getContext(), ReservationFragment.class);
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

                ResearchTask researchTask = new ResearchTask(booking,customer);
                String ls2 = "";
                ls2 = researchTask.getResponse();
                System.out.println("Resultat recapitulatif : "+ls2);

            }
        });

        return v;
    }


    public class ResearchTask extends AsyncTask<Void, Void, Boolean> {

        private String response = "";

        //private final String mQuery;
        //private final int mLimit;
        //private final String json;
        //private final GetRequest getRequest;
        //private final Boolean connected;

        //private final String mEmail;
        private String json;
        private final PostRequest postRequest;
        //private String resp;
        String headerKey;
        String headerValue;

        ResearchTask(CustomerBooking booking, Customer customer) {
            //mEmail = email;
            json = "{\n" +
                    "\"id\":\"" + booking.getId() + "\",\n" +
                    "\"created_at\":\"" + booking.getCreated_at() + "\",\n" +
                    "\"updated_at\":\"" + booking.getUpdated_at() + "\",\n" +
                    "\"begin_date\":\"" + booking.getBegin_date() + "\",\n" +
                    "\"end_date\":\"" + booking.getEnd_date() + "\",\n" +
                    "\"price\":\"" + booking.getPrice() + "\",\n" +
                    "\"currency\":\"" + booking.getCurrency() + "\",\n" +

                    "\"confirmed\":\"" + booking.isConfirmed() + "\",\n" +
                    "\"cancelled\":\"" + booking.isCancelled() + "\",\n" +
                    "\"no_show\":\"" + booking.isNo_show() + "\",\n" +
                    "\"no_show_comment\":\"" + booking.getNo_show_comment() + "\",\n" +
                    "\"first_booking\":\"" + booking.isFirst_booking() + "\",\n" +
                    "\"first_booking_comment\":\"" + booking.getFirst_booking_comment() + "\",\n" +

                    "\"home_address\":\"" + booking.getHome_address() + "\",\n" +
                    "\"home_post_code\":\"" + booking.getHome_post_code() + "\",\n" +
                    "\"home_city\":\"" + booking.getHome_city() + "\",\n" +
                    "\"home_country\":\"" + booking.getHome_country() + "\",\n" +
                    "\"observation\":\"" + booking.getObservation() + "\",\n" +
                    "\"phone\":\"" + booking.getPhone() + "\",\n" +

                    "\"products\":\"[{" +
            /*String s = "";
                    for(int i=0;i<booking.getProducts().length;i++) {
                        s = s + "\"id\":\"" + booking.getProducts()[i].getId() + "\",\n" +
                                "\"name\":\"" + booking.getProducts()[i].getName() + "\",\n" +
                                "\"price\":\"" + booking.getProducts()[i].getPrice() + "\",\n" +
                                "\"currency\":\"" + booking.getProducts()[i].getCurrency() + "\",\n" +
                                "\"duration\":\"" + booking.getProducts()[i].getDuration() + "\",\n" +
                                "\"description\":\"" + booking.getProducts()[i].getDescription() + "\",\n" +
                                "\"deleted_at\":\"" + booking.getProducts()[i].getDeleted_at() + "\",\n" +
                                "\"product\":\"" + null + "\",\n" +
                                "\"created_at\":\"" + booking.getProducts()[i].getCreated_at() + "\",\n" +
                                "\"updated_at\":\"" + booking.getProducts()[i].getUpdated_at() + "\",\n" +
                                "\"discounts\":\"" + "[]" + "\"},\n";
                    }
                        json = json.concat(s);

                        json = json + "],\"resource\":\"" + "{}" + "\",\n" + */
                         "}],\n " +
                    "\"resource\":\"" + "{}" + "\",\n" +
                                "\"parent\":\"" + "{}" + "\",\n" +




            //////////////////////////////Professionnal
            //String json = "{\n" +
              "\"professional\":\"{" +
                    "\"id\":\"" + booking.getProfessional().getId() + "\",\n" +
                    "\"acquisition\":\"{" + null + "}\",\n" +
                    "\"name\":\"" + booking.getProfessional().getShop_name() + "\",\n" +
                    "\"geoloc_latitude\":\"" + booking.getProfessional().getGeoloc_latitude() + "\",\n" +
                    "\"geoloc_longitude\":\"" + booking.getProfessional().getGeoloc_longitude() + "\",\n" +
                    "\"max_intervention_distance\":\"" + booking.getProfessional().getMax_intervention_distance() + "\",\n" +
                    "\"logo_image\":\"{}\",\n" +

                    "\"automatic_booking_confirmation\":\"" + booking.getProfessional().isAutomatic_booking_confirmation() + "\",\n" +
                    "\"customer_can_choose_resource_booking\":\"" + booking.getProfessional().isCustomer_can_choose_resource_booking() + "\",\n" +
                    "\"created_at\":\"" + booking.getProfessional().getCreated_at() + "\",\n" +
                    "\"updated_at\":\"" + booking.getProfessional().getUpdated_at() + "\",\n" +
                    "\"current_subscription_type\":\"{}\",\n" +

                    "\"notifications_preferences_sms\":\"" + booking.getProfessional().getNotification_preferences_sms() + "\",\n" +
                    "\"sms_happybirthday_enabled\":\"" + booking.getProfessional().isSms_happybirthday_enabled() + "\",\n" +
                    "\"sms_happybirthday_sender\":\"" + booking.getProfessional().getSms_happybirthday_sender() + "\",\n" +
                    "\"sms_happybirthday_content\":\"" + booking.getProfessional().getSms_happybirthday_content() + "\",\n" +
                    "\"sms_fidelity_enabled\":\"" + booking.getProfessional().isSms_fidelity_enabled() + "\",\n" +
                    "\"sms_fidelity_sender\":\"" + booking.getProfessional().getSms_fidelity_sender() + "\",\n" +
                    "\"sms_fidelity_content\":\"" + booking.getProfessional().getSms_fidelity_content() + "\",\n" +
                    "\"sms_remember_booking_enabled\":\"" + booking.getProfessional().isSms_remember_booking_enabled() + "\",\n" +
                    "\"discount_exclusivity\":\"{}\",\n" +
                    "\"preference_resource_type\":\"{}\",\n" +


                    "\"sponsoring_key\":\"" + booking.getProfessional().getSponsoring_key() + "\",\n" +
                    "\"show_discount\":\"{}\",\n" +
                    "\"shop_name\":\"" + booking.getProfessional().getShop_name() + "\",\n" +
                    "\"tags\":\"" + "[]" + "\",\n" +
                    "\"shop_description\":\"" + booking.getProfessional().getShop_description() + "\",\n" +
                    "\"specialty\":\"" + booking.getProfessional().getSpecialty() + "\",\n" +

                    "\"address\":\"" + booking.getProfessional().getAddress() + "\",\n" +
                    "\"post_code\":\"" + booking.getProfessional().getPost_code() + "\",\n" +
                    "\"city\":\"" + booking.getProfessional().getCity() + "\",\n" +
                    "\"country\":\"" + booking.getProfessional().getCountry() + "\",\n" +
                    "\"type\":\"" + "{" + null + "}" + "\",\n" +

                    "\"shop_phone\":\"" + booking.getProfessional().getShop_phone() + "\",\n" +
                    "\"shop_email\":\"" + booking.getProfessional().getShop_email() + "\",\n" +
                    "\"website_link\":\"" + booking.getProfessional().getWebsite_link() + "\",\n" +
                    "\"facebook_link\":\"" + booking.getProfessional().getFacebook_link() + "\",\n" +
                    "\"instagram_link\":\"" + booking.getProfessional().getInstagram_link() + "\",\n" +

                    "\"pinterest_link\":\"" + booking.getProfessional().getPinterest_link() + "\",\n" +
                    "\"product_categories\":\"" + "[{}]" + "\"\n" +

                    //PROFESSIONNAL PRODUCT"\"first_booking_comment\":\"" + booking.getFirst_booking_comment() + "\",\n" +
                    //RESOURCE"\"first_booking_comment\":\"" + booking.getFirst_booking_comment() + "\",\n" +
                    //BaseBooking PRODUCT"\"first_booking_comment\":\"" + booking.getFirst_booking_comment() + "\",\n" +
                    //Professional_Customer
                    //Comment

              "}\n,"+
              "\"comment\":\"" + "{}" + "\"\n";



            headerKey = "Authorization";
            headerValue = "Token " + String.valueOf(customer.getToken());
            postRequest = new PostRequest("https://www.gouiran-beaute.com/link/api/v1/booking/customer/professional/" + String.valueOf(customer.getId()) + "/" + String.valueOf(professional.getId()) + "/", json, headerKey, headerValue);
            String resp = null;
            try {
                resp = postRequest.execute().get();
                System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                System.out.println(resp);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            response = resp;
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

            Toast.makeText(getContext(), "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();

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



}
