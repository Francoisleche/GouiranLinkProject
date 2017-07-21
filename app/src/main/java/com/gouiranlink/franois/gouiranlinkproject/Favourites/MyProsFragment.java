package com.gouiranlink.franois.gouiranlinkproject.Favourites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.Image_N;
import com.gouiranlink.franois.gouiranlinkproject.Object.Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Product_Category_Tag;
import com.gouiranlink.franois.gouiranlinkproject.Object.Product_Category_WithoutTree;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Schedule;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Subscription_Type;
import com.gouiranlink.franois.gouiranlinkproject.Object.Resource;
import com.gouiranlink.franois.gouiranlinkproject.Professional_View.InformationsProfessional;
import com.gouiranlink.franois.gouiranlinkproject.Professional_View.ProfessionalView;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_APPEND;

/*
TODO Change this fragment
 */

public class MyProsFragment extends Fragment implements OnMapReadyCallback {
    private String token;
    private Customer customer;
    private GetRequest getRequest;
    private Professional PremierProfessionnal = new Professional();
    private Product_Category_WithoutTree[] PremierProfessionnalProduits;
    private Product_Category_Tag[] PremierProfessionnalProduitsTag;
    private Professional_Schedule[] ProfessionnalGenericSchedule;
    private Professional_Product[] PremierProfessionalProduct;
    private Resource[] ResourceProfessional;
    private ArrayList<String> Shop_image;
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

    private FileOutputStream fileOutputStream = null;



    List<Data> datas;

    double latitude = 0;
    double longitude = 0;
    GoogleMap mMap;

    public MyProsFragment() {
        // Required empty public constructor
    }

    private class Data {
        String shop_image;
        String name;
        Integer id;


        public Data() {
            this.shop_image = "";
            this.name = "";
            this.id = -1;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("BONJOUR BONJOUR BONJOUR BONJOUR10");
        if (getArguments() != null) {
            customer = (Customer) getArguments().getSerializable("Customer");
            token = (String) getArguments().getString("token");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_pros,container, false);


        final FrameLayout pro = (FrameLayout) root.findViewById(R.id.pro);
        final FrameLayout coup_coeur = (FrameLayout) root.findViewById(R.id.coup_coeur);
        System.out.println("BONJOUR BONJOUR BONJOUR BONJOUR11");

        datas = retrieveRequestInformations();

        List<String> imagesUrl = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        List<String> ids = new ArrayList<String>();
        List<String> avis = new ArrayList<String>();
        List<String> favoris = new ArrayList<String>();

        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(String.valueOf(datas.get(i).id));
        }

        //Recuperation des avis et favoris
        for (int i = 0; i < datas.size(); i++) {

            ArrayList<String> tous_les_avis = avis_jsonparser(recherche_avis(ids.get(i)));
            double moyenne = 0;
            if (tous_les_avis.size() == 0) {

            } else {
                for (int y = 0; y < tous_les_avis.size(); y++) {
                    moyenne = moyenne + Double.parseDouble(tous_les_avis.get(y));
                }
                moyenne = moyenne / tous_les_avis.size();
            }
            String ss = String.format("%1$s", moyenne(String.valueOf(moyenne)));
            avis.add(String.valueOf(ss));

            favoris.add(recherche_favoris(ids.get(i)));

        }

        //final GridView gridview = (GridView) root.findViewById(R.id.gridviewPros);
        final ListView gridview = (ListView) root.findViewById(R.id.gridviewPros);
        int layout = R.layout.grid_single_pros;
        int id = R.id.grid_text;
        String[] items = new String[ids.size()];
        String[] items1 = new String[names.size()];
        String[] items2 = new String[imagesUrl.size()];
        for(int i=0;i<ids.size();i++){
            items[i] = String.valueOf(ids.get(i));
            items1[i] = String.valueOf(names.get(i));
            items2[i] = String.valueOf(imagesUrl.get(i));
        }

        /*final FavouritesImageAdapter favouritesImageAdapter = new FavouritesImageAdapter(getActivity(),items,items1,items2);
        gridview.setAdapter(favouritesImageAdapter);
        favouritesImageAdapter.setmThumbPictures(imagesUrl);
        favouritesImageAdapter.setmThumbNames(names);
        favouritesImageAdapter.setmIds(ids);*/

        RechercheResultatAdapter resultatAdapter = new RechercheResultatAdapter(getActivity());
        resultatAdapter.setId(ids);
        resultatAdapter.setInstitutesNames(names);
        resultatAdapter.setAvis(avis);
        resultatAdapter.setFavoris(favoris);
        resultatAdapter.setPictures(imagesUrl);

        gridview.setAdapter(resultatAdapter);





        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Fragment[] fragment = {null};
                final FragmentTransaction[] ft = {null};


                final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Svp Veuillez patienter ...", "chargement des données...", true);


