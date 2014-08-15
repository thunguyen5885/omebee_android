package com.omebee.android.layers.ui.presenters;

import com.omebee.android.layers.ui.models.ProductsLauncherModel;
import com.omebee.android.layers.ui.models.base.IProductsLauncherModel;
import com.omebee.android.layers.ui.presenters.base.IProductsLauncherPresenter;
import com.omebee.android.layers.ui.presenters.base.ISearchPresenter;
import com.omebee.android.layers.ui.views.IProductsLauncherView;
import com.omebee.android.layers.ui.views.ISearchView;

/**
 * Created by Thu Nguyen on 8/6/2014.
 */
public class SearchPresenterImpl implements ISearchPresenter {
   private ISearchView mView;
   private IProductsLauncherModel mModel;

    public SearchPresenterImpl(ISearchView mView) {
        this.mView = mView;
        this.mModel = new ProductsLauncherModel();
    }

    @Override
    public void init(ISearchView view) {

    }

    public ISearchView getView() {
        return mView;
    }

    public void setView(ISearchView view) {
        this.mView = view;
    }

    @Override
    public void search(String key) {
        mView.showSearchProducts(mModel.search(key));
    }
}
