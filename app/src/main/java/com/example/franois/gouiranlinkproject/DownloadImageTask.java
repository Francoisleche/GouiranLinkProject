package com.example.franois.gouiranlinkproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by pyram_m on 13/01/17.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public DownloadImageTask(ImageView imageView){
        this.imageView = imageView;
    }

    protected Bitmap doInBackground(String ...args) {
        String url = args[0];
        Bitmap image = null;

        try {
            InputStream is = new URL(url).openStream();
            image= BitmapFactory.decodeStream(is);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return (image);
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
