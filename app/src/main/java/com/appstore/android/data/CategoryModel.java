package com.appstore.android.data;

import com.appstore.android.bean.BaseBean;
import com.appstore.android.bean.Category;
import com.appstore.android.bean.LoginBean;
import com.appstore.android.bean.requestbean.LoginRequestBean;
import com.appstore.android.data.http.ApiService;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by zhangqi on 2019/2/20
 */
public class CategoryModel {
    private ApiService apiService;

    public CategoryModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<BaseBean<List<Category>>> getCategories() {
        return apiService.getCategories();
    }

}
