package com.omebee.android.utils;

import java.text.DecimalFormat;

/**
 * Created by phan on 8/7/2014.
 * For all util common functions
 */
public class AppFnUtils {

    public static String priceWithDecimal (Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###,###");
        return formatter.format(price);
    }

    public static String priceWithoutDecimal (Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###,###.###");
        return formatter.format(price);
    }

    public static String priceToString(Double price) {
        String toShow = priceWithoutDecimal(price);
        if (toShow.indexOf(".") > 0) {
            return priceWithDecimal(price);
        } else {
            return priceWithoutDecimal(price);
        }
    }
}
