package com.omebee.android.layers.ui.components.views.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.omebee.android.R;

public class LoadAndRefreshLayout extends FrameLayout{
	public interface ILoadAndRefreshCallback{
		public void onLoadInitialData();
	}
	// For view control
	private LayoutInflater mInflater;
	private ProgressBar mPbLoading;
	private ImageView mBtnRefresh;
	
	// For call back object
	private ILoadAndRefreshCallback mILoadAndRefreshCallback;

    // For data, variable

	public LoadAndRefreshLayout(Context context) {
		super(context);
	}
	public LoadAndRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public LoadAndRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	/*For getter and setter Start*/
	public ILoadAndRefreshCallback getILoadAndRefreshCallback() {
		return mILoadAndRefreshCallback;
	}
	public void setILoadAndRefreshCallback(
			ILoadAndRefreshCallback mILoadAndRefreshCallback) {
		this.mILoadAndRefreshCallback = mILoadAndRefreshCallback;
	}
	/*For getter and setter End*/
	/*Some public method Start*/
	private void initView(){
		// Inflate layout and assign this layout as root view
		mInflater = LayoutInflater.from(getContext());
		View v = mInflater.inflate(R.layout.ctrl_load_and_refresh, this);
		mPbLoading = (ProgressBar) v.findViewById(R.id.pbLoading);
		mBtnRefresh = (ImageView) v.findViewById(R.id.btnRefresh);
		mBtnRefresh.setOnClickListener(onRefreshClickListener);
	}
	
	/**
	 * Process when load/refresh complete
	 */
	public void onComplete(){
		// Hide when load/refresh done
		setVisibility(View.GONE);
	}
	/**
	 * Request/enforce show refresh button
	 */
	public void enforceShowRefreshButton(){
		setVisibility(View.VISIBLE);
		mBtnRefresh.setVisibility(View.VISIBLE);
		mPbLoading.setVisibility(View.GONE);
	}
	/**
	 * Begin show loading dialog
	 */
	public void beginLoading(){
        initView();
		setVisibility(View.VISIBLE);
		mBtnRefresh.setVisibility(View.GONE);
		mPbLoading.setVisibility(View.VISIBLE);
	}
	/*Some public method End*/
	
	private OnClickListener onRefreshClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(mILoadAndRefreshCallback != null){
				mILoadAndRefreshCallback.onLoadInitialData();
			}
		}
	};
}
