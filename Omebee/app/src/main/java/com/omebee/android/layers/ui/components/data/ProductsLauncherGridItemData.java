package com.omebee.android.layers.ui.components.data;

import com.omebee.android.R;
import com.omebee.android.utils.AppConstants;

import static com.omebee.android.utils.AppFnUtils.priceToString;

/**
 * Created by ThuNguyen on 8/9/2014.
 */
public class ProductsLauncherGridItemData {

    private static final String TAG = "ProductsLauncherGridAdapter";
    private String mProductName;
    private String mProductDescription;
    private String mProductUrl;
    private double mImageHeightRatio;//Product image
    private double mProductPrice;
    private AppConstants.Currency mCurrency;
    private int mWishListResourceId;

    public ProductsLauncherGridItemData(String mProductName, String mProductDescription, String mProductUrl
            ,double mImageHeightRatio,double productPrice,AppConstants.Currency currency) {
        this.mProductName = mProductName;
        this.mProductDescription = mProductDescription;
        this.mProductUrl = mProductUrl;
        this.mImageHeightRatio = mImageHeightRatio;
        this.mProductPrice = productPrice;
        this.mCurrency = currency;
        //wishlist icon constant
        this.mWishListResourceId = R.drawable.icon_wishlist;
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

    public double getImageHeightRatio() {
        return mImageHeightRatio;
    }

    public void setImageHeightRatio(double mImageHeightRatio) {
        this.mImageHeightRatio = mImageHeightRatio;
    }

    public double getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(double mProductPrice) {
        this.mProductPrice = mProductPrice;
    }

    public String productPriceToString(){
        return priceToString(mProductPrice);
    }

    public AppConstants.Currency getCurrency() {
        return mCurrency;
    }

    public void setCurrency(AppConstants.Currency mCurrency) {
        this.mCurrency = mCurrency;
    }

    public String currencyToString() {
        return mCurrency.toString();
    }
    public String productPriceWithCurrencyToString(){
        return productPriceToString() + currencyToString();
    }
    public int getWishListResourceId() {
        return mWishListResourceId;
    }

    public void setWishListResourceId(int mWishListResourceId) {
        this.mWishListResourceId = mWishListResourceId;
    }
}
