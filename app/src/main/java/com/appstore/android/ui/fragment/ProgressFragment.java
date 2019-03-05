package com.appstore.android.ui.fragment;

import android.accounts.AbstractAccountAuthenticator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.appstore.android.MyApplication;
import com.appstore.android.R;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.presenter.BasePresenter;
import com.appstore.android.ui.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangqi on 2019/3/4
 */
public abstract class ProgressFragment<T extends BasePresenter> extends Fragment implements BaseView {

    private FrameLayout rootView;
    private View viewProgress;
    private FrameLayout viewContent;
    private View viewEmpty;
    private Unbinder unbinder;
    private TextView text_tip;

    private MyApplication myApplication;

    @Inject
    T presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_progress, container, false);
        viewProgress = rootView.findViewById(R.id.view_progress);
        viewContent = rootView.findViewById(R.id.view_content);
        viewEmpty = rootView.findViewById(R.id.view_empty);
        text_tip = rootView.findViewById(R.id.text_tip);

        viewEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmptyViewClick();
            }
        });
        return rootView;
    }

    public void onEmptyViewClick() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myApplication = (MyApplication) this.getActivity().getApplication();
        setupActivityComponent(myApplication.getAppComponent());
        setRealContentView();

        init();
    }

    private void setRealContentView() {
        View realContentView = LayoutInflater.from(getActivity()).inflate(setLayout(), viewContent, true);
        unbinder = ButterKnife.bind(this, realContentView);
    }

    public void showProgressView() {
        showView(R.id.view_progress);
    }

    public void showContentView() {
        showView(R.id.view_content);

    }

    public void showEmptyView() {
        showView(R.id.view_empty);
    }

    public void showEmptyView(int resId) {
        showEmptyView(getString(resId));
    }

    public void showEmptyView(String msg) {
        showView(R.id.view_empty);
        text_tip.setText(msg);
    }

    public void showView(int viewId) {
        for (int i = 0; i < rootView.getChildCount(); i++) {
            if (rootView.getChildAt(i).getId() == viewId) {
                rootView.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                rootView.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showLoading() {
        showProgressView();
    }

    @Override
    public void showError(String msg) {
        showEmptyView(msg);
    }

    @Override
    public void dismissLoading() {
        showContentView();
    }

    public abstract void setupActivityComponent(AppComponent appComponent);

    public abstract int setLayout();

    public abstract void init();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
    }
}
