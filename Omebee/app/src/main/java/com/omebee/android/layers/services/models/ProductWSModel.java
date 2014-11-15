package com.omebee.android.layers.services.models;

import com.omebee.android.layers.services.AbstractWebServiceModel;
import com.omebee.android.layers.services.IWebServiceModel;
import com.omebee.android.layers.services.models.builders.ProductWSBuilder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by phan on 8/7/2014.
 */
public class ProductWSModel extends AbstractWebServiceModel {
    private String productId;
    private String productName;
    private String productDescription;
    private String productUrl;
    private String categoryId;
    public ProductWSModel(String productId, String productName, String productDescription, String productUrl,
                          String categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productUrl = productUrl;
        this.categoryId = categoryId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public boolean match(String key){
        if(productName.toLowerCase().contains(key.toLowerCase()) ||
                productDescription.toLowerCase().contains(key.toLowerCase())){
            return true;
        }
        return false;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
