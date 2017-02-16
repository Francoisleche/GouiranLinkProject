package com.example.franois.gouiranlinkproject.ToolsClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    final private ImageView imageView;

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