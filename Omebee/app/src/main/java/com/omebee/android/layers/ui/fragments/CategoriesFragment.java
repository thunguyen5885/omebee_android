package com.omebee.android.layers.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseFragment;
import com.omebee.android.layers.ui.components.views.util.LoadAndRefreshLayout;
import com.omebee.android.layers.ui.components.adapters.CategoriesAdapter;
import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.components.views.abslistview.ExpandableHeightGridView;
import com.omebee.android.layers.ui.presenters.base.ICategoriesPresenter;
import com.omebee.android.layers.ui.presenters.base.IPresenter;

import java.util.List;

/**
 * Created by ThuNguyen on 10/25/2014.
 */
public class CategoriesFragment extends BaseFragment implements LoadAndRefreshLayout.ILoadAndRefreshCallback{
    private ExpandableHeightGridView mCategoriesGridView;
    private LoadAndRefreshLayout mLoadAndRefreshLayout;
    private ICategoriesPresenter mCategoriesPresenter;
    @Override
    public void setPresenter(IPresenter presenter) {
        mCategoriesPresenter = (ICategoriesPresenter) presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        mCategoriesGridView = (ExpandableHeightGridView) view.findViewById(R.id.gvCategories);
        mLoadAndRefreshLayout = (LoadAndRefreshLayout) view.findViewById(R.id.loadAndRefreshLayout);
        mLoadAndRefreshLayout.setILoadAndRefreshCallback(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Load data right now
        onLoadInitialData();
    }
    private void beginLoadingData(){
        if(mLoadAndRefreshLayout != null){
            mLoadAndRefreshLayout.beginLoading();
        }
        if(mCategoriesGridView != null){
            mCategoriesGridView.setVisibility(View.GONE);
        }
    }
    private void completeLoadingData(){
        if(mLoadAndRefreshLayout != null){
            mLoadAndRefreshLayout.onComplete();
        }
        if(mCategoriesGridView != null){
            mCategoriesGridView.setVisibility(View.VISIBLE);
        }
    }
    private void enforceReloadData(){
        if(mLoadAndRefreshLayout != null){
            mLoadAndRefreshLayout.enforceShowRefreshButton();
        }
    }
    public void showCategories(List<CategoryItemData> categoriesList){
        if(categoriesList == null || categoriesList.size() == 0){
            // Show refresh button to reload data
            enforceReloadData();
        }else {
            // Loading data done
            completeLoadingData();
            CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getActivity());
            categoriesAdapter.setCategoriesList(categoriesList);
            mCategoriesGridView.setAdapter(categoriesAdapter);
        }
    }

    @Override
    public void onLoadInitialData() {
        if(mCategoriesPresenter != null){
            // Show dialog to user
            beginLoadingData();
            mCategoriesPresenter.getTopCategories();
        }
    }
}
