package com.gouiranlink.franois.gouiranlinkproject.Rendezvous;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Resource;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by François on 15/06/2017.
 */

public class PrendreRdV1 extends Fragment {

    private Professional professional;
    private Professional_Product[] professional_product;
    private Professional_Product[] courante_liste_prestations;
    private ArrayList<Professional_Product> liste_prestations_selectionne = new ArrayList<Professional_Product>() {};
    private ArrayList<Professional_Product> liste_prestations_spinner = new ArrayList<Professional_Product>() {};
    private ArrayList<Professional_Product> liste_prestations_spinner2 = new ArrayList<Professional_Product>() {};
    private Customer customer;
    private String service_selectionne;
    private String service_selectionne_expandablelistview;
    private String horaire = "";

    private Resource[] ResourceProfessional;
    private Resource resource;

    private int position_list_clique;

    static String[] employe = new String[]{};
    static String[] services = new String[]{};

    private GetRequest getRequest;

    PrendreRdv1Adpater adapter;
    ArrayAdapter<Professional_Product>  adapter_services;

    private ArrayList<String> horaires_indisponibilites;

    TextView duree = null;
    TextView somme = null;


    double tariftotal = 0.00;
    int dureetotal = 0;
    int heuretotal = 0 ;
    int minutetotal = 0;

    public PrendreRdV1() {
        adapter_services = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Ooooooooooooooh Prendre RdV2");

        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            professional = (Professional) getArguments().getSerializable("Professionnal");
            professional_product = (Professional_Product[]) getArguments().getSerializable("ProfessionnalProduct");
            ResourceProfessional = (Resource[]) getArguments().getSerializable("ResourceProfessional");
            service_selectionne = (String) getArguments().getString("service");
            service_selectionne_expandablelistview = (String) getArguments().getString("service_selectionne_expandablelistview");
            position_list_clique = (int) getArguments().getInt("position_list_clique");
            customer = (Customer) getArguments().getSerializable("Customer");
        }

        /*for(int i=0;i<professional_product.length;i++){
            System.out.println("Ooooooooooooooooooh affiche un truc"+professional_product[i].getName());
            System.out.println("Ooooooooooooooooooh affiche un truc"+position_list_clique);
        }*/

        liste_prestations_selectionne.add(professional_product[position_list_clique]);
        System.out.println("Ooooooooooooooh Prendre RdV3");


        for(int i =0;i<professional_product.length;i++){
            liste_prestations_spinner.add(professional_product[i]);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_prendrerdv1, container, false);
        duree = (TextView) v.findViewById(R.id.duree_prestations);
        somme = (TextView) v.findViewById(R.id.somme_prestations);




        //Liste des prestations selectionnées
        final ListView listviewprendrerdv1 = (ListView) v.findViewById(R.id.listviewprendrerdv1);


        String[] items = new String[liste_prestations_selectionne.size()];
        for (int i = 0; i < liste_prestations_selectionne.size(); i++) {
            items[i] = liste_prestations_selectionne.get(i).getName();
        }

        //ADAPTER Listview FONCTIONNE BIEN
        adapter = new PrendreRdv1Adpater(getActivity(),liste_prestations_selectionne,liste_prestations_spinner);
        listviewprendrerdv1.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ArrayList<String> liste_prestations_selectionne2 = new ArrayList(){};
        for(int o=0;o<liste_prestations_selectionne.size();o++){
            liste_prestations_selectionne2.add(liste_prestations_selectionne.get(o).getName());
        }




        calcul_service();




        //Adapter spinner fonctionne bien
        final Spinner liste_services_prendrerdv1 = (Spinner) v.findViewById(R.id.liste_services_prendrerdv1);
        adapter_services = new ArrayAdapter<Professional_Product>(getContext(), android.R.layout.simple_spinner_dropdown_item,liste_prestations_spinner);
        adapter_services.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste_services_prendrerdv1.setAdapter(adapter_services);



        int selectionCurrent = liste_services_prendrerdv1.getSelectedItemPosition();

        liste_services_prendrerdv1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        Object item = parent.getItemAtPosition(pos);
                        System.out.println(item.toString());     //prints the text in spinner item.

                        Professional_Product p = new Professional_Product();
                        for(int i=0;i<professional_product.length;i++){
                            if(professional_product[i].getName().equals(item.toString())){
                                p=professional_product[i];
                            }
                        }




