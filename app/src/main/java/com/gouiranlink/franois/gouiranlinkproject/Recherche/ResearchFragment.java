package com.gouiranlink.franois.gouiranlinkproject.Recherche;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Product_Category_Tag;
import com.gouiranlink.franois.gouiranlinkproject.Object.Product_Category_WithoutTree;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Schedule;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Subscription_Type;
import com.gouiranlink.franois.gouiranlinkproject.Professional_View.InformationsProfessional;
import com.gouiranlink.franois.gouiranlinkproject.Professional_View.ProfessionalView;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_APPEND;
import static com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.BaseFragment.ARGS_INSTANCE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResearchFragment extends Fragment implements ProfessionalView.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<ArrayList<String>> donnée_geolocalise = new ArrayList<ArrayList<String>>();
    private View mProgressView;
    private LinearLayout recherche_layout;
    private View mLoginFormView;


    private Filter filter = new Filter();

    private Professional PremierProfessionnal = new Professional();
    private Product_Category_WithoutTree[] PremierProfessionnalProduits;
    private Product_Category_Tag[] PremierProfessionnalProduitsTag;

    private Professional_Schedule[] ProfessionnalGenericSchedule;

    private Professional_Product[] PremierProfessionalProduct;

    private Customer customer;
    private String token;

    private ResearchTask mAuthTask = null;
    private EditText recherche;
    private EditText recherche_ville;
    private TextView resultat1, resultat2, resultat3, resultat4, resultat5, resultat6;
    private Button carte;
    private GetRequest getRequest;

    private FileOutputStream fileOutputStream = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String[] resultat_rech = new String[5];

    private ImageView loupe_recherche;
    private ImageView loupe_recherche_ville;

    private ListView listView;
    private boolean ville_selectionne = false;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * //* @param param1 Parameter 1.
     * //* @param param2 Parameter 2.
     *
     * @return A new instance of fragment ResearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResearchFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        ResearchFragment firstFragment = new ResearchFragment();
        firstFragment.setArguments(args);
        return firstFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            customer = (Customer) getArguments().getSerializable("Customer");
            token = (String) getArguments().getString("token");
            System.out.println("Toooooooooooooken" + token);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_research, container, false);


        //mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.research_progress);
        recherche_layout = (LinearLayout) view.findViewById(R.id.recherche_layout);

        recherche = (EditText) view.findViewById(R.id.bonjour_recherche);
        recherche_ville = (EditText) view.findViewById(R.id.recherche_ville);
        loupe_recherche = (ImageView) view.findViewById(R.id.loupe_recherche_ville);
        loupe_recherche_ville = (ImageView) view.findViewById(R.id.loupe_recherche_ville);
        //resultat1 = (TextView) view.findViewById(R.id.textView_1);
        carte = (Button) view.findViewById(R.id.carte_research);

        /*resultat2 = (TextView) view.findViewById(R.id.textView_11);
        resultat3 = (TextView) view.findViewById(R.id.textView_12);
        resultat4 = (TextView) view.findViewById(R.id.textView_13);
        resultat5 = (TextView) view.findViewById(R.id.textView_14);
        resultat6 = (TextView) view.findViewById(R.id.textView_15);*/


        listView = (ListView) view.findViewById(R.id.mesresultats);

        /*listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);

            }});*/


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Fragment[] fragment = {null};
                final FragmentTransaction[] ft = {null};

                final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Downloading Infos...", true);


                Thread timer = new Thread() {
                    @Override
                    public void run() {

                        try {

                            try {
                                fileOutputStream = getContext().openFileOutput("GouiranLink", MODE_APPEND);
                                fileOutputStream.write(listView.getItemAtPosition(position).toString().getBytes());
                                fileOutputStream.write("`".getBytes());
                                fileOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            System.out.println(listView.getItemAtPosition(position).toString());


                            ResearchTask3 researchTask3 = new ResearchTask3(listView.getItemAtPosition(position).toString());
                            String ls2 = "";
                            ArrayList<String> recup2 = new ArrayList<String>();
                            ls2 = researchTask3.getResponse();
                            //setInfo(ls2);
                            recup2 = jsonparser2(ls2);


                            if (PremierProfessionnal.getProfessional_subscription_type().getName().equals("Full")) {
                                //Fragment fragment = null;
                                Bundle args = new Bundle();
                                args.putSerializable("Professionnal", PremierProfessionnal);
                                args.putSerializable("ProfessionnalProduct", PremierProfessionalProduct);
                                args.putSerializable("Customer", customer);
                                args.putSerializable("token", token);
                                System.out.println("CUSTOMER :" + customer.getName());


                                FragmentManager fm = getFragmentManager();
                                //FragmentTransaction ft = fm.beginTransaction();
                                ft[0] = fm.beginTransaction();
                                //getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);

                                fragment[0] = new ProfessionalView();
                                fragment[0].setArguments(args);

                                //ft.replace(R.id.fragment_remplace, fragment).addToBackStack(null);

                                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                //ft.commit();
                            } else if (PremierProfessionnal.getProfessional_subscription_type().getName().equals("Free")) {
                                //Fragment fragment = null;
                                Bundle args = new Bundle();
                                args.putSerializable("Professionnal", PremierProfessionnal);
                                args.putSerializable("ProfessionnalProduct", PremierProfessionalProduct);
                                args.putSerializable("Customer", customer);
                                args.putSerializable("token", token);

                                FragmentManager fm = getFragmentManager();
                                //FragmentTransaction ft = fm.beginTransaction();
                                ft[0] = fm.beginTransaction();
                                //getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);

                                fragment[0] = new InformationsProfessional();
                                fragment[0].setArguments(args);

                                //ft.replace(R.id.fragment_remplace, fragment).addToBackStack(null);

                                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                //ft.commit();
                            }


                            ft[0].replace(R.id.fragment_remplace, fragment[0]).addToBackStack(null);
                            ft[0].setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            ft[0].commit();
                            ringProgressDialog.cancel();
                        } catch (Exception e) {
                            System.out.println("PASSSSSSSSSSSSSSSSSSSSSSSSSSSSE PAS");
                            e.printStackTrace();
                        }

                    }
                };
                timer.start();
                ringProgressDialog.setCancelable(true);

                //Obligé de faire ça après !
                getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);


            }
        });


        recherche_ville.addTextChangedListener(new TextWatcher() {
            boolean isTyping = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            private Timer timer = new Timer();
            private final long DELAY = 2500;

            @Override
            public void afterTextChanged(Editable s) {
                if (!isTyping) {
                    isTyping = true;
                }

                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isTyping = false;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /*ResearchTask researchTask = new ResearchTask(recherche.getText().toString(), 5);
                                String ls = "";
                                ArrayList<String> recup = new ArrayList<String>();
                                ls = researchTask.getResponse();
                                recup = jsonparser(ls);


                                String[] results = new String[] {};
                                final List<String> resultsList = new ArrayList<String>(Arrays.asList(results));
                                ArrayAdapter<String> tableau = new ArrayAdapter<String>(getActivity(), R.layout.services, resultsList);
                                listView.setAdapter(tableau);
                                for (int i = 0; i < recup.size(); i++) {
                                    resultsList.add(recup.get(i));
                                    tableau.notifyDataSetChanged();
                                }*/

                                final Dialog dialog = new Dialog(getContext());
                                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                                View contentView = layoutInflater.inflate(R.layout.research_popup, null);
                                final LinearLayout root = (LinearLayout) contentView.findViewById(R.id.proRootLayout);
                                System.out.println("Ooooooooooooooooooh tablea ?" + recherche_ville.getText().toString());
                                ResearchTask4 researchTask = new ResearchTask4(recherche_ville.getText().toString());
                                String ls = "";
                                final ArrayList<String> recup2 = new ArrayList<String>();
                                ls = researchTask.getResponse();
                                //recup = jsonparser(ls);

                                String recup = "{" + '"' + "Recup" + '"' + " : [";
                                String ahbon = ls.replace("[", recup);
                                String ahbon2 = ahbon.replace("]", "]}");


                                JSONObject jsonObj = null;
                                try {
                                    jsonObj = new JSONObject(ahbon2);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JSONArray contacts = null;
                                try {
                                    contacts = jsonObj.getJSONArray("Recup");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // looping through All Contacts
                                for (int i = 0; i < contacts.length(); i++) {
                                    JSONObject c = null;
                                    try {
                                        c = contacts.getJSONObject(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    String id = null;
                                    try {
                                        id = c.getString("value");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    recup2.add(id);
                                    System.out.println("VAAAAAAAAAAAAAALUE " + id);


                                    // Getting JSON Array node
                                    //JSONArray contacts = jsonObj.getJSONArray("contacts");

                                    // looping through All Contacts
                                }


                                ListView listview = (ListView) root.findViewById(R.id.mesresultats_ville);

                                String[] results = new String[]{};
                                final List<String> resultsList = new ArrayList<String>(Arrays.asList(results));
                                ArrayAdapter<String> tableau = new ArrayAdapter<String>(getActivity(), R.layout.services, resultsList);
                                listview.setAdapter(tableau);
                                for (int i = 0; i < recup2.size(); i++) {
                                    resultsList.add(recup2.get(i));
                                    tableau.notifyDataSetChanged();
                                }
                                if (!recup2.isEmpty()) {
                                    dialog.setContentView(root);
                                    dialog.show();
                                }

                                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        recherche_ville.setText(recup2.get(position));
                                        dialog.cancel();
                                    }
                                });
                                System.out.println("LSSSSSSSSSS" + ls);


                            }
                        });
                    }
                }, DELAY);
            }
        });


        recherche.addTextChangedListener(new TextWatcher() {
            boolean isTyping = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            private Timer timer = new Timer();
            private final long DELAY = 2500;

            @Override
            public void afterTextChanged(Editable s) {
                if (!isTyping) {
                    isTyping = true;
                }


                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isTyping = false;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                /*ResearchTask2 researchTask = new ResearchTask2(recherche.getText().toString(), 5);
                                String ls = "";
                                ArrayList<ArrayList <String>> recup = new ArrayList<ArrayList<String>>();
                                ls = researchTask.getResponse();
                                recup = jsonparser3(ls);

                                String[] results = new String[] {};
                                final List<String> resultsList = new ArrayList<String>(Arrays.asList(results));
                                ArrayAdapter<String> tableau = new ArrayAdapter<String>(getActivity(), R.layout.services, resultsList);
                                listView.setAdapter(tableau);
                                for (int i = 0; i < recup.size(); i++) {
                                    resultsList.add(recup.get(i).get(3));
                                    tableau.notifyDataSetChanged();
                                }*/
                                if (!recherche_ville.getText().toString().isEmpty()) {

                                    // Récupération de la liste de String
                                    ResearchTask researchTask = new ResearchTask(recherche.getText().toString(), 5);
                                    String ls = "";
                                    ArrayList<String> recup = new ArrayList<String>();
                                    ls = researchTask.getResponse();
                                    System.out.println("ALllllllllllllo");
                                    recup = jsonparser(ls);


                                    System.out.println("ALllllllllllllo");
                                    String[] results = new String[]{};
                                    final List<String> resultsList = new ArrayList<String>(Arrays.asList(results));
                                    ArrayAdapter<String> tableau = new ArrayAdapter<String>(getActivity(), R.layout.services, resultsList);
                                    listView.setAdapter(tableau);
                                    int g = 0;
                                    for (int i = 0; i < recup.size(); i++) {
                                        resultsList.add(recup.get(i));
                                        tableau.notifyDataSetChanged();
                                        g++;
                                    }

                                    // Récupération des données de géoloc pour chacun des éléments
                                    ArrayList<ArrayList<String>> recup2 = new ArrayList<ArrayList<String>>();
                                    for (int j = 0; j < g; j++) {
                                        System.out.println("LATAIIIIIIILLLLLLLEESTDE :" + recup.get(j).length());
                                        System.out.println("LAPERSOOOOOONEESTDE :" + recup.get(j));
                                        String ls2 = "";
                                        ResearchTask2 researchTask2 = new ResearchTask2(recup.get(j), 1);
                                        ls2 = researchTask2.getResponse();
                                        System.out.println("repooooooooooooooooonse" + ls2);
                                        //ArrayList<ArrayList <String>> vue_personnes_sur_carte = new ArrayList<ArrayList <String>>();
                                        if (ls2 != null) {
                                            if (ls2.contains("{")) {
                                                try {

                                                    JSONObject jsonObj = new JSONObject(ls2);
                                                    JSONArray contacts = jsonObj.getJSONArray("data");

                                                    for (int i = 0; i < contacts.length(); i++) {
                                                        if (i == 0) {
                                                            ArrayList<String> personnes = new ArrayList<String>();
                                                            JSONObject c = contacts.getJSONObject(i);

                                                            String id = c.getString("id");
                                                            String geoloc_latitude = c.getString("geoloc_latitude");
                                                            String geoloc_longitude = c.getString("geoloc_longitude");
                                                            String shop_name = c.getString("shop_name");
                                                            System.out.println("shop_nameshop_nameshop_nameshop_name" + shop_name);
                                                            personnes.add(id);
                                                            personnes.add(geoloc_latitude);
                                                            personnes.add(geoloc_longitude);
                                                            personnes.add(shop_name);
                                                            recup2.add(personnes);
                                                        }
                                                    }

                                                } catch (final JSONException e) {
                                                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                                                }
                                            }
                                        }
                                    }


                                    //ls = researchTask.getResponse();
                                    //recup2 = jsonparser3(ls);

                                    donnée_geolocalise = recup2;
                                    for (int i = 0; i < donnée_geolocalise.size(); i++) {
                                        System.out.println("ppppppppppppppppppppppppppppppp" + donnée_geolocalise.get(i).get(3));
                                    }
                                    //ArrayList<ArrayList <String>> vue_personnes_sur_carte = new ArrayList<ArrayList <String>>();


                                } else {
                                    Toast.makeText(getActivity(), "Rentrer une ville ou un lieu", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }, DELAY);
            }
        });


        initUI(view);
        return view;
    }


    //PROGRESS DIALOG
    private void setInfo(String s) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Downloading Infos...", true);
                ringProgressDialog.setCancelable(true);
            }
        });

        ArrayList<String> recup2 = new ArrayList<String>();
        recup2 = jsonparser2(s);
