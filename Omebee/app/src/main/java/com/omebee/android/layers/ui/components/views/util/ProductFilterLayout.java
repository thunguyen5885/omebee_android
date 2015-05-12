package com.omebee.android.layers.ui.components.views.util;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.omebee.android.R;
import com.omebee.android.global.AppPresenter;
import com.omebee.android.layers.services.models.CategoryWSModel;
import com.omebee.android.layers.ui.ProductsLauncherActivity;
import com.omebee.android.layers.ui.components.adapters.SimpleSpinnerAdapter;
import com.omebee.android.layers.ui.components.data.FilterItemData;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ThuNguyen on 5/7/2015.
 */
public class ProductFilterLayout extends FrameLayout{
    public interface IOnLayoutCloseListener{
        void onClose();
    }
    // For control, view
    private Spinner mNewInSpinner;
    private RadioButton mRbSaleOffYes;
    private RadioButton mRbSaleOffNo;
    private FrameLayout mPriceRangeSeekbarOuterLayout;
    private RangeSeekBarLayout mPriceRangeSeekBarLayout;

    // For object, data
    private FilterItemData mFilterItemData;
    private IOnLayoutCloseListener mOnLayoutCloseListener;
    // Define for some view has been clicked before
    // To catch not permit 2 views clicked at the same time
    private boolean mIsViewClicked = false;
    private ProductsLauncherActivity.IFilterActionCallback mFilterActionCallback;

    public ProductFilterLayout(Context context) {
        super(context);
        init(context);
    }

    public ProductFilterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProductFilterLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * Initialize all the views on product_filter_layout
     * @param context
     */
    private void init(Context context){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.ctrl_product_filter, this);

        mNewInSpinner = (Spinner)view.findViewById(R.id.spNewInFilter);
        mRbSaleOffYes = (RadioButton)view.findViewById(R.id.rbSaleOffFilterYes);
        mRbSaleOffNo = (RadioButton)view.findViewById(R.id.rbSaleOffFilterNo);
        mPriceRangeSeekbarOuterLayout = (FrameLayout)view.findViewById(R.id.flPriceFilter);

        View applyView = view.findViewById(R.id.flApplyFilter);
        applyView.setOnClickListener(mOnClickListener);
        View clearAllView = view.findViewById(R.id.flClearAllFilter);
        clearAllView.setOnClickListener(mOnClickListener);
        View mainView = view.findViewById(R.id.rlMainProductFilter);
        mainView.setOnClickListener(mOnClickListener);
        View rootView = view.findViewById(R.id.flProductFilterRoot);
        rootView.setOnClickListener(mOnClickListener);
    }

    /**
     * Begin updating UI
     * @param filterItemData
     */
    public void updateUIFromData(FilterItemData filterItemData){
        if(filterItemData != null) {
            // Set object first
            setFilterItemData(filterItemData);

            // Get the new_in list from resource
            List<String> newInList = Arrays.asList(getContext().getResources().getStringArray(R.array.new_in_list));
            SimpleSpinnerAdapter<String> categorySpinnerAdapter = new SimpleSpinnerAdapter<String>(getContext(), newInList);
            mNewInSpinner.setAdapter(categorySpinnerAdapter);
            mNewInSpinner.setOnItemSelectedListener(mCategoryItemSelectedListener);

            // Update price range seek bar
            mPriceRangeSeekBarLayout = new RangeSeekBarLayout(filterItemData.getPriceRange()[0], filterItemData.getPriceRange()[1], getContext());
            mPriceRangeSeekbarOuterLayout.addView(mPriceRangeSeekBarLayout);

            // Update saleoff state
            if (filterItemData.isSaleOff()) {
                mRbSaleOffYes.setChecked(true);
            } else {
                mRbSaleOffNo.setChecked(true);
            }
        }
    }
    public void setFilterActionCallback(ProductsLauncherActivity.IFilterActionCallback filterActionCallback) {
        this.mFilterActionCallback = filterActionCallback;
    }

    public void setFilterItemData(FilterItemData filterItemData) {
        this.mFilterItemData = filterItemData;
    }

    public void setOnLayoutCloseListener(IOnLayoutCloseListener onLayoutCloseListener) {
        this.mOnLayoutCloseListener = onLayoutCloseListener;
    }

    // Listener, callback when user select an item on category spinner
    private AdapterView.OnItemSelectedListener mCategoryItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mNewInSpinner.setSelection(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    // Catch all the clicks of the buttons on layout
    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mIsViewClicked){
                return;
            }
            mIsViewClicked = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Reset
                    mIsViewClicked = false;
                }
            }, 500);
            if(v.getId() != R.id.rlMainProductFilter) { // Click on main view, not consume
                if (mOnLayoutCloseListener != null) {
                    mOnLayoutCloseListener.onClose();
                }
                switch (v.getId()) {
                    case R.id.flApplyFilter:
                        if (mFilterActionCallback != null) {
                            mFilterActionCallback.onApply(mFilterItemData);
                        }
                        break;
                    case R.id.flClearAllFilter:
                        if (mFilterActionCallback != null) {
                            mFilterActionCallback.onClearAll();
                        }
                        break;
                    case R.id.flProductFilterRoot:
                        break;
                }
            }
        }
    };
}
