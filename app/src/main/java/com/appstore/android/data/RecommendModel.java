package com.appstore.android.data;

import com.appstore.android.bean.AppInfo;
import com.appstore.android.bean.BaseBean;
import com.appstore.android.bean.PageBean;
import com.appstore.android.data.http.ApiService;

import retrofit2.Callback;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by zhangqi on 2019/2/20
 */
public class RecommendModel {
    private ApiService apiService;

    public RecommendModel(ApiService apiService) {
        this.apiService = apiService;
    }


    //    public Observable<BaseBean<PageBean<AppInfo>>> getApps() {
    //
    //        return apiService.getApps("{'page':0}");
    //
    //    }


    public Observable<PageBean<AppInfo>> getApps() {

        return apiService.getApps("{'page':0}");

    }
}
