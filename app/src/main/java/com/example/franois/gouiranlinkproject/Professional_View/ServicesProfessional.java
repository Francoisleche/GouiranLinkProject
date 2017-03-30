package com.example.franois.gouiranlinkproject.Professional_View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.Homepage.HomeFragment;
import com.example.franois.gouiranlinkproject.Object.Customer;
import com.example.franois.gouiranlinkproject.Object.Professional;
import com.example.franois.gouiranlinkproject.Object.Professional_Product;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.Rendezvous.PrendreRdv;
import com.example.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by François on 01/02/2017.
 */

public class ServicesProfessional extends Fragment {


    private ResearchTask mAuthTask = null;
    private GetRequest getRequest;


    public final static String AGE = "sdz.chapitreTrois.intent.example.AGE";
    private List<String> resultList;
    public Button button ;
    public ListView lstview;
    private Professional professional;
    private Professional_Product[] professional_product;
    private Customer customer;

    public ServicesProfessional(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            professional = (Professional)getArguments().getSerializable("Professionnal");
            professional_product = (Professional_Product[])getArguments().getSerializable("ProfessionnalProduct");
            customer = (Customer)getArguments().getSerializable("Customer");
        }
        System.out.println("Maaaaaaaaaaaaaaaarche bien :"+professional.toString());
        //
    }
/*
    public final static String AGE = "sdz.chapitreTrois.intent.example.AGE";
    private List<String> resultList;
    public Button button ;

    public ServicesProfessional(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_services_professional, container, false);


        ListView listView = (ListView) v.findViewById(R.id.maliste);

        ArrayAdapter<String> tableau = new ArrayAdapter<String>(
                listView.getContext(),R.layout.services);

        for(int i = 0 ; i<40;i++){
            tableau.add("Prestation n°"+i);

        }
        listView.setAdapter(tableau);

        final int taille = 40;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                for(int i=0;i<= taille ;i++){
                    Intent intent = new Intent(getActivity(),PrendreRdv.class);
                    intent.putExtra(AGE,position);
                    startActivity(intent);
                }
            }
        });


        button = (Button) v.findViewById(R.id.prendre_rdv);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                        "Yes",
                        Toast.LENGTH_SHORT).show();
                CamTestActivity cam = new CamTestActivity();
                Intent intent = new Intent(getActivity(),PrendreRdv.class);
                getActivity().startActivity(intent);

            }
        });


        return v;
    }*/




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_services_professional, container, false);

        lstview=(ListView) v.findViewById(R.id.maliste);


        String[] items = new String[professional_product.length];
        String[] items1 = new String[professional_product.length];
        String[] items2 = new String[professional_product.length];
        //Liste des categories
        /*
        if(professional.getProduct_categories() != null) {
            int category = professional.getProduct_categories().length;
            for (int i = 0; i < category; i++) {
                int services = professional.getProduct_categories()[i].getTags().length;
                for (int j = 0; j < services; j++) {

                    items[j] = ("Category :" + professional.getProduct_categories()[i].getName() + "\n Prestation n°" + professional.getProduct_categories()[i].getTags()[j].getLabel());

                }
            }
        }*/

        //Listes des produits / services
        if(professional_product.length != 0) {
            for (int i = 0; i < professional_product.length; i++) {

                    /*items[i] = ("Id :" + professional_product[i].getId() +
                            "\n Prestation : " + professional_product[i].getName() +
                            "\n Prix : " + professional_product[i].getPrice()+professional_product[i].getCurrency() +
                            "\n Durée : " + professional_product[i].getDuration());*/
                    items[i] = ("Prestation : " + professional_product[i].getName());
                          /*  "\n Prix : " + professional_product[i].getPrice()+professional_product[i].getCurrency() +
                            "\n Durée : " + professional_product[i].getDuration());*/

                items1[i] = ("Prix : " + professional_product[i].getPrice()+professional_product[i].getCurrency());
                items2[i] = ("Durée : " + professional_product[i].getDuration());
            }
        }


        else{
            for (int i = 0; i < 40; i++) {
                items[i] = ("Category :"+i);
            }
        }

        int layout = R.layout.services2;
        int id = R.id.txt;
        int tarif = R.id.tarif;
        int duree = R.id.dureepresta;

        int id2 = R.id.bt;

        //ServicesAdapter adapter=new ServicesAdapter(getActivity(),layout,id,items);
        ServicesAdapter adapter=new ServicesAdapter(getActivity(),layout,id,items,items1,items2);
        // Bind data to the ListView
        lstview.setAdapter(adapter);

        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Fragment fragment = null;
                Bundle args = new Bundle();
                System.out.println("CUSTOMER 2:"+ customer.getName());
            if(!customer.getName().equals("")){

                System.out.println("ON A CLIQUEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                Toast.makeText(getContext(), "An item of the ListView is clicked.", Toast.LENGTH_LONG).show();
                //for(int i=0;i<= 40 ;i++){
                //Intent intent = new Intent(getActivity(),PrendreRdv.class);
                //intent.putExtra(AGE,position);
                //startActivity(intent);
                //}



                args.putSerializable("Professionnal", professional);
                args.putSerializable("ProfessionnalProduct", professional_product);
                args.putSerializable("Customer", customer);
                args.putSerializable("service", "ServicesProfessional");
                args.putSerializable("position_list_clique", position);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                //getActivity().findViewById(R.id.fragment_services_professional).setVisibility(View.GONE);

                fragment = new PrendreRdv();
                fragment.setArguments(args);

                ft.replace(R.id.fragment_remplace, fragment).addToBackStack(null);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }else{
                Toast.makeText(getActivity(), "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                fragment = new HomeFragment();
                fragment.setArguments(args);
                ft.replace(R.id.fragment_remplace, fragment);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
            }
        });





    return v;
    }
/*
    public void clickMe(View view){
        Button bt=(Button)view;
        Toast.makeText(getActivity(), "Button "+bt.getText().toString(),Toast.LENGTH_LONG).show();
    }*/

    public void editActions(View v) {
        Toast.makeText(getActivity(), "Layout", Toast.LENGTH_SHORT).show();
    }

    public void clickMe(View v) {
        Toast.makeText(getActivity(), "clickMe", Toast.LENGTH_SHORT).show();
    }



    //recherche de la liste des produits avec les prix et tout
    public class ResearchTask extends AsyncTask<Void, Void, Boolean> {

        private String response = "";

        private final String mQuery;

        //private final GetRequest getRequest;
        //private final Boolean connected;

        ResearchTask(String query) {
            mQuery = query;

            getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1//professional-product/professional/?professional_id=");
            String resp = null;
            try {
                resp = getRequest.execute().get();
                System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                System.out.println(resp);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            response = resp;

        }

        String getResponse() {
            return response;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            //return (true);

            //return (connected);
            return true;
/*            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;*/
        }


        protected void onGetExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                //Intent i = new Intent();
                //Bundle b = new Bundle();
                //b.putString("token_access", "token_access");
                //b.putString("email", mEmail);
                //i.putExtras(b);
                //startActivity(i);
                //finish();
            } else {
                //mPasswordView.setError(getString(R.string.error_incorrect_password));
                //mPasswordView.requestFocus();
            }
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }

    }








}
