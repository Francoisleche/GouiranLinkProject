package com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4;

/**
 * Created by François on 24/08/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.R;

/**
 * Created by François on 22/08/2017.
 */

public class RootAdapter2 extends BaseExpandableListAdapter {

    private Object2 root;

    private final LayoutInflater inflater;

    public class Entry {
        public final CustExpListview2 cls;
        public final SecondLevelAdpater2 sadpt;

        public Entry(CustExpListview2 cls, SecondLevelAdpater2 sadpt) {
            this.cls = cls;
            this.sadpt = sadpt;
        }
    }

    public com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.RootAdapter2.Entry[] lsfirst;

    public RootAdapter2(Context context, Object2 root, ExpandableListView.OnGroupClickListener grpLst,
                       ExpandableListView.OnChildClickListener childLst, ExpandableListView.OnGroupExpandListener grpExpLst) {
        this.root = root;
        this.inflater = LayoutInflater.from(context);

        lsfirst = new com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.RootAdapter2.Entry[root.children.size()];


        for (int i = 0; i < root.children.size(); i++) {
            int nombre = 0;
            for(int j =0;j<root.children.get(i).children.size();j++){
                if(root.children.get(i).children.get(j).title != null){
                    if(!root.children.get(i).children.get(j).title.equals("")) {
                        if(root.children.get(i).children.get(j).title.length()>50) {
                            System.out.println("Nombre a afficher : " + root.children.get(i).children.get(j).title);
                            nombre++;
                        }else{

                        }
                    }
                }
            }
            //final CustExpListview celv = new CustExpListview(context,root.children.get(i).children.size());
            final CustExpListview2 celv = new CustExpListview2(context);
            SecondLevelAdpater2 adp = new SecondLevelAdpater2(root.children.get(i),context);
            celv.setAdapter(adp);
            celv.setGroupIndicator(null);
            celv.setOnChildClickListener(childLst);
            celv.setOnGroupClickListener(grpLst);
            celv.setOnGroupExpandListener(grpExpLst);

            lsfirst[i] = new com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.RootAdapter2.Entry(celv, adp);
        }

    }

    @Override
    public Object2 getChild(int groupPosition, int childPosition) {
        return root.children.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        // second level list
        return lsfirst[groupPosition].cls;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object2 getGroup(int groupPosition) {
        return root.children.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return root.children.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        // first level
        View layout = convertView;
        com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.RootAdapter2.GroupViewHolder holder;
        final Object2 item = (Object2) getGroup(groupPosition);

        if (layout == null) {
            layout = inflater.inflate(R.layout.filtre_root_expandable_item, parent, false);
            holder = new com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.RootAdapter2.GroupViewHolder();
            holder.title = (TextView) layout.findViewById(R.id.itemRootTitleFilter);
            layout.setTag(holder);
        } else {
            holder = (com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.RootAdapter2.GroupViewHolder) layout.getTag();
        }

        System.out.println("item.title : "+item.title);
        holder.title.setText(item.title.trim());

        return layout;
    }

    private static class GroupViewHolder {
        TextView title;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}