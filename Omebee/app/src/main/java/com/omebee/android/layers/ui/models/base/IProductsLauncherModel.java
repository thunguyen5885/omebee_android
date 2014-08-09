package com.omebee.android.layers.ui.models.base;

import com.omebee.android.unknown.ProductGridItemData;

import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public interface IProductsLauncherModel extends IBaseModel{
    String getProductName();
    List<ProductGridItemData> loadProductList();
}
