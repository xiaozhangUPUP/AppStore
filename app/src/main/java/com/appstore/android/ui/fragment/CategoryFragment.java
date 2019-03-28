package com.appstore.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appstore.android.R;
import com.appstore.android.bean.Category;
import com.appstore.android.common.Constants;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.di.component.DaggerCategoryComponent;
import com.appstore.android.di.module.CategoryModule;
import com.appstore.android.presenter.CategoryPresenter;
import com.appstore.android.presenter.contract.CategoryContract;
import com.appstore.android.ui.activity.CategoryAppActivity;
import com.appstore.android.ui.adapter.CategoryAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangqi on 2019/2/18
 */
public class CategoryFragment extends ProgressFragment<CategoryPresenter> implements CategoryContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private CategoryAdapter adapter;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerCategoryComponent.builder()
                .appComponent(appComponent)
                .categoryModule(new CategoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    public void init() {
        initRecyclerView();
        presenter.requestCategories();
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), CategoryAppActivity.class);
                intent.putExtra(Constants.CATEGORY, (Serializable) adapter.getData().get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void showResult(List<Category> categories) {
        adapter.addData(categories);
    }
}
