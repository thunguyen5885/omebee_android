package com.omebee.android.layers.ui.views;

import com.omebee.android.layers.ui.components.data.ProductDetailItemData;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.views.base.IBaseView;

/**
 * Created by IT on 9/16/2014.
 */
public interface IProductDetailView extends IBaseView{
    void showProductDetail(ProductDetailItemData productData);
}
