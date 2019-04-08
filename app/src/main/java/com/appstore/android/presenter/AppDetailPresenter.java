package com.appstore.android.presenter;

import com.appstore.android.bean.AppInfo;
import com.appstore.android.common.rx.RxHttpResponseCompat;
import com.appstore.android.common.rx.subscriber.ProgressSubscriber;
import com.appstore.android.data.AppInfoModel;
import com.appstore.android.presenter.contract.AppInfoContract;

import javax.inject.Inject;

/**
 * Created by zhangqi on 2019/4/4
 */
public class AppDetailPresenter extends BasePresenter<AppInfoModel, AppInfoContract.AppDetailView> {

    @Inject
    public AppDetailPresenter(AppInfoModel model, AppInfoContract.AppDetailView view) {
        super(model, view);
    }

    public void getAppDetail(int id) {
        model.getAppDetail(id)
                .compose(RxHttpResponseCompat.<AppInfo>compatResult())
                .subscribe(new ProgressSubscriber<AppInfo>(context, view) {
                    @Override
                    public void onNext(AppInfo appInfo) {
                        view.showAppDetail(appInfo);
                    }
                });
    }

}
