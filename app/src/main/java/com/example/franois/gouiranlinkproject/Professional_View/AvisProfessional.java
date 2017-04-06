package com.example.franois.gouiranlinkproject.Professional_View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franois.gouiranlinkproject.Object.Professional;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.Recherche.ResearchFragment;
import com.example.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by François on 01/02/2017.
 */

public class AvisProfessional extends Fragment{

    private GetRequest getRequest;
    private ResearchFragment.ResearchTask mAuthTask = null;
    private Professional professional;
    private String accessToken;

    public AvisProfessional(){
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            professional = (Professional)getArguments().getSerializable("Professionnal");
            accessToken = (String)getArguments().getString("token");
            System.out.println("Toooooooooooooken"+accessToken);
        }
        System.out.println("Maaaaaaaaaaaaaaaarche bien :"+professional.toString());
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_avis_professional, container, false);

        ResearchTask3 research = new ResearchTask3(accessToken);

        return view;
    }



    public class ResearchTask3 extends AsyncTask<Void, Void, Boolean> {

        private String response = "";

        //private final String mQuery;
        //private final int mLimit;
        //private final String json;
        //private final GetRequest getRequest;
        //private final Boolean connected;

        ResearchTask3(String accessToken) {
            //mQuery = query;
            //mLimit = limit;
            //System.out.println("QUERY :" + query + ",LIMIT :" + limit);
            /*json = "{\n" +
                    "\"query\":\"" + query + "\",\n" +
                    "\"limit\":\"" + limit + "\"" +
                    "}\n";*/
            //JSONObject jsonObject = new JSONObject(resp);
            //String accessToken = jsonObject.getString("access_token");
            getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/comment/professional/"+
                    String.valueOf(professional.getId())+"/", "Authorization", "Token " + accessToken);
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


}
