package com.omebee.android.layers.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseFragment;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.components.views.pullrefresh.ListViewPullAndLoadMore;
import com.omebee.android.layers.ui.components.views.pullrefresh.ListViewPullToRefresh;
import com.omebee.android.layers.ui.components.views.pullrefresh.ProductsLauncherAdapter;
import com.omebee.android.layers.ui.components.views.pullrefresh.ScrollViewMultiColumns;
import com.omebee.android.layers.ui.presenters.ProductsLauncherPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.IPresenter;

import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public class MultisizeProductsLauncherFragment extends BaseFragment{
    private ProductsLauncherPresenterImpl mPresenter;
    private ScrollViewMultiColumns mProductsGrid;
    private ProductsLauncherAdapter mProductsGridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multisize_products_launcher,
                container, false);
        mProductsGrid = (ScrollViewMultiColumns)view.findViewById(R.id.productGrid);
        mProductsGrid.setColumnNum(2);
//        // Catch the refresh listener
//        mProductsGrid.setOnRefreshListener(new ListViewPullToRefresh.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mPresenter.pullRefresh();
//            }
//        });
//        mProductsGrid.setOnLoadMoreListener(new ListViewPullAndLoadMore.OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                mPresenter.loadMore();
//            }
//        });
//        mProductsGrid.setIUpdateDataBackListener(new ListViewPullToRefresh.IUpdateDataBackListener() {
//            @Override
//            public void updateDataBack(Object dataCallback) {
//                if (dataCallback instanceof List) {
//                    pullRefresh((List<ProductsLauncherGridItemData>) dataCallback);
//                }
//            }
//        });
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
        // Check the adapter object, create new object in case of null otherwise notify it
        if(mProductsGridAdapter == null){
            mProductsGridAdapter = new ProductsLauncherAdapter(getActivity());
            mProductsGridAdapter.setProductsList(productList);
            mProductsGrid.setAdapter(mProductsGridAdapter);
        }else{ // Notify
            mProductsGridAdapter.setProductsList(productList);
            mProductsGridAdapter.notifyDataChanged();
        }
//        if(productList == null || productList.size() == 0){
//            mProductsGrid.setProgressBarVisibility(View.GONE);
//        }else{
//            mProductsGrid.setProgressBarVisibility(View.VISIBLE);
//        }
    }

    /**
     * Load more products
     * @param productList
     */
    public void loadMore(List<ProductsLauncherGridItemData> productList){
        // Make sure that load more complete to reset something
        if(productList != null && productList.size() > 0){
            mProductsGridAdapter.loadMore(productList);
        }
    }

    /**
     * Pull refresh the product (Add the newest items to the top of the list)
     * @param productList
     */
    public void pullRefresh(final List<ProductsLauncherGridItemData> productList){
        if (productList != null && productList.size() > 0) {
            mProductsGridAdapter.pullRefresh(productList);
            // Keep the last position that user stands before
//            mProductsGrid.setSelection((productList.size()/mMultiItemRowListAdapter.getItemsPerRow()) + 1);
        }
    }
}
