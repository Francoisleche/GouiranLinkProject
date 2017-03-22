package com.example.franois.gouiranlinkproject.Recherche;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.Object.GenericSchedule;
import com.example.franois.gouiranlinkproject.Object.Product;
import com.example.franois.gouiranlinkproject.Object.Product_Category_Tag;
import com.example.franois.gouiranlinkproject.Object.Product_Category_WithoutTree;
import com.example.franois.gouiranlinkproject.Object.Professional;
import com.example.franois.gouiranlinkproject.Object.Professional_Product;
import com.example.franois.gouiranlinkproject.Object.Professional_Schedule;
import com.example.franois.gouiranlinkproject.Object.PublicProfessional;
import com.example.franois.gouiranlinkproject.Professional_View.ProfessionalView;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;
import static com.example.franois.gouiranlinkproject.ToolsClasses.BaseFragment.ARGS_INSTANCE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResearchFragment extends Fragment implements ProfessionalView.OnFragmentInteractionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Professional PremierProfessionnal = new Professional();
    private Product_Category_WithoutTree[] PremierProfessionnalProduits;
    private Product_Category_Tag[] PremierProfessionnalProduitsTag;

    private Professional_Schedule[] ProfessionnalGenericSchedule;

    private Professional_Product[] PremierProfessionalProduct;


    private ResearchTask mAuthTask = null;
    private EditText recherche;
    private TextView resultat1, resultat2, resultat3, resultat4, resultat5, resultat6;
    private Button carte;
    private GetRequest getRequest;

    private FileOutputStream fileOutputStream = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String[] resultat_rech = new String[5];


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
        recherche = (EditText) view.findViewById(R.id.bonjour_recherche);
        //resultat1 = (TextView) view.findViewById(R.id.textView_1);
        carte = (Button) view.findViewById(R.id.carte_research);

        /*resultat2 = (TextView) view.findViewById(R.id.textView_11);
        resultat3 = (TextView) view.findViewById(R.id.textView_12);
        resultat4 = (TextView) view.findViewById(R.id.textView_13);
        resultat5 = (TextView) view.findViewById(R.id.textView_14);
        resultat6 = (TextView) view.findViewById(R.id.textView_15);*/

        final ListView listView = (ListView) view.findViewById(R.id.mesresultats);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*fileOutputStream = getContext().openFileOutput("GouiranLink", MODE_APPEND);
                Log.d("OUTPUT FOUND", listView.getItemAtPosition(position).toString());
                fileOutputStream.write(listView.getItemAtPosition(position).toString().getBytes());
                fileOutputStream.write("`".getBytes());
                fileOutputStream.close();*/

                System.out.println(listView.getItemAtPosition(position).toString());
                ResearchTask3 researchTask3 = new ResearchTask3(listView.getItemAtPosition(position).toString());
                String ls2 = "";
                ArrayList<String> recup2 = new ArrayList<String>();
                ls2 = researchTask3.getResponse();
                recup2 = jsonparser2(ls2);

                Fragment fragment = null;
                Bundle args = new Bundle();
                args.putSerializable("Professionnal", PremierProfessionnal);
                args.putSerializable("ProfessionnalProduct", PremierProfessionalProduct);


                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);

                fragment = new ProfessionalView();
                fragment.setArguments(args);

                ft.replace(R.id.fragment_remplace, fragment);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }
        });
        //MARCHE mautomatiquement à la lettre prêt mais c'est très long

        /*recherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                resultat1.setText(recherche.getText());
                ArrayList<String> recup = new ArrayList<String>();
                String ls = "";
                ResearchTask rt = new ResearchTask(recherche.getText().toString(), 5);
                ls = rt.getResponse();
                System.out.println("LSSSSSSS :" + ls);

                //resultat2.setText(ls);

                recup = jsonparser(ls);

                int size = recup.size();
                if (size == 0) {
                    resultat2.setText("");
                    resultat3.setText("");
                    resultat4.setText("");
                    resultat5.setText("");
                    resultat6.setText("");
                } else if (size == 1) {
                    resultat2.setText(recup.get(0));
                } else if (size == 2) {
                    resultat2.setText(recup.get(0));
                    resultat3.setText(recup.get(1));
                } else if (size == 3) {
                    resultat2.setText(recup.get(0));
                    resultat3.setText(recup.get(1));
                    resultat4.setText(recup.get(2));
                } else if (size == 4) {
                    resultat2.setText(recup.get(0));
                    resultat3.setText(recup.get(1));
                    resultat4.setText(recup.get(2));
                    resultat5.setText(recup.get(3));
                } else if (size == 5) {
                    resultat2.setText(recup.get(0));
                    resultat3.setText(recup.get(1));
                    resultat4.setText(recup.get(2));
                    resultat5.setText(recup.get(3));
                    resultat6.setText(recup.get(4));
                }


//List view des resultats


/*

                    for(int i = 0 ; i<40;i++){
                        tableau.add("Prestation n°"+i);

                    }
                    listView.setAdapter(tableau);*/


        //System.out.println("1 = "+recup.get(1)+ " 2 ="+recup.get(2));


            /*}
        });
*/

        //MARCHe mais on doit attendre que l'utilisateur est finis de taper, et ça marche qu'une fois ????????

        /*recherche.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                ArrayList<String> recup = new ArrayList<String>();

                String ls = "";
                ResearchTask rt = new ResearchTask(recherche.getText().toString(), 5);
                ls = rt.getResponse();
                recup = jsonparser(ls);

                resultat2.setText(recup.get(1));
                resultat3.setText(recup.get(2));
                resultat4.setText(recup.get(3));
                resultat5.setText(recup.get(4));
                resultat6.setText(recup.get(5));

                return false;
            }
        });*/

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
                                ResearchTask researchTask = new ResearchTask(recherche.getText().toString(), 5);
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
                                }



                                //2eme recherche pour les prestataires
                                /*ResearchTask2 researchTask2 = new ResearchTask2();
                                String ls2 = "";
                                ArrayList<String> recup2 = new ArrayList<String>();
                                ls2 = researchTask2.getResponse();
                                recup2 = jsonparser2(ls2);*/

                                //System.out.println(ls2);

                                /*resultat2.setText(recup.get(0));
                                resultat3.setText(recup.get(1));
                                resultat4.setText(recup.get(2));
                                resultat5.setText(recup.get(3));
                                resultat6.setText(recup.get(4));*/


                            }
                        });
                    }
                }, DELAY);
            }
        });


        initUI(view);
        return view;
    }

    private void initUI(View v) {
        Button button1 = (Button) v.findViewById(R.id.carte_research);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapPane.class);
                startActivity(intent);
            }
        });

        Button button2 = (Button) v.findViewById(R.id.filtre_research);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });

        Button button3 = (Button) v.findViewById(R.id.filtre_prestataire);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);
                ft.replace(R.id.fragment_remplace, new ProfessionalView());

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        Button button4 = (Button) v.findViewById(R.id.filtre_prestataire_trouve);
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ResearchTask2 researchTask2 = new ResearchTask2();
                String ls2 = "";
                ArrayList<String> recup2 = new ArrayList<String>();
                ls2 = researchTask2.getResponse();
                recup2 = jsonparser2(ls2);

                Fragment fragment = null;
                Bundle args = new Bundle();
                args.putSerializable("Professionnal", PremierProfessionnal);





                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);

                fragment = new ProfessionalView();
                fragment.setArguments(args);

                ft.replace(R.id.fragment_remplace, fragment);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();


                //String ls = "";
                //ResearchTask2 rt = new ResearchTask2("Coiffeur", "Montpellier", "", 43.6109200, 3.8772300, "", "34070", 1, 1, 1, 1, 1, true, "price", "asc");
                //ls = rt.getResponse();
                //System.out.println(ls);
                //Intent intent = new Intent(getActivity(), ProfessionelTrouve.class);
                //startActivity(intent);



                //2eme recherche pour les prestataires


                //System.out.println(ls2);


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

                        if(i==0){
                            String shop_images = c.getString("shop_images");
                            String resource_manager = c.getString("resource_manager");
                            String avg_grade = c.getString("avg_grade");
                            String comments_count = c.getString("comments_count");
                            premierprofessionel.add(shop_images);
                            premierprofessionel.add(resource_manager);
                            premierprofessionel.add(avg_grade);
                            premierprofessionel.add(comments_count);



                            System.out.println("LES PRODUCT RESOURCE MANAGER PRODUCT :"+resource_manager);
                            ///////////////////////Resource Manager - Product
                            String recup_resource_manager = "{" + '"' + "Recup2" ;
                            String resource_manager3 = resource_manager.replaceAll("(.*)"+"products", recup_resource_manager);
                            String a_remplace_2 = "}],"+ '"'+ "unavailabilities";
                            String resource_manager2 = resource_manager3.replace(a_remplace_2, "}]}");

                            if(!resource_manager.equals("[]")) {
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



                                    System.out.println("LES PRODUCT RESOURCE MANAGER PRODUCT 2222 :"+product_resource_manager);
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


                                    PremierProfessionalProduct[j]=PremierProfessionalProduct2;

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









                            String recup = "{" + '"' + "Recup" + '"' + " : [{"+ '"' +"parent"+ '"';
                            String ahbon = product_categories.replace("[{"+ '"' +"parent"+ '"', recup);
                            String ahbon2 = ahbon.replace("Z"+'"'+"}]", "Z"+'"'+"}]}");

                            System.out.println("AHBON2 :" +ahbon2);
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
                                String ahbon4 = ahbon3.replace("}]","}]}");

                                System.out.println("AHBON3 :" +ahbon4);
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

                            if(!schedule.equals("[]")) {
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
                            if(!max_intervention_distance.equals("null"))
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

        private String response = "";

        //private final String mQuery;
        //private final int mLimit;
        //private final String json;
        //private final GetRequest getRequest;
        //private final Boolean connected;

        //ResearchTask2(String query, String city, String sponsoring_key, Double latitude, Double longitude, String address, String post_code, int product_category_id, int product_id,
         //             int type_id, int specialty_id, int weekday, boolean automatic_booking_confirmation, String field, String order) {
        ResearchTask2(){
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
            getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/");

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
        ResearchTask3(String query){
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
            getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/?query[_all]="+query);

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


