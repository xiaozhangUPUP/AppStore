package com.appstore.android.bean;

import java.io.Serializable;

/**
 * Created by zhangqi on 2019/2/28
 */
public class BaseBean<T> implements Serializable {
    private static final int STATUS_SUCCESS = 1;
    private int status;
    private String message;
    private T data;

    public boolean success() {
        return status == STATUS_SUCCESS;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
