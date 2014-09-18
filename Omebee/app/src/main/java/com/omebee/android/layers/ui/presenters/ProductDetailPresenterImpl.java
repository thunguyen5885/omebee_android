package com.omebee.android.layers.ui.presenters;

import com.omebee.android.layers.ui.components.data.ProductDetailItemData;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.models.ProductsLauncherModel;
import com.omebee.android.layers.ui.models.base.IProductsLauncherModel;
import com.omebee.android.layers.ui.presenters.base.IProductDetailPresenter;
import com.omebee.android.layers.ui.views.IProductDetailView;

/**
 * Created by ThuNguyen on 9/16/2014.
 */
public class ProductDetailPresenterImpl implements IProductDetailPresenter, IProductsLauncherModel.IGetProductFromIdCallback {
    private IProductDetailView mView;
    private IProductsLauncherModel mModel;
    public  ProductDetailPresenterImpl(IProductDetailView view){
        mView = view;
        mModel = new ProductsLauncherModel();
        mModel.setIGetProductFromIdCallback(this);
    }

    @Override
    public void getProductFromId(String productId) {

        mModel.getProductFromId(productId);
    }

    @Override
    public void init(Object view) {

    }

    @Override
    public void getProductFromIdSuccess(ProductDetailItemData product) {
        if(mView != null){
            mView.showProductDetail(product);
        }
    }

    @Override
    public void getProductFromIdFailed() {

    }
}
