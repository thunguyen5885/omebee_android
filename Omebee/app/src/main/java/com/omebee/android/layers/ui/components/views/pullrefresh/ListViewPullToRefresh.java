package com.omebee.android.layers.ui.components.views.pullrefresh;

import com.omebee.android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;



public class ListViewPullToRefresh extends ListView implements OnScrollListener {

	private static final int TAP_TO_REFRESH = 1;
	private static final int PULL_TO_REFRESH = 2;
	private static final int RELEASE_TO_REFRESH = 3;
	protected static final int REFRESHING = 4;

	protected static final String TAG = "PullToRefreshGridView";

	private OnRefreshListener mOnRefreshListener;

	/**
	 * Listener that will receive notifications every time the list scrolls.
	 */
	private OnScrollListener mOnScrollListener;
	protected LayoutInflater mInflater;

	// header
	private RelativeLayout mRefreshView;

	private ProgressBar mRefreshViewProgress;

	protected int mCurrentScrollState;
	//protected int mRefreshState;

	private int mRefreshViewHeight;
	private int mRefreshOriginalBottomPadding;
	private int mLastMotionY;

	private boolean mBounceHack;

	public ListViewPullToRefresh(Context context) {
		super(context);
		init(context);
	}

	public ListViewPullToRefresh(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ListViewPullToRefresh(Context context, AttributeSet attrs,
                                 int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	protected void init(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// header
		mRefreshView = (RelativeLayout) mInflater.inflate(
				R.layout.ctrl_header_pull_to_refresh, null, false);

		mRefreshViewProgress = (ProgressBar) mRefreshView
				.findViewById(R.id.pull_to_refresh_progress);

		//mRefreshView.setOnClickListener(new OnClickRefreshListener());

        mRefreshOriginalBottomPadding = mRefreshView.getPaddingBottom();

		//mRefreshState = TAP_TO_REFRESH;

		addHeaderView(mRefreshView);

		super.setOnScrollListener(this);

		measureView(mRefreshView);
		mRefreshViewHeight = mRefreshView.getMeasuredHeight();

	}

	@Override
	protected void onAttachedToWindow() {
		//have to ask super to attach to window, otherwise it won't scroll in jelly bean.
		super.onAttachedToWindow();
		setSelection(1);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);

		setSelection(1);
	}

	/**
	 * Set the listener that will receive notifications every time the list
	 * scrolls.
	 * 
	 * @param l
	 *            The scroll listener.
	 */
	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mOnScrollListener = l;
	}

