package com.appstore.android.ui.fragment;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appstore.android.R;

import com.appstore.android.bean.IndexBean;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.di.component.DaggerRecommendComponent;
import com.appstore.android.di.module.RecommendModule;
import com.appstore.android.presenter.RecommendPresenter;
import com.appstore.android.presenter.contract.AppInfoContract;
import com.appstore.android.ui.adapter.IndexMultiAdapter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by zhangqi on 2019/2/18
 */
public class RecommendFragment extends ProgressFragment<RecommendPresenter> implements AppInfoContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private Context context;

    @Inject
    RecommendPresenter presenter;

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerRecommendComponent.builder()
                .appComponent(appComponent)
                .recommendModule(new RecommendModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void init() {
        presenter.requestDatas();
    }

    private void initRecycler(IndexBean datas) {

        recycler_view.setLayoutManager(new LinearLayoutManager(context));
        //为RecyclerView设置分割线(这个可以对DividerItemDecoration进行修改，自定义)
        //        recommendRecyclerview.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        IndexMultiAdapter recommendAppAdapter = new IndexMultiAdapter(context, datas);
        recycler_view.setAdapter(recommendAppAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onEmptyViewClick() {
        presenter.requestDatas();
    }

    //    @Override
    //    public void showNoData() {
    //        Toast.makeText(context, "暂时无数据...", Toast.LENGTH_SHORT).show();
    //    }

    @Override
    public void showResult(IndexBean datas) {
        initRecycler(datas);
    }
}
