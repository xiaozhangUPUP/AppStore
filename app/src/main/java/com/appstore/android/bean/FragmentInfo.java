package com.appstore.android.bean;

import android.support.v4.app.Fragment;

/**
 * Created by zhangqi on 2019/2/18
 */

public class FragmentInfo {

    private String title;

    private Class fragment;

    public FragmentInfo(String title, Class fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
