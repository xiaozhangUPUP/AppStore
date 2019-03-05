package com.appstore.android;

import android.app.Application;
import android.content.Context;

import com.appstore.android.di.component.AppComponent;
import com.appstore.android.di.component.DaggerAppComponent;
import com.appstore.android.di.module.AppModule;
import com.appstore.android.di.module.HttpModule;

public class MyApplication extends Application {

    private AppComponent appComponent;
    private static Context context;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).httpModule(new HttpModule()).build();
    }

    public static Context getContext() {
        return context;
    }
}
