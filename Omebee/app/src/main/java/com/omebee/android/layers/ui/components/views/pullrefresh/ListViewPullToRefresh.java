package com.omebee.android.layers.ui.components.views.pullrefresh;

import com.omebee.android.R;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;

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
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import java.util.List;


public class ListViewPullToRefresh extends ListView implements OnScrollListener {

	private static final int TAP_TO_REFRESH = 1;
	private static final int PULL_TO_REFRESH = 2;
	private static final int RELEASE_TO_REFRESH = 3;
	protected static final int REFRESHING = 4;

	protected static final String TAG = "PullToRefreshGridView";

	private OnRefreshListener mOnRefreshListener;
    private IUpdateDataBackListener mIUpdateDataBackListener;
	/**
	 * Listener that will receive notifications every time the list scrolls.
	 */
	private OnScrollListener mOnScrollListener;
	protected LayoutInflater mInflater;

	// header
	private LinearLayout mRefreshView;

	private ProgressBar mRefreshViewProgress;

	protected int mCurrentScrollState;
	//protected int mRefreshState;

	private int mRefreshViewHeight;
	private int mRefreshOriginalBottomPadding;
    private int mRefreshOriginalTopPadding;
	private int mLastMotionY;
    private int mLastMotionMoveY;
    private boolean isDownDirection = true;
    private boolean isRecovering = false;
	private boolean mBounceHack;
    private boolean mIsRefreshing = false;
    private boolean mIsAnimationEnd = true;
    private Object mDataCallbackHold = null;
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
		mRefreshView = (LinearLayout) mInflater.inflate(
				R.layout.ctrl_header_pull_to_refresh, null, false);

		mRefreshViewProgress = (ProgressBar) mRefreshView
				.findViewById(R.id.pull_to_refresh_progress);

		//mRefreshView.setOnClickListener(new OnClickRefreshListener());

        mRefreshOriginalBottomPadding = mRefreshView.getPaddingBottom();
        mRefreshOriginalTopPadding = mRefreshView.getPaddingTop();
		//mRefreshState = TAP_TO_REFRESH;

		addHeaderView(mRefreshView);

		super.setOnScrollListener(this);

		measureView(mRefreshView);
		mRefreshViewHeight = mRefreshView.getMeasuredHeight();
        Log.d(TAG, "mRefreshViewHeight "+mRefreshViewHeight);

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

    public IUpdateDataBackListener getIUpdateDataBackListener() {
        return mIUpdateDataBackListener;
    }

    public void setIUpdateDataBackListener(IUpdateDataBackListener mIUpdateDataBackListener) {
        this.mIUpdateDataBackListener = mIUpdateDataBackListener;
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
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, fromY, 0);
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
                        if(!isRecovering) {
                            // Initiate the refresh
                            Log.d(TAG, "prepareForRefresh");
                            recoverLayout();
                        }

//                        onRefresh();
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
                mLastMotionMoveY = y;
                isDownDirection = true;
                isRecovering = false;
                break;
            case MotionEvent.ACTION_MOVE:
                applyHeaderPadding(event);
                // Call onrefresh here
                if(!mIsRefreshing){
                    mIsRefreshing = true;
                    mIsAnimationEnd = false;
                    setDataCallbackHold(null);
                    onRefresh();
                }

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

//		for (int p = 0; p < pointerCount; p++) {
			//if (mRefreshState == RELEASE_TO_REFRESH) {
				if (isVerticalFadingEdgeEnabled()) {
					setVerticalScrollBarEnabled(false);
				}

//				int historicalY = (int) ev.getHistoricalY(p);
            int historicalY = (int) ev.getY();

            // Calculate the padding to apply, we divide by 1.7 to
            // simulate a more resistant effect during pull.
            int bottomPadding = (int) (((historicalY - mLastMotionY) - mRefreshViewHeight) / 1.7);
            int deltaMoveY = historicalY - mLastMotionMoveY;
            int topPadding = mRefreshView.getPaddingTop();
            if(deltaMoveY < 0){ // Move up
                topPadding -= deltaMoveY;
//                if(isDownDirection) {
//                    topPadding -= deltaMoveY;
//                }else{
//                    isDownDirection = true;
//                }
                isDownDirection = false;
            }else{ // Move down
                if(!isDownDirection){
                    topPadding -= deltaMoveY;
                }else{
                    //isDownDirection = false;
                    //topPadding +=
                }
                //isDownDirection = true;
            }
            topPadding = Math.max(topPadding, 0);
            Log.d("ThuNguyen", "bottomPadding: " + bottomPadding);

            if(isDistanceScrollExceedActionBarSize()){
                Log.d("ThuNguyen", "Exceed");
                if(!isRecovering) {
                    Log.d("ThuNguyen", "Start to recovering layout");
                    isRecovering = true;
                    recoverLayout();
                }
            }else if(!isRecovering){
                Log.d("ThuNguyen", "Start to set padding again");
               mRefreshView.setPadding(mRefreshView.getPaddingLeft(),
                       topPadding, mRefreshView.getPaddingRight(),
                        bottomPadding);
                // Keep the last move motion Y
                mLastMotionMoveY = historicalY;
            }
            //mRefreshView.invalidate();
			//}
//		}
	}

	/**
	 * Sets the header padding back to original size.
	 */
	private void resetHeaderPadding() {
		mRefreshView.setPadding(mRefreshView.getPaddingLeft(),
                mRefreshOriginalTopPadding, mRefreshView.getPaddingRight(),
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
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
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
//			setSelection(1);
        }
        // Reset isFreshing flag
        mIsRefreshing = false;
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

    /**
     * Interface definition for a data callback when the animation for action_up complete
     */
    public interface  IUpdateDataBackListener{
        public void updateDataBack(Object dataCallback);
    }

    private Animation.AnimationListener onAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            mIsAnimationEnd = false;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mIsAnimationEnd = true;

            if(mDataCallbackHold != null && mIUpdateDataBackListener != null){
                // Wait 200ms to update back
                new CountDownTimer(150, 50){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {

                        mIUpdateDataBackListener.updateDataBack(mDataCallbackHold);
                    }
                }.start();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    /**
     * Make layout be on the original position
     */
    private void recoverLayout(){
        prepareForRefresh();
        this.clearAnimation();
        Animation translateAnim = createTranslateAnimation(mRefreshView.getBottom());
        translateAnim.setAnimationListener(onAnimationListener);
        this.startAnimation(translateAnim);
    }
    private boolean isDistanceScrollExceedActionBarSize(){
        invalidate();
        final int[] coorPos = new int[2];
        View firstView = getChildAt(0);
        firstView.getLocationOnScreen(coorPos);

        Log.d("ThuNguyen", "Current padding = " + mRefreshView.getMeasuredHeight());
        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        Log.d("ThuNguyen", "progress bar height = " + mRefreshViewProgress.getMeasuredHeight());
        Log.d("ThuNguyen", "action bar bar height = " + actionBarSize);
        if(mRefreshView.getMeasuredHeight() >= 2*actionBarSize){
            return true;
        }
        return false;
    }
    public boolean isPreparedView(){
        return mIsAnimationEnd;
    }
    public void setDataCallbackHold(Object dataCallback){
        this.mDataCallbackHold = dataCallback;
    }
}
