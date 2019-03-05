package com.appstore.android.common.rx.subscriber;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.appstore.android.common.exception.BaseException;
import com.appstore.android.ui.BaseView;

/**
 * Created by zhangqi on 2019/3/4
 */
public abstract class ProgressSubscriber<T> extends ErrorHandlerSubscriber<T> {

    private BaseView view;

    public ProgressSubscriber(Context context, BaseView view) {
        super(context);
        this.view = view;
    }


    protected boolean isShowDialog() {
        return true;
    }

    @Override
    public void onStart() {
        Log.e("6666666666", "onStart:-------------------------- ");
        if (isShowDialog()) {
            view.showLoading();
        }

    }

    @Override
    public void onCompleted() {
        Log.e("6666666666", "onCompleted:-------------------------- ");
        view.dismissLoading();
    }

    @Override
    public void onError(Throwable e) {
        Log.e("6666666666", "onError:-------------------------- ");
        BaseException baseException =  mErrorHandler.handleError(e);
        view.showError(baseException.getDisplayMessage());

    }

}
