package com.omebee.android.layers.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseFragment;
import com.omebee.android.layers.ui.components.adapters.ProductsLauncherGridAdapter;
import com.omebee.android.layers.ui.components.views.pullrefresh.PullAndLoadListView;
import com.omebee.android.layers.ui.components.views.pullrefresh.PullToRefreshListView;
import com.omebee.android.layers.ui.presenters.ProductsLauncherPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.IPresenter;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;

import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherFragment extends BaseFragment{
    private ProductsLauncherPresenterImpl mPresenter;
    private PullAndLoadListView mProductsGrid;
    private ProductsLauncherGridAdapter mProductsGridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_launcher,
                container, false);
        mProductsGrid = (PullAndLoadListView)view.findViewById(R.id.productGrid);
        // Catch the refresh listener
        mProductsGrid.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.pullRefresh();
            }
        });
        mProductsGrid.setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadMore();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        if(mPresenter!=null){
            mPresenter.showProductList();
        }
    }

    public IPresenter getPresenter() {
        return mPresenter;
    }

    public void setPresenter(IPresenter presenter){
        mPresenter = (ProductsLauncherPresenterImpl) presenter;
    };

    public void displayProductName(String name){

    }

    public void selectItem(int position){
        mPresenter.onItemClicked(position);
    }

    /**
     * Load the first list of product
     * @param productList
     */
    public void setProductList(List<ProductsLauncherGridItemData> productList){
        mProductsGrid.onRefreshComplete();
        // Check the adapter object, create new object in case of null otherwise notify it
        if(mProductsGridAdapter == null){
            mProductsGridAdapter = new ProductsLauncherGridAdapter(getActivity());
            mProductsGridAdapter.setProductsList(productList);
            mProductsGrid.setAdapter(mProductsGridAdapter);
        }else{ // Notify
            mProductsGridAdapter.setProductsList(productList);
            mProductsGridAdapter.notifyDataSetChanged();
        }
        if(productList == null || productList.size() == 0){
            mProductsGrid.setProgressBarVisibility(View.GONE);
        }else{
            mProductsGrid.setProgressBarVisibility(View.VISIBLE);
        }
    }

    /**
     * Load more products
     * @param productList
     */
    public void loadMore(List<ProductsLauncherGridItemData> productList){
        // Make sure that load more complete to reset something
        mProductsGrid.onLoadMoreComplete();
        if(productList == null || productList.size() == 0){
            mProductsGrid.setProgressBarVisibility(View.GONE);
        }else{
            mProductsGrid.setProgressBarVisibility(View.VISIBLE);
        }
        mProductsGridAdapter.loadMore(productList);
        mProductsGridAdapter.notifyDataSetChanged();
    }
}
