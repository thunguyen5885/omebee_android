package com.omebee.android.layers.services;

import com.omebee.android.layers.services.IWebServiceModel;

/**
 * Created by phannguyen on 8/9/14.
 */
public abstract class AbstractWebServiceModel implements IWebServiceModel{
    public String techID;

    public String getTechID() {
        return techID;
    }

    public void setTechID(String techID) {
        this.techID = techID;
    }

}
