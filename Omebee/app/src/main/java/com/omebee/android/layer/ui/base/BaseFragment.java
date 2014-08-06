package com.omebee.android.layer.ui.base;

import android.app.Fragment;

import com.omebee.android.layer.ui.presenters.base.IPresenter;

/**
 * Created by phan on 8/6/2014.
 */
public abstract class BaseFragment extends Fragment{
    public void setPresenter(IPresenter presenter){};
    public IPresenter getPresenter(){return null;};
}
