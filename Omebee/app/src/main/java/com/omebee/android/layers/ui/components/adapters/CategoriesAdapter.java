package com.omebee.android.layers.ui.components.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
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
    private static final int COLUMN_NUM = 4;

    private Context mContext;
    private LayoutInflater inflater;
    private List<CategoryItemData> mCategoriesList;
    private int mItemSize;
    private int mItemSpacing;
    private boolean mIsClicked = false;
    public CategoriesAdapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from(context);
        // Calculate the item size to inflate
        int screenWidth = AppFnUtils.getScreenWidth((Activity)mContext);
        mItemSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, mContext.getResources().getDisplayMetrics());
        mItemSize = screenWidth / COLUMN_NUM;
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
            viewHolder.mFlTopCategoryItemLayout = convertView.findViewById(R.id.flTopCategoryItemLayout);
            viewHolder.mTopCategoryItemLayout = convertView.findViewById(R.id.topCategoryItemLayout);
            viewHolder.ivCategoryPoster = (NetworkImageView) convertView.findViewById(R.id.ivCategoryPoster);
            viewHolder.tvCategoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);
            // Save tag
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Enforce width and height for image view
        int screenWidth = AppFnUtils.getScreenWidth((Activity) mContext);
        int totalPadding = (int)(mContext.getResources().getDimension(R.dimen.gridview_item_spacing) * (COLUMN_NUM + 1));
        int layoutWidthWithoutPadding = screenWidth - totalPadding;

        int viewWidth = layoutWidthWithoutPadding / COLUMN_NUM;

        //convertView.getLayoutParams().width = viewWidth;
        //convertView.getLayoutParams().height = 3*viewWidth/2;
        viewHolder.mTopCategoryItemLayout.getLayoutParams().width = mItemSize;

        int posterWidth = viewWidth - (int)(2 * mContext.getResources().getDimension(R.dimen.common_padding));
        viewHolder.ivCategoryPoster.getLayoutParams().width = posterWidth;
        viewHolder.ivCategoryPoster.getLayoutParams().height = posterWidth;
        // Set data
        CategoryItemData categoryData = mCategoriesList.get(position);
        if (categoryData != null) {
            viewHolder.ivCategoryPoster.setImageUrl(categoryData.getPosterUrl(), DataSingleton.getInstance(mContext).getImageLoader());
            viewHolder.tvCategoryName.setText(categoryData.getName());
        }

        viewHolder.mTopCategoryItemLayout.setTag(categoryData);
        viewHolder.mTopCategoryItemLayout.setOnClickListener(onCategoryItemClickListener);

        int rowSeq = position / COLUMN_NUM;
        int colSeq = position % COLUMN_NUM;
        int left = 0, top = 0, right = 0, bottom = 0;
        if(rowSeq == 0){
            top = mItemSpacing;
            bottom = mItemSpacing;
        }else{
            bottom = mItemSpacing;
        }
        if(colSeq == 0){
            left = mItemSpacing;
            right = mItemSpacing;
        }else{
            right = mItemSpacing;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(left, top, right, bottom);
        viewHolder.mFlTopCategoryItemLayout.setLayoutParams(layoutParams);
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
                CategoryItemData dataItem = (CategoryItemData)v.getTag();
                if(dataItem != null) {
                    // Go to Sub Category screen
                    Intent intent = new Intent(mContext, SubCategoriesActivity.class);
                    intent.putExtra(AppConstants.KEY_CATEGORY_ID, dataItem.getId());
                    mContext.startActivity(intent);
                }
            }
        }
    };
    private static class ViewHolder{
        private View mFlTopCategoryItemLayout;
        private View mTopCategoryItemLayout;
        private NetworkImageView ivCategoryPoster;
        private TextView tvCategoryName;
    }
}
