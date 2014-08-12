package com.omebee.android.layers.ui;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.SearchView;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseActivity;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.fragments.SearchFragment;
import com.omebee.android.layers.ui.presenters.SearchPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.ISearchPresenter;
import com.omebee.android.layers.ui.views.ISearchView;

import java.util.List;

/**
 * Created by Thu Nguyen on 8/12/2014.
 */
public class SearchActivity extends BaseActivity implements ISearchView, SearchView.OnQueryTextListener{
    private SearchFragment mSearchFragment;
    private ISearchPresenter mSearchPresenter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.search);
        mSearchPresenter = new SearchPresenterImpl(this);
        // Initialize the fragment layout
        mSearchFragment = (SearchFragment) getFragmentManager().findFragmentById(R.id.searchFragment);
        if(mSearchFragment != null){
            mSearchFragment.setPresenter(mSearchPresenter);
        }
        // Always hidden title
        getActionBar().setTitle("");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.abmenu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void showProducts(List<ProductsLauncherGridItemData> productList) {
        mSearchFragment.showProducts(productList);
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
        searchItem.expandActionView();
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
                // Do something when collapsed
                finish();
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
        Log.d("ThuNguyen", "Text change: " + newText);
        mSearchFragment.search(newText.trim());
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("ThuNguyen", "Query Text Submit: " + query);
        mSearchFragment.search(query.trim());
        return true;
    }

    protected boolean isAlwaysExpanded() {
        return false;
    }
}
