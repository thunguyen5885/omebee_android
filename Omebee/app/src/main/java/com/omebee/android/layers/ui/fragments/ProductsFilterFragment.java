package com.omebee.android.layers.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseFragment;
import com.omebee.android.layers.ui.components.adapters.ProductsLauncherGridAdapter;
import com.omebee.android.layers.ui.components.data.FilterItemData;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.components.views.pullrefresh.GridViewLoadMore;
import com.omebee.android.layers.ui.components.views.pullrefresh.GridViewPullRefreshAndLoadMore;
import com.omebee.android.layers.ui.presenters.ProductsFilterPresenterImpl;
import com.omebee.android.layers.ui.presenters.ProductsLauncherPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.IPresenter;

import java.util.List;

/**
 * Created by ThuNguyen on 5/12/2015.
 */
public class ProductsFilterFragment extends BaseFragment{
    private static final String TAG = "ProductsFilterFragment";

    private View mRootView;
    private ProductsFilterPresenterImpl mPresenter;
    private GridViewLoadMore mProductsGrid;
    private ProductsLauncherGridAdapter mProductsGridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_products_filter,
                    container, false);
            mProductsGrid = (GridViewLoadMore) mRootView.findViewById(R.id.productGrid);

            mProductsGrid.setOnLoadMoreListener(new GridViewLoadMore.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mPresenter.loadMore();
                }
            });

        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

    }

    public IPresenter getPresenter() {
        return mPresenter;
    }

    public void setPresenter(IPresenter presenter){
        mPresenter = (ProductsFilterPresenterImpl) presenter;
    };

    public void filterProducts(FilterItemData filterItemData){
        if(mPresenter != null){
            mPresenter.filterProducts(filterItemData);
        }
    }
    /**
     * Load the first list of product
     * @param productList
     */
    public void setProductList(List<ProductsLauncherGridItemData> productList){
        // Check the adapter object, create new object in case of null otherwise notify it
        if(mProductsGridAdapter == null){
            mProductsGridAdapter = new ProductsLauncherGridAdapter(getActivity());
            mProductsGridAdapter.setProductsList(productList);
            mProductsGrid.setAdapter(mProductsGridAdapter);
        }else{ // Notify
            mProductsGridAdapter.setProductsList(productList);
            mProductsGridAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Load more products complete
     * @param productList
     */
    public void loadMoreComplete(List<ProductsLauncherGridItemData> productList, boolean isEndOfList){
        // Make sure that load more complete to reset something
        mProductsGrid.onLoadMoreComplete();
        if (productList != null && productList.size() > 0) {
            mProductsGridAdapter.addItemsOnLast(productList);
            mProductsGridAdapter.notifyDataSetChanged();
        }
        // Process when the listview load data completely
        if(isEndOfList){
            mProductsGrid.onReachToEndOfList();
        }
    }
}
