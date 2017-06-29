package com.gouiranlink.franois.gouiranlinkproject.Reservation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.DeleteRequest;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by François on 12/06/2017.
 */

public class DetailRdv extends Fragment {

    Reservation reser;
    String fragment_precedent;
    private Customer customer;


    private ArrayList<String> id_commentaires_deja_fait;

    public static DetailRdv newInstance() {
        DetailRdv f = new DetailRdv();
        return f;
    }


    public DetailRdv(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            reser = (Reservation) getArguments().getSerializable("Reservation");
            fragment_precedent = (String) getArguments().getString("Fragment");
            customer = (Customer) getArguments().getSerializable("Customer");
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_monrdv, container, false);
        final TextView text_nom_prestataire = (TextView) v.findViewById(R.id.monrdv_nomprestataire);
        final TextView text_daterdv = (TextView) v.findViewById(R.id.monrdv_daterdv);
        final TextView text_adresse = (TextView) v.findViewById(R.id.monrdv_adresse);
        final TextView list_prest = (TextView) v.findViewById(R.id.listedetails);

        ImageView annuler = (ImageView) v.findViewById(R.id.annuler);
        ImageView commentaire = (ImageView) v.findViewById(R.id.commentaire);

        LinearLayout upcoming = (LinearLayout) v.findViewById(R.id.validation_pro);
        LinearLayout done = (LinearLayout) v.findViewById(R.id.ecrire_un_avis);

        if(fragment_precedent.equals("UpcomingFragment")){
            upcoming.setVisibility(true ? View.VISIBLE : View.GONE);
            done.setVisibility(true ? View.GONE : View.VISIBLE);
        }else if(fragment_precedent.equals("DoneFragment")){
            upcoming.setVisibility(true ? View.GONE : View.VISIBLE);
            done.setVisibility(true ? View.VISIBLE : View.GONE);

            traitementcommentaire(recupcommentaire(String.valueOf(customer.getId())));
            boolean trouve = false;
            if(reser.boolean_commentaires_date_ok.equals(true)){
                for(int i =0;i<id_commentaires_deja_fait.size();i++){

                    if(id_commentaires_deja_fait.get(i).equals(reser.id)){
                        trouve = true;
                    }
                }
                if(trouve)
                    v.findViewById(R.id.commentaire).setVisibility(View.GONE);
                else
                    v.findViewById(R.id.commentaire).setVisibility(View.VISIBLE);
            }else{
                v.findViewById(R.id.commentaire).setVisibility(View.GONE);
            }


        }


        text_nom_prestataire.setText(reser.getInstitute());
        text_daterdv.setText(reser.getDate() + " à "+ reser.getHour());
        text_adresse.setText(reser.getAdress());

