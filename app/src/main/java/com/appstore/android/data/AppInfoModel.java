package com.appstore.android.data;

import com.appstore.android.bean.AppInfo;
import com.appstore.android.bean.BaseBean;
import com.appstore.android.bean.IndexBean;
import com.appstore.android.bean.PageBean;
import com.appstore.android.data.http.ApiService;

import retrofit2.Callback;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by zhangqi on 2019/2/20
 */
public class AppInfoModel {
    private ApiService apiService;

    public AppInfoModel(ApiService apiService) {
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

    public Observable<BaseBean<IndexBean>> index() {
        return apiService.index();
    }

    public Observable<BaseBean<PageBean<AppInfo>>> topList(int page) {

        return apiService.topList(page);
    }
}
