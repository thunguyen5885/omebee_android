package com.omebee.android.layers.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseActivity;
import com.omebee.android.layers.ui.fragments.ProductsLauncherFragment;
import com.omebee.android.layers.ui.presenters.ProductsLauncherPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.IProductsLauncherPresenter;
import com.omebee.android.layers.ui.views.IProductsLauncherView;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherActivity extends BaseActivity implements IProductsLauncherView{
    private ProductsLauncherPresenterImpl mProductsLauncherPresenter;
    private ProductsLauncherFragment mProductsLauncherFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_launcher);
        mProductsLauncherPresenter = new ProductsLauncherPresenterImpl(this);
        mProductsLauncherFragment = (ProductsLauncherFragment)getFragmentManager().findFragmentById(R.id.productsLauncherFragment);
        if(mProductsLauncherFragment!=null)
            mProductsLauncherFragment.setPresenter(mProductsLauncherPresenter);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.abmenu_products_launcher, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        boolean ret;
        if (item.getItemId() == R.id.menu_account)
        {
            // Handle Settings

            ret = true;
        }
        else
        {
            ret = super.onOptionsItemSelected( item );
        }
        return ret;
    }

    public IProductsLauncherPresenter getProductsLauncherPresenter() {
        return mProductsLauncherPresenter;
    }


    @Override
    public void displayProductName(String name) {
        mProductsLauncherFragment.displayProductName(name);
    }
}
