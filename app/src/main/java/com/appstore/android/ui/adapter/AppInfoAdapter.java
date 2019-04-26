package com.appstore.android.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstore.android.R;
import com.appstore.android.bean.AppInfo;
import com.appstore.android.ui.widget.DownloadProgressButton;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by zhangqi on 2019/3/11
 */
public class AppInfoAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder> {
    String baseImgUrl = "http://file.market.xiaomi.com/mfc/thumbnail/png/w150q80/";

    private Builder builder;

    private AppInfoAdapter(Builder builder) {
        super(builder.layoutId);
        this.builder = builder;
        openLoadAnimation();
    }

    public static Builder builder() {

        return new Builder();
    }

    @Override
    protected void convert(BaseViewHolder helper, AppInfo item) {
        ImageView imgView = helper.getView(R.id.img_app_icon);
        Glide.with(imgView.getContext()).load(baseImgUrl + item.getIcon()).into(imgView);
        helper.setText(R.id.txt_app_name, item.getDisplayName());

        TextView tvPosition = helper.getView(R.id.txt_position);
        if (tvPosition != null) {
            tvPosition.setVisibility(builder.isShowPosition ? View.VISIBLE : View.GONE);
            tvPosition.setText(item.getPosition() + 1 + ". ");
        }

        TextView tvCategory = helper.getView(R.id.txt_category);
        if (tvCategory != null) {
            tvCategory.setVisibility(builder.isShowCategoryName ? View.VISIBLE : View.GONE);
            tvCategory.setText(item.getLevel1CategoryName());
        }

        TextView tvBrief = helper.getView(R.id.txt_brief);
        if (tvBrief != null) {
            tvBrief.setVisibility(builder.isShowBrief ? View.VISIBLE : View.GONE);
            tvBrief.setText(item.getBriefShow());
        }

        TextView tvViewSize = helper.getView(R.id.txt_apk_size);
        if (tvViewSize != null) {
            tvViewSize.setText(item.getApkSize() / 1024 / 1024 + "MB");
        }

        DownloadProgressButton btn = helper.getView(R.id.btn_download);
        
    }


    public static class Builder {

        private boolean isShowPosition;
        private boolean isShowCategoryName;
        private boolean isShowBrief;

        private int layoutId = R.layout.template_appinfo;

        public Builder setLayout(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        public Builder showPosition(boolean b) {
            this.isShowPosition = b;
            return this;
        }

        public Builder showCategoryName(boolean b) {
            this.isShowCategoryName = b;
            return this;
        }

        public Builder showBrief(boolean b) {
            this.isShowBrief = b;
            return this;
        }

        public AppInfoAdapter build() {

            return new AppInfoAdapter(this);
        }


    }
}
