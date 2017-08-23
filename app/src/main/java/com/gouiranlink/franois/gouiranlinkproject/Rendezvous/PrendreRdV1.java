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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Product_Category_WithoutTree;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Resource;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by François on 15/06/2017.
 */

public class PrendreRdV1 extends Fragment {

    ArrayList<String> liste_service_selectionne_AutresPrestation2 = null;

    private Professional professional;
    private Professional_Product[] professional_product;

    private ArrayList<Professional_Product> liste_prestations_selectionne_PrendreRdV1 = new ArrayList<Professional_Product>() {};
    private ArrayList<Professional_Product> liste_prestations_spinner = new ArrayList<Professional_Product>() {};
    private ArrayList<Professional_Product> liste_prestations_spinner2 = new ArrayList<Professional_Product>() {};
    private Customer customer;
    private String service_selectionne;
    private Professional_Product[] courante_liste_prestations;
    private String service_selectionne_expandablelistview;
    private String horaire = "";
    private String heurefinal = "";

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

    LinearLayout prendredrdv1;
    LinearLayout autresprestations2;


    ///////////////////////////////AUTRES PRESTATIONS ///////////////
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter2;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

    private LinkedHashMap<String, AutresPrestations2.GroupInfo> subjects = new LinkedHashMap<String, AutresPrestations2.GroupInfo>();
    //private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();

    ArrayList<String> liste_service_selectionne = new ArrayList<String>() {};
    ArrayList<String> liste_service_selectionne_AutresPrestations2 = new ArrayList<String>() {};
    ////////////////////////////////////////////////////////////////





    public PrendreRdV1() {
        adapter_services = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Ooooooooooooooh Prendre RdV2");
        String s ="";
        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            professional = (Professional) getArguments().getSerializable("Professionnal");
            professional_product = (Professional_Product[]) getArguments().getSerializable("ProfessionnalProduct");
            ResourceProfessional = (Resource[]) getArguments().getSerializable("ResourceProfessional");
            service_selectionne = (String) getArguments().getString("service");
            service_selectionne_expandablelistview = (String) getArguments().getString("service_selectionne_expandablelistview");
            position_list_clique = (int) getArguments().getInt("position_list_clique");
            customer = (Customer) getArguments().getSerializable("Customer");
            s = (String) getArguments().getString("ExpandableListDetail");


            if(service_selectionne.equals("ServicesProfessional")){
                System.out.println("1");
                //expandableListDetail.remove("PHOTOOOOOOOO TEAMS");
               // liste_prestations_selectionne_PrendreRdV1.add(professional_product[position_list_clique]);
            }else{
                System.out.println("2");
                liste_prestations_selectionne_PrendreRdV1 = (ArrayList<Professional_Product>) getArguments().getSerializable("liste_prestations_selectionne_PrendreRdV1");
            }
        }

        products_jsonparser(recherche_product(String.valueOf(professional.getId())));
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        String[] separated_position = s.split("////");

        System.out.println("separated_position : "+ separated_position[0]+"  , "+separated_position[1]);
        //Supprime sur la deuxieme expandable listview l'element selectionne dans la precedente
        List<String> child = expandableListDetail.get(expandableListTitle.get(Integer.parseInt(separated_position[0])-1));

        String[] separated_position2 = (child.get(Integer.parseInt(separated_position[1]))).split("////");
        for (int j = 0; j < professional_product.length; j++) {
            //String[] separated_position2 = liste_service_selectionne_AutresPrestation2.get(i).split("////");
            //System.out.println("Ooooooooooooooh RETOUR Prendre RdV1 : " + separated_position[0] + "   " + professional_product[j].getName() + "    " + CurrentString);
            if (separated_position2[0].equals(professional_product[j].getName())) {
                liste_prestations_selectionne_PrendreRdV1.add(professional_product[j]);
            }
        }

