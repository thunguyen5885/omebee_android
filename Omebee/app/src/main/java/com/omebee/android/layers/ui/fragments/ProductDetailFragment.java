package com.omebee.android.layers.ui.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseFragment;
import com.omebee.android.layers.ui.components.data.ProductDetailItemData;
import com.omebee.android.layers.ui.presenters.ProductDetailPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.IPresenter;

/**
 * Created by ThuNguyen on 8/6/2014.
 */
public class ProductDetailFragment extends BaseFragment{
    private static final String TAG = "ImageCarouselFragment";
    private View mRootView;
    private ProductDetailPresenterImpl mPresenter;
    private String mProductId;

    public ProductDetailFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_product_detail,
                container, false);

        return mRootView;
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
     * @param
     */
    public void showProductDetail(ProductDetailItemData productData){
        updateProductOverview();
        updateProductDetail();
        updateShippingInfo();
        updateAlsoLike();
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

    /**
     * Update product overview (product name, description, price and cart)
     */
    private void updateProductOverview(){
        TextView tvProductName = (TextView)mRootView.findViewById(R.id.txt_productName);
        TextView tvProductDescription = (TextView)mRootView.findViewById(R.id.txt_productDescription);
        TextView tvSellingPrice = (TextView) mRootView.findViewById(R.id.txt_sellingPrice);
        TextView tvReducedPrice = (TextView) mRootView.findViewById(R.id.txt_reducedPrice);
        tvReducedPrice.setPaintFlags(tvReducedPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        TextView tvLikeCount = (TextView) mRootView.findViewById(R.id.txt_LikeNumber);

        // Update data later
    }

    /**
     * Include size, color,...
     */
    private void updateProductDetail(){

    }

    /**
     * Info for shipping of this product
     */
    private void updateShippingInfo(){

    }

    /**
     * Show the other image similar to this product
     */
    private void updateAlsoLike(){

    }
}
