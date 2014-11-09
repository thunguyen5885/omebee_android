package com.omebee.android.layers.ui.components.data;

/**
 * Created by ThuNguyen on 10/25/2014.
 */
public class CategoryItemData {
    private String mId;
    private String mName;
    private String mPosterUrl;
    public CategoryItemData(){}
    public CategoryItemData(String id, String name, String posterUrl){
        mId = id;
        mName = name;
        mPosterUrl = posterUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public void setPosterUrl(String mPosterUrl) {
        this.mPosterUrl = mPosterUrl;
    }
}
