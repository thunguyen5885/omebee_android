package com.omebee.android.global;

import com.omebee.android.layers.services.models.ProductWSModel;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by phan on 9/30/2014.
 */
public class AppPresenter {
    private static final String TAG = "AppPresenter";
    private static AppPresenter instance;
    private static Object lock = new Object();
    private OmebeeApplication mAppObj;
    private AppModel mModel;
    public static AppPresenter getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new AppPresenter();
            }
        }
        return instance;
    }

    public  AppPresenter(){
        mModel = new AppModel();
    }

    public OmebeeApplication getAppObj() {
        return mAppObj;
    }

    public void setAppObj(OmebeeApplication _appObj) {
        this.mAppObj = _appObj;
    }

    public void pushProductIntoWarehouse(ProductWSModel productItem){
        mAppObj.getProductWarehouse().pushItemIntoWarehouse(productItem);
    }

    public void pushProductListIntoWarehouse(List<ProductWSModel> productItems){
        mAppObj.getProductWarehouse().pushItemIntoWarehouse(productItems);
    }

    public void pushProductIntoWarehouse(ProductWSModel productItem,int pos){
        mAppObj.getProductWarehouse().pushItemIntoWarehouse(productItem,pos);
    }


    public void pushProductIntoWarehouse(List<ProductWSModel> productItems,int pos){
        mAppObj.getProductWarehouse().pushItemIntoWarehouse(productItems,pos);
    }

    public ProductWSModel findProductInWarehouse(String productId){
        return mAppObj.getProductWarehouse().findItemInWarehouse(productId);
    }

    public  void updateCategoryStore(JSONArray categoryArrObj){
        mAppObj.getCategoryStore().updateCategoryStore(categoryArrObj);
    }
    public  void updateCategoryStore(){
        mAppObj.getCategoryStore().updateCategoryStore(mModel.getUpdateCategoryTree());
    }
    public boolean isWarehouseEmpty(){
        return mAppObj.getProductWarehouse().isEmpty();
    }
}
