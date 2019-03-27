package com.appstore.android.ui.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appstore.android.R;
import com.appstore.android.bean.AppInfo;
import com.appstore.android.bean.PageBean;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.presenter.AppInfoPresenter;
import com.appstore.android.presenter.contract.AppInfoContract;
import com.appstore.android.ui.adapter.AppInfoAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.BindView;

/**
 * Created by zhangqi on 2019/3/13
 */
public abstract class BaseAppInfoFragment extends ProgressFragment<AppInfoPresenter> implements AppInfoContract.AppInfoView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    protected int page = 0;

    private AppInfoAdapter adapter;

    private void initRecyclerView() {
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = buildAdapter();
        adapter.setOnLoadMoreListener(this, recycler_view);
        recycler_view.setAdapter(adapter);

    }

    abstract AppInfoAdapter buildAdapter();

    abstract int type();

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    public void init() {
        presenter.requestData(type(), page);
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
        presenter.requestData(type(), page);
    }

    @Override
    public void onEmptyViewClick() {
        presenter.requestData(type(), page);
    }
}
