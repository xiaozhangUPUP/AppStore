package com.appstore.android.presenter;

import com.appstore.android.bean.AppInfo;
import com.appstore.android.bean.BaseBean;
import com.appstore.android.bean.PageBean;
import com.appstore.android.common.rx.RxHttpResponseCompat;
import com.appstore.android.common.rx.subscriber.ErrorHandlerSubscriber;
import com.appstore.android.common.rx.subscriber.ProgressSubscriber;
import com.appstore.android.data.AppInfoModel;
import com.appstore.android.presenter.contract.AppInfoContract;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class AppInfoPresenter extends BasePresenter<AppInfoModel, AppInfoContract.AppInfoView> {

    public static final int TOP_LIST = 1;
    public static final int GAME = 2;
    public static final int CATEGORY = 3;

    public static final int FEATURED = 0;
    public static final int TOPLIST = 1;
    public static final int NEWLIST = 2;

    @Inject
    public AppInfoPresenter(AppInfoModel model, AppInfoContract.AppInfoView view) {
        super(model, view);
    }


    public void requestData(int type, int page) {
        request(type, page, 0, 0);
    }

    public void requestCategoryApps(int categoryId, int page, int fragType) {
        request(CATEGORY, page, categoryId, fragType);
    }

    public void request(int type, int page, int categoryId, int fragType) {

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

        Observable observable = getObservable(type, page, categoryId, fragType);

        observable
                .compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
                .subscribe(subscriber);
    }

    private Observable<BaseBean<PageBean<AppInfo>>> getObservable(int type, int page, int categoryId, int fragType) {
        switch (type) {
            case TOP_LIST:
                return model.topList(page);
            case GAME:
                return model.games(page);
            case CATEGORY:
                if (fragType == FEATURED) {
                    return model.getFeaturedAppsByCategory(categoryId, page);
                } else if (fragType == TOPLIST) {
                    return model.getTopListAppsByCategory(categoryId, page);
                } else if (fragType == NEWLIST) {
                    return model.getNewListAppsByCategory(categoryId, page);
                }
            default:
                return Observable.empty();
        }

    }


}
