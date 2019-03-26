package com.appstore.android;

import android.app.Application;
import android.content.Context;

import com.appstore.android.common.AppIcons;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.di.component.DaggerAppComponent;
import com.appstore.android.di.module.AppModule;
import com.appstore.android.di.module.HttpModule;
import com.mikepenz.iconics.Iconics;

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

        //only required if you add a custom or generic font on your own
        Iconics.init(getApplicationContext());

        //register custom fonts like this (or also provide a font definition file)
        Iconics.registerFont(new AppIcons());
    }

    public static Context getContext() {
        return context;
    }
}
