package com.omebee.android.layers.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseFragment;
import com.omebee.android.layers.ui.components.views.util.LoadAndRefreshLayout;
import com.omebee.android.layers.ui.components.adapters.SubCategoriesAdapter;
import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.components.views.abslistview.AnimatedExpandableListView;
import com.omebee.android.layers.ui.presenters.base.IPresenter;
import com.omebee.android.layers.ui.presenters.base.ISubCategoriesPresenter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ThuNguyen on 11/10/2014.
 */
public class SubCategoriesFragment extends BaseFragment implements LoadAndRefreshLayout.ILoadAndRefreshCallback{
    // For view, control
    private AnimatedExpandableListView mSubCategoriesListView;
    private LoadAndRefreshLayout mLoadAndRefreshLayout;
    private TextView mTvNoResult;

    private SubCategoriesAdapter mSubCategoriesAdapter;
    private ISubCategoriesPresenter mSubCategoriesPresenter;
    // For data, variable
    private String mParentCategoryId;
    private HashMap<Integer, CategoryItemData> mCategoriesMap;
    private HashMap<Integer, List<CategoryItemData>> mSubCategoriesMap;
    private String mKeywordSearch;
    private boolean mIsLoadingData;
    public SubCategoriesFragment(){
        mCategoriesMap = new HashMap<Integer, CategoryItemData>();
        mSubCategoriesMap = new HashMap<Integer, List<CategoryItemData>>();
    }
    @Override
    public void setPresenter(IPresenter presenter) {
        mSubCategoriesPresenter = (ISubCategoriesPresenter) presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_categories, container, false);
        mSubCategoriesListView = (AnimatedExpandableListView) view.findViewById(R.id.elvSubCategories);
        mSubCategoriesListView.setOnGroupClickListener(onGroupClickListener);
        mSubCategoriesListView.setOnChildClickListener(onChildClickListener);
        mLoadAndRefreshLayout = (LoadAndRefreshLayout) view.findViewById(R.id.loadAndRefreshLayout);
        mLoadAndRefreshLayout.setILoadAndRefreshCallback(this);
        mTvNoResult = (TextView) view.findViewById(R.id.tvNoResult);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onLoadInitialData();
    }

    /**
     * Begin to load the data for first time
     * Load the level2 if choose the level1 at the previous screen
     * else load by searching from some keywords
     */
    public void onLoadInitialData() {
        if(mSubCategoriesPresenter != null){
            mLoadAndRefreshLayout.beginLoading();
            if (mParentCategoryId != null && mParentCategoryId.length() > 0) {
                mSubCategoriesPresenter.getSubCategories(mParentCategoryId);
            } else {
                mSubCategoriesPresenter.searchSubCategories(mKeywordSearch);
            }
        }
    }
    /**
     * Hide the loading dialog, also show the status if the result is empty
     * @param isEmptyResult
     */
    private void completeLoadingData(boolean isEmptyResult){
        if(mLoadAndRefreshLayout != null){
            mLoadAndRefreshLayout.onComplete();
        }
        if(mSubCategoriesListView != null){
            mSubCategoriesListView.setVisibility(View.VISIBLE);
        }
        if(isEmptyResult){
            mTvNoResult.setVisibility(View.VISIBLE);
        }else {
            mTvNoResult.setVisibility(View.GONE);
        }
    }

    public void setParentCategoryId(String categoryId){
        mParentCategoryId = categoryId;
    }
    public void setKeywordSearch(String keywordSearch){mKeywordSearch = keywordSearch;}

    /**
     * Show the subcategory
     * @param categoriesMap
     * @param subCategoriesMap
     */
    public void showSubCategories(HashMap<Integer, CategoryItemData> categoriesMap, HashMap<Integer, List<CategoryItemData>> subCategoriesMap){
        // Store data
        if(mCategoriesMap.size() > 0){
            mCategoriesMap.clear();
        }
        mCategoriesMap.putAll(categoriesMap);
        if(mSubCategoriesMap.size() > 0){
            mSubCategoriesMap.clear();
        }
        mSubCategoriesMap.putAll(subCategoriesMap);

        if (mSubCategoriesAdapter == null) {
            mSubCategoriesAdapter = new SubCategoriesAdapter(getActivity());
            mSubCategoriesAdapter.setCategoryItemDataList(mCategoriesMap);
            mSubCategoriesAdapter.setChildCategoryDataMap(mSubCategoriesMap);
            mSubCategoriesListView.setAdapter(mSubCategoriesAdapter);
        } else {
            mSubCategoriesAdapter.setCategoryItemDataList(mCategoriesMap);
            mSubCategoriesAdapter.setChildCategoryDataMap(mSubCategoriesMap);
            mSubCategoriesAdapter.notifyDataSetChanged();
        }

        // Process when loading done
        completeLoadingData(mCategoriesMap.isEmpty());
    }

    /**
     * Begin to search products from some keyword
     * @param keyword
     */
    public void processSearch(String keyword){
        mKeywordSearch = keyword;
        mSubCategoriesPresenter.searchSubCategories(keyword, mParentCategoryId);
    }

    /**
     * Find the filter the category and sub category so that their name match with keyword
     * Implemented on sub category activity
     * @param keyword
     * @param categoriesMap
     * @param subCategoriesMap
     */
