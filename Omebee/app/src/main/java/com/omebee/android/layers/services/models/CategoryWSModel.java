package com.omebee.android.layers.services.models;

import com.omebee.android.layers.services.AbstractWebServiceModel;

import java.util.List;

/**
 * Created by phan on 10/2/2014.
 */
public class CategoryWSModel extends AbstractWebServiceModel {
    private String CategoryName;
    private String CategoryId;
    private String CategoryParentId;
    private String CategoryParentName;
    private int CategoryLevel;
    private int CategoryPriority;
    private List<BrandWSModel> BrandsSet;//if brandset != null this is atomic category
}
