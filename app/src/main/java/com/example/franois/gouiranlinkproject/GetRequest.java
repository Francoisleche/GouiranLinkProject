package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by pyram_m on 13/01/17.
 */

public class GetRequest extends AsyncTask<String, Void, String>{

    static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    String url = "";
    String json = "";
    OkHttpClient client = new OkHttpClient();

    public GetRequest(String url) {
        this.url = url;
    }

    public GetRequest(String url, String json) {
        this.url = url;
        this.json = json;
    }

    public String getRequest() throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return (response.body().string());
    }

    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        try {
            res = this.getRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (res);
    }
}
