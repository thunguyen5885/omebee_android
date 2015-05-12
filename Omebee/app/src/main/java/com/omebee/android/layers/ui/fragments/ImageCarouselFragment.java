package com.omebee.android.layers.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseFragment;
import com.omebee.android.layers.ui.components.views.util.CarouselLayout;

import java.util.List;

/**
 * Created by ThuNguyen on 8/6/2014.
 */
public class ImageCarouselFragment extends BaseFragment{
    private static final String TAG = "ImageCarouselFragment";
    private CarouselLayout mCarouselLayout;
    public ImageCarouselFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carousel,
                container, false);
        mCarouselLayout = (CarouselLayout) view.findViewById(R.id.carouselLayout);
            mCarouselLayout.init();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

    }


    /**
     * Show a list of images
     * @param mImageList
     */
    public void showProductImageList(List<String> mImageList){
        if(mImageList != null && mImageList.size() > 0){
            mCarouselLayout.showProductImageList(mImageList);
        }
    }

}
