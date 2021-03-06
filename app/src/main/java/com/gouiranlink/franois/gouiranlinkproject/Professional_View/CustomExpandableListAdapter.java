package com.gouiranlink.franois.gouiranlinkproject.Professional_View;

/**
 * Created by François on 04/05/2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.R;

import java.util.HashMap;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);


        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
        TextView text_expandedListItem = (TextView) convertView.findViewById(R.id.text_expandedListItem);
        TextView prix_expandedListItem = (TextView) convertView.findViewById(R.id.prix_expandedListItem);
        TextView duree_expandedListItem = (TextView) convertView.findViewById(R.id.duree_expandedListItem);
        TextView discount_expandedListItem = (TextView) convertView.findViewById(R.id.discount_expandedListItem);

        String CurrentString = expandedListText;
        System.out.println(CurrentString);
        String[] separated = CurrentString.split("////");

        expandedListTextView.setTypeface(null, Typeface.BOLD);
        expandedListTextView.setText(separated[0]);
        //expandedListTextView.setLayoutParams(new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.MATCH_PARENT));

        prix_expandedListItem.setText(separated[1]+" €");
        //prix_expandedListItem.setLayoutParams(new LinearLayout.LayoutParams(800, LinearLayout.LayoutParams.MATCH_PARENT));


        System.out.println("A QUEL HEUUUUUURE : "+ separated[2].substring(0,2) + "   " +separated[2].substring(3,5));

        if(separated[2].substring(0,2).equals("00")){
            duree_expandedListItem.setText(separated[2].substring(3,5) +" min");
        }else if(!separated[2].substring(0,2).equals("00") && separated[2].substring(3,5).equals("00")){
            duree_expandedListItem.setText(Integer.parseInt(separated[2].substring(0,2)) +"h");
        }else{
            duree_expandedListItem.setText(Integer.parseInt(separated[2].substring(0,2)) +"h" + separated[2].substring(3,5) +"");
        }



        //duree_expandedListItem.setText(separated[2].substring(0,5) +" min");
        //duree_expandedListItem.setLayoutParams(new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.MATCH_PARENT));

        text_expandedListItem.setText(separated[3]);



        discount_expandedListItem.setText(separated[4]);


        /*int s =0;
        if(separated.length!=2) {
            System.out.println("separated[2] :::::::::::::" + separated[2]);
            s = (Integer.parseInt(separated[2]) * 100) / Integer.parseInt(separated[1]);
        }
        else
            s=0;
        discount_expandedListItem.setText("économisez jusqu'à " +String.valueOf(s)+"%");
        discount_expandedListItem.setLayoutParams(new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.MATCH_PARENT));*/

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);

       // TextView listItemTextView = (TextView) convertView.findViewById(R.id.nombre_item);


        if (listTitle.equals("PHOTOOOOOOOO TEAMS")) {
            listTitleTextView.setVisibility(INVISIBLE);
            listTitleTextView.setLayoutParams(new LinearLayout.LayoutParams(400, 400));

        } else {
            listTitleTextView.setText(listTitle);
            listTitleTextView.setVisibility(VISIBLE);
            listTitleTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            String text = "";
            if(expandableListDetail.get(listTitle).size() == 1)
                text = "1 prestation";
            else
                text = expandableListDetail.get(listTitle).size()+" prestations";

            //listItemTextView.setText(text);
            //listItemTextView.setVisibility(VISIBLE);
            //listItemTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}