package com.appstore.android.di.component;

import com.appstore.android.di.FragmentScope;
import com.appstore.android.di.module.AppModule;
import com.appstore.android.di.module.RecommendModule;
import com.appstore.android.ui.fragment.RecommendFragment;

import dagger.Component;

/**
 * Created by zhangqi on 2019/2/20
 */
@FragmentScope
@Component(modules = RecommendModule.class, dependencies = AppComponent.class)
public interface RecommendComponent {
    void inject(RecommendFragment fragment);
}
