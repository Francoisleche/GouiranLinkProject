package com.gouiranlink.franois.gouiranlinkproject.Rendezvous;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by François on 26/06/2017.
 */

public class AutresPrestations2Adapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    private ArrayList<String> liste_selection = new ArrayList<String>();

    TextView text_expandedListItem;
    TextView prix_expandedListItem;
    TextView duree_expandedListItem;
    TextView discount_expandedListItem;
    TextView expandedListTextView;

    LinearLayout list_item2_linear;

    public AutresPrestations2Adapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail,ArrayList<String> liste_selection) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.liste_selection=liste_selection;
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
    public View getChildView(final int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        //View grid;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item2, null);
        }

        list_item2_linear = (LinearLayout) convertView.findViewById(R.id.list_item2_linear);
        expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
        text_expandedListItem = (TextView) convertView.findViewById(R.id.text_expandedListItem);
        prix_expandedListItem = (TextView) convertView.findViewById(R.id.prix_expandedListItem);
        duree_expandedListItem = (TextView) convertView.findViewById(R.id.duree_expandedListItem);
        discount_expandedListItem = (TextView) convertView.findViewById(R.id.discount_expandedListItem);
        final ImageView plus = (ImageView) convertView.findViewById(R.id.bouton_plus_expandable);

        String CurrentString = expandedListText;
        System.out.println(CurrentString);
        String[] separated = CurrentString.split("////");
        final boolean[] trouve = {false};
        final boolean[] trouve2 = {false};


        plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getListe_selection().size()>0) {
                    for(int i = 0; i< getListe_selection().size(); i++) {
                        String[] separated2_position = getListe_selection().get(i).split("////");
                        if (listPosition == Integer.parseInt(separated2_position[0]) && expandedListPosition == Integer.parseInt(separated2_position[1])) {
                            trouve2[0] = true;
                        }
                    }
                    if(trouve2[0]){
                        getListe_selection().remove(listPosition+"////"+expandedListPosition);
                        notifyDataSetChanged();
                    }else{
                        getListe_selection().add(listPosition+"////"+expandedListPosition);
                        notifyDataSetChanged();
                    }
                }else{
                    getListe_selection().add(listPosition+"////"+expandedListPosition);
                    notifyDataSetChanged();
                }

            }
        });



        if (getListe_selection().size()>0) {
            for(int i = 0; i< getListe_selection().size(); i++){
                String[] separated2_position = getListe_selection().get(i).split("////");
                System.out.println("OOOOoooooooooh :"+getListe_selection().get(i));
                if(listPosition == Integer.parseInt(separated2_position[0]) && expandedListPosition== Integer.parseInt(separated2_position[1])){
                    trouve[0] = true;
                }
            }
            if(trouve[0]){
                expandedListTextView.setTypeface(null, Typeface.BOLD);
                expandedListTextView.setText(separated[0]);
                prix_expandedListItem.setText(separated[1]+" €");
                //duree_expandedListItem.setText(separated[2]+" min");
                if(separated[2].substring(0,2).equals("00")){
                    duree_expandedListItem.setText(separated[2].substring(3,5) +" min");
                }else if(!separated[2].substring(0,2).equals("00") && separated[2].substring(3,5).equals("00")){
                    duree_expandedListItem.setText(Integer.parseInt(separated[2].substring(0,2)) +"h");
                }else{
                    duree_expandedListItem.setText(Integer.parseInt(separated[2].substring(0,2)) +"h" + separated[2].substring(3,5) +"");
                }
                text_expandedListItem.setText(separated[3]);
                plus.setImageResource(R.drawable.ic_check_circle_pink_24dp);
                discount_expandedListItem.setText(separated[4]);
                //list_item2_linear.setBackgroundColor(Color.BLUE);
                notifyDataSetChanged();
            }else{
                expandedListTextView.setTypeface(null, Typeface.BOLD);
                expandedListTextView.setText(separated[0]);
                prix_expandedListItem.setText(separated[1]+" €");
                //duree_expandedListItem.setText(separated[2]+" min");
                if(separated[2].substring(0,2).equals("00")){
                    duree_expandedListItem.setText(separated[2].substring(3,5) +" min");
                }else if(!separated[2].substring(0,2).equals("00") && separated[2].substring(3,5).equals("00")){
                    duree_expandedListItem.setText(Integer.parseInt(separated[2].substring(0,2)) +"h");
                }else{
                    duree_expandedListItem.setText(Integer.parseInt(separated[2].substring(0,2)) +"h" + separated[2].substring(3,5) +"");
                }
                text_expandedListItem.setText(separated[3]);
                plus.setImageResource(R.drawable.ic_plus_24dp);
                discount_expandedListItem.setText(separated[4]);
                //list_item2_linear.setBackgroundColor(Color.WHITE);
                notifyDataSetChanged();
            }
        }else{
            expandedListTextView.setTypeface(null, Typeface.BOLD);
            expandedListTextView.setText(separated[0]);
            prix_expandedListItem.setText(separated[1]+" €");
            //duree_expandedListItem.setText(separated[2]+" min");
            if(separated[2].substring(0,2).equals("00")){
                duree_expandedListItem.setText(separated[2].substring(3,5) +" min");
            }else if(!separated[2].substring(0,2).equals("00") && separated[2].substring(3,5).equals("00")){
                duree_expandedListItem.setText(Integer.parseInt(separated[2].substring(0,2)) +"h");
            }else{
                duree_expandedListItem.setText(Integer.parseInt(separated[2].substring(0,2)) +"h" + separated[2].substring(3,5) +"");
            }
            text_expandedListItem.setText(separated[3]);
            plus.setImageResource(R.drawable.ic_plus_24dp);
            discount_expandedListItem.setText(separated[4]);
            //list_item2_linear.setBackgroundColor(Color.WHITE);
            notifyDataSetChanged();
        }

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

    public ArrayList<String> getListe_selection2() {
        return liste_selection;
    }

    public ArrayList<String> getListe_selection() {
        return liste_selection;
    }

    public void setListe_selection(ArrayList<String> liste_selection) {
        this.liste_selection = liste_selection;
    }



}