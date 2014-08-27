package com.omebee.android.layers.ui.components.views.pullrefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.omebee.android.R;

/**
 * @author Thu Nguyen
 * Class is to create and manage the multi column layout and
 * multisize layout for each item
 */
public class ScrollViewMultiColumns extends ScrollView implements MultiColumnsAdapter.IViewTransmission{
	protected static final String TAG = "ScrollViewMultiColumns";
	protected LayoutInflater mInflater;
    protected LinearLayout mRootLayout;

    private MultiColumnsAdapter mAdapter;
    // Variable to set the number of column
    private int mColumnNum = 1;
    private int mNextPositionToAdd = 0;
	public ScrollViewMultiColumns(Context context) {
		super(context);
		init(context);
	}

	public ScrollViewMultiColumns(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ScrollViewMultiColumns(Context context, AttributeSet attrs,
                                  int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

    public int getColumnNum() {
        return mColumnNum;
    }

    public void setColumnNum(int columnNum) {
        if(columnNum <= 0){
            throw new IllegalArgumentException("Number of columns must be greater than 0");
        }
        this.mColumnNum = columnNum;
        // Clear all child views
        if(mRootLayout.getChildCount() > 0){
            mRootLayout.removeAllViews();
        }
        // Add items here
        for(int index = 0; index < mColumnNum; index++){
            LinearLayout itemLayout = new LinearLayout(this.getContext());
            itemLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams itemLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            itemLayoutParams.weight = 1;
            itemLayout.setLayoutParams(itemLayoutParams);
            mRootLayout.addView(itemLayout);
        }
    }

    public MultiColumnsAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(MultiColumnsAdapter adapter) {
        this.mAdapter = adapter;
        adapter.setIViewTransmission(this);
        adapter.launchData();

        // Refresh layout
        mRootLayout.invalidate();
    }

    protected void init(Context context) {
        // Create root layout here (it is child of scroll layout)
        mRootLayout = new LinearLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRootLayout.setLayoutParams(params);
        mRootLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.addView(mRootLayout);
	}

    @Override
    public void addItemToFirst(View view, int position) {

    }

    @Override
    public void addItemToLast(View view, int position) {
        LinearLayout itemLayout = (LinearLayout)mRootLayout.getChildAt(mNextPositionToAdd);
        itemLayout.addView(view);
        mNextPositionToAdd++;
        mNextPositionToAdd = mNextPositionToAdd % mColumnNum;
    }

    @Override
    public void onFinished() {
        mRootLayout.invalidate();
    }

}
