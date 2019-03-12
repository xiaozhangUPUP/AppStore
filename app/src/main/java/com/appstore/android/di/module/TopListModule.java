package com.appstore.android.di.module;

import com.appstore.android.data.AppInfoModel;
import com.appstore.android.data.http.ApiService;
import com.appstore.android.presenter.contract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

@Module
public class TopListModule {

    private AppInfoContract.AppInfoView view;

    public TopListModule(AppInfoContract.AppInfoView view) {
        this.view = view;
    }

    @Provides
    public AppInfoContract.AppInfoView provideView() {
        return view;
    }

    @Provides
    public AppInfoModel provideModel(ApiService apiService) {
        return new AppInfoModel(apiService);
    }
}
