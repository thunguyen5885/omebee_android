package com.omebee.android.layers.ui.components.views.pullrefresh;

import com.omebee.android.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;



public class ListViewPullAndLoadMore extends ListViewPullToRefresh {

	public ListViewPullAndLoadMore(Context context, AttributeSet attrs) {
		super(context, attrs);
		initComponent(context);
	}

	// Listener to process load more items when user reaches the end of the list
	private OnLoadMoreListener mOnLoadMoreListener;
	// To know if the list is loading more items
	private boolean mIsLoadingMore = false;

	// footer
	private LinearLayout mFooterView;
	// private TextView mLabLoadMore;
	private SmoothProgressBar mProgressBarLoadMore;

	public ListViewPullAndLoadMore(Context context) { super(context);
	  initComponent(context); }

	public ListViewPullAndLoadMore(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initComponent(context);
	}

	public void initComponent(Context context) {

		// footer
		mFooterView = (LinearLayout) mInflater.inflate(
				R.layout.ctrl_footer_load_more, this, false);
		/*
		 * mLabLoadMore = (TextView) mFooterView
		 * .findViewById(R.id.load_more_lab_view);
		 */
		mProgressBarLoadMore = (SmoothProgressBar) mFooterView
				.findViewById(R.id.load_more_progressBar);

		addFooterView(mFooterView);
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

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);

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
