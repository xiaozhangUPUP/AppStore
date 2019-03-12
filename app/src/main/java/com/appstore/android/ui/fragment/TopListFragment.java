package com.appstore.android.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appstore.android.R;
import com.appstore.android.bean.AppInfo;
import com.appstore.android.bean.PageBean;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.di.component.DaggerTopListComponent;
import com.appstore.android.di.module.TopListModule;
import com.appstore.android.presenter.AppInfoPresenter;
import com.appstore.android.presenter.contract.AppInfoContract;
import com.appstore.android.ui.adapter.AppInfoAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by zhangqi on 2019/2/18
 */
public class TopListFragment extends ProgressFragment<AppInfoPresenter> implements AppInfoContract.AppInfoView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private int page = 0;

    private AppInfoAdapter adapter;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

        DaggerTopListComponent.builder()
                .appComponent(appComponent)
                .topListModule(new TopListModule(this))
                .build()
                .inject(this);
    }

    private void initRecyclerView() {
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new AppInfoAdapter();
        adapter.setOnLoadMoreListener(this, recycler_view);
        recycler_view.setAdapter(adapter);

    }

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    public void init() {
        presenter.getTopListApps(page);
        initRecyclerView();
    }

    @Override
    public void showResult(PageBean<AppInfo> pageBean) {
        adapter.addData(pageBean.getDatas());
        if (pageBean.isHasMore()) {
            page++;
        }
        adapter.setEnableLoadMore(pageBean.isHasMore());
    }

    @Override
    public void onLoadMoreComplete() {
        adapter.loadMoreComplete();
    }


    @Override
    public void onLoadMoreRequested() {
        presenter.getTopListApps(page);
    }
}
