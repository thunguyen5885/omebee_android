package com.omebee.android.layers.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseActivity;
import com.omebee.android.layers.ui.components.data.ProductDetailItemData;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.fragments.ImageCarouselFragment;
import com.omebee.android.layers.ui.presenters.ProductDetailPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.IProductDetailPresenter;
import com.omebee.android.layers.ui.views.IProductDetailView;
import com.omebee.android.utils.AppConstants;

/**
 * Created by ThuNguyen on 9/16/2014.
 */
public class ProductDetailActivity extends BaseActivity implements IProductDetailView{
    private IProductDetailPresenter mProductDetailPresenter;
    private ImageCarouselFragment mImageCarouselFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_product_detail);
        // Always hidden title
        getActionBar().setTitle("");

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        mImageCarouselFragment = (ImageCarouselFragment) getFragmentManager().findFragmentById(R.id.imageCarouselFragment);
        mProductDetailPresenter = new ProductDetailPresenterImpl(this);
        if(mImageCarouselFragment != null){
            mImageCarouselFragment.setPresenter(mProductDetailPresenter);
        }
        // Get product id from home page
        String productId = getIntent().getStringExtra(AppConstants.KEY_PRODUCT_ID);
        if(productId != null && productId.length() > 0){
            mImageCarouselFragment.setProductId(productId);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProductDetail(ProductDetailItemData productData) {
        if(productData != null) {
            // Set title as product name
            getActionBar().setTitle(productData.getProductName());
            // Show list of image
            mImageCarouselFragment.showProductImageList(productData.getImageUrls());
        }
    }
}
