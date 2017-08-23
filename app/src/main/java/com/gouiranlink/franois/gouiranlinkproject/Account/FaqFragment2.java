package com.gouiranlink.franois.gouiranlinkproject.Account;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by François on 21/08/2017.
 */


public class FaqFragment2 extends Fragment
{

    private Customer customer;
    FragmentManager fragmentManager;
    ExpandableListView expandableListView;


    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


    private String[] faqIdTitreList = new String[10];
    private String[] faqTitretitreList = new String[10];

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = (Customer)getArguments().getSerializable("Customer");
        }
        fragmentManager = getActivity().getSupportFragmentManager();



    }



    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_faq, container, false);


        json_parser_article(faq_api());


        expandableListView = (ExpandableListView) root.findViewById(R.id.expandableListView);
        //expandableListView.setAdapter(new ParentLevel());
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new ParentLevel(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        //expandableListDetail = ExpandableListDataPump.getData();

        /*
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new FaqAdapter2(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(
                new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPosition) {

                    }
                });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {



            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                return true;
            }
        });*/





        return root;

    }


    public String faq_api(){
        GetRequest getRequest = new GetRequest("http://gouiran.link.free.fr/getallfaq3.php");
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Reeeeeeeeeeeeeeeeeeeeeeesp");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }


    public void json_parser_article(String str){

        if (str != null) {
            try {

                JSONObject jsonObj = new JSONObject(str);
                JSONArray nb_article = jsonObj.getJSONArray("datas");

                faqIdTitreList = new String[nb_article.length()];
                faqTitretitreList = new String[nb_article.length()];

                for (int i = 0; i < nb_article.length(); i++) {

                    JSONObject c = nb_article.getJSONObject(i);

                    faqIdTitreList[i]=c.getString("id_titre");
                    faqTitretitreList[i]=c.getString("Titre_titre");
                    JSONArray nb_faq = c.getJSONArray("data");

                    ArrayList<String> list_data = new ArrayList<>();

                    for (int y = 0; y < nb_faq.length(); y++) {
                        //JSONObject c2 = nb_faq.getJSONObject(y);
                        String s = nb_faq.getString(y);
                        System.out.println("sssssssssssssssssssss : "+s);

                        list_data.add(s);

                    }
                    expandableListDetail.put(faqTitretitreList[i], list_data);


                }


            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }


    }







    public class ParentLevel extends BaseExpandableListAdapter
    {

        private Context context;
        private List<String> expandableListTitle;
        private HashMap<String, List<String>> expandableListDetail;

        public ParentLevel(Context context, List<String> expandableListTitle,
                          HashMap<String, List<String>> expandableListDetail) {
            this.context = context;
            this.expandableListTitle = expandableListTitle;
            this.expandableListDetail = expandableListDetail;
        }

        @Override
        public Object getChild(int arg0, int arg1)
        {
            return arg1;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent)
        {
            CustExpListview SecondLevelexplv = new CustExpListview(getActivity());
            SecondLevelexplv.setAdapter(new SecondLevelAdapter(getActivity(), expandableListTitle, expandableListDetail));
            SecondLevelexplv.setGroupIndicator(null);
            return SecondLevelexplv;
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition)
        {
            return this.expandableListTitle.get(groupPosition);
        }

        @Override
        public int getGroupCount()
        {
            return this.expandableListTitle.size();
        }

        @Override
        public long getGroupId(int groupPosition)
        {
            return groupPosition;
        }

        /*@Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent)
        {
            TextView tv = new TextView(getActivity());
            tv.setText("->FirstLevel");
            tv.setBackgroundColor(Color.BLUE);
            tv.setPadding(10, 7, 7, 7);

            return tv;
        }*/


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
        public boolean hasStableIds()
        {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }
    }





    public class CustExpListview extends ExpandableListView
    {

        int intGroupPosition, intChildPosition, intGroupid;

        public CustExpListview(Context context)
        {
            super(context);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
        {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(960, MeasureSpec.AT_MOST);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(600, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }







    public class SecondLevelAdapter extends BaseExpandableListAdapter
    {

        private Context context;
        private List<String> expandableListTitle,expandableListTitle2,expandableListTitle3;
        private HashMap<String, List<String>> expandableListDetail,expandableListDetail2,expandableListDetail3;

        public SecondLevelAdapter(Context context, List<String> expandableListTitle2,
                          HashMap<String, List<String>> expandableListDetail2) {
            this.context = context;
            this.expandableListTitle = expandableListTitle2;
            this.expandableListDetail = expandableListDetail2;

        }

        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition))
                .get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent){
        final String expandedListText = (String) getChild(groupPosition, childPosition);


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

            return convertView;
    }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition)
        {
            return this.expandableListTitle.get(groupPosition);
        }

        @Override
        public int getGroupCount()
        {
            return this.expandableListTitle.size();
        }

        @Override
        public long getGroupId(int groupPosition)
        {
            return groupPosition;
        }

        /*@Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent)
        {
            TextView tv = new TextView(getActivity());
            tv.setText("-->Second Level");
            tv.setPadding(12, 7, 7, 7);
            tv.setBackgroundColor(Color.RED);

            return tv;
        }*/
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent)
        {
            /*String listTitle = (String) getGroup(groupPosition);

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandablelist_item_faq, null);
            }
            TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);

            // TextView listItemTextView = (TextView) convertView.findViewById(R.id.nombre_item);


            String CurrentString = expandedListText;
            System.out.println("CurrentString : "+CurrentString );

            if (CurrentString != null) {
                try {

                    JSONObject jsonObj = new JSONObject(CurrentString);
                    String Titre = jsonObj.getString("Titre_texte");
                    String Text = jsonObj.getString("Texte");

                    expandedListTextView.setTypeface(null, Typeface.BOLD);
                    expandedListTextView.setText(Titre);

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }*/


            return convertView;
        }




        @Override
        public boolean hasStableIds() {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return true;
        }

    }
}
