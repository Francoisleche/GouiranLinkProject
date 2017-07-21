package com.gouiranlink.franois.gouiranlinkproject.Account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

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

/**
 * Created by François on 13/07/2017.
 */

public class FaqFragment extends Fragment{

    private Customer customer;
    FragmentManager fragmentManager;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


    private String[] faqIdTitreList = new String[10];
    private String[] faqTitretitreList = new String[10];
    private ArrayList<ArrayList<String>> faqIdTexteList = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> faqTitretexteList = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> faqTexteList = new ArrayList<ArrayList<String>>();

    public FaqFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        //expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new FaqAdapter(getActivity(), expandableListTitle, expandableListDetail);
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
                /*Toast.makeText(getContext(),expandableListTitle.get(groupPosition) + " List Collapsed."+ " " + iterateur_image,
                        Toast.LENGTH_SHORT).show();*/


            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                /*Toast.makeText(getContext(),expandableListTitle.get(groupPosition) + " -> "
                                + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition),
                        Toast.LENGTH_SHORT).show();
                return false;*/


                return true;
            }
        });





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


}