        String s = "";
        for(int i = 0; i<reser.products.size();i++){
            s = s + " - " + reser.products.get(i) +" \n";
        }
        list_prest.setText(s);


        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new AlertDialog.Builder(getContext())
                        .setTitle("Confirmation")
                        .setMessage("Voulez-vous supprimer le RdV ?")
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog2, int which) {
                                deleteReservationList(reser.id);
                                Fragment fragment = null;
                                Bundle args = new Bundle();
                                args.putSerializable("Customer", customer);
                                args.putSerializable("Retour", fragment_precedent);
                                fragment = new Reservation2Fragment();
                                fragment.setArguments(args);
                                fragment.setArguments(args);
                                FragmentManager frgManager = getFragmentManager();
                                frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("tag").commit();
                            }
                        })
                        .create();
                d.show();
            }
        });





        commentaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog_commentaire = new Dialog(getContext());
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                final View contentView2 = layoutInflater.inflate(R.layout.comment_popup, null);
                System.out.println("OOOooooooooooooh id :" + customer.getId());
                final LinearLayout root = (LinearLayout) contentView2.findViewById(R.id.Layout_comment);
                //recupcommentaire(String.valueOf(customer.getId()));

                dialog_commentaire.setContentView(root);
                dialog_commentaire.show();

                contentView2.findViewById(R.id.comment_valid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText edit = (EditText)contentView2.findViewById(R.id.description_comment);
                        String s = edit.getText().toString();
                        faire_un_commentaire(String.valueOf(customer.getId()),s);

                        dialog_commentaire.cancel();
                    }
                });

            }
        });
















        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Fragment fragment = null;
                    Bundle args = new Bundle();
                    args.putSerializable("Customer", customer);
                    args.putSerializable("Retour", fragment_precedent);
                    fragment = new Reservation2Fragment();
                    fragment.setArguments(args);
                    fragment.setArguments(args);
                    FragmentManager frgManager = getFragmentManager();
                    frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    //frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("tag").commit();

                }
                return true;
            }
        });




        return v;
    }






    private void deleteReservationList(String id) {
        String headerKey;
        String headerValue;
        String resp;

        headerKey = "Authorization";
        headerValue = "Token " + String.valueOf(customer.getToken());
        System.out.println("headerValue = Token " + String.valueOf(customer.getToken()));
        DeleteRequest getRequest = new DeleteRequest("https://www.gouiran-beaute.com/link/api/v1/booking/" + id + "/",String.valueOf(customer.getId()), headerKey, headerValue);
        try {
            resp = getRequest.execute().get();
            System.out.println("DELETE : " + resp);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }







    private String recupcommentaire(String id) {
        String headerKey;
        String headerValue;
        String resp = "";

        headerKey = "Authorization";
        headerValue = "Token " + String.valueOf(customer.getToken());
        System.out.println("headerValue = Token " + String.valueOf(customer.getToken()));
        GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/comment/customer/"+id+ "/", headerKey, headerValue);
        try {
            resp = getRequest.execute().get();
            System.out.println("GET : " + resp);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }


    public void traitementcommentaire(String jsonStr){
        try {
            if (!jsonStr.equals("[]")) {
                JSONObject jsonObj = null;
                jsonObj = new JSONObject(jsonStr);

                //JSONArray contacts = jsonObj.getJSONArray("data");
                JSONArray Customer_comment = jsonObj.getJSONArray("data");

                System.out.println("AHBON3 :" + jsonStr);


                //Shop_image = new ArrayList<>();
                //ProfessionnalGenericSchedule = new Professional_Schedule[Professional_Schedule.length()];
                id_commentaires_deja_fait = new ArrayList<>(Customer_comment.length());
                for (int j = 0; j < Customer_comment.length(); j++) {
                    JSONObject p2 = Customer_comment.getJSONObject(j);
                    String id = p2.getJSONObject("booking").getString("id");
                    String begin_date = p2.getJSONObject("booking").getString("begin_date");
                    String price = p2.getJSONObject("booking").getString("price");
                    System.out.println("Oooooooooooooooooooooooooooooooooooh :" + id +"      "+ begin_date+"     " +price);
                    id_commentaires_deja_fait.add(id);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    private void faire_un_commentaire(String id,String text) {
        String headerKey;
        String headerValue;
        String resp;
        String json,json2;

            String format = "dd/MM/yyyy HH:mm:ss.SS";

        Calendar cal = Calendar.getInstance();
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
        String strDate = formater.format(cal.getTime());
        java.util.Date date = new java.util.Date();
        System.out.println("Pas poli : "+strDate);
        System.out.println("Pas poli2 : "+date);
        String jour1=formater.format(date).toString().substring(0,2);
        String mois1=formater.format(date).toString().substring(3,5);
        String annee1=formater.format(date).toString().substring(6,10);



        System.out.println("Pas poli : " + annee1+"/"+mois1+"/"+jour1+"T"+formater.format(date).toString().substring(11,19) + "Z");
        String date2 = annee1+"/"+mois1+"/"+jour1+"T"+formater.format(date).toString().substring(11,19) + "Z";
        System.out.println("Pas poli2 : "+ date2);

        json2 = "{\n" +
                "\"id\":\"" + String.valueOf(customer.getId()) + "\"," +
                "\"name\":\"" + customer.getName() + "\"," +
                "\"surname\":\"" + customer.getSurname() + "\""+
                "}\n";


        json = "{\n" +
                "\"booking\":{\"" +
                "begin_date\":\"" + date2 + "\"," +
                "\"products\":\n" + reser.products_json +
                "," +
                "\"grade\":" + 5 + "," +
                "\"text\":\"" + text + "\"," +
                "\"customer\":" + json2 +
                "}\n"+"}";


        System.out.println("JSOOOOOOOOOOOOOOn :"+json);
        headerKey = "Authorization";
        headerValue = "Token " + String.valueOf(customer.getToken());
        System.out.println("headerValue = Token " + String.valueOf(customer.getToken()));
        PostRequest postRequest = new PostRequest("https://www.gouiran-beaute.com/link/api/v1/comment/customer/"+id+ "/", json, headerKey, headerValue);
        try {
            resp = postRequest.execute().get();
            System.out.println("POST : " + resp);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }





}
