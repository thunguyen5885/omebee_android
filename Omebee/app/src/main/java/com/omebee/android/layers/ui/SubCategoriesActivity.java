package com.omebee.android.layers.ui;

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
import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.fragments.SubCategoriesFragment;
import com.omebee.android.layers.ui.presenters.SubCategoriesPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.ISubCategoriesPresenter;
import com.omebee.android.layers.ui.views.ISubCategoriesView;
import com.omebee.android.utils.AppConstants;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ThuNguyen on 10/25/2014.
 */
public class SubCategoriesActivity extends BaseActivity implements ISubCategoriesView, SearchView.OnQueryTextListener{
    private SubCategoriesFragment mSubCategoriesFragment;
    private ISubCategoriesPresenter mSubCategoryPresenter;
    private SearchView mSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_sub_categories);
        getActionBar().setTitle(R.string.categories_text);

        // Initialize fragment object
        mSubCategoriesFragment = (SubCategoriesFragment) getFragmentManager().findFragmentById(R.id.subCategoriesFragment);
        mSubCategoryPresenter = new SubCategoriesPresenterImpl(this);
        mSubCategoriesFragment.setPresenter(mSubCategoryPresenter);

        // Initialize the get extra
        String categoryId = getIntent().getStringExtra(AppConstants.KEY_CATEGORY_ID);
        mSubCategoriesFragment.setParentCategoryId(categoryId);

        // Translate animation
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
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
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.menu_search:
                // Process when click search icon
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupSearchView(MenuItem searchItem) {
        searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        // search hint
        mSearchView.setQueryHint(getString(R.string.search_hint));

        mSearchView.setOnQueryTextListener(this);
        // When using the support library, the setOnActionExpandListener() method is
        // static and accepts the MenuItem object as an argument
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Reset search fragment, means show all categories
                //mSubCategoriesFragment.processSearch("");
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
        if(newText.trim().length() > 0) {
            mSubCategoriesFragment.processSearch(newText.trim());
        }
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query.trim().length() > 0) {
            mSubCategoriesFragment.processSearch(query.trim());
        }
        return true;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mSubCategoriesFragment.updateGroupIndicatorBound();
    }

    @Override
    public void showSubCategories(HashMap<Integer, CategoryItemData> categoriesMap, HashMap<Integer, List<CategoryItemData>> subCategoryMap) {
        mSubCategoriesFragment.showSubCategories(categoriesMap, subCategoryMap);
    }
}
