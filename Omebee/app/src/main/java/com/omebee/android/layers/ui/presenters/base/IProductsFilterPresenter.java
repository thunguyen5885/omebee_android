package com.omebee.android.layers.ui.presenters.base;

import com.omebee.android.layers.ui.components.data.FilterItemData;
import com.omebee.android.layers.ui.views.IProductsFilterView;

/**
 * Created by Thu Nguyen on 8/6/2014.
 */
public interface IProductsFilterPresenter extends IPresenter<IProductsFilterView>{
    // Filter function
    void filterProducts(FilterItemData filterItemData);
    void loadMore();
}
