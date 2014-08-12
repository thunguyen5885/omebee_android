package com.omebee.android.layers.ui.presenters.base;

import com.omebee.android.layers.ui.views.IProductsLauncherView;
import com.omebee.android.layers.ui.views.ISearchView;

/**
 * Created by Thu Nguyen on 8/6/2014.
 */
public interface ISearchPresenter extends IPresenter<ISearchView>{
    void search(String key);
}
