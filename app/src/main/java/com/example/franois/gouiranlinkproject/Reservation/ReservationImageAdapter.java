package com.example.franois.gouiranlinkproject.Reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.ToolsClasses.DownloadImageTask;

import java.util.ArrayList;
import java.util.List;

public class ReservationImageAdapter extends BaseAdapter {
    final private Context mContext;
    private List<String> institutesNames = new ArrayList<String>();
    //private List<String> types = new ArrayList<String>();
    List<List<String>> types = new ArrayList<List<String>>();
    private List<String> pictures = new ArrayList<String>();
    private List<String> dates = new ArrayList<String>();
    private List<String> hours = new ArrayList<String>();


    ReservationImageAdapter(Context c) {
        mContext = c;
    }

    public Context getmContext() {
        return mContext;
    }

    public List<String> getInstitutesNames() {
        return institutesNames;
    }

    public void setInstitutesNames(List<String> institutesNames) {
        this.institutesNames = institutesNames;
    }

    public List<List<String>> getTypes() {
        return types;
    }

    public void setTypes(List<List<String>> types) {
        this.types = types;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<String> getHours() {
        return hours;
    }

    public void setHours(List<String> hours) {
        this.hours = hours;
    }

    public int getCount() {
        return institutesNames.size();
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
        String type;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single_reservation, null);
            TextView textView = (TextView)grid.findViewById(R.id.institute_name);
            ImageView imageView = (ImageView)grid.findViewById(R.id.picture);
            textView.setText(institutesNames.get(position));
            new DownloadImageTask(imageView).execute(pictures.get(position));
            textView = (TextView)grid.findViewById(R.id.type);
            type = "";
            for (int i = 0; i < types.get(position).size(); i++) {
                type += types.get(position).get(i);
                if (i + 1 < types.get(position).size())
                    type += " ";
            }
            textView.setText(type);
            textView = (TextView)grid.findViewById(R.id.date);
            textView.setText(dates.get(position));
            textView = (TextView)grid.findViewById(R.id.hour);
            textView.setText(hours.get(position));

        } else {
            grid = convertView;
        }
        return (grid);
    }

}
