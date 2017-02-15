package com.example.franois.gouiranlinkproject.Professional_View;

import android.content.ClipData;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.R;

import java.util.ArrayList;

import static android.R.attr.id;
import static android.R.attr.targetActivity;

/**
 * Created by François on 14/02/2017.
 */

public class ServicesAdapter extends ArrayAdapter<String> {

        int groupid;
        String[] item_list;
        ArrayList<String> desc;
        Context context;

        public ServicesAdapter(Context context, int vg, int id, String[] item_list){
            super(context,vg, id, item_list);
            this.context=context;
            groupid=vg;
            this.item_list=item_list;

        }
        // Hold views of the ListView to improve its scrolling performance
        static class ViewHolder {
            public TextView textview;
            public Button button;

        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            // Inflate the list_item.xml file if convertView is null
            if(rowView==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView= inflater.inflate(groupid, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.textview= (TextView) rowView.findViewById(R.id.txt);
                viewHolder.button= (Button) rowView.findViewById(R.id.bt);
                rowView.setTag(viewHolder);

            }
            // Set text to each TextView of ListView item
            ViewHolder holder = (ViewHolder) rowView.getTag();
            holder.textview.setText(item_list[position]);
            //holder.button.setText(item_list[position]);
            holder.button.setText("Je prend Rdv");
            return rowView;
        }

    public void clickMe(View v) {
        Toast.makeText(this.context, "clickMe", Toast.LENGTH_SHORT).show();
    }

    }