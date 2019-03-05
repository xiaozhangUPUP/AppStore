package com.appstore.android.di.component;

import android.app.Application;

import com.appstore.android.common.rx.RxErrorHandler;
import com.appstore.android.data.http.ApiService;
import com.appstore.android.di.module.AppModule;
import com.appstore.android.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    public ApiService getApiService();

    public Application getApplication();

    public RxErrorHandler getRxErrorHandler();
}
