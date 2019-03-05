package com.appstore.android.common.rx;

import android.content.Context;
import android.widget.Toast;

import com.appstore.android.common.exception.ApiException;
import com.appstore.android.common.exception.BaseException;
import com.appstore.android.common.exception.ErrorMessageFactory;
import com.google.gson.JsonParseException;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * Created by zhangqi on 2019/2/28
 */
public class RxErrorHandler {
    private Context context;

    public RxErrorHandler(Context context) {
        this.context = context;
    }

    public BaseException handleError(Throwable e) {
        BaseException exception = new BaseException();

        if (e instanceof ApiException) {
            exception.setCode(((ApiException) e).getCode());
        } else if (e instanceof SocketException) {
            exception.setCode(BaseException.SOCKET_ERROR);
        } else if (e instanceof JsonParseException) {
            exception.setCode(BaseException.JSON_ERROR);
        } else if (e instanceof SocketTimeoutException) {
            exception.setCode(BaseException.SOCKET_TIMEOUT_ERROR);
        } else if (e instanceof HttpException) {
            exception.setCode(((HttpException) e).code());
        } else {
            exception.setCode(BaseException.UNKNOWN_ERROR);
        }

        exception.setDisplayMessage(ErrorMessageFactory.create(context, exception.getCode()));
        return exception;
    }

    public void showErrorMessage(BaseException e) {
        Toast.makeText(context, e.getDisplayMessage(), Toast.LENGTH_SHORT).show();
    }

}
