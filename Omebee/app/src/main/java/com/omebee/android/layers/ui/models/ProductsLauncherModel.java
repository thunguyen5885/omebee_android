package com.omebee.android.layers.ui.models;

import com.omebee.android.layers.services.IWebServiceAccess;

import com.omebee.android.layers.services.IWebServiceModel;
import com.omebee.android.layers.services.WSAccessFactory;
import com.omebee.android.layers.services.WSResult;
import com.omebee.android.layers.services.concretes.ProductWSAccess;
import com.omebee.android.layers.services.models.ProductWSModel;
import com.omebee.android.layers.services.models.builders.ProductWSBuilder;
import com.omebee.android.layers.ui.models.base.IProductsLauncherModel;
import com.omebee.android.unknown.ProductGridItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherModel implements IProductsLauncherModel{


    @Override
    public String getProductName() {
        try {
            IWebServiceAccess<ProductWSModel> WS = WSAccessFactory.getInstance().getWebServiceAccess(ProductWSAccess.class);
            WSResult<ProductWSModel> res =  WS.executeRequest();
            return  res.getWsResultModel().getProductName();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Tèo";
    }

    @Override
    public List<ProductGridItemData> loadProductList() {
        String LARGE_BASE_URL = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/large/";
        String THUMB_BASE_URL = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/";
        ProductWSModel[] ITEMS = new ProductWSModel[] {
                new ProductWSModel("Flying in the Light", "Romain Guy", "flying_in_the_light.jpg"),
                new ProductWSModel("Caterpillar", "Romain Guy", "caterpillar.jpg"),
                new ProductWSModel("Look Me in the Eye", "Romain Guy", "look_me_in_the_eye.jpg"),
                new ProductWSModel("Flamingo", "Romain Guy", "flamingo.jpg"),
                new ProductWSModel("Rainbow", "Romain Guy", "rainbow.jpg"),
                new ProductWSModel("Over there", "Romain Guy", "over_there.jpg"),
                new ProductWSModel("Jelly Fish 2", "Romain Guy", "jelly_fish_2.jpg"),
                new ProductWSModel("Lone Pine Sunset", "Romain Guy", "lone_pine_sunset.jpg")
        };
        List<ProductGridItemData> productList = new ArrayList<ProductGridItemData>();
        for(ProductWSModel productModelItem: ITEMS){
            ProductGridItemData item = new ProductGridItemData(productModelItem.getProductName(), productModelItem.getProductDescription(),
                    THUMB_BASE_URL + productModelItem.getProductUrl());
            productList.add(item);
        }
        return  productList;

    }

}
