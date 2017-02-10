package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

class ImageAdapter extends BaseAdapter {
    final private Context mContext;

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
            // if it's not recycled, initialize some attributes
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
            R.drawable.cb650f, R.drawable.er6n,
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