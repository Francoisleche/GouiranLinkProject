package com.gouiranlink.franois.gouiranlinkproject.ToolsClasses;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRequest extends AsyncTask<String, Void, String>{

    static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String url = "";
    private String headerKey = null;
    private String headerValue = null;
    final private OkHttpClient client = new OkHttpClient();

    public GetRequest(String url) {
        this.url = url;
    }

    public GetRequest(String url, String headerKey, String headerValue) {
        this.url = url;
        this.headerKey = headerKey;
        this.headerValue = headerValue;
        Log.d("HEADER", "HEADER");
    }

    private String getRequest() throws IOException {
        Request request;
        if (headerKey != null && headerValue != null) {
            request = new Request.Builder()
                    .url(url)
                    .addHeader(headerKey, headerValue)
                    .build();
        }
        else {
            request = new Request.Builder()
                    .url(url)
                    .build();
        }
        Log.d("REQUEST GET + ", request.toString());
        Response response = client.newCall(request).execute();
        Log.d("REQUEST GET - ", response.body().toString());
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
