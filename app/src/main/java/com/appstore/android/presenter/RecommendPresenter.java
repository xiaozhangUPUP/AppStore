package com.appstore.android.presenter;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.appstore.android.bean.AppInfo;
import com.appstore.android.bean.BaseBean;
import com.appstore.android.bean.PageBean;
import com.appstore.android.common.rx.RxErrorHandler;
import com.appstore.android.common.rx.RxHttpResponseCompat;
import com.appstore.android.common.rx.subscriber.ErrorHandlerSubscriber;
import com.appstore.android.common.rx.subscriber.ProgressDialogSubscriber;
import com.appstore.android.common.rx.subscriber.ProgressSubscriber;
import com.appstore.android.data.RecommendModel;
import com.appstore.android.presenter.contract.RecommendContract;


import javax.inject.Inject;

import rx.Emitter;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.observables.AsyncOnSubscribe;
import rx.schedulers.Schedulers;


/**
 * Created by zhangqi on 2019/2/20
 */
public class RecommendPresenter extends BasePresenter<RecommendModel, RecommendContract.View> {

    public static final String TAG = RecommendPresenter.class.getSimpleName();


    @Inject
    public RecommendPresenter(RecommendModel model, RecommendContract.View view) {
        super(model, view);
    }

    public void requestDatas() {
        model.getApps()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<PageBean<AppInfo>>(context, view) {
                    @Override
                    public void onNext(PageBean<AppInfo> appInfoPageBean) {
                        view.showResult(appInfoPageBean.getDatas());
                    }
                });


        //        model.getApps()
        //                .compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
        //                .subscribe(new ProgressSubscriber<PageBean<AppInfo>>(context, view) {
        //                    @Override
        //                    public void onNext(PageBean<AppInfo> appInfoPageBean) {
        //                        view.showResult(appInfoPageBean.getDatas());
        //                    }
        //                });
    }
}
