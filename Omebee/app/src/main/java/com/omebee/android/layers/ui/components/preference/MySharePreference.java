package com.omebee.android.layers.ui.components.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ThuNguyen on 1/8/2015.
 */
public class MySharePreference {
    private static final String OMEBEE_PREFERENCE_KEY = "omebee_preference_key";
    private static final String FILTER_ITEM_INFO_KEY = "filter_item_info_key";

    public static void storeFilterItemInfo(Context context, String filterItemJson){
        SharedPreferences.Editor editor = context.getSharedPreferences(
                OMEBEE_PREFERENCE_KEY, Context.MODE_PRIVATE).edit();
        editor.putString(FILTER_ITEM_INFO_KEY, filterItemJson);
        editor.commit();

    }
    public static void clearFilterItemInfo(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(
                OMEBEE_PREFERENCE_KEY, Context.MODE_PRIVATE).edit();
        editor.putString(FILTER_ITEM_INFO_KEY, "");
        editor.commit();
    }
    public static String getFilterItemInfo(Context context){
        SharedPreferences lSharePreference = context.getSharedPreferences(
                OMEBEE_PREFERENCE_KEY, Context.MODE_PRIVATE);
        String filterItemInfoJson = lSharePreference.getString(
                FILTER_ITEM_INFO_KEY, "");
        return filterItemInfoJson;
    }
    public static boolean isFilterItemStore(Context context){
        String filterItemInfoJson = getFilterItemInfo(context);
        return (filterItemInfoJson != null) && (filterItemInfoJson.length() > 0);
    }
}
