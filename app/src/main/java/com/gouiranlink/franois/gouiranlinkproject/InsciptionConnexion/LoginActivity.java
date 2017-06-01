package com.gouiranlink.franois.gouiranlinkproject.InsciptionConnexion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Manifest;
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.ParentActivity2;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.MyCustomer;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.PostRequest;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.RetrieveCustomerInformationFromRequest;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;

/*
Activity for login either with Gouiran Link account or Facebook or Google Plus
 */

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final int REQUEST_CODE_LOCATION = 123;

    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView a_masque;

    final private MyCustomer myCustomer = new MyCustomer();

    private static final int RC_SIGN_IN = 9001;
    private String email = "";
    private String birthday = "";
    private static GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private static final String TAG = "SignInActivity";


    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    LoginButton loginButton;
    SignInButton signInButton;

    private GetRequest getRequest;
    private String[] languages2;
    private String[] tab_autocomplete_place, tab_autocomplete_name;

    private Customer customer;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            tab_autocomplete_place = (String[]) getIntent().getStringArrayExtra("place");
        }




        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        /*LinearLayout ll = (LinearLayout) findViewById(R.id.visible);
        ll.setBackground(getDrawable(R.drawable.connexion_inscription));*/






        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
            }
        };
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                nextActivity(newProfile, String.valueOf(accessToken));
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        Button fb = (Button) findViewById(R.id.fb);
        loginButton = (LoginButton) findViewById(R.id.facebook_login_button);

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Toast.makeText(getApplicationContext(), "onCompleted()", Toast.LENGTH_SHORT).show();

                                try {
                                    String picture = "http://graph.facebook.com/" + object.getString("id") + "/picture?type=large";
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    email = object.getString("email");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    birthday = object.getString("birthday"); // 01/31/1980 format
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Profile profile = Profile.getCurrentProfile();
                nextActivity(profile, loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(), "Veuillez vérifier que vous êtes bien connecté à internet.", Toast.LENGTH_SHORT).show();
            }
        };
        loginButton.registerCallback(callbackManager, callback);


        findViewById(R.id.sign_in_button).setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("912405249984-gc5dc7g2alj2c45978nt90pdo2rpb2t4.apps.googleusercontent.com")
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        AppEventsLogger.activateApp(getApplication());
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mEmailView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);



    }








    private void nextActivity(String resp) {
        //cacher le clavier
        hideKeyBoard(this);

        final Intent main = new Intent(LoginActivity.this, ParentActivity2.class);
        customer = null;
        accessToken = "";
        try {
            JSONObject jsonObject = new JSONObject(resp);
            accessToken = jsonObject.getString("access_token");
            GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/authentication/user/", "Authorization", "Token " + accessToken);
            resp = getRequest.execute().get();


            RetrieveCustomerInformationFromRequest retrieveCustomerInformationFromRequest = new RetrieveCustomerInformationFromRequest(resp, accessToken);
            customer = retrieveCustomerInformationFromRequest.generateCustomer();
            customer.setmFacebook(false);
            customer.setmGoogle(false);
            customer.setmGouiranLink(true);
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }




        final ProgressDialog ringProgressDialog = ProgressDialog.show(this, "Svp veuillez patienter ...", "Chargement des données", true);
        Thread timer = new Thread() {
            @Override
            public void run() {
                webservice_autocomplete();
                main.putExtra("place", tab_autocomplete_place);
                main.putExtra("autocomplete", tab_autocomplete_name);
                main.putExtra("Customer", customer);
                main.putExtra("token",accessToken);
                System.out.println("Toooooooooooooken"+accessToken);
                System.out.println("CONNECTER GOUIRAN LINK 1");
                startActivity(main);
            }
        };
        timer.start();
        ringProgressDialog.setCancelable(false);




    }


    public void webservice_autocomplete() {
        /*String[] A  = place_jsonparser(recherche_place("A"));
        String[] B  = place_jsonparser(recherche_place("B"));
        String[] C  = place_jsonparser(recherche_place("C"));
        String[] D  = place_jsonparser(recherche_place("D"));
        String[] E  = place_jsonparser(recherche_place("E"));
        String[] F  = place_jsonparser(recherche_place("F"));
        String[] G  = place_jsonparser(recherche_place("G"));
        String[] H  = place_jsonparser(recherche_place("H"));
        String[] I  = place_jsonparser(recherche_place("I"));
        String[] J  = place_jsonparser(recherche_place("J"));
        String[] K  = place_jsonparser(recherche_place("K"));
        String[] L  = place_jsonparser(recherche_place("L"));
        String[] M  = place_jsonparser(recherche_place("M"));
        String[] N  = place_jsonparser(recherche_place("N"));
        String[] O  = place_jsonparser(recherche_place("O"));
        String[] P  = place_jsonparser(recherche_place("P"));
        String[] Q  = place_jsonparser(recherche_place("Q"));
        String[] R  = place_jsonparser(recherche_place("R"));
        String[] S  = place_jsonparser(recherche_place("S"));
        String[] T  = place_jsonparser(recherche_place("T"));
        String[] U  = place_jsonparser(recherche_place("U"));
        String[] V  = place_jsonparser(recherche_place("V"));
        String[] W  = place_jsonparser(recherche_place("W"));
        String[] X  = place_jsonparser(recherche_place("X"));
        String[] Y  = place_jsonparser(recherche_place("Y"));
        String[] Z  = place_jsonparser(recherche_place("Z"));

        tab_autocomplete_place = new String[A.length+B.length+C.length+D.length+E.length+F.length+G.length+H.length+
                I.length+J.length+K.length+L.length+M.length+N.length+O.length+P.length+Q.length+R.length+
                S.length+T.length+U.length+V.length+W.length+X.length+Y.length+Z.length];

        ArrayList list = new ArrayList();
        for (Object object : A) {
            list.add(object);
        }
        for (Object object : B) {
            list.add(object);
        }
        for (Object object : C) {
            list.add(object);
        }
        for (Object object : D) {
            list.add(object);
        }
        for (Object object : E) {
            list.add(object);
        }
        for (Object object : F) {
            list.add(object);
        }
        for (Object object : G) {
            list.add(object);
        }
        for (Object object : H) {
            list.add(object);
        }
        for (Object object : I) {
            list.add(object);
        }
        for (Object object : J) {
            list.add(object);
        }

        for (Object object : K) {
            list.add(object);
        }
        for (Object object : L) {
            list.add(object);
        }
        for (Object object : M) {
            list.add(object);
        }
        for (Object object : N) {
            list.add(object);
        }
        for (Object object : O) {
            list.add(object);
        }

        for (Object object : P) {
            list.add(object);
        }
        for (Object object : Q) {
            list.add(object);
        }
        for (Object object : R) {
            list.add(object);
        }
        for (Object object : S) {
            list.add(object);
        }
        for (Object object : T) {
            list.add(object);
        }

        for (Object object : U) {
            list.add(object);
        }
        for (Object object : V) {
            list.add(object);
        }
        for (Object object : W) {
            list.add(object);
        }
        for (Object object : X) {
            list.add(object);
        }
        for (Object object : Y) {
            list.add(object);
        }
        for (Object object : Z) {
            list.add(object);
        }


        list.toArray(tab_autocomplete_place);


        for(int g = 0; g< tab_autocomplete_place.length; g++){
            System.out.println("AFFFFICHE tab_autocomplete_name "+g+" : "+ tab_autocomplete_place[g]);
        }*/

        String[] A = recupToutlesautocomplete("A");
        String[] B = recupToutlesautocomplete("B");
        String[] C = recupToutlesautocomplete("C");
        String[] D = recupToutlesautocomplete("D");
        String[] E = recupToutlesautocomplete("E");

        String[] F = recupToutlesautocomplete("F");
        String[] G = recupToutlesautocomplete("G");
        String[] H = recupToutlesautocomplete("H");
        String[] I = recupToutlesautocomplete("I");
        String[] J = recupToutlesautocomplete("J");

        String[] K = recupToutlesautocomplete("K");
        String[] L = recupToutlesautocomplete("L");
        String[] M = recupToutlesautocomplete("M");
        String[] N = recupToutlesautocomplete("N");
        String[] O = recupToutlesautocomplete("O");

        String[] P = recupToutlesautocomplete("P");
        String[] Q = recupToutlesautocomplete("Q");
        String[] R = recupToutlesautocomplete("R");
        String[] S = recupToutlesautocomplete("S");
        String[] T = recupToutlesautocomplete("T");

        String[] U = recupToutlesautocomplete("U");
        String[] V = recupToutlesautocomplete("V");
        String[] W = recupToutlesautocomplete("W");
        String[] X = recupToutlesautocomplete("X");
        String[] Y = recupToutlesautocomplete("Y");
        String[] Z = recupToutlesautocomplete("Z");


        int taille = A.length+B.length+C.length+D.length+E.length +
                F.length+G.length+H.length+I.length+J.length+K.length+L.length+M.length+N.length+O.length+
                P.length+Q.length+R.length+S.length+T.length+U.length+V.length+W.length+X.length+Y.length+Z.length;
        tab_autocomplete_name = new String[taille];

        ArrayList list2 = new ArrayList();
        for (Object object : A) {
            list2.add(object);
        }
        for (Object object : B) {
            list2.add(object);
        }
        for (Object object : C) {
            list2.add(object);
        }
        for (Object object : D) {
            list2.add(object);
        }
        for (Object object : E) {
            list2.add(object);
        }
        for (Object object : F) {
            list2.add(object);
        }
        for (Object object : G) {
            list2.add(object);
        }
        for (Object object : H) {
            list2.add(object);
        }
        for (Object object : I) {
            list2.add(object);
        }
        for (Object object : J) {
            list2.add(object);
        }

        for (Object object : K) {
            list2.add(object);
        }
        for (Object object : L) {
            list2.add(object);
        }
        for (Object object : M) {
            list2.add(object);
        }
        for (Object object : N) {
            list2.add(object);
        }
        for (Object object : O) {
            list2.add(object);
        }

        for (Object object : P) {
            list2.add(object);
        }
        for (Object object : Q) {
            list2.add(object);
        }
        for (Object object : R) {
            list2.add(object);
        }
        for (Object object : S) {
            list2.add(object);
        }
        for (Object object : T) {
            list2.add(object);
        }

        for (Object object : U) {
            list2.add(object);
        }
        for (Object object : V) {
            list2.add(object);
        }
        for (Object object : W) {
            list2.add(object);
        }
        for (Object object : X) {
            list2.add(object);
        }
        for (Object object : Y) {
            list2.add(object);
        }
        for (Object object : Z) {
            list2.add(object);
        }


        list2.toArray(tab_autocomplete_name);


        for(int g = 0; g< tab_autocomplete_name.length; g++){
            System.out.println("AFFFFICHE tab_autocomplete_name "+g+" : "+ tab_autocomplete_name[g]);
        }


        System.out.println("AFFFFICHE tab_autocomplete_name :"+ tab_autocomplete_name.length);

        tab_autocomplete_name[0] = "ESSAIE";
        tab_autocomplete_name[100] = "ESSAIE";
        tab_autocomplete_name[200] = "ESSAIE";
        tab_autocomplete_name[300] = "ESSAIE";
        tab_autocomplete_name[400] = "ESSAIE";
        tab_autocomplete_name[500] = "ESSAIE";
        tab_autocomplete_name[600] = "ESSAIE";
        tab_autocomplete_name[700] = "ESSAIE";
        tab_autocomplete_name[800] = "ESSAIE";
        tab_autocomplete_name[900] = "ESSAIE";
        tab_autocomplete_name[1000] = "ESSAIE";
        tab_autocomplete_name[1100] = "ESSAIE";
        tab_autocomplete_name[1200] = "ESSAIE";
        tab_autocomplete_name[1300] = "ESSAIE";
        tab_autocomplete_name[1400] = "ESSAIE";
        tab_autocomplete_name[1500] = "ESSAIE";
        tab_autocomplete_name[1600] = "ESSAIE";
        tab_autocomplete_name[1638] = "ESSAIE";
        tab_autocomplete_name[1738] = "ESSAIE";
        tab_autocomplete_name[1838] = "ESSAIE";
        tab_autocomplete_name[1938] = "ESSAIE";
        tab_autocomplete_name[2038] = "ESSAIE";
        tab_autocomplete_name[2138] = "ESSAIE";
        tab_autocomplete_name[2204] = "ESSAIE";
        tab_autocomplete_name[2238] = "ESSAIE";
        tab_autocomplete_name[2338] = "ESSAIE";


    }

    public String recherche_place(String query) {
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/autocomplete/professional/place/?query=" + query + "&limit=100");
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
                        System.out.println("VAAAAAAAAAAAAAALUE "+i+" : " + id);


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

    public String[] place_jsonparser(String str){
        String ls = str;
        String[] recup2 = new String[]{};
        //ls = researchTask.getResponse();
        //recup = jsonparser(ls);

        String recup = "{" + '"' + "Recup" + '"' + " : [";
        String ahbon = ls.replace("[", recup);
        String ahbon2 = ahbon.replace("]", "]}");

        try {
            JSONObject jsonObj = null;
            jsonObj = new JSONObject(ahbon2);
            JSONArray contacts = null;
            contacts = jsonObj.getJSONArray("Recup");
            recup2 = new String[contacts.length()];
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = null;
                c = contacts.getJSONObject(i);
                String id = c.getString("value");
                recup2[i]=id;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recup2;
    }

    public String[] recupToutlesautocomplete(String s){
        ParserTask p = new ParserTask(s,100);
        String ls = "";
        ArrayList<String> recup = new ArrayList<String>();
        ls = p.getResponse();
        recup = jsonparser(ls);

        String[] results ;
        results = new String[recup.size()];

        for (int i = 1; i < recup.size(); i++) {
            results[i] = recup.get(i);
        }

        return results;
    }

    public class ParserTask extends AsyncTask<Void, Void, Boolean> {




        private String response = "";

        private final String mQuery;
        private final int mLimit;
        private final String json;
        //private final GetRequest getRequest;
        //private final Boolean connected;

        ParserTask(String query, int limit) {
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

                String ls = "";
                ArrayList<String> recup = new ArrayList<String>();
                ls = resp;
                recup = jsonparser(ls);

                String[] results ;
                results = new String[recup.size()];
                languages2 = new String[recup.size()];

                for (int i = 1; i < recup.size(); i++) {
                    results[i] = recup.get(i);
                    languages2[i] = recup.get(i);
                    System.out.println("RESULTAT DE  RESULTS : "+results[i]);
                }


                onPostExecute(results);

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


        protected String[] onPostExecute(String[] languages) {

            System.out.println("On est passé");

            //ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,languages);

            //text.setAdapter(adapter);

            return languages;
        }


    }





    private void nextActivity(Profile profile, String accessToken) {
        if (profile != null) {
            Intent main = new Intent(LoginActivity.this, ParentActivity2.class);
            myCustomer.setmFacebook(true);
            myCustomer.setmGoogle(false);
            myCustomer.setmGouiranLink(false);
            myCustomer.setName(profile.getName());
            myCustomer.setSurname(profile.getLastName());
            myCustomer.setBirthday(birthday);
            myCustomer.setEmail(email);
            main.putExtra("MyCustomer", myCustomer);
            main.putExtra("token",accessToken);
            String json = "{\n" +
                    "\"accessToken\":\"" + AccessToken.getCurrentAccessToken().getToken() + "\",\n" +
                    "\"userID\":\"" + profile.getId() + "\"\n" +
                    "}\n";

            PostRequest postRequest = new PostRequest("https://www.gouiran-beaute.com/link/api/v1/authentication/facebook/", json);
            String resp = null;
            try {
                resp = postRequest.execute().get();
                Toast.makeText(this, resp, Toast.LENGTH_SHORT).show();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            Customer customer = null;
            if (resp != null && resp.contains("access_token")) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(resp);
                    accessToken = jsonObject.getString("access_token");
                    GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/authentication/user/", "Authorization", "Token " + accessToken);
                    resp = getRequest.execute().get();
                    RetrieveCustomerInformationFromRequest retrieveCustomerInformationFromRequest = new RetrieveCustomerInformationFromRequest(resp, accessToken);
                    customer = retrieveCustomerInformationFromRequest.generateCustomer();
                    customer.setmFacebook(true);
                    customer.setmGoogle(false);
                    customer.setmGouiranLink(false);
                } catch (JSONException | ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            main.putExtra("Customer", customer);
            main.putExtra("token",accessToken);
            startActivity(main);

        }
    }


    private void nextActivity(GoogleSignInAccount acct) {
        if (acct != null) {
            Intent main = new Intent(LoginActivity.this, ParentActivity2.class);
            myCustomer.setmFacebook(false);
            myCustomer.setmGoogle(true);
            myCustomer.setmGouiranLink(false);
            myCustomer.setName(acct.getFamilyName());
            myCustomer.setSurname(acct.getGivenName());
            Toast.makeText(this, acct.getGivenName(), Toast.LENGTH_SHORT).show();
            myCustomer.setEmail(acct.getEmail());
            myCustomer.setBirthday(null);
            String token = acct.getIdToken();
            String json = "{\n" +
                    "\"tokenId\":\"" + token + "\",\n" +
                    "\"googleId\":\"" + getString(R.string.server_client_id) + "\"\n" +
                    "}\n";
            PostRequest postRequest = new PostRequest("https://www.gouiran-beaute.com/link/api/v1/authentication/google/", json);
            String resp = null;
            try {
                resp = postRequest.execute().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            String accessToken = null;
            Customer customer = null;
            if (resp != null && resp.contains("access_token")) {
                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    accessToken = jsonObject.getString("access_token");
                    GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/authentication/user/", "Authorization", "Token " + accessToken);
                    resp = getRequest.execute().get();
                    RetrieveCustomerInformationFromRequest retrieveCustomerInformationFromRequest = new RetrieveCustomerInformationFromRequest(resp, accessToken);
                    customer = retrieveCustomerInformationFromRequest.generateCustomer();
                    customer.setmFacebook(false);
                    customer.setmGoogle(true);
                    customer.setmGouiranLink(false);
                } catch (JSONException | InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            main.putExtra("Customer", customer);
            main.putExtra("token",accessToken);
            startActivity(main);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        nextActivity(profile, String.valueOf(AccessToken.getCurrentAccessToken()));
    }


    protected void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(this, acct.getDisplayName(), Toast.LENGTH_LONG).show();
            nextActivity(acct);
        }
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        //mProgressDialog.show();
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public static void signOut(GoogleApiClient googleApiClient) {
        googleApiClient.connect();
        if (googleApiClient.isConnected()) {
            Log.d("IS", "CONNECTED");
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // [START_EXCLUDE]
                            // [END_EXCLUDE]
                        }
                    });
        } else
            Log.d("IS NOT", "CONNECTED");
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
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
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

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
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            //mImageLoginView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String json;
        private final PostRequest postRequest;
        private final Boolean connected;
        private String resp;

        UserLoginTask(String email, String password) {
            mEmail = email;
            json = "{\n" +
                    "\"public_key\":\"" + email + "\",\n" +
                    "\"private_key\":\"" + password + "\",\n" +
                    "\"kind\":\"Customer\"\n" +
                    "}\n";
            postRequest = new PostRequest("https://www.gouiran-beaute.com/link/api/v1/authentication/signin/", json);
            resp = null;
            try {
                resp = postRequest.execute().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            assert resp != null;
            if (resp.contains("access_token")) {
                connected = true;

                Log.d("true", "true");
            } else {
                connected = false;
                Log.d("false", "false");
            }
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

            // return (true);
            return (connected);

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

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                /*Intent i = new Intent(LoginActivity.this, ParentActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("connected", true);
                b.putString("token_access", "token_access");
                b.putString("email", mEmail);
                i.putExtras(b);
                startActivity(i);
                finish();*/
                nextActivity(resp);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.sign_in_button:
                //signIn();
                break;
            case R.id.google:
                signInButton.performClick();
                signIn();
                break;
            case R.id.fb:
                loginButton.performClick();
                break;
            case R.id.email_sign_up_button:
                System.out.println("ON VA CREEEEEER UN COMPTE");
                Toast.makeText(this, "Creating account", Toast.LENGTH_LONG);
                i = new Intent(this, SignUp.class);
                startActivity(i);
                finish();
                break;
            case R.id.ignorer_pour_l_instant:
                Bundle b = new Bundle();
                b.putBoolean("connected", false);
                String mEmail = "";
                b.putString("email", mEmail);
                //i = new Intent(LoginActivity.this, ParentActivity.class);
                i = new Intent(LoginActivity.this, ParentActivity2.class);
                i.putExtras(b);
                startActivity(i);
                finish();
                break;
            case R.id.mot_de_passe_oublie:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("Mot de passe oublié");
                alert.setMessage("Veuillez entrer l'adresse e-mail utilisé lors de la création de votre compte.");
                final EditText input = new EditText(this);
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        String email = input.getText().toString();
                        String resp = null;
                        String json = "{\n" +
                                "\"public_key\":\"" + email + "\",\n" +
                                "\"kind\":\"Customer\"\n" +
                                "}\n";
                        PostRequest postRequest = new PostRequest("https://www.gouiran-beaute.com/link/api/v1/authentication/lost-password/", json);

                        try {
                            resp = postRequest.execute().get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        System.out.println(resp);


                       /* byte [] b =null;
                        b = resp.getBytes();


                        byte[] someBytes = b;
                        String encodingName = "ISO-8859-1";
                        String s = "";
                        try {
                            s = new String ( resp.getBytes("UTF-8"), encodingName );
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }*/
                        //System.out.println("SSSSSSSS");

                        String s = null;
                        try {
                            String resp2 = resp.replace("\"", "");
                            s = new String( resp2.getBytes(), "ISO-8859-1" );
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        System.out.println("SSSSSSSS1" + s);

                        s = null;
                        try {
                            String resp2 = resp.replace("\"", "");
                            s = new String( resp2.getBytes(),"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        System.out.println("SSSSSSSS2" + s);

                        s = null;
                        try {
                            String resp2 = resp.replace("\"", "");
                            s = new String( resp2.getBytes(), "UTF-16" );
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        System.out.println("SSSSSSSS3" + s);

                        s = null;
                        try {
                            String resp2 = resp.replace("\"", "");
                            s = new String( resp2.getBytes("ISO-8859-1"), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        System.out.println("SSSSSSSS4" + s);

                        s = "Il est temps de vérifier votre boite mail ! Un email vient de vous être envoyé pour modifier votre mot de passe";

                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        System.out.println(resp);
                    }
                });
                alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                alert.show();

                break;
        }
    }

    public static void hideKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                0);
    }




}

