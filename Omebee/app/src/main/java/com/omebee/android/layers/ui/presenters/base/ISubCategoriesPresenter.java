package com.omebee.android.layers.ui.presenters.base;

/**
 * Created by ThuNguyen on 9/16/2014.
 */
public interface ISubCategoriesPresenter extends IPresenter{
    void getSubCategories(String parentCategoryId);
    void getTop3LevelCategories();
    void searchSubCategories(String keyword);
    void searchSubCategories(String keyword, String parentCategoryId);
}
