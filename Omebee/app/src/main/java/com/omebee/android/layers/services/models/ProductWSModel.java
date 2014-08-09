package com.omebee.android.layers.services.models;

import com.omebee.android.layers.services.IWebServiceModel;
import com.omebee.android.layers.services.models.builders.ProductWSBuilder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by phan on 8/7/2014.
 */
public class ProductWSModel implements IWebServiceModel {

    private String productName;
    private String productDescription;
    private String productUrl;
    public ProductWSModel(String productName, String productDescription, String productUrl) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productUrl = productUrl;
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


}
