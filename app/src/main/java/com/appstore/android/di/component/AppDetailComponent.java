package com.appstore.android.di.component;

import com.appstore.android.di.FragmentScope;
import com.appstore.android.di.module.AppDetailModule;
import com.appstore.android.di.module.AppInfoModule;
import com.appstore.android.ui.fragment.AppDetailFragment;
import com.appstore.android.ui.fragment.CategoryAppFragment;
import com.appstore.android.ui.fragment.GamesFragment;
import com.appstore.android.ui.fragment.TopListFragment;

import dagger.Component;

/**
 * Created by zhangqi on 2019/2/20
 */
@FragmentScope
@Component(modules = AppDetailModule.class, dependencies = AppComponent.class)
public interface AppDetailComponent {
    void inject(AppDetailFragment fragment);

}
