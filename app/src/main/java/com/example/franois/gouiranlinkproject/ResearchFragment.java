package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static com.example.franois.gouiranlinkproject.BaseFragment.ARGS_INSTANCE;



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
        private TextView resultat1;
        private Button carte;


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
            recherche.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {


                    System.out.println("Ommmmmmmmmmmggggggggggggg0");
                    ResearchTask ult = new ResearchTask(recherche.getText().toString(),5);
                    System.out.println("Ommmmmmmmmmmggggggggggggg1");
                    System.out.println(recherche.getText().toString());
                    resultat1.setText("Coucou");

                    return false;
                }
            });

        /*carte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //startActivity(new Intent(MainActivity.this, ActivityB.class));
                Intent intent = new Intent(getApplicationContext(), MapPane.class);
                startActivity(intent);
                Fragment fg = new Fragment();
            }
        });*/

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

        public void recuperation(){

        }


        /**
         * Represents an asynchronous login/registration task used to authenticate
         * the user.
         */
        public class ResearchTask extends AsyncTask<Void, Void, Boolean> {

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
               /* getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/autocomplete/professional/_all/", json);
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
                if (resp.contains("access_token"))
                    connected = true;
                else
                    connected = false;*/
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


