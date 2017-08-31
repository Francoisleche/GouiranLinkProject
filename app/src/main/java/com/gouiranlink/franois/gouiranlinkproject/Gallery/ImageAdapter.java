package com.gouiranlink.franois.gouiranlinkproject.Gallery;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/*
Represents the grid for the gallery
 */

class ImageAdapter extends BaseAdapter implements Serializable {
    final private Context mContext;
    final ArrayList<String> mimages;
    static final int PICK_IMAGE_REQUEST = 1;
    public static final int IMAGE_GALLERY_REQUEST = 20;

    final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"*/.jpg");
    final File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Stagiaire.png");


    ImageAdapter(Context c, ArrayList<String> images) {
        mContext = c;
        mimages=images;
    }

    public int getCount() {
        return mimages.size();
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
            imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //Picasso.with(convertView.getContext()).load(mimages.get(position)).into(imageView);

        Glide.with(mContext).load("file://" + mimages.get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);


        //imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    /*public View getView(int position, View convertView, ViewGroup parent, int[] tab) {
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
    }*/



}