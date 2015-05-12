package com.omebee.android.layers.ui.components.views.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.omebee.android.R;
import com.omebee.android.layers.ui.components.views.abslistview.CustomStaggeredGridView;
import com.omebee.android.layers.ui.components.views.swipe.CustomSwipeRefreshLayout;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by phannguyen on 9/2/14.
 */
public class GridViewPullRefreshAndLoadMore extends CustomSwipeRefreshLayout {
    private static final String TAG = "GridViewPullRefreshAndLoadMore";
    protected LayoutInflater mInflater;
    private CustomStaggeredGridView mMainListView;

    private boolean mIsLoadingMore = false;

    // footer
    private LinearLayout mFooterView;

    private SmoothProgressBar mProgressBarLoadMore;
    private OnLoadMoreListener mOnLoadMoreListener;

    public GridViewPullRefreshAndLoadMore(Context context) {
        super(context);
        initComponent(context);
    }

    public GridViewPullRefreshAndLoadMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponent(context);
    }
    public void initComponent(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.ctrl_listview_pullrefresh_loadmore, this,true);

        mMainListView = (CustomStaggeredGridView)findViewById(R.id.mainlistview);

        // footer
        mFooterView = (LinearLayout) mInflater.inflate(
                R.layout.ctrl_footer_load_more, this, false);
		/*
		 * mLabLoadMore = (TextView) mFooterView
		 * .findViewById(R.id.load_more_lab_view);
		 */
        mProgressBarLoadMore = (SmoothProgressBar) mFooterView
                .findViewById(R.id.load_more_progressBar);

        mMainListView.addFooterView(mFooterView);

        setupColorForProgressBar(context);
        setupOnScrollLoadMoreListener();
    }

    private void setupColorForProgressBar(Context context) {

       /* this.setColorSchemeResources(
                R.color.holo_blue_light,
                R.color.holo_orange_light,
                R.color.holo_green_light,
                R.color.holo_red_light);*/
        this.setColorScheme(
                R.color.holo_blue_light,
                R.color.holo_orange_light,
                R.color.holo_green_light,
                R.color.holo_red_light);

    }

    private void setupOnScrollLoadMoreListener(){
        mMainListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                //do nothing
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                //mMainListView.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
               // Log.d(TAG, "SCROLL To position "+mMainListView.get());
                // if need a list to load more items
                if (mOnLoadMoreListener != null) {
                    if (visibleItemCount == totalItemCount) {
                        return;
                    }
                    // Start to load more when user scroll over 1/4 the number of list
                    boolean loadMore = firstVisibleItem + visibleItemCount >= ((totalItemCount - 1)/4); // -1 for the footerview
                   // Log.d("TAG, "isLoadMore = " + loadMore);
                   // Log.d(TAG, "firstVisibleItem = " + firstVisibleItem + ",visibleItemCount = " + visibleItemCount + ",totalItemCount = " + totalItemCount);
                    if (!mIsLoadingMore && loadMore) {
				        mProgressBarLoadMore.setVisibility(View.VISIBLE);
                        mIsLoadingMore = true;
                        mOnLoadMoreListener.onLoadMore();
                    }

                }
            }
        });
    }
    @Override
    public void onAttachedToWindow() {
        //have to ask super to attach to window, otherwise it won't scroll in jelly bean.
        super.onAttachedToWindow();
    }


    public void setSelection(int position){
        mMainListView.setSelection(position);
    }

    public void setAdapter(ListAdapter adapter) {
        mMainListView.setAdapter(adapter);
        mMainListView.setSelection(0);//display first item
    }

    /**
     * keep current position when add more items at top
     * @param nAddTopItems
     */
    public void keepCurrentPositionAsAddTop(int nAddTopItems){
        mMainListView.keepCurrentPositionAsAddTop(nAddTopItems);
    }
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        if(mMainListView != null){
//            mMainListView.setOnItemClickListener(onItemClickListener);
//            mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Log.d("ThuNguyen", "Position = " + position);
//                }
//            });
            mMainListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("ThuNguyen", "Position = " + position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
    /**
     * Register a callback to be invoked when this list should be refreshed.
     *
     * @param onRefreshListener
     *            The callback to run.
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        super.setOnRefreshListener(onRefreshListener);
    }

    public interface OnRefreshListener extends CustomSwipeRefreshLayout.OnRefreshListener {
        public void onRefresh();
    }

    public void OnRefreshComplete(){
        this.setRefreshing(false);
    }

    public void onLoadMoreComplete() {
        mIsLoadingMore = false;
        mProgressBarLoadMore.setVisibility(View.GONE);
    }

    public void onReachToEndOfList(){
        mIsLoadingMore = true;
    }
    public void onResetToBeginLoading(){
        mIsLoadingMore = false;
    }
    /**
     * Register a callback to be invoked when this list reaches the end (last
     * item be visible)
     *
     * @param onLoadMoreListener
     *            The callback to run.
     */

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }
    /**
     * Interface definition for a callback to be invoked when list reaches the
     * last item (the user load more items in the list)
     */
    public interface OnLoadMoreListener {
        /**
         * Called when the list reaches the last item (the last item is visible
         * to the user) A call to
         * {@link com.omebee.android.layers.ui.components.views.pullrefresh.ListViewPullAndLoadMore #onLoadMoreComplete()} is expected to
         * indicate that the loadmore has completed.
         */
        public void onLoadMore();
    }
}
