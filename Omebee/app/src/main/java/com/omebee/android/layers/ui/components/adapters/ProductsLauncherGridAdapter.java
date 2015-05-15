package com.omebee.android.layers.ui.components.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.omebee.android.R;
import com.omebee.android.global.DataSingleton;
import com.omebee.android.layers.ui.ProductDetailActivity;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.components.views.material.MaterialRippleView;
import com.omebee.android.layers.ui.components.views.util.DynamicImageView;
import com.omebee.android.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThuNguyen on 8/9/2014.
 */
public class ProductsLauncherGridAdapter extends BaseAdapter implements View.OnClickListener{
    private static final String TAG = "ProductsLauncherGridAdapter";
    private Context mContext;
    private List<ProductsLauncherGridItemData> mProductsList = new ArrayList<ProductsLauncherGridItemData>();
    private final LayoutInflater mLayoutInflater;

    // For variable
    private boolean mIsClicked = false;
    public ProductsLauncherGridAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return mProductsList.size();
    }

    @Override
    public ProductsLauncherGridItemData getItem(int position) {
        return mProductsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductsLauncherGridItemData productItemData = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.ctrl_grid_products_launcher_item, parent, false);
            holder = new ViewHolder(convertView);
            holder.setWishlistIcon(productItemData.getWishListResourceId());

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setPosition(position);
        holder.mRippleView.setOnClickCallback(this);
        holder.wishlistIcon.setTag(position);
        holder.wishlistIcon.setOnClickListener(this);

        holder.productImage.setHeightRatio(productItemData.getImageHeightRatio());
        // Load the thumbnail image
        holder.productImage.setImageUrl(productItemData.getProductUrl(), DataSingleton.getInstance(mContext).getImageLoader());
        // Set the TextView's contents
        holder.productName.setText(productItemData.getProductName());
        holder.productPrice.setText(productItemData.productPriceWithCurrencyToString());

        return convertView;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(mIsClicked == true){
            return;
        }
        mIsClicked = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIsClicked = false;
            }
        }, 500);
        switch(v.getId()){
            case R.id.product_card_item_layout:
                ViewHolder viewHolder = (ViewHolder)v.getTag();
                if(viewHolder != null) {
                    ProductsLauncherGridItemData productData = getItem(viewHolder.position);

                    if (productData != null) {
                        Log.d("ThuNguyen", "click on item position: " + viewHolder.position);
                        // Go to product detail screen
                        Intent intent = new Intent(mContext, ProductDetailActivity.class);
                        intent.putExtra(AppConstants.KEY_PRODUCT_ID, productData.getProductId());
                        ((Activity)mContext).startActivityForResult(intent, AppConstants.REQUEST_CODE_HOME_TO_PRODUCT_DETAIL);
                    }
                }
                break;
            case R.id.imageview_wishlist:
                int position = (Integer) v.getTag();
                ProductsLauncherGridItemData productData = getItem(position);
                new AlertDialog.Builder(mContext)
                        .setTitle("Alert")
                        .setMessage("wishlist icon for product name: " + productData.getProductName())

                        .show();
                break;
        }
        // Clear animation for RippleView
        if(v instanceof RippleView){
        }
    }

    public static class ViewHolder {
        int position;
        MaterialRippleView mRippleView;
        DynamicImageView productImage;
        TextView productName;
        TextView productPrice;
        ImageView wishlistIcon;

        public ViewHolder(View v) {
            mRippleView = (MaterialRippleView) v.findViewById(R.id.product_card_item_layout);
            productImage = (DynamicImageView) v.findViewById(R.id.imageview_product);
            productName = (TextView) v.findViewById(R.id.txt_productName);
            productPrice = (TextView) v.findViewById(R.id.txt_productPrice);
            wishlistIcon = (ImageView) v.findViewById(R.id.imageview_wishlist);
            v.setTag(this);
        }
        public void setWishlistIcon(int resourceId){
            if(wishlistIcon!=null)
            {
                wishlistIcon.setImageResource(resourceId);
            }
        }
        public void setPosition(int position){
            this.position = position;
        }
    }
    public List<ProductsLauncherGridItemData> getProductsList() {
        return mProductsList;
    }

    /**
     * Set the product list to show
     * @param productsList
     */
    public void setProductsList(List<ProductsLauncherGridItemData> productsList) {
        if(mProductsList != null){
            this.mProductsList.clear();
        }
        if(productsList != null && productsList.size() > 0) {
            this.mProductsList.addAll(productsList);
        }
    }

    /**
     * Add the items to the current list on last
     * @param productsList
     */
    public void addItemsOnLast(List<ProductsLauncherGridItemData> productsList){
        if(productsList != null && productsList.size() > 0) {
            this.mProductsList.addAll(productsList);
        }
    }

    /**
     * Add the items to the current list on first
     * @param productsList
     */
    public void addItemsOnFirst(List<ProductsLauncherGridItemData> productsList){
        if(productsList != null && productsList.size() > 0) {
            this.mProductsList.addAll(0, productsList);
        }
    }


}
