package com.omebee.android.layers.ui.components.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.omebee.android.R;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.utils.ImageMemoryCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThuNguyen on 8/9/2014.
 */
public class ProductsLauncherGridAdapter extends BaseAdapter{
    private Context mContext;
    private ImageLoader mImageLoader;
    private List<ProductsLauncherGridItemData> mProductsList = new ArrayList<ProductsLauncherGridItemData>();

    public ProductsLauncherGridAdapter(Context context){
        mContext = context;
        mImageLoader = new ImageLoader(Volley.newRequestQueue(context), ImageMemoryCache.INSTANCE);
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
    public View getView(int position, View view, ViewGroup parent) {

        view = ((Activity)mContext).getLayoutInflater().inflate(R.layout.grid_products_launcher_item, null);

        ProductsLauncherGridItemData productItemData = getItem(position);

        // Load the thumbnail image
        NetworkImageView image = (NetworkImageView) view.findViewById(R.id.imageview_item);
        image.setImageUrl(productItemData.getProductUrl(), mImageLoader);

        // Set the TextView's contents
        TextView name = (TextView) view.findViewById(R.id.textview_name);
        name.setText(productItemData.getProductName());

        return view;
    }
    public List<ProductsLauncherGridItemData> getProductsList() {
        return mProductsList;
    }

    public void setProductsList(List<ProductsLauncherGridItemData> productsList) {
        if(mProductsList != null){
            this.mProductsList.clear();
        }
        this.mProductsList.addAll(productsList);
    }
}
