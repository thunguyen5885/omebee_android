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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.omebee.android.R;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by phan on 8/26/2014.
 */
public class ListViewPullRefreshLoadMore extends LinearLayout{

    protected static final String TAG = "ListViewPullRefreshLoadMore";

    private ListViewPullToRefresh.OnRefreshListener mOnRefreshListener;
    private ListViewPullToRefresh.IUpdateDataBackListener mIUpdateDataBackListener;
    /**
     * Listener that will receive notifications every time the list scrolls.
     */
    //private AbsListView.OnScrollListener mOnScrollListener;
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
    private int mActionBarSize;
    private boolean mIsRefreshing = false;
    private boolean mIsAnimationEnd = true;
    private Object mDataCallbackHold = null;

    private ListViewPullAndLoadMore.OnLoadMoreListener mOnLoadMoreListener;
    // To know if the list is loading more items
    private boolean mIsLoadingMore = false;

    // footer
    private LinearLayout mFooterView;
    // private TextView mLabLoadMore;
    private SmoothProgressBar mProgressBarLoadMore;
    private ListView mMainListView;

    public ListViewPullRefreshLoadMore(Context context) {
        super(context);
        initComponent(context);
    }

    public ListViewPullRefreshLoadMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponent(context);
    }

    public ListViewPullRefreshLoadMore(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initComponent(context);
    }

    public void initComponent(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.ctrl_listview_pullrefresh_loadmore, this);
        this.setOrientation(VERTICAL);
        //Main listview
        mMainListView = (ListView)findViewById(R.id.mainlistview);
        setupOnScrollListenerForMainListView();
        setupTouchEventForMainListView();
        mMainListView.setVerticalScrollBarEnabled(false);
        mMainListView.setHorizontalScrollBarEnabled(false);
        //header
        mRefreshView = (LinearLayout) mInflater.inflate(
                R.layout.ctrl_header_pull_to_refresh, null, false);

        mRefreshViewProgress = (ProgressBar) mRefreshView
                .findViewById(R.id.pull_to_refresh_progress);

        //mRefreshView.setOnClickListener(new OnClickRefreshListener());

        mRefreshOriginalBottomPadding = mRefreshView.getPaddingBottom();
        mRefreshOriginalTopPadding = mRefreshView.getPaddingTop();
        //mRefreshState = TAP_TO_REFRESH;

        addHeaderView(mRefreshView);
        measureView(mRefreshView);
        mRefreshViewHeight = mRefreshView.getMeasuredHeight();
        Log.d(TAG, "mRefreshViewHeight " + mRefreshViewHeight);

        // footer
        mFooterView = (LinearLayout) mInflater.inflate(
                R.layout.ctrl_footer_load_more, this, false);
        mProgressBarLoadMore = (SmoothProgressBar) mFooterView
                .findViewById(R.id.load_more_progressBar);
        addFooterView(mFooterView);

        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });
        mActionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
    }

    public void addFooterView(View footerView){
        this.addView(footerView);
    }
    public void addHeaderView(View headerView){
        this.addView(headerView,0);
    }

    public void setSelection(int position){
        mMainListView.setSelection(position);
    }
    public void setAdapter(ListAdapter adapter) {
        mMainListView.setAdapter(adapter);
        mMainListView.setSelection(0);//display first item
    }
    @Override
    protected void onAttachedToWindow() {
        //have to ask super to attach to window, otherwise it won't scroll in jelly bean.
        super.onAttachedToWindow();
        mMainListView.setSelection(0);//display first item
    }
    private void setupOnScrollListenerForMainListView(){
        mMainListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mCurrentScrollState = scrollState;

                if (mCurrentScrollState == SCROLL_STATE_IDLE) {
                    mBounceHack = false;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // When the refresh view is completely visible, change the text to say
                // "Release to refresh..." and flip the arrow drawable.
                if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    if (firstVisibleItem == 0) {
                        mRefreshViewProgress.setVisibility(View.VISIBLE);
                        Log.d(TAG, "SCROLL_STATE_TOUCH_SCROLL 123");
                    } else {
                        Log.d(TAG, "SCROLL_STATE_TOUCH_SCROLL 456");
                        resetHeader();
                    }
                } else if (mCurrentScrollState == SCROLL_STATE_FLING && firstVisibleItem == 0) {
                    Log.d(TAG, "SCROLL_STATE_FLING ABC");
                    mMainListView.setSelection(0);
                    mBounceHack = true;
                } else if (mBounceHack && mCurrentScrollState == SCROLL_STATE_FLING) {
                    Log.d(TAG, "SCROLL_STATE_FLING XYZ");
                    mMainListView.setSelection(0);
                }

                // if need a list to load more items
                if (mOnLoadMoreListener != null) {

                    if (visibleItemCount == totalItemCount) {
//				mProgressBarLoadMore.setVisibility(View.GONE);
                        // mLabLoadMore.setVisibility(View.GONE);
                        return;
                    }

                    boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

                    if (!mIsLoadingMore && loadMore
                            && mCurrentScrollState != SCROLL_STATE_IDLE) {
//				mProgressBarLoadMore.setVisibility(View.VISIBLE);
                        // mLabLoadMore.setVisibility(View.VISIBLE);
                        mIsLoadingMore = true;
                        onLoadMore();
                    }

                }
            }
        });
    }

    private Animation createTranslateAnimation(int fromY){
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, fromY, 0);
        translateAnimation.setDuration(350);
        return translateAnimation;
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

    private void setupTouchEventForMainListView(){
        mMainListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int y = (int) event.getY();
                mBounceHack = false;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (!isVerticalScrollBarEnabled()) {
                            setVerticalScrollBarEnabled(true);
                        }
                        if (mMainListView.getFirstVisiblePosition() == 0) {
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
                                mMainListView.setSelection(0);
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
                return false;
            }
        });
    }