	/**
	 * Register a callback to be invoked when this list should be refreshed.
	 * 
	 * @param onRefreshListener
	 *            The callback to run.
	 */
	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		mOnRefreshListener = onRefreshListener;
	}

	/**
	 * Set a text to represent when the list was last updated.
	 * 
	 * @param lastUpdated
	 *            Last updated at.
	 */
	public void setLastUpdated(CharSequence lastUpdated) {

	}

    private Animation createTranslateAnimation(int fromY){
        Animation translateAnimation = new TranslateAnimation(0, 0, fromY, 0);
        translateAnimation.setDuration(350);
        return translateAnimation;
    }
	@Override
	public boolean onTouchEvent(MotionEvent event) {
        final int y = (int) event.getY();
        mBounceHack = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (!isVerticalScrollBarEnabled()) {
                    setVerticalScrollBarEnabled(true);
                }
                if (getFirstVisiblePosition() == 0) {
                    if ((mRefreshView.getBottom() >= mRefreshViewHeight
                            || mRefreshView.getTop() >= 0)) {
                        // Initiate the refresh
                        Log.d(TAG, "prepareForRefresh");
                        prepareForRefresh();
                        this.clearAnimation();
                        Animation translateAnim = createTranslateAnimation(mRefreshView.getBottom());
                        this.startAnimation(translateAnim);
                        //onRefresh();
                    } else if (mRefreshView.getBottom() < mRefreshViewHeight
                            || mRefreshView.getTop() <= 0) {
                        // Abort refresh and scroll down below the refresh view
                        Log.d(TAG, "resetHeader");
                        resetHeader();
                        setSelection(1);
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                applyHeaderPadding(event);
                break;
        }
        return super.onTouchEvent(event);
	}

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // When the refresh view is completely visible, change the text to say
        // "Release to refresh..." and flip the arrow drawable.
        if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL) {
            Log.d(TAG, "SCROLL_STATE_TOUCH_SCROLL");
            if (firstVisibleItem == 0) {
                mRefreshViewProgress.setVisibility(View.VISIBLE);
                Log.d(TAG, "SCROLL_STATE_TOUCH_SCROLL 123");
            } else {
                Log.d(TAG, "SCROLL_STATE_TOUCH_SCROLL 6789");
                resetHeader();
            }
        } else if (mCurrentScrollState == SCROLL_STATE_FLING && firstVisibleItem == 0) {
            Log.d(TAG, "SCROLL_STATE_FLING 123");
            setSelection(1);
            mBounceHack = true;
        } else if (mBounceHack && mCurrentScrollState == SCROLL_STATE_FLING) {
            Log.d(TAG, "SCROLL_STATE_FLING 456");
            setSelection(1);
        }

        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem,
                    visibleItemCount, totalItemCount);
        }
    }

	private void applyHeaderPadding(MotionEvent ev) {
		// getHistorySize has been available since API 1
		int pointerCount = ev.getHistorySize();

		for (int p = 0; p < pointerCount; p++) {
			//if (mRefreshState == RELEASE_TO_REFRESH) {
				if (isVerticalFadingEdgeEnabled()) {
					setVerticalScrollBarEnabled(false);
				}

				int historicalY = (int) ev.getHistoricalY(p);

				// Calculate the padding to apply, we divide by 1.7 to
				// simulate a more resistant effect during pull.
				int bottomPadding = (int) (((historicalY - mLastMotionY) - mRefreshViewHeight) / 1.7);

                Log.d("ThuNguyen", "bottomPadding: " + bottomPadding);
				mRefreshView.setPadding(mRefreshView.getPaddingLeft(),
                        mRefreshView.getPaddingTop(), mRefreshView.getPaddingRight(),
                        bottomPadding);
                //mRefreshView.invalidate();
			//}
		}
	}

	/**
	 * Sets the header padding back to original size.
	 */
	private void resetHeaderPadding() {
		mRefreshView.setPadding(mRefreshView.getPaddingLeft(),
                mRefreshView.getPaddingTop(), mRefreshView.getPaddingRight(),
                mRefreshOriginalBottomPadding);

	}

	/**
	 * Resets the header to the original state.
	 */
	private void resetHeader() {
			resetHeaderPadding();

			// Set refresh view text to the pull label
			mRefreshViewProgress.setVisibility(View.GONE);

	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mCurrentScrollState = scrollState;

		if (mCurrentScrollState == SCROLL_STATE_IDLE) {
			mBounceHack = false;
		}

		if (mOnScrollListener != null) {
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	public void prepareForRefresh() {
		resetHeaderPadding();
		//mRefreshViewProgress.setVisibility(View.GONE);
		// Set refresh view text to the refreshing label
	}

	public void onRefresh() {
		Log.d(TAG, "onRefresh");

		if (mOnRefreshListener != null) {
			mOnRefreshListener.onRefresh();
		}
	}

	/**
	 * Resets the list to a normal state after a refresh.
	 * 
	 * @param lastUpdated
	 *            Last updated at.
	 */
	public void onRefreshComplete(CharSequence lastUpdated) {
		setLastUpdated(lastUpdated);
		onRefreshComplete();
	}

	/**
	 * Resets the list to a normal state after a refresh.
	 */
	public void onRefreshComplete() {
		Log.d(TAG, "onRefreshComplete");

		resetHeader();

		// If refresh view is visible when loading completes, scroll down to
		// the next item.
		if (mRefreshView.getBottom() > 0) {
			invalidateViews();
			setSelection(1);
		}
	}

	/**
	 * Invoked when the refresh view is clicked on. This is mainly used when
	 * there's only a few items in the list and it's not possible to drag the
	 * list.
	 */
	private class OnClickRefreshListener implements OnClickListener {

		public void onClick(View v) {
			//if (mRefreshState != REFRESHING) {
				prepareForRefresh();
				onRefresh();
			//}
		}

	}

	/**
	 * Interface definition for a callback to be invoked when list should be
	 * refreshed.
	 */
	public interface OnRefreshListener {
		/**
		 * Called when the list should be refreshed.
		 * <p>
		 * A call to {@link com.omebee.android.layers.ui.components.views.pullrefresh.ListViewPullToRefresh #onRefreshComplete()} is
		 * expected to indicate that the refresh has completed.
		 */
		public void onRefresh();
	}

}
