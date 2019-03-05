package com.appstore.android.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import com.appstore.android.MyApplication;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.presenter.BasePresenter;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangqi on 2019/2/22
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    private Unbinder unbinder;
    private MyApplication myApplication;

    @Inject
    T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(setLayout());

        unbinder = ButterKnife.bind(this);
        myApplication = (MyApplication) getApplication();

        setupActivityComponent(myApplication.getAppComponent());
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
    }

    public abstract int setLayout();

    public abstract void setupActivityComponent(AppComponent appComponent);

    public abstract void init();
}
