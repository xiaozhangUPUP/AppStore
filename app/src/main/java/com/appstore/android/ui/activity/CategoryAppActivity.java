package com.appstore.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.appstore.android.R;
import com.appstore.android.bean.Category;
import com.appstore.android.common.AppIcons;
import com.appstore.android.common.Constants;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.ui.adapter.CategoryAppViewPagerAdapter;
import com.appstore.android.ui.fragment.CategoryAppFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAppActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.activity_cateogry_app)
    LinearLayout activityCateogryApp;

    private Category category;


    @Override
    public int setLayout() {
        return R.layout.activity_category_app;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {
        getData();
        initTablayout();
    }

    private void initTablayout() {
        toolBar.setTitle(category.getName());
        toolBar.setNavigationIcon(new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_ios_arrow_back)
                .sizeDp(16).color(getResources().getColor(R.color.md_white_1000)));

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (category != null) {
            CategoryAppViewPagerAdapter adapter = new CategoryAppViewPagerAdapter(getSupportFragmentManager(), category.getId());
            viewPager.setOffscreenPageLimit(adapter.getCount());
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }

    }


    private void getData() {
        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra(Constants.CATEGORY);

    }
}
