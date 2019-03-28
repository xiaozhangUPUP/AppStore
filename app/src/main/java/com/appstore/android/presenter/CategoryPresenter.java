package com.appstore.android.presenter;

import com.appstore.android.bean.Category;
import com.appstore.android.common.rx.RxHttpResponseCompat;
import com.appstore.android.common.rx.subscriber.ProgressSubscriber;
import com.appstore.android.data.CategoryModel;
import com.appstore.android.presenter.contract.CategoryContract;

import java.util.List;

import javax.inject.Inject;

public class CategoryPresenter extends BasePresenter<CategoryModel, CategoryContract.View> {

    @Inject
    public CategoryPresenter(CategoryModel model, CategoryContract.View view) {
        super(model, view);
    }


    public void requestCategories() {
        model.getCategories()
                .compose(RxHttpResponseCompat.<List<Category>>compatResult())
                .subscribe(new ProgressSubscriber<List<Category>>(context, view) {
                    @Override
                    public void onNext(List<Category> categories) {
                        view.showResult(categories);
                    }
                });
    }

}
