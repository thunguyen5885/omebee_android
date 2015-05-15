package com.omebee.android.layers.ui.components.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThuNguyen on 5/8/2015.
 */
public class FilterItemData {
    // Define some keys for serializable
    private static final String NEW_IN_KEY = "new_in_key";
    private static final String PRICE_MIN_VALUE_KEY = "price_min_value_key";
    private static final String PRICE_MAX_VALUE_KEY = "price_max_value_key";
    private static final String PRICE_SELECTED_MIN_VALUE_KEY = "price_selected_min_value_key";
    private static final String PRICE_SELECTED_MAX_VALUE_KEY = "price_selected_max_value_key";
    private static final String SALE_OFF_KEY = "sale_off_key";

    // Properties
    private int mNewInIndex;
    private double[] mPriceRange = new double[2]; // Min and max price
    private double[] mSelectedPriceRange = new double[2];
    private boolean mIsSaleOff;

    // Constructor
    public FilterItemData(){
        setDefaultVal();
    }
    public FilterItemData(double[] priceRange, double[]selectedPriceRange, boolean isSaleOff){
        mPriceRange = priceRange;
        mIsSaleOff = isSaleOff;
    }
    public FilterItemData(String jsonString){
        try {
            JSONObject filterRootJsonObject = new JSONObject(jsonString);
            if(filterRootJsonObject != null){
                mNewInIndex = filterRootJsonObject.optInt(NEW_IN_KEY, 0);
                mPriceRange[0] = filterRootJsonObject.optDouble(PRICE_MIN_VALUE_KEY, 0);
                mPriceRange[1] = filterRootJsonObject.optDouble(PRICE_MAX_VALUE_KEY, 0);
                mSelectedPriceRange[0] = filterRootJsonObject.optDouble(PRICE_SELECTED_MIN_VALUE_KEY, 0);
                mSelectedPriceRange[1] = filterRootJsonObject.optDouble(PRICE_SELECTED_MAX_VALUE_KEY, 0);
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
            filterRootJsonObject.accumulate(NEW_IN_KEY, mNewInIndex);
            filterRootJsonObject.accumulate(PRICE_MIN_VALUE_KEY, mPriceRange[0]);
            filterRootJsonObject.accumulate(PRICE_MAX_VALUE_KEY, mPriceRange[1]);
            filterRootJsonObject.accumulate(PRICE_SELECTED_MIN_VALUE_KEY, mSelectedPriceRange[0]);
            filterRootJsonObject.accumulate(PRICE_SELECTED_MAX_VALUE_KEY, mSelectedPriceRange[1]);
            filterRootJsonObject.accumulate(SALE_OFF_KEY, mIsSaleOff);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return filterRootJsonObject.toString();
    }
    private void setDefaultVal(){
        mPriceRange[0] = 0;
        mPriceRange[1] = 0;
        mSelectedPriceRange[0] = 0;
        mSelectedPriceRange[1] = 0;
        mNewInIndex = 0;
        mIsSaleOff = false;
    }
    public void setVal(int newInIndex, double[] selectedPriceRange, boolean isSaleOff){
        mNewInIndex = newInIndex;
        mSelectedPriceRange = selectedPriceRange;
        mIsSaleOff = isSaleOff;
    }
    public void initPriceRange(double[] priceRange){
        mPriceRange[0] = mSelectedPriceRange[0] = priceRange[0];
        mPriceRange[1] = mSelectedPriceRange[1] = priceRange[1];
    }
    public boolean isInRange(double val){
        return (val >= mPriceRange[0] && val <= mPriceRange[1]);
    }
    public void setPriceRange(double[] mPriceRange) {
        this.mPriceRange = mPriceRange;
    }

    public void setSelectedPriceRange(double[] selectedPriceRange) {
        this.mSelectedPriceRange = selectedPriceRange;
    }

    public void setNewInIndex(int newInIndex) {
        this.mNewInIndex = newInIndex;
    }

    public void setIsSaleOff(boolean isSaleOff) {
        this.mIsSaleOff = isSaleOff;
    }

    public double[] getPriceRange() {
        return mPriceRange;
    }

    public double[] getSelectedPriceRange() {
        return mSelectedPriceRange;
    }

    public int getNewInIndex() {
        return mNewInIndex;
    }

    public boolean isSaleOff() {
        return mIsSaleOff;
    }
}
