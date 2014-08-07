package com.omebee.android.layers.services.models.builders;

import com.omebee.android.layers.services.models.ProductWSModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by phan on 8/7/2014.
 * This builder for ProductWSModel
 */
public class ProductWSBuilder {
    String productName = "";
    String productDescription = "";

    public ProductWSBuilder() {
    }

    public ProductWSBuilder(JSONObject productJson) throws JSONException {
        //parse json and set value for properties
    }

    public ProductWSBuilder withProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public ProductWSBuilder withProductDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }


    public ProductWSModel build(){
        return new ProductWSModel(productName,productDescription);
    }
}
