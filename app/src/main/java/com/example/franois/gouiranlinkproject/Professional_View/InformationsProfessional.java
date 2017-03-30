package com.example.franois.gouiranlinkproject.Professional_View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.franois.gouiranlinkproject.Object.Professional;
import com.example.franois.gouiranlinkproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by François on 01/02/2017.
 */

public class InformationsProfessional extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    private Professional professional;

    public InformationsProfessional(){
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            professional = (Professional)getArguments().getSerializable("Professionnal");
        }
        System.out.println("Maaaaaaaaaaaaaaaarche bien :"+professional.toString());
        //
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_informations_professional, container, false);

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

        return view;
    }


}

