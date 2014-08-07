package com.omebee.android.layers.services.concretes;

import com.omebee.android.layers.services.AbstractWSAccess;
import com.omebee.android.layers.services.WSRequest;
import com.omebee.android.layers.services.models.ProductWSModel;
import com.omebee.android.layers.services.models.builders.ProductWSBuilder;
import com.omebee.android.utils.AppConstants.WSMethod;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by phan on 8/7/2014.
 */
public class ProductWSAccess extends AbstractWSAccess<ProductWSModel> {
    private static final URI GET_PRODUCT_URI = URI.create(WEBSERVICE_HOST + "/get_product.json");


    @Override
    protected WSRequest buildRequest() {
        WSRequest request = new WSRequest(WSMethod.GET, GET_PRODUCT_URI, headers, null);
        return request;
    }

    @Override
    protected ProductWSModel parseResponseBody(String responseBody) throws Exception {
        JSONObject json = new JSONObject(responseBody);
        return new ProductWSBuilder(json).build();
    }
}
