package com.omebee.android.layers.ui.models.base;

import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;

import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public interface IProductsLauncherModel extends IBaseModel{
    String getProductName();
    List<ProductsLauncherGridItemData> loadProductList();
    List<ProductsLauncherGridItemData> searchProduct(String keyword);
    void loadMore();
    void pullRefresh();
    IPullRefreshCallback getIPullRefreshCallback();
    void setIPullRefreshCallback(IPullRefreshCallback iPullRefreshCallback);
    ILoadMoreCallback getILoadMoreRefreshCallback();
    void setILoadMoreRefreshCallback(ILoadMoreCallback mILoadMoreRefreshCallback);

    public interface IPullRefreshCallback{
        void pullRefreshSuccess(List<ProductsLauncherGridItemData> productsList);
        void pullRefreshFailed();
    }
    public interface  ILoadMoreCallback{
        void loadMoreSuccess(List<ProductsLauncherGridItemData> productsList, boolean isEndOfList);
        void loadMoreFailed();
    }
}


