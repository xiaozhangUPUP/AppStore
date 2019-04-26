package com.appstore.android.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import zlc.season.rxdownload2.RxDownload;

/**
 * Created by zhangqi on 2019/4/26
 */
@Module
public class DownloadModule {

    @Provides
    @Singleton
    public RxDownload provideRxDownLoad() {
        return null;
    }
}
