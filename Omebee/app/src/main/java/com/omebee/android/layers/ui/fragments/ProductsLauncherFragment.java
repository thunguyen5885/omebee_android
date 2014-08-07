package com.omebee.android.layers.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseFragment;
import com.omebee.android.layers.ui.presenters.ProductsLauncherPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.IPresenter;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherFragment extends BaseFragment{
    ProductsLauncherPresenterImpl mPresenter;
    TextView txtName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_launcher,
                container, false);
        txtName = (TextView)view.findViewById(R.id.txtname);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        //if(mPresenter!=null)
           // presenter do something
    }

    public IPresenter getPresenter() {
        return mPresenter;
    }

    public void setPresenter(IPresenter presenter){
        mPresenter = (ProductsLauncherPresenterImpl) presenter;
    };

    public void displayProductName(String name){
        txtName.setText(name);
    }
}
