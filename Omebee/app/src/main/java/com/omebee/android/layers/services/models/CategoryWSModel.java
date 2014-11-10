package com.omebee.android.layers.services.models;

import com.omebee.android.layers.services.AbstractWebServiceModel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by phan on 10/2/2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryWSModel extends AbstractWebServiceModel{
    private String CategoryName;
    private String CategoryId;
    private String CategoryParentId;
    private String CategoryParentName;
    private int CategoryLevel;
    private int CategoryPriority;
    private List<String> BrandsSet;//if brandset != null this is atomic category, list of brand id

    public String getCategoryName() {
        return CategoryName;
    }

    @JsonProperty("CategoryName")
    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    @JsonProperty("CategoryId")
    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryParentId() {
        return CategoryParentId;
    }

    @JsonProperty("CategoryParentId")
    public void setCategoryParentId(String categoryParentId) {
        CategoryParentId = categoryParentId;
    }

    public String getCategoryParentName() {
        return CategoryParentName;
    }

    @JsonProperty("CategoryParentName")
    public void setCategoryParentName(String categoryParentName) {
        CategoryParentName = categoryParentName;
    }

    public int getCategoryLevel() {
        return CategoryLevel;
    }

    @JsonProperty("CategoryLevel")
    public void setCategoryLevel(int categoryLevel) {
        CategoryLevel = categoryLevel;
    }

    public int getCategoryPriority() {
        return CategoryPriority;
    }

    @JsonProperty("CategoryPriority")
    public void setCategoryPriority(int categoryPriority) {
        CategoryPriority = categoryPriority;
    }

    public List<String> getBrandsSet() {
        return BrandsSet;
    }
    @JsonProperty("BrandsSet")
    public void setBrandsSet(List<String> brandsSet) {
        BrandsSet = brandsSet;
    }
}
