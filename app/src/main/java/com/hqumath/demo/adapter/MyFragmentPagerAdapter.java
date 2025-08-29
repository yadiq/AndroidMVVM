package com.hqumath.demo.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hqumath.demo.base.BaseFragment;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: MyFragmentPagerAdapter
 * 作    者: Created by gyd
 * 创建时间: 2019/9/2 15:15
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragmentList;
    private List<String> titles;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public void setData(List<BaseFragment> fragmentList, List<String> titles) {
        this.fragmentList = fragmentList;
        this.titles = titles;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : titles.get(position);
    }

}