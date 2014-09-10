package com.omebee.android.layers.ui.components.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.etsy.android.grid.util.DynamicHeightTextView;
import com.omebee.android.R;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.components.views.foreground.ForegroundLinearLayout;
import com.omebee.android.layers.ui.components.views.util.DynamicImageView;
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

    private Drawable mDrawable;
    private final LayoutInflater mLayoutInflater;
    public ProductsLauncherGridAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader(Volley.newRequestQueue(context), ImageMemoryCache.INSTANCE);
        mDrawable = mContext.getResources().getDrawable(R.drawable.layout_item_selector);

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
            //Log.d("Phan", "create new View position:" + position);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.productImage.setHeightRatio(productItemData.getImageHeightRatio());
        // Load the thumbnail image
        holder.productImage.setImageUrl(productItemData.getProductUrl(), mImageLoader);
        // Set the TextView's contents
        holder.title.setText(productItemData.getProductName());

        return convertView;
    }
    private class ViewHolder {
        DynamicImageView productImage;
        TextView title;
        public ViewHolder(View v) {
            productImage = (DynamicImageView) v.findViewById(R.id.imageview_item);
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


}
