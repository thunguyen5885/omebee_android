package com.omebee.android.layers.ui.base;

import com.omebee.android.layers.ui.components.filter.FilterPropertiesSet;

/**
 * Created by phan on 11/7/2014.
 */
public class BaseFilterableActivity extends BaseActivity {
    private FilterPropertiesSet mFilterPropertiesSet;

    public FilterPropertiesSet getFilterPropertiesSet() {
        return mFilterPropertiesSet;
    }

    public void setFilterPropertiesSet(FilterPropertiesSet filterPropertiesSet) {
        this.mFilterPropertiesSet = filterPropertiesSet;
    }
}
