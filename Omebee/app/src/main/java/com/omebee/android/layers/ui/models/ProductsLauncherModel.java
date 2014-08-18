package com.omebee.android.layers.ui.models;

import android.os.AsyncTask;

import com.omebee.android.layers.services.IWebServiceAccess;

import com.omebee.android.layers.services.WSAccessFactory;
import com.omebee.android.layers.services.WSResult;
import com.omebee.android.layers.services.concretes.ProductWSAccess;
import com.omebee.android.layers.services.models.ProductWSModel;
import com.omebee.android.layers.ui.models.base.IProductsLauncherModel;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherModel implements IProductsLauncherModel{
    String LARGE_BASE_URL = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/large/";
    String THUMB_BASE_URL = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/";
    ProductWSModel[] ITEMS = new ProductWSModel[] {
            new ProductWSModel("Picture 1", "Romain Guy", "flamingo.jpg"),
            new ProductWSModel("Picture 2", "Romain Guy", "rainbow.jpg"),
            new ProductWSModel("Picture 3", "Romain Guy", "over_there.jpg"),
            new ProductWSModel("Picture 4", "Romain Guy", "jelly_fish_2.jpg"),
            new ProductWSModel("Picture 5", "Romain Guy", "lone_pine_sunset.jpg"),
            new ProductWSModel("Picture 6", "Romain Guy", "flying_in_the_light.jpg"),
            new ProductWSModel("Picture 7", "Romain Guy", "caterpillar.jpg"),
            new ProductWSModel("Picture 8", "Romain Guy", "look_me_in_the_eye.jpg")
    };
    public interface IPullRefreshCallback{
        void pullRefreshSuccess(List<ProductsLauncherGridItemData> productsList);
        void pullRefreshFailed();
    }
    public interface  ILoadMoreCallback{
        void loadMoreSuccess(List<ProductsLauncherGridItemData> productsList);
        void loadMoreFailed();
    }
    // Properties
    private IPullRefreshCallback mIPullRefreshCallback;
    private ILoadMoreCallback mILoadMoreRefreshCallback;
    // Temporary to limit the load more
    private static final int TIMES_TO_LOAD_MORE = 5000;
    private int mCurrentPage = 0;
    private int mRefreshCount = 0;
    public IPullRefreshCallback getIPullRefreshCallback() {
        return mIPullRefreshCallback;
    }

    public void setIPullRefreshCallback(IPullRefreshCallback iPullRefreshCallback) {
        this.mIPullRefreshCallback = iPullRefreshCallback;
    }

    public ILoadMoreCallback getmILoadMoreRefreshCallback() {
        return mILoadMoreRefreshCallback;
    }

    public void setmILoadMoreRefreshCallback(ILoadMoreCallback mILoadMoreRefreshCallback) {
        this.mILoadMoreRefreshCallback = mILoadMoreRefreshCallback;
    }

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
    public List<ProductsLauncherGridItemData> loadProductList() {


        List<ProductsLauncherGridItemData> productList = new ArrayList<ProductsLauncherGridItemData>();
        for(ProductWSModel productModelItem: ITEMS){
            ProductsLauncherGridItemData item = new ProductsLauncherGridItemData(productModelItem.getProductName(), productModelItem.getProductDescription(),
                    THUMB_BASE_URL + productModelItem.getProductUrl());
            productList.add(item);
        }
        return  productList;

    }

    @Override
    public List<ProductsLauncherGridItemData> searchProduct(String keyword) {

        List<ProductsLauncherGridItemData> productList = new ArrayList<ProductsLauncherGridItemData>();
        if(keyword.length() > 0) {
            for (ProductWSModel productModelItem : ITEMS) {
                if (productModelItem.match(keyword)) {
                    ProductsLauncherGridItemData item = new ProductsLauncherGridItemData(productModelItem.getProductName(), productModelItem.getProductDescription(),
                            THUMB_BASE_URL + productModelItem.getProductUrl());
                    productList.add(item);
                }
            }
        }
        return  productList;
    }

    /**
     * Create dump data for pull refresh
     * @return
     */
    public List<ProductsLauncherGridItemData> createDumpDataForPullRefresh() {

        mRefreshCount ++;
        List<ProductsLauncherGridItemData> productList = new ArrayList<ProductsLauncherGridItemData>();
        for(int index = 0; index < 5; index ++) {
            String pictureName = "New picture " + (ITEMS.length + (mRefreshCount - 1) * 10  + index + 1);
            Random random = new Random();
            int indexRan = random.nextInt(ITEMS.length);

            ProductWSModel productModelItem = ITEMS[indexRan];
            ProductsLauncherGridItemData item = new ProductsLauncherGridItemData(pictureName, productModelItem.getProductDescription(),
                    THUMB_BASE_URL + productModelItem.getProductUrl());
            productList.add(item);
        }
        return productList;
    }

    /**
     * Create dump data for load more function
     * @return
     */
    private List<ProductsLauncherGridItemData> createDumpDataForLoadMore(){
        mCurrentPage ++;
        if(mCurrentPage <= TIMES_TO_LOAD_MORE){ // Load more 10 items
            List<ProductsLauncherGridItemData> productList = new ArrayList<ProductsLauncherGridItemData>();
            for(int index = 0; index < 10; index ++) {
                String pictureName = "Picture " + (ITEMS.length + (mCurrentPage - 1) * 10  + index + 1);
                Random random = new Random();
                int indexRan = random.nextInt(ITEMS.length);

                ProductWSModel productModelItem = ITEMS[indexRan];
                ProductsLauncherGridItemData item = new ProductsLauncherGridItemData(pictureName, productModelItem.getProductDescription(),
                        THUMB_BASE_URL + productModelItem.getProductUrl());
                productList.add(item);
            }
            return productList;
        }

        return null;
    }
    @Override
    public void loadMore() {
        new LoadMoreDataTask().execute();
    }

    @Override
    public void pullRefresh() {

        new PullToRefreshDataTask().execute();
    }


    private class LoadMoreDataTask extends AsyncTask<Void, Void, List<ProductsLauncherGridItemData>> {

        @Override
        protected List<ProductsLauncherGridItemData> doInBackground(Void... params) {

            if (isCancelled()) {
                return null;
            }

            // Simulates a background task
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }

            return createDumpDataForLoadMore();
        }

        @Override
        protected void onPostExecute(List<ProductsLauncherGridItemData> result) {
            if(mILoadMoreRefreshCallback != null){
                mILoadMoreRefreshCallback.loadMoreSuccess(result);
            }
            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            // Notify the loading more operation has finished
            if(mILoadMoreRefreshCallback != null){
                mILoadMoreRefreshCallback.loadMoreFailed();
            }
        }
    }

    private class PullToRefreshDataTask extends AsyncTask<Void, Void, List<ProductsLauncherGridItemData>> {

        @Override
        protected List<ProductsLauncherGridItemData> doInBackground(Void... params) {

            if (isCancelled()) {
                return null;
            }

            // Simulates a background task
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            return createDumpDataForPullRefresh();
        }

        @Override
        protected void onPostExecute(List<ProductsLauncherGridItemData> result) {
            super.onPostExecute(result);
            if(mIPullRefreshCallback != null){
                mIPullRefreshCallback.pullRefreshSuccess(result);
            }
            // Reset the current page
            //mCurrentPage = 0;
        }

        @Override
        protected void onCancelled() {
           if(mIPullRefreshCallback != null){
               mIPullRefreshCallback.pullRefreshFailed();
           }
        }
    }
}
