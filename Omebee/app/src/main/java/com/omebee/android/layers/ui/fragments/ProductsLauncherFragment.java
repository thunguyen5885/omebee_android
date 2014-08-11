package com.omebee.android.layers.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseFragment;
import com.omebee.android.layers.ui.components.adapters.ProductsLauncherGridAdapter;
import com.omebee.android.layers.ui.presenters.ProductsLauncherPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.IPresenter;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;

import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherFragment extends BaseFragment{
    private ProductsLauncherPresenterImpl mPresenter;
    private GridView mProductsGrid;
    private ProductsLauncherGridAdapter mProductsGridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_launcher,
                container, false);
        mProductsGrid = (GridView)view.findViewById(R.id.productGrid);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        if(mPresenter!=null){
            mPresenter.showProductList();
        }
    }

    public IPresenter getPresenter() {
        return mPresenter;
    }

    public void setPresenter(IPresenter presenter){
        mPresenter = (ProductsLauncherPresenterImpl) presenter;
    };

    public void displayProductName(String name){

    }

    public void selectItem(int position){
        mPresenter.onItemClicked(position);
    }

    public void setProductList(List<ProductsLauncherGridItemData> productList){
        // Check the adapter object, create new object in case of null otherwise notify it
        if(mProductsGridAdapter == null){
            mProductsGridAdapter = new ProductsLauncherGridAdapter(getActivity());
            mProductsGridAdapter.setProductsList(productList);
            mProductsGrid.setAdapter(mProductsGridAdapter);
        }else{
            mProductsGridAdapter.setProductsList(productList);
            mProductsGridAdapter.notifyDataSetChanged();
        }
    }
}
