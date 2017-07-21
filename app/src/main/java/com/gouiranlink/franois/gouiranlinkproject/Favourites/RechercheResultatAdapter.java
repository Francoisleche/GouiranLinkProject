package com.gouiranlink.franois.gouiranlinkproject.Favourites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by François on 02/06/2017.
 */

public class RechercheResultatAdapter extends BaseAdapter {

    final private Context mContext;

    private List<String> pictures = new ArrayList<String>();
    private List<String> id = new ArrayList<String>();
    private List<String> institutesNames = new ArrayList<String>();
    private List<String> avis = new ArrayList<String>();
    private List<String> favoris = new ArrayList<String>();


    RechercheResultatAdapter(Context c) {
        mContext = c;
    }

    public Context getmContext() {
        return mContext;
    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public List<String> getInstitutesNames() {
        return institutesNames;
    }

    public void setInstitutesNames(List<String> institutesNames) {
        this.institutesNames = institutesNames;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public int getCount() {
        return institutesNames.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        View grid;
        String type;
        LayoutInflater inflater = null;

        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.services_resultat_recherche, null);
        } else {
            grid = convertView;
        }

        TextView textViewshopname = (TextView) grid.findViewById(R.id.shopname_resultat);
        TextView textViewavis = (TextView) grid.findViewById(R.id.resultat_avis);
        TextView textViewfavoris = (TextView) grid.findViewById(R.id.resultat_favoris);
        ImageView imageView = (ImageView) grid.findViewById(R.id.image_recherche);


        textViewshopname.setText(institutesNames.get(position));
        //textViewavis.setText(getAvis().get(position));
        //textViewfavoris.setText(getFavoris().get(position));

        //Marche Impeccablement bien :)
        if(pictures.get(position) == ""){
            imageView.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        }else{
            Picasso.with(grid.getContext()).load(pictures.get(position))
                    .into(imageView);
        }


        RatingBar ratingbar = (RatingBar) grid.findViewById(R.id.rtbProductRating);

        //Moyenne des avis
        double moyenne = 0;
        if(avis.size() == 0){

        }else{
            for(int i =0;i<avis.size();i++){
                moyenne = moyenne + Double.parseDouble(avis.get(i));
            }
            moyenne = moyenne/avis.size();
        }
        ratingbar.setRating(Float.parseFloat(moyenne(String.valueOf(moyenne))));
        String ss  = String.format("%1$s/5", moyenne(String.valueOf(moyenne)));
        textViewavis.setText(ss);

        String s  = String.format("%1$s FAVORIS", getFavoris().get(position));
        textViewfavoris.setText(s);


        return (grid);
    }


    public List<String> getAvis() {
        return avis;
    }

    public void setAvis(List<String> avis) {
        this.avis = avis;
    }

    public List<String> getFavoris() {
        return favoris;
    }

    public void setFavoris(List<String> favoris) {
        this.favoris = favoris;
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

