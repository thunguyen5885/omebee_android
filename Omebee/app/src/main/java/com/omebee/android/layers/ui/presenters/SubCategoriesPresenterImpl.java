package com.omebee.android.layers.ui.presenters;

import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.models.CategoriesModel;
import com.omebee.android.layers.ui.models.base.ICategoriesModel;
import com.omebee.android.layers.ui.presenters.base.ICategoriesPresenter;
import com.omebee.android.layers.ui.presenters.base.ISubCategoriesPresenter;
import com.omebee.android.layers.ui.views.ICategoriesView;
import com.omebee.android.layers.ui.views.ISubCategoriesView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ThuNguyen on 9/16/2014.
 */
public class SubCategoriesPresenterImpl implements ISubCategoriesPresenter, ICategoriesModel.ILoadSubCategoriesCallback{
    private ISubCategoriesView mView;
    private ICategoriesModel mModel;
    public SubCategoriesPresenterImpl(ISubCategoriesView view){
        mView = view;
        mModel = new CategoriesModel();
        mModel.setILoadSubCategoriesCallback(this);
    }

    @Override
    public void init(Object view) {

    }


    @Override
    public void loadSubCategoriesSuccess(HashMap<Integer, CategoryItemData> parentCategoriesMap, HashMap<Integer, List<CategoryItemData>> subCategoriesMap) {
        mView.showSubCategories(parentCategoriesMap, subCategoriesMap);
    }

    @Override
    public void loadSubCategoriesFailed() {
        mView.showSubCategories(null, null);
    }

    @Override
    public void getSubCategories(String parentCategoryId) {
        mModel.loadSubCategories(parentCategoryId);
    }

    @Override
    public void getTop3LevelCategories() {
        mModel.loadTop3LevelCategories();
    }
}
