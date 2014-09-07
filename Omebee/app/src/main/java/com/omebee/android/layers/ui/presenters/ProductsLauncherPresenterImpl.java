package com.omebee.android.layers.ui.presenters;

import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.models.ProductsLauncherModel;
import com.omebee.android.layers.ui.models.base.IProductsLauncherModel;
import com.omebee.android.layers.ui.presenters.base.IProductsLauncherPresenter;
import com.omebee.android.layers.ui.views.IProductsLauncherView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherPresenterImpl implements IProductsLauncherPresenter, IProductsLauncherModel.IPullRefreshCallback, IProductsLauncherModel.ILoadMoreCallback {
    private IProductsLauncherView mView;
    private IProductsLauncherModel mModel;
    ReentrantLock pullRefreshLock = new ReentrantLock();
    ReentrantLock loadMoreLock = new ReentrantLock();
    public ProductsLauncherPresenterImpl(IProductsLauncherView mView) {
        this.mView = mView;
        this.mModel = new ProductsLauncherModel();
        this.mModel.setIPullRefreshCallback(this);
        this.mModel.setILoadMoreRefreshCallback(this);
    }

    @Override
    public void init(IProductsLauncherView view) {

    }

    public IProductsLauncherView getView() {
        return mView;
    }

    public void setView(IProductsLauncherView view) {
        this.mView = view;
    }

    @Override
    public void showName() {
        mView.displayProductName(mModel.getProductName());
    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void showProductList() {
        mView.showProducts(mModel.loadProductList());
    }

    @Override
    public void loadMore() {
        if(loadMoreLock.isLocked())//it's working on load more so don't need to do another loadmore at this time
            return;
        loadMoreLock.lock();
        mModel.loadMore();
    }

    @Override
    public void pullRefresh() {
        if(pullRefreshLock.isLocked())//it's working on pull refresh so don't need to do another pullrefresh at this time
            return;
        pullRefreshLock.lock();
        mModel.pullRefresh();
    }

    @Override
    public void loadMoreSuccess(List<ProductsLauncherGridItemData> productsList) {
        mView.loadMoreProductsComplete(productsList);
        if(loadMoreLock.isLocked())
            loadMoreLock.unlock();
    }

    @Override
    public void loadMoreFailed() {
        mView.loadMoreProductsComplete(new ArrayList<ProductsLauncherGridItemData>());
        if(loadMoreLock.isLocked())
            loadMoreLock.unlock();
    }

    @Override
    public void pullRefreshSuccess(List<ProductsLauncherGridItemData> productsList) {
        mView.pullRefreshProductsComplete(productsList);
        if(pullRefreshLock.isLocked())
            pullRefreshLock.unlock();
    }

    @Override
    public void pullRefreshFailed() {
        mView.pullRefreshProductsComplete(new ArrayList<ProductsLauncherGridItemData>());
        if(pullRefreshLock.isLocked())
            pullRefreshLock.unlock();
    }
}
