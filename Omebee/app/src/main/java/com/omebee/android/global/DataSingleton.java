package com.omebee.android.global;

import android.content.Context;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.omebee.android.utils.ImageMemoryCache;

/**
 * Created by Guest on 10/25/2014.
 */
public class DataSingleton {
    private static DataSingleton mInstance;
    private ImageLoader mImageLoader;

    private DataSingleton(Context context){
        mImageLoader = new ImageLoader(Volley.newRequestQueue(context), ImageMemoryCache.INSTANCE);
    }
    public static DataSingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new DataSingleton(context);
        }
        return mInstance;
    }
    public ImageLoader getImageLoader(){
        return mImageLoader;
    }
}
