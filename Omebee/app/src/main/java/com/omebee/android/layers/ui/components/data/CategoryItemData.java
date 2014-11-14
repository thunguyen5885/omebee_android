package com.omebee.android.layers.ui.components.data;

import com.omebee.android.layers.services.models.CategoryWSModel;

/**
 * Created by ThuNguyen on 10/25/2014.
 */
public class CategoryItemData {
    private String mId;
    private String mName;
    private String mPosterUrl;
    private int mLevel;
    public CategoryItemData(){}
    public CategoryItemData(String id, String name, String posterUrl){
        mId = id;
        mName = name;
        mPosterUrl = posterUrl;
    }
    public CategoryItemData(CategoryWSModel dataModel){
        if(dataModel != null){
            mId = dataModel.getCategoryId();
            mName = dataModel.getCategoryName();
            mPosterUrl = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/flamingo.jpg";
            mLevel = dataModel.getCategoryLevel();
        }
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
    public void setLevel(int level){
        mLevel = level;
    }
    public int getLevel(){
        return mLevel;
    }
}
