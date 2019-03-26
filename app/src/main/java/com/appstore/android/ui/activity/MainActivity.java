package com.appstore.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.appstore.android.R;
import com.appstore.android.bean.User;
import com.appstore.android.common.AppIcons;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.ui.adapter.ViewPagerAdapter;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.mikepenz.iconics.IconicsDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {

        RxBus.get().register(this);

        initDrawerLayout();

        initTabLayout();
    }

    @Subscribe
    public void getUser(User user) {

    }

    private void initTabLayout() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
    }

    private void initDrawerLayout() {
        headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click headerView", Toast.LENGTH_SHORT).show();
            }
        });

        navigationView.getMenu().findItem(R.id.menu_app_update).setIcon(new IconicsDrawable(this, AppIcons.Icon.icon_upadte));
        navigationView.getMenu().findItem(R.id.menu_setting).setIcon(new IconicsDrawable(this, AppIcons.Icon.icon_setting));
        navigationView.getMenu().findItem(R.id.menu_app_uninstall).setIcon(new IconicsDrawable(this, AppIcons.Icon.icon_uninstall));
        navigationView.getMenu().findItem(R.id.menu_download_manager).setIcon(new IconicsDrawable(this, AppIcons.Icon.icon_download));
        navigationView.getMenu().findItem(R.id.menu_logout).setIcon(new IconicsDrawable(this, AppIcons.Icon.icon_logout));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                }
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.toolbar_menu);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();
        drawerlayout.addDrawerListener(actionBarDrawerToggle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }
}
