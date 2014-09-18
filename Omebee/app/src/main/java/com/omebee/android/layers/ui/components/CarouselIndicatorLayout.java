package com.omebee.android.layers.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.omebee.android.R;
import com.omebee.android.utils.AppFnUtils;

/**
 * Created by ThuNguyen on 9/17/2014.
 */
public class CarouselIndicatorLayout extends LinearLayout{
    public static final int NUM_OF_CHILD_MAXIMUM = 15;
    private int mSelectedIndex = -1;
    private LayoutInflater mLayoutInflater;

    public CarouselIndicatorLayout(Context context) {
        super(context);
        init(context);
    }

    public CarouselIndicatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CarouselIndicatorLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    public void setSelection(int position){
        if(position >= NUM_OF_CHILD_MAXIMUM || getChildCount() < 2){
            return;
        }
        if(mSelectedIndex >= 0){
            View lastSelectedView = getChildAt(mSelectedIndex);
            lastSelectedView.setBackgroundResource(R.drawable.layout_carousel_indicator_background_normal);
        }
        if(mSelectedIndex != position){
            mSelectedIndex = position;
            View selectedView = getChildAt(mSelectedIndex);
            selectedView.setBackgroundResource(R.drawable.layout_carousel_indicator_background_selected);
        }
    }
    private void init(Context context){
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * Create child view
     * @param numOfChild
     */
    public void createChildLayout(int numOfChild){
        if(numOfChild > NUM_OF_CHILD_MAXIMUM || numOfChild < 2){
            return;
        }
        for(int index = 0; index < numOfChild; index++){
            View view = mLayoutInflater.inflate(R.layout.ctr_carousel_indicator, null);
            // Set fixed width and height for child view
            int childWidth = AppFnUtils.dpToPx(getResources(), 10);
            int childHeight = AppFnUtils.dpToPx(getResources(), 10);
            int margin = AppFnUtils.dpToPx(getResources(), 2);
            LinearLayout.LayoutParams params = new LayoutParams(childWidth, childHeight);
            params.leftMargin = margin;
            params.rightMargin = margin;
            view.setBackgroundResource(R.drawable.layout_carousel_indicator_background_normal);
            addView(view, params);
        }
        // Set selection 0 for default
        if(numOfChild > 0){
            setSelection(0);
        }
    }

}
