package com.appstore.android.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.appstore.android.bean.FragmentInfo;
import com.appstore.android.ui.fragment.CategoryFragment;
import com.appstore.android.ui.fragment.GamesFragment;
import com.appstore.android.ui.fragment.RankingFragment;
import com.appstore.android.ui.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangqi on 2019/2/18
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> fragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments() {
        fragments.add(new FragmentInfo("推荐", RecommendFragment.class));
        fragments.add(new FragmentInfo("排行", RankingFragment.class));
        fragments.add(new FragmentInfo("游戏", GamesFragment.class));
        fragments.add(new FragmentInfo("分类", CategoryFragment.class));
    }

    @Override
    public Fragment getItem(int i) {
        try {
            return (Fragment) fragments.get(i).getFragment().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }
}
