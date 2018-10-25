package com.gsatechworld.gugrify.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.utils.custom_snackbar.TSnackbar;


public class CustomSnackBarUtil {
    /* for snack bar usage

    CustomSnackBarUtil customSnackBarUtil = new CustomSnackBarUtil(getContext());
                    customSnackBarUtil.showSnackBar(ll_login_register, NO_INTERNET,
    getContext().getResources().getColor(R.color.md_red_400),
    getContext().getResources().getColor(R.color.color_white)); */


    private Context mContext;

    public CustomSnackBarUtil(Context context) {
        this.mContext = context;
    }

    public void showSnackBar(View view, String title, int snackBgColor, int textColor) {
        TSnackbar snackbar = TSnackbar.make(view, title, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(snackBgColor);
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(textColor);
        snackbar.show();
    }
}
