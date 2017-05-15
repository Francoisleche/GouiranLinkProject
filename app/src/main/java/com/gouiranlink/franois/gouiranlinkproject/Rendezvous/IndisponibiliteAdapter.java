package com.gouiranlink.franois.gouiranlinkproject.Rendezvous;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.R;

import java.util.ArrayList;

/**
 * Created by François on 10/05/2017.
 */

public class IndisponibiliteAdapter extends ArrayAdapter<String> {

    int groupid;
    String[] horaire;
    ArrayList<String> desc;
    Context context;
    boolean premiere_fois;
    boolean[] hidden = null;
    String prix;



    public IndisponibiliteAdapter(Context context, int vg, int id,String[] horaire, String prix){
        super(context,vg, id, horaire);
        this.context=context;
        groupid=vg;
        this.setHoraire(horaire);
        this.prix=prix;

        hidden = new boolean[horaire.length];
        for (int i = 0; i < horaire.length; i++)
            hidden[i] = false;
    }

    public IndisponibiliteAdapter(Context context, int vg, int id, String[] item_list){
        super(context,vg, id, item_list);
        this.context=context;
        groupid=vg;
        this.setHoraire(getHoraire());

    }

    public String[] getHoraire() {
        return horaire;
    }

    public void setHoraire(String[] horaire) {
        this.horaire = horaire;
    }

    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView textview;
        public Button button;

    }




    public void hide(int position) {
        hidden[getRealPosition(position)] = true;
        notifyDataSetChanged();
        notifyDataSetInvalidated();
    }
    public void unHide(int position) {
        hidden[getRealPosition(position)] = false;
        notifyDataSetChanged();
        notifyDataSetInvalidated();
    }

    private int getRealPosition(int position) {
        int hElements = getHiddenCountUpTo(position);
        int diff = 0;
        for(int i=0;i<hElements;i++) {
            diff++;
            if(hidden[position+diff])
                i--;
        }
        return (position + diff);
    }
    private int getHiddenCount() {
        int count = 0;
        for(int i = 0; i< getHoraire().length; i++)
            if(hidden[i])
                count++;
        return count;
    }
    private int getHiddenCountUpTo(int location) {
        int count = 0;
        for(int i=0;i<=location;i++) {
            if(hidden[i])
                count++;
        }
        return count;
    }

    @Override
    public int getCount() {
        return (getHoraire().length - getHiddenCount());
    }








    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;

        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "personne_layout.xml"
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layoutItem = (LinearLayout) inflater.inflate(groupid, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView textviewnote = (TextView) layoutItem.findViewById(R.id.horaire);
        TextView textviewprix= (TextView) layoutItem.findViewById(R.id.horaire_prix);

        textviewnote.setTypeface(null, Typeface.BOLD);
        textviewnote.setText(getHoraire()[position]);
        textviewprix.setText(prix+"€");


        return layoutItem;
    }




}
