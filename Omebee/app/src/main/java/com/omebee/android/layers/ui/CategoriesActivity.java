package com.omebee.android.layers.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseActivity;
import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.fragments.CategoriesFragment;
import com.omebee.android.layers.ui.presenters.CategoriesPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.ICategoriesPresenter;
import com.omebee.android.layers.ui.views.ICategoriesView;

import java.util.List;

/**
 * Created by ThuNguyen on 10/25/2014.
 */
public class CategoriesActivity extends BaseActivity implements ICategoriesView{
    private CategoriesFragment mCategoriesFragment;
    private ICategoriesPresenter mCategoryPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_categories);
        getActionBar().setTitle(R.string.categories_text);

        // Initialize fragment object
        mCategoriesFragment = (CategoriesFragment) getFragmentManager().findFragmentById(R.id.categoriesFragment);
        mCategoryPresenter = new CategoriesPresenterImpl(this);
        mCategoriesFragment.setPresenter(mCategoryPresenter);
        // Translate animation
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    public void showCategories(List<CategoryItemData> categoriesList) {
        mCategoriesFragment.showCategories(categoriesList);
    }
}
