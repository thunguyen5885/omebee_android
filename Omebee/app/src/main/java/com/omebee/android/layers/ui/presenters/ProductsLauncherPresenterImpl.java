package com.omebee.android.layers.ui.presenters;

import com.omebee.android.layers.ui.models.ProductsLauncherModel;
import com.omebee.android.layers.ui.models.base.IProductsLauncherModel;
import com.omebee.android.layers.ui.presenters.base.IProductsLauncherPresenter;
import com.omebee.android.layers.ui.views.IProductsLauncherView;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherPresenterImpl implements IProductsLauncherPresenter {
   private IProductsLauncherView mView;
   private IProductsLauncherModel mModel;

    public ProductsLauncherPresenterImpl(IProductsLauncherView mView) {
        this.mView = mView;
        this.mModel = new ProductsLauncherModel();
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
}
