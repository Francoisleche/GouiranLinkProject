package com.gouiranlink.franois.gouiranlinkproject.Professional_View;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.R;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by François on 14/02/2017.
 */

public class ServicesAdapter extends ArrayAdapter<String> {

        int groupid;
        String[] item_list;
        String[] item_tarif;
        String[] item_duree;
        ArrayList<String> desc;
        Context context;
        boolean premiere_fois;
        boolean[] hidden = null;

   public ServicesAdapter(Context context, int vg, int id, String[] item_list,String[] item_tarif,String[] item_duree){
        super(context,vg, id, item_list);
        this.context=context;
        groupid=vg;
        this.item_list=item_list;
        this.item_tarif=item_tarif;
        this.item_duree=item_duree;

        hidden = new boolean[item_list.length];
        for (int i = 0; i < item_list.length; i++)
            hidden[i] = false;


    }

    public ServicesAdapter(Context context, int vg, int id, String[] item_list){
        super(context,vg, id, item_list);
        this.context=context;
        groupid=vg;
        this.item_list=item_list;

    }
        // Hold views of the ListView to improve its scrolling performance
        static class ViewHolder {
            public TextView textview;
            public TextView textviewtarif;
            public TextView textviewduree;
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
        for(int i=0;i<item_list.length;i++)
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
        return (item_list.length - getHiddenCount());
    }

    /*public SherifHidingAdapter(Context mContext, String[] objects) {
        super(mContext, R.layout.some_layout, objects);
        items = objects;
        hidden = new boolean[objects.length];
        for (int i = 0; i < objects.length; i++)
            hidden[i] = false;
    }*/






        /*@RequiresApi(api = Build.VERSION_CODES.M)
        public View getView(int position, View convertView, ViewGroup parent) {



            // Inflate the list_item.xml file if convertView is null

            ViewHolder viewHolder = new ViewHolder();
            View rowView = convertView;
            if(rowView==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView= inflater.inflate(groupid, parent, false);



                viewHolder.textview= (TextView) rowView.findViewById(R.id.txt);
                viewHolder.textviewtarif= (TextView) rowView.findViewById(R.id.tarif);
                viewHolder.textviewduree= (TextView) rowView.findViewById(R.id.dureepresta);
                viewHolder.button= (Button) rowView.findViewById(R.id.bt);


                rowView.setTag(viewHolder);

            }else{
                viewHolder = (ViewHolder) rowView.getTag();
            }



            ViewHolder holder = (ViewHolder) rowView.getTag();
            rowView.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.textview.setText(item_list[position]);
            holder.textviewtarif.setText(item_tarif[position]);
            holder.textviewduree.setText(item_duree[position]);
            System.out.println("POURQUOI 2");
            //holder.button.setText(item_list[position]);
            holder.button.setText("Je prend Rdv");




// Set text to each TextView of ListView item


/*
            if(position == 0 && premiere_fois){
                if(item_list[position].equals("")) {
                    ViewHolder holder = (ViewHolder) rowView.getTag();
                    rowView.setVisibility(View.INVISIBLE);
                    rowView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
                    System.out.println("POURQUOI ça DISPARAIIIIIIIIIT ????" + position);
                    holder.textview.setText(item_list[position]);
                    holder.textviewtarif.setText(item_tarif[position]);
                    holder.textviewduree.setText(item_duree[position]);
                    premiere_fois = false;
                }
            }else{
                if(item_list[position].equals("")) {

                    rowView.setVisibility(View.INVISIBLE);
                    rowView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
                    System.out.println("POURQUOI ça DISPARAIIIIIIIIIT ????" + position);
                    premiere_fois = false;
                }else {
                    ViewHolder holder = (ViewHolder) rowView.getTag();
                    rowView.setBackgroundColor(Color.parseColor("#ffffff"));
                    holder.textview.setText(item_list[position]);
                    holder.textviewtarif.setText(item_tarif[position]);
                    holder.textviewduree.setText(item_duree[position]);
                    System.out.println("POURQUOI 2");
                    //holder.button.setText(item_list[position]);
                    holder.button.setText("Je prend Rdv");
                }
            }*/


     /*       return rowView;
        }*/



    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "personne_layout.xml"
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layoutItem = (LinearLayout) inflater.inflate(groupid, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        //(2) : Récupération des TextView de notre layout
        TextView textview= (TextView) layoutItem.findViewById(R.id.txt);
        TextView textviewtarif= (TextView) layoutItem.findViewById(R.id.tarif);
        TextView textviewduree= (TextView) layoutItem.findViewById(R.id.dureepresta);


        //(4) Changement de la couleur du fond de notre item
        if (item_list[position].equals("")) {
            //layoutItem.setBackgroundColor(Color.BLUE);
            layoutItem.setVisibility(INVISIBLE);
            layoutItem.setLayoutParams(new LinearLayout.LayoutParams(400, 400));
        } else {
            textview.setText(item_list[position]);
            textviewtarif.setText(item_tarif[position]);
            textviewduree.setText(item_duree[position]);
            //layoutItem.setBackgroundColor(Color.MAGENTA);
            layoutItem.setVisibility(VISIBLE);
            layoutItem.setBackgroundColor(Color.parseColor("#ffffff"));
            //layoutItem.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
            layoutItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }

        //On retourne l'item créé.
        return layoutItem;
    }





    }