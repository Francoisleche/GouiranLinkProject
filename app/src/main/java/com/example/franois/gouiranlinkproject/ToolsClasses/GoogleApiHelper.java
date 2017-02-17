package com.example.franois.gouiranlinkproject.ToolsClasses;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

public class GoogleApiHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = GoogleApiHelper.class.getSimpleName();
    Context context;
    GoogleApiClient mGoogleApiClient;

    public GoogleApiHelper(Context context) {
        buildGoogleApiClient();
        connect();
        this.context = context;
    }

    public GoogleApiClient getGoogleApiClient() {
        return this.mGoogleApiClient;
    }

    public void connect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void disconnect() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    public boolean isConnected() {
        if (mGoogleApiClient != null) {
            return mGoogleApiClient.isConnected();
        } else {
            return false;
        }
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }

    @Override
    public void onConnected(Bundle bundle) {
        //You are connected do what ever you want
        //Like i get last known location
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: googleApiClient.connect()");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: connectionResult.toString() = " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}