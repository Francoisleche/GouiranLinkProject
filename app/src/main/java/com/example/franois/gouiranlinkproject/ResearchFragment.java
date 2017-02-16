package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;


import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.Toast;

import com.example.franois.gouiranlinkproject.Recherche.ProfessionelTrouve;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;


import com.example.franois.gouiranlinkproject.ToolsClasses.GetRequest;


import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static com.example.franois.gouiranlinkproject.BaseFragment.ARGS_INSTANCE;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
     * A simple {@link Fragment} subclass.
     * Activities that contain this fragment must implement the
     * {@link com.example.franois.gouiranlinkproject.ResearchFragment.OnFragmentInteractionListener} interface
     * to handle interaction events.
     * Use the {@link com.example.franois.gouiranlinkproject.ResearchFragment#newInstance} factory method to
     * create an instance of this fragment.
     */
    public class ResearchFragment extends Fragment {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        private ResearchTask mAuthTask = null;
        private EditText recherche;
        private TextView resultat1,resultat2,resultat3,resultat4;
        private Button carte;
        private GetRequest getRequest;


        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

        private OnFragmentInteractionListener mListener;
        private String[] resultat_rech = new String[5];




        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         //* @param param1 Parameter 1.
         //* @param param2 Parameter 2.
         * @return A new instance of fragment ResearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static com.example.franois.gouiranlinkproject.ResearchFragment newInstance(int instance) {
            Bundle args = new Bundle();
            args.putInt(ARGS_INSTANCE, instance);
            com.example.franois.gouiranlinkproject.ResearchFragment firstFragment = new com.example.franois.gouiranlinkproject.ResearchFragment();
            firstFragment.setArguments(args);
            return firstFragment;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        /*carte = (Button) getActivity().findViewById(R.id.carte_research);
        carte.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Do something here
                //startActivity(new Intent(MainActivity.this, ActivityB.class));
                Intent intent = new Intent(getApplicationContext(), MapPane.class);
                startActivity(intent);

            }
        });*/

        /*recherche.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

                //
                ResearchTask ult = new ResearchTask(recherche.getText().toString(),5);


                return false;
            }
        });*/




        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View view = inflater.inflate(R.layout.fragment_research, container, false);
            recherche = (EditText) view.findViewById(R.id.bonjour_recherche);
            resultat1 = (TextView) view.findViewById(R.id.textView_1);
            carte = (Button) view.findViewById(R.id.carte_research);

            resultat2 = (TextView) view.findViewById(R.id.textView_11);
            resultat3 = (TextView) view.findViewById(R.id.textView_12);
            resultat4 = (TextView) view.findViewById(R.id.textView_13);

            ListView listView = (ListView) view.findViewById(R.id.mesresultats);
            ArrayAdapter<String> tableau = new ArrayAdapter<String>(listView.getContext(),R.layout.services);

            //MARCHE mautomatiquement à la lettre prêt mais c'est très long

            recherche.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    resultat1.setText("Coucou1");
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                    // TODO Auto-generated method stub
                    resultat1.setText("Coucou2");

                }

                @Override
                public void afterTextChanged(Editable s) {
                    resultat1.setText(recherche.getText());
                    ArrayList<String> recup = new ArrayList<String>();
                    String ls = "";
                    ResearchTask rt = new ResearchTask(recherche.getText().toString(),5);
                    ls = rt.getResponse();
                    //System.out.println("LSSSSSSS :"+ls);

                    //resultat2.setText(ls);

                    recup = jsonparser(ls);

                    int size = recup.size();
                    if(size == 0 ){
                        resultat2.setText("");
                        resultat3.setText("");
                        resultat4.setText("");
                    }else if(size == 1 ){
                        resultat2.setText(recup.get(0));
                    }else if(size == 2 ){
                        resultat2.setText(recup.get(0));
                        resultat3.setText(recup.get(1));
                    }else if(size >= 3){
                        resultat2.setText(recup.get(0));
                        resultat3.setText(recup.get(1));
                        resultat4.setText(recup.get(3));
                    }


//List view des resultats


/*

                    for(int i = 0 ; i<40;i++){
                        tableau.add("Prestation n°"+i);

                    }
                    listView.setAdapter(tableau);*/


                    //System.out.println("1 = "+recup.get(1)+ " 2 ="+recup.get(2));




                }
            });



                //MARCHe mais on doit attendre que l'utilisateur est finis de taper, et ça marche qu'une fois ????????

                recherche.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    ArrayList<String> recup = new ArrayList<String>();
    /*
                    System.out.println("Ommmmmmmmmmmggggggggggggg0");
                    ResearchTask ult = new ResearchTask(recherche.getText().toString(),5);
                    System.out.println("Ommmmmmmmmmmggggggggggggg1");
                    System.out.println(recherche.getText().toString());
                    resultat1.setText("Coucou");*/

                    String ls = "";
                    ResearchTask rt = new ResearchTask(recherche.getText().toString(),5);
                    ls = rt.getResponse();
                    recup = jsonparser(ls);

                    resultat2.setText(recup.get(1));
                    resultat3.setText(recup.get(2));
                    resultat4.setText(recup.get(3));










                    //resultat3.setText(ls);

                    return false;
                }
            });










            initUI(view);
            return view;
        }

        private void initUI(View v){
            Button button1 =(Button)v.findViewById(R.id.carte_research);
            button1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MapPane.class);
                    startActivity(intent);
                }
            });

            Button button2 =(Button)v.findViewById(R.id.filtre_research);
            button2.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), FilterActivity.class);
                    startActivity(intent);
                }
            });

            Button button3 =(Button)v.findViewById(R.id.filtre_prestataire);
            button3.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ProfessionalView.class);
                    startActivity(intent);
                }
            });

            Button button4 =(Button)v.findViewById(R.id.filtre_prestataire_trouve);
            button4.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    String ls = "";
                    ResearchTask2 rt = new ResearchTask2("Coiffeur","Montpellier","",43.6109200,3.8772300,"","34070",1,1,1,1,1,true,"price","asc");
                    ls = rt.getResponse();
                    System.out.println(ls);
                    //Intent intent = new Intent(getActivity(), ProfessionelTrouve.class);
                    //startActivity(intent);
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

    public ArrayList<String> jsonparser( String jsonStr){
        String ls = "";
        ArrayList<String> specialite = new ArrayList<>();

        if (jsonStr != null) {
            if(jsonStr.contains("{")){
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
                    }catch( final JSONException e){
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                    }
                }else{
                    specialite.add(ls);
                    specialite.add(ls);
                    specialite.add(ls);
                    specialite.add(ls);
                    specialite.add(ls);
                }
        }else {
            System.out.println("C'est nul");
            Log.e(TAG, "Couldn't get json from server.");
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
                getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/autocomplete/professional/_all/?query="+query+"&limit="+limit);
                String resp = null;
                try {
                    resp = getRequest.execute().get();
                    System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                    System.out.println(resp);
                } catch (InterruptedException | ExecutionException e){
                    e.printStackTrace();
                }
                response = resp;

            }

            String getResponse(){
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

        private final String mQuery;
        //private final int mLimit;
        //private final String json;
        //private final GetRequest getRequest;
        //private final Boolean connected;

        ResearchTask2(String query, String city,String sponsoring_key,Double latitude,Double longitude, String address, String post_code,int product_category_id,int product_id,
                      int type_id,int specialty_id,int weekday,boolean automatic_booking_confirmation,String field,String order) {
            mQuery = query;
            //mLimit = limit;
            //System.out.println("QUERY :" + query + ",LIMIT :" + limit);
            /*json = "{\n" +
                    "\"query\":\"" + query + "\",\n" +
                    "\"limit\":\"" + limit + "\"" +
                    "}\n";*/
            getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/professional/_all/?query[_all]="+query+
                    "& query[city]="+city+"&query[sponsoring_key]="+sponsoring_key+"&query[geoloc][latitude]="+latitude+"&query[geoloc][longitude]="+longitude+
                    "&query[geoloc][address]="+address+"&query[post_code]="+post_code+"&query[product_category_id]="+product_category_id+
                    "&query[product_id]="+product_id+"&query[type_id]="+type_id+"&query[specialty_id]="+specialty_id+"&query[weekday]="+
                    weekday+"&query[automatic_booking_confirmation]="+automatic_booking_confirmation+"&sort[field]="+field+"&sort[order]="+order);
            String resp = null;
            try {
                resp = getRequest.execute().get();
                System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                System.out.println(resp.toString());
            } catch (InterruptedException e){
                e.printStackTrace();
            } catch (ExecutionException e){
                e.printStackTrace();
            }
            response = resp;

        }


        public String getResponse(){
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


    }


