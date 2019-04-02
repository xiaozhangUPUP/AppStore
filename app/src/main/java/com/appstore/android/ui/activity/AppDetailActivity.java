package com.appstore.android.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.appstore.android.R;
import com.appstore.android.common.util.DensityUtil;
import com.appstore.android.di.component.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppDetailActivity extends BaseActivity {


    @BindView(R.id.view_content)
    FrameLayout viewContent;

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
        View view = myApplication.getView();
        Bitmap viemBitmapCache = getViemBitmapCache(view);
        if (viemBitmapCache != null) {
            viewContent.setBackground(new BitmapDrawable(viemBitmapCache));
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
        viewContent.setLayoutParams(layoutParams);
        open();
    }

    private void open() {
        int h = DensityUtil.getScreenH(this);
        ObjectAnimator animator = ObjectAnimator.ofFloat(viewContent, "scaleY", 1f, h);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                viewContent.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

        });
        animator.setStartDelay(2000);
        animator.setDuration(10000);
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

}
