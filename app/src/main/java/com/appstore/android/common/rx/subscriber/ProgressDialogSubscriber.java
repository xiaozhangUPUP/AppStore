package com.appstore.android.common.rx.subscriber;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import com.appstore.android.common.rx.RxErrorHandler;

/**
 * Created by zhangqi on 2019/3/4
 */
public abstract class ProgressDialogSubscriber<T> extends ErrorHandlerSubscriber<T> {

    private Context context;
    private ProgressDialog dialog;

    public ProgressDialogSubscriber(Context context) {
        super(context);
        this.context = context;
    }


    protected boolean isShowDialog() {
        return true;
    }

    @Override
    public void onStart() {
        Log.e("6666666666", "onStart:-------------------------- ");
        if (isShowDialog()) {
            showProgressDialog();
        }

    }

    @Override
    public void onCompleted() {
        Log.e("6666666666", "onCompleted:-------------------------- ");
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        Log.e("6666666666", "onError:-------------------------- ");
        super.onError(e);
        dismissProgressDialog();
    }

    private void initProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage("loading...");
        }

    }

    private void showProgressDialog() {
        initProgressDialog();
        dialog.show();
    }

    private void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
