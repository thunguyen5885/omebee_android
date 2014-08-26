package com.omebee.android.layers.ui.components.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.omebee.android.R;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.components.views.pullrefresh.DynamicHeightImageView;
import com.omebee.android.utils.ImageMemoryCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ThuNguyen on 8/9/2014.
 */
public class ProductsLauncherGridAdapter extends BaseAdapter{
    private Context mContext;
    private ImageLoader mImageLoader;
    private List<ProductsLauncherGridItemData> mProductsList = new ArrayList<ProductsLauncherGridItemData>();
    private final Random mRandom;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
    public ProductsLauncherGridAdapter(Context context){
        mContext = context;
        mImageLoader = new ImageLoader(Volley.newRequestQueue(context), ImageMemoryCache.INSTANCE);
        this.mRandom = new Random();
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductsLauncherGridItemData productItemData = getItem(position);

        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.ctrl_grid_products_launcher_item, null);
        }
        ViewHolder holder = (ViewHolder) v.getTag();
        if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(holder);
        }
        double positionHeight = getPositionRatio(position);
        holder.productImage.setHeightRatio(positionHeight);
        // Load the thumbnail image
        holder.productImage.setImageUrl(productItemData.getProductUrl(), mImageLoader);
        // Set the TextView's contents
        holder.title.setText(productItemData.getProductName());

        return v;
    }
    private class ViewHolder {
        DynamicHeightImageView productImage;
        TextView title;
        public ViewHolder(View v) {
            productImage = (DynamicHeightImageView) v.findViewById(R.id.imageview_item);
            title = (TextView) v.findViewById(R.id.textview_name);
            v.setTag(this);
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
    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d("Thu Nguyen", "getPositionRatio:" + position + " ratio:" + ratio);
            }
            return ratio;
        }
    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
        // the width
    }
}
