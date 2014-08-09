package com.omebee.android.unknown;

/**
 * Created by Guest on 8/9/2014.
 */
public class ProductGridItemData {
    private String mProductName;
    private String mProductDescription;
    private String mProductUrl;

    public ProductGridItemData(String mProductName, String mProductDescription, String mProductUrl) {
        this.mProductName = mProductName;
        this.mProductDescription = mProductDescription;
        this.mProductUrl = mProductUrl;
    }

    public String getmProductName() {
        return mProductName;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public String getmProductDescription() {
        return mProductDescription;
    }

    public void setmProductDescription(String mProductDescription) {
        this.mProductDescription = mProductDescription;
    }

    public String getmProductUrl() {
        return mProductUrl;
    }

    public void setmProductUrl(String mProductUrl) {
        this.mProductUrl = mProductUrl;
    }
}
