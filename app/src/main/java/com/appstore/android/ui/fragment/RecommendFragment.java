package com.appstore.android.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appstore.android.MyApplication;
import com.appstore.android.R;
import com.appstore.android.bean.AppInfo;

import com.appstore.android.bean.IndexBean;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.di.component.DaggerRecommendComponent;
import com.appstore.android.di.module.RecommendModule;
import com.appstore.android.presenter.RecommendPresenter;
import com.appstore.android.presenter.contract.RecommendContract;
import com.appstore.android.ui.adapter.IndexMultiAdapter;
import com.appstore.android.ui.adapter.RecommendAppAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangqi on 2019/2/18
 */
public class RecommendFragment extends ProgressFragment<RecommendPresenter> implements RecommendContract.View {
    @BindView(R.id.recommend_recyclerview)
    RecyclerView recommendRecyclerview;

    private Context context;

    @Inject
    RecommendPresenter presenter;

    @Override
    public int setLayout() {
        return R.layout.fragment_recommend;
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

        recommendRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        //为RecyclerView设置分割线(这个可以对DividerItemDecoration进行修改，自定义)
        //        recommendRecyclerview.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recommendRecyclerview.setItemAnimator(new DefaultItemAnimator());
        IndexMultiAdapter recommendAppAdapter = new IndexMultiAdapter(context, datas);
        recommendRecyclerview.setAdapter(recommendAppAdapter);
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
