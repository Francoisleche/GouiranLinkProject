package com.gouiranlink.franois.gouiranlinkproject.ToolsClasses;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



/**
 * Created by François on 07/04/2017.
 */

public class PatchRequest extends AsyncTask<String, Void, String> {

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String url = "";
    private String json = "";
    private String key = null;
    private String value = null;
    private String headerKey = null;
    private String headerValue = null;
    final private OkHttpClient client = new OkHttpClient();

    public PatchRequest (String url) {
        this.url = url;
    }

    public PatchRequest (String url, String json) {
        this.url = url;
        this.json = json;
    }

    public PatchRequest(String url, String headerKey, String headerValue) {
        this.url = url;
        this.headerKey = headerKey;
        this.headerValue = headerValue;
        Log.d("HEADER", "HEADER");
    }

    public PatchRequest (String url, String json, String key, String value) {
        this.url = url;
        this.json = json;
        this.key = key;
        this.value = value;
    }


    private String PatchRequest() throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request;
        Response response;
        if (this.key == null && this.value == null) {
            request = new Request.Builder()
                    .url(url)
                    .patch(requestBody)
                    .build();
        }
        else {
            request = new Request.Builder()
                    .url(url)
                    .patch(requestBody)
                    .addHeader(key, value)
                    .build();
        }
        response = client.newCall(request).execute();
        String res = response.body().string();
        Log.d("-1 TESTTEST", res);
        /*if (res != null && !res.equals("")) {
            res = StringEscapeUtils.unescapeJava(res);
            int ind = res.indexOf("\"");
            Log.d("0 TESTTEST", res);
            res = new StringBuilder(res).replace(ind, ind + 1, "").toString();
            ind = res.lastIndexOf("\"");
            res = new StringBuilder(res).replace(ind, ind + 1, "").toString();
            Log.d("1 TESTTEST", res);
        }*/
        return (res);
    }

    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        try {
            res = this.PatchRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (res);
    }
}