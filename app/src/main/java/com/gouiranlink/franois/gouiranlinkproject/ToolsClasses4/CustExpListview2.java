package com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4;

/**
 * Created by François on 24/08/2017.
 */

import android.content.Context;
import android.widget.ExpandableListView;

/**
 * Created by François on 22/08/2017.
 */

public class CustExpListview2 extends ExpandableListView {

    int nombre;

    public CustExpListview2(Context context) {
        super(context);
    }

    public CustExpListview2(Context context, int nombre) {
        super(context);
        this.nombre=nombre;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //widthMeasureSpec = MeasureSpec.makeMeasureSpec(600, MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(10000000, MeasureSpec.AT_MOST);
        //widthMeasureSpec = ViewGroup.LayoutParams.MATCH_PARENT;
        //heightMeasureSpec = ViewGroup.LayoutParams.WRAP_CONTENT;
        //System.out.println("taille : "+nombre);
        //heightMeasureSpec = MeasureSpec.makeMeasureSpec((nombre*200), MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDetachedFromWindow() {
        try {
            super.onDetachedFromWindow();
        } catch (IllegalArgumentException e) {
            // TODO: Workaround for
            // http://code.google.com/p/android/issues/detail?id=22751
        }
    }
}