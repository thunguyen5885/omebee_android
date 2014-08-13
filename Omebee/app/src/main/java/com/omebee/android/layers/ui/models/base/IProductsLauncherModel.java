package com.omebee.android.layers.ui.models.base;

import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;

import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public interface IProductsLauncherModel extends IBaseModel{
    String getProductName();
    List<ProductsLauncherGridItemData> loadProductList();
    List<ProductsLauncherGridItemData> search(String key);
    void loadMore();
    void pullRefresh();
}
