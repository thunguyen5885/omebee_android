package com.omebee.android.layers.ui.components;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.omebee.android.R;
import com.omebee.android.layers.ui.components.adapters.ImagePagerAdapter;
import com.omebee.android.layers.ui.components.viewpager.JazzyViewPager;
import com.omebee.android.utils.AppFnUtils;

import java.util.List;

/**
 * Created by ThuNguyen on 9/17/2014.
 */
public class CarouselLayout extends LinearLayout{
    private JazzyViewPager mViewPager;
    private CarouselIndicatorLayout mIndicatorLayout;
    private int mImageListSize = 0;
    public CarouselLayout(Context context) {
        super(context);
    }

    public CarouselLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CarouselLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
//        // Make the height of layout be a half of screen height
        int height = AppFnUtils.getScreenHeight((Activity)getContext()) / 2;
        // Update layout params for viewpager to full_width and half_height
        LayoutParams params =  new LayoutParams(width, height);
        mViewPager.setLayoutParams(params);
    }

    public void init(){
        mViewPager = (JazzyViewPager) findViewById(R.id.jazzy_pager);
        mViewPager.setPageMargin(10);
        mViewPager.setOnPageChangeListener(onPageChangeListener);
        mIndicatorLayout = (CarouselIndicatorLayout) findViewById(R.id.carouselIndicatorLayout);
        // Setup jazzy viewpager
        //setupJazzy(JazzyViewPager.TransitionEffect.Tablet);
        setupJazzy(JazzyViewPager.TransitionEffect.Standard);
    }

    /**
     * Initialize the jazzy view pager
     * @param effect
     */
    private void setupJazzy(JazzyViewPager.TransitionEffect effect) {
        mViewPager.setTransitionEffect(effect);
        //mViewPager.setPageMargin(0);
    }
    /**
     * Show a list of images
     * @param mImageList
     */
    public void showProductImageList(List<String> mImageList){
        if(mImageList != null && mImageList.size() > 0){
            ImagePagerAdapter pagerAdapter = new ImagePagerAdapter(mViewPager);
            pagerAdapter.setImageUrls(mImageList);
            mViewPager.setAdapter(pagerAdapter);
            pagerAdapter.initPositionToShow();
            // Also create carousel indicator
            mImageListSize = mImageList.size();
            if(mImageListSize > 0) {
                mIndicatorLayout.createChildLayout(mImageListSize);
            }
        }
    }
    // For page change listener
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int position) {
            if(mImageListSize > 0) {
                position = position % mImageListSize;
                mIndicatorLayout.setSelection(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