                        addItemsToList(p);
                        removeItemsToList2(p);






                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });











        //Liste des employés
        employe = new String[ResourceProfessional.length];
        for (int i = 0; i < ResourceProfessional.length; i++) {
            employe[i] = ResourceProfessional[i].getName() + ResourceProfessional[i].getSurname();
        }
        final Spinner spinner = (Spinner) v.findViewById(R.id.liste_employe_prendrerdv1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, employe);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);


        //Suivant
        final Button appuie_horaire = (Button) v.findViewById(R.id.suivant_prendrerdv1);
        appuie_horaire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "on a appuyé", Toast.LENGTH_SHORT).show();

                ArrayList<String> products = new ArrayList<String>();
                for (int i = 0; i < liste_prestations_selectionne.size(); i++) {
                    products.add(String.valueOf(liste_prestations_selectionne.get(i).getId()));
                }


                String id = spinner.getSelectedItem().toString();
                int position = 0;
                for (int i = 0; i < spinner.getCount(); i++) {
                    String value = spinner.getItemAtPosition(i).toString();
                    if (value.equals(id)) {
                        position = i;
                    }
                }


                ////////////////CALCUL TEMPS ET COUTS
                for(int i=0; i < liste_prestations_selectionne.size();i++){
                    tariftotal = tariftotal + liste_prestations_selectionne.get(i).getPrice();
                }

                for(int i = 0; i < liste_prestations_selectionne.size();i++){
                    StringTokenizer tokens = new StringTokenizer(liste_prestations_selectionne.get(i).getDuration(), ":");
                    String first = tokens.nextToken();
                    String second = tokens.nextToken();
                    String third = tokens.nextToken();
                    dureetotal = dureetotal + Integer.parseInt(first)*60+Integer.parseInt(second);
                }
                heuretotal = (int) dureetotal/60;
                minutetotal = (int) dureetotal%60;

                duree.setText(String.valueOf(heuretotal)+"h"+String.valueOf(minutetotal)+"min");
                somme.setText(String.valueOf(tariftotal)+liste_prestations_selectionne.get(0).getCurrency());

                /////////////////////////////////




                System.out.println("Spinnnnner position" + position);
                resource = ResourceProfessional[position];
                System.out.println("resource position" + resource.getName());

                Resource pivot = ResourceProfessional[position];
                ResourceProfessional[position] = ResourceProfessional[0];
                ResourceProfessional[0] = pivot;


                horaires_jsonparser(recherche_horaires(String.valueOf(professional.getId()), "2017-05-05T00:00:00Z", "2017-08-17T00:00:00Z", String.valueOf(ResourceProfessional[position].getId()), products));


                Fragment fragment = null;
                Bundle args = new Bundle();
                args.putSerializable("horaires_indisponibilites", horaires_indisponibilites);
                args.putSerializable("prix", tariftotal);
                args.putSerializable("duree", dureetotal);
                args.putSerializable("id_employe", id);
                args.putSerializable("ResourceProfessional", ResourceProfessional);


                args.putSerializable("liste_prestations_selectionne", liste_prestations_selectionne);
                args.putSerializable("courante_liste_prestations", courante_liste_prestations);


                args.putSerializable("Customer", customer);
                args.putSerializable("Professionnal", professional);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                fragment = new PrendreRdV2();
                fragment.setArguments(args);
                ft.replace(R.id.fragment_remplace, fragment).addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }
        });


        return v;
    }

    public void addItemsToList(Professional_Product p) {
        liste_prestations_selectionne.add(p);
        adapter.notifyDataSetChanged();
    }

    public void removeItemsToList(Professional_Product p) {
        liste_prestations_selectionne.remove(p);
        adapter.notifyDataSetChanged();
    }

    public void addItemsToList2(Professional_Product p) {
        liste_prestations_spinner.add(p);
        adapter_services.notifyDataSetChanged();
    }

    public void removeItemsToList2(Professional_Product p) {
        liste_prestations_spinner.remove(p);
        adapter_services.notifyDataSetChanged();
    }


    public void calcul_service(){
        services = new String[]{};
        //Autres Prestations
        services = new String[professional_product.length - liste_prestations_selectionne.size()];
        System.out.println("taille :" + (professional_product.length - liste_prestations_selectionne.size()));
        int compteur = 0;
        for (int i = 0; i < professional_product.length; i++) {
            boolean trouve = false;
            for (int j = 0; j < liste_prestations_selectionne.size(); j++) {
                System.out.println(" Oooooooooooh : " + liste_prestations_selectionne.get(j).getName());
                //System.out.println(" Oooooooooooh : " + professional_product[i].getName() + "     " + liste_prestations_selectionne.get(j).getName());
                if (professional_product[i].getName().equals(liste_prestations_selectionne.get(j).getName())){
                    trouve = true;
                }
            }
            if (!trouve) {
                services[compteur] = professional_product[i].getName();
                compteur++;
            }
        }
    }





    //Horaires
    public String recherche_horaires(String id_prof, String date_debut, String date_fin, String id_employe, ArrayList<String> id_product) {

        String json = "";
        for (int i = 0; i < id_product.size(); i++) {
            json = json + "&query[products][" + i + "]=" + id_product.get(i);
        }
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee JSON" + json);
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/booking-unavailabilities/professional/" +
                id_prof + "/?query[begin_date]=" + date_debut + "&query[end_date]=" + date_fin + "&query[resource]=" + id_employe + json);
        System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee JSON" + json);
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public void horaires_jsonparser(String jsonStr) {
        try {

            String recup_schedule = "{" + '"' + "data" + '"' + " : [{";
            String schedule1 = jsonStr.replace("[{", recup_schedule);
            String schedule2 = schedule1.replace("}]", "}]}");

            if (!jsonStr.equals("[]")) {
                JSONObject jsonObj = new JSONObject(schedule2);
                //JSONArray contacts = jsonObj.getJSONArray("data");
                JSONArray Professional_Shop_image = jsonObj.getJSONArray("data");


                horaires_indisponibilites = new ArrayList<String>();

                for (int j = 0; j < Professional_Shop_image.length(); j++) {
                    JSONObject p2 = Professional_Shop_image.getJSONObject(j);
                    String begin_date = p2.getString("begin_date");
                    String end_date = p2.getString("end_date");


                    String s = begin_date + "////" + end_date;
                    horaires_indisponibilites.add(s);
                }
            }

        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }


}
