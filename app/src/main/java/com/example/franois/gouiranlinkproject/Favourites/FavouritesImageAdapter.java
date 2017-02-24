package com.example.franois.gouiranlinkproject.Favourites;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.ToolsClasses.DownloadImageTask;

import java.util.ArrayList;
import java.util.List;

class FavouritesImageAdapter extends BaseAdapter {
    final private Context mContext;
    private List<String> mThumbIds = new ArrayList<String>();
    private List<String> mThumbNames = new ArrayList<String>();

    FavouritesImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.size();
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
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single_pros, null);
            TextView textView = (TextView)grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText(mThumbNames.get(position));
            new DownloadImageTask(imageView).execute(mThumbIds.get(position));

        } else {
            grid = convertView;
        }
        return (grid);
    }

    public List<String> getmThumbIds() {
        return mThumbIds;
    }

    public void setmThumbIds(List<String> mThumbIds) {
        this.mThumbIds = mThumbIds;
    }

    public List<String> getmThumbNames() {
        return mThumbNames;
    }

    public void setmThumbNames(List<String> mThumbNames) {
        this.mThumbNames = mThumbNames;
    }
}
