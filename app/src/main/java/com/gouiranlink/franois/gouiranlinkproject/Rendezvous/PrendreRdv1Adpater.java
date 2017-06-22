package com.gouiranlink.franois.gouiranlinkproject.Rendezvous;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.R;

import java.util.ArrayList;

/**
 * Created by François on 15/06/2017.
 */

public class PrendreRdv1Adpater extends BaseAdapter {

        private Activity mContext;
        private ArrayList<Professional_Product> mList;
        private ArrayList<Professional_Product> mList2;
        private LayoutInflater mLayoutInflater = null;
        private int position_annuler;
        private boolean clique = false;

        public PrendreRdv1Adpater(Activity context, ArrayList<Professional_Product> list,ArrayList<Professional_Product> list2) {
            mContext = context;
            mList = list;
            mList2 = list2;
            mLayoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return mList.size();
        }
        @Override
        public Object getItem(int pos) {
            return mList.get(pos);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            CompleteListViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater li = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.services_prendrerdv1, null);
                viewHolder = new CompleteListViewHolder(v);
                v.setTag(viewHolder);
            } else {
                viewHolder = (CompleteListViewHolder) v.getTag();
            }
            viewHolder.mTVItem.setText(mList.get(position).getName());


            Button deleteBtn = (Button) v.findViewById(R.id.button_prendrerdv1);
            deleteBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //do something
                    mList2.add(mList.get(position));
                    notifyDataSetChanged();

                    mList.remove(position); //or some other task
                    notifyDataSetChanged();

                }
            });


            return v;
        }

    public int getPositionAnnuler() {
        return position_annuler;
    }

    public void setPositionAnnuler(int position_annuler) {
        this.position_annuler = position_annuler;
    }

    public boolean isClique() {
        return clique;
    }

    public void setClique(boolean clique) {
        this.clique = clique;
    }

}
    class CompleteListViewHolder {
        public TextView mTVItem;
        public CompleteListViewHolder(View base) {
            mTVItem = (TextView) base.findViewById(R.id.textprendrerdv1);
        }
    }