package com.omebee.android.layers.ui.components.views.pullrefresh;

import android.view.View;

/**
 * Created by Thu Nguyen on 8/27/2014.
 */
public class MultiColumnsAdapter {
    public interface IViewTransmission {
        public void addItemToFirst(View view, int position);
        public void addItemToLast(View view, int position);
        public void onFinished();
    }
    protected IViewTransmission mIViewTransmission;

    public IViewTransmission getIViewTransmission() {
        return mIViewTransmission;
    }

    public void setIViewTransmission(IViewTransmission iViewTransmission) {
        this.mIViewTransmission = iViewTransmission;
    }

    public void notifyDataChanged(){};
    public int getCount(){
        return 0;
    }
    public View getView(int position){
        return null;
    }
    public View getView(Object data){
        return null;
    }
    public void launchData(){

    }
    public void pullRefresh(Object data){

    }
    public void loadMore(Object data){

    }
}
