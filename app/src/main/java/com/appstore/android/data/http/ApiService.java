package com.appstore.android.data.http;

import com.appstore.android.bean.AppInfo;
import com.appstore.android.bean.BaseBean;
import com.appstore.android.bean.Category;
import com.appstore.android.bean.IndexBean;
import com.appstore.android.bean.LoginBean;
import com.appstore.android.bean.PageBean;
import com.appstore.android.bean.requestbean.LoginRequestBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {
    public static final String BASE_URL = "http://112.124.22.238:8081/course_api/cniaoplay/";

    //    @GET("featured2")
    //    public Observable<BaseBean<PageBean<AppInfo>>> getApps(@Query("p") String jsonParam);


    @GET("featured")
    public Observable<PageBean<AppInfo>> getApps(@Query("p") String jsonParam);

    @POST("login")
    public Observable<BaseBean<LoginBean>> login(@Body LoginRequestBean requestBean);

    @GET("index")
    public Observable<BaseBean<IndexBean>> index();

    @GET("toplist")
    public Observable<BaseBean<PageBean<AppInfo>>> topList(@Query("page") int page);

    @GET("game")
    public Observable<BaseBean<PageBean<AppInfo>>> games(@Query("page") int page);

    @GET("category")
    Observable<BaseBean<List<Category>>> getCategories();

    @GET("category/featured/{categoryid}")
    Observable<BaseBean<PageBean<AppInfo>>> getFeaturedAppsByCategory(@Path("categoryid") int categoryid, @Query("page") int page);

    @GET("category/toplist/{categoryid}")
    Observable<BaseBean<PageBean<AppInfo>>> getTopListAppsByCategory(@Path("categoryid") int categoryid,@Query("page") int page);

    @GET("category/newlist/{categoryid}")
    Observable<BaseBean<PageBean<AppInfo>>> getNewListAppsByCategory(@Path("categoryid") int categoryid,@Query("page") int page);

    @GET("app/{id}")
    Observable<BaseBean<AppInfo>> getAppDetail(@Path("id") int id);
}
