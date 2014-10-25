package com.omebee.android.layers.ui.models;

import android.os.AsyncTask;

import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.models.base.ICategoriesModel;

import java.util.List;

/**
 * Created by ThuNguyen on 10/25/2014.
 */
public class CategoriesModel implements ICategoriesModel{
    private ILoadCategoriesCallback mILoadCategoriesCallback;
    @Override
    public void setILoadCategoriesCallback(ILoadCategoriesCallback callBack) {
        mILoadCategoriesCallback = callBack;
    }

    @Override
    public ILoadCategoriesCallback getILoadCategoriesCallback() {
        return mILoadCategoriesCallback;
    }

    @Override
    public void loadCategories() {
        new LoadCategoriesAsynTask().execute();
    }
    private class LoadCategoriesAsynTask extends AsyncTask<Void, Void, List<CategoryItemData>>{


        @Override
        protected List<CategoryItemData> doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onCancelled(List<CategoryItemData> categoryList) {
            super.onCancelled(categoryList);
            if(mILoadCategoriesCallback != null){
                mILoadCategoriesCallback.loadCategoriesFailed();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if(mILoadCategoriesCallback != null){
                mILoadCategoriesCallback.loadCategoriesFailed();
            }
        }

        @Override
        protected void onPostExecute(List<CategoryItemData> categoryList) {
            super.onPostExecute(categoryList);
            if(mILoadCategoriesCallback != null){
                mILoadCategoriesCallback.loadCategoriesSuccess(categoryList);
            }
        }
    }
}
