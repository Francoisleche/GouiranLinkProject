package com.gouiranlink.franois.gouiranlinkproject.Account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses3.Object;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses3.RootAdapter;

import java.util.ArrayList;

/**
 * Created by François on 22/08/2017.
 */

public class FaqFragment3 extends Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_faq, container, false);


        Object  obj = new Object();
        Constant o = new Constant();
        obj.children =  new ArrayList<Object>();
        for(int i = 0; i< o.faqTitretitreList.length/*Constant.state.length*/; i++)
        {
            Object root =  new Object();
            root.title = o.faqTitretitreList[i];/*Constant.state[i]*/;
            root.children =  new ArrayList<Object>();
            for(int j=0;j<o.faqTitreList[i].length/*Constant.parent[i].length*/;j++)
            {
                Object parent =  new Object();
                parent.title=o.faqTitreList[i][j];/*Constant.parent[i][j];*/
                parent.children =  new ArrayList<Object>();
                for(int k=0;k<o.faqTexteList[i][j].length/*Constant.child[i][j].length*/;k++)
                {
                    Object child =  new Object();
                    child.title = o.faqTexteList[i][j][k];/*Constant.child[i][j][k]*/;
                    parent.children.add(child);
                }

                //Enlever ceux qui n'ont rien
                if(parent.title != null){
                    if(!parent.title.equals("")){
                        root.children.add(parent);
                    }
                }
                //root.children.add(parent);
            }
            obj.children.add(root);
        }

        if (!obj.children.isEmpty()) {
            final ExpandableListView elv = (ExpandableListView) view.findViewById(R.id.expandableListView);

            elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {

                    return false; /* or false depending on what you need */
                }
            });


            ExpandableListView.OnGroupClickListener grpLst = new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView eListView, View view, int groupPosition,
                                            long id) {

                    return false/* or false depending on what you need */;
                }
            };


            ExpandableListView.OnChildClickListener childLst = new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView eListView, View view, int groupPosition,
                                            int childPosition, long id) {

                    return false/* or false depending on what you need */;
                }
            };

            ExpandableListView.OnGroupExpandListener grpExpLst = new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {

                }
            };

            final RootAdapter adapter = new RootAdapter(getActivity(), obj, grpLst, childLst, grpExpLst);
            elv.setAdapter(adapter);
        }

    return view;
    }
}
