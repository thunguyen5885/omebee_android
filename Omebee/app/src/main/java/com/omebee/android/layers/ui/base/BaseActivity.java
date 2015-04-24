package com.omebee.android.layers.ui.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.omebee.android.R;

/**
 * Created by phan on 8/6/2014.
 */
public abstract class BaseActivity extends Activity {
    //protected boolean mIsDualPane = false;//this variable is used for determining whether we are in single-pane or dual-pane(tablets) mode
    protected void showFragment(Fragment frag, int containerId) {
        FragmentManager fragmentManager = getFragmentManager();
        String backStateName = frag.getClass().getName();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        boolean fragmentPopped = fragmentManager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped && fragmentManager.findFragmentByTag(backStateName) == null) { // fragment not in back stack, create it.
            fragmentTransaction.replace(containerId, frag, backStateName);
            fragmentTransaction.addToBackStack(backStateName);
        } else {
            Fragment currFrag = (Fragment)fragmentManager.findFragmentByTag(backStateName);
            fragmentTransaction.show(currFrag);
        }

        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
