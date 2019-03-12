package com.appstore.android.presenter;

import com.appstore.android.bean.IndexBean;
import com.appstore.android.common.rx.RxHttpResponseCompat;
import com.appstore.android.common.rx.subscriber.ProgressSubscriber;
import com.appstore.android.data.AppInfoModel;
import com.appstore.android.presenter.contract.AppInfoContract;


import javax.inject.Inject;


/**
 * Created by zhangqi on 2019/2/20
 */
public class RecommendPresenter extends BasePresenter<AppInfoModel, AppInfoContract.View> {

    public static final String TAG = RecommendPresenter.class.getSimpleName();


    @Inject
    public RecommendPresenter(AppInfoModel model, AppInfoContract.View view) {
        super(model, view);
    }

    public void requestDatas() {
        model.index().compose(RxHttpResponseCompat.<IndexBean>compatResult())
                .subscribe(new ProgressSubscriber<IndexBean>(context,view) {
                    @Override
                    public void onNext(IndexBean indexBean) {

                        view.showResult(indexBean);
                    }
                });


        //        RxPermissions rxPermissions = new RxPermissions((Activity) context);
        //
        //        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
        //                .flatMap(new Func1<Boolean, Observable<PageBean<AppInfo>>>() {
        //                    @Override
        //                    public Observable<PageBean<AppInfo>> call(Boolean aBoolean) {
        //
        //                        if (aBoolean) {
        //
        //                            return model.getApps();
        //                        } else {
        //
        //                            return Observable.empty();
        //                        }
        //
        //
        //                    }
        //                })
        //                .subscribeOn(Schedulers.io())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .subscribe(new ProgressSubscriber<PageBean<AppInfo>>(context, view) {
        //                    @Override
        //                    public void onNext(PageBean<AppInfo> appInfoPageBean) {
        //                        view.showResult(appInfoPageBean.getDatas());
        //                    }
        //                });


        //        model.getApps()
        //                .subscribeOn(Schedulers.io())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .subscribe(new ProgressSubscriber<PageBean<AppInfo>>(context, view) {
        //                    @Override
        //                    public void onNext(PageBean<AppInfo> appInfoPageBean) {
        //                        view.showResult(appInfoPageBean.getDatas());
        //                    }
        //                });


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
