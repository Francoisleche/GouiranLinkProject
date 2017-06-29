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
import java.util.HashMap;
import java.util.List;

/**
 * Created by François on 15/06/2017.
 */

public class PrendreRdv1Adpater extends BaseAdapter {

        private Activity mContext;
        private ArrayList<Professional_Product> mList;
        private Professional_Product[] mList2;
        private HashMap<String, List<String>> mList3;
        private LayoutInflater mLayoutInflater = null;
        private int position_annuler;
        private boolean clique = false;

        public PrendreRdv1Adpater(Activity context, ArrayList<Professional_Product> list, Professional_Product[] list2, HashMap<String, List<String>> list3) {
            mContext = context;
            mList = list;
            mList2 = list2;
            mList3 = list3;
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

                    Professional_Product p = mList.get(position);
                    System.out.println("OOOOOOOOOOOh : "+p.getProduct().getCategory().getName());

                    mList.remove(position); //or some other task
                    notifyDataSetChanged();


                    if(p.getProduct().getCategory().getName().equals("Coiffure Femme")){
                        System.out.println("1");
                        mList3.get("Coiffure Femme").add(p.getName()+"////"+p.getPrice()+"////"+p.getDuration()+"////"+p.getProduct().getDescription()+"////"+p.getDiscount());
                        System.out.println("1.1");
                    }
                    else if(p.getProduct().getCategory().getName().equals("Bien-Être")){
                        System.out.println("2");
                        mList3.get("Bien-Être").add(p.getName()+"////"+p.getPrice()+"////"+p.getDuration()+"////"+p.getProduct().getDescription()+"////"+p.getDiscount());
                        System.out.println("2.1");
                    }
                    else if(p.getProduct().getCategory().getName().equals("Beauté")){
                        System.out.println("3");
                        mList3.get("Beauté").add(p.getName()+"////"+p.getPrice()+"////"+p.getDuration()+"////"+p.getProduct().getDescription()+"////"+p.getDiscount());
                        System.out.println("3.1");
                    }
                    else if(p.getProduct().getCategory().getName().equals("Homme")){
                        System.out.println("4");
                        mList3.get("Homme").add(p.getName()+"////"+p.getPrice()+"////"+p.getDuration()+"////"+p.getProduct().getDescription()+"////"+p.getDiscount());
                        System.out.println("4.1");
                    }
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