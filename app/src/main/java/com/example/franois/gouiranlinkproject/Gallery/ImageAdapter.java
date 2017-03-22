package com.example.franois.gouiranlinkproject.Gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.franois.gouiranlinkproject.R;

import java.io.File;

import static java.security.AccessController.getContext;

/*
Represents the grid for the gallery
 */

class ImageAdapter extends BaseAdapter {
    final private Context mContext;
    static final int PICK_IMAGE_REQUEST = 1;
    public static final int IMAGE_GALLERY_REQUEST = 20;

    final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"*/.jpg");
    final File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Stagiaire.png");


    ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    final private Integer[] mThumbIds = {
            R.drawable.imagegouiranlink1,
            R.drawable.gsxr, R.drawable.h2r,
            R.drawable.kawa, R.drawable.tiger,
            R.drawable.zzr1400,
            R.drawable.cb650f, R.drawable.er6n,
            R.drawable.gsxr, R.drawable.h2r,
            R.drawable.kawa, R.drawable.tiger,
            R.drawable.zzr1400,
            R.drawable.cb650f, R.drawable.er6n,
            R.drawable.gsxr, R.drawable.h2r,
            R.drawable.kawa, R.drawable.tiger
    };



}