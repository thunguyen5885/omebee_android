package com.omebee.android.layers.ui.models;

import android.os.AsyncTask;

import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.models.base.ICategoriesModel;

import java.util.ArrayList;
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
            List<CategoryItemData> categoryItemDataList = new ArrayList<CategoryItemData>();
            for(int index = 0; index < 10; index++){
                CategoryItemData item = new CategoryItemData();
                item.setName("Sport");
                item.setPosterUrl("http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/flamingo.jpg");
                categoryItemDataList.add(item);
            }
            return categoryItemDataList;
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
