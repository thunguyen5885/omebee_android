package com.omebee.android.layers.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseFragment;
import com.omebee.android.layers.ui.components.CarouselLayout;
import com.omebee.android.layers.ui.components.adapters.ImagePagerAdapter;
import com.omebee.android.layers.ui.components.viewpager.JazzyViewPager;
import com.omebee.android.layers.ui.presenters.ProductDetailPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.IPresenter;

import java.util.Collections;
import java.util.List;

/**
 * Created by ThuNguyen on 8/6/2014.
 */
public class ImageCarouselFragment extends BaseFragment{
    private static final String TAG = "ImageCarouselFragment";
    private ProductDetailPresenterImpl mPresenter;
    private CarouselLayout mCarouselLayout;
    private String mProductId;
    public ImageCarouselFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ctrl_carousel,
                container, false);
        if(view instanceof CarouselLayout){
            mCarouselLayout = (CarouselLayout) view;
            mCarouselLayout.init();
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        if(mPresenter != null && mProductId != null) {
            mPresenter.getProductFromId(mProductId);
        }
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
    public String getProductId() {
        return mProductId;
    }

    public void setProductId(String productId) {
        this.mProductId = productId;
    }

    public ProductDetailPresenterImpl getPresenter() {
        return mPresenter;
    }
    @Override
    public void setPresenter(IPresenter mPresenter) {
        this.mPresenter = (ProductDetailPresenterImpl) mPresenter;
    }

}
