package com.omebee.android.layers.ui.components.views.material;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.andexert.library.RippleView;

/**
 * Created by ThuNguyen on 5/14/2015.
 */
public class MaterialRippleView extends RippleView{
    private static final int RIPPLE_DURATION = 250;// ms
    private boolean mIsRippleComplete = false;
    private boolean mIsNeedToConsumeCallback = false;
    private OnClickListener mOnClickCallback;
    public MaterialRippleView(Context context) {
        super(context);
        init();
    }

    public MaterialRippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaterialRippleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        setOnRippleCompleteListener(mOnRippleCompleteListener);
        setOnClickListener(mOnClickListener);
        setOnTouchListener(mOnTouchListener);
        setRippleDuration(RIPPLE_DURATION);
    }
    public void reset(){
        mIsRippleComplete = false;
        mIsNeedToConsumeCallback = false;
    }
    public void setOnClickCallback(OnClickListener onClickListener){
        mOnClickCallback = onClickListener;
    }

    // Define call back listener when ripple animation ends
    private OnRippleCompleteListener mOnRippleCompleteListener = new OnRippleCompleteListener() {
        @Override
        public void onComplete(RippleView rippleView) {
            if(mIsNeedToConsumeCallback == true){
                mIsNeedToConsumeCallback = false;
                mIsRippleComplete = false;
                if(mOnClickCallback != null){
                    mOnClickCallback.onClick(MaterialRippleView.this);
                }
            }else{
                mIsRippleComplete = true;
            }
        }
    };
    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("ThuNguyen", "Omebee mIsRippleComplete = " + mIsRippleComplete);
            if(mIsRippleComplete == true){
                mIsNeedToConsumeCallback = false;
                mIsRippleComplete = false;
                if(mOnClickCallback != null){
                    mOnClickCallback.onClick(MaterialRippleView.this);
                }
            }else{
                mIsNeedToConsumeCallback = true;
            }
        }
    };
    private OnTouchListener mOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_CANCEL:
                    Log.d("ThuNguyen", "Omebee action cancel");
                    reset();
                    break;
                default:
                    break;
            }
            return false;
        }
    };
}
