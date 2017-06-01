package com.gouiranlink.franois.gouiranlinkproject.Reservation;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class DoneFragment extends Fragment {

    public DoneFragment() {
        // Required empty public constructor
    }

    final private LinearLayout[] linearLayouts = new LinearLayout[10];
    final private LinearLayout[] separatorParts = new LinearLayout[10];
    final private LinearLayout[] firstParts = new LinearLayout[10];
    final private LinearLayout[] secondParts = new LinearLayout[10];
    final private LinearLayout[] thirdParts = new LinearLayout[10];
    final private Button[] buttons = new Button[10];
    private Typeface font;
    private Customer customer;
    List<Reservation> reservations;
    private ListView listviewDone;

    private String products_json ="";


    private ArrayList<Boolean> boolean_commentaires;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = (Customer) getArguments().getSerializable("Customer");
        }
        //reservations = new ArrayList<Reservation>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root;
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_done, null);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Acrom W00 Medium.ttf");

        /*for (int i = 0; i < 10; i++) {
            int resID = getResources().getIdentifier("reservation" + (i + 1), "id", getActivity().getPackageName());
            linearLayouts[i] = (LinearLayout)root.findViewById(resID);
            resID = getResources().getIdentifier("reservation" + (i + 1) + "_part0", "id", getActivity().getPackageName());
            separatorParts[i] = (LinearLayout)root.findViewById(resID);
            resID = getResources().getIdentifier("reservation" + (i + 1) + "_part1", "id", getActivity().getPackageName());
            firstParts[i] = (LinearLayout)root.findViewById(resID);
            resID = getResources().getIdentifier("reservation" + (i + 1) + "_part2", "id", getActivity().getPackageName());
            secondParts[i] = (LinearLayout)root.findViewById(resID);
            resID = getResources().getIdentifier("reservation" + (i + 1) + "_part3", "id", getActivity().getPackageName());
            thirdParts[i] = (LinearLayout)root.findViewById(resID);
            buttons[i] = new Button(getActivity());
        }
        RelativeLayout layout = (RelativeLayout) root.findViewById(R.id.upcoming_relative);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        */
        return (root);
    }

    private class Reservation {
        private String id;
        String picture;
        String institute;
        List<String> type;
        String date;
        String hour;
        String adress;

        public Reservation() {
            picture = "";
            institute = "";
            type = new ArrayList<String>();
            date = "";
            hour = "";
            adress = "";
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getInstitute() {
            return institute;
        }

        public void setInstitute(String institute) {
            this.institute = institute;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    private List<Reservation> getReservationList() {
        List<Reservation> reservationList = new ArrayList<Reservation>();
        String headerKey;
        String headerValue;
        String resp;

        headerKey = "Authorization";
        headerValue = "Token " + String.valueOf(customer.getToken());
        System.out.println("MATOKEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEN" + String.valueOf(customer.getToken()));
        GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/booking/customer/" + String.valueOf(customer.getId()) + "/", headerKey, headerValue);
        try {
            resp = getRequest.execute().get();

            String s = resp.replace(",",", \n");
            System.out.println(resp);

            JSONObject jsonObject = new JSONObject(resp);
            JSONArray arr = jsonObject.getJSONArray("data");
            boolean_commentaires = new ArrayList<>(arr.length());
            System.out.println("RESERVATIONNNNNNNNNNNNNNNS : "+arr.length());
            for (int i = 0; i < arr.length(); i++) {
                Reservation reservation = new Reservation();
                if (arr.getJSONObject(i).has("begin_date") && !arr.getJSONObject(i).isNull("begin_date") && isPassed(arr.getJSONObject(i).getString("begin_date"))) {

//                if (arr.getJSONObject(i).has("confirmed") && arr.getJSONObject(i).getBoolean("confirmed")) {
                    if (arr.getJSONObject(i).getJSONObject("professional").has("shop_name") && !arr.getJSONObject(i).getJSONObject("professional").isNull("shop_name")) {
                        reservation.institute = arr.getJSONObject(i).getJSONObject("professional").getString("shop_name");
                        reservation.id = arr.getJSONObject(i).getString("id");
                    }

                    if (arr.getJSONObject(i).getJSONObject("professional").has("address") &&
                            arr.getJSONObject(i).getJSONObject("professional").has("post_code") &&
                            arr.getJSONObject(i).getJSONObject("professional").has("city") &&
                            !arr.getJSONObject(i).getJSONObject("professional").isNull("address") &&
                            !arr.getJSONObject(i).getJSONObject("professional").isNull("post_code") &&
                            !arr.getJSONObject(i).getJSONObject("professional").isNull("city")) {

                        reservation.adress = arr.getJSONObject(i).getJSONObject("professional").getString("address") +
                                arr.getJSONObject(i).getJSONObject("professional").getString("post_code") +
                                arr.getJSONObject(i).getJSONObject("professional").getString("city");
                    }

                    if (arr.getJSONObject(i).getJSONObject("professional").getJSONObject("logo_image").getJSONObject("thumbnails").getJSONObject("standard").has("url") &&
                            !arr.getJSONObject(i).getJSONObject("professional").getJSONObject("logo_image").getJSONObject("thumbnails").getJSONObject("standard").isNull("url"))
                        reservation.picture = arr.getJSONObject(i).getJSONObject("professional").getJSONObject("logo_image").getJSONObject("thumbnails").getJSONObject("standard").getString("url");
                    for (int j = 0; j < arr.getJSONObject(i).getJSONArray("products").length(); j++) {
                        if (arr.getJSONObject(i).getJSONArray("products").getJSONObject(j).getJSONObject("product").getJSONObject("category").getJSONObject("parent").has("name") &&
                                !arr.getJSONObject(i).getJSONArray("products").getJSONObject(j).getJSONObject("product").getJSONObject("category").getJSONObject("parent").isNull("name"))
                            reservation.type.add(arr.getJSONObject(i).getJSONArray("products").getJSONObject(j).getJSONObject("product").getJSONObject("category").getJSONObject("parent").getString("name"));
                    }
                    if (arr.getJSONObject(i).has("begin_date") && !arr.getJSONObject(i).isNull("begin_date")) {

                        boolean_commentaires.add(date_commentaire(arr.getJSONObject(i).getString("begin_date")));
                        products_json = arr.getJSONObject(i).getString("products");
                        System.out.println("DONNNNNNNE FRAGMENT 1 :" +getDate(arr.getJSONObject(i).getString("begin_date")));
                        System.out.println("DONNNNNNNE FRAGMENT 2 :" +getHour(arr.getJSONObject(i).getString("begin_date")));

                        reservation.date = getDate(arr.getJSONObject(i).getString("begin_date"));
                        reservation.hour = getHour(arr.getJSONObject(i).getString("begin_date"));
                    }
                    Log.d("DONEDATE=", arr.getJSONObject(i).getString("begin_date"));
                    reservationList.add(reservation);
//                }
/*                else if (arr.getJSONObject(i).has("confirmed") && !arr.getJSONObject(i).getBoolean("confirmed")) {
                    // TODO NON CONFIRME
                }*/
                }
            }


        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return (reservationList);
    }

    private boolean isPassed(String complete) {
        String completeDate;
        String day;
        String number;
        String month;
        String year;
        String[] parts;



        /*DateTimeFormatter parser = ISODateTimeFormat.dateTime();
        DateTime dt = parser.parseDateTime(complete);

        DateTimeFormatter formatter = DateTimeFormat.mediumDateTime();
        System.out.println(formatter.print(dt));*/

        completeDate = complete.substring(0, complete.indexOf("T"));
        parts = completeDate.split("-");
        Calendar c = Calendar.getInstance();
        number = parts[2];
        month = parts[1];
        year = parts[0];
        c.setTime(new Date(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]), Integer.valueOf(parts[2])));
        DateTime current = new org.joda.time.DateTime( org.joda.time.DateTimeZone.UTC );
        complete = complete.replace("T", " ");
        complete = complete.replace("Z", "");
        System.out.println("Complete " + complete);
        System.out.println("Current " + current);
        System.out.println("Comparison " + current.isAfter(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(complete)));

        return (current.isAfter(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(complete)));
    }

    private String getDate(String complete) {
        String completeDate;
        String day;
        String number;
        String month;
        String year;
        String[] parts;

        completeDate = complete.substring(0, complete.indexOf("T"));
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
        return (day + " " + number + " " + month + " " + year);
    }

    private String getHour(String complete) {
        String completeHour;
        String hour;
        String minute;
        String[] parts;

        completeHour = complete.substring(complete.indexOf("T") + 1, complete.indexOf("Z"));
        parts = completeHour.split(":");
        hour = parts[0];
        minute = parts[1];
        return (hour + "h" + minute);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        reservations = getReservationList();
        List<String> institutesNames = new ArrayList<String>();
        List<List<String>> types = new ArrayList<List<String>>();
        List<String> pictures = new ArrayList<String>();
        List<String> dates = new ArrayList<String>();
        List<String> hours = new ArrayList<String>();
        List<String> adress = new ArrayList<String>();
        List<String> id = new ArrayList<String>();

        for (int i = 0; i < reservations.size(); i++) {
            institutesNames.add(reservations.get(i).getInstitute());
            id.add(reservations.get(i).getId());
            types.add(reservations.get(i).getType());
            pictures.add(reservations.get(i).getPicture());
            dates.add(reservations.get(i).getDate());
            hours.add(reservations.get(i).getHour());
            adress.add(reservations.get(i).getAdress());
        }

        listviewDone = (ListView) getActivity().findViewById(R.id.listviewDone);

        //GridView gridview = (GridView) getActivity().findViewById(R.id.gridviewDone);
        ReservationImageAdapter reservationImageAdapter = new ReservationImageAdapter(getActivity());
        reservationImageAdapter.setId(id);
        reservationImageAdapter.setInstitutesNames(institutesNames);
        reservationImageAdapter.setTypes(types);
        reservationImageAdapter.setPictures(pictures);
        reservationImageAdapter.setDates(dates);
        reservationImageAdapter.setHours(hours);
        reservationImageAdapter.setAdress(adress);
        reservationImageAdapter.setType_reservation("Done");
        reservationImageAdapter.setCustomer(customer);
        reservationImageAdapter.setBoolean_Commentaire(boolean_commentaires);
        reservationImageAdapter.setProducts_json(products_json);

        System.out.println("RESERVATIONNNNNNNNNNNNNNNS2 : " + institutesNames.size() + " "+types.size()+"   "+adress.size());
        for(int i =0;i<dates.size();i++){
            System.out.println("RESERVATIONNNNNNNNNNNNNNNS3 :"+id.get(i)+"      "+ institutesNames.get(i).toString()+"   "+dates.get(i).toString());
        }


        /*gridview.setAdapter(reservationImageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });*/

        listviewDone.setAdapter(reservationImageAdapter);
        listviewDone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }



    public boolean date_commentaire(String date2){
        boolean boo = false;
        String format = "dd/MM/yy H:mm:ss";

        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
        java.util.Date date = new java.util.Date();

        System.out.println( formater.format(date) +"     date 2 : "+date2);


        String jour1=formater.format(date).toString().substring(0,2);
        String jour2=date2.toString().substring(8,10);
        String mois1=formater.format(date).toString().substring(3,5);
        String mois2=date2.toString().substring(5,7);
        String annee1="20"+formater.format(date).toString().substring(6,8);
        String annee2=date2.toString().substring(0,4);
        String heure1=formater.format(date).toString().substring(9,11);
        String heure2=date2.toString().substring(11,13);

        if(Integer.parseInt(jour1)>Integer.parseInt(jour2)){
            boo=  true;
        }else{
            if(Integer.parseInt(mois1)>Integer.parseInt(mois2)){
                boo=  true;
            }else{
                if(Integer.parseInt(annee1)>Integer.parseInt(annee2)){
                    boo=  true;
                }else{
                    boo=  false;
                }
            }
        }







        return boo;
    }

}