package com.example.franois.gouiranlinkproject.Professional_View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.R;

import java.util.ArrayList;

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

        public ServicesAdapter(Context context, int vg, int id, String[] item_list,String[] item_tarif,String[] item_duree){
        super(context,vg, id, item_list);
        this.context=context;
        groupid=vg;
        this.item_list=item_list;
        this.item_tarif=item_tarif;
        this.item_duree=item_duree;

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

        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            // Inflate the list_item.xml file if convertView is null

            ViewHolder viewHolder = new ViewHolder();
            if(rowView==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView= inflater.inflate(groupid, parent, false);



                viewHolder.textview= (TextView) rowView.findViewById(R.id.txt);
                viewHolder.textviewtarif= (TextView) rowView.findViewById(R.id.tarif);
                viewHolder.textviewduree= (TextView) rowView.findViewById(R.id.dureepresta);
                viewHolder.button= (Button) rowView.findViewById(R.id.bt);
                rowView.setTag(viewHolder);



            }else{

            }




// Set text to each TextView of ListView item
            ViewHolder holder = (ViewHolder) rowView.getTag();

            holder.textview.setText(item_list[position]);
            holder.textviewtarif.setText(item_tarif[position]);
            holder.textviewduree.setText(item_duree[position]);

            //holder.button.setText(item_list[position]);
            holder.button.setText("Je prend Rdv");
            return rowView;
        }

    public void clickMe(View v) {
        Toast.makeText(this.context, "clickMe", Toast.LENGTH_SHORT).show();
    }

    }