package com.omebee.android.layers.ui.views;

import com.omebee.android.layers.ui.views.base.IBaseView;
import com.omebee.android.unknown.ProductGridItemData;

import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public interface IProductsLauncherView extends IBaseView {
    void displayProductName(String name);
    void showProducts(List<ProductGridItemData> productList);
}
