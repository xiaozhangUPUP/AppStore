package com.appstore.android.di.module;

import com.appstore.android.data.AppInfoModel;
import com.appstore.android.data.http.ApiService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqi on 2019/4/8
 */
@Module
public class AppModelModule {
    @Provides
    public AppInfoModel privodeModel(ApiService apiService) {

        return new AppInfoModel(apiService);
    }

}
