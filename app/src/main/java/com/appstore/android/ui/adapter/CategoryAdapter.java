package com.appstore.android.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appstore.android.R;
import com.appstore.android.bean.Category;
import com.appstore.android.common.Constants;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by zhangqi on 2019/3/28
 */
public class CategoryAdapter extends BaseQuickAdapter<Category, BaseViewHolder> {


    public CategoryAdapter() {
        super(R.layout.template_catetory);
    }

    @Override
    protected void convert(BaseViewHolder helper, Category item) {
        helper.setText(R.id.text_name, item.getName());
        ImageView icon = helper.getView(R.id.img_icon);
        Glide.with(icon.getContext()).load(Constants.BASE_IMG_URL + item.getIcon()).into(icon);

    }
}
