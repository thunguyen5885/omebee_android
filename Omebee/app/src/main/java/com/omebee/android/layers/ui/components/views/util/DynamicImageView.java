package com.omebee.android.layers.ui.components.views.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.android.volley.toolbox.NetworkImageView;
import com.etsy.android.grid.util.DynamicHeightImageView;

/**
 * Created by Thu Nguyen on 8/29/2014.
 */
public class DynamicImageView extends NetworkImageView{
    private double mHeightRatio;
    public DynamicImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DynamicImageView(Context context) {
        super(context);
    }
    public void setHeightRatio(double ratio) {
        if (ratio != mHeightRatio) {
            mHeightRatio = ratio;
            requestLayout();
        }
    }
    public double getHeightRatio() {
        return mHeightRatio;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHeightRatio > 0.0) {
            // set the image views size
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * mHeightRatio);
            setMeasuredDimension(width, height);
        }
        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


}
