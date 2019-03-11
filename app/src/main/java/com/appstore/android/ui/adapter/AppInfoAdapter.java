package com.appstore.android.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.appstore.android.R;
import com.appstore.android.bean.AppInfo;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by zhangqi on 2019/3/11
 */
public class AppInfoAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder> {
    String baseImgUrl = "http://file.market.xiaomi.com/mfc/thumbnail/png/w150q80/";

    public AppInfoAdapter() {
        super(R.layout.template_appinfo);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppInfo item) {
        ImageView imgView = helper.getView(R.id.img_app_icon);
        Glide.with(imgView.getContext()).load(baseImgUrl + item.getIcon()).into(imgView);
        helper.setText(R.id.txt_app_name, item.getDisplayName())
                .setText(R.id.txt_brief, item.getBriefShow());
    }
}
