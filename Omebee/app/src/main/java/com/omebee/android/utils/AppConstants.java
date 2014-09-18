package com.omebee.android.utils;

/**
 * Created by phan on 8/7/2014.
 * For all constant used in app
 */
public class AppConstants {
    public static final int UNDEFINED_ID = -1;

    public static enum WSMethod {
        GET, POST, PUT, DELETE
    }

    public static enum Currency{
        VND("đ"),
        USD("$");
        private String value;

        Currency(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
        @Override
        public String toString() {
            return this.getValue();
        }
    }
    public static final String DEFAULT_ENCODING = "UTF-8";
    /*Define all the keys for transmission between screens Start*/
    public static final String KEY_PRODUCT_ID = "product_id";
    /*Define all the keys for transmission between screens End*/

    /*Define request code from this screen to that screen*/
    public static final int REQUEST_CODE_HOME_TO_PRODUCT_DETAIL = 1001;
}
