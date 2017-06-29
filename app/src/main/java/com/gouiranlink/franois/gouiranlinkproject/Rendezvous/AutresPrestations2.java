package com.gouiranlink.franois.gouiranlinkproject.Rendezvous;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Resource;
import com.gouiranlink.franois.gouiranlinkproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by François on 26/06/2017.
 */

public class AutresPrestations2 extends Fragment{

    private ArrayList<Professional_Product> liste_prestations_selectionne_PrendreRdV1 = new ArrayList<Professional_Product>() {};
    private Professional professional;
    private Professional_Product[] professional_product;
    private Resource[] ResourceProfessional;
    private int position_list_clique;
    private String service_selectionne;
    private String service_selectionne_expandablelistview;
    private Customer customer;



    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<String, GroupInfo>();
    //private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();

    ArrayList<String> liste_service_selectionne = new ArrayList<String>() {};
    ArrayList<String> liste_service_selectionne_AutresPrestations2 = new ArrayList<String>() {};


    public AutresPrestations2(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            expandableListDetail = (HashMap<String, List<String>>) getArguments().getSerializable("ExpandableListDetail");
            professional = (Professional) getArguments().getSerializable("Professionnal");
            professional_product = (Professional_Product[]) getArguments().getSerializable("ProfessionnalProduct");
            ResourceProfessional = (Resource[]) getArguments().getSerializable("ResourceProfessional");
            service_selectionne = (String) getArguments().getString("service");
            service_selectionne_expandablelistview = (String) getArguments().getString("service_selectionne_expandablelistview");
            position_list_clique = (int) getArguments().getInt("position_list_clique");
            customer = (Customer) getArguments().getSerializable("Customer");
            liste_prestations_selectionne_PrendreRdV1 = (ArrayList<Professional_Product>) getArguments().getSerializable("liste_prestations_selectionne_PrendreRdV1");
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_autresprestations2, container, false);

        for(int i=0;i<expandableListDetail.size();i++){
            System.out.println("Detaiiiiiiiiil : "  + expandableListDetail.keySet());
        }


        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView_autresprestations);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new AutresPrestations2Adapter(getActivity(), expandableListTitle, expandableListDetail,liste_service_selectionne);
        expandableListView.setAdapter(expandableListAdapter);
        /*expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                //liste_service_selectionne_AutresPrestation2.add(p);
                v.setBackgroundColor(getResources().getColor(R.color.green));
                parent.getChildAt(childPosition).setBackgroundColor(Color.parseColor("#5bc7d2"));

                return true;
            }
        });*/
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Toast.makeText(getContext(), " Clicked on :: " + expandableListTitle.get(groupPosition)
                        + "/" + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_LONG).show();

                String CurrentString = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                System.out.println(CurrentString);

                //v.getChildAt(childPosition).setBackgroundColor(getResources().getColor(R.color.GouiranDarkPink));

                return false;
            }
        });



        Button valider_autresprestations = (Button) v.findViewById(R.id.valider_autresprestations);
        valider_autresprestations.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ArrayList<String> tt = liste_service_selectionne;
                for(int i =0;i<tt.size();i++){
                    String[] separated_position = tt.get(i).split("////");
                    String CurrentString = expandableListDetail.get(expandableListTitle.get(Integer.parseInt(separated_position[0]))).get(Integer.parseInt(separated_position[1]));
                    String[] separated_position2 = CurrentString.split("////");

                        for(int j=0;j<professional_product.length;j++){
                            //String[] separated_position2 = liste_service_selectionne_AutresPrestation2.get(i).split("////");
                            System.out.println("Ooooooooooooooh RETOUR Prendre RdV1 : "+separated_position[0] +"   "+ professional_product[j].getName()+ "    " + CurrentString);
                            if(separated_position2[0].equals(professional_product[j].getName())){
                                liste_prestations_selectionne_PrendreRdV1.add(professional_product[j]);
                            }
                        }

                    //Remove expandable List

                    /*List<String> child = expandableListDetail.get(expandableListTitle.get(Integer.parseInt(separated_position[0])));
                    child.remove(Integer.parseInt(separated_position[1]));*/
                }
                for(int i =0;i<tt.size();i++){
                    String[] separated_position = tt.get(i).split("////");
                    String CurrentString = expandableListDetail.get(expandableListTitle.get(Integer.parseInt(separated_position[0]))).get(Integer.parseInt(separated_position[1]));
                    //Remove expandable List
                    List<String> child = expandableListDetail.get(expandableListTitle.get(Integer.parseInt(separated_position[0])));
                    child.remove(Integer.parseInt(separated_position[1]));
                }



                Bundle args = new Bundle();

                args.putSerializable("Professionnal", professional);
                args.putSerializable("ProfessionnalProduct", professional_product);
                args.putSerializable("ResourceProfessional", ResourceProfessional);
                args.putSerializable("Customer", customer);
                args.putSerializable("service", "AutresPrestations2");
                args.putSerializable("service_selectionne_expandablelistview", service_selectionne_expandablelistview);
                args.putSerializable("position_list_clique", position_list_clique);
                args.putSerializable("ExpandableListDetail", expandableListDetail);
                args.putSerializable("liste_prestations_selectionne_PrendreRdV1", liste_prestations_selectionne_PrendreRdV1);
                //String CurrentString = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment=null;
                fragment = new PrendreRdV1();
                fragment.setArguments(args);
                ft.replace(R.id.content_frame, fragment);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }
        });





        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.e("coucouc","coucou");
                }
                return true;
            }
        });





        return v;
    }

    public class GroupInfo {

        private String name;
        private ArrayList<ChildInfo> list = new ArrayList<ChildInfo>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<ChildInfo> getProductList() {
            return list;
        }

        public void setProductList(ArrayList<ChildInfo> productList) {
            this.list = productList;
        }

    }

    public class ChildInfo {

        private String sequence = "";
        private String name = "";

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }


    @Override
    public void onViewCreated(View view ,Bundle savedInstanceState) {
        getActivity().findViewById(R.id.content_frame).setVisibility(View.VISIBLE);

    }



}
