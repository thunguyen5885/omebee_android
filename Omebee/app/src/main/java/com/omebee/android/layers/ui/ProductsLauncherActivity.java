package com.omebee.android.layers.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseActivity;
import com.omebee.android.layers.ui.components.data.FilterItemData;
import com.omebee.android.layers.ui.components.preference.MySharePreference;
import com.omebee.android.layers.ui.fragments.ProductsFilterFragment;
import com.omebee.android.layers.ui.fragments.ProductsSearchFragment;
import com.omebee.android.layers.ui.fragments.ProductsLauncherFragment;
import com.omebee.android.layers.ui.presenters.ProductsFilterPresenterImpl;
import com.omebee.android.layers.ui.presenters.ProductsSearchPresenterImpl;
import com.omebee.android.layers.ui.presenters.ProductsLauncherPresenterImpl;
import com.omebee.android.layers.ui.views.IProductSearchView;
import com.omebee.android.layers.ui.views.IProductsFilterView;
import com.omebee.android.layers.ui.views.IProductsLauncherView;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.utils.DialogUtil;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherActivity extends BaseActivity implements IProductsLauncherView, IProductsFilterView, IProductSearchView, SearchView.OnQueryTextListener{
    private static final String TAG = "ProductsLauncherActivity";

    // Define interface for callback from filter dialog
    public interface IFilterActionCallback{
        void onApply(FilterItemData filterItemData);
        void onClearAll();
    }
    private ProductsLauncherFragment mProductsLauncherFragment;
    private ProductsSearchFragment mProductsSearchFragment;
    private ProductsFilterFragment mProductsFilterFragment;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuTitles;

    private SearchView mSearchView;

    private FilterItemData mFilterItemData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_products_launcher);

        initUIComponent();

        // Default to load product launcher fragment
        showProductsLauncherFragment();
    }

    /**
     * Initialize the product launcher fragment
     */
    private void initProductLauncherFragment(){
        if(mProductsLauncherFragment == null || mProductsLauncherFragment.isDetached()){
            mProductsLauncherFragment = new ProductsLauncherFragment();
            ProductsLauncherPresenterImpl productsLauncherPresenter = new ProductsLauncherPresenterImpl(this);
            mProductsLauncherFragment.setPresenter(productsLauncherPresenter);
        }
    }
    /**
     * Initialize the product search fragment
     */
    private void initProductSearchFragment(){
        if(mProductsSearchFragment == null || mProductsSearchFragment.isDetached()){
            mProductsSearchFragment = new ProductsSearchFragment();
            ProductsSearchPresenterImpl productsSearchPresenter = new ProductsSearchPresenterImpl(this);
            mProductsSearchFragment.setPresenter(productsSearchPresenter);
        }
    }
    /**
     * Initialize the product filter fragment
     */
    private void initProductFilterFragment(){
        if(mProductsFilterFragment == null || mProductsFilterFragment.isDetached()){
            mProductsFilterFragment = new ProductsFilterFragment();
            ProductsFilterPresenterImpl productsFilterPresenter = new ProductsFilterPresenterImpl(this);
            mProductsFilterFragment.setPresenter(productsFilterPresenter);
        }
    }
    /**
     * Initialize the UI component such as DrawerLayout, action bar,...
     */
    private void initUIComponent(){
        mTitle = mDrawerTitle = getTitle();
        mMenuTitles = getResources().getStringArray(R.array.product_menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.ctrl_navigation_drawer_list_item, mMenuTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        // Always hidden title
        getActionBar().setTitle("");

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
//        mDrawerToggle = new ActionBarDrawerToggle(
//                this,                  /* host Activity */
//                mDrawerLayout,         /* DrawerLayout object */
//                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
//                R.string.drawer_open,  /* "open drawer" description for accessibility */
//                R.string.drawer_close  /* "close drawer" description for accessibility */
//        );
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                drawerArrow,
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ){

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }
    protected void showProductsLauncherFragment(){
        // Init first
        initProductLauncherFragment();
        showFragment(mProductsLauncherFragment, R.id.product_launcher_frame);
    }
    protected void showProductsFilterFragment(){
        initProductFilterFragment();
        showFragment(mProductsFilterFragment, R.id.product_launcher_frame);
    }
    protected void showProductsSearchFragment(){
        // Init first
        initProductSearchFragment();
        showFragment(mProductsSearchFragment, R.id.product_launcher_frame);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.abmenu_products_launcher, menu);
        // Init filter menuitem
        MenuItem filterItem = menu.findItem(R.id.menu_filter);
        // Init search menuitem
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem, filterItem);


        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action itsible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.menu_search:
                // Close drawer
                mDrawerLayout.closeDrawers();
                // Go to search screen
                showProductsSearchFragment();
                break;
            case R.id.menu_filter:
                // Show dialog filter to user
                processAndShowFilterDialog();
                break;
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                    mDrawerLayout.openDrawer(mDrawerList);
                }
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSearchProducts(List<ProductsLauncherGridItemData> productList) {
        mProductsSearchFragment.showProducts(productList);
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position == 0){ // The category item
                // Go to categories screen
                Intent intent = new Intent(ProductsLauncherActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        }
    }

    private void onItemSelectedFromDrawerMenu(int position) {
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
//        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    @Override
    public void displayProductName(String name) {
        mProductsLauncherFragment.displayProductName(name);
    }

    @Override
    public void showProducts(List<ProductsLauncherGridItemData> productList) {
        mProductsLauncherFragment.setProductList(productList);
    }

    @Override
    public void showFilterProducts(List<ProductsLauncherGridItemData> productList) {
        mProductsFilterFragment.setProductList(productList);
    }

    @Override
    public void loadMoreProductsComplete(List<ProductsLauncherGridItemData> productList, boolean isEndOfList) {
        mProductsLauncherFragment.loadMoreComplete(productList, isEndOfList);
    }

    @Override
    public void pullRefreshProductsComplete(List<ProductsLauncherGridItemData> productList) {
        mProductsLauncherFragment.pullRefreshComplete(productList);
    }
    private void processAndShowFilterDialog(){
        // Check user choose filter before
        if(MySharePreference.isFilterItemStore(this)){
            mFilterItemData = new FilterItemData(MySharePreference.getFilterItemInfo(this));
        }else{
            mFilterItemData = new FilterItemData();
            double[] priceRange = new double[2];
            priceRange[0] = 10;
            priceRange[1] = 1000;
            mFilterItemData.initPriceRange(priceRange);

        }
        DialogUtil.showFilterDialog(ProductsLauncherActivity.this, mOnFilterActionCallback, mFilterItemData);
    }
    /**
     * Setup the search view from menuitem
     * @param searchItem
     * @param filterItem
     */
    private void setupSearchView(MenuItem searchItem, final MenuItem filterItem) {

        // Setting the textview default behaviour properties
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) mSearchView.findViewById(id);
        textView.setTextColor(getResources().getColor(R.color.black));

        // search hint
        mSearchView.setQueryHint(getString(R.string.search_hint));

        mSearchView.setOnQueryTextListener(this);
        // When using the support library, the setOnActionExpandListener() method is
        // static and accepts the MenuItem object as an argument
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Show filter menuitem
                filterItem.setVisible(true);
                // Reset search fragment
                mProductsSearchFragment.reset();
                onBackPressed();
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Hide filter menuitem
                filterItem.setVisible(false);
                return true;  // Return true to expand action view
            }
        });

    }

    @Override
    public boolean onQueryTextChange(String newText) { // Autocomplete
        if(newText.trim().length() > 0) {
            mProductsSearchFragment.search(newText.trim());
        }
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query.trim().length() > 0) {
            mProductsSearchFragment.search(query.trim());
        }
        return true;
    }

    // Instance an interface for filter callback
    private IFilterActionCallback mOnFilterActionCallback = new IFilterActionCallback() {
        @Override
        public void onApply(final FilterItemData filterItemData) {
            // Store filter_item_data object on preference
            MySharePreference.storeFilterItemInfo(ProductsLauncherActivity.this, filterItemData.toString());

            showProductsFilterFragment();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Begin to get filter data
                    mProductsFilterFragment.filterProducts(filterItemData);
                }
            }, 500);

        }

        @Override
        public void onClearAll() {
            // Clear filter_item_data object on preference
            MySharePreference.clearFilterItemInfo(ProductsLauncherActivity.this);

            // Remove filter fragment if any
            removeFragment(mProductsFilterFragment);
        }
    };
}