/// Your other code goes here...

    }


    private void initUI(View v) {

        Button button1 = (Button) v.findViewById(R.id.carte_research);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                String[] tableau = new String[5];
                tableau[0] = "Bonjour";
                tableau[1] = "Bonjour1";
                tableau[2] = "Bonjour2";
                tableau[3] = "Bonjour3";
                tableau[4] = "Bonjour4";


                boolean recherche_boolean = false;
                System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOh comprend pas" + recherche.getText());
                if (recherche.getText().toString().isEmpty()) {
                    recherche_boolean = false;
                } else {
                    recherche_boolean = true;
                }
                //recherche_boolean
                args.putBoolean("recherche_boolean", recherche_boolean);
                args.putSerializable("tableau", tableau);
                args.putSerializable("donnée_geolocalise", donnée_geolocalise);
                Intent intent = new Intent(getActivity(), MapPane.class);
                intent.putExtras(args);
                startActivity(intent);
            }
        });

        Button button2 = (Button) v.findViewById(R.id.filtre_research);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View contentView = layoutInflater.inflate(R.layout.filtres_popup, null);
                final LinearLayout root = (LinearLayout) contentView.findViewById(R.id.proRootLayout2);
                System.out.println("Ooooooooooooooooooh filtres ?");

                dialog.setContentView(root);
                dialog.show();

                final CheckBox acceptation_automatique_rdv = (CheckBox) contentView.findViewById(R.id.acceptation_auto);
                final CheckBox plusreserve = (CheckBox) contentView.findViewById(R.id.plusreserve);
                final Spinner spinner = (Spinner) contentView.findViewById(R.id.liste_specialite);
                final CheckBox type1 = (CheckBox) contentView.findViewById(R.id.type1);
                final CheckBox type2 = (CheckBox) contentView.findViewById(R.id.type2);
                final CalendarView jourouverture = (CalendarView) contentView.findViewById(R.id.jourouverture);

                Button accepter_filtre = (Button) contentView.findViewById(R.id.accepter_filtre);
                accepter_filtre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (acceptation_automatique_rdv.isChecked()) {
                            filter.setAcceptation_Automatique_RDV(true);
                        }
                        if (plusreserve.isChecked()) {
                            filter.setLes_plusreserve_semaine(true);
                        }
                        /*if(spinner.isClickable()){
                            filter.setSpecialite(spinner.getSelectedItem().toString());
                        }*/
                        if (type1.isChecked() && type2.isChecked()) {
                            filter.setType("Tout");
                        } else if (type1.isChecked() && !type2.isChecked()) {
                            filter.setType("Salon");
                        } else if (!type1.isChecked() && type2.isChecked()) {
                            filter.setType("Domicile");
                        } else {
                            filter.setType("");
                        }

                        dialog.cancel();

                    }
                });


                //Intent intent = new Intent(getActivity(), FilterActivity.class);
                //startActivity(intent);
            }
        });

        loupe_recherche_ville.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!recherche_ville.getText().toString().isEmpty()) {

                    // Récupération de la liste de String
                    ResearchTask researchTask = new ResearchTask(recherche.getText().toString(), 5);
                    String ls = "";
                    ArrayList<String> recup = new ArrayList<String>();
                    ls = researchTask.getResponse();
                    System.out.println("ALllllllllllllo");
                    recup = jsonparser(ls);


                    System.out.println("ALllllllllllllo");
                    String[] results = new String[]{};
                    final List<String> resultsList = new ArrayList<String>(Arrays.asList(results));
                    ArrayAdapter<String> tableau = new ArrayAdapter<String>(getActivity(), R.layout.services, resultsList);
                    listView.setAdapter(tableau);
                    int g = 0;
                    for (int i = 0; i < recup.size(); i++) {
                        resultsList.add(recup.get(i));
                        tableau.notifyDataSetChanged();
                        g++;
                    }

                    // Récupération des données de géoloc pour chacun des éléments
                    ArrayList<ArrayList<String>> recup2 = new ArrayList<ArrayList<String>>();
                    for (int j = 0; j < g; j++) {
                        System.out.println("LATAIIIIIIILLLLLLLEESTDE :" + recup.get(j).length());
                        System.out.println("LAPERSOOOOOONEESTDE :" + recup.get(j));
                        String ls2 = "";
                        ResearchTask2 researchTask2 = new ResearchTask2(recup.get(j), 1);
                        ls2 = researchTask2.getResponse();
                        System.out.println("repooooooooooooooooonse" + ls2);
                        //ArrayList<ArrayList <String>> vue_personnes_sur_carte = new ArrayList<ArrayList <String>>();
                        if (ls2 != null) {
                            if (ls2.contains("{")) {
                                try {

                                    JSONObject jsonObj = new JSONObject(ls2);
                                    JSONArray contacts = jsonObj.getJSONArray("data");

                                    for (int i = 0; i < contacts.length(); i++) {
                                        if (i == 0) {
                                            ArrayList<String> personnes = new ArrayList<String>();
                                            JSONObject c = contacts.getJSONObject(i);

                                            String id = c.getString("id");
                                            String geoloc_latitude = c.getString("geoloc_latitude");
                                            String geoloc_longitude = c.getString("geoloc_longitude");
                                            String shop_name = c.getString("shop_name");
                                            System.out.println("shop_nameshop_nameshop_nameshop_name" + shop_name);
                                            personnes.add(id);
                                            personnes.add(geoloc_latitude);
                                            personnes.add(geoloc_longitude);
                                            personnes.add(shop_name);
                                            recup2.add(personnes);
                                        }
                                    }

                                } catch (final JSONException e) {
                                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                                }
                            }
                        }
                    }


                    //ls = researchTask.getResponse();
                    //recup2 = jsonparser3(ls);

                    donnée_geolocalise = recup2;
                    for (int i = 0; i < donnée_geolocalise.size(); i++) {
                        System.out.println("ppppppppppppppppppppppppppppppp" + donnée_geolocalise.get(i).get(3));
                    }
                    //ArrayList<ArrayList <String>> vue_personnes_sur_carte = new ArrayList<ArrayList <String>>();


                } else {
                    Toast.makeText(getActivity(), "Rentrer une ville ou un lieu", Toast.LENGTH_SHORT).show();
                }

            }
        });


        loupe_recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!recherche_ville.getText().toString().isEmpty()) {
                    // Récupération de la liste de String
                    ResearchTask researchTask = new ResearchTask(recherche.getText().toString(), 5);
                    String ls = "";
                    ArrayList<String> recup = new ArrayList<String>();
                    ls = researchTask.getResponse();
                    System.out.println("ALllllllllllllo");
                    recup = jsonparser(ls);

                    System.out.println("ALllllllllllllo");
                    String[] results = new String[]{};
                    final List<String> resultsList = new ArrayList<String>(Arrays.asList(results));
                    ArrayAdapter<String> tableau = new ArrayAdapter<String>(getActivity(), R.layout.services, resultsList);
                    listView.setAdapter(tableau);
                    int g = 0;
                    for (int i = 0; i < recup.size(); i++) {
                        resultsList.add(recup.get(i));
                        tableau.notifyDataSetChanged();
                        g++;
                    }

                    // Récupération des données de géoloc pour chacun des éléments
                    ArrayList<ArrayList<String>> recup2 = new ArrayList<ArrayList<String>>();
                    for (int j = 0; j < g; j++) {
                        System.out.println("LATAIIIIIIILLLLLLLEESTDE :" + recup.get(j).length());
                        System.out.println("LAPERSOOOOOONEESTDE :" + recup.get(j));
                        String ls2 = "";
                        ResearchTask2 researchTask2 = new ResearchTask2(recup.get(j), 1);
                        ls2 = researchTask2.getResponse();
                        System.out.println("repooooooooooooooooonse" + ls2);
                        //ArrayList<ArrayList <String>> vue_personnes_sur_carte = new ArrayList<ArrayList <String>>();
                        if (ls2 != null) {
                            if (ls2.contains("{")) {
                                try {

                                    JSONObject jsonObj = new JSONObject(ls2);
                                    JSONArray contacts = jsonObj.getJSONArray("data");

                                    for (int i = 0; i < contacts.length(); i++) {
                                        if (i == 0) {
                                            ArrayList<String> personnes = new ArrayList<String>();
                                            JSONObject c = contacts.getJSONObject(i);

                                            String id = c.getString("id");
                                            String geoloc_latitude = c.getString("geoloc_latitude");
                                            String geoloc_longitude = c.getString("geoloc_longitude");
                                            String shop_name = c.getString("shop_name");
                                            System.out.println("shop_nameshop_nameshop_nameshop_name" + shop_name);
                                            personnes.add(id);
                                            personnes.add(geoloc_latitude);
                                            personnes.add(geoloc_longitude);
                                            personnes.add(shop_name);
                                            recup2.add(personnes);
                                        }
                                    }

                                } catch (final JSONException e) {
                                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                                }
                            }
                        }
                    }


                    //ls = researchTask.getResponse();
                    //recup2 = jsonparser3(ls);

                    donnée_geolocalise = recup2;
                    for (int i = 0; i < donnée_geolocalise.size(); i++) {
                        System.out.println("ppppppppppppppppppppppppppppppp" + donnée_geolocalise.get(i).get(3));
                    }
                    //ArrayList<ArrayList <String>> vue_personnes_sur_carte = new ArrayList<ArrayList <String>>();

                } else {
                    Toast.makeText(getActivity(), "Rentrer une ville ou un lieu", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        //mEmailView.setError(null);
        //mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        //String email = mEmailView.getText().toString();
        //String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        /*if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }*/

        // Check for a valid email address.
        /*if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            //mAuthTask = new UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        System.out.println("00000000000000000000000000000000000000000");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

           /* mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });*/
            System.out.println("11111111111111111111111111111111111111111111");
            FrameLayout fragment_remplace = (FrameLayout) getActivity().findViewById(R.id.fragment_remplace);
            fragment_remplace.setVisibility(show ? View.GONE : View.VISIBLE);

            //recherche_layout.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            //recherche_layout.setVisibility(show ? View.GONE : View.VISIBLE);
            recherche_layout.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recherche_layout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            //mImageLoginView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            System.out.println("22222222222222222222222222222222222222222222");
            recherche_layout.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            recherche_layout.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            //mImageLoginView.setVisibility(show ? View.VISIBLE : View.GONE);
            //mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public ArrayList<String> jsonparser(String jsonStr) {
        String ls = "";
        ArrayList<String> specialite = new ArrayList<>();


        if (jsonStr != null) {
            if (jsonStr.contains("{")) {
                try {
                    //JSONArray jsonObj2 = new JSONArray(jsonStr);



                            /*JSONObject reader = new JSONObject(jsonStr);
                            JSONObject sys  = reader.getJSONObject("value");
                            String country = sys.getString("value");*/

                    String recup = "{" + '"' + "Recup" + '"' + " : [";
                    String ahbon = jsonStr.replace("[", recup);
                    String ahbon2 = ahbon.replace("]", "]}");


                    JSONObject jsonObj = new JSONObject(ahbon2);

                    JSONArray contacts = jsonObj.getJSONArray("Recup");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("value");

                        //JSONObject reader = new JSONObject(ahbon2);
                        //JSONObject sys = reader.getJSONObject("Recup");
                        //String country = sys.getString("value");

                        specialite.add(id);
                        System.out.println("VAAAAAAAAAAAAAALUE " + id);


                        // Getting JSON Array node
                        //JSONArray contacts = jsonObj.getJSONArray("contacts");

                        // looping through All Contacts
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                specialite.add(ls);
                specialite.add(ls);
                specialite.add(ls);
                specialite.add(ls);
                specialite.add(ls);
            }
        } else {
            System.out.println("C'est nul");
            Log.e(TAG, "Couldn't get json from server.");
        }

        for (int i = 0; i < specialite.size(); i++) {
            System.out.println("SPECIALITE : " + specialite.get(i));
        }

        return specialite;
    }

    public ArrayList<String> jsonparser2(String jsonStr) {


        /*getActivity().runOnUiThread(new Runnable()
        {
            public void run()
            {
                final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Downloading Infos...", true);
                ringProgressDialog.setCancelable(true);
            }
        });*/


        String ls = "";
        ArrayList<String> specialite = new ArrayList<>();
        ArrayList<String> premierprofessionel = new ArrayList<>();
        ArrayList<String> listeprestationpremierprofessionnel = new ArrayList<>();
        ArrayList<String> listelabelprestationpremierprofessionnel = new ArrayList<>();

        if (jsonStr != null) {
            if (jsonStr.contains("{")) {
                try {


                    //String recup = "{" + '"' + "Recup" + '"' + " : [";
                    //String ahbon = jsonStr.replace("[", recup);
                    //String ahbon2 = ahbon.replace("]", "]}");


                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray contacts = jsonObj.getJSONArray("data");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String image = c.getString("shop_images");
                        String id_professional = c.getString("id");

                        if (i == 0) {
                            String shop_images = c.getString("shop_images");
                            String resource_manager = c.getString("resource_manager");
                            String avg_grade = c.getString("avg_grade");
                            String comments_count = c.getString("comments_count");
                            premierprofessionel.add(shop_images);
                            premierprofessionel.add(resource_manager);
                            premierprofessionel.add(avg_grade);
                            premierprofessionel.add(comments_count);


                            System.out.println("LES PRODUCT RESOURCE MANAGER PRODUCT :" + resource_manager);
                            ///////////////////////Resource Manager - Product
                            String recup_resource_manager = "{" + '"' + "Recup2";
                            String resource_manager3 = resource_manager.replaceAll("(.*)" + "products", recup_resource_manager);
                            String a_remplace_2 = "}]," + '"' + "unavailabilities";
                            String resource_manager2 = resource_manager3.replace(a_remplace_2, "}]}");

                            if (!resource_manager.equals("[]")) {
                                System.out.println("AHBON3 :" + resource_manager2);
                                JSONObject jsonObj_resource_manager = new JSONObject(resource_manager2);
                                JSONArray Professional_Manager = jsonObj_resource_manager.getJSONArray("Recup2");
                                PremierProfessionalProduct = new Professional_Product[Professional_Manager.length()];
                                for (int j = 0; j < Professional_Manager.length(); j++) {
                                    Professional_Product PremierProfessionalProduct2 = new Professional_Product();
                                    Product product = new Product();
                                    Product_Category_WithoutTree product_category_withoutTree = new Product_Category_WithoutTree();
                                    JSONObject p2 = Professional_Manager.getJSONObject(j);
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
                                    String product_name = (String) json2.get("name");

                                    JSONObject json3 = json2.getJSONObject("category");
                                    int product_category_id = (int) json3.get("id");
                                    String product_category_name = (String) json3.get("name");
                                    String product_category_created_at = (String) json3.get("created_at");
                                    String product_category_updated_at = (String) json3.get("updated_at");

                                    product_category_withoutTree.setId(product_category_id);
                                    product_category_withoutTree.setName(product_category_name);
                                    product_category_withoutTree.setCreated_at(product_category_created_at);
                                    product_category_withoutTree.setUpdated_at(product_category_updated_at);

                                    product.setId(product_id);
                                    product.setName(product_name);
                                    product.setCategory(product_category_withoutTree);


                                    PremierProfessionalProduct2.setProduct(product);


                                    PremierProfessionalProduct[j] = PremierProfessionalProduct2;

                                }
                            }


                            String id = c.getString("id");
                            String acquisition = c.getString("acquisition");
                            String geoloc_latitude = c.getString("geoloc_latitude");
                            String geoloc_longitude = c.getString("geoloc_longitude");
                            premierprofessionel.add(id);
                            premierprofessionel.add(acquisition);
                            premierprofessionel.add(geoloc_latitude);
                            premierprofessionel.add(geoloc_longitude);

                            String max_intervention_distance = c.getString("max_intervention_distance");
                            String logo_image = c.getString("logo_image");
                            String automatic_booking_confirmation = c.getString("automatic_booking_confirmation");
                            String customer_can_choose_resource_booking = c.getString("customer_can_choose_resource_booking");
                            premierprofessionel.add(max_intervention_distance);
                            premierprofessionel.add(logo_image);
                            premierprofessionel.add(automatic_booking_confirmation);
                            premierprofessionel.add(customer_can_choose_resource_booking);

                            String created_at = c.getString("created_at");
                            String updated_at = c.getString("updated_at");
                            String current_subscription_type = c.getString("current_subscription_type");
                            String notifications_preferences_sms = c.getString("notifications_preferences_sms");
                            premierprofessionel.add(created_at);
                            premierprofessionel.add(updated_at);
                            premierprofessionel.add(current_subscription_type);
                            premierprofessionel.add(notifications_preferences_sms);


                            /////////////////SUBSCRIPTION ACCOUNT (FREE Ou PREMIUM)///////////////////
                            Professional_Subscription_Type professional_subscription_type = new Professional_Subscription_Type();
                            JSONObject jsonObj_resource_manager = new JSONObject(current_subscription_type);
                            int id_current_subscription_type = (int) jsonObj_resource_manager.get("id");
                            String name_current_subscription_type = (String) jsonObj_resource_manager.get("name");
                            professional_subscription_type.setId(id_current_subscription_type);
                            professional_subscription_type.setName(name_current_subscription_type);
                            PremierProfessionnal.setProfessional_subscription_type(professional_subscription_type);
                            /////////////////////////////////////////////////////////////////////////


                            String sms_happybirthday_enabled = c.getString("sms_happybirthday_enabled");
                            String sms_happybirthday_sender = c.getString("sms_happybirthday_sender");
                            String sms_happybirthday_content = c.getString("sms_happybirthday_content");
                            String sms_fidelity_enabled = c.getString("sms_fidelity_enabled");
                            String sms_fidelity_sender = c.getString("sms_fidelity_sender");
                            String sms_fidelity_content = c.getString("sms_fidelity_content");
                            String sms_remember_booking_enabled = c.getString("sms_remember_booking_enabled");
                            String discount_exclusivity = c.getString("discount_exclusivity");
                            premierprofessionel.add(sms_happybirthday_enabled);
                            premierprofessionel.add(sms_happybirthday_sender);
                            premierprofessionel.add(sms_happybirthday_content);
                            premierprofessionel.add(sms_fidelity_enabled);
                            premierprofessionel.add(sms_fidelity_sender);
                            premierprofessionel.add(sms_fidelity_content);
                            premierprofessionel.add(sms_remember_booking_enabled);
                            premierprofessionel.add(discount_exclusivity);

                            String preference_resource_type = c.getString("preference_resource_type");
                            String sponsoring_key = c.getString("sponsoring_key");
                            String show_discount = c.getString("show_discount");
                            String shop_name = c.getString("shop_name");
                            premierprofessionel.add(preference_resource_type);
                            premierprofessionel.add(sponsoring_key);
                            premierprofessionel.add(show_discount);
                            premierprofessionel.add(shop_name);

                            String tags = c.getString("tags");
                            String shop_description = c.getString("shop_description");
                            String specialty = c.getString("specialty");
                            String address = c.getString("address");
                            premierprofessionel.add(tags);
                            premierprofessionel.add(shop_description);
                            premierprofessionel.add(specialty);
                            premierprofessionel.add(address);

                            String post_code = c.getString("post_code");
                            String city = c.getString("city");
                            String country = c.getString("country");
                            String type = c.getString("type");
                            premierprofessionel.add(post_code);
                            premierprofessionel.add(city);
                            premierprofessionel.add(country);
                            premierprofessionel.add(type);

                            String shop_phone = c.getString("shop_phone");
                            String shop_email = c.getString("shop_email");
                            String website_link = c.getString("website_link");
                            String facebook_link = c.getString("facebook_link");
                            premierprofessionel.add(shop_phone);
                            premierprofessionel.add(shop_email);
                            premierprofessionel.add(website_link);
                            premierprofessionel.add(facebook_link);

                            String instagram_link = c.getString("instagram_link");
                            String pinterest_link = c.getString("pinterest_link");
                            String product_categories = c.getString("product_categories");
                            premierprofessionel.add(instagram_link);
                            premierprofessionel.add(pinterest_link);
                            premierprofessionel.add(product_categories);


                            String recup = "{" + '"' + "Recup" + '"' + " : [{" + '"' + "parent" + '"';
                            String ahbon = product_categories.replace("[{" + '"' + "parent" + '"', recup);
                            String ahbon2 = ahbon.replace("Z" + '"' + "}]", "Z" + '"' + "}]}");

                            System.out.println("AHBON2 :" + ahbon2);
                            JSONObject jsonObj2 = new JSONObject(ahbon2);
                            JSONArray prestations = jsonObj2.getJSONArray("Recup");

                            PremierProfessionnalProduits = new Product_Category_WithoutTree[prestations.length()];
                            // looping through All Contacts
                            for (int j = 0; j < prestations.length(); j++) {
                                Product_Category_WithoutTree services = new Product_Category_WithoutTree();
                                JSONObject p = prestations.getJSONObject(j);


                                String parent = p.getString("parent");
                                String id_prest = p.getString("id");
                                String name_prest = p.getString("name");
                                String tags_prest = p.getString("tags");
                                String created_at_prest = p.getString("created_at");
                                String updated_at_prest = p.getString("updated_at");
                                listeprestationpremierprofessionnel.add(parent);
                                listeprestationpremierprofessionnel.add(id_prest);
                                listeprestationpremierprofessionnel.add(tags_prest);


                                ////////////////////////PRODUCT CATEGORY
                                String recup2 = "{" + '"' + "Recup2" + '"' + " : [{";
                                String ahbon3 = tags_prest.replace("[{", recup2);
                                String ahbon4 = ahbon3.replace("}]", "}]}");

                                System.out.println("AHBON3 :" + ahbon4);
                                JSONObject jsonObj3 = new JSONObject(ahbon4);
                                JSONArray prestations2 = jsonObj3.getJSONArray("Recup2");
                                PremierProfessionnalProduitsTag = new Product_Category_Tag[prestations2.length()];
                                // looping through All Contacts
                                for (int g = 0; g < prestations2.length(); g++) {
                                    Product_Category_Tag tag = new Product_Category_Tag();
                                    JSONObject p2 = prestations2.getJSONObject(g);
                                    String id_label = p2.getString("id");
                                    String label = p2.getString("label");
                                    listelabelprestationpremierprofessionnel.add(label);

                                    //Remplissage des services
                                    tag.setId(Integer.parseInt(id_label));
                                    tag.setLabel(label);
                                    PremierProfessionnalProduitsTag[g] = tag;

                                }


                                services.setId(Integer.parseInt(id_prest));
                                services.setName(name_prest);
                                services.setTags(PremierProfessionnalProduitsTag);
                                services.setCreated_at(created_at_prest);
                                services.setUpdated_at(updated_at_prest);
                                PremierProfessionnalProduits[j] = services;

                            }


                            String awards = c.getString("awards");
                            String unavailabilities = c.getString("unavailabilities");
                            String schedule = c.getString("schedule");
                            premierprofessionel.add(awards);
                            premierprofessionel.add(unavailabilities);
                            premierprofessionel.add(schedule);


                            ///////////////////////SCHEDULE

                            String recup_schedule = "{" + '"' + "Recup2" + '"' + " : [{";
                            String schedule1 = schedule.replace("[{", recup_schedule);
                            String schedule2 = schedule1.replace("}]", "}]}");

                            if (!schedule.equals("[]")) {
                                System.out.println("AHBON3 :" + schedule2);
                                JSONObject jsonObj_schedule = new JSONObject(schedule2);
                                JSONArray Professional_Schedule = jsonObj_schedule.getJSONArray("Recup2");
                                ProfessionnalGenericSchedule = new Professional_Schedule[Professional_Schedule.length()];
                                for (int j = 0; j < Professional_Schedule.length(); j++) {
                                    Professional_Schedule pro_schedule = new Professional_Schedule();
                                    JSONObject p2 = Professional_Schedule.getJSONObject(j);
                                    String id_schedule = p2.getString("id");
                                    String weekeday_schedule = p2.getString("weekday");
                                    String begin_time_schedule = p2.getString("begin_time");
                                    String end_time_schedule = p2.getString("end_time");


                                    //Remplissage des horaires d'ouverture
                                    pro_schedule.setId(Integer.parseInt(id_schedule));
                                    pro_schedule.setWeekday(Integer.parseInt(weekeday_schedule));
                                    pro_schedule.setBegin_time(begin_time_schedule);
                                    pro_schedule.setEnd_time(end_time_schedule);
                                    ProfessionnalGenericSchedule[j] = pro_schedule;
                                }
                            }


                            //Remplissage du public professionnal
                            PremierProfessionnal.setShop_name(shop_name);
                            PremierProfessionnal.setShop_description(shop_description);
                            PremierProfessionnal.setAddress(address);
                            PremierProfessionnal.setPost_code(post_code);
                            PremierProfessionnal.setCity(city);
                            PremierProfessionnal.setCountry(country);
                            PremierProfessionnal.setShop_phone(shop_phone);
                            PremierProfessionnal.setShop_email(shop_email);
                            PremierProfessionnal.setWebsite_link(website_link);
                            PremierProfessionnal.setFacebook_link(facebook_link);
                            PremierProfessionnal.setInstagram_link(instagram_link);
                            PremierProfessionnal.setPinterest_link(pinterest_link);


                            PremierProfessionnal.setProduct_categories(PremierProfessionnalProduits);
                            PremierProfessionnal.setSchedule(ProfessionnalGenericSchedule);


                            //Remplissage du professionnal
                            PremierProfessionnal.setId(Integer.valueOf(id));
                            PremierProfessionnal.setGeoloc_latitude(Double.parseDouble(geoloc_latitude));
                            PremierProfessionnal.setGeoloc_longitude(Double.parseDouble(geoloc_longitude));
                            if (!max_intervention_distance.equals("null"))
                                PremierProfessionnal.setMax_intervention_distance(Integer.parseInt(max_intervention_distance));
                            else
                                PremierProfessionnal.setMax_intervention_distance(0);
                            PremierProfessionnal.setAutomatic_booking_confirmation(Boolean.parseBoolean(automatic_booking_confirmation));
                            PremierProfessionnal.setCustomer_can_choose_resource_booking(Boolean.parseBoolean(customer_can_choose_resource_booking));
                            PremierProfessionnal.setCreated_at(created_at);
                            PremierProfessionnal.setUpdated_at(updated_at);

                            PremierProfessionnal.setNotification_preferences_sms(notifications_preferences_sms);
                            PremierProfessionnal.setSms_happybirthday_enabled(Boolean.parseBoolean(sms_happybirthday_enabled));
                            PremierProfessionnal.setSms_happybirthday_sender(sms_happybirthday_sender);
                            PremierProfessionnal.setSms_happybirthday_content(sms_happybirthday_content);

                            PremierProfessionnal.setSms_fidelity_enabled(Boolean.parseBoolean(sms_fidelity_enabled));
                            PremierProfessionnal.setSms_fidelity_sender(sms_fidelity_sender);
                            PremierProfessionnal.setSms_fidelity_content(sms_fidelity_content);
                            PremierProfessionnal.setSms_remember_booking_enabled(Boolean.parseBoolean(sms_remember_booking_enabled));
                            PremierProfessionnal.setSponsoring_key(sponsoring_key);


                            System.out.println(PremierProfessionnal.toString());


                        }

                        //JSONObject reader = new JSONObject(ahbon2);
                        //JSONObject sys = reader.getJSONObject("Recup");
                        //String country = sys.getString("value");

                        specialite.add(id_professional);
                        System.out.println("VAAAAAAAAAAAAAALUE " + id_professional);


                        // Getting JSON Array node
                        //JSONArray contacts = jsonObj.getJSONArray("contacts");

                        // looping through All Contacts
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                specialite.add(ls);
                specialite.add(ls);
                specialite.add(ls);
                specialite.add(ls);
                specialite.add(ls);
            }
        } else {
            System.out.println("C'est nul");
            Log.e(TAG, "Couldn't get json from server.");
        }

        for (int i = 0; i < premierprofessionel.size(); i++) {
            System.out.println("PREMIERPROFESSIONNEL : " + premierprofessionel.get(i));
        }
        for (int i = 0; i < listeprestationpremierprofessionnel.size(); i++) {
            System.out.println("LISTEPRESTATIONSPREMIERPROFESSIONNEL : " + listeprestationpremierprofessionnel.get(i));
        }
        for (int i = 0; i < listelabelprestationpremierprofessionnel.size(); i++) {
            System.out.println("LISTELABELPRESTATIONSPREMIERPROFESSIONNEL : " + listelabelprestationpremierprofessionnel.get(i));
        }

        return specialite;

    }


    public ArrayList<ArrayList<String>> jsonparser3(String jsonStr) {

        ArrayList<ArrayList<String>> vue_personnes_sur_carte = new ArrayList<ArrayList<String>>();

        if (jsonStr != null) {
            if (jsonStr.contains("{")) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray contacts = jsonObj.getJSONArray("data");

                    for (int i = 0; i < contacts.length(); i++) {
                        ArrayList<String> personnes = new ArrayList<String>();
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String geoloc_latitude = c.getString("geoloc_latitude");
                        String geoloc_longitude = c.getString("geoloc_longitude");
                        String shop_name = c.getString("shop_name");
                        personnes.add(id);
                        personnes.add(geoloc_latitude);
                        personnes.add(geoloc_longitude);
                        personnes.add(shop_name);
                        vue_personnes_sur_carte.add(personnes);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }

            }
        }

        return vue_personnes_sur_carte;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ResearchTask extends AsyncTask<Void, Void, Boolean> {

        private String response = "";

        private final String mQuery;
        private final int mLimit;
        private final String json;
        //private final GetRequest getRequest;
        //private final Boolean connected;

        ResearchTask(String query, int limit) {
            mQuery = query;
            mLimit = limit;
            System.out.println("QUERY :" + query + ",LIMIT :" + limit);
            json = "{\n" +
                    "\"query\":\"" + query + "\",\n" +
                    "\"limit\":\"" + limit + "\"" +
                    "}\n";
            getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/autocomplete/professional/_all/?query=" + query + "&limit=" + limit);
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


    public class ResearchTask2 extends AsyncTask<Void, Void, Boolean> {

        private final String mQuery;
        private final int mLimit;

        private String response = "";

        //private final String mQuery;
        //private final int mLimit;
        //private final String json;
        //private final GetRequest getRequest;
        //private final Boolean connected;

        //ResearchTask2(String query, String city, String sponsoring_key, Double latitude, Double longitude, String address, String post_code, int product_category_id, int product_id,
        //             int type_id, int specialty_id, int weekday, boolean automatic_booking_confirmation, String field, String order) {
        ResearchTask2(String query, int limit) {
            mQuery = query;
            mLimit = limit;
            // mQuery = query;
            //mLimit = limit;
            //System.out.println("QUERY :" + query + ",LIMIT :" + limit);
            /*json = "{\n" +
                    "\"query\":\"" + query + "\",\n" +
                    "\"limit\":\"" + limit + "\"" +
                    "}\n";*/
            /*getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/_all/?query[_all]=" + query +
                    "& query[city]=" + city + "&query[sponsoring_key]=" + sponsoring_key + "&query[geoloc][latitude]=" + latitude + "&query[geoloc][longitude]=" + longitude +
                    "&query[geoloc][address]=" + address + "&query[post_code]=" + post_code + "&query[product_category_id]=" + product_category_id +
                    "&query[product_id]=" + product_id + "&query[type_id]=" + type_id + "&query[specialty_id]=" + specialty_id + "&query[weekday]=" +
                    weekday + "&query[automatic_booking_confirmation]=" + automatic_booking_confirmation + "&sort[field]=" + field + "&sort[order]=" + order);*/
            getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/?query[_all]=" + query + "&limit=" + limit);

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
            response = resp;

        }


        public String getResponse() {
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


    public class ResearchTask3 extends AsyncTask<Void, Void, Boolean> {

        private String response = "";

        private final String mQuery;
        //private final int mLimit;
        //private final String json;
        //private final GetRequest getRequest;
        //private final Boolean connected;

        //ResearchTask2(String query, String city, String sponsoring_key, Double latitude, Double longitude, String address, String post_code, int product_category_id, int product_id,
        //             int type_id, int specialty_id, int weekday, boolean automatic_booking_confirmation, String field, String order) {
        ResearchTask3(String query) {
            mQuery = query;
            //mLimit = limit;
            //System.out.println("QUERY :" + query + ",LIMIT :" + limit);
            /*json = "{\n" +
                    "\"query\":\"" + query + "\",\n" +
                    "\"limit\":\"" + limit + "\"" +
                    "}\n";*/
            /*getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/_all/?query[_all]=" + query +
                    "& query[city]=" + city + "&query[sponsoring_key]=" + sponsoring_key + "&query[geoloc][latitude]=" + latitude + "&query[geoloc][longitude]=" + longitude +
                    "&query[geoloc][address]=" + address + "&query[post_code]=" + post_code + "&query[product_category_id]=" + product_category_id +
                    "&query[product_id]=" + product_id + "&query[type_id]=" + type_id + "&query[specialty_id]=" + specialty_id + "&query[weekday]=" +
                    weekday + "&query[automatic_booking_confirmation]=" + automatic_booking_confirmation + "&sort[field]=" + field + "&sort[order]=" + order);*/
            getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/?query[_all]=" + query);

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
            response = resp;

        }


        public String getResponse() {
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


    public class ResearchTask4 extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Downloading Infos...", true);
                    ringProgressDialog.setCancelable(true);
                }
            });
        }

        private String response = "";

        private final String mQuery;
        //private final int mLimit;
        //private final String json;
        //private final GetRequest getRequest;
        //private final Boolean connected;

        //ResearchTask2(String query, String city, String sponsoring_key, Double latitude, Double longitude, String address, String post_code, int product_category_id, int product_id,
        //             int type_id, int specialty_id, int weekday, boolean automatic_booking_confirmation, String field, String order) {
        ResearchTask4(String query) {
            mQuery = query;
            getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/autocomplete/professional/place/?query=" + query);

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
            response = resp;

        }


        public String getResponse() {
            return response;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Downloading Infos...", true);
                        ringProgressDialog.setCancelable(true);
                    }
                });
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


    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity()
                .setTitle(R.string.research);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}



