package com.omebee.android.layers.ui.components.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThuNguyen on 5/8/2015.
 */
public class FilterItemData {
    // Define some keys for serializable
    private static final String CATEGORY_KEY = "category_key";
    private static final String PRICE_MIN_VALUE_KEY = "price_min_value_key";
    private static final String PRICE_MAX_VALUE_KEY = "price_max_value_key";
    private static final String SALE_OFF_KEY = "sale_off_key";

    // Properties
    private String mCategoryId;
    private double[] mPriceRange = new double[2]; // Min and max price
    private boolean mIsSaleOff;

    // Constructor
    public FilterItemData(){
        setDefaultVal();
    }
    public FilterItemData(String categoryId, double[] priceRange, boolean isSaleOff){
        mCategoryId = categoryId;
        mPriceRange = priceRange;
        mIsSaleOff = isSaleOff;
    }
    public FilterItemData(String jsonString){
        try {
            JSONObject filterRootJsonObject = new JSONObject(jsonString);
            if(filterRootJsonObject != null){
                mCategoryId = filterRootJsonObject.optString(CATEGORY_KEY, "");
                mPriceRange[0] = filterRootJsonObject.optDouble(PRICE_MIN_VALUE_KEY, 0);
                mPriceRange[1] = filterRootJsonObject.optDouble(PRICE_MAX_VALUE_KEY, 0);
                mIsSaleOff = filterRootJsonObject.optBoolean(SALE_OFF_KEY, false);
            }
        } catch (JSONException e) {
            setDefaultVal();
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        JSONObject filterRootJsonObject = new JSONObject();
        try {
            filterRootJsonObject.accumulate(CATEGORY_KEY, mCategoryId);
            filterRootJsonObject.accumulate(PRICE_MIN_VALUE_KEY, mPriceRange[0]);
            filterRootJsonObject.accumulate(PRICE_MAX_VALUE_KEY, mPriceRange[1]);
            filterRootJsonObject.accumulate(SALE_OFF_KEY, mIsSaleOff);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return filterRootJsonObject.toString();
    }
    private void setDefaultVal(){
        mCategoryId = "";
        mPriceRange[0] = 0;
        mPriceRange[1] = 0;
        mIsSaleOff = false;
    }

    public void setCategoryId(String categoryId) {
        this.mCategoryId = categoryId;
    }

    public void setPriceRange(double[] mPriceRange) {
        this.mPriceRange = mPriceRange;
    }

    public void setIsSaleOff(boolean isSaleOff) {
        this.mIsSaleOff = isSaleOff;
    }

    public String getCategoryId() {
        return mCategoryId;
    }

    public double[] getPriceRange() {
        return mPriceRange;
    }

    public boolean isSaleOff() {
        return mIsSaleOff;
    }
}
