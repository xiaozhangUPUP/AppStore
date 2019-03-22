package com.appstore.android.presenter.contract;

import com.appstore.android.bean.LoginBean;
import com.appstore.android.ui.BaseView;

/**
 * Created by zhangqi on 2019/3/22
 */
public interface LoginContract {

    public interface View extends BaseView {
        void checkPhoneError();
        void checkPhoneSuccess();

        void loginSuccess(LoginBean bean);
    }
}