        child.remove(Integer.parseInt(separated_position[1]));



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_prendrerdv1, container, false);

        prendredrdv1 = (LinearLayout) v.findViewById(R.id.prendredrdv1);
        autresprestations2 = (LinearLayout) v.findViewById(R.id.autresprestations2);


        duree = (TextView) v.findViewById(R.id.duree_prestations);
        somme = (TextView) v.findViewById(R.id.somme_prestations);

        Button ajouter_autre_prestation = (Button) v.findViewById(R.id.ajouter_autre_prestation);
        ajouter_autre_prestation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                prendredrdv1.setVisibility(true ? View.GONE : View.VISIBLE);
                autresprestations2.setVisibility(true ? View.VISIBLE : View.GONE);

                liste_service_selectionne = new ArrayList<String>();
                expandableListAdapter2 = new AutresPrestations2Adapter(getActivity(), expandableListTitle, expandableListDetail,liste_service_selectionne);
                System.out.println("Espace ou pas : "+expandableListDetail.get("Homme").get(0).toString());
                System.out.println("Espace ou pas : "+expandableListDetail.get("Homme").get(1).toString());
                expandableListView.setAdapter(expandableListAdapter2);

            }
        });


        //Liste des prestations selectionnées
        final ListView listviewprendrerdv1 = (ListView) v.findViewById(R.id.listviewprendrerdv1);


        String[] items = new String[liste_prestations_selectionne_PrendreRdV1.size()];
        for (int i = 0; i < liste_prestations_selectionne_PrendreRdV1.size(); i++) {
            items[i] = liste_prestations_selectionne_PrendreRdV1.get(i).getName();
        }

        //ADAPTER Listview FONCTIONNE BIEN
        adapter = new PrendreRdv1Adpater(getActivity(), liste_prestations_selectionne_PrendreRdV1,professional_product,expandableListDetail);
        listviewprendrerdv1.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ArrayList<String> liste_prestations_selectionne2 = new ArrayList(){};
        for(int o = 0; o< liste_prestations_selectionne_PrendreRdV1.size(); o++){
            liste_prestations_selectionne2.add(liste_prestations_selectionne_PrendreRdV1.get(o).getName());
        }


        //calcul_service();


        //Adapter spinner fonctionne bien
        /*final Spinner liste_services_prendrerdv1 = (Spinner) v.findViewById(R.id.liste_services_prendrerdv1);
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
                });*/











        //Liste des employés
        employe = new String[ResourceProfessional.length];
        for (int i = 0; i < ResourceProfessional.length; i++) {
            //employe[i] = ResourceProfessional[i].getName() + ResourceProfessional[i].getSurname();
            employe[i] = ResourceProfessional[i].getName();
        }

        final Spinner spinner = (Spinner) v.findViewById(R.id.liste_employe_prendrerdv1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity() , android.R.layout.simple_spinner_item , employe);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);


        //Suivant
        final Button appuie_horaire = (Button) v.findViewById(R.id.suivant_prendrerdv1);
        appuie_horaire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                tariftotal = 0.00;
                dureetotal = 0;
                heurefinal = "";

                Toast.makeText(getApplicationContext(), "on a appuyé", Toast.LENGTH_SHORT).show();

                ArrayList<String> products = new ArrayList<String>();
                for (int i = 0; i < liste_prestations_selectionne_PrendreRdV1.size(); i++) {
                    products.add(String.valueOf(liste_prestations_selectionne_PrendreRdV1.get(i).getId()));
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
                for(int i = 0; i < liste_prestations_selectionne_PrendreRdV1.size(); i++){
                    tariftotal = tariftotal + liste_prestations_selectionne_PrendreRdV1.get(i).getPrice();
                }

                for(int i = 0; i < liste_prestations_selectionne_PrendreRdV1.size(); i++){
                    StringTokenizer tokens = new StringTokenizer(liste_prestations_selectionne_PrendreRdV1.get(i).getDuration(), ":");
                    String first = tokens.nextToken();
                    String second = tokens.nextToken();
                    String third = tokens.nextToken();
                    dureetotal = dureetotal + Integer.parseInt(first)*60+Integer.parseInt(second);
                }
                heuretotal = (int) dureetotal/60;
                minutetotal = (int) dureetotal%60;

                duree.setText(String.valueOf(heuretotal)+"h"+String.valueOf(minutetotal)+"min");
                System.out.println("Heure Finaaaaaaaaaal");
                heurefinal = (String.valueOf(heuretotal)+"h"+String.valueOf(minutetotal)+"min");
                System.out.println("Heure Finaaaaaaaaaal" + heurefinal);
                somme.setText(String.valueOf(tariftotal)+ liste_prestations_selectionne_PrendreRdV1.get(0).getCurrency());

                /////////////////////////////////




                System.out.println("Spinnnnner position" + position);
                resource = ResourceProfessional[position];
                System.out.println("resource position" + resource.getName());

                Resource pivot = ResourceProfessional[position];
                ResourceProfessional[position] = ResourceProfessional[0];
                ResourceProfessional[0] = pivot;



                String format = "dd/MM/yyyy H:mm:ss";
                java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
                java.util.Date date = new java.util.Date();
                String datedispo =  formater.format( date );

                String date1 = datedispo.substring(6,10)+"-"+datedispo.substring(3,5)+"-"+datedispo.substring(0,2)+"T00:00:00Z";
                int annee_calcul = Integer.parseInt(datedispo.substring(6,10));
                int mois_calcul = Integer.parseInt(datedispo.substring(3,5))+3;
                String annee_cal = "";
                String mois_cal = "";
                if(mois_calcul>12){
                    mois_calcul = (int) mois_calcul/12;
                    annee_calcul++;
                }
                if(mois_calcul<10){
                    mois_cal = "0" + mois_calcul;
                }else{
                    mois_cal = String.valueOf(mois_calcul);
                }
                annee_cal = String.valueOf(annee_calcul);


                String date2 = annee_cal+"-"+mois_cal+"-"+datedispo.substring(0,2)+"T00:00:00Z";



                System.out.println("daaaaaaaaaaate 1 : " + date1);
                System.out.println("daaaaaaaaaaate 2 : " + date2);
                //horaires_jsonparser(recherche_horaires(String.valueOf(professional.getId()), "2017-05-05T00:00:00Z", "2017-08-17T00:00:00Z", String.valueOf(ResourceProfessional[position].getId()), products));
                horaires_jsonparser(recherche_horaires(String.valueOf(professional.getId()), date1, date2, String.valueOf(ResourceProfessional[position].getId()), products));


                Fragment fragment = null;
                Bundle args = new Bundle();
                args.putSerializable("horaires_indisponibilites", horaires_indisponibilites);
                args.putSerializable("prix", tariftotal);
                //args.putSerializable("duree", dureetotal);
                System.out.println("Heure Finaaaaaaaaaal" + heurefinal);
                args.putSerializable("duree", heurefinal);
                args.putSerializable("id_employe", id);
                args.putSerializable("ResourceProfessional", ResourceProfessional);


                args.putSerializable("liste_prestations_selectionne_PrendreRdV1", liste_prestations_selectionne_PrendreRdV1);
                args.putSerializable("courante_liste_prestations", courante_liste_prestations);


                args.putSerializable("Customer", customer);
                args.putSerializable("Professionnal", professional);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                fragment = new PrendreRdV2();
                fragment.setArguments(args);
                ft.replace(R.id.content_frame, fragment).addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });





        ////////////////////////////////////// AUTRES PRESTATIONS ////////////////////////////////////
        for(int i=0;i<expandableListDetail.size();i++){
            System.out.println("Detaiiiiiiiiil : "  + expandableListDetail.keySet());
        }




        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView_autresprestations);


        liste_service_selectionne = new ArrayList<String>() {};

        //expandableListAdapter2 = new AutresPrestations2Adapter(getActivity(), expandableListTitle, expandableListDetail,liste_service_selectionne);
        //expandableListView.setAdapter(expandableListAdapter2);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                //Toast.makeText(getContext(), " Clicked on :: " + expandableListTitle.get(groupPosition)
                //        + "/" + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_LONG).show();

                String CurrentString = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                System.out.println(CurrentString);

                return false;
            }
        });



        Button valider_autresprestations = (Button) v.findViewById(R.id.valider_autresprestations);
        valider_autresprestations.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                ArrayList<String> tt = liste_service_selectionne;

                if(tt.size()!=0) {
                    //System.out.println("ttttttttttttttttttttttttttttttttttttt 0 : "+tt.get(0));
                    for (int i = 0; i < tt.size(); i++) {
                        System.out.println("ttttttttttttttttttttttttttttttttttttt : " + i + "    ," + tt.get(i));
                        String[] separated_position = tt.get(i).split("////");
                        System.out.println("ttttttttttttttttttttttttttttttttttttt : " + separated_position[0] + "   " + separated_position[1]);
                        String CurrentString = expandableListDetail.get(expandableListTitle.get(Integer.parseInt(separated_position[0]))).get(Integer.parseInt(separated_position[1]));
                        String[] separated_position2 = CurrentString.split("////");

                        for (int j = 0; j < professional_product.length; j++) {
                            //String[] separated_position2 = liste_service_selectionne_AutresPrestation2.get(i).split("////");
                            System.out.println("Ooooooooooooooh RETOUR Prendre RdV1 : " + separated_position[0] + "   " + professional_product[j].getName() + "    " + CurrentString);
                            if (separated_position2[0].equals(professional_product[j].getName())) {
                                liste_prestations_selectionne_PrendreRdV1.add(professional_product[j]);
                            }
                        }


                        for(int p=0;p<liste_prestations_selectionne_PrendreRdV1.size();p++){
                            System.out.println("Ajouter dans liste_prestations_selectionne_PrendreRdV1 : "+liste_prestations_selectionne_PrendreRdV1.get(p).getName());
                        }

                    }

                    System.out.println("Taille de tt : " + tt.size());
                    int y = tt.size();
                    for (int i = 0; tt.size()>0; i++) {
                        calcul_plusbas(tt);
                    }
                }





                prendredrdv1.setVisibility(true ? View.VISIBLE : View.GONE);
                autresprestations2.setVisibility(true ? View.GONE : View.VISIBLE);

                adapter.notifyDataSetChanged();

            }
        });
        ///////////////////////////////////////////////////////////////////////////////




        //Retour arrière géré
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if(prendredrdv1.getVisibility() == View.GONE){
                        prendredrdv1.setVisibility(true ? View.VISIBLE : View.GONE);
                        autresprestations2.setVisibility(true ? View.GONE : View.VISIBLE);
                        return true;
                    }else{
                        return false;
                    }
                }
                return true;
            }
        });


        return v;
    }

    public void calcul_plusbas(ArrayList<String> tt) {
        int group=0;
        int plus_bas = 0;
        int parent_a_supprimer =0;
        for(int i=0;i<tt.size();i++){
            System.out.println("Calcul PLUSBAS tt : "+tt.get(i)+ "    ,Taille de tt : "+tt.size() + "   ,i :"+i);
            String[] separated_position = tt.get(i).split("////");
            if(Integer.parseInt(separated_position[1])>=plus_bas){
                plus_bas=Integer.parseInt(separated_position[1]);
                group=i;
                parent_a_supprimer=Integer.parseInt(separated_position[0]);
            }
        }
        System.out.println("Calcul PLUSBAS : group : "+group+"    , plusbas : "+plus_bas+"  , parent_a_supprimer :"+parent_a_supprimer);
        List<String> child = expandableListDetail.get(expandableListTitle.get(parent_a_supprimer));
        child.remove(plus_bas);
        tt.remove(group);
        String s = group +"////"+plus_bas;
    }

    public void addItemsToList(Professional_Product p) {
        liste_prestations_selectionne_PrendreRdV1.add(p);
        adapter.notifyDataSetChanged();
    }

    public void removeItemsToList(Professional_Product p) {
        liste_prestations_selectionne_PrendreRdV1.remove(p);
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


    /*public void calcul_service(){
        services = new String[]{};
        //Autres Prestations
        services = new String[professional_product.length - liste_prestations_selectionne_PrendreRdV1.size()];
        System.out.println("taille :" + (professional_product.length - liste_prestations_selectionne_PrendreRdV1.size()));
        int compteur = 0;
        for (int i = 0; i < professional_product.length; i++) {
            boolean trouve = false;
            for (int j = 0; j < liste_prestations_selectionne_PrendreRdV1.size(); j++) {
                System.out.println(" Oooooooooooh : " + liste_prestations_selectionne_PrendreRdV1.get(j).getName());
                //System.out.println(" Oooooooooooh : " + professional_product[i].getName() + "     " + liste_prestations_selectionne_PrendreRdV1.get(j).getName());
                if (professional_product[i].getName().equals(liste_prestations_selectionne_PrendreRdV1.get(j).getName())){
                    trouve = true;
                }
            }
            if (!trouve) {
                services[compteur] = professional_product[i].getName();
                compteur++;
            }
        }
    }*/





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

    @Override
    public void onViewCreated(View view ,Bundle savedInstanceState) {
        getActivity().findViewById(R.id.content_frame).setVisibility(View.VISIBLE);

    }


    public String recherche_product(String query) {
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional-product/professional/"+Integer.parseInt(query)+"/?query[show_all]=true");

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

    public ArrayList<Professional_Product> products_jsonparser(String jsonStr) {
        //Professional_Product product = new Professional_Product();
        ArrayList<Professional_Product> liste_product = new ArrayList<Professional_Product>();
        List<String> liste_coifure_femme = new ArrayList<String>();
        List<String> liste_coifure_femme2 = new ArrayList<String>();
        List<String> liste_bien_etre = new ArrayList<String>();
        List<String> liste_bien_etre2 = new ArrayList<String>();
        List<String> liste_beaute = new ArrayList<String>();
        List<String> liste_beaute2 = new ArrayList<String>();
        List<String> liste_homme = new ArrayList<String>();
        List<String> liste_homme2 = new ArrayList<String>();
        List<String> photo = new ArrayList<String>();

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray contacts = jsonObj.getJSONArray("data");

            for (int i = 0; i < contacts.length(); i++) {
                Professional_Product PremierProfessionalProduct2 = new Professional_Product();
                Product product = new Product();
                Product_Category_WithoutTree product_category_withoutTree = new Product_Category_WithoutTree();
                JSONObject p2 = contacts.getJSONObject(i);
                String id_resource_manager = p2.getString("id");
                String name_resource_manager = p2.getString("name");
                String price_resource_manager = p2.getString("price");
                String currency_resource_manager = p2.getString("currency");

                String duration_resource_manager = p2.getString("duration");
                String description_resource_manager = p2.getString("description");

                String deleted_at_resource_manager = p2.getString("deleted_at");
                String created_at_resource_manager = p2.getString("created_at");
                String updated_at_resource_manager = p2.getString("updated_at");

                String product_resource_manager = p2.getString("product");
                String discounts_resource_manager = p2.getString("discounts");

                System.out.println("AFFICHEEEEEER au moins un truc");
                String discounts = " ";
                System.out.println("AFFICHEEEEEER DISCOUNT :" + discounts_resource_manager);
                if(!discounts_resource_manager.equals("[]")){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateTime = dateFormat.format(new Date());

                    JSONArray discounts_tableau = p2.getJSONArray("discounts");
                    System.out.println("AFFICHEEEEEER DISCOUNT :"+discounts_tableau.get(0).toString());

                    for (int y = 0; y < discounts_tableau.length(); y++) {
                        JSONObject p3 = discounts_tableau.getJSONObject(y);
                        String id_discount = p3.getString("id");
                        String begin_date_discount = p3.getString("begin_date");
                        String end_date_discount = p3.getString("end_date");
                        String is_percentage_discount = p3.getString("is_percentage");
                        String amount_discount = p3.getString("amount");
                        String currency_discount = p3.getString("currency");

                        String date1 = currentDateTime.substring(0,10);
                        String date2 = begin_date_discount.substring(0,10);
                        String date3 = end_date_discount.substring(0,10);

                        int dat1 = Integer.parseInt(date1.replace("-",""));
                        int dat2 = Integer.parseInt(date2.replace("-",""));
                        int dat3 = Integer.parseInt(date3.replace("-",""));

                        System.out.println("AFFICHEEEEEER DISCOUNT1 :" + "dat1="+dat1+"***dat2="+dat2+"***dat3="+dat3);
                        if(dat1 >= dat2 && dat1 <= dat3) {
                            System.out.println("AFFICHEEEEEER DISCOUNT2");
                            if (is_percentage_discount.equals("true")) {
                                discounts = "- " + amount_discount + "%";
                            } else {
                                discounts = "- " + amount_discount + "€";
                            }
                        }


                    }
                    //discounts = discounts_tableau.getJSONObject(0).getString("amount");
                    System.out.println("AFFICHEEEEEER DISCOUNT :" + discounts);
                }



                //System.out.println("LES PRODUCT RESOURCE MANAGER PRODUCT :"+product_resource_manager);


                //Remplissage des produits
                PremierProfessionalProduct2.setId(Integer.parseInt(id_resource_manager));
                PremierProfessionalProduct2.setName(name_resource_manager);
                PremierProfessionalProduct2.setPrice(Double.parseDouble(price_resource_manager));
                PremierProfessionalProduct2.setCurrency(currency_resource_manager);
                PremierProfessionalProduct2.setDuration(duration_resource_manager);
                PremierProfessionalProduct2.setDescription(description_resource_manager);
                PremierProfessionalProduct2.setDescription(description_resource_manager);
                PremierProfessionalProduct2.setDeleted_at(deleted_at_resource_manager);
                PremierProfessionalProduct2.setCreated_at(created_at_resource_manager);
                PremierProfessionalProduct2.setUpdated_at(updated_at_resource_manager);


                System.out.println("LES PRODUCT RESOURCE MANAGER PRODUCT 2222 :" + product_resource_manager);
                JSONObject json2 = p2.getJSONObject("product");
                int product_id = (int) json2.get("id");

                //////////////NE pas utiliser, ceci est l'ancien nom
                String product_name = (String) json2.get("name");
                /////////////////////////////////////////////


                String description = (String) json2.get("description");
                if(description.equals("")){
                    description = " ";
                }

                JSONObject json3 = json2.getJSONObject("category");
                int product_category_id = (int) json3.get("id");
                //String product_category_name = (String) json3.get("name");
                String product_category_name = (String) json3.getJSONObject("parent").get("name");
                String product_category_created_at = (String) json3.get("created_at");
                String product_category_updated_at = (String) json3.get("updated_at");

                product_category_withoutTree.setId(product_category_id);
                product_category_withoutTree.setName(product_category_name);
                product_category_withoutTree.setCreated_at(product_category_created_at);
                product_category_withoutTree.setUpdated_at(product_category_updated_at);

                product.setDescription(description);
                product.setId(product_id);
                product.setName(product_name);
                product.setCategory(product_category_withoutTree);

                PremierProfessionalProduct2.setProduct(product);


                // pourquoi //// ? parce que / est deja utiliser dans les noms de products
                if(product_category_name.equals("Coiffure Femme")){
                    liste_coifure_femme.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description_resource_manager+"////"+discounts);
                   // liste_coifure_femme2.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                }else if(product_category_name.equals("Bien-Être")){
                    liste_bien_etre.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description_resource_manager+"////"+discounts);
                   // liste_bien_etre2.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                }else if(product_category_name.equals("Beauté")){
                    liste_beaute.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description_resource_manager+"////"+discounts);
                   // liste_beaute2.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                }else if(product_category_name.equals("Homme")){
                    liste_homme.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description_resource_manager+"////"+discounts);
                    //liste_homme2.add(name_resource_manager+"////"+price_resource_manager+"////"+duration_resource_manager+"////"+description+"////"+discounts);
                }


            }

            if(!liste_coifure_femme.isEmpty()) {
                expandableListDetail.put("Coiffure Femme", liste_coifure_femme);
            }
            if(!liste_bien_etre.isEmpty()) {
                expandableListDetail.put("Bien-Être", liste_bien_etre);
            }
            if(!liste_beaute.isEmpty()) {
                expandableListDetail.put("Beauté", liste_beaute);
            }
            if(!liste_homme.isEmpty()) {
                expandableListDetail.put("Homme", liste_homme);
            }

        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

        return liste_product;
    }





}
