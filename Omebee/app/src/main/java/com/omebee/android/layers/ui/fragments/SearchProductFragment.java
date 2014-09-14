package com.omebee.android.layers.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseFragment;
import com.omebee.android.layers.ui.components.adapters.ProductsLauncherGridAdapter;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.presenters.base.IPresenter;
import com.omebee.android.layers.ui.presenters.base.IProductSearchPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thu Nguyen on 8/12/2014.
 */
public class SearchProductFragment extends BaseFragment{
    private static final String TAG = "SearchProductFragment";
    private ListView mProductListView;
    private TextView mEmptyView;
    private ProductsLauncherGridAdapter mProductAdapter;
    private IProductSearchPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_search,
                container, false);
        mProductListView = (ListView) view.findViewById(R.id.productSearchList);
        mEmptyView = (TextView) view.findViewById(R.id.emptySearchView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Search with keyword
     * @param key
     */
    public void search(String key){
        if(key.length() > 0) {
            if (mPresenter != null) {
                mPresenter.searchProduct(key);
            }
        }else{
            showProducts(new ArrayList<ProductsLauncherGridItemData>());
            // Hide empty view
            mEmptyView.setVisibility(View.GONE);
        }
    }

    public void showProducts(List<ProductsLauncherGridItemData> productList) {
        if(mProductAdapter == null){
            mProductAdapter = new ProductsLauncherGridAdapter(getActivity());
            mProductAdapter.setProductsList(productList);
            mProductListView.setAdapter(mProductAdapter);
        }else{
            mProductAdapter.setProductsList(productList);
            mProductAdapter.notifyDataSetChanged();
        }
        if(productList.size() == 0){ // Search with no products
            mEmptyView.setVisibility(View.VISIBLE);
            mProductListView.setVisibility(View.GONE);
        }else{
            mEmptyView.setVisibility(View.GONE);
            mProductListView.setVisibility(View.VISIBLE);
        }
    }
    public void setPresenter(IProductSearchPresenter presenter){
        mPresenter = presenter;
    }
    public IPresenter getPresenter(){
        return mPresenter;
    }

    /**
     * Reset before hide
     */
    public void reset(){
        mEmptyView.setVisibility(View.GONE);
        if(mProductAdapter != null) {
            mProductAdapter.getProductsList().clear();
            mProductAdapter.notifyDataSetChanged();
        }
    }
}
