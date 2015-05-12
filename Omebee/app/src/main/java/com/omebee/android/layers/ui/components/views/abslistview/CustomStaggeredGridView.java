package com.omebee.android.layers.ui.components.views.abslistview;

import android.content.Context;
import android.util.AttributeSet;

import com.etsy.android.grid.StaggeredGridView;

/**
 * Created by Thu Nguyen on 8/29/2014.
 */
public class CustomStaggeredGridView extends StaggeredGridView{
    private static final String TAG = "CustomStaggeredGridView";
    public CustomStaggeredGridView(Context context) {
        super(context);
    }

    public CustomStaggeredGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomStaggeredGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
