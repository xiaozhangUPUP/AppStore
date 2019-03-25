package com.appstore.android.presenter;

import com.appstore.android.bean.AppInfo;
import com.appstore.android.bean.BaseBean;
import com.appstore.android.bean.LoginBean;
import com.appstore.android.bean.PageBean;
import com.appstore.android.common.Constants;
import com.appstore.android.common.rx.RxHttpResponseCompat;
import com.appstore.android.common.rx.subscriber.ErrorHandlerSubscriber;
import com.appstore.android.common.rx.subscriber.ProgressSubscriber;
import com.appstore.android.common.util.ACache;
import com.appstore.android.common.util.SharePreferencesUtils;
import com.appstore.android.common.util.VerificationUtils;
import com.appstore.android.data.AppInfoModel;
import com.appstore.android.data.LoginModel;
import com.appstore.android.presenter.contract.AppInfoContract;
import com.appstore.android.presenter.contract.LoginContract;
import com.hwangjr.rxbus.RxBus;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class LoginPresenter extends BasePresenter<LoginModel, LoginContract.View> {

    @Inject
    public LoginPresenter(LoginModel model, LoginContract.View view) {
        super(model, view);
    }


    public void login(String phone, String password) {
        if (!VerificationUtils.matcherPhoneNum(phone)) {
            view.checkPhoneError();
            return;
        } else {
            view.checkPhoneSuccess();
        }

        model.login(phone, password)
                .compose(RxHttpResponseCompat.<LoginBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<LoginBean>(context) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        view.loginSuccess(loginBean);
                        saveUser(loginBean);
                        RxBus.get().post(loginBean.getUser());
                    }
                });

    }

    private void saveUser(LoginBean loginBean) {
        ACache aCache = ACache.get(context);
        aCache.put(Constants.TOKEN, loginBean.getToken());
        aCache.put(Constants.USER, loginBean.getUser());

    }


}
