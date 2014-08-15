package com.omebee.android.layers.ui.views;

import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.views.base.IBaseView;

import java.util.List;

/**
 * Created by Thu Nguyen on 8/6/2014.
 */
public interface ISearchView extends IBaseView {
    void showSearchProducts(List<ProductsLauncherGridItemData> productList);
}
