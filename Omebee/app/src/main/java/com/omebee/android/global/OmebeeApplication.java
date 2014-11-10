package com.omebee.android.global;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.omebee.android.global.data.CategoryStore;
import com.omebee.android.global.data.ProductWarehouse;

/**
 * Created by phan on 9/30/2014.
 */
public class OmebeeApplication extends Application{
    private static final String TAG = "OmebeeApplication";
    private ProductWarehouse mProductWarehouse = new ProductWarehouse();
    private CategoryStore mCategoryStore = new CategoryStore();
    /*****************************Main events *****************************************************/

    @Override
    public void onCreate() {
        // First, call the parent class
        super.onCreate();

        // This is a good place to put code that must manage global data across
        // multiple activities, but it's better to keep most things in a
        // database, rather than in memory
        Log.i(TAG, "onCreate");
        //init app presenter for using cross app
        AppPresenter.getInstance().setAppObj(this);
        AppPresenter.getInstance().updateCategoryStore();
    }

    @Override
    public void onTerminate() {
        Log.i(TAG, "onTerminate");

    }

    @Override
    public void onLowMemory() {
        // In-memory caches should be thrown overboard here
        Log.i(TAG, "onLowMemory");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i(TAG, "onConfigurationChanged");
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, newConfig.toString());
        }

    }

    /**********************************************************************************************/

    public ProductWarehouse getProductWarehouse() {
        return mProductWarehouse;
    }

    public void setProductWarehouse(ProductWarehouse productWarehouse) {
        this.mProductWarehouse = productWarehouse;
    }

    public CategoryStore getCategoryStore() {
        return mCategoryStore;
    }

    public void setCategoryStore(CategoryStore mCategoryStore) {
        this.mCategoryStore = mCategoryStore;
    }

}
