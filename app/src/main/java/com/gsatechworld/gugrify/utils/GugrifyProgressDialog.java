package com.gsatechworld.gugrify.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class GugrifyProgressDialog {



    ProgressDialog progressDialog = null;
    Context mContext = null;

    public GugrifyProgressDialog(Context context) {
        mContext = context;
        progressDialog = new ProgressDialog(mContext);
    }

    public void showProgressView(String msgTitle, String subMessage) {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        progressDialog = ProgressDialog.show(mContext,
                msgTitle,
                subMessage,
                false, false);
    }

    public void closeProgressView() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.finalize();
    }
}
