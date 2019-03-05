package com.appstore.android.common.exception;

/**
 * Created by zhangqi on 2019/2/28
 */
public class ApiException extends BaseException {

    public ApiException(int code, String displayMessage) {
        super(code, displayMessage);
    }

}
