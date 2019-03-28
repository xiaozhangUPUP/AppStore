package com.appstore.android.di.module;

import com.appstore.android.data.http.ApiService;
import com.appstore.android.data.CategoryModel;
import com.appstore.android.presenter.contract.CategoryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqi on 2019/2/20
 */

@Module
public class CategoryModule {

    private CategoryContract.View view;

    public CategoryModule(CategoryContract.View view) {
        this.view = view;
    }

    @Provides
    public CategoryContract.View provideView() {
        return view;
    }



    @Provides
    public CategoryModel provideModel(ApiService apiService) {
        return new CategoryModel(apiService);
    }

}
