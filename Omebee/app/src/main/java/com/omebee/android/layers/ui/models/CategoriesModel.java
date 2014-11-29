package com.omebee.android.layers.ui.models;

import android.os.AsyncTask;

import com.omebee.android.global.AppPresenter;
import com.omebee.android.layers.services.models.CategoryWSModel;
import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.models.base.ICategoriesModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ThuNguyen on 10/25/2014.
 */
public class CategoriesModel implements ICategoriesModel{
    private ILoadCategoriesCallback mILoadCategoriesCallback;
    private ILoadSubCategoriesCallback mILoadSubCategoriesCallback;
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

    @Override
    public void setILoadSubCategoriesCallback(ILoadSubCategoriesCallback callBack) {
        mILoadSubCategoriesCallback = callBack;
    }

    @Override
    public ILoadSubCategoriesCallback getILoadSubCategoriesCallback() {
        return mILoadSubCategoriesCallback;
    }

    @Override
    public void loadSubCategories(String parentCategoryId) {
        new LoadSubCategoriesAsynTask().execute(parentCategoryId);
    }

    @Override
    public void loadTop3LevelCategories() {
        new LoadCategoriesTop3LevelsAsynTask().execute();
    }

    private class LoadCategoriesAsynTask extends AsyncTask<Void, Void, List<CategoryItemData>>{
        @Override
        protected List<CategoryItemData> doInBackground(Void... params) {
            // Get from app presenter
            List<CategoryWSModel> categoryWSList = AppPresenter.getInstance().getCategoryLevel1List();
            List<CategoryItemData> categoryItemDataList = new ArrayList<CategoryItemData>();
            if(categoryWSList != null && categoryWSList.size() > 0) {
                for (int index = 0; index < categoryWSList.size(); index++) {
                    CategoryWSModel dataItem = categoryWSList.get(index);
                    CategoryItemData item = new CategoryItemData(dataItem);
                    categoryItemDataList.add(item);
                }
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
    private class LoadSubCategoriesAsynTask extends AsyncTask<String, Void, Void>{
        private HashMap<Integer, CategoryItemData> mCategoryItemDataMap = new HashMap<Integer, CategoryItemData>();
        private HashMap<Integer, List<CategoryItemData>> mSubCategoryItemDataMap = new HashMap<Integer, List<CategoryItemData>>();

        @Override
        protected Void doInBackground(String... params) {
            List<CategoryWSModel> categoryWSList = AppPresenter.getInstance().getCategoryLevel2List(params[0].toString());
            if(categoryWSList != null && categoryWSList.size() > 0){
                for(int index = 0; index < categoryWSList.size(); index ++){
                    CategoryWSModel dataModel = categoryWSList.get(index);
                    CategoryItemData item = new CategoryItemData(dataModel);
                    mCategoryItemDataMap.put(index, item);

                    // For sub category
                    List<CategoryWSModel> subDataModelList = AppPresenter.getInstance().getCategoryLevel3List(dataModel.getCategoryId());
                    if(subDataModelList != null && subDataModelList.size() > 0){
                        List<CategoryItemData> subItemList = new ArrayList<CategoryItemData>();
                        for(CategoryWSModel subDataModel: subDataModelList){
                            CategoryItemData subItem = new CategoryItemData(subDataModel);
                            subItemList.add(subItem);
                        }

                        mSubCategoryItemDataMap.put(index, subItemList);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mILoadSubCategoriesCallback != null){
                mILoadSubCategoriesCallback.loadSubCategoriesSuccess(mCategoryItemDataMap, mSubCategoryItemDataMap);
            }
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
            if(mILoadSubCategoriesCallback != null){
                mILoadSubCategoriesCallback.loadSubCategoriesFailed();
            }
        }
    }
    private class LoadCategoriesTop3LevelsAsynTask extends AsyncTask<Void, Void, Void>{
        private HashMap<Integer, CategoryItemData> mCategoryItemDataMap = new HashMap<Integer, CategoryItemData>();
        private HashMap<Integer, List<CategoryItemData>> mSubCategoryItemDataMap = new HashMap<Integer, List<CategoryItemData>>();

        @Override
        protected Void doInBackground(Void... params) {
            List<CategoryWSModel> categoryWSList = AppPresenter.getInstance().getCategoryLevel1List();
            if(categoryWSList != null && categoryWSList.size() > 0){
                for(int index = 0; index < categoryWSList.size(); index ++){
                    CategoryWSModel dataModel = categoryWSList.get(index);
                    CategoryItemData item = new CategoryItemData(dataModel);
                    mCategoryItemDataMap.put(index, item);

                    // For category level 2
                    List<CategoryWSModel> dataModelLevel2List = AppPresenter.getInstance().getCategoryLevel2List(dataModel.getCategoryId());
                    if(dataModelLevel2List != null && dataModelLevel2List.size() > 0){
                        List<CategoryItemData> subItemList = new ArrayList<CategoryItemData>();
                        for(CategoryWSModel dataModelLevel2: dataModelLevel2List){
                            CategoryItemData dataItemLevel2 = new CategoryItemData(dataModelLevel2);
                            subItemList.add(dataItemLevel2);
                            // Also get category level 2 here
                            List<CategoryWSModel> dataModelLevel3List = AppPresenter.getInstance().getCategoryLevel3List(dataModelLevel2.getCategoryId());
                            if(dataModelLevel3List != null && dataModelLevel3List.size() > 0){
                                for(CategoryWSModel dataModelLevel3 : dataModelLevel3List){
                                    CategoryItemData dataItemLevel3 = new CategoryItemData(dataModelLevel3);
                                    subItemList.add(dataItemLevel3);
                                }
                            }
                        }

                        mSubCategoryItemDataMap.put(index, subItemList);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mILoadSubCategoriesCallback != null){
                mILoadSubCategoriesCallback.loadSubCategoriesSuccess(mCategoryItemDataMap, mSubCategoryItemDataMap);
            }
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
            if(mILoadSubCategoriesCallback != null){
                mILoadSubCategoriesCallback.loadSubCategoriesFailed();
            }
        }
    }
}
