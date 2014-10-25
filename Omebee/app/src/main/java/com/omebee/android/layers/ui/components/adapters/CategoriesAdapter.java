package com.omebee.android.layers.ui.components.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.omebee.android.R;
import com.omebee.android.global.DataSingleton;
import com.omebee.android.layers.ui.components.OutlineContainer;
import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.components.viewpager.JazzyViewPager;
import com.omebee.android.utils.AppFnUtils;
import com.omebee.android.utils.ImageMemoryCache;

import java.util.List;

/**
 * Created by Thu Nguyen on 9/17/2014.
 */
public class CategoriesAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<CategoryItemData> mCategoriesList;

    public CategoriesAdapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return (mCategoriesList != null) ? mCategoriesList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.ctrl_top_category_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivCategoryPoster = (NetworkImageView) convertView.findViewById(R.id.ivCategoryPoster);
            viewHolder.tvCategoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);
            // Save tag
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set data
        CategoryItemData categoryData = mCategoriesList.get(position);
        viewHolder.ivCategoryPoster.setImageUrl(categoryData.getPosterUrl(), DataSingleton.getInstance(mContext).getImageLoader());
        viewHolder.tvCategoryName.setText(categoryData.getName());
        return convertView;
    }

    public List<CategoryItemData> getCategoriesList() {
        return mCategoriesList;
    }

    public void setCategoriesList(List<CategoryItemData> categoriesList) {
        if(mCategoriesList != null && mCategoriesList.size() > 0){
            mCategoriesList.clear();
        }
        mCategoriesList = categoriesList;
    }

    private class ViewHolder{
        private NetworkImageView ivCategoryPoster;
        private TextView tvCategoryName;
    }
}
