package com.appstore.android.common.rx.subscriber;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.appstore.android.common.exception.ApiException;
import com.appstore.android.common.exception.BaseException;
import com.appstore.android.common.exception.ErrorMessageFactory;
import com.appstore.android.common.rx.RxErrorHandler;
import com.appstore.android.ui.activity.LoginActivity;
import com.google.gson.JsonParseException;

import java.net.SocketException;

import retrofit2.HttpException;

/**
 * Created by zhangqi on 2019/2/28
 */
public abstract class ErrorHandlerSubscriber<T> extends BaseSubscriber<T> {


    protected RxErrorHandler mErrorHandler = null;

    protected Context mContext;

    public ErrorHandlerSubscriber(Context context) {

        this.mContext = context;


        mErrorHandler = new RxErrorHandler(mContext);

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        BaseException exception = mErrorHandler.handleError(e);
        if (exception == null) {
            e.printStackTrace();
            Log.e("ErrorHandlerSubscriber", e.getMessage());
        } else {
            mErrorHandler.showErrorMessage(exception);
            if (exception.getCode() == BaseException.ERROR_TOKEN
                    || exception.getCode() == BaseException.ERROR_TOKEN_2) {
                toLogin();
            }
        }
    }

    private void toLogin() {
        mContext.startActivity(new Intent(mContext, LoginActivity.class));

    }
}
