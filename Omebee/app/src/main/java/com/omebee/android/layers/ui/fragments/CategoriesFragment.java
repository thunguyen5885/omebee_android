package com.omebee.android.layers.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseFragment;
import com.omebee.android.layers.ui.components.adapters.CategoriesAdapter;
import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.components.views.util.ExpandableHeightGridView;
import com.omebee.android.layers.ui.presenters.base.ICategoriesPresenter;
import com.omebee.android.layers.ui.presenters.base.IPresenter;

import java.util.List;

/**
 * Created by ThuNguyen on 10/25/2014.
 */
public class CategoriesFragment extends BaseFragment{
    private ExpandableHeightGridView mCategoriesGridView;
    private ICategoriesPresenter mCategoriesPresenter;
    @Override
    public void setPresenter(IPresenter presenter) {
        mCategoriesPresenter = (ICategoriesPresenter) presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        mCategoriesGridView = (ExpandableHeightGridView) view.findViewById(R.id.gvCategories);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mCategoriesPresenter != null){
            mCategoriesPresenter.getTopCategories();
        }
    }

    public void showCategories(List<CategoryItemData> categoriesList){
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getActivity());
        categoriesAdapter.setCategoriesList(categoriesList);
        mCategoriesGridView.setAdapter(categoriesAdapter);
    }
}
