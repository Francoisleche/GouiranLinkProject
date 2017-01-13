package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

public class PostRequest extends AsyncTask<String, Void, String>{

    static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    String url = "";
    String json = "";
    OkHttpClient client = new OkHttpClient();

    public PostRequest (String url) {
        this.url = url;
    }

    public PostRequest (String url, String json) {
        this.url = url;
        this.json = json;
    }

    public String postRequest() throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string();
        Log.d("1 TESTTEST", res);
        return (res);
    }

    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        try {
            res = this.postRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("2 TESTTEST", res);
        return (res);
    }
}
