package com.appstore.android.di.module;

import android.app.ProgressDialog;

import com.appstore.android.data.AppInfoModel;
import com.appstore.android.data.http.ApiService;
import com.appstore.android.presenter.contract.AppInfoContract;
import com.appstore.android.ui.fragment.RecommendFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqi on 2019/2/20
 */

@Module
public class RecommendModule {

    private AppInfoContract.View view;

    public RecommendModule(AppInfoContract.View view) {
        this.view = view;
    }

    @Provides
    public AppInfoContract.View provideView() {
        return view;
    }

    //    @Provides
    //    public RecommendPresenter providePresenter(AppInfoContract.View view, AppInfoModel model) {
    //        return new RecommendPresenter(model, view);
    //    }

    @Provides
    public ProgressDialog provideProgressDialog(AppInfoContract.View view) {
        return new ProgressDialog(((RecommendFragment) view).getActivity());
    }

    @Provides
    public AppInfoModel provideModel(ApiService apiService) {
        return new AppInfoModel(apiService);
    }

}
