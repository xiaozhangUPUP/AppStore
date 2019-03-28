package com.appstore.android.presenter.contract;

import com.appstore.android.bean.Category;
import com.appstore.android.bean.LoginBean;
import com.appstore.android.ui.BaseView;

import java.util.List;

/**
 * Created by zhangqi on 2019/3/22
 */
public interface CategoryContract {

    public interface View extends BaseView {
        void showResult(List<Category> categories);
    }
}
