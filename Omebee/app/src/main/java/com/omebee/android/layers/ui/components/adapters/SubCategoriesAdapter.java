package com.omebee.android.layers.ui.components.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.omebee.android.R;
import com.omebee.android.global.DataSingleton;
import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.components.views.util.AnimatedExpandableListView;
import com.omebee.android.utils.AppFnUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Thu Nguyen on 9/17/2014.
 */
public class SubCategoriesAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater inflater;
    private List<CategoryItemData> mParentCategoryData;
    private HashMap<Integer, List<CategoryItemData>> mChildCategoryDataMap;

    public SubCategoriesAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }
    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if(convertView == null){
            holder = new ChildHolder();
            convertView = inflater.inflate(R.layout.ctr_child_category_item, parent, false);
            holder.tvSubCategory = (TextView) convertView.findViewById(R.id.tvChildCategory);
            convertView.setTag(holder);
        }else{
            holder = (ChildHolder) convertView.getTag();
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
        return mParentCategoryData != null ? mParentCategoryData.size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.ctr_group_category_item, parent, false);
            holder.tvParentCategory = (TextView) convertView.findViewById(R.id.tvGroupCategory);
            convertView.setTag(holder);
        }else{
            holder = (GroupHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    private class GroupHolder{
        private TextView tvParentCategory;
    }
    private class ChildHolder{
        private TextView tvSubCategory;
    }
}
