package com.gouiranlink.franois.gouiranlinkproject.Favourites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.DownloadImageTask;

import java.util.ArrayList;
import java.util.List;

/*
Adapter which represents the layout for a single favourite
 */

class FavouritesImageAdapter extends BaseAdapter {
    final private Context mContext;
    private List<String> mThumbPictures = new ArrayList<String>();
    private List<String> mThumbNames = new ArrayList<String>();
    private List<Integer> mIds = new ArrayList<Integer>();

    FavouritesImageAdapter(Context c) {
        mContext = c;
    }

    public List<Integer> getmIds() {
        return mIds;
    }

    public void setmIds(List<Integer> mIds) {
        this.mIds = mIds;
    }

    public int getCount() {
        return mThumbPictures.size();
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
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single_pros, null);
            TextView textView = (TextView)grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText(mThumbNames.get(position));
            //Si le compte est Free, alors il ne possède pas de photo
            if(!mThumbPictures.get(position).equals("Free")){
                new DownloadImageTask(imageView).execute(mThumbPictures.get(position));
            }else{
                imageView.setImageResource(R.drawable.logo_free);
            }

        } else {
            grid = convertView;
        }
        return (grid);
    }

    public List<String> getmThumbPictures() {
        return mThumbPictures;
    }

    public void setmThumbPictures(List<String> mThumbPictures) {
        this.mThumbPictures = mThumbPictures;
    }

    public List<String> getmThumbNames() {
        return mThumbNames;
    }

    public void setmThumbNames(List<String> mThumbNames) {
        this.mThumbNames = mThumbNames;
    }
}
