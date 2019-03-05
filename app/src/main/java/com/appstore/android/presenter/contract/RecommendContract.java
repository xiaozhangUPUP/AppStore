package com.appstore.android.presenter.contract;

import com.appstore.android.bean.AppInfo;
import com.appstore.android.presenter.BasePresenter;
import com.appstore.android.ui.BaseView;

import java.util.List;

/**
 * Created by zhangqi on 2019/2/20
 */
public interface RecommendContract {

    interface View extends BaseView {

        void showNoData();

        void showResult(List<AppInfo> datas);
    }

}
