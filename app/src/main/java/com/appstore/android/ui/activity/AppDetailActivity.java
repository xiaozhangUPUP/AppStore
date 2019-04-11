package com.appstore.android.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appstore.android.R;
import com.appstore.android.bean.AppInfo;
import com.appstore.android.common.Constants;
import com.appstore.android.common.util.DensityUtil;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.ui.fragment.AppDetailFragment;
import com.bumptech.glide.Glide;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppDetailActivity extends BaseActivity {


    @BindView(R.id.view_content)
    FrameLayout viewContent;
    @BindView(R.id.img_icon)
    ImageView imgIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.view_coordinator)
    CoordinatorLayout viewCoordinator;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.view_temp)
    View viewTemp;

    private AppInfo appInfo;

    @Override
    public int setLayout() {
        return R.layout.activity_app_detail;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void init() {
        appInfo = (AppInfo) getIntent().getSerializableExtra(Constants.APP_INFO_OBJ);
        Glide.with(this)
                .load(Constants.BASE_IMG_URL + appInfo.getIcon())
                .into(imgIcon);
        txtName.setText(appInfo.getDisplayName());

        toolbar.setNavigationIcon(
                new IconicsDrawable(this)
                        .icon(Ionicons.Icon.ion_ios_arrow_back)
                        .sizeDp(16)
                        .color(getResources().getColor(R.color.md_white_1000)
                        )
        );

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        View view = myApplication.getView();
        Bitmap viemBitmapCache = getViemBitmapCache(view);
        if (viemBitmapCache != null) {
            viewTemp.setBackground(new BitmapDrawable(viemBitmapCache));
        }

        int[] outLocation = new int[2];

        // 拿到原来view的坐标
        view.getLocationOnScreen(outLocation);

        int left = outLocation[0];
        int top = outLocation[1];

        int statusBarH = DensityUtil.getStatusBarH(this);

        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        marginLayoutParams.leftMargin = left;
        marginLayoutParams.topMargin = top - statusBarH;
        marginLayoutParams.width = view.getWidth();
        marginLayoutParams.height = view.getHeight();
        int dif = DensityUtil.getScreenH(this) - top;
        if (dif < view.getHeight()) {
            //            marginLayoutParams.topMargin = DensityUtil.getScreenH(this) - view.getHeight() - statusBarH;
            marginLayoutParams.topMargin = top - statusBarH - (view.getHeight() - dif);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(marginLayoutParams);

        // 重新设置view的位置
        viewTemp.setLayoutParams(layoutParams);
        open();
    }

    private void open() {
        int h = DensityUtil.getScreenH(this);
        ObjectAnimator animator = ObjectAnimator.ofFloat(viewTemp, "scaleY", 1f, h);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                viewTemp.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewTemp.setVisibility(View.GONE);
                viewCoordinator.setVisibility(View.VISIBLE);
                initFragment();
            }

        });
        animator.setStartDelay(500);
        animator.setDuration(100);
        animator.start();
    }

    private Bitmap getViemBitmapCache(View view) {

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        if (bitmap == null) {
            return null;
        }

        bitmap = Bitmap.createBitmap(bitmap);
        view.destroyDrawingCache();
        return bitmap;
    }

    private void initFragment() {
        AppDetailFragment appDetailFragment = new AppDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.APP_ID, appInfo.getId());
        appDetailFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.view_content, appDetailFragment);
        transaction.commitAllowingStateLoss();

    }

}
