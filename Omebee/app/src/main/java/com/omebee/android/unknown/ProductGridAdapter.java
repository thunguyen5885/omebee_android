package com.omebee.android.unknown;

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
import com.omebee.android.layers.services.models.ProductWSModel;
import com.omebee.android.utils.ImageMemoryCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThuNguyen on 8/9/2014.
 */
public class ProductGridAdapter extends BaseAdapter{
    private Context mContext;
    private ImageLoader mImageLoader;
    private List<ProductGridItemData> mProductsList = new ArrayList<ProductGridItemData>();

    public ProductGridAdapter(Context context){
        mContext = context;
        mImageLoader = new ImageLoader(Volley.newRequestQueue(context), ImageMemoryCache.INSTANCE);
    }
    @Override
    public int getCount() {
        return mProductsList.size();
    }

    @Override
    public ProductGridItemData getItem(int position) {
        return mProductsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = ((Activity)mContext).getLayoutInflater().inflate(R.layout.product_grid_item, null);

        ProductGridItemData productItemData = getItem(position);

        // Load the thumbnail image
        NetworkImageView image = (NetworkImageView) view.findViewById(R.id.imageview_item);
        image.setImageUrl(productItemData.getmProductUrl(), mImageLoader);

        // Set the TextView's contents
        TextView name = (TextView) view.findViewById(R.id.textview_name);
        name.setText(productItemData.getmProductName());

        return view;
    }
    public List<ProductGridItemData> getProductsList() {
        return mProductsList;
    }

    public void setProductsList(List<ProductGridItemData> productsList) {
        if(mProductsList != null){
            this.mProductsList.clear();
        }
        this.mProductsList.addAll(productsList);
    }
}
