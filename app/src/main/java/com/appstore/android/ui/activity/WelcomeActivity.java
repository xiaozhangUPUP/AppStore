package com.appstore.android.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.appstore.android.R;
import com.appstore.android.common.Constants;
import com.appstore.android.common.util.SharePreferencesUtils;
import com.eftimoff.androipathview.PathView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.pathView)
    PathView pathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        pathView.getPathAnimator().delay(100).duration(5000).listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
            @Override
            public void onAnimationEnd() {
                jump();
            }
        }).start();
    }

    private void jump() {
        String stringByKey = SharePreferencesUtils.getStringByKey(Constants.IS_SHOW_GUIDE);

        if (TextUtils.isEmpty(stringByKey)) {
            startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
        } else {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        }
        finish();

    }
}
