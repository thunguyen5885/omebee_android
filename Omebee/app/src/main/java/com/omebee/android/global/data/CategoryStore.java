package com.omebee.android.global.data;

import com.omebee.android.layers.services.models.BrandWSModel;
import com.omebee.android.layers.services.models.CategoryWSModel;
import com.omebee.android.layers.services.models.ProductWSModel;
import com.omebee.android.utils.BackEndConstants;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by phan on 10/3/2014.
 */
public class CategoryStore {
    //category tree will fix in max three level.
    private HashMap<String,CategoryWSModel> mCategoryLevel1Map = new HashMap<String,CategoryWSModel>();//top level of category
    private HashMap<String,ArrayList> mCategoryLevel2Map = new HashMap<String, ArrayList>();//second level of category, key will be category id of top level
    private HashMap<String,ArrayList> mCategoryLevel3Map = new HashMap<String, ArrayList>();//third level of category(atomic category, no children), key will be category id of second level
    private HashMap<String,ArrayList> mBrandsMap = new HashMap<String, ArrayList>();//fourth level of category is brand of product, key will be category id of third level
    private HashMap<String,CategoryWSModel> mAllCategoryMap = new HashMap<String, CategoryWSModel>();
    private HashMap<String,BrandWSModel> mAllBrandMap = new HashMap<String, BrandWSModel>();
    public  CategoryStore(){}

    public  CategoryStore(JSONObject categoryStoreObj){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JSONArray arrayCategories = categoryStoreObj.getJSONArray(BackEndConstants.JSON_CATEGORYSTORE_CATEGORIES);
            JSONArray arrayBrands = categoryStoreObj.getJSONArray(BackEndConstants.JSON_CATEGORYSTORE_BRANDS);
            List<CategoryWSModel> categoriesList = objectMapper.readValue(
                    arrayCategories.toString(),
                    new TypeReference<List<CategoryWSModel>>() {
                    });
            List<BrandWSModel> brandsList = objectMapper.readValue(
                    arrayBrands.toString(),
                    new TypeReference<List<BrandWSModel>>() {
                    });
            constructDataForCategoryStore(categoriesList,brandsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void updateCategoryStore(JSONObject categoryStoreObj){
        try {
            mCategoryLevel1Map.clear();
            mCategoryLevel2Map.clear();
            mCategoryLevel3Map.clear();
            mBrandsMap.clear();
            mAllCategoryMap.clear();
            mAllBrandMap.clear();

            ObjectMapper objectMapper = new ObjectMapper();
            JSONArray arrayCategories = categoryStoreObj.getJSONArray(BackEndConstants.JSON_CATEGORYSTORE_CATEGORIES);
            JSONArray arrayBrands = categoryStoreObj.getJSONArray(BackEndConstants.JSON_CATEGORYSTORE_BRANDS);
            List<CategoryWSModel> categoriesList = objectMapper.readValue(
                    arrayCategories.toString(),
                    new TypeReference<List<CategoryWSModel>>() {
                    });
            List<BrandWSModel> brandsList = objectMapper.readValue(
                    arrayBrands.toString(),
                    new TypeReference<List<BrandWSModel>>() {
                    });
            constructDataForCategoryStore(categoriesList,brandsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<CategoryWSModel> getCategoryLevel1(){
        List<CategoryWSModel> res = new ArrayList<CategoryWSModel>(mCategoryLevel1Map.values());
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

    public List<BrandWSModel> getBranchesOfAtomicCategory(int CategoryParentId){
        if(mBrandsMap.containsKey(CategoryParentId))
            return mBrandsMap.get(CategoryParentId);
        return null;
    }

    public List<CategoryWSModel> GetAllChildrenCategoryIds(int CategoryParentId){
        List<CategoryWSModel> res = new ArrayList<CategoryWSModel>();
        if(mCategoryLevel2Map.containsKey(CategoryParentId)){//level 1
            res.addAll( mCategoryLevel2Map.get(CategoryParentId));
            for(Object item : mCategoryLevel2Map.get(CategoryParentId)){
                CategoryWSModel cateItem = (CategoryWSModel)item;
                if(mCategoryLevel3Map.containsKey(cateItem.getCategoryId())){
                    res.addAll(mCategoryLevel3Map.get(cateItem.getCategoryId()));
                }
            }
        }else{
            if(mCategoryLevel3Map.containsKey(CategoryParentId)){
                res.addAll( mCategoryLevel3Map.get(CategoryParentId));
            }
        }
        return res;
    }

    private void constructDataForCategoryStore(List<CategoryWSModel> categoriesList,List<BrandWSModel> brandsList){
        for(BrandWSModel brandItem : brandsList){
            mAllBrandMap.put(brandItem.getBrandId(),brandItem);
        }

        for(CategoryWSModel cateItem : categoriesList){
            mAllCategoryMap.put(cateItem.getCategoryId(),cateItem);
            switch (cateItem.getCategoryLevel()){
                case 1:
                    mCategoryLevel1Map.put(cateItem.getCategoryId(),cateItem);
                    break;
                case 2:
                    if(mCategoryLevel2Map.containsKey(cateItem.getCategoryParentId()))
                        mCategoryLevel2Map.get(cateItem.getCategoryParentId()).add(cateItem);
                    else
                        mCategoryLevel2Map.put(cateItem.getCategoryParentId(), new ArrayList(Arrays.asList(cateItem)));
                    break;
                case 3:
                    if(mCategoryLevel3Map.containsKey(cateItem.getCategoryParentId()))
                        mCategoryLevel3Map.get(cateItem.getCategoryParentId()).add(cateItem);
                    else
                        mCategoryLevel3Map.put(cateItem.getCategoryParentId(), new ArrayList(Arrays.asList(cateItem)));
                    if(cateItem.getBrandsSet()!=null && cateItem.getBrandsSet().size()>0){
                        if(!mBrandsMap.containsKey(cateItem.getCategoryId()))
                            mBrandsMap.put(cateItem.getCategoryId(),new ArrayList());
                        for(String brandIdItem: cateItem.getBrandsSet()){
                            mBrandsMap.get(cateItem.getCategoryId()).add(mAllBrandMap.get(brandIdItem));
                        }
                    }
                    break;
                default:
                    break;
            }
        }


    }
}
