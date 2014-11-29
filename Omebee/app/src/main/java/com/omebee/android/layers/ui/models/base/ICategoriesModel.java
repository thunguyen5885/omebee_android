package com.omebee.android.layers.ui.models.base;

import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.components.data.ProductDetailItemData;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;

import java.util.HashMap;
import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public interface ICategoriesModel extends IBaseModel{
    public interface ILoadCategoriesCallback{
        public void loadCategoriesSuccess(List<CategoryItemData> categoriesList);
        public void loadCategoriesFailed();
    }
    public interface ILoadSubCategoriesCallback{
        public void loadSubCategoriesSuccess(HashMap<Integer, CategoryItemData> parentCategoriesMap, HashMap<Integer, List<CategoryItemData>> subCategoriesMap);
        public void loadSubCategoriesFailed();
    }
    // For load top categories
    void setILoadCategoriesCallback(ILoadCategoriesCallback callBack);
    ILoadCategoriesCallback getILoadCategoriesCallback();
    void loadCategories();

    // For load sub categories
    void setILoadSubCategoriesCallback(ILoadSubCategoriesCallback callBack);
    ILoadSubCategoriesCallback getILoadSubCategoriesCallback();
    void loadSubCategories(String parentCategoryId);
    void loadTop3LevelCategories();
}


