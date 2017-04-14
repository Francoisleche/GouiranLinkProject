package com.gouiranlink.franois.gouiranlinkproject.ToolsClasses;

import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostFileRequest extends AsyncTask<String, Void, String>{

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String url = "";
    private String id = "";
    private String key = "";
    private String value = "";
    private String file = null;
    final private OkHttpClient client = new OkHttpClient();

    public PostFileRequest (String url, String id, String file, String key, String value) {
        this.url = url;
        this.id = id;
        this.file = file;
        this.key = key;
        this.value = value;
    }

    private String postFileRequest() throws IOException {

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("customer_id", id)
                .addFormDataPart("file", file, RequestBody.create(MultipartBody.FORM, new File(file))).build();
        Request request = new Request.Builder()
                .url("https://www.gouiran-beaute.com/link/api/v1/customer/upload/image/customer/1487/")
                .post(body)
                .addHeader(key, value)
                .addHeader("Content-type", "multipart/form-data")
                .build();

        Response response = this.client.newCall(request).execute();
        return (response.body().string());
    }

    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        try {
            res = this.postFileRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (res);
    }
}
