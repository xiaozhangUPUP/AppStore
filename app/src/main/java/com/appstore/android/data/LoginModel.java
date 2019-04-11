package com.appstore.android.data;

import com.appstore.android.bean.AppInfo;
import com.appstore.android.bean.BaseBean;
import com.appstore.android.bean.IndexBean;
import com.appstore.android.bean.LoginBean;
import com.appstore.android.bean.PageBean;
import com.appstore.android.bean.requestbean.LoginRequestBean;
import com.appstore.android.data.http.ApiService;

import io.reactivex.Observable;


/**
 * Created by zhangqi on 2019/2/20
 */
public class LoginModel {
    private ApiService apiService;

    public LoginModel(ApiService apiService) {
        this.apiService = apiService;
    }


    public Observable<BaseBean<LoginBean>> login(String phone, String pwd) {

        LoginRequestBean param = new LoginRequestBean();
        param.setEmail(phone);
        param.setPassWord(pwd);

        return apiService.login(param);
    }

}
