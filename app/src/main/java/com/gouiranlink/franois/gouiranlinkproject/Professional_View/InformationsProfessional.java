package com.gouiranlink.franois.gouiranlinkproject.Professional_View;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.vision.text.Text;
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.PatchRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by François on 01/02/2017.
 */

public class InformationsProfessional extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    private Professional professional;
    private Customer customer;

    public InformationsProfessional(){
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            professional = (Professional)getArguments().getSerializable("Professionnal");
            customer = (Customer)getArguments().getSerializable("Customer");
        }
        System.out.println("Maaaaaaaaaaaaaaaarche bien :"+professional.toString());
        //
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_informations_professional, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                //googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng position_prestataire = new LatLng(professional.getGeoloc_latitude(), professional.getGeoloc_longitude());
                googleMap.addMarker(new MarkerOptions().position(position_prestataire).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(position_prestataire).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });



        TextView shop_name=(TextView) view.findViewById(R.id.shop_name);
        shop_name.setText(professional.getShop_name());

        TextView address=(TextView) view.findViewById(R.id.address);
        address.setText(professional.getAddress());

        TextView post_code_city=(TextView) view.findViewById(R.id.post_code_city);
        String code_postale_ville = professional.getPost_code()+" - "+professional.getCity();
        post_code_city.setText(code_postale_ville);


        TextView horaires=(TextView) view.findViewById(R.id.horaires);
        String planning = "";
        String date ="";
        String horaire_debut = "";
        String horaire_fin = "";

        if(professional.getSchedule()!=null) {
            for (int i = 0; i < professional.getSchedule().length; i++) {
                switch (professional.getSchedule()[i].getWeekday()) {
                    case 1:
                        date = "Lundi";
                        break;
                    case 2:
                        date = "Mardi";
                        break;
                    case 3:
                        date = "Mercredi";
                        break;
                    case 4:
                        date = "Jeudi";
                        break;
                    case 5:
                        date = "Vendredi";
                        break;
                    case 6:
                        date = "Samedi";
                        break;
                    case 7:
                        date = "Dimanche";
                        break;
                    default:
                        break;
                }
                horaire_debut = professional.getSchedule()[i].getBegin_time();
                horaire_fin = professional.getSchedule()[i].getEnd_time();

                planning = planning + date + "\n" + horaire_debut + " - " + horaire_fin + "\n";
            }
        }else{
            planning = "Horaires indisponible";
        }

        horaires.setText(planning);




        //Button shop_phone=(Button) view.findViewById(R.id.shop_phone);
        //shop_phone.setText(professional.getShop_phone());
        view.findViewById(R.id.shop_phone).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //String n = "tel:0618757417";
                //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(n));
                //startActivity(intent);
                String n = "tel:"+professional.getShop_phone();
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                call.setData(Uri.parse(n));
                getActivity().startActivity(call);
            }
        });


        //Récupérer l'adresse email de l'utilisateur
        //Button shop_email=(Button) view.findViewById(R.id.shop_email);
        //shop_email.setText(professional.getShop_email());
        view.findViewById(R.id.shop_email).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("text/plain");
                //email.putExtra(android.content.Intent.EXTRA_EMAIL,"meynardfrancois@gmail.com");
                email.putExtra(Intent.EXTRA_SUBJECT, "Contact");
                email.putExtra(Intent.EXTRA_TEXT, "Saisir votre demande ici...");
                email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(email, "Choisir le logiciel"));

                /*GMailSender sender = new GMailSender(userid.getText().toString(), password.getText().toString());
                try {
                    sender.sendMail(subject, body, from, to);
                }
                catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }*/
            }
        });

        TextView shop_description=(TextView) view.findViewById(R.id.shop_description);
        shop_description.setText(professional.getShop_description());

        //final ImageView tx =  (ImageView) view.findViewById(R.id.text_grandi);
        final ImageView im =  (ImageView) view.findViewById(R.id.favorites_info_prof);
        List<Data> listdata = retrieveRequestInformations();
        boolean deja_favoris = false;
        for(int j= 0; j<listdata.size();j++){
            if(listdata.get(j).id.equals(professional.getId())){
                deja_favoris = true;
            }
        }

        if(deja_favoris){
            Drawable monDrawable = getResources().getDrawable(R.drawable.ic_coeur2);
            im.setImageDrawable(monDrawable);

        }else{
            Drawable monDrawable = getResources().getDrawable(R.drawable.ic_coeur1);
            im.setImageDrawable(monDrawable);
        }

        final boolean[] premiere_fois = {true};
        final boolean[] deja_favoris2 = {deja_favoris};
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.anim1));
                System.out.println("On a appuyééééééééééééééééééééé");
                //v.setBackgroundResource(R.drawable.ic_coeur2);
                System.out.println("premiere_fois[0] :"+premiere_fois[0]);
                if(!premiere_fois[0]){
                    List<Data> listdata2 = retrieveRequestInformations();
                    boolean deja_favoris3 = false;
                    for(int j= 0; j<listdata2.size();j++){
                        if(listdata2.get(j).id.equals(professional.getId())){
                            deja_favoris3 = true;
                        }
                    }
                    System.out.println("deja_favoris3 :"+deja_favoris3);
                    deja_favoris2[0] = deja_favoris3;

                }
                premiere_fois[0] = false;
                System.out.println("deja_favoris2 :"+deja_favoris2[0]);
                if(!deja_favoris2[0]) {
                    System.out.println("1");
                    /*for (int i = 0; i < 10; i++) {
                        try {*/
                            Drawable monDrawable = getResources().getDrawable(R.drawable.ic_coeur2);
                            //monDrawable.setAlpha(i * 10);
                            im.setImageDrawable(monDrawable);
                            /*Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }*/
                    PatchReservationList(String.valueOf(professional.getId()));
                }else{
                    System.out.println("2");
                        Drawable monDrawable = getResources().getDrawable(R.drawable.ic_coeur1);
                        im.setImageDrawable(monDrawable);
                    PatchReservationList(String.valueOf(professional.getId()));
                }

                /*for(int i=0;i<500;i++){
                    ViewGroup.LayoutParams params = im.getLayoutParams();
                    //LinearLayout.LayoutParams params = tx.getLayoutParams();
                    params. = tx.getWidth()+i;
                    tx.setLayoutParams(params);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }*/

            }
        });

        return view;
    }






    private void PatchReservationList(String id) {
        String headerKey;
        String headerValue;
        String resp;

        headerKey = "Authorization";
        headerValue = "Token " + String.valueOf(customer.getToken());
        System.out.println("headerValue = Token " + String.valueOf(customer.getToken()));
        PatchRequest getRequest = new PatchRequest("https://www.gouiran-beaute.com/link/api/v1/professional/favoris/" + id + "/",String.valueOf(customer.getId()), headerKey, headerValue);
        try {
            resp = getRequest.execute().get();
            System.out.println("Patch : " + resp);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    private class Data {
        String shop_image;
        String name;
        Integer id;
        List<String> universes;


        public Data() {
            this.shop_image = "";
            this.name = "";
            this.id = -1;
            universes = new ArrayList<String>();
        }
    }

    private List<Data> retrieveRequestInformations() {
        List<Data> datas = new ArrayList<Data>();
        String headerKey;
        String headerValue;
        String resp;

        headerKey = "Authorization";
        headerValue = "Token " + String.valueOf(customer.getToken());
        GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/customer/favoris/customer/" + String.valueOf(customer.getId()) + "/", headerKey, headerValue);
        try {
            resp = getRequest.execute().get();
            Toast.makeText(getActivity(), "CRUSHED", Toast.LENGTH_SHORT).show();
            JSONObject jsonObject = new JSONObject(resp);
            JSONArray arr = jsonObject.getJSONArray("data");
            for (int i = 0; i < arr.length(); i++) {
                Data data = new Data();
                if (arr.getJSONObject(i).has("shop_name") && !arr.getJSONObject(i).isNull("shop_name")) {
                    data.name = arr.getJSONObject(i).getString("shop_name");
                    Log.d("TOTOTATA", data.name);
                }
                if (arr.getJSONObject(i).has("id"))
                    data.id = arr.getJSONObject(i).getInt("id");

                for (int j = 0; j < arr.getJSONObject(i).getJSONArray("shop_images").length(); j++) {
                    if (arr.getJSONObject(i).getJSONArray("shop_images").getJSONObject(j).getJSONObject("image").getJSONObject("thumbnails").getJSONObject("standard").getString("url") != null) {
                        data.shop_image = arr.getJSONObject(i).getJSONArray("shop_images").getJSONObject(j).getJSONObject("image").getJSONObject("thumbnails").getJSONObject("standard").getString("url");
                    }
                }
                for (int j = 0; j < arr.getJSONObject(i).getJSONArray("product_categories").length(); j++) {
                    if (arr.getJSONObject(i).getJSONArray("product_categories").getJSONObject(j).getString("name") != null)
                        data.universes.add(arr.getJSONObject(i).getJSONArray("product_categories").getJSONObject(j).getString("name"));
                }
                datas.add(data);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return (datas);
    }


}

