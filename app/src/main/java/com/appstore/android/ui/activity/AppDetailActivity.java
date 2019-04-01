package com.appstore.android.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appstore.android.R;
import com.appstore.android.di.component.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppDetailActivity extends BaseActivity {


    @BindView(R.id.img_view)
    ImageView imgView;
    @BindView(R.id.activity_appdetail)
    LinearLayout activityAppdetail;

    @Override
    public int setLayout() {
        return R.layout.activity_app_detail;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {
        View view = myApplication.getView();
        Bitmap viemBitmapCache = getViemBitmapCache(view);
        if (viemBitmapCache != null) {
            imgView.setImageBitmap(viemBitmapCache);
        }
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
