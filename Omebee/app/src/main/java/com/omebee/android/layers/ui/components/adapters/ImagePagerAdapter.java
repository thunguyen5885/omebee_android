package com.omebee.android.layers.ui.components.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.omebee.android.R;
import com.omebee.android.layers.ui.components.OutlineContainer;
import com.omebee.android.layers.ui.components.viewpager.JazzyViewPager;
import com.omebee.android.utils.AppFnUtils;
import com.omebee.android.utils.ImageMemoryCache;

import java.util.List;

/**
 * Created by Thu Nguyen on 9/17/2014.
 */
public class ImagePagerAdapter extends PagerAdapter {
    private JazzyViewPager mViewPager;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private List<String> mImageUrls;
    public ImagePagerAdapter(JazzyViewPager viewPager){
        mViewPager = viewPager;
        mInflater = LayoutInflater.from(mViewPager.getContext());
        mImageLoader = new ImageLoader(Volley.newRequestQueue(mViewPager.getContext()), ImageMemoryCache.INSTANCE);

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mInflater.inflate(R.layout.ctrl_image_pager_item, null);
        // Initialize the networkimageview
        NetworkImageView ivProduct = (NetworkImageView) view.findViewById(R.id.ivProduct);
        int imageWidth = AppFnUtils.getScreenWidth((Activity)mViewPager.getContext());
        int imageHeight = AppFnUtils.getScreenHeight((Activity)mViewPager.getContext()) / 2;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(imageWidth, imageHeight);
        ivProduct.setLayoutParams(params);
        if(mImageUrls != null && position < mImageUrls.size()) {
            ivProduct.setImageUrl(mImageUrls.get(position), mImageLoader);
        }
        container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mViewPager.setObjectForPosition(view, position);
        return view;

    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object obj) {
        container.removeView(mViewPager.findViewFromObject(position));
    }
    @Override
    public int getCount() {
        return mImageUrls.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object obj) {
        if (view instanceof OutlineContainer) {
            return ((OutlineContainer) view).getChildAt(0) == obj;
        } else {
            return view == obj;
        }
    }

    public List<String> getImageUrls() {
        return mImageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.mImageUrls = imageUrls;
    }



}
