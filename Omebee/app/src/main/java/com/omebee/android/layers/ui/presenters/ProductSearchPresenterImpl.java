package com.omebee.android.layers.ui.presenters;

import com.omebee.android.layers.ui.models.ProductsLauncherModel;
import com.omebee.android.layers.ui.models.base.IProductsLauncherModel;
import com.omebee.android.layers.ui.presenters.base.IProductSearchPresenter;
import com.omebee.android.layers.ui.views.IProductSearchView;

/**
 * Created by Thu Nguyen on 8/6/2014.
 */
public class ProductSearchPresenterImpl implements IProductSearchPresenter {
   private IProductSearchView mView;
   private IProductsLauncherModel mModel;

    public ProductSearchPresenterImpl(IProductSearchView mView) {
        this.mView = mView;
        this.mModel = new ProductsLauncherModel();
    }

    @Override
    public void init(IProductSearchView view) {

    }

    public IProductSearchView getView() {
        return mView;
    }

    public void setView(IProductSearchView view) {
        this.mView = view;
    }

    @Override
    public void searchProduct(String keyword) {
        mView.showSearchProducts(mModel.searchProduct(keyword));
    }
}
