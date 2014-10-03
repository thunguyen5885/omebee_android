package com.omebee.android.global.data;

import com.omebee.android.global.AppPresenter;
import com.omebee.android.layers.services.models.ProductWSModel;
import com.omebee.android.layers.ui.components.data.ProductDetailItemData;
import com.omebee.android.utils.AppConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by phan on 10/1/2014.
 */
public class ProductWarehouse {
    //this list will store all products receive from backend, from here data will be built for displaying product grid and filter as well as instant search.
    private List<ProductWSModel> mProductListWarehouse = new ArrayList<ProductWSModel>();//this probably a tree or other data structure than a list

    public  ProductWarehouse(){

    }

    public List<ProductWSModel> getProductListWarehouse() {
        return mProductListWarehouse;
    }

    public void setProductListWarehouse(List<ProductWSModel> productListWarehouse) {
        this.mProductListWarehouse = productListWarehouse;
    }

    public void pushItemIntoWarehouse(ProductWSModel productItem){
        mProductListWarehouse.add(productItem);
    }

    public void pushItemIntoWarehouse(ProductWSModel productItem,int pos){
        mProductListWarehouse.add(pos,productItem);
    }

    public void pushItemIntoWarehouse(List<ProductWSModel> productItems){
        mProductListWarehouse.addAll(productItems);
    }

    public void pushItemIntoWarehouse(List<ProductWSModel> productItems,int pos){
        mProductListWarehouse.addAll(pos,productItems);
    }

    public ProductWSModel findItemInWarehouse(String productItemId){
        Iterator<ProductWSModel> iterator = mProductListWarehouse.iterator();
        while(iterator.hasNext()){
            ProductWSModel productModelItem = iterator.next();
            if(productModelItem.getProductId().equals(productItemId)){
                return productModelItem;
            }
        }
        return null;
    }
    public boolean isEmpty(){
        return mProductListWarehouse.size() <= 0;
    }

}
