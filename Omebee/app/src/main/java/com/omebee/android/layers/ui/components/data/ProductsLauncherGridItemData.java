package com.omebee.android.layers.ui.components.data;

/**
 * Created by ThuNguyen on 8/9/2014.
 */
public class ProductsLauncherGridItemData {
    private String mProductName;
    private String mProductDescription;
    private String mProductUrl;

    public ProductsLauncherGridItemData(String mProductName, String mProductDescription, String mProductUrl) {
        this.mProductName = mProductName;
        this.mProductDescription = mProductDescription;
        this.mProductUrl = mProductUrl;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public String getProductDescription() {
        return mProductDescription;
    }

    public void setProductDescription(String mProductDescription) {
        this.mProductDescription = mProductDescription;
    }

    public String getProductUrl() {
        return mProductUrl;
    }

    public void setProductUrl(String mProductUrl) {
        this.mProductUrl = mProductUrl;
    }

    // Check name for duplication temporarily
    // Will check by product id later
    public boolean duplicate(ProductsLauncherGridItemData item){
        if(this.mProductName != null && item.getProductName() != null &&
                this.mProductName.equals(item.getProductName())){
            return true;
        }
        return false;
    }
}
