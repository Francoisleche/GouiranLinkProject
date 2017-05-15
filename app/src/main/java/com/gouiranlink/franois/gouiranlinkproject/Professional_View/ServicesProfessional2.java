package com.gouiranlink.franois.gouiranlinkproject.Professional_View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.gouiranlink.franois.gouiranlinkproject.Homepage.HomeFragment;
import com.gouiranlink.franois.gouiranlinkproject.Homepage.HomeFragment2;
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Resource;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.Rendezvous.PrendreRdv;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.DownloadImageTask;

/**
 * Created by François on 04/05/2017.
 */

public class ServicesProfessional2 extends Fragment {


    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    private Professional professional;
    private Professional_Product[] professional_product;
    private Resource[] ResourceProfessional;
    private Customer customer;
    private ArrayList<String> Shop_image;

    private int iterateur_image=0;

    public ServicesProfessional2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            professional = (Professional) getArguments().getSerializable("Professionnal");
            professional_product = (Professional_Product[]) getArguments().getSerializable("ProfessionnalProduct");
            ResourceProfessional = (Resource[])getArguments().getSerializable("ResourceProfessional");
            customer = (Customer) getArguments().getSerializable("Customer");
            Shop_image = (ArrayList<String>) getArguments().getSerializable("Shop_image");
            expandableListDetail = (HashMap<String, List<String>>) getArguments().getSerializable("ExpandableListDetail");
        }
        System.out.println("Maaaaaaaaaaaaaaaarche bien :" + professional.toString());
        //

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_services_professional2, container, false);

        final ImageView image = (ImageView) v.findViewById(R.id.image_service_pro);
        new DownloadImageTask(image).execute(Shop_image.get(0));

        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView);
        //expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(
                new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded." + " " + iterateur_image,
                        Toast.LENGTH_SHORT).show();
                if(iterateur_image<Shop_image.size()){
                    new DownloadImageTask(image).execute(Shop_image.get(iterateur_image++));
                }else{
                    iterateur_image =0;
                    new DownloadImageTask(image).execute(Shop_image.get(iterateur_image));
                }
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed."+ " " + iterateur_image,
                        Toast.LENGTH_SHORT).show();
                if(iterateur_image<Shop_image.size()){
                    new DownloadImageTask(image).execute(Shop_image.get(iterateur_image++));
                }else{
                    iterateur_image =0;
                    new DownloadImageTask(image).execute(Shop_image.get(iterateur_image));
                }

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


                if(!customer.getName().equals("")){

                    if(groupPosition != (0)) {

                        System.out.println("ON A CLIQUEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                        Toast.makeText(getContext(), "An item of the ListView is clicked.", Toast.LENGTH_LONG).show();

                        Bundle args = new Bundle();
                        args.putSerializable("Professionnal", professional);
                        args.putSerializable("ProfessionnalProduct", professional_product);
                        args.putSerializable("ResourceProfessional", ResourceProfessional);
                        args.putSerializable("Customer", customer);
                        args.putSerializable("service", "ServicesProfessional");

                        String CurrentString = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                        String[] separated = CurrentString.split("////");
                        //args.putSerializable("service_selectionne_expandablelistview", (Serializable) expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition));
                        args.putSerializable("service_selectionne_expandablelistview", (Serializable) separated[0]);
                        args.putSerializable("position_list_clique", groupPosition);
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        //getActivity().findViewById(R.id.fragment_services_professional).setVisibility(View.GONE);
                        Fragment fragment=null;
                        fragment = new PrendreRdv();
                        fragment.setArguments(args);

                        ft.replace(R.id.fragment_remplace, fragment).addToBackStack(null);

                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.commit();
                    }
                    else{
                        Toast.makeText(getActivity(), "Ne pas appuyer sur le premier", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Bundle args = new Bundle();
                    Toast.makeText(getActivity(), "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment fragment=null;
                    fragment = new HomeFragment2();
                    fragment.setArguments(args);
                    ft.replace(R.id.fragment_remplace, fragment);

                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();

                }

                return true;
            }
        });


        return v;
    }

}
