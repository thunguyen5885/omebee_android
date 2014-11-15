package com.omebee.android.layers.ui.models;

import android.os.AsyncTask;
import android.util.Log;

import com.omebee.android.global.AppPresenter;
import com.omebee.android.layers.services.IWebServiceAccess;

import com.omebee.android.layers.services.WSAccessFactory;
import com.omebee.android.layers.services.WSResult;
import com.omebee.android.layers.services.concretes.ProductWSAccess;
import com.omebee.android.layers.services.models.ProductWSModel;
import com.omebee.android.layers.ui.components.CarouselIndicatorLayout;
import com.omebee.android.layers.ui.components.data.ProductDetailItemData;
import com.omebee.android.layers.ui.models.base.IProductsLauncherModel;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.utils.AppConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherModel implements IProductsLauncherModel{
    private static final String TAG = "ProductsLauncherModel";
    String LARGE_BASE_URL = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/large/";
    //static final String THUMB_BASE_URL = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/";
    private final Random mRandom = new Random();
    //private static List<ProductWSModel> mProductModelData = new ArrayList<ProductWSModel>();
    static ProductWSModel[] ITEMS = new ProductWSModel[] {
            new ProductWSModel("0001","Picture 1", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/flamingo.jpg","53"),
            new ProductWSModel("0002","Picture 2", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/rainbow.jpg","54"),
            new ProductWSModel("0003","Picture 3", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/over_there.jpg","55"),
            new ProductWSModel("0004","Picture 4", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/jelly_fish_2.jpg","56"),
            new ProductWSModel("0005","Picture 5", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/lone_pine_sunset.jpg","57"),
            new ProductWSModel("0006","Picture 6", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/flying_in_the_light.jpg","58"),
            new ProductWSModel("0007","Picture 7", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/caterpillar.jpg","59"),
            new ProductWSModel("0008","Picture 8", "Romain Guy", "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/look_me_in_the_eye.jpg","60"),
            new ProductWSModel("0009","Picture 9", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wcsa-black-wb-print-01_medium.jpeg?v=1407820974","61"),
            new ProductWSModel("0010","Picture 10", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wcsa-bluesteel-wb-print-01_medium.jpeg?v=1407820974","62"),
            new ProductWSModel("0011","Picture 11", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wcsa-cocoa-wb-print-01_medium.jpeg?v=1407820974","63"),
            new ProductWSModel("0012","Picture 12", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wcsa-cognac-wb-print-01_medium.jpeg?v=1407820974","64"),
            new ProductWSModel("0013","Picture 13", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-slate-wb-singapore-web_medium.jpeg?v=1407820965","65"),
            new ProductWSModel("0014","Picture 14", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-slate-wb-web-01_medium.jpeg?v=1407820965","66"),
            new ProductWSModel("0015","Picture 15", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-slate-wb-web-02_medium.jpeg?v=1407820965","56"),
            new ProductWSModel("0016","Picture 16", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-slate-wb-web-04_medium.jpeg?v=1407820965","58"),
            new ProductWSModel("0017","Picture 17", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-slate-wb-web-06_medium.jpeg?v=1407820965","90"),
            new ProductWSModel("0018","Picture 18", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-slate-wb-web-06_medium.jpeg?v=1407820965","100"),
            new ProductWSModel("0019","Picture 19", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-cognac-wb-singapore-web_medium.jpeg?v=1407820965","110"),
            new ProductWSModel("0020","Picture 20", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-cognac-wb-web-01_medium.jpeg?v=1407820965","83"),
            new ProductWSModel("0021","Picture 21", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-cognac-wb-web-02_medium.jpeg?v=1407820965","94"),
            new ProductWSModel("0022","Picture 22", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-cognac-wb-web-05_medium.jpeg?v=1407820965","97"),
            new ProductWSModel("0023","Picture 23", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-black-wb-singapore-web_medium.jpeg?v=1407820965","87"),
            new ProductWSModel("0024","Picture 24", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-black-wb-web-02_medium.jpeg?v=1407820965","86"),
            new ProductWSModel("0025","Picture 25", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-black-wb-web-05_9fc4dc5b-67ed-44e7-97a6-1977669442e9_medium.jpeg?v=1407820965","68"),
            new ProductWSModel("0026","Picture 26", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wepa-black-wb-web-06_medium.jpeg?v=1407820965","93"),
            new ProductWSModel("0027","Picture 27", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-01_medium.jpeg?v=1407820934","103"),
            new ProductWSModel("0028","Picture 28", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-03.jpeg?v=1407820934","73"),
            new ProductWSModel("0029","Picture 29", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-cognac-wb-web-09.jpeg?v=1407820934","83"),
            new ProductWSModel("0030","Picture 30", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-05.jpeg?v=1407820934","55"),
            new ProductWSModel("0031","Picture 31", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-06.jpeg?v=1407820934","73"),
            new ProductWSModel("0032","Picture 32", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-07.jpeg?v=1407820934","78"),
            new ProductWSModel("0033","Picture 33", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-09.jpeg?v=1407820934","86"),
            new ProductWSModel("0034","Picture 34", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-black-wb-web-10.jpeg?v=1407820934","98"),
            new ProductWSModel("0035","Picture 35", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-cognac-wb-web-03.jpeg?v=1407820934","112"),
            new ProductWSModel("0036","Picture 36", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-cognac-wb-web-05.jpeg?v=1407820934","125"),
            new ProductWSModel("0037","Picture 37", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-cognac-wb-web-06.jpeg?v=1407820934","115"),
            new ProductWSModel("0038","Picture 38", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-cognac-wb-web-07.jpeg?v=1407820934","89")
//            new ProductWSModel("Picture 39", "Romain Guy", "https://cdn.shopify.com/s/files/1/0604/6201/products/bellroy-wesa-cognac-wb-web-09.jpeg?v=1407820934"),

    };
    public ProductsLauncherModel(){
        /*if(mProductModelData.size() == 0) {
            mProductModelData = new ArrayList<ProductWSModel>(Arrays.asList(ITEMS));
        }*/
        // Push the initial data for the first time
        if(AppPresenter.getInstance().isWarehouseEmpty()) {
            List<ProductWSModel> mProductModelData = new ArrayList<ProductWSModel>(Arrays.asList(ITEMS));
            AppPresenter.getInstance().pushProductListIntoWarehouse(mProductModelData);
            // Clear the temp list
            mProductModelData.clear();
        }
    }
    // Properties
    private IPullRefreshCallback mIPullRefreshCallback;
    private ILoadMoreCallback mILoadMoreRefreshCallback;
    private IGetProductFromIdCallback mIGetProductFromIdCallback;
    // Temporary to limit the load more
    private static final int TIMES_TO_LOAD_MORE = 100;
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

    public IGetProductFromIdCallback getIGetProductFromIdCallback() {
        return mIGetProductFromIdCallback;
    }

    public void setIGetProductFromIdCallback(IGetProductFromIdCallback mIGetProductFromIdCallback) {
        this.mIGetProductFromIdCallback = mIGetProductFromIdCallback;
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
        Random random = new Random();
        List<ProductsLauncherGridItemData> productList = new ArrayList<ProductsLauncherGridItemData>();
        int countTest = 0;
        for (ProductWSModel productModelItem : ITEMS) {
            double price = random.nextInt(10000000)+100000;
            ProductsLauncherGridItemData item = new ProductsLauncherGridItemData(productModelItem.getProductId(), productModelItem.getProductName(), productModelItem.getProductDescription(),
                    productModelItem.getProductUrl(),getRandomHeightRatio(),price, AppConstants.Currency.VND);
            productList.add(item);
            ProductWSModel wsModel = new ProductWSModel(productModelItem.getProductId(), productModelItem.getProductName(), productModelItem.getProductDescription(), productModelItem.getProductUrl(),productModelItem.getCategoryId());
            AppPresenter.getInstance().pushProductIntoWarehouse(wsModel);
            countTest++;
            if(countTest>10)
                break;
        }
        return  productList;

    }

    @Override
    public List<ProductsLauncherGridItemData> searchProduct(String keyword) {
        Random random = new Random();
        List<ProductsLauncherGridItemData> productList = new ArrayList<ProductsLauncherGridItemData>();
        if(keyword.length() > 0) {
            for (ProductWSModel productModelItem : ITEMS) {
                if (productModelItem.match(keyword)) {
                    double price = random.nextInt(10000000)+100000;

                    ProductsLauncherGridItemData item = new ProductsLauncherGridItemData(productModelItem.getProductId(), productModelItem.getProductName(), productModelItem.getProductDescription(),
                            productModelItem.getProductUrl(),getRandomHeightRatio(),price, AppConstants.Currency.VND);
                    productList.add(item);
                }
            }
        }
        return  productList;
    }
    @Override
    public void getProductFromId(String productId){
        new GetProductDetailTask().execute(productId);
    }
    protected  List<String> createRandomImageUrlList(){
        List<String> imageUrls = new ArrayList<String>();
        Random random = new Random();
        int randInt = random.nextInt(ITEMS.length);
        randInt = Math.max(1, randInt);
        randInt = Math.min(randInt, CarouselIndicatorLayout.NUM_OF_CHILD_MAXIMUM);
        for(int index = 0; index < randInt; index ++){
            ProductWSModel productModel = ITEMS[index];
            imageUrls.add(productModel.getProductUrl());
        }
        return imageUrls;
    }
    public ProductDetailItemData findProductFromId(String productId) {
        ProductWSModel productModelItem = AppPresenter.getInstance().findProductInWarehouse(productId);
        if(productModelItem!=null){
            Random random = new Random();
            double price = random.nextInt(10000000)+100000;
            List<String> imageUrls = createRandomImageUrlList();
            ProductDetailItemData item = new ProductDetailItemData(productModelItem.getProductName(), productModelItem.getProductDescription(),
                    productModelItem.getProductUrl(),getRandomHeightRatio(),price, AppConstants.Currency.VND, imageUrls);
            return item;
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
    private class GetProductDetailTask extends AsyncTask<String, Void, ProductDetailItemData> {

        @Override
        protected ProductDetailItemData doInBackground(String... params) {

            if (isCancelled()) {
                return null;
            }

            // Simulates a background task
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }

            return findProductFromId(params[0].toString());
        }


        @Override
        protected void onPostExecute(ProductDetailItemData result) {
            super.onPostExecute(result);
            if(mIGetProductFromIdCallback != null){
                mIGetProductFromIdCallback.getProductFromIdSuccess(result);
            }
            // Reset the current page
            //mCurrentPage = 0;
        }

        @Override
        protected void onCancelled() {

        }
    }
    public static String getRandomUrl(){
        Random random = new Random();
        int index = random.nextInt(ITEMS.length);
        String url = ITEMS[index].getProductUrl();
        Log.d(TAG, "URL: " + url);
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
        int num = random.nextInt(10) + 100;
        Log.d(TAG, "createDumpDataForPullRefresh "+mRefreshCount +" with num = "+num);
//        if(num%2==0) {
//            num++;
//        }
        List<ProductsLauncherGridItemData> productList = new ArrayList<ProductsLauncherGridItemData>();
        if(mRefreshCount > TIMES_TO_LOAD_MORE) {
            Log.d(TAG, "createDumpDataForPullRefresh "+mRefreshCount +" with num = 0");
            return productList;
        }

        for(int index = 0; index < num; index ++) {
            String productId = String.valueOf(System.currentTimeMillis() + index);
            String pictureName = "New picture " + mPictureCount;//((mRefreshCount - 1) * num  + index + 1);
            mPictureCount++;
            int indexRan = random.nextInt(ITEMS.length);
            double price = random.nextInt(10000000)+100000;
            ProductWSModel productModelItem = ITEMS[indexRan];
            ProductsLauncherGridItemData item = new ProductsLauncherGridItemData(productId, pictureName, productModelItem.getProductDescription(),
                    productModelItem.getProductUrl(),getRandomHeightRatio(),price, AppConstants.Currency.VND);
            productList.add(0, item);
            // Add new item as test data
            ProductWSModel wsModel = new ProductWSModel(productId, pictureName, productModelItem.getProductDescription(), productModelItem.getProductUrl(),productModelItem.getCategoryId());
            AppPresenter.getInstance().pushProductIntoWarehouse(wsModel,0);
        }
        Log.d(TAG, "createDumpDataForPullRefresh "+mRefreshCount +" with Picture number = "+(mPictureCount-1));
        return productList;
    }

    /**
     * Create dump data for load more function
     * @return
     */
    private List<ProductsLauncherGridItemData> createDumpDataForLoadMore(){
        Log.d(TAG, "createDumpDataForLoadMore "+mCurrentPage);
        mCurrentPage ++;
        int num = ITEMS_PER_PAGE_LOAD_MORE;

        if(mCurrentPage <= TIMES_TO_LOAD_MORE/10){ // Load more 10 items
            List<ProductsLauncherGridItemData> productList = new ArrayList<ProductsLauncherGridItemData>();
            if(mCurrentPage == TIMES_TO_LOAD_MORE - 1){
                num = ITEMS_PER_PAGE_LOAD_MORE - 1;
            }
            for(int index = 0; index < num; index ++) {
                String productId = String.valueOf(System.currentTimeMillis() + index);
                String pictureName = "Old Picture " + ((mCurrentPage - 1) * num  + index + 1);
                Random random = new Random();
                int indexRan = random.nextInt(ITEMS.length);
                double price = random.nextInt(10000000)+100000;
                ProductWSModel productModelItem = ITEMS[indexRan];
                ProductsLauncherGridItemData item = new ProductsLauncherGridItemData(productId, pictureName, productModelItem.getProductDescription(),
                        productModelItem.getProductUrl(),getRandomHeightRatio(),price, AppConstants.Currency.VND);
                productList.add(item);

                // Add new item as test data
                ProductWSModel wsModel = new ProductWSModel(productId, pictureName, productModelItem.getProductDescription(), productModelItem.getProductUrl(),productModelItem.getCategoryId());
                AppPresenter.getInstance().pushProductIntoWarehouse(wsModel);
            }
            return productList;
        }

        return null;
    }
}
