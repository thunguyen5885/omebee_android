package com.omebee.android.layer.ui.presenters;

import com.omebee.android.layer.ui.models.ProductsLauncherModel;
import com.omebee.android.layer.ui.models.base.IProductsLauncherModel;
import com.omebee.android.layer.ui.presenters.base.IProductsLauncherPresenter;
import com.omebee.android.layer.ui.views.IProductsLauncherView;

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
        mView.displayName(mModel.getFullName());
    }
}
