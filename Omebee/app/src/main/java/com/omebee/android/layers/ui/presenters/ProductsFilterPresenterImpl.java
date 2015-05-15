package com.omebee.android.layers.ui.presenters;

import com.omebee.android.layers.ui.components.data.FilterItemData;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.models.ProductsLauncherModel;
import com.omebee.android.layers.ui.models.base.IProductsLauncherModel;
import com.omebee.android.layers.ui.presenters.base.IProductsFilterPresenter;
import com.omebee.android.layers.ui.presenters.base.IProductsLauncherPresenter;
import com.omebee.android.layers.ui.views.IProductsFilterView;
import com.omebee.android.layers.ui.views.IProductsLauncherView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsFilterPresenterImpl implements IProductsFilterPresenter, IProductsLauncherModel.ILoadMoreCallback{
    private IProductsFilterView mView;
    private IProductsLauncherModel mModel;
    ReentrantLock loadMoreLock = new ReentrantLock();
    public ProductsFilterPresenterImpl(IProductsFilterView mView) {
        this.mView = mView;
        this.mModel = new ProductsLauncherModel();
        this.mModel.setILoadMoreRefreshCallback(this);
    }
    @Override
    public void init(IProductsFilterView view) {

    }


    @Override
    public void filterProducts(FilterItemData filterItemData) {
        mView.showFilterProducts(mModel.filterProduct(filterItemData));
    }

    @Override
    public void loadMore() {
        if(loadMoreLock.isLocked())//it's working on load more so don't need to do another loadmore at this time
            return;
        loadMoreLock.lock();
        mModel.loadMore();
    }


    @Override
    public void loadMoreSuccess(List<ProductsLauncherGridItemData> productsList, boolean isEndOfList) {
        mView.loadMoreProductsComplete(productsList, isEndOfList);
        if(loadMoreLock.isLocked())
            loadMoreLock.unlock();
    }

    @Override
    public void loadMoreFailed() {
        mView.loadMoreProductsComplete(new ArrayList<ProductsLauncherGridItemData>(), false);
        if(loadMoreLock.isLocked())
            loadMoreLock.unlock();
    }

    public IProductsFilterView getView() {
        return mView;
    }

    public void setView(IProductsFilterView view) {
        this.mView = view;
    }

}
