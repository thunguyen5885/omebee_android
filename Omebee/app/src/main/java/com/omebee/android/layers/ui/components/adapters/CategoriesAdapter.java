package com.omebee.android.layers.ui.components.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.omebee.android.R;
import com.omebee.android.global.DataSingleton;
import com.omebee.android.layers.ui.SubCategoriesActivity;
import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.components.views.foreground.ForegroundLinearLayout;
import com.omebee.android.utils.AppConstants;
import com.omebee.android.utils.AppFnUtils;

import java.util.List;

/**
 * Created by Thu Nguyen on 9/17/2014.
 */
public class CategoriesAdapter extends BaseAdapter {
    private Context mContext;
    private GridView mGridView;
    private LayoutInflater inflater;
    private List<CategoryItemData> mCategoriesList;
    private boolean mIsClicked = false;
    public CategoriesAdapter(Context context, GridView gridView){
        mContext = context;
        inflater = LayoutInflater.from(context);
        mGridView = gridView;
    }
    @Override
    public int getCount() {
        if(mCategoriesList != null){
            int numColumns = mGridView.getNumColumns();
            int itemsNum = mCategoriesList.size();
            int remainNum = itemsNum % numColumns;
            if(remainNum == 0){
                return itemsNum;
            }
            return (itemsNum + (numColumns - remainNum));
        }
        return 0;
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
        // Enforce width and height for image view
        int screenWidth = AppFnUtils.getScreenWidth((Activity) mContext);
        int totalPadding = (int)(mContext.getResources().getDimension(R.dimen.gridview_item_spacing) * (mGridView.getNumColumns() + 1));
        int layoutWidthWithoutPadding = screenWidth - totalPadding;

        int viewWidth = layoutWidthWithoutPadding / mGridView.getNumColumns();

        convertView.getLayoutParams().width = viewWidth;
        convertView.getLayoutParams().height = 3*viewWidth/2;

        int posterWidth = 3*viewWidth / 4;

        viewHolder.ivCategoryPoster.getLayoutParams().width = posterWidth;
        viewHolder.ivCategoryPoster.getLayoutParams().height = posterWidth;
        // Set data
        if(position < mCategoriesList.size()) {
            CategoryItemData categoryData = mCategoriesList.get(position);
            if (categoryData != null) {
                viewHolder.ivCategoryPoster.setImageUrl(categoryData.getPosterUrl(), DataSingleton.getInstance(mContext).getImageLoader());
                viewHolder.tvCategoryName.setText(categoryData.getName());
                viewHolder.data = categoryData;
            }

        }
        if(position >= mCategoriesList.size() && convertView instanceof ForegroundLinearLayout){
            ((ForegroundLinearLayout)convertView).setForeground(null);
        }
        convertView.setOnClickListener(onCategoryItemClickListener);
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
    private View.OnClickListener onCategoryItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!mIsClicked){
                mIsClicked = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsClicked = false;
                    }
                }, 1000);
                ViewHolder holder = (ViewHolder) v.getTag();
                CategoryItemData dataItem = holder.data;
                if(dataItem != null) {
                    // Go to Sub Category screen
                    Intent intent = new Intent(mContext, SubCategoriesActivity.class);
                    intent.putExtra(AppConstants.KEY_CATEGORY_ID, dataItem.getId());
                    mContext.startActivity(intent);
                }
            }
        }
    };
    private class ViewHolder{
        CategoryItemData data;
        private NetworkImageView ivCategoryPoster;
        private TextView tvCategoryName;
    }
}