                Thread timer = new Thread() {
                    @Override
                    public void run() {

                        try {

                            try {
                                fileOutputStream = getContext().openFileOutput("GouiranLink", MODE_APPEND);
                                //fileOutputStream.write(listView.getItemAtPosition(position).toString().getBytes());
                                fileOutputStream.write(position);
                                fileOutputStream.write("`".getBytes());
                                fileOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            //DEUXIEME METHODE
                            //Recupérer liste des produits
                            System.out.println("DEBUT DONNEE GENERALE");
                            System.out.println("D" + position);
                            //donneegeneral_jsonparser(recherche_donneegeneral(listView.getItemAtPosition(position).toString()));
                            donneegeneral_jsonparser(recherche_donneegeneral(String.valueOf(datas.get(position).id)));
                            String identifiant = String.valueOf(datas.get(position).id);

                            System.out.println("DEBUT PRODUCT");
                            System.out.println("D " + identifiant);
                            products_jsonparser(recherche_product(identifiant));
                            for (int i = 0; i < PremierProfessionalProduct.length; i++) {
                                System.out.println("PremierProfesionnelProduct : " + PremierProfessionalProduct[i].getName() + PremierProfessionalProduct[i].getPrice());
                            }

                            System.out.println("DEBUT SCHEDULE");
                            schedule_jsonparser(recherche_schedule(identifiant));
                            PremierProfessionnal.setSchedule(ProfessionnalGenericSchedule);
                            for (int i = 0; i < ProfessionnalGenericSchedule.length; i++) {
                                System.out.println("PremierProfesionnelSchedule : " + ProfessionnalGenericSchedule[i].getWeekday() + ProfessionnalGenericSchedule[i].getBegin_time());
                            }

                            System.out.println("DEBUT SHOP_IMAGE");
                            shop_image_jsonparser(recherche_shop_image(identifiant));
                            for (int i = 0; i < Shop_image.size(); i++) {
                                System.out.println("Shop_image : " + Shop_image.get(i));
                            }


                            System.out.println("DEBUT RESOURCE");
                            ressource_jsonparser(recherche_ressource(identifiant));
                            for (int i = 0; i < ResourceProfessional.length; i++) {
                                System.out.println("Resource : " + ResourceProfessional[i]);
                            }


                            if (PremierProfessionnal.getProfessional_subscription_type().getName().equals("Full")) {


                                Bundle args = new Bundle();
                                args.putSerializable("Professionnal", PremierProfessionnal);
                                args.putSerializable("ProfessionnalProduct", PremierProfessionalProduct);
                                args.putSerializable("Customer", customer);
                                args.putSerializable("ExpandableListDetail", expandableListDetail);
                                args.putSerializable("ResourceProfessional", ResourceProfessional);
                                args.putSerializable("Shop_image", Shop_image);
                                args.putSerializable("token", token);
                                System.out.println("CUSTOMER :" + customer.getName());
                                args.putSerializable("Fragment","MyProsFragment");

                                FragmentManager fm = getFragmentManager();
                                ft[0] = fm.beginTransaction();
                                fragment[0] = new ProfessionalView();
                                fragment[0].setArguments(args);
                            } else if (PremierProfessionnal.getProfessional_subscription_type().getName().equals("Free")) {
                                //Fragment fragment = null;
                                Bundle args = new Bundle();
                                args.putSerializable("Professionnal", PremierProfessionnal);
                                args.putSerializable("ProfessionnalProduct", PremierProfessionalProduct);
                                args.putSerializable("Customer", customer);
                                args.putSerializable("token", token);

                                FragmentManager fm = getFragmentManager();
                                //FragmentTransaction ft = fm.beginTransaction();
                                ft[0] = fm.beginTransaction();
                                //getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);

                                fragment[0] = new InformationsProfessional();
                                fragment[0].setArguments(args);

                            }


                            ft[0].replace(R.id.content_frame, fragment[0],"InformationsProfessionalTag").addToBackStack(null);
                            ft[0].setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            ft[0].commit();
                            ringProgressDialog.cancel();
                        } catch (Exception e) {
                            System.out.println("PASSSSSSSSSSSSSSSSSSSSSSSSSSSSE PAS");
                            e.printStackTrace();
                        }
                    }
                };
                timer.start();
                ringProgressDialog.setCancelable(true);

                //Obligé de faire ça après !
                System.out.println("On passsssssssssse !!!!!!!!");
                //getActivity().findViewById(R.id.fragment_favorites).setVisibility(View.GONE);






                //getActivity().findViewById(R.id.done1).setVisibility(View.GONE);
                //pro.setVisibility(true ? View.GONE : View.VISIBLE);
                //coup_coeur.setVisibility(true ? View.GONE : View.VISIBLE);
                //getActivity().findViewById(R.id.simpleFrameLayout).setVisibility(View.GONE);
            }
        });







        System.out.println("BONJOUR BONJOUR BONJOUR BONJOUR112");

        return (root);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        System.out.println("BONJOUR BONJOUR BONJOUR BONJOUR12");
    }








                /*final Dialog dialog = new Dialog(getContext());
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View contentView = layoutInflater.inflate(R.layout.pro_summary, null);
                final LinearLayout root = (LinearLayout) contentView.findViewById(R.id.proRootLayout);
                GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/" + String.valueOf(favouritesImageAdapter.getmIds().get(position) + "/"));
                try {
                    TextView textView = (TextView) root.findViewById(R.id.shop_name);
                    String resp = getRequest.execute().get();
                    JSONObject obj = new JSONObject(resp);
                    textView.setText(obj.getString("shop_name"));
                    textView = (TextView) root.findViewById(R.id.shop_address);
                    textView.setText(obj.getString("address"));
                    textView = (TextView) root.findViewById(R.id.shop_post_city);
                    textView.setText(obj.getString("city") + " - " + obj.getString("post_code"));
                    textView = (TextView) root.findViewById(R.id.shop_description);
                    textView.setText(obj.getString("shop_description"));
                    textView = (TextView) root.findViewById(R.id.schedule);
                    JSONArray arr = obj.getJSONArray("schedule");
                    String schedule = "";
                    for (int i = 0; i < arr.length(); i++) {
                        schedule += intToDay(arr.getJSONObject(i).getInt("weekday"));
                        schedule += " : ";
                        String begin_time = arr.getJSONObject(i).getString("begin_time");
                        schedule += begin_time.substring(0, Math.min(begin_time.length(), 2)) + "h" + begin_time.substring(3, 5);
                        schedule += " - ";
                        String end_time = arr.getJSONObject(i).getString("end_time");
                        schedule += end_time.substring(0, Math.min(end_time.length(), 2)) + "h" + end_time.substring(3, 5);
                        schedule += "\n";
                    }
                    textView.setText(schedule);
                    textView = (TextView) root.findViewById(R.id.unavailabilities);
                    arr = obj.getJSONArray("unavailabilities");
                    schedule = "";
                    for (int i = 0; i < arr.length(); i++) {
                        schedule += getDate(arr.getJSONObject(i).getString("begin_date"));
                        schedule += " - ";
                        schedule += getDate(arr.getJSONObject(i).getString("end_date"));
                        schedule += "\n";
                    }
                    textView.setText(schedule);
                    final String phoneNumber = obj.getString("shop_phone");
                    final String email = obj.getString("shop_email");
                    final Intent callIntent = new Intent(Intent.ACTION_CALL);
                    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    callIntent.setData(Uri.parse("tel:" + phoneNumber));

                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, email);

                    //Button mail = (Button) root.findViewById(R.id.mail);
                    root.findViewById(R.id.mail).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getContext().startActivity(Intent.createChooser(emailIntent, "Envoyer e-mail..."));
                        }
                    });

                    //Button call = (Button) root.findViewById(R.id.call);
                    root.findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getContext().startActivity(Intent.createChooser(callIntent, "Appeler..."));
                        }
                    });

                    latitude = obj.getDouble("geoloc_latitude");
                    longitude = obj.getDouble("geoloc_longitude");

                    SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(final GoogleMap googleMap) {
                            LatLng sydney = new LatLng(latitude, longitude);
                            googleMap.addMarker(new MarkerOptions()
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)).anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                                    .position(new LatLng(latitude, longitude)));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        }
                    });
                } catch (InterruptedException | ExecutionException | JSONException e) {
                    e.printStackTrace();
                }
                favouritesImageAdapter.getmIds().get(position);
                dialog.setContentView(root);
                dialog.show();*/





    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("BONJOUR BONJOUR BONJOUR BONJOUR13");
        mMap = googleMap;
        Toast.makeText(getActivity(), "MAP MARKED", Toast.LENGTH_SHORT).show();
        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin)).anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(latitude, longitude)));
        LatLng coordinate = new LatLng(latitude, longitude);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 11.0f);
        mMap.animateCamera(yourLocation);
    }

    private String getDate(String complete) {
        System.out.println("BONJOUR BONJOUR BONJOUR BONJOUR14");
        String completeDate;
        String day;
        String number;
        String month;
        String year;
        String[] parts;

        Log.d("DATE=", complete);
        completeDate = complete.substring(0, complete.indexOf("T"));
        Log.d("DATE=", completeDate);
        parts = completeDate.split("-");
        Calendar c = Calendar.getInstance();
        number = parts[2];
        month = parts[1];
        year = parts[0];
        c.setTime(new Date(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]), Integer.valueOf(parts[2])));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                day = "Lundi";
                break;
            case Calendar.TUESDAY:
                day = "Mardi";
                break;
            case Calendar.WEDNESDAY:
                day = "Mercredi";
                break;
            case Calendar.THURSDAY:
                day = "Jeudi";
                break;
            case Calendar.FRIDAY:
                day = "Vendredi";
                break;
            case Calendar.SATURDAY:
                day = "Samedi";
                break;
            case Calendar.SUNDAY:
                day = "Dimanche";
                break;
            default:
                day = "";
        }
        switch (Integer.valueOf(month)) {
            case 1:
                month = "Janvier";
                break;
            case 2:
                month = "Février";
                break;
            case 3:
                month = "Mars";
                break;
            case 4:
                month = "Avril";
                break;
            case 5:
                month = "Mai";
                break;
            case 6:
                month = "Juin";
                break;
            case 7:
                month = "Juillet";
                break;
            case 8:
                month = "Août";
                break;
            case 9:
                month = "Septembre";
                break;
            case 10:
                month = "Octobre";
                break;
            case 11:
                month = "Novembre";
                break;
            case 12:
                month = "Décembre";
                break;
            default:
                day = "";
        }
        return (day + " " + number + " " + month + " " + year);
    }

    String intToDay(int pos) {
        String[] day = new String[] {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        return (day[pos - 1]);
    }

    private List<Data> retrieveRequestInformations() {
        System.out.println("BONJOUR BONJOUR BONJOUR BONJOUR15");
        List<Data> datas = new ArrayList<Data>();
        String headerKey;
        String headerValue;
        String resp;

        headerKey = "Authorization";
        headerValue = "Token " + String.valueOf(customer.getToken());
        GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/booking/customer/" + String.valueOf(customer.getId()) + "/", headerKey, headerValue);
        try {
            resp = getRequest.execute().get();
            Log.d("RESPIRE", resp);
            JSONObject jsonObject = new JSONObject(resp);
            JSONArray arr = jsonObject.getJSONArray("data");
            final List<String> names = new ArrayList<String>();
            for (int i = 0; i < arr.length(); i++) {
                Data data = new Data();
                if (arr.getJSONObject(i).getJSONObject("professional").has("shop_name") && !arr.getJSONObject(i).getJSONObject("professional").isNull("shop_name")) {
                    if (!names.contains(arr.getJSONObject(i).getJSONObject("professional").getString("shop_name"))) {
                        if (arr.getJSONObject(i).getJSONObject("professional").has("id")){
                            data.id = arr.getJSONObject(i).getJSONObject("professional").getInt("id");
                            data.name = arr.getJSONObject(i).getJSONObject("professional").getString("shop_name");
                            names.add(arr.getJSONObject(i).getJSONObject("professional").getString("shop_name"));

                            System.out.println(arr.getJSONObject(i).getJSONObject("professional").getInt("id"));
                            System.out.println(arr.getJSONObject(i).getJSONObject("professional").getString("shop_name"));
                            System.out.println(arr.getJSONObject(i).getJSONObject("professional").getJSONObject("logo_image").getJSONObject("thumbnails").getJSONObject("standard").getString("url"));
                            Log.d("TITI", data.name);
                        }

                        if (arr.getJSONObject(i).getJSONObject("professional").getJSONObject("logo_image").getJSONObject("thumbnails").getJSONObject("standard").getString("url") != null)
                            data.shop_image = arr.getJSONObject(i).getJSONObject("professional").getJSONObject("logo_image").getJSONObject("thumbnails").getJSONObject("standard").getString("url");



                        datas.add(data);
                    }



                }

            }

            for(int y = 0; y<datas.size();y++){
                System.out.println("idddddddddddddddd : "+datas.get(y).id);
            }


        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return (datas);
    }



    //////////////////////////RECUPERATION DONNEES





            //SHOP IMAGE
            public String recherche_shop_image(String query) {
                System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee SCHEDULE");
                getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/shop-image/professional/"+query+"/");
                System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee SCHEDULE");
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


            public void shop_image_jsonparser(String jsonStr) {
                if(jsonStr.equals("[]")){
                    Shop_image = new ArrayList<>();
                }else {
                    try {

            /*String recup_schedule = "{" + '"' + "data" + '"' + " : [{";
            String schedule1 = jsonStr.replace("[{", recup_schedule);
            String schedule2 = schedule1.replace("}]", "}]}");*/

                        if (!jsonStr.equals("[]")) {
                            JSONObject jsonObj = new JSONObject(jsonStr);
                            //JSONArray contacts = jsonObj.getJSONArray("data");
                            JSONArray Professional_Shop_image = jsonObj.getJSONArray("data");

                            System.out.println("AHBON3 :" + jsonStr);


                            Shop_image = new ArrayList<>();
                            //ProfessionnalGenericSchedule = new Professional_Schedule[Professional_Schedule.length()];
                            for (int j = 0; j < Professional_Shop_image.length(); j++) {
                                //Professional_Schedule pro_schedule = new Professional_Schedule();
                                JSONObject p2 = Professional_Shop_image.getJSONObject(j);
                                String url = p2.getJSONObject("image").getString("url");
                                //String weekeday_schedule = p2.getString("weekday");
                                //String begin_time_schedule = p2.getString("begin_time");
                                //String end_time_schedule = p2.getString("end_time");

                                //Remplissage des horaires d'ouverture
                    /*pro_schedule.setId(Integer.parseInt(id_schedule));
                    pro_schedule.setWeekday(Integer.parseInt(weekeday_schedule));
                    pro_schedule.setBegin_time(begin_time_schedule);
                    pro_schedule.setEnd_time(end_time_schedule);
                    ProfessionnalGenericSchedule[j] = pro_schedule;*/

                                Shop_image.add(url);
                            }
                        }

                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                    }
                }

            }


            public ArrayList<String> shop_image_jsonparser2(String jsonStr) {
                ArrayList<String> tableau_image = new ArrayList<>();

                if(jsonStr.equals("[]")){
                    tableau_image = new ArrayList<>();
                }else {
                    try {

                        if (!jsonStr.equals("[]")) {
                            JSONObject jsonObj = new JSONObject(jsonStr);
                            JSONArray Professional_Shop_image = jsonObj.getJSONArray("data");

                            System.out.println("AHBON3 :" + jsonStr);


                            tableau_image = new ArrayList<>();
                            for (int j = 0; j < Professional_Shop_image.length(); j++) {
                                JSONObject p2 = Professional_Shop_image.getJSONObject(j);
                                String url = p2.getJSONObject("image").getString("url");

                                tableau_image.add(url);
                            }
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                    }
                }
                return tableau_image;
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
                    ResourceProfessional = new Resource[0];
                }else {
                    try {

                        String recup_schedule = "{" + '"' + "data" + '"' + " : [{";
                        String schedule1 = jsonStr.replace("[{", recup_schedule);
                        String schedule2 = schedule1.replace("}]", "}]}");

                        if (!jsonStr.equals("[]")) {
                            JSONObject jsonObj = new JSONObject(schedule2);
                            //JSONArray contacts = jsonObj.getJSONArray("data");
                            JSONArray Professional_Shop_image = jsonObj.getJSONArray("data");
                            System.out.println("AHBON3 :" + jsonStr);
                            ResourceProfessional = new Resource[Professional_Shop_image.length()];

                            for (int j = 0; j < Professional_Shop_image.length(); j++) {
                                Resource resource = new Resource();
                                JSONObject p2 = Professional_Shop_image.getJSONObject(j);
                                String id = p2.getString("id");
                                String type = p2.getJSONObject("type").getString("name");
                                String name = p2.getString("name");
                                String surname = p2.getString("surname");

                                resource.setId(Integer.parseInt(id));
                                resource.setName(name);
                                resource.setSurname(surname);

                                ResourceProfessional[j] = resource;
                            }
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                    }
                }

            }



            //RESSOURCE
            public String recherche_favoris(String query) {
                System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee Resource");
                getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/favoris/"+query+"/");
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






            public String recherche_avis(String query) {
                getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/comment/professional/"+query+"/");
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



            public ArrayList<String> avis_jsonparser(String str) {
                ArrayList<String> tableau_commentaire = new ArrayList<>();
                int count_commentaire = 0;
                try {

                    if (!str.equals("[]")) {
                        JSONObject jsonObj = new JSONObject(str);
                        JSONArray tab_comment = jsonObj.getJSONArray("data");


                        count_commentaire = tab_comment.length();


                        //Remplir la liste des commentaires
                        for (int j = 0; j < tab_comment.length(); j++) {
                            JSONObject p2 = tab_comment.getJSONObject(j);

                            String comment_grade = p2.getJSONObject("booking").getJSONObject("comment").getString("grade");
                            tableau_commentaire.add(comment_grade);
                        }
                    }else{
                        count_commentaire=0;
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }

                return tableau_commentaire;

            }


    public String recherche_product(String query) {
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional-product/professional/"+Integer.parseInt(query)+"/?query[show_all]=true");

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

    public ArrayList<Professional_Product> products_jsonparser(String jsonStr) {
        //Professional_Product product = new Professional_Product();
        ArrayList<Professional_Product> liste_product = new ArrayList<Professional_Product>();
        List<String> liste_coifure_femme = new ArrayList<String>();
        List<String> liste_bien_etre = new ArrayList<String>();
        List<String> liste_beaute = new ArrayList<String>();
        List<String> liste_homme = new ArrayList<String>();
        List<String> photo = new ArrayList<String>();

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray contacts = jsonObj.getJSONArray("data");

            PremierProfessionalProduct = new Professional_Product[contacts.length()];
            for (int i = 0; i < contacts.length(); i++) {
                Professional_Product PremierProfessionalProduct2 = new Professional_Product();
                Product product = new Product();
                Product_Category_WithoutTree product_category_withoutTree = new Product_Category_WithoutTree();
                JSONObject p2 = contacts.getJSONObject(i);
                String id_resource_manager = p2.getString("id");
                String name_resource_manager = p2.getString("name");
                String price_resource_manager = p2.getString("price");
                String currency_resource_manager = p2.getString("currency");

                String duration_resource_manager = p2.getString("duration");
                String description_resource_manager = p2.getString("description");

                String deleted_at_resource_manager = p2.getString("deleted_at");
                String created_at_resource_manager = p2.getString("created_at");
                String updated_at_resource_manager = p2.getString("updated_at");

                String product_resource_manager = p2.getString("product");
                String discounts_resource_manager = p2.getString("discounts");

                System.out.println("AFFICHEEEEEER au moins un truc");
                String discounts = "";
                System.out.println("AFFICHEEEEEER DISCOUNT :" + discounts_resource_manager);
                if(!discounts_resource_manager.equals("[]")){
                    JSONArray discounts_tableau = p2.getJSONArray("discounts");
                    discounts = discounts_tableau.getJSONObject(0).getString("amount");
                    System.out.println("AFFICHEEEEEER DISCOUNT :" + discounts);
                }



                //System.out.println("LES PRODUCT RESOURCE MANAGER PRODUCT :"+product_resource_manager);


                //Remplissage des produits
                PremierProfessionalProduct2.setId(Integer.parseInt(id_resource_manager));
                PremierProfessionalProduct2.setName(name_resource_manager);
                PremierProfessionalProduct2.setPrice(Double.parseDouble(price_resource_manager));
                PremierProfessionalProduct2.setCurrency(currency_resource_manager);
                PremierProfessionalProduct2.setDuration(duration_resource_manager);
                PremierProfessionalProduct2.setDescription(description_resource_manager);
                PremierProfessionalProduct2.setDescription(description_resource_manager);
                PremierProfessionalProduct2.setDeleted_at(deleted_at_resource_manager);
                PremierProfessionalProduct2.setCreated_at(created_at_resource_manager);
                PremierProfessionalProduct2.setUpdated_at(updated_at_resource_manager);


                System.out.println("LES PRODUCT RESOURCE MANAGER PRODUCT 2222 :" + product_resource_manager);
                JSONObject json2 = p2.getJSONObject("product");
                int product_id = (int) json2.get("id");
                String product_name = (String) json2.get("name");
                String description = (String) json2.get("description");

                JSONObject json3 = json2.getJSONObject("category");
                int product_category_id = (int) json3.get("id");
                //String product_category_name = (String) json3.get("name");
                String product_category_name = (String) json3.getJSONObject("parent").get("name");
                String product_category_created_at = (String) json3.get("created_at");
                String product_category_updated_at = (String) json3.get("updated_at");

                product_category_withoutTree.setId(product_category_id);
                product_category_withoutTree.setName(product_category_name);
                product_category_withoutTree.setCreated_at(product_category_created_at);
                product_category_withoutTree.setUpdated_at(product_category_updated_at);

                product.setId(product_id);
                product.setName(product_name);
                product.setCategory(product_category_withoutTree);

                PremierProfessionalProduct2.setProduct(product);

                PremierProfessionalProduct[i] = PremierProfessionalProduct2;


                // pourquoi //// ? parce que / est deja utiliser dans les noms de products
                if(product_category_name.equals("Coiffure Femme")){
                    liste_coifure_femme.add(product_name+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                }else if(product_category_name.equals("Bien-Être")){
                    liste_bien_etre.add(product_name+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                }else if(product_category_name.equals("Beauté")){
                    liste_beaute.add(product_name+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                }else if(product_category_name.equals("Homme")){
                    liste_homme.add(product_name+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                }


            }

            if(!liste_coifure_femme.isEmpty())
                expandableListDetail.put("Coiffure Femme", liste_coifure_femme);
            if(!liste_bien_etre.isEmpty())
                expandableListDetail.put("Bien-Être", liste_bien_etre);
            if(!liste_beaute.isEmpty())
                expandableListDetail.put("Beauté", liste_beaute);
            if(!liste_homme.isEmpty())
                expandableListDetail.put("Homme", liste_homme);
            expandableListDetail.put("PHOTOOOOOOOO TEAMS", photo);

        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

        return liste_product;
    }




    public String recherche_donneegeneral(String query) {
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/"+query+"/");

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


    public void donneegeneral_jsonparser(String jsonStr) {
        String ls = "";
        ArrayList<String> specialite = new ArrayList<>();
        ArrayList<String> premierprofessionel = new ArrayList<>();
        ArrayList<String> listeprestationpremierprofessionnel = new ArrayList<>();
        ArrayList<String> listelabelprestationpremierprofessionnel = new ArrayList<>();

        if (jsonStr != null) {
            if (jsonStr.contains("{")) {
                try {


                    //JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject c = new JSONObject(jsonStr);

                    //JSONArray contacts = jsonObj.getJSONArray("data");

                    // looping through All Contacts
                    //for (int i = 0; i < contacts.length(); i++) {
                    //JSONObject c = contacts.getJSONObject(i);

                    //String image = c.getString("shop_images");
                    String id_professional = c.getString("id");
                        /*JSONArray pictures = c.getJSONArray("shop_images");

                    Image_N image = new Image_N();
                    ArrayList<String[]> thumbnails = null;
                        String picture = "";
                    for(int i= 0;i<pictures.length();i++){
                        JSONObject p = pictures.getJSONObject(i);
                        if(i==0){
                            picture = p.getJSONObject("image").getString("url");
                        }


                }

                image.setUrl(picture);*/

                    //if (i == 0) {
                            /*String shop_images = c.getString("shop_images");
                            String resource_manager = c.getString("resource_manager");
                            String avg_grade = c.getString("avg_grade");
                            String comments_count = c.getString("comments_count");
                            premierprofessionel.add(shop_images);
                            premierprofessionel.add(resource_manager);
                            premierprofessionel.add(avg_grade);
                            premierprofessionel.add(comments_count);*/


                    String id = c.getString("id");
                    String acquisition = c.getString("acquisition");
                    String geoloc_latitude = c.getString("geoloc_latitude");
                    String geoloc_longitude = c.getString("geoloc_longitude");
                    premierprofessionel.add(id);
                    premierprofessionel.add(acquisition);
                    premierprofessionel.add(geoloc_latitude);
                    premierprofessionel.add(geoloc_longitude);

                    String max_intervention_distance = c.getString("max_intervention_distance");
                    String logo_image = c.getString("logo_image");
                    String url = "null";
                    if(c.getString("logo_image").equals("null")){
                        url = "null";
                    }else{
                        url = c.getJSONObject("logo_image").getString("url");
                    }

                    Image_N image = new Image_N();
                    ArrayList<String[]> thumbnails = null;
                    image.setUrl(url);


                    PremierProfessionnal.setLogo_image(image);


                    String automatic_booking_confirmation = c.getString("automatic_booking_confirmation");
                    String customer_can_choose_resource_booking = c.getString("customer_can_choose_resource_booking");
                    premierprofessionel.add(max_intervention_distance);
                    premierprofessionel.add(logo_image);
                    premierprofessionel.add(automatic_booking_confirmation);
                    premierprofessionel.add(customer_can_choose_resource_booking);

                    String created_at = c.getString("created_at");
                    String updated_at = c.getString("updated_at");
                    String current_subscription_type = c.getString("current_subscription_type");
                    String notifications_preferences_sms = c.getString("notifications_preferences_sms");
                    premierprofessionel.add(created_at);
                    premierprofessionel.add(updated_at);
                    premierprofessionel.add(current_subscription_type);
                    premierprofessionel.add(notifications_preferences_sms);


                    /////////////////SUBSCRIPTION ACCOUNT (FREE Ou PREMIUM)///////////////////
                    Professional_Subscription_Type professional_subscription_type = new Professional_Subscription_Type();
                    JSONObject jsonObj_resource_manager = new JSONObject(current_subscription_type);
                    int id_current_subscription_type = (int) jsonObj_resource_manager.get("id");
                    String name_current_subscription_type = (String) jsonObj_resource_manager.get("name");
                    professional_subscription_type.setId(id_current_subscription_type);
                    professional_subscription_type.setName(name_current_subscription_type);
                    PremierProfessionnal.setProfessional_subscription_type(professional_subscription_type);
                    /////////////////////////////////////////////////////////////////////////


                    String sms_happybirthday_enabled = c.getString("sms_happybirthday_enabled");
                    String sms_happybirthday_sender = c.getString("sms_happybirthday_sender");
                    String sms_happybirthday_content = c.getString("sms_happybirthday_content");
                    String sms_fidelity_enabled = c.getString("sms_fidelity_enabled");
                    String sms_fidelity_sender = c.getString("sms_fidelity_sender");
                    String sms_fidelity_content = c.getString("sms_fidelity_content");
                    String sms_remember_booking_enabled = c.getString("sms_remember_booking_enabled");
                    String discount_exclusivity = c.getString("discount_exclusivity");
                    premierprofessionel.add(sms_happybirthday_enabled);
                    premierprofessionel.add(sms_happybirthday_sender);
                    premierprofessionel.add(sms_happybirthday_content);
                    premierprofessionel.add(sms_fidelity_enabled);
                    premierprofessionel.add(sms_fidelity_sender);
                    premierprofessionel.add(sms_fidelity_content);
                    premierprofessionel.add(sms_remember_booking_enabled);
                    premierprofessionel.add(discount_exclusivity);

                    String preference_resource_type = c.getString("preference_resource_type");
                    String sponsoring_key = c.getString("sponsoring_key");
                    String show_discount = c.getString("show_discount");
                    String shop_name = c.getString("shop_name");
                    premierprofessionel.add(preference_resource_type);
                    premierprofessionel.add(sponsoring_key);
                    premierprofessionel.add(show_discount);
                    premierprofessionel.add(shop_name);

                    String tags = c.getString("tags");
                    String shop_description = c.getString("shop_description");
                    String specialty = c.getString("specialty");
                    String address = c.getString("address");
                    premierprofessionel.add(tags);
                    premierprofessionel.add(shop_description);
                    premierprofessionel.add(specialty);
                    premierprofessionel.add(address);

                    String post_code = c.getString("post_code");
                    String city = c.getString("city");
                    String country = c.getString("country");
                    String type = c.getString("type");
                    premierprofessionel.add(post_code);
                    premierprofessionel.add(city);
                    premierprofessionel.add(country);
                    premierprofessionel.add(type);

                    String shop_phone = c.getString("shop_phone");
                    String shop_email = c.getString("shop_email");
                    String website_link = c.getString("website_link");
                    String facebook_link = c.getString("facebook_link");
                    premierprofessionel.add(shop_phone);
                    premierprofessionel.add(shop_email);
                    premierprofessionel.add(website_link);
                    premierprofessionel.add(facebook_link);

                    String instagram_link = c.getString("instagram_link");
                    String pinterest_link = c.getString("pinterest_link");
                    String product_categories = c.getString("product_categories");
                    premierprofessionel.add(instagram_link);
                    premierprofessionel.add(pinterest_link);
                    premierprofessionel.add(product_categories);


                    String recup = "{" + '"' + "Recup" + '"' + " : [{" + '"' + "parent" + '"';
                    String ahbon = product_categories.replace("[{" + '"' + "parent" + '"', recup);
                    String ahbon2 = ahbon.replace("Z" + '"' + "}]", "Z" + '"' + "}]}");

                    System.out.println("AHBON2 :" + ahbon2);
                    JSONObject jsonObj2 = new JSONObject(ahbon2);
                    JSONArray prestations = jsonObj2.getJSONArray("Recup");

                    PremierProfessionnalProduits = new Product_Category_WithoutTree[prestations.length()];
                    // looping through All Contacts
                    for (int j = 0; j < prestations.length(); j++) {
                        Product_Category_WithoutTree services = new Product_Category_WithoutTree();
                        JSONObject p = prestations.getJSONObject(j);


                        String parent = p.getString("parent");
                        String id_prest = p.getString("id");
                        String name_prest = p.getString("name");
                        String tags_prest = p.getString("tags");
                        String created_at_prest = p.getString("created_at");
                        String updated_at_prest = p.getString("updated_at");
                        listeprestationpremierprofessionnel.add(parent);
                        listeprestationpremierprofessionnel.add(id_prest);
                        listeprestationpremierprofessionnel.add(tags_prest);


                        ////////////////////////PRODUCT CATEGORY
                        String recup2 = "{" + '"' + "Recup2" + '"' + " : [{";
                        String ahbon3 = tags_prest.replace("[{", recup2);
                        String ahbon4 = ahbon3.replace("}]", "}]}");

                        System.out.println("AHBON3 :" + ahbon4);
                        JSONObject jsonObj3 = new JSONObject(ahbon4);
                        JSONArray prestations2 = jsonObj3.getJSONArray("Recup2");
                        PremierProfessionnalProduitsTag = new Product_Category_Tag[prestations2.length()];
                        // looping through All Contacts
                        for (int g = 0; g < prestations2.length(); g++) {
                            Product_Category_Tag tag = new Product_Category_Tag();
                            JSONObject p2 = prestations2.getJSONObject(g);
                            String id_label = p2.getString("id");
                            String label = p2.getString("label");
                            listelabelprestationpremierprofessionnel.add(label);

                            //Remplissage des services
                            tag.setId(Integer.parseInt(id_label));
                            tag.setLabel(label);
                            PremierProfessionnalProduitsTag[g] = tag;

                        }


                        services.setId(Integer.parseInt(id_prest));
                        services.setName(name_prest);
                        services.setTags(PremierProfessionnalProduitsTag);
                        services.setCreated_at(created_at_prest);
                        services.setUpdated_at(updated_at_prest);
                        PremierProfessionnalProduits[j] = services;

                    }


                    String awards = c.getString("awards");
                    String unavailabilities = c.getString("unavailabilities");
                    String schedule = c.getString("schedule");
                    premierprofessionel.add(awards);
                    premierprofessionel.add(unavailabilities);
                    premierprofessionel.add(schedule);


                    //Remplissage du public professionnal
                    PremierProfessionnal.setShop_name(shop_name);
                    PremierProfessionnal.setShop_description(shop_description);
                    PremierProfessionnal.setAddress(address);
                    PremierProfessionnal.setPost_code(post_code);
                    PremierProfessionnal.setCity(city);
                    PremierProfessionnal.setCountry(country);
                    PremierProfessionnal.setShop_phone(shop_phone);
                    PremierProfessionnal.setShop_email(shop_email);
                    PremierProfessionnal.setWebsite_link(website_link);
                    PremierProfessionnal.setFacebook_link(facebook_link);
                    PremierProfessionnal.setInstagram_link(instagram_link);
                    PremierProfessionnal.setPinterest_link(pinterest_link);


                    PremierProfessionnal.setProduct_categories(PremierProfessionnalProduits);
                    PremierProfessionnal.setSchedule(ProfessionnalGenericSchedule);


                    //Remplissage du professionnal
                    PremierProfessionnal.setId(Integer.valueOf(id));
                    PremierProfessionnal.setGeoloc_latitude(Double.parseDouble(geoloc_latitude));
                    PremierProfessionnal.setGeoloc_longitude(Double.parseDouble(geoloc_longitude));
                    if (!max_intervention_distance.equals("null"))
                        PremierProfessionnal.setMax_intervention_distance(Integer.parseInt(max_intervention_distance));
                    else
                        PremierProfessionnal.setMax_intervention_distance(0);
                    PremierProfessionnal.setAutomatic_booking_confirmation(Boolean.parseBoolean(automatic_booking_confirmation));
                    PremierProfessionnal.setCustomer_can_choose_resource_booking(Boolean.parseBoolean(customer_can_choose_resource_booking));
                    PremierProfessionnal.setCreated_at(created_at);
                    PremierProfessionnal.setUpdated_at(updated_at);

                    PremierProfessionnal.setNotification_preferences_sms(notifications_preferences_sms);
                    PremierProfessionnal.setSms_happybirthday_enabled(Boolean.parseBoolean(sms_happybirthday_enabled));
                    PremierProfessionnal.setSms_happybirthday_sender(sms_happybirthday_sender);
                    PremierProfessionnal.setSms_happybirthday_content(sms_happybirthday_content);

                    PremierProfessionnal.setSms_fidelity_enabled(Boolean.parseBoolean(sms_fidelity_enabled));
                    PremierProfessionnal.setSms_fidelity_sender(sms_fidelity_sender);
                    PremierProfessionnal.setSms_fidelity_content(sms_fidelity_content);
                    PremierProfessionnal.setSms_remember_booking_enabled(Boolean.parseBoolean(sms_remember_booking_enabled));
                    PremierProfessionnal.setSponsoring_key(sponsoring_key);


                    System.out.println(PremierProfessionnal.toString());


                    //}

                    specialite.add(id_professional);
                    System.out.println("VAAAAAAAAAAAAAALUE " + id_professional);

                    //}
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }


            }
        }
    }

    public String recherche_schedule(String query) {
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee SCHEDULE");
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional-schedule/professional/"+query+"/");
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee SCHEDULE");
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


    public void schedule_jsonparser(String jsonStr) {
        if(jsonStr.equals("[]")){
            ProfessionnalGenericSchedule = new Professional_Schedule[0];
        }else {
            try {

                String recup_schedule = "{" + '"' + "data" + '"' + " : [{";
                String schedule1 = jsonStr.replace("[{", recup_schedule);
                String schedule2 = schedule1.replace("}]", "}]}");

                if (!jsonStr.equals("[]")) {
                    JSONObject jsonObj = new JSONObject(schedule2);
                    //JSONArray contacts = jsonObj.getJSONArray("data");
                    JSONArray Professional_Schedule = jsonObj.getJSONArray("data");

                    System.out.println("AHBON3 :" + jsonStr);

                    ProfessionnalGenericSchedule = new Professional_Schedule[Professional_Schedule.length()];
                    for (int j = 0; j < Professional_Schedule.length(); j++) {
                        Professional_Schedule pro_schedule = new Professional_Schedule();
                        JSONObject p2 = Professional_Schedule.getJSONObject(j);
                        String id_schedule = p2.getString("id");
                        String weekeday_schedule = p2.getString("weekday");
                        String begin_time_schedule = p2.getString("begin_time");
                        String end_time_schedule = p2.getString("end_time");

                        //Remplissage des horaires d'ouverture
                        pro_schedule.setId(Integer.parseInt(id_schedule));
                        pro_schedule.setWeekday(Integer.parseInt(weekeday_schedule));
                        pro_schedule.setBegin_time(begin_time_schedule);
                        pro_schedule.setEnd_time(end_time_schedule);
                        ProfessionnalGenericSchedule[j] = pro_schedule;
                    }
                }

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }

    }


    public String moyenne(String moy){
        String moyenn = null;

        System.out.println(moy.substring(0,1));
        System.out.println(moy.substring(1,2));
        System.out.println(moy.substring(2,3));
        if(Integer.parseInt(moy.substring(2,3)) <= 3){
            moyenn = moy.substring(0,1);
        }else if(Integer.parseInt(moy.substring(2,3)) >= 4 && Integer.parseInt(moy.substring(2,3)) <= 7){
            moyenn = moy.substring(0,1) + ".5";
        }else if(Integer.parseInt(moy.substring(2,3)) >= 8){
            moyenn = String.valueOf(Integer.parseInt(moy.substring(0,1))+1);
        }


        return moyenn;
    }





}