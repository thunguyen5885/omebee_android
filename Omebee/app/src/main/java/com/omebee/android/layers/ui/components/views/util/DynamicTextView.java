package com.omebee.android.layers.ui.components.views.util;

import android.content.Context;
import android.util.AttributeSet;

import com.etsy.android.grid.util.DynamicHeightTextView;

/**
 * Created by Thu Nguyen on 8/29/2014.
 */
public class DynamicTextView extends DynamicHeightTextView{
    private static final String TAG = "DynamicTextView";
    public DynamicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicTextView(Context context) {
        super(context);
    }
}
