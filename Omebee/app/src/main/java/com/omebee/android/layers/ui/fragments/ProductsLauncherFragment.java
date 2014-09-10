package com.omebee.android.layers.ui.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseFragment;
import com.omebee.android.layers.ui.components.adapters.ProductsLauncherGridAdapter;
import com.omebee.android.layers.ui.components.views.pullrefresh.GridViewPullRefreshAndLoadMore;
import com.omebee.android.layers.ui.components.views.pullrefresh.ListViewPullAndLoadMore;
import com.omebee.android.layers.ui.components.views.pullrefresh.ListViewPullRefreshLoadMore;
import com.omebee.android.layers.ui.components.views.pullrefresh.ListViewPullToRefresh;
import com.omebee.android.layers.ui.components.views.pullrefresh.MultiItemRowListAdapter;
import com.omebee.android.layers.ui.components.views.util.CustomStaggeredGridView;
import com.omebee.android.layers.ui.presenters.ProductsLauncherPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.IPresenter;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;

import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherFragment extends BaseFragment{
    private ProductsLauncherPresenterImpl mPresenter;
    private GridViewPullRefreshAndLoadMore mProductsGrid;
    private ProductsLauncherGridAdapter mProductsGridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_launcher,
                container, false);
        mProductsGrid = (GridViewPullRefreshAndLoadMore)view.findViewById(R.id.productGrid);
        // Catch the refresh listener
        mProductsGrid.setOnRefreshListener(new GridViewPullRefreshAndLoadMore.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.pullRefresh();
            }
        });
        mProductsGrid.setOnLoadMoreListener(new GridViewPullRefreshAndLoadMore.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadMore();
            }
        });
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

    /**
     * Pull refresh the product complete (Add the newest items to the top of the list)
     * @param productList
     */
    public void pullRefreshComplete(final List<ProductsLauncherGridItemData> productList){

        mProductsGrid.OnRefreshComplete();
        if (productList != null && productList.size() > 0) {
            mProductsGridAdapter.addItemsOnFirst(productList);
            mProductsGridAdapter.notifyDataSetChanged();
            // Keep the last position that user stands before
            mProductsGrid.keepCurrentPositionAsAddTop(productList.size());
            // Prefer an animation to make effect
            createAnimationForPullRefresh();
        }
    }

    /**
     * Create fade-in animation
     */
    private void createAnimationForPullRefresh(){
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.view_fade_in);
        getView().startAnimation(animation);
    }
}
