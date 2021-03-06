package com.appstore.android.di.module;

import com.appstore.android.data.AppInfoModel;
import com.appstore.android.data.http.ApiService;
import com.appstore.android.presenter.contract.AppInfoContract;
import com.appstore.android.ui.adapter.AppInfoAdapter;

import dagger.Module;
import dagger.Provides;

@Module(includes = AppModelModule.class)
public class AppInfoModule {

    private AppInfoContract.AppInfoView view;

    public AppInfoModule(AppInfoContract.AppInfoView view) {
        this.view = view;
    }

    @Provides
    public AppInfoContract.AppInfoView provideView() {
        return view;
    }


}
