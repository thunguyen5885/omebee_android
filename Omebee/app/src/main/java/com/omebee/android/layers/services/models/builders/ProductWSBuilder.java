package com.omebee.android.layers.services.models.builders;

import com.omebee.android.layers.services.models.ProductWSModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by phan on 8/7/2014.
 * This builder for ProductWSModel
 */
public class ProductWSBuilder {
    String productId = "";
    String productName = "";
    String productDescription = "";
    String productUrl = "";
    String categoryId = "";
    public ProductWSBuilder() {
    }

    public ProductWSBuilder(JSONObject productJson) throws JSONException {
        //parse json and set value for properties
    }
    public ProductWSBuilder withProductId(String productId) {
        this.productId = productId;
        return this;
    }
    public ProductWSBuilder withProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public ProductWSBuilder withProductDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }

    public ProductWSBuilder withCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public ProductWSModel build(){
        return new ProductWSModel(productId, productName, productDescription, productUrl,categoryId);
    }
}
