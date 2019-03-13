package com.appstore.android.ui.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appstore.android.R;
import com.appstore.android.bean.AppInfo;
import com.appstore.android.bean.PageBean;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.di.component.DaggerAppInfoComponent;
import com.appstore.android.di.module.AppInfoModule;
import com.appstore.android.presenter.AppInfoPresenter;
import com.appstore.android.presenter.contract.AppInfoContract;
import com.appstore.android.ui.adapter.AppInfoAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.BindView;


/**
 * Created by zhangqi on 2019/2/18
 */
public class TopListFragment extends BaseAppInfoFragment {

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

        DaggerAppInfoComponent.builder()
                .appComponent(appComponent)
                .appInfoModule(new AppInfoModule(this))
                .build()
                .injectTopListFragment(this);
    }


    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter
                .builder()
                .showPosition(true)
                .showCategoryName(true)
                .build();
    }

    @Override
    int type() {
        return AppInfoPresenter.TOP_LIST;
    }

}
