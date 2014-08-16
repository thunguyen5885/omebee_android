package com.omebee.android.layers.ui.presenters.base;

import com.omebee.android.layers.ui.views.IProductSearchView;

/**
 * Created by Thu Nguyen on 8/6/2014.
 */
public interface IProductSearchPresenter extends IPresenter<IProductSearchView>{
    // Search function
    void searchProduct(String keyword);
}