////////////////Footer functions//////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Register a callback to be invoked when this list reaches the end (last
     * item be visible)
     *
     * @param onLoadMoreListener
     *            The callback to run.
     */

    public void setOnLoadMoreListener(ListViewPullAndLoadMore.OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public void onLoadMore() {
        Log.d(TAG, "onLoadMore");
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMore();
        }
    }

    /**
     * Notify the loading more operation has finished
     */
    public void onLoadMoreComplete() {
        mIsLoadingMore = false;
    }
    public void setProgressBarVisibility(int visibility){
        mProgressBarLoadMore.setVisibility(visibility);

    }

////////////Header functions///////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Register a callback to be invoked when this list should be refreshed.
     *
     * @param onRefreshListener
     *            The callback to run.
     */
    public void setOnRefreshListener(ListViewPullToRefresh.OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    public ListViewPullToRefresh.IUpdateDataBackListener getIUpdateDataBackListener() {
        return mIUpdateDataBackListener;
    }

    public void setIUpdateDataBackListener(ListViewPullToRefresh.IUpdateDataBackListener mIUpdateDataBackListener) {
        this.mIUpdateDataBackListener = mIUpdateDataBackListener;
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
     */
    public void onRefreshComplete() {
        Log.d(TAG, "onRefreshComplete");
        resetHeader();
        // If refresh view is visible when loading completes, scroll down to
        // the next item.
        if (mRefreshView.getBottom() > 0) {
            mMainListView.invalidateViews();
//			setSelection(0);
        }
        // Reset isFreshing flag
        mIsRefreshing = false;
    }

    private void recoverLayout(){
        prepareForRefresh();
        mMainListView.clearAnimation();
        Animation translateAnim = createTranslateAnimation(mRefreshView.getBottom());
        translateAnim.setAnimationListener(onAnimationListener);
        mMainListView.startAnimation(translateAnim);
    }
    private boolean isDistanceScrollExceedActionBarSize(){
       // Log.d("ThuNguyen", "Current padding = " + mRefreshView.getMeasuredHeight());

       // Log.d("ThuNguyen", "progress bar height = " + mRefreshViewProgress.getMeasuredHeight());
       // Log.d("ThuNguyen", "action bar bar height = " + actionBarSize);
        if(mRefreshView.getMeasuredHeight() >= 2*mActionBarSize){
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
