package com.example.franois.gouiranlinkproject.InsciptionConnexion;

import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.Object.Customer;
import com.example.franois.gouiranlinkproject.ParentActivity;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.example.franois.gouiranlinkproject.ToolsClasses.MyCustomer;
import com.example.franois.gouiranlinkproject.ToolsClasses.PostRequest;
import com.example.franois.gouiranlinkproject.ToolsClasses.RetrieveCustomerInformationFromRequest;
import com.facebook.FacebookSdk;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginFacebookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    LoginButton loginButton;

    private View mProgressView;
    private View mLoginFormView;


    private ProgressDialog mProgressDialog;


    private String email = "";
    private String birthday = "";
    final private MyCustomer myCustomer = new MyCustomer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_facebook);

        FacebookSdk.sdkInitialize(getApplicationContext());

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
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
        AppEventsLogger.activateApp(getApplication());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), LoginFacebookActivity.ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }



    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(LoginFacebookActivity.ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginFacebookActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

    }


    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void nextActivity(Profile profile, String accessToken) {
        if (profile != null) {
            Intent main = new Intent(LoginFacebookActivity.this, ParentActivity.class);
            myCustomer.setmFacebook(true);
            myCustomer.setmGoogle(false);
            myCustomer.setmGouiranLink(false);
            myCustomer.setName(profile.getName());
            myCustomer.setSurname(profile.getLastName());
            myCustomer.setBirthday(birthday);
            myCustomer.setEmail(email);
            main.putExtra("MyCustomer", myCustomer);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb:
                loginButton.performClick();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
         showProgressDialog();
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


}

