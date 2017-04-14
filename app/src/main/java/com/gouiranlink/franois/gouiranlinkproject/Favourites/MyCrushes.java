package com.gouiranlink.franois.gouiranlinkproject.Favourites;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/*
"Mes coups de coeur"
 */

public class MyCrushes extends Fragment implements OnMapReadyCallback {

    private Customer customer;
    List<Data> datas;

    double latitude = 0;
    double longitude = 0;
    GoogleMap mMap;

    public MyCrushes() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = (Customer) getArguments().getSerializable("Customer");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_crushes, null);
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        datas = retrieveRequestInformations();
        List<String> imagesUrl = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        List<Integer> ids = new ArrayList<Integer>();

        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        // TODO REMOVE
        /*for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
            ids.add(datas.get(i).id);
        }*/
        // TODO JUSQU'ICI
        GridView gridview = (GridView) getActivity().findViewById(R.id.gridviewCrushes);
        final FavouritesImageAdapter favouritesImageAdapter = new FavouritesImageAdapter(getActivity());
        favouritesImageAdapter.setmThumbPictures(imagesUrl);
        favouritesImageAdapter.setmThumbNames(names);
        favouritesImageAdapter.setmIds(ids);
        gridview.setAdapter(favouritesImageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(getContext());
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

                    Button mail = (Button) root.findViewById(R.id.mail);
                    mail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getContext().startActivity(Intent.createChooser(emailIntent, "Envoyer e-mail..."));
                        }
                    });

                    Button call = (Button) root.findViewById(R.id.call);
                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getContext().startActivity(Intent.createChooser(callIntent, "Appeler..."));
                        }
                    });

                    latitude = obj.getDouble("geoloc_latitude");
                    longitude = obj.getDouble("geoloc_longitude");

                    SupportMapFragment mSupportMapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
                    mSupportMapFragment.getMapAsync((OnMapReadyCallback) getActivity());

                } catch (InterruptedException | ExecutionException | JSONException e) {
                    e.printStackTrace();
                }
                favouritesImageAdapter.getmIds().get(position);
                dialog.setContentView(root);
                dialog.show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(getActivity(), "MAP MARKED", Toast.LENGTH_SHORT).show();
        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)).anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(latitude, longitude)));
        LatLng coordinate = new LatLng(latitude, longitude);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 11.0f);
        mMap.animateCamera(yourLocation);
    }

    private String getDate(String complete) {
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
}