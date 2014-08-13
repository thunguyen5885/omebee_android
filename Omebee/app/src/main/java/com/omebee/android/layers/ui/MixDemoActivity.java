package com.omebee.android.layers.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.omebee.android.R;
import com.omebee.android.layers.ui.base.BaseActivity;
import com.omebee.android.layers.ui.components.data.ProductsLauncherGridItemData;
import com.omebee.android.layers.ui.fragments.SearchFragment;
import com.omebee.android.layers.ui.presenters.SearchPresenterImpl;
import com.omebee.android.layers.ui.presenters.base.ISearchPresenter;
import com.omebee.android.layers.ui.views.ISearchView;

import java.util.List;
import java.util.Random;

/**
 * Created by Thu Nguyen on 8/12/2014.
 */
public class MixDemoActivity extends BaseActivity{
    private LinearLayout mMixLeftLayout;
    private LinearLayout mMixRightLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mix_layout);
        mMixLeftLayout = (LinearLayout) findViewById(R.id.mixLeftLayout);
        mMixRightLayout = (LinearLayout) findViewById(R.id.mixRightLayout);

        addView();
    }

    private void addView(){
        for(int index = 0; index < 200; index ++) {
            Random random = new Random();
            int nextInt = random.nextInt(300) * 5;
            FrameLayout view = new FrameLayout(this);
            if(index % 2 == 0){
                view.setBackgroundColor(Color.BLUE);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, nextInt);
                view.setLayoutParams(params);
                mMixLeftLayout.addView(view);
                view = new FrameLayout(this);
                view.setBackgroundColor(Color.WHITE);
                params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 10);
                view.setLayoutParams(params);
                mMixLeftLayout.addView(view);
            }else{
                view.setBackgroundColor(Color.GREEN);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, nextInt);
                view.setLayoutParams(params);
                mMixRightLayout.addView(view);

                view = new FrameLayout(this);
                view.setBackgroundColor(Color.WHITE);
                params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 10);
                view.setLayoutParams(params);
                mMixRightLayout.addView(view);
            }


        }

    }
}
