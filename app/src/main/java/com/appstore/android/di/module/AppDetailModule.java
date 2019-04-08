package com.appstore.android.di.module;

import com.appstore.android.data.AppInfoModel;
import com.appstore.android.data.http.ApiService;
import com.appstore.android.presenter.contract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

@Module(includes = AppModelModule.class)
public class AppDetailModule {

    private AppInfoContract.AppDetailView view;

    public AppDetailModule(AppInfoContract.AppDetailView view) {
        this.view = view;
    }

    @Provides
    public AppInfoContract.AppDetailView provideView() {
        return view;
    }

}
