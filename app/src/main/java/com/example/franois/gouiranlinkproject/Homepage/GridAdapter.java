package com.example.franois.gouiranlinkproject.Homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.ToolsClasses.DownloadImageTask;

/**
 * Created by pyram_m on 31/03/17.
 */

public class GridAdapter extends BaseAdapter {
    final private Context mContext;
    final private String[] mShopName;
    final private String[] mShopImage;


    GridAdapter(Context c, String[] shopNameList, String[] shopImageList) {
        mContext = c;
        mShopImage = shopImageList;
        mShopName = shopNameList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        System.out.println(mShopName[0]);
        if (convertView == null) {
            System.out.println("GRID= " + mShopName[position]);
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single_recent, null);
            // First
            TextView textView = (TextView)grid.findViewById(R.id.firstText);
            ImageView imageView = (ImageView)grid.findViewById(R.id.firstImage);
            textView.setText(mShopName[position]);
            new DownloadImageTask(imageView).execute(mShopImage[position]);
            // Second
            if (mShopImage[position].length() < position + 1) {
                textView = (TextView) grid.findViewById(R.id.firstText);
                imageView = (ImageView) grid.findViewById(R.id.firstImage);
                textView.setText(mShopName[position]);
                new DownloadImageTask(imageView).execute(mShopImage[position]);
            }
            // Third
            if (mShopImage[position].length() < position + 2) {
                textView = (TextView) grid.findViewById(R.id.firstText);
                imageView = (ImageView) grid.findViewById(R.id.firstImage);
                textView.setText(mShopName[position]);
                new DownloadImageTask(imageView).execute(mShopImage[position]);
            }
        } else {
            System.out.println("ELSRGRID");
            grid = convertView;
        }

        return (grid);
    }
}
