package com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4;

/**
 * Created by François on 24/08/2017.
 */

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.R;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by François on 22/08/2017.
 */

public class SecondLevelAdpater2 extends BaseExpandableListAdapter {

    public Object2 child;
    Context mContext;
    LayoutInflater inflater;

    public SecondLevelAdpater2(Object2 child, Context context) {
        this.child = child;
        this.mContext=context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public Object2 getChild(int groupPosition, int childPosition) {
        return child.children.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // third level
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        View layout = convertView;
        final Object2 item = (Object2) getChild(groupPosition, childPosition);

        com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.SecondLevelAdpater2.ChildViewHolder holder;

        if (layout == null) {
            layout = inflater.inflate(R.layout.filtre_child_expandable_item, parent, false);

            holder = new com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.SecondLevelAdpater2.ChildViewHolder();
            holder.title = (TextView) layout.findViewById(R.id.itemChildFilter);
            layout.setTag(holder);
        } else {
            holder = (com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.SecondLevelAdpater2.ChildViewHolder) layout.getTag();
        }

        holder.title.setText(item.title.trim());



        return layout;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.children.get(groupPosition).children.size();
    }

    @Override
    public Object2 getGroup(int groupPosition) {
        return child.children.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        //return child.children.size();
        int nombre = 0;
        for(int i =0;i<child.children.size();i++){
            if(child.title!=null) {
                nombre++;
            }
        }

        return nombre;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // Second level
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        View layout = convertView;
        com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.SecondLevelAdpater2.ViewHolder holder;

        final Object2 item = (Object2) getGroup(groupPosition);

        if (layout == null) {
            layout = inflater.inflate(R.layout.filtre_parent_expandable_item, parent, false);
            holder = new com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.SecondLevelAdpater2.ViewHolder();
            holder.title = (TextView) layout.findViewById(R.id.itemParentTitleFilter);
            layout.setTag(holder);
        } else {
            holder = (com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.SecondLevelAdpater2.ViewHolder) layout.getTag();
        }

        //System.out.println("item.title.trim() : "+item.title.trim());
        //if(item.title.trim()!=null)

        if(item.title==null){
            holder.title.setVisibility(INVISIBLE);
            holder.title.setText("");
        }else{
            System.out.println("item.title.trim() : "+item.title.trim());
            System.out.println("item.title : "+item.title);
            System.out.println("item.title.trim() : "+item.title.trim());
            holder.title.setVisibility(VISIBLE);
            holder.title.setText(item.title.trim());
        }

        //holder.title.setText(item.title.trim());


        return layout;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }


    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        Log.d("SecondLevelAdapter", "Unregistering observer");
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class ViewHolder {
        TextView title;
    }

    private static class ChildViewHolder {
        TextView title;
    }

}