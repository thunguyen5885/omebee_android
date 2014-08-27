package com.omebee.android.layers.ui.components.adapters;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.omebee.android.R;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.components.views.pullrefresh.DynamicHeightImageView;
import com.omebee.android.layers.ui.components.views.pullrefresh.MultiColumnsAdapter;
import com.omebee.android.utils.ImageMemoryCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by IT on 8/27/2014.
 */
public class ProductsLauncherAdapter extends MultiColumnsAdapter {
    private Context mContext;
    private ImageLoader mImageLoader;
    private List<ProductsLauncherGridItemData> mProductsList = new ArrayList<ProductsLauncherGridItemData>();
    private LayoutInflater mLayoutInflater;
    private final Random mRandom;
    public ProductsLauncherAdapter(Context context){
        mContext = context;
        RequestQueue volleyQueue = Volley.newRequestQueue(context);
        DiskBasedCache cache = new DiskBasedCache(context.getCacheDir(), 16 * 1024 * 1024);
        volleyQueue = new RequestQueue(cache, new BasicNetwork(new HurlStack()));
        volleyQueue.start();
        mImageLoader = new ImageLoader(volleyQueue, ImageMemoryCache.INSTANCE);
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mRandom = new Random();
    }

    public List<ProductsLauncherGridItemData> getProductsList() {
        return mProductsList;
    }

    public void setProductsList(List<ProductsLauncherGridItemData> mProductsList) {
        this.mProductsList = mProductsList;
    }

    @Override
    public void notifyDataChanged() {
        super.notifyDataChanged();
    }

    @Override
    public int getCount() {
        return mProductsList.size();
    }

    @Override
    public void launchData() {
        if(mIViewTransmission != null) {
            // Begin to render data here
            for (int index = 0; index < getCount(); index++) {
                View view = getView(index);
                mIViewTransmission.addItemToLast(view, index);
            }
            mIViewTransmission.onFinished();
        }
    }

    @Override
    public void loadMore(Object data) {
        if(data instanceof List){
            List<ProductsLauncherGridItemData> productList = (List<ProductsLauncherGridItemData>) data;
            mProductsList.addAll(productList);
            if(mIViewTransmission != null) {
                for (int index = 0; index < productList.size(); index++) {
                    View view = getView(productList.get(index));
                    mIViewTransmission.addItemToLast(view, index);
                }
                mIViewTransmission.onFinished();
            }
        }
    }

    @Override
    public void pullRefresh(Object data) {
        if(data instanceof List){
            List<ProductsLauncherGridItemData> productList = (List<ProductsLauncherGridItemData>) data;
            mProductsList.addAll(0, productList);
            if(mIViewTransmission != null) {
                for (int index = 0; index < productList.size(); index++) {
                    View view = getView(productList.get(index));
                    mIViewTransmission.addItemToFirst(view, index);
                }
                mIViewTransmission.onFinished();
            }
        }
    }

    @Override
    public View getView(int position) {
        // Get product data at the position
        ProductsLauncherGridItemData productItemData = mProductsList.get(position);
        return getView(productItemData);
    }

    @Override
    public View getView(Object data) {
        if(data instanceof ProductsLauncherGridItemData){
            ProductsLauncherGridItemData productItemData = (ProductsLauncherGridItemData) data;
            View view = mLayoutInflater.inflate(R.layout.ctrl_multisize_products_launcher_item, null);
            DynamicHeightImageView networkImageView = (DynamicHeightImageView) view.findViewById(R.id.imageview_item);
            TextView titleView = (TextView) view.findViewById(R.id.textview_name);
            // Render data to control view
            if(productItemData != null) {
                double positionHeight = getRandomHeightRatio();
                networkImageView.setHeightRatio(positionHeight);
//                mImageLoader.get("", new ImageLoader.ImageListener() {
//                    @Override
//                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                    }
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                })
                networkImageView.setImageUrl(productItemData.getProductUrl(), mImageLoader);
                // Set the TextView's contents
                titleView.setText(productItemData.getProductName());
            }
            return view;
        }
        return null;
    }

    private double getRandomHeightRatio() {
        return mRandom.nextDouble() + 1.0; // height will be 1.0 - 1.5
    }
}
