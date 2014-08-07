package com.omebee.android.layers.ui.models;

import com.omebee.android.layers.services.IWebServiceAccess;

import com.omebee.android.layers.services.IWebServiceModel;
import com.omebee.android.layers.services.WSAccessFactory;
import com.omebee.android.layers.services.WSResult;
import com.omebee.android.layers.services.concretes.ProductWSAccess;
import com.omebee.android.layers.services.models.ProductWSModel;
import com.omebee.android.layers.ui.models.base.IProductsLauncherModel;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherModel implements IProductsLauncherModel{


    @Override
    public String getProductName() {
        try {
            IWebServiceAccess WS = WSAccessFactory.getInstance().getWebServiceAccess(ProductWSAccess.class);
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
}
