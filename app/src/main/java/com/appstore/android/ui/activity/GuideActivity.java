package com.appstore.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appstore.android.R;
import com.appstore.android.common.Constants;
import com.appstore.android.common.util.DensityUtil;
import com.appstore.android.common.util.SharePreferencesUtils;
import com.appstore.android.ui.adapter.GuideFragmentAdapter;
import com.appstore.android.ui.fragment.GuideFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.guide_viewpager)
    ViewPager guideViewpager;
    @BindView(R.id.guide_btn_enter)
    Button guideBtnEnter;
    @BindView(R.id.indicator)
    LinearLayout indicator;
    private GuideFragmentAdapter guideFragmentAdapter;

    private int prePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(GuideFragment.newInstance(R.drawable.guide_1, R.color.color_bg_guide1, R.string.guide_1));
        fragments.add(GuideFragment.newInstance(R.drawable.guide_2, R.color.color_bg_guide2, R.string.guide_2));
        fragments.add(GuideFragment.newInstance(R.drawable.guide_3, R.color.color_bg_guide3, R.string.guide_3));

        for (int i = 0; i < fragments.size(); i++) {
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.guide_drawable_selector);
            int width = DensityUtil.dip2px(this, 8);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
                params.leftMargin = width;
            }
            point.setLayoutParams(params);
            indicator.addView(point);
        }

        guideFragmentAdapter = new GuideFragmentAdapter(getSupportFragmentManager(), fragments);

        guideViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                guideBtnEnter.setVisibility(i == guideFragmentAdapter.getCount() - 1 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageSelected(int i) {
                indicator.getChildAt(prePosition).setEnabled(false);
                indicator.getChildAt(i).setEnabled(true);
                prePosition = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

//        guideViewpager.setPageTransformer();

        guideViewpager.setAdapter(guideFragmentAdapter);
    }

    @OnClick(R.id.guide_btn_enter)
    public void onViewClicked() {
        jumpMain();
    }

    private void jumpMain() {
        SharePreferencesUtils.saveString(Constants.IS_SHOW_GUIDE, "false");
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        finish();
    }
}
