package com.omebee.android.layers.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseActivity;
import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.fragments.CategoriesFragment;
import com.omebee.android.layers.ui.presenters.CategoriesPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.ICategoriesPresenter;
import com.omebee.android.layers.ui.views.ICategoriesView;
import com.omebee.android.utils.AppConstants;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ThuNguyen on 10/25/2014.
 */
public class CategoriesActivity extends BaseActivity implements ICategoriesView, SearchView.OnQueryTextListener{
    private CategoriesFragment mCategoriesFragment;
    private ICategoriesPresenter mCategoryPresenter;
    private SearchView mSearchView;
    private MenuItem mMenuItemSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_categories);
        getActionBar().setTitle(R.string.categories_text);
        // Initialize fragment object
        mCategoriesFragment = new CategoriesFragment();
        mCategoryPresenter = new CategoriesPresenterImpl(this);
        mCategoriesFragment.setPresenter(mCategoryPresenter);

        // Default show the categories fragment
        showFragment(mCategoriesFragment, R.id.flCategoryLayout);

        // Translate animation
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.abmenu_category, menu);
        mMenuItemSearch = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) mMenuItemSearch.getActionView();
        setupSearchView();
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
    private void setupSearchView() {
        mMenuItemSearch.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        // Setting the textview default behaviour properties
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) mSearchView.findViewById(id);
        textView.setTextColor(getResources().getColor(R.color.black));

        // search hint
        mSearchView.setQueryHint(getString(R.string.search_hint));

        mSearchView.setOnQueryTextListener(this);
        // When using the support library, the setOnActionExpandListener() method is
        // static and accepts the MenuItem object as an argument
        MenuItemCompat.setOnActionExpandListener(mMenuItemSearch, new MenuItemCompat.OnActionExpandListener() {
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

    /**
     * Reset the search view as: clear text, iconified
     */
    private void resetSearchView(){
        MenuItemCompat.collapseActionView(mMenuItemSearch);
        mSearchView.clearFocus();
    }
    @Override
    public boolean onQueryTextChange(String newText) { // Autocomplete
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query.trim().length() > 0) {
            // Reset search view before
            resetSearchView();
            // Go to sub category screen
            Intent intent = new Intent(this, SubCategoriesActivity.class);
            intent.putExtra(AppConstants.KEY_SEARCH_KEYWORD, query);
            startActivity(intent);
        }
        return true;
    }


    @Override
    public void showCategories(List<CategoryItemData> categoriesList) {
        mCategoriesFragment.showCategories(categoriesList);
    }
}
