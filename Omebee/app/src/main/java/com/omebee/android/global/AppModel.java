package com.omebee.android.global;

import com.omebee.android.layers.services.models.BrandWSModel;
import com.omebee.android.layers.services.models.CategoryWSModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phan on 10/3/2014.
 */
public class AppModel {
    private static final String TAG = "AppModel";
    public JSONArray getUpdateCategoryTree(){

        return null;
    }
    private void createDumpDataForCategory(){

        List<CategoryWSModel> cateList = new ArrayList<CategoryWSModel>();
        List<BrandWSModel> brandList = new ArrayList<BrandWSModel>();
        String json = "";

       /* ArrayList<LinkPojo> arrLinks = linksDAOObj.GetLinksHistoryFromDateToDate(strStartDate, strEndDate,categoryId,sortby);

        //convert from list to json array object
        Gson GsonMapper = new Gson();
        JsonElement element = GsonMapper.toJsonTree(arrLinks, new TypeToken<List<LinkPojo>>() {}.getType());

        if (! element.isJsonArray()) {
            // fail appropriately
            throw new Exception("Cannot convert history data to json");
        }

        JsonArray jsonLinksArr = element.getAsJsonArray();*/
    }
}
