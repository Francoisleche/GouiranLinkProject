package com.gouiranlink.franois.gouiranlinkproject.InsciptionConnexion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.ParentActivity2;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.MyCustomer;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.PostRequest;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.RetrieveCustomerInformationFromRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginGoogleActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static GoogleApiClient mGoogleApiClient;
    final private MyCustomer myCustomer = new MyCustomer();
    SignInButton signInButton;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";

    private View mProgressView;
    private View mLoginFormView;


    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_google);

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

    }

    private void nextActivity(GoogleSignInAccount acct) {
        if (acct != null) {
            Intent main = new Intent(LoginGoogleActivity.this, ParentActivity2.class);
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
            startActivity(main);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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


    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(this, acct.getDisplayName(), Toast.LENGTH_LONG).show();
            nextActivity(acct);
        }
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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google:
                signInButton.performClick();
                signIn();
                break;
            default:
                break;
        }

    }
}
