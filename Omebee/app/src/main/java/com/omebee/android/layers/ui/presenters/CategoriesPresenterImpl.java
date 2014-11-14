package com.omebee.android.layers.ui.presenters;

import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.components.data.ProductDetailItemData;
import com.omebee.android.layers.ui.models.CategoriesModel;
import com.omebee.android.layers.ui.models.ProductsLauncherModel;
import com.omebee.android.layers.ui.models.base.ICategoriesModel;
import com.omebee.android.layers.ui.models.base.IProductsLauncherModel;
import com.omebee.android.layers.ui.presenters.base.ICategoriesPresenter;
import com.omebee.android.layers.ui.presenters.base.IProductDetailPresenter;
import com.omebee.android.layers.ui.views.ICategoriesView;
import com.omebee.android.layers.ui.views.IProductDetailView;

import java.util.List;

/**
 * Created by ThuNguyen on 9/16/2014.
 */
public class CategoriesPresenterImpl implements ICategoriesPresenter, ICategoriesModel.ILoadCategoriesCallback{
    private ICategoriesView mView;
    private ICategoriesModel mModel;
    public CategoriesPresenterImpl(ICategoriesView view){
        mView = view;
        mModel = new CategoriesModel();
        mModel.setILoadCategoriesCallback(this);
    }

    @Override
    public void init(Object view) {

    }

    @Override
    public void getTopCategories() {
        mModel.loadCategories();
    }

    @Override
    public void loadCategoriesSuccess(List<CategoryItemData> categoriesList) {
        mView.showCategories(categoriesList);
    }

    @Override
    public void loadCategoriesFailed() {
        mView.showCategories(null);
    }
}
