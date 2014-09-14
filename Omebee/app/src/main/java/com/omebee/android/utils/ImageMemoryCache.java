package com.omebee.android.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

import java.util.Map;

/**
 * An image memory cache implementation for Volley which allows the retrieval of entries via a URL.
 * Volley internally inserts items with a key which is a combination of a the size of the image,
 * and the url.
 *
 * This class provide the method {@link #getBitmapFromUrl(String)} which allows the retrieval of
 * a bitmap solely on the URL.
 */
public class ImageMemoryCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    private static final String TAG = "ImageMemoryCache";
    /**
     * Singleton instance which has it's maximum size set to be 1/8th of the allowed memory size.
     */
    public static final ImageMemoryCache INSTANCE = new ImageMemoryCache(
            (int) (Runtime.getRuntime().maxMemory() / 8));

    // Cache the last created snapshot
    private Map<String, Bitmap> mLastSnapshot;

    private ImageMemoryCache(int maxSize) {
        super(maxSize);
    }

    public Bitmap getBitmapFromUrl(String url) {
        // If we do not have a snapshot to use, generate one
        if (mLastSnapshot == null) {
            mLastSnapshot = snapshot();
        }

        // Iterate through the snapshot to find any entries which match our url
        for (Map.Entry<String, Bitmap> entry : mLastSnapshot.entrySet()) {
            if (url.equals(extractUrl(entry.getKey()))) {
                // We've found an entry with the same url, return the bitmap
                return entry.getValue();
            }
        }

        // We didn't find an entry, so return null
        return null;
    }

    @Override
    public Bitmap getBitmap(String key) {
        return get(key);
    }

    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        put(key, bitmap);

        // An entry has been added, so invalidate the snapshot
        mLastSnapshot = null;
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);

        // An entry has been removed, so invalidate the snapshot
        mLastSnapshot = null;
    }

    private static String extractUrl(String key) {
        return key.substring(key.indexOf("http"));
    }


    @Override
    protected int sizeOf(String key, Bitmap value) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return value.getAllocationByteCount();
        }
        return value.getByteCount();

    }
}
