package com.appstore.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appstore.android.R;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.di.component.DaggerAppInfoComponent;
import com.appstore.android.di.module.AppInfoModule;
import com.appstore.android.presenter.AppInfoPresenter;
import com.appstore.android.ui.adapter.AppInfoAdapter;

/**
 * Created by zhangqi on 2019/2/18
 */
public class GamesFragment extends BaseAppInfoFragment {

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerAppInfoComponent.builder()
                .appComponent(appComponent)
                .appInfoModule(new AppInfoModule(this))
                .build()
                .injectGamesFragment(this);
    }

    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter
                .builder()
                .showBrief(true)
                .showCategoryName(true)
                .build();
    }

    @Override
    int type() {
        return AppInfoPresenter.GAME;
    }

}
