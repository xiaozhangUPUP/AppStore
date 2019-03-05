package com.appstore.android.di.module;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.appstore.android.data.RecommendModel;
import com.appstore.android.data.http.ApiService;
import com.appstore.android.presenter.RecommendPresenter;
import com.appstore.android.presenter.contract.RecommendContract;
import com.appstore.android.ui.fragment.RecommendFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqi on 2019/2/20
 */

@Module
public class RecommendModule {

    private RecommendContract.View view;

    public RecommendModule(RecommendContract.View view) {
        this.view = view;
    }

    @Provides
    public RecommendContract.View provideView() {
        return view;
    }

    //    @Provides
    //    public RecommendPresenter providePresenter(RecommendContract.View view, RecommendModel model) {
    //        return new RecommendPresenter(model, view);
    //    }

    @Provides
    public ProgressDialog provideProgressDialog(RecommendContract.View view) {
        return new ProgressDialog(((RecommendFragment) view).getActivity());
    }

    @Provides
    public RecommendModel provideModel(ApiService apiService) {
        return new RecommendModel(apiService);
    }

}
