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
    private HashMap<Integer,CategoryWSModel> mCategoryLevel1List = new HashMap<Integer,CategoryWSModel>();//top level of category
    private HashMap<Integer,ArrayList> mCategoryLevel2Map = new HashMap<Integer, ArrayList>();//second level of category, key will be category id of top level
    private HashMap<Integer,ArrayList> mCategoryLevel3Map = new HashMap<Integer, ArrayList>();//third level of category(atomic category, no children), key will be category id of second level
    private HashMap<Integer,ArrayList> mBrandsMap = new HashMap<Integer, ArrayList>();//fourth level of category is brand of product, key will be category id of third level
    private HashMap<Integer,ArrayList> mAllCategoryMap = new HashMap<Integer, ArrayList>();
    public  CategoryStore(){}

    public  CategoryStore(JSONArray categoryArrObj){
        //init value for mCategoryLevel1List,mCategoryLevel2Map,mCategoryLevel3Map,mBranchesMap from array json object

    }
    public void updateCategoryStore(JSONArray categoryArrObj){

    }

    public List<CategoryWSModel> getCategoryLevel1(){
        List<CategoryWSModel> res = new ArrayList<CategoryWSModel>(mCategoryLevel1List.values());
        return res;
    }

    public List<CategoryWSModel> getCategoryLevel2ByParentId(int CategoryParentId){
        if(mCategoryLevel2Map.containsKey(CategoryParentId))
            return mCategoryLevel2Map.get(CategoryParentId);
        return null;
    }

    public List<CategoryWSModel> getCategoryLevel3ByParentId(int CategoryParentId){
        if(mCategoryLevel3Map.containsKey(CategoryParentId))
            return mCategoryLevel3Map.get(CategoryParentId);
        return null;
    }

    public List<CategoryWSModel> getBranchesOfAtomicCategory(int CategoryParentId){
        if(mBrandsMap.containsKey(CategoryParentId))
            return mBrandsMap.get(CategoryParentId);
        return null;
    }

    public List<Integer> GetAllChildrenCategoryIds(int CategoryParentId){
        return null;
    }
}
