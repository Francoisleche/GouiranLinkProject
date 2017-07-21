package com.gouiranlink.franois.gouiranlinkproject.ToolsClasses;

import android.util.Log;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer_Profession;
import com.gouiranlink.franois.gouiranlinkproject.Object.Image_N;
import com.gouiranlink.franois.gouiranlinkproject.Object.Product_Category_WithoutTree;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class RetrieveCustomerInformationFromRequest implements Serializable {
    private String json;
    private String token;

    public RetrieveCustomerInformationFromRequest(String json, String token) {
        this.json = json;
        this.token = token;
        Log.d("TOKEN=", token);
        Log.d("JSON=", json);
    }

    public Customer generateCustomer() {
        Customer customer = null;
        int id;
        String name;

        String surname;
        Image_N image;
        String created_at;
        String updated_at;
        boolean has_subscribed;
        boolean share_with_professional;
        boolean blocked;
        String gender;
        String phone;
        String mobilephone;
        String birthday_date;
        String address;
        String post_code;
        String city;
        String country;
        String geoloc_latitude;
        String geoloc_longitude;
        boolean sms;
        boolean newsletter;
        Customer_Profession profession = null;
        String profession_other;
        String language;
        Image_N image_customer;
        Product_Category_WithoutTree[] product_categories;
        String email;

        try {
            Log.d("JSON=", json);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject user = jsonObject.getJSONObject("user");
            id = Integer.valueOf(user.getString("id"));
            name = user.getString("name");
            surname = user.getString("surname");
            ArrayList<String[]> arrayList = new ArrayList<>();
            String[] standard = new String[3];
            String[] search = new String[3];
            String[] favoris = new String[3];
            String[] logo = new String[3];
            if (user.has("image") && !user.isNull("image")) {
                standard[0] = user.getJSONObject("image").getJSONObject("thumbnails").getJSONObject("standard").getString("width");
                standard[1] = user.getJSONObject("image").getJSONObject("thumbnails").getJSONObject("standard").getString("height");
                standard[2] = user.getJSONObject("image").getJSONObject("thumbnails").getJSONObject("standard").getString("url");
                search[0] = user.getJSONObject("image").getJSONObject("thumbnails").getJSONObject("search").getString("width");
                search[1] = user.getJSONObject("image").getJSONObject("thumbnails").getJSONObject("search").getString("height");
                search[2] = user.getJSONObject("image").getJSONObject("thumbnails").getJSONObject("search").getString("url");
                favoris[0] = user.getJSONObject("image").getJSONObject("thumbnails").getJSONObject("favoris").getString("width");
                favoris[1] = user.getJSONObject("image").getJSONObject("thumbnails").getJSONObject("favoris").getString("height");
                favoris[2] = user.getJSONObject("image").getJSONObject("thumbnails").getJSONObject("favoris").getString("url");
                logo[0] = user.getJSONObject("image").getJSONObject("thumbnails").getJSONObject("logo").getString("width");
                logo[1] = user.getJSONObject("image").getJSONObject("thumbnails").getJSONObject("logo").getString("height");
                logo[2] = user.getJSONObject("image").getJSONObject("thumbnails").getJSONObject("logo").getString("url");
                arrayList.add(standard);
                arrayList.add(search);
                arrayList.add(favoris);
                arrayList.add(logo);
            }
            else {
                Log.d("ELSE ELSE", "ELSE ELSE");
                standard[0] = "https://www.gouiran-beaute.com/link/cache/media/personnage/cr,300,300-5c8799.jpg";
                search[0] = "https://www.gouiran-beaute.com/link/cache/media/personnage/cr,300,300-5c8799.jpg";
                favoris[0] = "https://www.gouiran-beaute.com/link/cache/media/personnage/cr,300,300-5c8799.jpg";
                logo[0] = "https://www.gouiran-beaute.com/link/cache/media/personnage/cr,300,300-5c8799.jpg";
                standard[1] = "https://www.gouiran-beaute.com/link/cache/media/personnage/cr,300,300-5c8799.jpg";
                search[1] = "https://www.gouiran-beaute.com/link/cache/media/personnage/cr,300,300-5c8799.jpg";
                favoris[1] = "https://www.gouiran-beaute.com/link/cache/media/personnage/cr,300,300-5c8799.jpg";
                logo[1] = "https://www.gouiran-beaute.com/link/cache/media/personnage/cr,300,300-5c8799.jpg";
                standard[2] = "https://www.gouiran-beaute.com/link/cache/media/personnage/cr,300,300-5c8799.jpg";
                search[2] = "https://www.gouiran-beaute.com/link/cache/media/personnage/cr,300,300-5c8799.jpg";
                favoris[2] = "https://www.gouiran-beaute.com/link/cache/media/personnage/cr,300,300-5c8799.jpg";
                logo[2] = "https://www.gouiran-beaute.com/link/cache/media/personnage/cr,300,300-5c8799.jpg";
                arrayList.add(standard);
                arrayList.add(search);
                arrayList.add(favoris);
                arrayList.add(logo);
            }
            /*Log.d("IMAGE", image.getUrl());
            Log.d("IMAGEE", Arrays.toString(image.getThumbnails().get(0)));
            Log.d("IMAGEEE", Arrays.toString(image.getThumbnails().get(1)));
            Log.d("IMAGEEEE", Arrays.toString(image.getThumbnails().get(2)));
            Log.d("IMAGEEEEE", Arrays.toString(image.getThumbnails().get(3)));*/
            created_at = user.getString("created_at");
            updated_at = user.getString("updated_at");
            has_subscribed = user.getBoolean("has_subscribed");
            share_with_professional = user.getBoolean("share_with_professional");
            blocked = user.getBoolean("blocked");
            gender = user.getString("gender");
            phone = user.getString("phone");
            mobilephone = user.getString("mobilephone");
            birthday_date = user.getString("birthday_date");
            address = user.getString("address");
            post_code = user.getString("post_code");
            city = user.getString("city");
            country = user.getString("country");
            geoloc_latitude = user.getString("geoloc_latitude");
            geoloc_longitude = user.getString("geoloc_longitude");
            sms = user.getBoolean("sms");
            newsletter = user.getBoolean("newsletter");

            String s = user.getString("profession");
            System.out.println("Professsssssionnnnn : " +s);
            if (s.equals("null")){
                profession = null;
            }else{
                profession = new Customer_Profession();
                profession.setId(Integer.parseInt(user.getJSONObject("profession").getString("id")));
                profession.setName(user.getJSONObject("profession").getString("name"));
            }


            profession_other = user.getString("profession_other");
            language = user.getString("language");

            image_customer = null;

            image = null;

            product_categories = null;
            email = user.getString("email");

            customer = new Customer(id, name, surname, image, created_at, updated_at, has_subscribed, share_with_professional, blocked,
            gender, phone, mobilephone, birthday_date, address, post_code, city,
                    country, geoloc_latitude, geoloc_longitude, sms, newsletter, profession,
                    profession_other, language, image_customer, product_categories, email, token);

            if (user.has("image") && !user.isNull("image"))
                image = new Image_N(user.getJSONObject("image").getString("url"), arrayList);
            else
                image = new Image_N("https://www.gouiran-beaute.com/link/cache/media/personnage/cr,300,300-5c8799.jpg", arrayList);

            customer.setImage(image);
            Log.d("CUSTOMER CREATED", customer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return (customer);
    }
}
