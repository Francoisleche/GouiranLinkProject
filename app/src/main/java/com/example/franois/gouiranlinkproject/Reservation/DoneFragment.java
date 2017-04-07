package com.example.franois.gouiranlinkproject.Reservation;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.Favourites.MyCrushes;
import com.example.franois.gouiranlinkproject.Object.Customer;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.ToolsClasses.DownloadImageTask;
import com.example.franois.gouiranlinkproject.ToolsClasses.GetRequest;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = (Customer) getArguments().getSerializable("Customer");
        }
        reservations = new ArrayList<Reservation>();
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
        String picture;
        String institute;
        List<String> type;
        String date;
        String hour;

        public Reservation() {
            picture = "";
            institute = "";
            type = new ArrayList<String>();
            date = "";
            hour = "";
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

            JSONObject jsonObject = new JSONObject(resp);
            JSONArray arr = jsonObject.getJSONArray("data");
            for (int i = 0; i < arr.length(); i++) {
                Reservation reservation = new Reservation();
                if (arr.getJSONObject(i).has("begin_date") && !arr.getJSONObject(i).isNull("begin_date") && isPassed(arr.getJSONObject(i).getString("begin_date"))) {

//                if (arr.getJSONObject(i).has("confirmed") && arr.getJSONObject(i).getBoolean("confirmed")) {
                    if (arr.getJSONObject(i).getJSONObject("professional").has("shop_name") && !arr.getJSONObject(i).getJSONObject("professional").isNull("shop_name")) {
                        reservation.institute = arr.getJSONObject(i).getJSONObject("professional").getString("shop_name");
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

        for (int i = 0; i < reservations.size(); i++) {
            institutesNames.add(reservations.get(i).getInstitute());
            types.add(reservations.get(i).getType());
            pictures.add(reservations.get(i).getPicture());
            dates.add(reservations.get(i).getDate());
            hours.add(reservations.get(i).getHour());
        }

        listviewDone = (ListView) getActivity().findViewById(R.id.listviewDone);

        //GridView gridview = (GridView) getActivity().findViewById(R.id.gridviewDone);
        ReservationImageAdapter reservationImageAdapter = new ReservationImageAdapter(getActivity());
        reservationImageAdapter.setInstitutesNames(institutesNames);
        reservationImageAdapter.setTypes(types);
        reservationImageAdapter.setPictures(pictures);
        reservationImageAdapter.setDates(dates);
        reservationImageAdapter.setHours(hours);
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

}