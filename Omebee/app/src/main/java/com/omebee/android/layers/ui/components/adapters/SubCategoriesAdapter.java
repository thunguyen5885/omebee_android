package com.omebee.android.layers.ui.components.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omebee.android.R;
import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.components.views.util.AnimatedExpandableListView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Thu Nguyen on 9/17/2014.
 */
public class SubCategoriesAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private HashMap<Integer, CategoryItemData> mParentCategoryDataMap;
    private HashMap<Integer, List<CategoryItemData>> mChildCategoryDataMap;

    public SubCategoriesAdapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from(context);
    }
    public void setCategoryItemDataList(HashMap<Integer, CategoryItemData> categoryDataMap){
        if(mParentCategoryDataMap != null && mParentCategoryDataMap.size() > 0){
            mParentCategoryDataMap.clear();
        }else{
            mParentCategoryDataMap = new HashMap<Integer, CategoryItemData>();
        }
        mParentCategoryDataMap.putAll(categoryDataMap);
    }
    public void setChildCategoryDataMap(HashMap<Integer, List<CategoryItemData>> childCategoryDataMap){
        if(mChildCategoryDataMap != null && mChildCategoryDataMap.size() > 0){
            mChildCategoryDataMap.clear();
        }else{
            mChildCategoryDataMap = new HashMap<Integer, List<CategoryItemData>>();
        }
        mChildCategoryDataMap.putAll(childCategoryDataMap);
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if(convertView == null){
            holder = new ChildHolder();
            convertView = inflater.inflate(R.layout.ctr_child_category_item, parent, false);
            holder.vChildLayout = convertView.findViewById(R.id.llChildLayout);
            holder.tvSubCategory = (TextView) convertView.findViewById(R.id.tvChildCategory);
            holder.vChildPadding = convertView.findViewById(R.id.vChildPadding);
            convertView.setTag(holder);
        }else{
            holder = (ChildHolder) convertView.getTag();
        }
        Object childObj = getChild(groupPosition, childPosition);
        if(childObj != null && childObj instanceof CategoryItemData){
            CategoryItemData childItemData = (CategoryItemData) childObj;
            holder.tvSubCategory.setText(childItemData.getName());
        }

        if(childPosition == getRealChildrenCount(groupPosition) - 1){
            holder.vChildLayout.setBackgroundResource(R.drawable.layout_item_last_child_of_group);
            holder.vChildPadding.setVisibility(View.VISIBLE);
        }else{
            holder.vChildLayout.setBackgroundResource(R.drawable.layout_item_not_last_child_of_group);
            holder.vChildPadding.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        if(mChildCategoryDataMap != null){
            List<CategoryItemData> childItemList = mChildCategoryDataMap.get(groupPosition);
            return (childItemList != null) ? childItemList.size() : 0;
        }
        return 0;
    }

    @Override
    public int getGroupCount() {
        return mParentCategoryDataMap != null ? mParentCategoryDataMap.size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if(mParentCategoryDataMap != null && groupPosition < mParentCategoryDataMap.size()){
            return mParentCategoryDataMap.get(groupPosition);
        }
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if(mChildCategoryDataMap != null){
            List<CategoryItemData> childItemDataList = mChildCategoryDataMap.get(groupPosition);
            if(childItemDataList != null && childPosition < childItemDataList.size()){
                return childItemDataList.get(childPosition);
            }
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if(convertView == null){
            holder = new GroupHolder();
            convertView = inflater.inflate(R.layout.ctr_group_category_item, parent, false);
            holder.vGroupLayout = convertView.findViewById(R.id.rlGroupLayout);
            holder.tvParentCategory = (TextView) convertView.findViewById(R.id.tvGroupCategory);
            holder.ivIndicator = (ImageView) convertView.findViewById(R.id.ivGroupIndicator);
            holder.vGroupPadding = convertView.findViewById(R.id.vGroupPadding);
            convertView.setTag(holder);
        }else{
            holder = (GroupHolder) convertView.getTag();
        }
        // Set name for group
        Object groupObj = getGroup(groupPosition);
        if(groupObj != null && groupObj instanceof CategoryItemData){
            CategoryItemData groupCategoryItemData = (CategoryItemData) groupObj;
            holder.tvParentCategory.setText(groupCategoryItemData.getName());
        }
        if(isExpanded){
            holder.vGroupLayout.setBackgroundResource(R.drawable.layout_item_group_expand_background);
            holder.vGroupPadding.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.ivIndicator.setBackgroundResource(R.drawable.ic_arrow_up);
        }else{
            holder.vGroupLayout.setBackgroundResource(R.drawable.layout_item_group_collapse_background);
            holder.vGroupPadding.setBackgroundColor(mContext.getResources().getColor(R.color.staggered_grid_bg));
            holder.ivIndicator.setBackgroundResource(R.drawable.ic_arrow_down);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private static class GroupHolder{
        private TextView tvParentCategory;
        private ImageView ivIndicator;
        private View vGroupPadding;
        private View vGroupLayout;
    }
    private static class ChildHolder{
        private View vChildLayout;
        private TextView tvSubCategory;
        private View vChildPadding;

    }
}
