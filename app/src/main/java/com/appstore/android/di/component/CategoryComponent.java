package com.appstore.android.di.component;

import com.appstore.android.di.FragmentScope;
import com.appstore.android.di.module.CategoryModule;
import com.appstore.android.di.module.LoginModule;
import com.appstore.android.ui.activity.LoginActivity;
import com.appstore.android.ui.fragment.CategoryFragment;

import dagger.Component;

/**
 * Created by zhangqi on 2019/2/20
 */
@FragmentScope
@Component(modules = CategoryModule.class, dependencies = AppComponent.class)
public interface CategoryComponent {

    void inject(CategoryFragment fragment);
}
