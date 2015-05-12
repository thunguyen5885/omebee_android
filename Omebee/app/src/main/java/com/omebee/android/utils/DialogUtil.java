package com.omebee.android.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;

import com.omebee.android.R;
import com.omebee.android.layers.ui.ProductsLauncherActivity;
import com.omebee.android.layers.ui.components.data.FilterItemData;
import com.omebee.android.layers.ui.components.views.util.ProductFilterLayout;


public class DialogUtil {

    public static void showFilterDialog(Context context, ProductsLauncherActivity.IFilterActionCallback filterActionCallback,
                                          FilterItemData filterItemData){
        final Dialog dialog = new Dialog(context,android.R.style.Theme_Holo_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ProductFilterLayout productFilterLayout = new ProductFilterLayout(context);
        productFilterLayout.updateUIFromData(filterItemData);
        productFilterLayout.setFilterActionCallback(filterActionCallback);
        productFilterLayout.setOnLayoutCloseListener(new ProductFilterLayout.IOnLayoutCloseListener() {
            @Override
            public void onClose() {
                dialog.dismiss();
            }
        });
        dialog.setContentView(productFilterLayout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

        // Get screen width
        int screenWidth = AppFnUtils.getScreenWidth((Activity) context);
        int screenHeight = AppFnUtils.getScreenHeight((Activity) context);
        dialog.getWindow().setLayout(screenWidth, screenHeight);
        dialog.show();
    }

    public static Dialog createConfirmYesNoDialog(Context context, String title, String message,DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener){
        AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(context,android.R.style.Theme_Holo_Dialog);
        confirmDialogBuilder.setTitle(title);
        confirmDialogBuilder.setMessage(message);
        confirmDialogBuilder.setPositiveButton("Yes",yesListener);
        confirmDialogBuilder.setNegativeButton("No",noListener);
        return confirmDialogBuilder.create();
    }

    public static Dialog createInformDialog(Context context, String title, String message,DialogInterface.OnClickListener okListener){
        AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(context);
        confirmDialogBuilder.setTitle(title);
        confirmDialogBuilder.setMessage(message);
        confirmDialogBuilder.setPositiveButton("OK",okListener);
        return confirmDialogBuilder.create();
    }

}
