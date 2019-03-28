package com.appstore.android.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.appstore.android.ui.fragment.CategoryAppFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangqi on 2019/3/28
 */
public class CategoryAppViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> titles = new ArrayList<>(3);
    private int categoryId;

    public CategoryAppViewPagerAdapter(FragmentManager fm, int categoryId) {
        super(fm);
        this.categoryId = categoryId;
        titles.add("精品");
        titles.add("排行");
        titles.add("新品");
    }

    @Override
    public Fragment getItem(int i) {
        return CategoryAppFragment.newInstance(categoryId, i);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
