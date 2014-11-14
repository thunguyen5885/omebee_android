package com.omebee.android.layers.ui.views;

import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.views.base.IBaseView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ThuNguyen on 10/25/2014.
 */
public interface ISubCategoriesView extends IBaseView{
    void showSubCategories(HashMap<Integer, CategoryItemData> categoriesMap, HashMap<Integer, List<CategoryItemData>> subCategoryMap);
}
