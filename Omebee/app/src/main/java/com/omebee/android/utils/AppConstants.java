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
}
