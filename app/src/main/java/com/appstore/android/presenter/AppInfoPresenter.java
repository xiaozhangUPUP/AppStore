package com.appstore.android.presenter;

import com.appstore.android.bean.AppInfo;
import com.appstore.android.bean.PageBean;
import com.appstore.android.common.rx.RxHttpResponseCompat;
import com.appstore.android.common.rx.subscriber.ErrorHandlerSubscriber;
import com.appstore.android.common.rx.subscriber.ProgressSubscriber;
import com.appstore.android.data.AppInfoModel;
import com.appstore.android.presenter.contract.AppInfoContract;

import javax.inject.Inject;

import rx.Subscriber;

public class AppInfoPresenter extends BasePresenter<AppInfoModel, AppInfoContract.AppInfoView> {

    @Inject
    public AppInfoPresenter(AppInfoModel model, AppInfoContract.AppInfoView view) {
        super(model, view);
    }

    public void getTopListApps(int page) {

        Subscriber subscriber = null;
        if (page == 0) {
            // 第一页显示loading -----
            subscriber = new ProgressSubscriber<PageBean<AppInfo>>(context, view) {
                @Override
                public void onNext(PageBean<AppInfo> appInfoPageBean) {
                    view.showResult(appInfoPageBean);
                }
            };
        } else {
            // 加载下一页
            subscriber = new ErrorHandlerSubscriber<PageBean<AppInfo>>(context) {
                @Override
                public void onCompleted() {

                    view.onLoadMoreComplete();
                }

                @Override
                public void onNext(PageBean<AppInfo> pageBean) {
                    view.showResult(pageBean);
                }
            };

        }

        model.topList(page)
                .compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
                .subscribe(subscriber);
    }
}
