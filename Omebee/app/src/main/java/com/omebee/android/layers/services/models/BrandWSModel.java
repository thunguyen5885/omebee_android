package com.omebee.android.layers.services.models;

import com.omebee.android.layers.services.AbstractWebServiceModel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by phannguyen on 10/25/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrandWSModel extends AbstractWebServiceModel {
    private String BrandName;
    private String BrandId;

    public String getBrandName() {
        return BrandName;
    }

    @JsonProperty("BrandName")
    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getBrandId() {
        return BrandId;
    }

    @JsonProperty("BrandId")
    public void setBrandId(String brandId) {
        BrandId = brandId;
    }
}
