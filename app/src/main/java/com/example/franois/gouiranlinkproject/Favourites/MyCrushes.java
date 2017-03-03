package com.example.franois.gouiranlinkproject.Favourites;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.Object.Customer;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.ToolsClasses.DownloadImageTask;
import com.example.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/*
"Mes coups de coeur"
 */

public class MyCrushes extends Fragment {

    private Customer customer;
    List<Data> datas;

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
        List<String> universes;


        public Data() {
            this.shop_image = "";
            this.name = "";
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
            JSONObject jsonObject = new JSONObject(resp);
            JSONArray arr = jsonObject.getJSONArray("data");
            for (int i = 0; i < arr.length(); i++) {
                Data data = new Data();
                if (arr.getJSONObject(i).has("shop_name") && !arr.getJSONObject(i).isNull("shop_name")) {
                    data.name = arr.getJSONObject(i).getString("shop_name");
                    Log.d("TOTOTATA", data.name);
                }
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

        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        // TODO REMOVE
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        for (int i = 0; i < datas.size(); i++) {
            imagesUrl.add(datas.get(i).shop_image);
            names.add(datas.get(i).name);
        }
        // TODO JUSQU'ICI
        GridView gridview = (GridView) getActivity().findViewById(R.id.gridview);
        FavouritesImageAdapter favouritesImageAdapter = new FavouritesImageAdapter(getActivity());
        favouritesImageAdapter.setmThumbIds(imagesUrl);
        favouritesImageAdapter.setmThumbNames(names);
        gridview.setAdapter(favouritesImageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

}