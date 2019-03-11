package com.appstore.android.data.http;

import com.appstore.android.bean.AppInfo;
import com.appstore.android.bean.BaseBean;
import com.appstore.android.bean.IndexBean;
import com.appstore.android.bean.PageBean;
import com.appstore.android.bean.requestbean.LoginRequestBean;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {
    public static final String BASE_URL = "http://112.124.22.238:8081/course_api/cniaoplay/";

    //    @GET("featured2")
    //    public Observable<BaseBean<PageBean<AppInfo>>> getApps(@Query("p") String jsonParam);


    @GET("featured")
    public Observable<PageBean<AppInfo>> getApps(@Query("p") String jsonParam);

    @POST("login")
    public Observable<BaseBean> login(@Body LoginRequestBean requestBean);

    @GET("index")
    public  Observable<BaseBean<IndexBean>> index();
}
