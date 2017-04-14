package com.gouiranlink.franois.gouiranlinkproject.Homepage;

import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

class NearbyProfessionals {
    final private String[] shopNameList;
    final private String[] shopImageList;

    String[] getShopNameList() {
        return shopNameList;
    }

    String[] getShopImageList() {
        return shopImageList;
    }

    NearbyProfessionals(int latitude, int longitude) {
        String resp = null;
        JSONObject obj;
        JSONArray arr;

        shopImageList = new String[5];
        shopNameList = new String[5];

        GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/?query[geoloc][latitude]=" + String.valueOf(latitude)
                + "&query[geoloc][longitude]=" + String.valueOf(longitude));

        try {
            resp = getRequest.execute().get();
            obj = new JSONObject(resp);
            arr = obj.getJSONArray("data");

            for (int i = 0; i < arr.length() && i < 5; i++) {

                if (arr.getJSONObject(i).getString("shop_name") != null) {
                    shopNameList[i] = arr.getJSONObject(i).getString("shop_name");
                }
                for (int j = 0; j < arr.getJSONObject(i).getJSONArray("shop_images").length(); j++) {
                    if (arr.getJSONObject(i).getJSONArray("shop_images").getJSONObject(j).getJSONObject("image").getJSONObject("thumbnails").getJSONObject("standard").getString("url") != null) {
                        shopImageList[i] = arr.getJSONObject(i).getJSONArray("shop_images").getJSONObject(j).getJSONObject("image").getJSONObject("thumbnails").getJSONObject("search").getString("url");
                    }
                }

            }

        } catch (InterruptedException | JSONException | ExecutionException e) {
            e.printStackTrace();
        }
    }



}
