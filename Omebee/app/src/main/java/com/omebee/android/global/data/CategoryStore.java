package com.omebee.android.global.data;

import com.omebee.android.layers.services.models.CategoryWSModel;
import com.omebee.android.layers.services.models.ProductWSModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by phan on 10/3/2014.
 */
public class CategoryStore {
    //category tree will fix in max three level.
    private List<CategoryWSModel> mCategoryLevel1List = new ArrayList<CategoryWSModel>();//top level of category
    private HashMap<Integer,ArrayList> mCategoryLevel2Map = new HashMap<Integer, ArrayList>();//second level of category, key will be category id of top level
    private HashMap<Integer,ArrayList> mCategoryLevel3Map = new HashMap<Integer, ArrayList>();//third level of category, key will be category id of second level

    public  CategoryStore(){}
    public  CategoryStore(JSONArray categoryArrObj){
        //init value for mCategoryLevel1List,mCategoryLevel2Map,mCategoryLevel3Map from array json object
    }
    public void updateCategoryStore(JSONArray categoryArrObj){}


}
