package com.gouiranlink.franois.gouiranlinkproject.Account;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
/**
 * Created by François on 20/07/2017.
 */


public class FaqAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<String> expandableListTitle;
        private HashMap<String, List<String>> expandableListDetail;

        public FaqAdapter(Context context, List<String> expandableListTitle,
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
                convertView = layoutInflater.inflate(R.layout.expandablelist_item_faq, null);
            }
            TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
            TextView text_expandedListItem = (TextView) convertView.findViewById(R.id.text_expandedListItem);

            String CurrentString = expandedListText;
            System.out.println("CurrentString : "+CurrentString );

            if (CurrentString != null) {
                try {

                    JSONObject jsonObj = new JSONObject(CurrentString);
                    String Titre = jsonObj.getString("Titre_texte");
                    String Text = jsonObj.getString("Texte");

                    expandedListTextView.setTypeface(null, Typeface.BOLD);
                    expandedListTextView.setText(Titre);
                    text_expandedListItem.setText(Text);

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            }

            System.out.println(CurrentString);
            //String[] separated = CurrentString.split("////");


            //expandedListTextView.setText(separated[0]);
            //expandedListTextView.setLayoutParams(new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.MATCH_PARENT));




            //duree_expandedListItem.setText(separated[2].substring(0,5) +" min");
            //duree_expandedListItem.setLayoutParams(new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.MATCH_PARENT));

            //text_expandedListItem.setText(separated[3]);


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