//    public void searchSubCategories(String keyword, HashMap<Integer, CategoryItemData> categoriesMap, HashMap<Integer, List<CategoryItemData>> subCategoriesMap){
//
//        if(keyword == null || keyword.trim().length() == 0) {
//            // Add all data firstly
//            categoriesMap.putAll(mCategoriesMap);
//            subCategoriesMap.putAll(mSubCategoriesMap);
//        }else {
//            String trimmedKeyword = keyword.trim();
//            int loopCount = mCategoriesMap.size();
//            // Find the child's name matching with the keyword
//            // Should browse all branches beginning from its parents
//            for(int groupIndex = 0; groupIndex < loopCount; groupIndex++) {
//                CategoryItemData groupCategoryData = mCategoriesMap.get(groupIndex);
//                List<CategoryItemData> subCategoriesList = mSubCategoriesMap.get(groupIndex);
//                List<CategoryItemData> newSubCategoriesList = new ArrayList<CategoryItemData>();
//                if(subCategoriesList != null && subCategoriesList.size() > 0){
//                    for(int childIndex = 0; childIndex < subCategoriesList.size(); childIndex++){
//                        CategoryItemData categoryData = subCategoriesList.get(childIndex);
//                        if(categoryData != null && categoryData.getName().toLowerCase().contains(trimmedKeyword.toLowerCase())){
//                            newSubCategoriesList.add(categoryData);
//                        }
//                    }
//                }
//                // Decide to add data to parent map
//                // Keep parent group if its children aren't empty
//                // Also keep parent group if parent name matching with keyword
//                if(newSubCategoriesList.size() > 0){
//                    categoriesMap.put(groupIndex, groupCategoryData);
//                    subCategoriesMap.put(groupIndex, newSubCategoriesList);
//                }else{
//                    // Check the group's name
//                    if(groupCategoryData != null && groupCategoryData.getName().toLowerCase().contains(trimmedKeyword.toLowerCase())){
//                        // Also add to group
//                        categoriesMap.put(groupIndex, groupCategoryData);
//                        subCategoriesMap.put(groupIndex, newSubCategoriesList);
//                    }else{
//                        // Nothing to add neither to group nor to child
//                    }
//                }
//            }
//        }
//    }
    public void searchTopCategories(String keyword, HashMap<Integer, CategoryItemData> categoriesMap, HashMap<Integer, List<CategoryItemData>> subCategoriesMap){

    }
    /**
     * Update group indicator bound to right side instead of left side as default
     */
    public void updateGroupIndicatorBound(){
        int leftSide = mSubCategoriesListView.getRight() - (int)getResources().getDimension(R.dimen.expandable_list_view_indicator_size);
        int rightSide = mSubCategoriesListView.getWidth();
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mSubCategoriesListView.setIndicatorBounds(leftSide, rightSide);
        }else{
            mSubCategoriesListView.setIndicatorBoundsRelative(leftSide, rightSide);
        }
    }


    private ExpandableListView.OnGroupClickListener onGroupClickListener = new ExpandableListView.OnGroupClickListener() {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
            // We call collapseGroupWithAnimation(int) and
            // expandGroupWithAnimation(int) to animate group
            // expansion/collapse.
            if (mSubCategoriesListView.isGroupExpanded(groupPosition)) {
                // Then collapse with animation
                mSubCategoriesListView.collapseGroupWithAnimation(groupPosition);
            } else {
                mSubCategoriesListView.expandGroupWithAnimation(groupPosition);
            }
            return true;
        }
    };

    private ExpandableListView.OnChildClickListener onChildClickListener = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            return true;
        }
    };

}
