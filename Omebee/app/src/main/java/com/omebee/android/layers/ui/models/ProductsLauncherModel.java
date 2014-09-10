package com.omebee.android.layers.ui.models;

import android.os.AsyncTask;
import android.util.Log;

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
    //static final String THUMB_BASE_URL = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/";
    private final Random mRandom = new Random();
    static ProductWSModel[] ITEMS = new ProductWSModel[] {
            new ProductWSModel("Picture 1", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/flamingo.jpg"),
            new ProductWSModel("Picture 2", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/rainbow.jpg"),
            new ProductWSModel("Picture 3", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/over_there.jpg"),
            new ProductWSModel("Picture 4", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/jelly_fish_2.jpg"),
            new ProductWSModel("Picture 5", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/lone_pine_sunset.jpg"),
            new ProductWSModel("Picture 6", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/flying_in_the_light.jpg"),
            new ProductWSModel("Picture 7", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/caterpillar.jpg"),
            new ProductWSModel("Picture 8", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/look_me_in_the_eye.jpg"),
            new ProductWSModel("Picture 9", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wcsa-black-wb-print-01_medium.jpeg?v=1407820974"),
            new ProductWSModel("Picture 10", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wcsa-bluesteel-wb-print-01_medium.jpeg?v=1407820974"),
            new ProductWSModel("Picture 11", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wcsa-cocoa-wb-print-01_medium.jpeg?v=1407820974"),
            new ProductWSModel("Picture 12", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wcsa-cognac-wb-print-01_medium.jpeg?v=1407820974"),
            new ProductWSModel("Picture 13", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-slate-wb-singapore-web_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 14", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-slate-wb-web-01_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 15", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-slate-wb-web-02_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 16", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-slate-wb-web-04_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 17", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-slate-wb-web-06_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 18", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-slate-wb-web-06_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 19", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-cognac-wb-singapore-web_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 20", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-cognac-wb-web-01_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 21", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-cognac-wb-web-02_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 22", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-cognac-wb-web-05_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 23", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-black-wb-singapore-web_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 24", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-black-wb-web-02_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 25", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-black-wb-web-05_9fc4dc5b-67ed-44e7-97a6-1977669442e9_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 26", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-black-wb-web-06_medium.jpeg?v=1407820965"),
            new ProductWSModel("Picture 27", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-01_medium.jpeg?v=1407820934"),
            new ProductWSModel("Picture 28", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-03.jpeg?v=1407820934"),
            new ProductWSModel("Picture 29", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-cognac-wb-web-09.jpeg?v=1407820934"),
            new ProductWSModel("Picture 30", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-05.jpeg?v=1407820934"),
            new ProductWSModel("Picture 31", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-06.jpeg?v=1407820934"),
            new ProductWSModel("Picture 32", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-07.jpeg?v=1407820934"),
            new ProductWSModel("Picture 33", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-09.jpeg?v=1407820934"),
            new ProductWSModel("Picture 34", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-10.jpeg?v=1407820934"),
            new ProductWSModel("Picture 35", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-cognac-wb-web-03.jpeg?v=1407820934"),
            new ProductWSModel("Picture 36", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-cognac-wb-web-05.jpeg?v=1407820934"),
            new ProductWSModel("Picture 37", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-cognac-wb-web-06.jpeg?v=1407820934"),
            new ProductWSModel("Picture 38", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-cognac-wb-web-07.jpeg?v=1407820934")
//            new ProductWSModel("Picture 39", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-cognac-wb-web-09.jpeg?v=1407820934"),

    };

    // Properties
    private IPullRefreshCallback mIPullRefreshCallback;
    private ILoadMoreCallback mILoadMoreRefreshCallback;
    // Temporary to limit the load more
    private static final int TIMES_TO_LOAD_MORE = 10;
    private static final int ITEMS_PER_PAGE_LOAD_MORE = 4;
    private int mCurrentPage = 0;
    private int mRefreshCount = 0;

    public IPullRefreshCallback getIPullRefreshCallback() {
        return mIPullRefreshCallback;
    }

    public void setIPullRefreshCallback(IPullRefreshCallback iPullRefreshCallback) {
        this.mIPullRefreshCallback = iPullRefreshCallback;
    }

    public ILoadMoreCallback getILoadMoreRefreshCallback() {
        return mILoadMoreRefreshCallback;
    }

    public void setILoadMoreRefreshCallback(ILoadMoreCallback mILoadMoreRefreshCallback) {
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
        int countTest = 0;
        for (ProductWSModel productModelItem : ITEMS) {
            ProductsLauncherGridItemData item = new ProductsLauncherGridItemData(productModelItem.getProductName(), productModelItem.getProductDescription(),
                    productModelItem.getProductUrl(),getRandomHeightRatio());
            productList.add(item);
            countTest++;
            if(countTest>10)
                break;
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
                            productModelItem.getProductUrl(),getRandomHeightRatio());
                    productList.add(item);
                }
            }
        }
        return  productList;
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
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            return createDumpDataForLoadMore();
        }

        @Override
        protected void onPostExecute(List<ProductsLauncherGridItemData> result) {
            if(mILoadMoreRefreshCallback != null){
                boolean isEndOfList = (result != null) && (result.size() < ITEMS_PER_PAGE_LOAD_MORE);
                mILoadMoreRefreshCallback.loadMoreSuccess(result, isEndOfList);
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
    public static String getRandomUrl(){
        Random random = new Random();
        int index = random.nextInt(ITEMS.length);
        String url = ITEMS[index].getProductUrl();
        Log.d("ThuNguyen", "URL: " + url);
        return url;
    }
    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
    }

    int mPictureCount = 1;
    /**
     * Create dump data for pull refresh
     * @return
     */
    public List<ProductsLauncherGridItemData> createDumpDataForPullRefresh() {

        mRefreshCount ++;
        Random random = new Random();
        int num = 4;//random.nextInt(10);
        Log.d("ThuNguyen", "createDumpDataForPullRefresh "+mRefreshCount +" with num = "+num);
//        if(num%2==0) {
//            num++;
//        }
        List<ProductsLauncherGridItemData> productList = new ArrayList<ProductsLauncherGridItemData>();
        for(int index = 0; index < num; index ++) {
            String pictureName = "New picture " + mPictureCount;//((mRefreshCount - 1) * num  + index + 1);
            mPictureCount++;
            int indexRan = random.nextInt(ITEMS.length);

            ProductWSModel productModelItem = ITEMS[indexRan];
            ProductsLauncherGridItemData item = new ProductsLauncherGridItemData(pictureName, productModelItem.getProductDescription(),
                    productModelItem.getProductUrl(),getRandomHeightRatio());
            productList.add(0, item);

        }
        return productList;
    }

    /**
     * Create dump data for load more function
     * @return
     */
    private List<ProductsLauncherGridItemData> createDumpDataForLoadMore(){
        Log.d("ThuNguyen", "createDumpDataForLoadMore "+mCurrentPage);
        mCurrentPage ++;
        int num = ITEMS_PER_PAGE_LOAD_MORE;

        if(mCurrentPage <= TIMES_TO_LOAD_MORE){ // Load more 10 items
            List<ProductsLauncherGridItemData> productList = new ArrayList<ProductsLauncherGridItemData>();
            if(mCurrentPage == TIMES_TO_LOAD_MORE - 1){
                num = ITEMS_PER_PAGE_LOAD_MORE - 1;
            }
            for(int index = 0; index < num; index ++) {
                String pictureName = "Old Picture " + ((mCurrentPage - 1) * num  + index + 1);
                Random random = new Random();
                int indexRan = random.nextInt(ITEMS.length);

                ProductWSModel productModelItem = ITEMS[indexRan];
                ProductsLauncherGridItemData item = new ProductsLauncherGridItemData(pictureName, productModelItem.getProductDescription(),
                        productModelItem.getProductUrl(),getRandomHeightRatio());
                productList.add(item);
            }
            return productList;
        }

        return null;
    }
}
