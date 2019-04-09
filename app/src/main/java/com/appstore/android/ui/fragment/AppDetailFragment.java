package com.appstore.android.ui.fragment;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appstore.android.R;
import com.appstore.android.bean.AppInfo;
import com.appstore.android.common.Constants;
import com.appstore.android.common.util.DateUtils;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.di.component.DaggerAppDetailComponent;
import com.appstore.android.di.module.AppDetailModule;
import com.appstore.android.di.module.AppModelModule;
import com.appstore.android.presenter.AppDetailPresenter;
import com.appstore.android.presenter.contract.AppInfoContract;
import com.appstore.android.ui.adapter.AppInfoAdapter;
import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangqi on 2019/4/4
 */
public class AppDetailFragment extends ProgressFragment<AppDetailPresenter> implements AppInfoContract.AppDetailView {


    @BindView(R.id.view_gallery)
    LinearLayout viewGallery;
    @BindView(R.id.expandable_text)
    TextView expandableText;
    @BindView(R.id.expand_collapse)
    ImageButton expandCollapse;
    @BindView(R.id.view_introduction)
    ExpandableTextView viewIntroduction;
    @BindView(R.id.txt_update_time)
    TextView txtUpdateTime;
    @BindView(R.id.txt_version)
    TextView txtVersion;
    @BindView(R.id.txt_apk_size)
    TextView txtApkSize;
    @BindView(R.id.txt_publisher)
    TextView txtPublisher;
    @BindView(R.id.txt_publisher2)
    TextView txtPublisher2;
    @BindView(R.id.recycler_view_same_dev)
    RecyclerView recyclerViewSameDev;
    @BindView(R.id.recycler_view_relate)
    RecyclerView recyclerViewRelate;

    private int appId;
    private AppInfoAdapter adapter;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerAppDetailComponent.builder()
                .appComponent(appComponent)
                .appDetailModule(new AppDetailModule(this))
                .appModelModule(new AppModelModule())
                .build()
                .inject(this);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_app_detail;
    }

    @Override
    public void init() {
        Bundle bundle = this.getArguments();
        appId = bundle.getInt(Constants.APP_ID);
        presenter.getAppDetail(appId);
    }

    @Override
    public void showAppDetail(AppInfo appInfo) {
        showScreenshot(appInfo.getScreenshot());
        viewIntroduction.setText(appInfo.getIntroduction());
        txtUpdateTime.setText(DateUtils.formatDate(appInfo.getUpdateTime()));
        txtApkSize.setText((appInfo.getApkSize() / 1024 / 1024) + " MB");
        txtVersion.setText(appInfo.getVersionName());
        txtPublisher.setText(appInfo.getPublisherName());
        txtPublisher2.setText(appInfo.getPublisherName());


        adapter = AppInfoAdapter.builder()
                .setLayout(R.layout.template_appinfo2)
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSameDev.setLayoutManager(layoutManager);
        adapter.addData(appInfo.getSameDevAppInfoList());
        recyclerViewSameDev.setAdapter(adapter);

        adapter = AppInfoAdapter.builder()
                .setLayout(R.layout.template_appinfo2)
                .build();

        recyclerViewRelate.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter.addData(appInfo.getRelateAppInfoList());
        recyclerViewRelate.setAdapter(adapter);

    }

    private void showScreenshot(String screenshot) {
        String[] urls = screenshot.split(",");
        for (String url : urls) {
            ImageView imageView = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.template_imageview, viewGallery, false);
            Glide.with(getActivity()).load(Constants.BASE_IMG_URL + url).into(imageView);
            viewGallery.addView(imageView);
        }
    }


}
