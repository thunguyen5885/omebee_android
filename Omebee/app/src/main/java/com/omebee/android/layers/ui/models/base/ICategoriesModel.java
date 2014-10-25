package com.omebee.android.layers.ui.models.base;

import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.components.data.ProductDetailItemData;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;

import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public interface ICategoriesModel extends IBaseModel{
    public interface ILoadCategoriesCallback{
        public void loadCategoriesSuccess(List<CategoryItemData> categoriesList);
        public void loadCategoriesFailed();
    }
    void setILoadCategoriesCallback(ILoadCategoriesCallback callBack);
    ILoadCategoriesCallback getILoadCategoriesCallback();
    void loadCategories();
}


