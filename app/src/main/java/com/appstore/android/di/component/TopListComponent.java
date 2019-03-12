package com.appstore.android.di.component;

import com.appstore.android.data.AppInfoModel;
import com.appstore.android.di.FragmentScope;
import com.appstore.android.di.module.RecommendModule;
import com.appstore.android.di.module.TopListModule;
import com.appstore.android.ui.fragment.RecommendFragment;
import com.appstore.android.ui.fragment.TopListFragment;

import dagger.Component;

/**
 * Created by zhangqi on 2019/2/20
 */
@FragmentScope
@Component(modules = TopListModule.class, dependencies = AppComponent.class)
public interface TopListComponent {
    void inject(TopListFragment fragment);
}
