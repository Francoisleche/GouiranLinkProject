package com.gouiranlink.franois.gouiranlinkproject.Reservation;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.DeleteRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ReservationImageAdapter extends BaseAdapter {
    final private Context mContext;
    private Customer customer;
    private List<Boolean> boolean_commentaires_date_ok;
    private ArrayList<String> id_commentaires_deja_fait;

    private List<String> institutesNames = new ArrayList<String>();
    //private List<String> types = new ArrayList<String>();
    List<List<String>> types = new ArrayList<List<String>>();
    private List<String> pictures = new ArrayList<String>();
    private List<String> dates = new ArrayList<String>();
    private List<String> hours = new ArrayList<String>();
    private List<String> adress = new ArrayList<String>();
    private List<String> id = new ArrayList<String>();
    private String type_reservation = new String();
    private String products_json;




    ReservationImageAdapter(Context c) {
        mContext = c;
    }

    public Context getmContext() {
        return mContext;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public List<String> getAdress() {
        return adress;
    }

    public void setAdress(List<String> adress) {
        this.adress = adress;
    }

    public String getType_reservation() {
        return type_reservation;
    }

    public void setType_reservation(String type_reservation) {
        this.type_reservation = type_reservation;
    }

    public List<String> getInstitutesNames() {
        return institutesNames;
    }

    public void setInstitutesNames(List<String> institutesNames) {
        this.institutesNames = institutesNames;
    }

    public List<List<String>> getTypes() {
        return types;
    }

    public void setTypes(List<List<String>> types) {
        this.types = types;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<String> getHours() {
        return hours;
    }

    public void setHours(List<String> hours) {
        this.hours = hours;
    }

    public int getCount() {
        return institutesNames.size();
    }

    public Fragment getItem(int position) {
        return DetailRdv.newInstance();
    }


    public long getItemId(int position) {
        return position;
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_single_reservation,container, false);


        return (rootView);
    }



    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final int position2 = position;
        View grid;
        String type;
        LayoutInflater inflater = null;

        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_single_reservation, null);
        } else {
            grid = convertView;
        }
        /*for(int i =0;i<dates.size();i++){
            System.out.println("Plein les cuuuuuuuuuuuuuuuuuuul  :"+institutesNames.get(i).toString()+"   "+dates.get(i).toString());
        }*/




            TextView textView = (TextView)grid.findViewById(R.id.institute_name);
            //ImageView imageView = (ImageView)grid.findViewById(R.id.picture);
            textView.setText(institutesNames.get(position));



        //Marche Impeccablement bien :)
        //Picasso.with(grid.getContext()).load(pictures.get(position)).into(imageView);





            //new DownloadImageTask(imageView).execute(pictures.get(position));


            textView = (TextView)grid.findViewById(R.id.type);
            type = "";
            for (int i = 0; i < types.get(position).size(); i++) {
                if(i<2){
                    type += types.get(position).get(i) + "\n";
                }
                if(i==2){
                    type += "...";
                }
            }
            textView.setText(type);
            textView = (TextView)grid.findViewById(R.id.date);
            textView.setText("le " + dates.get(position) + " à "+hours.get(position));
            /*textView = (TextView)grid.findViewById(R.id.hour);
            textView.setText();*/


        ImageView button = (ImageView) grid.findViewById(R.id.button);
        ((Activity) mContext).getFragmentManager();//use this

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getmContext(), "ooooooh" + position,Toast.LENGTH_SHORT).show();

            }
        });*/




        return (grid);
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




    public List<Boolean> getBoolean_Commentaire() {
        return boolean_commentaires_date_ok;
    }

    public void setBoolean_Commentaire(List<Boolean> boolean_commentaires) {
        this.boolean_commentaires_date_ok = boolean_commentaires;
    }

    public String getProducts_json() {
        return products_json;
    }

    public void setProducts_json(String products_json) {
        this.products_json = products_json;
    }



    protected Drawable ImageOperations(String url) {
        try {
            //URL urle = new URL(url);
            //Object content = urle.getContent();

            InputStream inputstream = new URL(url).openStream();
            Drawable  drawable = Drawable.createFromStream(inputstream, "src");
            return drawable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    protected Drawable drawableFromUrl(URL url) throws IOException {
        Drawable ret = null;
        InputStream stream = null;
        try {
            stream = url.openStream();
            ret = Drawable.createFromStream(stream, "src");
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            Log.e("drawableFromUrl","Failed to read image from URL !",e);
            throw new IOException("ImageFromUrlFailure",e);
        } finally {
            if (stream != null) try { stream.close(); } catch (Exception ex) { Log.wtf("drawableFromUrl","Failed to gracefully close the stream !",ex); }
        }
        return ret;
    }

}
