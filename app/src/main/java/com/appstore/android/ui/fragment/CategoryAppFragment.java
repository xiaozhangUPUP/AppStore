package com.appstore.android.ui.fragment;

import android.annotation.SuppressLint;

import com.appstore.android.di.component.AppComponent;
import com.appstore.android.ui.adapter.AppInfoAdapter;

/**
 * Created by zhangqi on 2019/3/28
 */
@SuppressLint("ValidFragment")
public class CategoryAppFragment extends BaseAppInfoFragment {

    public static final int FEATURED = 0;
    public static final int TOPLIST = 1;
    public static final int NEWLIST = 3;

    private int categoryId;
    private int fragmentType;

    private CategoryAppFragment(int categoryId, int fragmentType) {
        this.categoryId = categoryId;
        this.fragmentType = fragmentType;
    }


    public static CategoryAppFragment newInstance(int categoryId, int fragmentType) {
        return new CategoryAppFragment(categoryId, fragmentType);

    }

    @Override
    AppInfoAdapter buildAdapter() {
        return null;
    }

    @Override
    public void init() {
        //        initRecyclerView();
    }

    @Override
    int type() {
        return 0;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }
}
