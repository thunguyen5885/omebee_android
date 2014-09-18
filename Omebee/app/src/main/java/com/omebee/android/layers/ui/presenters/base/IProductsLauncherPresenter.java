package com.omebee.android.layers.ui.presenters.base;

import com.omebee.android.layers.ui.views.IProductsLauncherView;

/**
 * Created by phan on 8/6/2014.
 */
public interface IProductsLauncherPresenter extends IPresenter<IProductsLauncherView>{
    void showName();
    void onItemClicked(int position);
    void showProductList();
    void loadMore();
    void pullRefresh();
    void getProductFromId(String id);
}
