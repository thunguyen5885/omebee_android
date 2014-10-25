package com.omebee.android.layers.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseActivity;
import com.omebee.android.layers.ui.fragments.ProductsLauncherFragment;
import com.omebee.android.layers.ui.fragments.SearchProductFragment;
import com.omebee.android.layers.ui.presenters.ProductSearchPresenterImpl;
import com.omebee.android.layers.ui.presenters.ProductsLauncherPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.IProductSearchPresenter;
import com.omebee.android.layers.ui.presenters.base.IProductsLauncherPresenter;
import com.omebee.android.layers.ui.views.IProductSearchView;
import com.omebee.android.layers.ui.views.IProductsLauncherView;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

/**
 * Created by phan on 8/6/2014.
 */
public class ProductsLauncherActivity extends BaseActivity implements IProductsLauncherView, IProductSearchView, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener{
    private static final String TAG = "ProductsLauncherActivity";
    private ProductsLauncherPresenterImpl mProductsLauncherPresenter;
    private ProductsLauncherFragment mProductsLauncherFragment;
    private SearchProductFragment mSearchFragment;
    private IProductSearchPresenter mSearchPresenter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuTitles;

    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_products_launcher);

        // Initialize the product launcher fragment
        mProductsLauncherPresenter = new ProductsLauncherPresenterImpl(this);
        mProductsLauncherFragment = (ProductsLauncherFragment) getFragmentManager().findFragmentById(R.id.productsLauncherFragment);
        if(mProductsLauncherFragment != null) {
            mProductsLauncherFragment.setPresenter(mProductsLauncherPresenter);
        }
        // Initialize the search fragment
        mSearchFragment = (SearchProductFragment) getFragmentManager().findFragmentById(R.id.searchFragment);
        if(mSearchFragment != null) {
            mSearchPresenter = new ProductSearchPresenterImpl(this);
        }
        mSearchFragment.setPresenter(mSearchPresenter);
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

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
        // Default to load product launcher fragment
        showHomeFragment(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.abmenu_products_launcher, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);
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
                showHomeFragment(false);
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSearchProducts(List<ProductsLauncherGridItemData> productList) {
        mSearchFragment.showProducts(productList);
    }


    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //selectItem(position);
            if(position == 0){ // The category item
                // Go to categories screen
                Intent intent = new Intent(ProductsLauncherActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        }
    }

    private void selectItem(int position) {
//
//        // update selected item and title, then close the drawer
        mProductsLauncherFragment.selectItem(position);
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
//        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        // Pass any configuration change to the drawer toggls
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }


    public IProductsLauncherPresenter getProductsLauncherPresenter() {
        return mProductsLauncherPresenter;
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
    public void loadMoreProductsComplete(List<ProductsLauncherGridItemData> productList, boolean isEndOfList) {
        mProductsLauncherFragment.loadMoreComplete(productList, isEndOfList);
    }

    @Override
    public void pullRefreshProductsComplete(List<ProductsLauncherGridItemData> productList) {
        mProductsLauncherFragment.pullRefreshComplete(productList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mProductsLauncherPresenter.onItemClicked(position);
    }

    private void setupSearchView(MenuItem searchItem) {

        if (isAlwaysExpanded()) {
            mSearchView.setIconifiedByDefault(false);
        } else {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }
        // search hint
        mSearchView.setQueryHint(getString(R.string.search_hint));
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        if (searchManager != null) {
//            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
//
//            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
//            for (SearchableInfo inf : searchables) {
//                if (inf.getSuggestAuthority() != null
//                        && inf.getSuggestAuthority().startsWith("applications")) {
//                    info = inf;
//                }
//            }
//            mSearchView.setSearchableInfo(info);
//        }

        mSearchView.setOnQueryTextListener(this);
        // When using the support library, the setOnActionExpandListener() method is
        // static and accepts the MenuItem object as an argument
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Reset search fragment
                mSearchFragment.reset();
                // Come back
                showHomeFragment(true);
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });

    }
    @Override
    public boolean onQueryTextChange(String newText) { // Autocomplete
        Log.d(TAG, "Text change: " + newText);
        if(newText.trim().length() > 0) {
            mSearchFragment.search(newText.trim());
        }
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "Query Text Submit: " + query);
        if(query.trim().length() > 0) {
            mSearchFragment.search(query.trim());
        }
        return true;
    }

    protected boolean isAlwaysExpanded() {
        return false;
    }

    /**
     * Change to other fragment
     * @param isHomeFragment
     */
    protected void showHomeFragment(boolean isHomeFragment){
        if(isHomeFragment){
            mProductsLauncherFragment.getView().setVisibility(View.VISIBLE);
            mSearchFragment.getView().setVisibility(View.GONE);
        }else{
            mProductsLauncherFragment.getView().setVisibility(View.GONE);
            mSearchFragment.getView().setVisibility(View.VISIBLE);
        }
    }
}
