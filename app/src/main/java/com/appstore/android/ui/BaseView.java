package com.appstore.android.ui;

/**
 * Created by zhangqi on 2019/2/20
 */
public interface BaseView {

    void showLoading();

    void dismissLoading();

    void showError(String msg);

}
