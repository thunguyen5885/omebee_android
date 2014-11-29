package com.omebee.android.layers.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.SearchView;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseActivity;
import com.omebee.android.layers.ui.components.data.CategoryItemData;
import com.omebee.android.layers.ui.fragments.CategoriesFragment;
import com.omebee.android.layers.ui.fragments.SubCategoriesFragment;
import com.omebee.android.layers.ui.presenters.CategoriesPresenterImpl;
import com.omebee.android.layers.ui.presenters.SubCategoriesPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.ICategoriesPresenter;
import com.omebee.android.layers.ui.presenters.base.ISubCategoriesPresenter;
import com.omebee.android.layers.ui.views.ICategoriesView;
import com.omebee.android.layers.ui.views.ISubCategoriesView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ThuNguyen on 10/25/2014.
 */
public class CategoriesActivity extends BaseActivity implements ICategoriesView, ISubCategoriesView, SearchView.OnQueryTextListener{
    private CategoriesFragment mCategoriesFragment;
    private SubCategoriesFragment mSubCategoriesFragment;
    private ICategoriesPresenter mCategoryPresenter;
    private SearchView mSearchView;
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
        showFragment(mCategoriesFragment);

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
        mSearchView.setQueryHint(getString(R.string.search_category));

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
            if(mSubCategoriesFragment == null){
                mSubCategoriesFragment = new SubCategoriesFragment();
                mSubCategoriesFragment.setKeywordSearch(newText);
                ISubCategoriesPresenter subCategoriesPresenter = new SubCategoriesPresenterImpl(this);
                mSubCategoriesFragment.setPresenter(subCategoriesPresenter);
            }else{
                mSubCategoriesFragment.processSearch(newText);
            }
            showFragment(mSubCategoriesFragment);
        }else{
            showFragment(mCategoriesFragment);
        }
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
//        if(query.trim().length() > 0) {
//            mSubCategoriesFragment.processSearch(query.trim());
//        }else{
//            showFragment(mCategoriesFragment);
//        }
        return true;
    }
    private void showFragment(Fragment frag) {
        FragmentManager fragmentManager = getFragmentManager();
        String backStateName = frag.getClass().getName();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment curFragment = fragmentManager.findFragmentById(R.id.flCategoryLayout);
        //if(curFragment)
        fragmentTransaction.replace(R.id.flCategoryLayout, frag);
//        boolean fragmentPopped = fragmentManager.popBackStackImmediate (backStateName, 0);
//
//        if (!fragmentPopped && fragmentManager.findFragmentByTag(backStateName) == null) { // fragment not in back stack, create it.
//            fragmentTransaction.replace(R.id.flCategoryLayout, frag, backStateName);
//            fragmentTransaction.addToBackStack(backStateName);
//        } else {
//            Fragment currFrag = (Fragment)fragmentManager.findFragmentByTag(backStateName);
//            fragmentTransaction.show(currFrag);
//        }

        fragmentTransaction.commit();
    }

    @Override
    public void showCategories(List<CategoryItemData> categoriesList) {
        mCategoriesFragment.showCategories(categoriesList);
    }

    @Override
    public void showSubCategories(HashMap<Integer, CategoryItemData> categoriesMap, HashMap<Integer, List<CategoryItemData>> subCategoryMap) {
        mSubCategoriesFragment.showSubCategories(categoriesMap, subCategoryMap);
    }
}
