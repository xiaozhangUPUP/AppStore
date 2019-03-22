package com.appstore.android.di.module;

import android.app.ProgressDialog;

import com.appstore.android.data.AppInfoModel;
import com.appstore.android.data.LoginModel;
import com.appstore.android.data.http.ApiService;
import com.appstore.android.presenter.contract.AppInfoContract;
import com.appstore.android.presenter.contract.LoginContract;
import com.appstore.android.ui.fragment.RecommendFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqi on 2019/2/20
 */

@Module
public class LoginModule {

    private LoginContract.View view;

    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @Provides
    public LoginContract.View provideView() {
        return view;
    }



    @Provides
    public LoginModel provideModel(ApiService apiService) {
        return new LoginModel(apiService);
    }

}
