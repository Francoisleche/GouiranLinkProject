package com.gouiranlink.franois.gouiranlinkproject.Professional_View;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.Object.Comment;
import com.gouiranlink.franois.gouiranlinkproject.R;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by François on 26/04/2017.
 */

public class AvisAdapter extends ArrayAdapter<String> {

    int groupid;
    String[] item_list;
    Comment[] comment;
    ArrayList<String> desc;
    Context context;
    boolean premiere_fois;
    boolean[] hidden = null;

    public AvisAdapter(Context context, int vg, int id,String[] item_list,Comment[] comment){
        super(context,vg, id, item_list);
        this.context=context;
        groupid=vg;
        this.item_list=item_list;
        this.comment=comment;

        hidden = new boolean[item_list.length];
        for (int i = 0; i < item_list.length; i++)
            hidden[i] = false;


    }

    public AvisAdapter(Context context, int vg, int id, String[] item_list){
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
        TextView textviewnote = (TextView) layoutItem.findViewById(R.id.comment_note);
        TextView textviewsurname= (TextView) layoutItem.findViewById(R.id.comment_surname);
        TextView textviewdate= (TextView) layoutItem.findViewById(R.id.comment_date);
        TextView textviewprestation= (TextView) layoutItem.findViewById(R.id.comment_prestation);
        TextView textviewtext= (TextView) layoutItem.findViewById(R.id.comment_text);


        //(3) : Renseignement des valeurs
        textviewnote.setText(String.valueOf(comment[position].getGrade())+"/5");
        textviewsurname.setText(comment[position].getCustomer().getName());
        textviewdate.setText(comment[position].getCreated_at());
        String s = "";
        for(int i = 0;i<comment[position].getProfessional_products().length;i++){

            s = s + comment[position].getProfessional_products()[i].getName() + " , ";
        }
        textviewprestation.setText(s);
        textviewtext.setText(comment[position].getText());
        //System.out.println("COMBIEN YEN A ? "+item_list.length);
        //textviewnote.setText(item_list[position]);


        return layoutItem;
    }


}
