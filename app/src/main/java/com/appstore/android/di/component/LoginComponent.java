package com.appstore.android.di.component;

import com.appstore.android.di.FragmentScope;
import com.appstore.android.di.module.AppInfoModule;
import com.appstore.android.di.module.LoginModule;
import com.appstore.android.ui.activity.LoginActivity;
import com.appstore.android.ui.fragment.GamesFragment;
import com.appstore.android.ui.fragment.TopListFragment;

import dagger.Component;

/**
 * Created by zhangqi on 2019/2/20
 */
@FragmentScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {

    void inject(LoginActivity activity);
}
