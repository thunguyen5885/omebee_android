package com.omebee.android.layers.ui.presenters;

import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.models.ProductsLauncherModel;
import com.omebee.android.layers.ui.models.base.IProductsLauncherModel;
import com.omebee.android.layers.ui.presenters.base.IProductsLauncherPresenter;
import com.omebee.android.layers.ui.views.IProductsLauncherView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherPresenterImpl implements IProductsLauncherPresenter, ProductsLauncherModel.IPullRefreshCallback, ProductsLauncherModel.ILoadMoreCallback {
   private IProductsLauncherView mView;
   private IProductsLauncherModel mModel;

    public ProductsLauncherPresenterImpl(IProductsLauncherView mView) {
        this.mView = mView;
        this.mModel = new ProductsLauncherModel();
        ((ProductsLauncherModel) mModel).setIPullRefreshCallback(this);
        ((ProductsLauncherModel) mModel).setmILoadMoreRefreshCallback(this);
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
        mModel.loadMore();
    }

    @Override
    public void pullRefresh() {
        mModel.pullRefresh();
    }

    @Override
    public void loadMoreSuccess(List<ProductsLauncherGridItemData> productsList) {
        mView.loadMoreProducts(productsList);
    }

    @Override
    public void loadMoreFailed() {
        mView.loadMoreProducts(new ArrayList<ProductsLauncherGridItemData>());
    }

    @Override
    public void pullRefreshSuccess(List<ProductsLauncherGridItemData> productsList) {
        mView.showProducts(productsList);
    }

    @Override
    public void pullRefreshFailed() {
        mView.showProducts(new ArrayList<ProductsLauncherGridItemData>());
    }
}
