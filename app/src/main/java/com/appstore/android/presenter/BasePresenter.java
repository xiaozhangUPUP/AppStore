package com.appstore.android.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.appstore.android.ui.BaseView;

/**
 * Created by zhangqi on 2019/2/20
 */
public class BasePresenter<M, V extends BaseView> {

    protected M model;
    protected V view;

    protected Context context;

    public BasePresenter(M model, V view) {
        this.model = model;
        this.view = view;
        initContext();
    }

    private void initContext() {
        if (view instanceof Fragment) {
            context = ((Fragment) view).getActivity();
        } else {
            context = (Activity) view;
        }
    }
}
